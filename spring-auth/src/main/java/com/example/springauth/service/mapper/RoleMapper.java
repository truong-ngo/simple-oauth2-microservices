package com.example.springauth.service.mapper;

import com.example.springauth.domain.RoleDomain;
import com.example.springauth.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDomain, Role> {

}
