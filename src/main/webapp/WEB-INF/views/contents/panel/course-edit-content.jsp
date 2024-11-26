<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="targetCourse" scope="request" type="fr.cyu.coffeeclasses.vanilla.entity.element.Course"/>
<jsp:useBean id="teachers" scope="request" type="java.util.Set<fr.cyu.coffeeclasses.vanilla.entity.user.Teacher>"/>

<div class="edit-course">
	<h1>Modifier un cours</h1>
	<form action="${pageContext.request.contextPath}/panel/admin/courses/edit" method="post">
		<input type="hidden" name="id" value="${targetCourse.id}" />

		<label for="courseName">Nom du cours :</label>
		<input type="text" id="courseName" name="name" value="${targetCourse.name}" required /><br/>

		<label for="teacher">Enseignant :</label>
		<select id="teacher" name="teacher" required>
			<c:forEach var="teacher" items="${teachers}">
				<option value="${teacher.id}" ${teacher.id == targetCourse.teacher.id ? 'selected' : ''}>
						${teacher.firstName} ${teacher.lastName}
				</option>
			</c:forEach>
		</select><br/>

		<button type="submit">Enregistrer</button>
	</form>

	<form action="${pageContext.request.contextPath}/panel/admin/courses/delete" method="get">
		<input type="hidden" name="id" value="${targetCourse.id}" />
		<button type="submit">Supprimer</button>
	</form>
</div>
