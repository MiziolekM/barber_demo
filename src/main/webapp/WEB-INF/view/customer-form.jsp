<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
	<title>Zapisz Uzytkownika</title>

	<!-- referencja odwoÅanie do arkusa style -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link type="text/css" rel="stylesheet" href="/global.css" />

	<style>
		.error {
	color: red
}
</style>

</head>

<body>
	<div class="main-container customers-form-section">
		<header class="header">CRM - Customer Relationship Manager</header>
		<h1 class="custom-page-header">
			Zaktualizuj Użytkownika
		</h1>
		<div id="container">

			<form:form action="updateUser" modelAttribute="crmUser" method="POST">

				<!-- potrzebujemy powiazac dane z customerem  id-->
				<form:hidden path="idUser" />

				<!-- Place for messages: error, alert etc ... -->
				<div class="form-group">
					<div class="col-xs-15">
						<div>

							<!-- Sprawdzanie błędów rejestracji-->
							<c:if test="${updateError != null}">

								<div class="error">${updateError}</div>

							</c:if>

						</div>
					</div>
				</div>
				<table>
					<tbody>

						<tr>
							<td><label class="control-label">Nazwa użytkownika:</label></td>
							<td>
								<form:errors path="userName" cssClass="error" />
								<form:input path="userName" class="form-control" />
							</td>
						</tr>

						<tr>
							<td><label class="control-label">Imię:</label></td>
							<td>
								<form:errors path="firstName" cssClass="error" />
								<form:input path="firstName" class="form-control" />
							</td>
						</tr>

						<tr>

							<td>
								<form:hidden path="password" />
							</td>
						</tr>

						<tr>

							<td>
								<form:hidden path="matchingPassword" />
							</td>
						</tr>

						<tr>
							<td><label class="control-label">Nazwisko:</label></td>
							<td>
								<form:errors path="lastName" cssClass="error" />
								<form:input path="lastName" class="form-control" />
							</td>
						</tr>

						<tr>
							<td><label class="control-label">Numer telefonu:</label></td>
							<td>
								<form:errors path="phoneNumber" cssClass="error" />
								<form:input path="phoneNumber" type="number" class="form-control" pattern=".{9}" title="Numer telefonu musi zawierać dokładnie 9 cyfr"/>
							</td>
						</tr>

						<tr>
							<td><label class="control-label">Email:</label></td>
							<td>
								<form:errors path="email" cssClass="error" />
								<form:input path="email" class="form-control" />
							</td>
						</tr>

						<form:hidden path="roles" />

						<!-- <tr>
						<td><label>Role:</label></td>
						<td><c:forEach items="${crmUser.roles}" var="r">
								<c:out value="${r.getRole()}" />
								<br>
							</c:forEach></td>
					</tr> -->
						<tr>
							<td colspan="2" style="padding-left: 0">
								<div class="text-center">
									<input type="submit" value="Zapisz" class="save btn btn-primary" />
								</div>
							</td>
						</tr>

					</tbody>
				</table>

			</form:form>

			<div style=""></div>

			<p>
				<a class="simple-link" href="${pageContext.request.contextPath}/crm/list">Wróć do
					głównego panelu</a>
			</p>

		</div>
	</div>

</body>

</html>