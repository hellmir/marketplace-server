package com.personal.marketnote.community.adapter.out.persistence.post;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.community.adapter.out.mapper.PostJpaEntityToDomainMapper;
import com.personal.marketnote.community.adapter.out.persistence.post.entity.PostJpaEntity;
import com.personal.marketnote.community.adapter.out.persistence.post.repository.PostJpaRepository;
import com.personal.marketnote.community.domain.post.Post;
import com.personal.marketnote.community.port.out.post.SavePostPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class PostPersistenceAdapter implements SavePostPort {
    private final PostJpaRepository postJpaRepository;

    @Override
    public Post save(Post post) {
        PostJpaEntity savedEntity = postJpaRepository.save(PostJpaEntity.from(post));
        savedEntity.setIdToOrderNum();

        return PostJpaEntityToDomainMapper.mapToDomain(savedEntity).orElse(null);
    }
}
