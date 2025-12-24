package com.oponiti.shopreward.common.config;

import com.oponiti.shopreward.domain.user.authentication.accesspermission.AccessPermissionRegistry;
import com.oponiti.shopreward.domain.user.authentication.accesspermission.AccessPermissionRegistryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessPermissionRegistryConfig {
    @Bean
    public AccessPermissionRegistry accessPermissionRegistry(AccessPermissionRegistryFactory accessPermissionRegistryFactory) {
        return accessPermissionRegistryFactory.createAccessPermissionRegistry();
    }
}
