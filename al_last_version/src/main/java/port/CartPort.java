package port;

import Entity.CartItem;
import Entity.Product;
import jakarta.ejb.Local;

import java.util.ArrayList;

@Local
public interface CartPort {

    void addProduct(Product p);

    double getTotal();

    ArrayList<CartItem> getCarItems();

    void cartAction(String cartOp, long productId);

    void clearCart();
}
