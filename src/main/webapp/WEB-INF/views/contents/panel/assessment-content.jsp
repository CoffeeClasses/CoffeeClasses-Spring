<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.element.Assessment" %>
<jsp:useBean id="teacher" scope="request" type="fr.cyu.coffeeclasses.vanilla.entity.user.Teacher"/>

<div class="assessment-management">
	<h1>Gestion des notes et évaluations</h1>
	<h3>Mes évaluations</h3>
		<ul id="grade-assessment-section">
			<% for (Assessment assessment : teacher.getAssessments()) { %>
				<li>
					<form action="${pageContext.request.contextPath}/panel/teacher/grades" method="get">
						<label><%= assessment.getName() %></label>
						<input type="hidden" name="assessmentId" value='<%= assessment.getId() %>'>
						<button type="submit">Noter/modifier les notes</button>
					</form>
				</li>
			<% } %>
		</ul>
	<hr>
	<h3>Ajouter une évaluation</h3>
	<form action="${pageContext.request.contextPath}/panel/teacher/assessments" method="post" class="add-assessment-form">
		<div class="form-group">
			<label for="selectedCourse">Cours à évaluer: </label>
			<select name="selectedCourse" id="selectedCourse" required>
				<c:forEach var="course" items="${teacher.courses}">
					<option value='${course.id}'>${course.name}</option>
				</c:forEach>
			</select>
		</div>
		<br>
		<div class="form-group">
			<label for="name">Nom de l'évaluation: </label>
			<input type="text" id="name" name="name" required>
		</div>
		<br>
		<div class="form-group">
			<label for="maxGrade">Note maximale: </label>
			<input type="number" min="1" id="maxGrade" name="maxGrade" required>
		</div>
		<br>
		<div class="form-group">
			<label for="date">Date de l'évaluation :</label>
			<input type="date" id="date" name="date" required /><br/>
		</div>
		<button type="submit">Ajouter</button>
	</form>
</div>