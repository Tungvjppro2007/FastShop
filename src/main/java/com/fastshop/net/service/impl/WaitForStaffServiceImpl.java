package com.fastshop.net.service.impl;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.WaitForStaff;
import com.fastshop.net.repository.WaitForStaffDAO;
import com.fastshop.net.service.WaitForStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitForStaffServiceImpl implements WaitForStaffService {
    @Autowired
    private WaitForStaffDAO waitForStaffDAO;
    @Override
    public void save(WaitForStaff waitForStaff) {
        waitForStaffDAO.save(waitForStaff);
    }

    @Override
    public void delete(Long id) {
        waitForStaffDAO.deleteById(id);
    }

    @Override
    public List<WaitForStaff> findAll() {
        return waitForStaffDAO.findAll();
    }

    @Override
    public WaitForStaff findByAccount(Account account) {
        return waitForStaffDAO.findByAccount(account);
    }

    @Override
    public WaitForStaff findById(Long id) {
        return waitForStaffDAO.findById(id).orElse(null);
    }

    @Override
    public void deleteByAccount(Account account) {
        waitForStaffDAO.deleteByAccount(account);
    }
}
