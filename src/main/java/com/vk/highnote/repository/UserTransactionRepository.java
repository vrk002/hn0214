package com.vk.highnote.repository;

import com.vk.highnote.model.UserTransaction;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {
    List<UserTransaction> findByUserId(@NonNull Long userId);
}
