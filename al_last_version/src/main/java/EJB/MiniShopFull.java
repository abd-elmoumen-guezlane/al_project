package EJB;

import Entity.CartItem;
import Entity.Product;
import jakarta.ejb.Stateful;
import port.CartPort;

import java.util.ArrayList;

@Stateful
public class MiniShopFull implements CartPort {

    private double total = 0;
    private final ArrayList<CartItem> cartItems = new ArrayList<>();

    public void addProduct(Product p) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(p.getId())) {
                item.increment();
                total += p.getSellingPrice();
                return;
            }
        }
        cartItems.add(new CartItem(p, 1));
        total += p.getSellingPrice();
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<CartItem> getCarItems() {
        return cartItems;
    }

    public void cartAction(String cartOp, long productId) {
        if (cartOp == null) {
            return;
        }
        switch (cartOp) {
            case "inc":
                increment(productId);
                break;
            case "dec":
                decrement(productId);
                break;
            case "del":
                remove(productId);
                break;
            default:
                break;
        }
    }

    private void increment(long productId) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                item.increment();
                total += item.getProduct().getSellingPrice();
                return;
            }
        }
    }

    private void decrement(long productId) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                if (item.getQuantity() > 1) {
                    item.decrement();
                    total -= item.getProduct().getSellingPrice();
                }
                return;
            }
        }
    }

    private void remove(long productId) {
        for (int i = 0; i < cartItems.size(); i++) {

        CartItem item = cartItems.get(i);

            if (item.getProduct().getId().equals(productId)) {
                 total -= item.getProduct().getSellingPrice() * item.getQuantity();
                 cartItems.remove(i);
                 return;
            }
        }
    }

    public void clearCart() {
        total = 0;
        cartItems.clear();
    }
}
