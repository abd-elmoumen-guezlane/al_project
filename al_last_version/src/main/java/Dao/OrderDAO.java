package Dao;
import Entity.Order;
import Entity.OrderItem;

public interface OrderDAO {
    void saveOrder(Order order);
    void saveOrderItem(OrderItem item);
}

