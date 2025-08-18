package com.voyachek.pharmacy.user.repository;

import com.voyachek.pharmacy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Optional<User> findUserById(UUID id);

    Optional<User> findUserByNameAndIdNot(String name, UUID id);

    Optional<User> findUserByEmailAndIdNot(String email, UUID id);
}
