package Dao;

import Entity.Order;
import Entity.OrderItem;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class OrderdaoImp implements OrderDAO {

    @PersistenceContext(unitName = "MiniShopPU")
    private EntityManager em;

    @Override
    public void saveOrder(Order order) {
        em.persist(order);
    }
    
     @Override
    public void saveOrderItem(OrderItem item) {
        em.persist(item);
    }
    
}