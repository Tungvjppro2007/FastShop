package com.fastshop.net.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import com.fastshop.net.model.WaitForStaff;
import com.fastshop.net.service.WaitForStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Authority;
import com.fastshop.net.model.Role;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.AuthorityService;
import com.fastshop.net.service._CookieService;
import org.springframework.web.servlet.view.RedirectView;

@CrossOrigin("*")
@RestController
public class RestAuthorityController {
    @Autowired
    AuthorityService authorityService;

    @Autowired
    _CookieService cookieService;

    @Autowired
    AccountService accountService;

    @Autowired
    WaitForStaffService waitForStaffService;

    @GetMapping("/rest/authorities")
    public List<Authority> getAll(Model model) {
        return authorityService.findAll();
    }

    // lấy tất cả danh sách account có role là staff
    @GetMapping("/rest/authorities/staff")
    public List<Account> getAccountStaff() {
        return authorityService.findAll().stream()
                .filter(auth -> auth.getRole().getId().equals("STAFF"))
                .map(auth -> auth.getAccount())
                .collect(Collectors.toList());
    }

    // lấy tất cả danh sách account có role là staff
    @GetMapping("/rest/authorities/staff/active")
    public List<Authority> getAccountStaffActive() {
        return authorityService.findAll().stream()
                .filter(auth -> auth.getRole().getId().equals("STAFF"))
                .filter(auth -> auth.getAccount().getActive() != null && auth.getAccount().getActive() != false)
                .collect(Collectors.toList());
    }

    // lấy authorities id từ username của account
    @GetMapping("/rest/authorities/account/{username}")
    public Integer getAuthIdByUsername(@PathVariable("username") String username) {
        return authorityService.findAll().stream()
                .filter(auth -> auth.getAccount().getUsername().equals(username))
                .collect(Collectors.toList()).get(0).getId();
    }

    // lấy role của accout từ username trong authorities
    @GetMapping("/rest/authorities/role/{username}")
    public String getRoleByUsername(@PathVariable("username") String username) {
        return authorityService.findAll().stream()
                .filter(auth -> auth.getAccount().getUsername().equals(username))
                .collect(Collectors.toList()).get(0).getRole().getName();
    }

    @PostMapping("/rest/authorities")
    public Authority post(@RequestBody Authority authority) {
        authorityService.save(authority);
        return authority;
    }

    @PutMapping("/rest/authorities/{username}")
    public Authority put(@PathVariable("username") String username, @RequestBody Authority authority) {
        authorityService.save(authority);
        return authority;
    }

    @DeleteMapping("/rest/authorities/{username}")
    public void delete(@PathVariable("username") String username) {
        authorityService.deleteById(getAuthIdByUsername(username));
    }

    @GetMapping("/registerAsStaff")
    public RedirectView registerAsStaff(@ModelAttribute("auth") Authority auth) {
        try {
            Account account = accountService.findByUsername(cookieService.getValue("username"));
            WaitForStaff waitForStaff = new WaitForStaff();
            waitForStaff.setAccount(account);
            waitForStaffService.save(waitForStaff);
        //    authorityService.updateRoleByAccount(account);
//            return new ResponseEntity<>("Role updated successfully", HttpStatus.OK);
            return new RedirectView("/fastshop.com");
        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            return new RedirectView("/fastshop.com");
        }
    }

    @DeleteMapping("/confirmStaff/{id}")
    public void confirmStaff(@PathVariable("id") Long id) {
        Account account = waitForStaffService.findById(id).getAccount();
        authorityService.updateRoleByAccount(account);
        waitForStaffService.delete(id);
    }

//    @GetMapping("/wait-for-staff")
//    public List<WaitForStaff> getWaitForStaff() {
//        return waitForStaffService.findAll();
//    }
}
