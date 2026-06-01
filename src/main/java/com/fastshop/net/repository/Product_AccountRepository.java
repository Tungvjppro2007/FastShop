package com.fastshop.net.repository;

import com.fastshop.net.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Product;
import com.fastshop.net.model.Product_Account;

import java.util.List;

@Repository
public interface Product_AccountRepository extends JpaRepository<Product_Account, Long>{
    Product_Account findByProduct(Product product);
    List<Product_Account> findByAccount(Account account);
}
