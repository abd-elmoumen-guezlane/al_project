package Servlets;

import Entity.Product;
import Entity.Order;
import exception.ProductNotFoundException;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import port.CartPort;
import port.CatalogPort;
import port.CheckoutPort;

import java.io.IOException;

@WebServlet(name = "MiniShopServlet", urlPatterns = {"/MiniShopServlet"})
public class MiniShopServlet extends HttpServlet {

    @Inject
    private CatalogPort catalog;

    @Inject
    private CartPort cart;

    @Inject
    private CheckoutPort checkout;
    
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("products", catalog.getCatalog());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        
        String orderState = request.getParameter("checkout");
        
        if("confirmOrder".equals(orderState)){
                String name = request.getParameter("customerName");
                String email = request.getParameter("email");
                String address = request.getParameter("address");
                
                Order order = checkout.confirmOrder(name, email, address, cart);
                cart.clearCart();

                session.setAttribute("total", 0);
                session.setAttribute("listdeproduit", null);
                
                request.setAttribute("order", order);
                request.getRequestDispatcher("confirmation.jsp").forward(request, response);
                return;    
        }
        
        if ("true".equals(request.getParameter("viderPanier"))) {
            cart.clearCart();
            session.setAttribute("total", cart.getTotal());
            session.setAttribute("listdeproduit", cart.getCarItems());
            response.sendRedirect("cart.jsp");
            return;
        }

        String cartOp = request.getParameter("cartOp");
        String produitId = request.getParameter("produitId");

        if (cartOp != null && produitId != null) {
            try {
                cart.cartAction(cartOp, Long.parseLong(produitId.trim()));
            } catch (NumberFormatException ex) {
                session.setAttribute("cartError", "Identifiant produit invalide.");
            }
            session.setAttribute("total", cart.getTotal());
            session.setAttribute("listdeproduit", cart.getCarItems());
            response.sendRedirect("cart.jsp");
            return;
        }

        String produit = request.getParameter("produit");
        boolean afficherSeulement = "true".equals(request.getParameter("afficherInfo"));
        String error = null;
        Product selected = null;

        if (produit == null || produit.trim().isEmpty()) {
            error = "Aucun produit sélectionné";
        } else {
            try {
                selected = catalog.findById(Long.parseLong(produit.trim()));
                if (!afficherSeulement) {
                    cart.addProduct(selected);
                }
            } catch (ProductNotFoundException e) {
                error = e.getMessage();
            } catch (NumberFormatException e) {
                error = "Identifiant produit invalide.";
            }
        }

        session.setAttribute("error", error);
        session.setAttribute("produit", selected);
        session.setAttribute("total", cart.getTotal());
        session.setAttribute("listdeproduit", cart.getCarItems());
        response.sendRedirect("result.jsp");
    }

}
