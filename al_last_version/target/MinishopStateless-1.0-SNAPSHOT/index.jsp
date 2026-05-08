<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${products == null}">
    <jsp:forward page="/MiniShopServlet"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
    <title>MiniShop</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>MiniShop - choisir un produit</h1>
<form action="MiniShopServlet" method="post">
    <input type="hidden" name="afficherInfo" value="true"/>
    <label for="produit">choisir un produit</label>
    <select id="produit" name="produit">
        <option value=""> -- Sélectionner un produit -- </option>
        <c:forEach var="product" items="${products}">
            <option value="${product.id}">
                <c:out value="${not empty product.description ? product.description : product.id}"/>
            </option>
        </c:forEach>
    </select>
    <button type="submit">Voir le produit</button>
</form>
<p>
    <a href="cart.jsp">Voir le panier</a>
</p>
</body>
</html>
