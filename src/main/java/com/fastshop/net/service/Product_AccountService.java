package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Product;
import com.fastshop.net.model.Product_Account;

public interface Product_AccountService {
    Account getAccountWhoPostedProduct(Product product);
    Product_Account addProduct_Account(Product_Account product_account);
    List<Product_Account> getProduct_AccountsByAccount(Account account);
}
