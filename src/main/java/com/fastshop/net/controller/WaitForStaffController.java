package com.fastshop.net.controller;

import com.fastshop.net.model.Authority;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service.WaitForStaffService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class WaitForStaffController {
    @Autowired
    private WaitForStaffService waitForStaffService;
    @Autowired
    private  AuthorityService authorityService;
    @Autowired
    private  AccountService accountService;
    
    @Transactional
    @GetMapping("/check-wait/{username}")
    public String requestMethodName(@PathVariable String username) {
        waitForStaffService.deleteByAccount(accountService.findByUsername(username));
        authorityService.updateRoleByAccount(accountService.findByUsername(username));
        return "redirect:/admin/employee";
    }

    @Transactional
    @GetMapping("/delete-wait/{username}")
    public String deleteWait(@PathVariable String username) {
        waitForStaffService.deleteByAccount(accountService.findByUsername(username));
        return "redirect:/admin/employee";
    }
}
