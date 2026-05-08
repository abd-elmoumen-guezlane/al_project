<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmation de commande</title>
</head>

<body>

<h1>Commande confirmée avec succès</h1>

<hr/>

<h2>Informations client</h2>

<p><b>Nom :</b> ${order.name}</p>
<p><b>Email :</b> ${order.email}</p>
<p><b>Adresse :</b> ${order.address}</p>

<hr/>

<h2>Détails de la commande</h2>

<p><b>ID commande :</b> ${order.id}</p>
<p><b>Total :</b> ${order.total} DA</p>

<hr/>

<p>Merci pour votre commande </p>

<p>
    <a href="MiniShopServlet">Retour à la boutique</a>
</p>

</body>
</html>