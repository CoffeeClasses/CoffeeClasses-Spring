<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="target" scope="request" type="fr.cyu.coffeeclasses.vanilla.entity.user.User"/>

<div class="confirmation-container">
	<h1>Confirmer la suppression</h1>
	<p>Êtes-vous sûr de vouloir supprimer cet utilisateur ? Cette action est irréversible.</p>

	<h2><%= (target.getFirstName() + " " + target.getLastName()) %></h2>

	<div class="button-container">
		<form action="${pageContext.request.contextPath}/panel/admin/users/delete" method="post">
			<input type="hidden" name="id" value="${target.id}">
			<button type="submit" class="confirm-button">Oui, supprimer</button>
		</form>
		<a href="${pageContext.request.contextPath}/panel/admin/users/edit?id=${target.id}" class="cancel-link">Annuler</a>
	</div>
</div>
