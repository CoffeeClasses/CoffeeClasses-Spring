<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="add-user">
	<h1>Ajouter un utilisateur</h1>
	<form action="${pageContext.request.contextPath}/panel/admin/users/add" method="post">
		<label for="firstName">Prénom :</label>
		<input type="text" id="firstName" name="firstName" required /><br/>

		<label for="lastName">Nom :</label>
		<input type="text" id="lastName" name="lastName" required /><br/>

		<label for="birthDate">Date de naissance :</label>
		<input type="date" id="birthDate" name="birthDate" value="${target.birthDate}" required /><br/>

		<label for="email">E-mail :</label>
		<input type="email" id="email" name="email" required /><br/>

		<label for="password">Mot de passe :</label>
		<input type="password" id="password" name="password" required /><br/>

		<label for="role">Rôle :</label>
		<select name="role" id="role" required>
			<option value="student">Étudiant</option>
			<option value="teacher">Professeur</option>
			<option value="administrator">Administrateur</option>
		</select><br/>

		<button type="submit">Créer</button>
	</form>
</div>
