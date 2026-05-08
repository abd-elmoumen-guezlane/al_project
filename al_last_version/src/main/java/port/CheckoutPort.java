package port;

import Entity.Order;
import jakarta.ejb.Local;

@Local
public interface CheckoutPort {

    Order confirmOrder(String name, String email, String address, CartPort cart);
}
