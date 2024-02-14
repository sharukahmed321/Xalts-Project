package com.xalts.repository;

import com.xalts.entity.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {
    Optional<UserTaskEntity> findByTaskId(Long Id);
}
