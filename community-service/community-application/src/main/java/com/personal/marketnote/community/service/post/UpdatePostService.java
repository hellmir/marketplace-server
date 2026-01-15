package com.personal.marketnote.community.service.post;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.domain.post.Post;
import com.personal.marketnote.community.exception.PostNotEditableException;
import com.personal.marketnote.community.port.in.command.post.UpdatePostCommand;
import com.personal.marketnote.community.port.in.usecase.post.GetPostUseCase;
import com.personal.marketnote.community.port.in.usecase.post.UpdatePostUseCase;
import com.personal.marketnote.community.port.out.post.UpdatePostPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class UpdatePostService implements UpdatePostUseCase {
    private final GetPostUseCase getPostUseCase;
    private final UpdatePostPort updatePostPort;

    @Override
    public void updatePost(UpdatePostCommand command) {
        Long id = command.id();
        Post post = getPostUseCase.getPost(id);

        if (!post.isEditable()) {
            throw new PostNotEditableException();
        }

        post.update(command.title(), command.content());
        updatePostPort.update(post);
    }
}
