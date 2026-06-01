package com.fastshop.net.service;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.WaitForStaff;

import java.util.List;

public interface WaitForStaffService {
    void save(WaitForStaff waitForStaff);
    void delete(Long id);
    List<WaitForStaff> findAll();
    WaitForStaff findByAccount(Account account);
    WaitForStaff findById(Long id);
    void deleteByAccount(Account account);
}
