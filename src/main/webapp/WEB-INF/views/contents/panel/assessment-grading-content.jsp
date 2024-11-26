<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>


<jsp:useBean id="assessment" scope="request" type="fr.cyu.coffeeclasses.vanilla.entity.element.Assessment"/>
<jsp:useBean id="studentGrades" scope="request" type="java.util.Map<fr.cyu.coffeeclasses.vanilla.entity.user.Student, fr.cyu.coffeeclasses.vanilla.entity.element.Grade>"/>
<%@ page import="java.util.Set" %>

<div class="assessment-grading-management">
	<h2>Attribution des notes</h2>
	<h4 id="assessment-name">${assessment.name}</h4>
	<p>Cours : ${assessment.course.name}</p>
	<p>Note maximale : ${assessment.maximum}/ ${assessment.maximum}</p>
	<form action="${pageContext.request.contextPath}/panel/teacher/grades" method="post">
		<input type="hidden" name="assessmentId" value='${assessment.id}'>
		<table id="gradingTable">
			<tr>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Note</th>
			</tr>
			<c:if test="${empty assessment.students}">
				<tr>
					<td colspan="3">Aucun étudiant n'est inscrit à ce cours</td>
				</tr>
			</c:if>
			<c:forEach var="student" items="${assessment.students}">
				<tr>
					<td>${student.firstName}</td>
					<td>${student.lastName}</td>
					<c:set var="grade" value="${studentGrades[student]}" />
					<c:choose>
						<c:when test="${not empty grade}">
							<!-- Already graded student -->
							<td>
								<input
										type="number"
										min="0"
										max="${assessment.maximum}"
										step="0.01"
										name="${student.id}"
										value="${grade.value}"
										required
								/>
							</td>
						</c:when>
						<c:otherwise>
							<!-- Still not graded -->
							<td>
								<input
										type="number"
										min="0"
										max="${assessment.maximum}"
										name="${student.id}"
										required
								/>
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${!empty assessment.students}">
			<button type="submit" id="gradingButton">Enregistrer les notes</button>
		</c:if>
	</form>
</div>