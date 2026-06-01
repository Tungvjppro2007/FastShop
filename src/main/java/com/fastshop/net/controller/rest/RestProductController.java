package com.fastshop.net.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Product;
import com.fastshop.net.model.Product_Account;
import com.fastshop.net.service.AccountService;
import com.fastshop.net.service.ProductService;
import com.fastshop.net.service.Product_AccountService;
import com.fastshop.net.service._CookieService;

@CrossOrigin("*")
@RestController
public class RestProductController {
    @Autowired
    ProductService productSevice;
    @Autowired
    _CookieService cookie;
    @Autowired
    Product_AccountService product_AccountService;
    @Autowired
    AccountService accountService;

    // @GetMapping("/rest/products")
    // public List<Product> getAll(Model model) {
    //     return productSevice.findAll();
    // }
    @GetMapping("rest/products")
    public List<Product> getAll(Model model) {
        Account account = accountService.findByUsername(cookie.getValue("username"));
        List<Product_Account> product_Accounts = product_AccountService.getProduct_AccountsByAccount(account);
        ArrayList<Product> products = new ArrayList<>();
        for (Product_Account product_Account : product_Accounts) {
            Product product = product_Account.getProduct();
            products.add(product);
        }
        return products;
    }

    @GetMapping("/rest/products/{id}")
    public Product getProductById(@PathVariable("id") Integer id) {
        return productSevice.findById(id);
    }

    @PostMapping("/rest/products")
    public Product post(@RequestBody Product product) {
        productSevice.save(product);
        return product;
    }

    @PutMapping("/rest/products/{id}")
    public Product put(@PathVariable("id") Integer id, @RequestBody Product product) {
        productSevice.save(product);
        return product;
    }

    @DeleteMapping("/rest/products/{id}")
    public void delete(@PathVariable("id") Integer id) {
        productSevice.deleteById(id);
    }
}
