package com.fastshop.net.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Role;
import com.fastshop.net.model.WaitForStaff;

public interface AuthorityService {
    void save(Authority authority);
    void deleteById(Integer id);
    void deleteByAuthority(Authority authority);
    Role getRoleByAccount(Account account);
    Authority findByAccount(Account account);
    Integer countByRoleEqualsUser();
    List<Authority> findAll();
    List<Account> getListStaff();
    List<Account> findByKeyword(String keyword);
    List<WaitForStaff> getWaitForStaff();

    @Transactional
    void updateRoleByAccount(Account account);
}
