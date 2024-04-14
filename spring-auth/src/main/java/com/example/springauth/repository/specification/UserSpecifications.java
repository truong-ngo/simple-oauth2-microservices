package com.example.springauth.repository.specification;

import com.example.springauth.domain.UserDomain;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserSpecifications implements BaseSpecification<UserDomain> {
    public Specification<UserDomain> hasId(Long id) {
        return equal(id, "id");
    }

    public Specification<UserDomain> hasUsername(String username) {
        return equal(username, "username");
    }

    public Specification<UserDomain> usernameLike(String pattern) {
        return like(pattern, "username");
    }

    public Specification<UserDomain> isExpired() {
        return isTrue("accountExpired");
    }

    public Specification<UserDomain> isNonExpired() {
        return isFalse("accountExpired");
    }

    public Specification<UserDomain> isLocked() {
        return isTrue("accountLocked");
    }

    public Specification<UserDomain> isNonLocked() {
        return isFalse("accountLocked");
    }

    public Specification<UserDomain> isEnable() {
        return isTrue("enabled");
    }

    public Specification<UserDomain> isNotEnable() {
        return isFalse("enabled");
    }
}
