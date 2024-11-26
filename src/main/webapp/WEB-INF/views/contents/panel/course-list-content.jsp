<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="course-list">
	<h1>Liste des cours</h1>

	<form action="${pageContext.request.contextPath}/panel/admin/courses" method="get">
		<div>
			<label for="search">Rechercher un cours :</label>
			<input type="text" id="search" name="search" placeholder="Entrez un nom de cours">
		</div>
		<div>
			<button type="submit">Rechercher</button>
		</div>
	</form>

	<div>
		<a href="${pageContext.request.contextPath}/panel/admin/courses/add">
			<button type="button">Ajouter un cours</button>
		</a>
	</div>

	<jsp:useBean id="courses" scope="request" type="java.util.Set<fr.cyu.coffeeclasses.vanilla.entity.element.Course>"/>
	<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.element.Course" %>
	<%
		if (courses != null && !courses.isEmpty()) { %>
	<h2>Liste des cours</h2>
	<table>
		<thead>
		<tr>
			<th>ID</th>
			<th>Nom du cours</th>
			<th>Professeur</th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="course" items="${courses}">
			<tr>
				<td>${course.id}</td>
				<td>${course.name}</td>
				<td>${course.teacher.firstName} ${course.teacher.lastName}</td>
				<td>
					<a href="${pageContext.request.contextPath}/panel/admin/courses/edit?id=${course.id}">Modifier</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<% } else { %>
	<p>Aucun cours trouvé.</p>
	<% } %>
</div>
</div>
