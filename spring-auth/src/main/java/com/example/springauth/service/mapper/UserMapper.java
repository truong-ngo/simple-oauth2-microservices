package com.example.springauth.service.mapper;

import com.example.springauth.domain.UserDomain;
import com.example.springauth.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper extends EntityMapper<UserDomain, User> {

}
