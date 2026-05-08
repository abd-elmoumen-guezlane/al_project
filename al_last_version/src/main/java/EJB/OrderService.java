package EJB;

import Dao.OrderDAO;
import Entity.CartItem;
import Entity.Order;
import Entity.OrderItem;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import port.CartPort;
import port.CheckoutPort;

@Stateless
public class OrderService implements CheckoutPort {

    @EJB
    private OrderDAO orderDAO;

    @Override
    public Order confirmOrder(String name, String email, String address, CartPort cart) {


        Order order = new Order();
        order.setName(name);
        order.setEmail(email);
        order.setAddress(address);
        order.setTotal(cart.getTotal());


        orderDAO.saveOrder(order);

        for (CartItem p : cart.getCarItems()) {

            OrderItem item = new OrderItem();

            item.setOrder(order);
            item.setProduct(p.getProduct());
            item.setQuantity(p.getQuantity());
            item.setPrice(p.getProduct().getSellingPrice());

            orderDAO.saveOrderItem(item);
        }

        return order;
    }
}