package com.personal.marketnote.file.adapter.in.client.file.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.file.adapter.in.client.file.controller.apidocs.AddFilesApiDocs;
import com.personal.marketnote.file.adapter.in.client.file.controller.apidocs.DeleteFileApiDocs;
import com.personal.marketnote.file.adapter.in.client.file.controller.apidocs.GetFilesApiDocs;
import com.personal.marketnote.file.adapter.in.client.file.mapper.FileRequestToCommandMapper;
import com.personal.marketnote.file.adapter.in.client.file.request.AddFilesRequest;
import com.personal.marketnote.file.port.in.result.GetFilesResult;
import com.personal.marketnote.file.port.in.usecase.file.AddFileUseCase;
import com.personal.marketnote.file.port.in.usecase.file.DeleteFileUseCase;
import com.personal.marketnote.file.port.in.usecase.file.GetFileUseCase;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_POINTCUT;

@Controller
@RequestMapping("/api/v1/files")
@Tag(name = "파일 API", description = "파일 관련 API")
@RequiredArgsConstructor
public class FileController {
    private final AddFileUseCase addFileUseCase;
    private final GetFileUseCase getFileUseCase;
    private final DeleteFileUseCase deleteFileUseCase;

    /**
     * 파일 추가
     *
     * @param addFilesRequest 파일 추가 요청
     * @Author 성효빈
     * @Date 2026-01-03
     * @Description 파일을 추가합니다.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @AddFilesApiDocs
    public ResponseEntity<BaseResponse<Void>> addFiles(
            @Parameter(hidden = true) @ModelAttribute AddFilesRequest addFilesRequest
    ) {
        addFileUseCase.addFiles(FileRequestToCommandMapper.mapToCommand(addFilesRequest));

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "파일 추가 성공"
                ),
                HttpStatus.CREATED
        );
    }

    /**
     * 파일 목록 조회
     *
     * @param ownerType 소유 도메인 타입
     * @param ownerId   소유 도메인 ID
     * @param sort      파일 종류
     * @Author 성효빈
     * @Date 2026-01-07
     * @Description 파일 목록을 조회합니다.
     */
    @GetMapping
    @GetFilesApiDocs
    public ResponseEntity<BaseResponse<GetFilesResult>> getFiles(
            @RequestParam("ownerType") String ownerType,
            @RequestParam("ownerId") Long ownerId,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        GetFilesResult result = getFileUseCase.getFiles(ownerType, ownerId, sort);

        return new ResponseEntity<>(
                BaseResponse.of(
                        result,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파일 목록 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 파일 삭제
     *
     * @param id 파일 ID
     * @Author 성효빈
     * @Date 2026-01-07
     * @Description 파일을 삭제합니다.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize(ADMIN_POINTCUT)
    @DeleteFileApiDocs
    public ResponseEntity<BaseResponse<Void>> deleteFile(@PathVariable("id") Long id) {
        deleteFileUseCase.delete(id);

        return new ResponseEntity<>(
                BaseResponse.of(
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파일 삭제 성공"
                ),
                HttpStatus.OK
        );
    }
}
