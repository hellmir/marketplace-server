package com.personal.marketnote.file.adapter.in.client.file.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.file.adapter.in.client.file.controller.apidocs.AddFilesApiDocs;
import com.personal.marketnote.file.adapter.in.client.file.mapper.FileRequestToCommandMapper;
import com.personal.marketnote.file.adapter.in.client.file.request.AddFilesRequest;
import com.personal.marketnote.file.port.in.usecase.file.FileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;

@Controller
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileUseCase fileUseCase;

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
    public ResponseEntity<BaseResponse<Void>> addFiles(@ModelAttribute AddFilesRequest addFilesRequest) {
        fileUseCase.addFiles(FileRequestToCommandMapper.mapToCommand(addFilesRequest));

        return ResponseEntity.ok(
                BaseResponse.of(
                        null,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "파일 추가 성공"
                )
        );
    }
}
