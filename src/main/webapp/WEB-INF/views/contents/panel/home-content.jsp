<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" scope="request" type="fr.cyu.coffeeclasses.vanilla.entity.user.User"/>
<% request.setAttribute("userType", user.getClass().getSimpleName()); %>

<div class="home-content">
	<div class="site-description">
		<h1>Bienvenue sur CoffeeClasses</h1>
		<h2>Vous êtes connecté en tant
			<c:choose>
				<c:when test="${userType == 'Administrator'}">
					qu'administrateur.
				</c:when>
				<c:when test="${userType == 'Teacher'}">
					que professeur.
				</c:when>
				<c:when test="${userType == 'Student'}">
					qu'étudiant.
				</c:when>
				<c:otherwise>
					?!?!?
				</c:otherwise>
			</c:choose>
		</h2>
		<p>
			CoffeeClasses est une plateforme conçue pour répondre aux besoins de gestion des étudiants, enseignants et administrateurs. <br>
            Que vous soyez ici pour organiser vos cours, suivre les performances ou gérer les utilisateurs, nous mettons à votre disposition des outils performants pour simplifier votre quotidien.
		</p>
	</div>

	<div class="features-grid">
		<c:if test="${userType == 'Student'}">
			<div class="feature-card">
				<h2>Gestion des cours</h2>
				<p>Consultez la liste de vos cours et leurs détails, comme les professeurs associés ou les ressources disponibles.</p>
			</div>
			<div class="feature-card">
				<h2>Résultats et notes</h2>
				<p>Accédez à vos résultats et consultez vos moyennes par cours.</p>
			</div>
			<div class="feature-card">
				<h2>Performances</h2>
				<p>Générez un rapport sur vos performances et recevez des notifications sur vos notes ou vos inscriptions.</p>
			</div>
		</c:if>

		<c:if test="${userType == 'Teacher'}">
			<div class="feature-card">
				<h2>Gestion des cours</h2>
				<p>Créez, modifiez ou supprimez les cours que vous enseignez et gérez les listes des inscrits.</p>
			</div>
			<div class="feature-card">
				<h2>Évaluations</h2>
				<p>Saisissez les notes de vos étudiants et suivez leurs progressions.</p>
			</div>
			<div class="feature-card">
				<h2>Rapports de résultats</h2>
				<p>Générez et partagez des rapports sur les performances des étudiants de vos cours.</p>
			</div>
		</c:if>

		<c:if test="${userType == 'Administrator'}">
			<div class="feature-card">
				<h2>Gestion des utilisateurs</h2>
				<p>Ajoutez, modifiez ou supprimez les comptes des étudiants, enseignants et administrateurs.</p>
			</div>
			<div class="feature-card">
				<h2>Gestion des cours</h2>
				<p>Assignez les enseignants à des cours et organisez les inscriptions des étudiants.</p>
			</div>
			<div class="feature-card">
				<h2>Statistiques globales</h2>
				<p>Obtenez des résumés détaillés des performances de l’ensemble des utilisateurs.</p>
			</div>
		</c:if>
	</div>
</div>
