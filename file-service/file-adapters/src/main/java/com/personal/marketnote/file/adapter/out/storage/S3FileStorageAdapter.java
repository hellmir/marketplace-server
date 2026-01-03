package com.personal.marketnote.file.adapter.out.storage;

import com.personal.marketnote.common.domain.file.OwnerType;
import com.personal.marketnote.common.utility.FormatConverter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.file.adapter.out.exception.S3UploadFailedException;
import com.personal.marketnote.file.port.out.storage.UploadFilesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.FIRST_ERROR_CODE;

@Component
@RequiredArgsConstructor
public class S3FileStorageAdapter implements UploadFilesPort {
    private static final String TLS_HTTP_PROTOCOL = "https://";
    private static final String S3_ADDRESS = "s3.amazonaws.com";

    private final S3Client s3Client;

    @Value("${file.s3.bucket-name}")
    private String s3BucketName;

    @Override
    public List<String> uploadFiles(List<MultipartFile> files, OwnerType ownerType, Long ownerId) {
        if (!FormatValidator.hasValue(files)) {
            return List.of();
        }

        List<String> urls = new ArrayList<>(files.size());
        for (MultipartFile file : files) {
            urls.add(upload(file, ownerType, ownerId));
        }

        return urls;
    }

    private String upload(MultipartFile multipartFile, OwnerType ownerType, Long ownerId) {
        String originalFileName = multipartFile.getOriginalFilename();
        String sanitizedFilename = FormatValidator.hasValue(originalFileName)
                ? FormatConverter.sanitizeFileName(originalFileName)
                : "";
        long ms = System.currentTimeMillis();
        String key = String.format("%s/%d/%s_%s", ownerType.name().toLowerCase(), ownerId, ms, sanitizedFilename);

        try {
            File tempFile = convertMultipartFileToFile(multipartFile);
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(s3BucketName)
                            .key(key)
                            .build(),
                    Paths.get(tempFile.getPath()));
            tempFile.delete();
        } catch (IOException ie) {
            throw new S3UploadFailedException(FIRST_ERROR_CODE, ie);
        }

        return String.format("%s%s.%s/%s", TLS_HTTP_PROTOCOL, s3BucketName, S3_ADDRESS, key);
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(String.format("%s/%s", System.getProperty("java.io.tmpdir"), file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }
}


