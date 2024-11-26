<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="target" scope="request" type="fr.cyu.coffeeclasses.vanilla.entity.user.User"/>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Teacher" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.user.Student" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.element.Course" %>
<%@ page import="java.util.Set" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.element.Enrollment" %>
<%@ page import="fr.cyu.coffeeclasses.vanilla.entity.element.Grade" %>

<div class="box">
	<h1>${target.firstName} ${target.lastName}</h1>

	<h2>Informations personnelles</h2>
	<p><strong>Nom :</strong> ${target.lastName}</p>
	<p><strong>Prénom :</strong> ${target.firstName}</p>
	<p><strong>Adresse e-mail :</strong> ${target.email}</p>
	<p><strong>Date de naissance :</strong> ${target.birthDate}</p>

	<% if (target instanceof Teacher) { Teacher teacher = (Teacher) target; %>
	<h2>Cours</h2>
	<%
		Set<Course> courses = teacher.getCourses();
		if (!courses.isEmpty()) {
	%>
	<ul>
		<% for (Course course : courses) { %>
		<li><%= course.getName() %></li>
		<% } %>
	</ul>
	<% } else { %>
	<p class="empty">Aucun cours assigné.</p>
	<% } %>
	<% } %>

	<!-- Check if user is a Student -->
	<% if (target instanceof Student) { Student student = (Student) target; %>
	<h2>Cours et évaluations</h2>
	<%
		Set<Enrollment> enrollments = student.getEnrollments();
		if (!enrollments.isEmpty()) {
			for (Enrollment enrollment : enrollments) {
	%>
	<div class="course-box">
		<h3><%= enrollment.getCourse().getName() %></h3>
		<!-- Liste des notes -->
		<ul>
			<% if (enrollment.getGrades() != null && !enrollment.getGrades().isEmpty()) { %>
			<% for (Grade grade : enrollment.getGrades()) { %>
			<li><%= grade.getAssessment().getName() %>: <%= grade.getValue() %>/<%= grade.getAssessment().getMaximum() %></li>
			<% } %>
			<% } else { %>
			<li class="empty">Aucune note disponible.</li>
			<% } %>
		</ul>
	</div>
	<% } %>
	<% } else { %>
	<p class="empty">Aucun cours assigné.</p>
	<% } %>
	<% } %>
</div>
