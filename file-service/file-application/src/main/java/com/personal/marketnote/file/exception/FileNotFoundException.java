package com.personal.marketnote.file.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class FileNotFoundException extends EntityNotFoundException {
    private static final String FILE_NOT_FOUND_EXCEPTION_MESSAGE = "파일을 찾을 수 없습니다. 전송된 파일 ID: %d";

    public FileNotFoundException(Long id) {
        super(String.format(FILE_NOT_FOUND_EXCEPTION_MESSAGE, id));
    }
}
