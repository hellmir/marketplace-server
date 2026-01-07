package com.personal.marketnote.file.service.file;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.port.in.usecase.file.DeleteFileUseCase;
import com.personal.marketnote.file.port.in.usecase.file.GetFileUseCase;
import com.personal.marketnote.file.port.out.file.UpdateFilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class DeleteFileService implements DeleteFileUseCase {
    private final GetFileUseCase getFileUseCase;
    private final UpdateFilePort updateFilePort;

    @Override
    public void delete(Long id) {
        FileDomain file = getFileUseCase.getFile(id);
        file.delete();
        updateFilePort.update(file);
    }
}
