package dao;

import entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller p);
    void update(Seller p);
    void delete(Seller p);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
}
