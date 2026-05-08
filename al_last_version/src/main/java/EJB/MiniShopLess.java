package EJB;

import Dao.ProductDAO;
import Entity.Product;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import port.CatalogPort;

import java.util.List;

@Stateless
public class MiniShopLess implements CatalogPort {

    @EJB
    private ProductDAO productDAO;

    public Product findById(Long id) {
        return productDAO.findById(id);
    }
    
    public List<Product> getCatalog() {
        return productDAO.findAllOrdered();
    }
}
