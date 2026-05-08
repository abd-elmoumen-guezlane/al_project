package exception;

import jakarta.ejb.ApplicationException;

/**
 * Produit absent en base : ne doit pas faire échouer la transaction EJB (évite HTTP 500).
 */
@ApplicationException(rollback = false)
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
