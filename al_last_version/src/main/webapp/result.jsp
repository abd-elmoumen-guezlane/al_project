<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Résultat Produit</title>
</head>
<body>

<p>
    <a href="cart.jsp">Voir le panier</a>
    &mdash;
    <a href="MiniShopServlet">Retour au catalogue</a>
</p>

<h1>Résultat</h1>

<c:if test="${not empty sessionScope.error}">
    <p style="color:red;"><c:out value="${sessionScope.error}"/></p>
</c:if>

<c:if test="${not empty sessionScope.produit}">
    <h2>Détails du produit</h2>
    <table border="1" cellpadding="6" cellspacing="0">
        <tr><th>PRODUCT_ID</th><td><c:out value="${sessionScope.produit.id}"/></td></tr>
        <tr><th>MANUFACTURER_ID</th><td><c:out value="${sessionScope.produit.manufacturerId}"/></td></tr>
        <tr><th>PRODUCT_CODE</th><td><c:out value="${sessionScope.produit.productCode}"/></td></tr>
        <tr><th>PURCHASE_COST</th><td><c:out value="${sessionScope.produit.purchaseCost}"/></td></tr>
        <tr><th>QUANTITY_ON_HAND</th><td><c:out value="${sessionScope.produit.quantityOnHand}"/></td></tr>
        <tr><th>MARKUP</th><td><c:out value="${sessionScope.produit.markup}"/></td></tr>
        <tr><th>AVAILABLE</th><td>${sessionScope.produit.available ? 'TRUE' : 'FALSE'}</td></tr>
        <tr><th>DESCRIPTION</th><td><c:out value="${sessionScope.produit.description}"/></td></tr>
        <tr><th>Prix de vente </th><td><c:out value="${sessionScope.produit.sellingPrice}"/></td></tr>
    </table>
    <form action="MiniShopServlet" method="post">
        <input type="hidden" name="produit" value="${sessionScope.produit.id}"/>
        <button type="submit">Ajouter au panier</button>
    </form>
    <c:if test="${not empty sessionScope.prix}">
        <p><strong>Prix utilisé pour la commande :</strong> <c:out value="${sessionScope.prix}"/></p>
    </c:if>
</c:if>

</body>
</html>
