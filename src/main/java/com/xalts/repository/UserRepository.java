package com.xalts.repository;

import com.xalts.entity.UserEntity;
import com.xalts.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByUserLoginId(UUID userId);

}
