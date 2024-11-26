<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="java.security.InvalidParameterException" %>

<jsp:useBean id="user" scope="request" type="fr.cyu.coffeeclasses.vanilla.entity.user.User"/>
<%
	request.setAttribute("userType", user.getClass().getSimpleName());
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="icon" href="${pageContext.request.contextPath}/svg/logo.svg">
	<title>CoffeeClasses - <%= request.getParameter("title") %></title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/panel.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/<%= request.getParameter("contentPage") %>.css">
</head>
<body>
	<header class="panel-header">
		<!-- Bannière -->
		<div class="project-title">
			<img src="${pageContext.request.contextPath}/svg/logo.svg" alt="Logo" class="project-logo">
			<i class="project-name">CoffeeClasses</i>
		</div>

		<!-- Liens de navigation -->
		<div class="panel-links">
			<a href="${pageContext.request.contextPath}/panel/home">Accueil</a>
			<c:choose>
				<c:when test="${userType == 'Administrator'}">
					<a href="${pageContext.request.contextPath}/panel/admin/users">Utilisateurs</a>
					<a href="${pageContext.request.contextPath}/panel/admin/courses">Cours</a>
				</c:when>
				<c:when test="${userType == 'Teacher'}">
					<a href="${pageContext.request.contextPath}/panel/teacher/assessments">Mes évaluations</a>
				</c:when>
				<c:when test="${userType == 'Student'}">
					<a href="${pageContext.request.contextPath}/panel/student/details">Mes cours et résultats</a>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			<a href="${pageContext.request.contextPath}/panel/users/show?id=<%= user.getId() %>">Profil</a>
		</div>

		<!-- Nom d'utilisateur et déconnexion -->
		<div class="user-actions">
			<span class="username">${user.firstName} ${user.lastName}</span>
			<a href="${pageContext.request.contextPath}/logout" class="logout-form">
				<button type="submit" class="logout-button">Déconnexion</button>
			</a>
		</div>
	</header>

	<!-- Display error message if it exists -->
	<c:if test="${not empty errorMessage}">
		<div class="error-message">
				${errorMessage}
		</div>
	</c:if>
	<!-- Display success message if it exists -->
	<c:if test="${not empty successMessage}">
		<div class="success-message">
				${successMessage}
		</div>
	</c:if>

	<main>
		<%
			String contentPage = request.getParameter("contentPage");
			if (contentPage != null) {
				out.flush();
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/contents/panel/" + contentPage + "-content.jsp");
				dispatcher.include(request, response);
			} else {
				throw new InvalidParameterException("Panel layout requested with no content.");
			}
		%>
	</main>
</body>
