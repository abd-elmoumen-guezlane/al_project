package Dao;

import Entity.Product;
import exception.ProductNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProductDaoImp implements ProductDAO {

    @PersistenceContext(unitName = "MiniShopPU")
    private EntityManager em;

    @Override
    public Product findById(Long id) {
        Product p = em.find(Product.class, id);

        if (p == null) {
            throw new ProductNotFoundException("Produit inconnu.");
        }

        return p;
    }

    @Override
    public List<Product> findAllOrdered() {
        return em.createNamedQuery("Product.findAllOrdered", Product.class).getResultList();
    }
}
