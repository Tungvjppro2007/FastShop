package com.fastshop.net.repository;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.WaitForStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitForStaffDAO extends JpaRepository<WaitForStaff, Long> {
    WaitForStaff findByAccount(Account account);

    void deleteByAccount(Account account);
}
