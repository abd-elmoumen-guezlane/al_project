package Dao;

import Entity.Product;

import java.util.List;

public interface ProductDAO {
    Product findById(Long id);

    List<Product> findAllOrdered();
}
