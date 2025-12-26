package com.personal.shop.user.adapter.out.persistence.authentication.repository;

import com.personal.shop.user.adapter.out.persistence.authentication.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleJpaEntity, String> {
}
