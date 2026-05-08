<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Panier</title>
</head>
<body>

<p>
    <a href="MiniShopServlet">Retour au catalogue</a>
    <a href="result.jsp">Dernier résultat</a>
</p>

<h1>Panier</h1>

<c:if test="${not empty sessionScope.cartError}">
    <p style="color:#c00"><c:out value="${sessionScope.cartError}"/></p>
    <c:remove var="cartError" scope="session"/>
</c:if>

<c:if test="${empty sessionScope.listdeproduit}">
    <p>Le panier est vide.</p>
</c:if>

<c:if test="${not empty sessionScope.listdeproduit}">
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Description</th>
                <th>Prix unit.</th>
                <th>Qté</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="line" items="${sessionScope.listdeproduit}">
                <tr>
                    <td><c:out value="${line.product.id}"/></td>
                    <td><c:out value="${line.product.description}"/></td>
                    <td><c:out value="${line.product.sellingPrice}"/></td>
                    <td><c:out value="${line.quantity}"/></td>
                    <td>
                        <form action="/MiniShopServlet" method="post" style="display:inline;">
                            <input type="hidden" name="cartOp" value="inc"/>
                            <input type="hidden" name="produitId" value="${line.product.id}"/>
                            <button type="submit" title="Ajouter une unité">+</button>
                        </form>
                        <c:if test="${line.quantity > 1}">
                            <form action="/MiniShopServlet" method="post" style="display:inline;">
                                <input type="hidden" name="cartOp" value="dec"/>
                                <input type="hidden" name="produitId" value="${line.product.id}"/>
                                <button type="submit" title="Retirer une unité">−</button>
                            </form>
                        </c:if>
                        <form action="/MiniShopServlet" method="post" style="display:inline;">
                            <input type="hidden" name="cartOp" value="del"/>
                            <input type="hidden" name="produitId" value="${line.product.id}"/>
                            <button type="submit">Retirer du panier</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="4"><strong>Total</strong></td>
                <td><strong><c:out value="${sessionScope.total}"/></strong></td>
            </tr>
        </tfoot>
    </table>
    
    <form action="checkout.jsp" method="get">
        <button type="submit">Commander</button>
    </form>        
            
    <form action="/MiniShopServlet" method="post" style="margin-top:1em;">
        <input type="hidden" name="viderPanier" value="true"/>
        <button type="submit">Vider le panier</button>
    </form>
</c:if>

</body>
</html>
