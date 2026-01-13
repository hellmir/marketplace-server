package com.personal.marketnote.community.service.post;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.domain.post.Post;
import com.personal.marketnote.community.mapper.PostCommandToStateMapper;
import com.personal.marketnote.community.port.in.command.post.RegisterPostCommand;
import com.personal.marketnote.community.port.in.result.post.RegisterPostResult;
import com.personal.marketnote.community.port.in.usecase.post.RegisterPostUseCase;
import com.personal.marketnote.community.port.out.post.SavePostPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class RegisterPostService implements RegisterPostUseCase {
    private final SavePostPort savePostPort;

    @Override
    public RegisterPostResult registerPost(RegisterPostCommand command) {
        Post savedPost = savePostPort.save(
                Post.from(PostCommandToStateMapper.mapToState(command))
        );

        return RegisterPostResult.from(savedPost);
    }
}
