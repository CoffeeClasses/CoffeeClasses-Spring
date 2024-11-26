<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="target" scope="request" type="fr.cyu.coffeeclasses.vanilla.entity.user.User"/>
<jsp:useBean id="availableCourses" scope="request" type="java.util.Set<fr.cyu.coffeeclasses.vanilla.entity.element.Course>"/>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Teacher" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Student" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.element.Course" %>

<div class="search-users">
	<h1>Modifier un utilisateur</h1>
	<form action="${pageContext.request.contextPath}/panel/admin/users/edit" method="post">
		<input type="hidden" name="id" value="${target.id}" />

		<label for="firstName">Prénom :</label>
		<input type="text" id="firstName" name="firstName" value="${target.firstName}" required /><br/>

		<label for="lastName">Nom :</label>
		<input type="text" id="lastName" name="lastName" value="${target.lastName}" required /><br/>

		<label for="birthDate">Date de naissance :</label>
		<input type="date" id="birthDate" name="birthDate" value="${target.birthDate}" required /><br/>

		<label for="email">Mail :</label>
		<input type="email" id="email" name="email" value="${target.email}" required /><br/>

		<label for="password">Mot de passe :</label>
		<input type="password" id="password" name="password" /><br/>

		<% if (target instanceof Student) { %>
			<h3>Inscriptions</h3>
			<fieldset>
				<legend>Sélectionner des cours</legend>
					<% for (Course course : availableCourses) { String checked = ((Student) target).hasCourse(course) ? "checked" : ""; %>
						<label>
							<input type="checkbox" name="courses" value="<%= course.getId() %>" <%= checked %> />
							<%= course.getName() %>
						</label><br/>
					<% } %>
			</fieldset>
		<% } else if (target instanceof Teacher) { %>
			<h3>Assignations</h3>
			<fieldset>
				<legend>Sélectionner des cours</legend>
				<% for (Course course : availableCourses) { String checked = ((Teacher) target).getCourses().contains(course) ? "checked" : ""; %>
				<label>
					<input type="checkbox" name="courses" value="<%= course.getId() %>" <%= checked %> />
					<%= course.getName() %>
				</label><br/>
				<% } %>
			</fieldset>
		<% } %>

		<button type="submit">Enregistrer</button>
	</form>

	<form action="${pageContext.request.contextPath}/panel/admin/users/delete" method="get">
		<input type="hidden" name="id" value="${target.id}" />
		<button type="submit">Supprimer</button>
	</form>
</div>