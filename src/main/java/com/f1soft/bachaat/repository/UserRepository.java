package com.f1soft.bachaat.repository;

import com.f1soft.bachaat.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByMobileNumber(String mobileNumber);

    @Override
    Page<User> findAll(Pageable pageable);
}
