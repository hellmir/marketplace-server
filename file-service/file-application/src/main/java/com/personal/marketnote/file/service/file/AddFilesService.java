package com.personal.marketnote.file.service.file;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.domain.file.FileSort;
import com.personal.marketnote.common.domain.file.OwnerType;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.domain.file.ResizedFile;
import com.personal.marketnote.file.mapper.FileCommandToDomainMapper;
import com.personal.marketnote.file.port.in.command.AddFileCommand;
import com.personal.marketnote.file.port.in.command.AddFilesCommand;
import com.personal.marketnote.file.port.in.usecase.file.AddFileUseCase;
import com.personal.marketnote.file.port.out.file.SaveFilesPort;
import com.personal.marketnote.file.port.out.resized.SaveResizedFilesPort;
import com.personal.marketnote.file.port.out.storage.UploadFilesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class AddFilesService implements AddFileUseCase {
    private final UploadFilesPort uploadFilesPort;
    private final SaveFilesPort saveFilesPort;
    private final SaveResizedFilesPort saveResizedFilesPort;

    public void addFiles(AddFilesCommand addFilesCommand) {
        List<FileDomain> fileDomains = FileCommandToDomainMapper.mapToDomain(addFilesCommand);

        List<MultipartFile> originals = addFilesCommand.fileInfo()
                .stream()
                .map(AddFileCommand::file)
                .toList();

        List<String> s3Urls = uploadFilesPort.uploadFiles(
                originals,
                OwnerType.from(addFilesCommand.ownerType()),
                addFilesCommand.ownerId()
        );

        List<FileDomain> savedFiles = saveFilesPort.saveAll(fileDomains, s3Urls);

        List<MultipartFile> resizedToUpload = new ArrayList<>();
        List<ResizedFile> resizedToSave = new ArrayList<>();

        for (int i = 0; i < savedFiles.size(); i++) {
            resize(savedFiles.get(i), originals.get(i), resizedToUpload, resizedToSave);
        }

        if (FormatValidator.hasValue(resizedToUpload)) {
            List<String> resizedS3Urls = uploadFilesPort.uploadFiles(
                    resizedToUpload,
                    OwnerType.from(addFilesCommand.ownerType()),
                    addFilesCommand.ownerId()
            );

            if (FormatValidator.hasValue(resizedToSave)) {
                List<ResizedFile> resizedWithUrls = new ArrayList<>(resizedToSave.size());
                for (int i = 0; i < resizedToSave.size(); i++) {
                    ResizedFile base = resizedToSave.get(i);
                    resizedWithUrls.add(ResizedFile.of(base.getFileId(), base.getSize(), resizedS3Urls.get(i)));
                }
                saveResizedFilesPort.saveAll(resizedWithUrls);
            }
            return;
        }

        // 업로드할 리사이즈 파일이 없으면 저장도 없음
    }

    private void resize(
            FileDomain savedFile, MultipartFile originalFile,
            List<MultipartFile> resizedToUpload, List<ResizedFile> resizedToSave
    ) {
        FileSort sort = savedFile.getSort();

        if (sort.isCatalogImage()) {
            int size = 300;

            try {
                MultipartFile resizedFile = resizeSquare(
                        originalFile, size, size,
                        appendSizeToFilename(originalFile.getOriginalFilename(), size + "x" + size)
                );
                resizedToUpload.add(resizedFile);
                resizedToSave.add(ResizedFile.of(savedFile.getId(), size + "x" + size));
            } catch (IOException ignored) {
            }

            return;
        }

        if (sort.isRepresentativeImage()) {
            List<Integer> widths = List.of(600, 800);
            for (int width : widths) {
                try {
                    MultipartFile resized = resizeByWidth(
                            originalFile, width,
                            appendSizeToFilename(originalFile.getOriginalFilename(), String.valueOf(width))
                    );
                    resizedToUpload.add(resized);
                    resizedToSave.add(ResizedFile.of(savedFile.getId(), String.valueOf(width)));
                } catch (IOException ignored) {
                }
            }
        }
    }

    private MultipartFile resizeSquare(MultipartFile original, int targetW, int targetH, String newOriginalFilename) throws IOException {
        BufferedImage src = ImageIO.read(original.getInputStream());
        BufferedImage dst = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dst.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, targetW, targetH, null);
        g2.dispose();

        String ext = getExtension(newOriginalFilename, "png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(dst, ext, baos);

        return new InMemoryMultipartFile("file", newOriginalFilename, "image/" + ext, baos.toByteArray());
    }

    private MultipartFile resizeByWidth(MultipartFile original, int targetWidth, String newOriginalFilename) throws IOException {
        BufferedImage src = ImageIO.read(original.getInputStream());
        int origW = src.getWidth();
        int origH = src.getHeight();
        int targetH = (int) Math.round((double) origH * targetWidth / origW);

        BufferedImage dst = new BufferedImage(targetWidth, targetH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dst.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, targetWidth, targetH, null);
        g2.dispose();

        String ext = getExtension(newOriginalFilename, "png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(dst, ext, baos);

        return new InMemoryMultipartFile("file", newOriginalFilename, "image/" + ext, baos.toByteArray());
    }

    private String getExtension(String filename, String defaultExt) {
        if (filename == null) return defaultExt;
        int idx = filename.lastIndexOf('.');
        if (idx < 0) return defaultExt;
        return filename.substring(idx + 1).toLowerCase();
    }

    private String appendSizeToFilename(String originalFilename, String sizeTag) {
        if (originalFilename == null) {
            return sizeTag;
        }
        int idx = originalFilename.lastIndexOf('.');
        if (idx < 0) {
            return originalFilename + "_" + sizeTag;
        }
        String name = originalFilename.substring(0, idx);
        String ext = originalFilename.substring(idx + 1);
        return name + "_" + sizeTag + "." + ext;
    }
}
