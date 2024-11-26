<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="target" scope="request" type="fr.cyu.coffeeclasses.vanilla.entity.user.Student"/>

<div class="box">
	<h1>${target.firstName} ${target.lastName}</h1>

	<h2>Cours et évaluations</h2>

	<!-- Vérification si l'utilisateur a des cours assignés -->
	<c:choose>
		<c:when test="${not empty target.enrollments}">
			<c:forEach var="enrollment" items="${target.enrollments}">
				<div class="course-box">
					<h3>${enrollment.course.name}</h3>
					<p><strong>Enseignant :</strong> ${enrollment.course.teacher.firstName} ${enrollment.course.teacher.lastName}</p>

					<c:set var="totalWeightedGrades" value="0.0" />
					<c:set var="totalMaximumWeights" value="0.0" />

					<!-- A -->
					<ul>
						<c:choose>
							<c:when test="${not empty enrollment.grades}">
								<c:forEach var="grade" items="${enrollment.grades}">
									<li>
										${grade.assessment.name} : ${grade.value}/${grade.assessment.maximum}
									</li>
									<c:set var="totalWeightedGrades" value="${totalWeightedGrades + (grade.value / grade.assessment.maximum * 20)}" />
									<c:set var="totalMaximumWeights" value="${totalMaximumWeights + 20}" />
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li class="empty">Aucune note disponible.</li>
							</c:otherwise>
						</c:choose>
					</ul>

					<c:choose>
						<c:when test="${totalMaximumWeights > 0}">
							<p><strong>Moyenne :</strong>
								<fmt:formatNumber value="${totalWeightedGrades / (totalMaximumWeights / 20)}" type="number" maxFractionDigits="2" /> / 20
							</p>
						</c:when>
						<c:otherwise>
							<p><strong>Moyenne :</strong> Pas de notes disponibles</p>
						</c:otherwise>
					</c:choose>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<p class="empty">Aucun cours assigné.</p>
		</c:otherwise>
	</c:choose>
</div>
