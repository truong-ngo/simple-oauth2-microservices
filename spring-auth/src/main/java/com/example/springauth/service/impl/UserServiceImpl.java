package com.example.springauth.service.impl;

import com.example.springauth.model.User;
import com.example.springauth.repository.UserRepository;
import com.example.springauth.repository.specification.UserSpecifications;
import com.example.springauth.service.core.UserService;
import com.example.springauth.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserSpecifications specifications;

    @Override
    @jakarta.transaction.Transactional
    public void createUser(UserDetails user) {
        repository.save(mapper.toEntity((User) user));
    }

    @Override
    @Transactional
    public void updateUser(UserDetails user) {
        repository.save(mapper.toEntity((User) user));
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        repository.delete(Specification.where(specifications.hasUsername(username)));
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            throw new AccessDeniedException(
                    "Can't change password as no Authentication object found in context " + "for current user.");
        }
        String username = currentUser.getName();
        log.info(String.format("Changing password for user '%s'", username));
        User user = (User) loadUserByUsername(username);
        Assert.state(user != null, "Current user doesn't exist in database.");
        Assert.state(user.getPassword().equals(oldPassword), "Old password not match.");
        user.setPassword(newPassword);
        repository.save(mapper.toEntity(user));
    }

    @Override
    public boolean userExists(String username) {
        return repository.exists(Specification.where(specifications.hasUsername(username)));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findOne(Specification.where(specifications.hasUsername(username))).map(mapper::toDto).orElse(null);
    }
}
