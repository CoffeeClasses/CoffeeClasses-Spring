<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form action="${pageContext.request.contextPath}/login" method="post">
	<h2>Connexion au serveur</h2>

	<div class="form-group">
		<label for="mail">E-mail :</label>
		<input type="text" id="mail" name="mail" required>
	</div>

	<div class="form-group">
		<label for="password">Mot de passe :</label>
		<input type="password" id="password" name="password" required>
	</div>

	<button type="submit">Se connecter</button>
	<!-- p><a href="${pageContext.request.contextPath}/password-restore">Vous avez oublié votre mot de passe ?</a></p -->
</form>
