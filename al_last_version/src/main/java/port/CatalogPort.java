package port;

import Entity.Product;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface CatalogPort {

    Product findById(Long id);

    List<Product> getCatalog();
}
