<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
</head>

<body>

<h1>Checkout</h1>

<h2>Informations client</h2>

<form action="MiniShopServlet" method="post">

    <input type="hidden" name="checkout" value="confirmOrder"/>

    <label>Nom complet :</label><br/>
    <input type="text"
           name="customerName"
           placeholder="Entrez votre nom complet"
           required />
    <br/><br/>

    <label>Adresse :</label><br/>
    <textarea name="address"
              rows="4"
              cols="30"
              placeholder="Entrez votre adresse complète"
              required></textarea>
    <br/><br/>

    <label>Email :</label><br/>
    <input type="email"
           name="email"
           placeholder="exemple@gmail.com"
           required />
    <br/><br/>

    <button type="submit">
        Confirmer la commande
    </button>

</form>

<br/>

<a href="/cart.jsp">
    Retour au panier
</a>

</body>
</html>