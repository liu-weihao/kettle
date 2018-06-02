package com.yoogurt.kettle.repository;

import com.yoogurt.kettle.beans.AuthorizedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizedUserRepository extends JpaRepository<AuthorizedUser, Integer> {
}
