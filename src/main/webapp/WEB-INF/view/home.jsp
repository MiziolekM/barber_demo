<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*"%>
<%@ page import="javax.servlet.*,java.text.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
	<title>Barber</title>
	<!-- Reference Bootstrap files -->
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
	<header class="header page-header">Strona domowa</header>
	<div class="user-info">
		<strong>Użytkownik</strong>:
		<security:authentication property="principal.username" />
		<span class="user-info__roles label label-default">
			<security:authentication property="principal.authorities" /></span>

		<!-- dodaj logowanie button -->
		<form:form action="${pageContext.request.contextPath}/logout" class="logout-form" method="POST">

			<input type="submit" class="btn btn-sm btn-link" value="Wyloguj się">

		</form:form>
	</div>
	<h1 class="custom-page-header">
		Witamy na stonie domowej! - Zapisz się już teraz
	</h1>

	<!-- <hr>
	<p>
		<strong>Użytkownik</strong>:
		<security:authentication property="principal.username" />
		<br> <br> Role:
		<security:authentication property="principal.authorities" />
	</p> -->
	<div id="container" class="col-md-6 col-md-offset-3">
		<form:form action="saveOrder" class="form-horizontal" modelAttribute="dtoOrder" method="POST">

			<!-- potrzebujemy powiazac dane z customerem  id-->
			<form:hidden path="idOrder" />
			<form:hidden path="price" />


			<!-- Place for messages: error, alert etc ... -->
			<div class="form-group">
				<div class="col-xs-15">
					<div>

						<!-- Sprawdzanie błędów rejestracji
						<c:if test="${saveOrderError != null}">

							<div class="error">${saveOrderError}</div>

						</c:if>-->

					</div>
				</div>
			</div>
			<!-- <table>
				<tbody>
					<tr>
						<td> -->
			<security:authentication var="user" property="principal" />
			<security:authorize access="hasRole('ROLE_CUSTOMER') and isAuthenticated()">
				<form:hidden path="userName" value="${user.username}" />
			</security:authorize>
			<!-- </tr>

					<tr>
						<td> -->
			<div class="form-group">
				<label class="col-sm-4 control-label">Kiedy:</label>
				<!-- </td>
						<td> -->
				<form:errors path="date" cssClass="error" />
				<div class="col-sm-8 right-column">
					<form:select path="date" class="form-control">
						<c:forEach items="${prepDates}" var="d">
							<option value="${d}">
								<fmt:formatDate value="${d}" pattern="MM-dd HH:mm" />
							</option>
						</c:forEach> -->
					</form:select>
				</div>
			</div>
			<!-- </td>

					</tr>
					<tr>
						<td> -->
			<div class="form-group">
				<label class="col-sm-4 control-label">Jaka usługa:</label>
				<!-- </td>
						<td> -->
				<form:errors path="service" cssClass="error" />
				<div class="col-sm-8 right-column">
					<form:select path="service" class="form-control">
						<option value="Strzyżenie głowy">Strzyżenie głowy</option>
						<option value="Strzyżenie brody">Strzyżenie brody</option>
						<option value="Komplet">Komplet</option>
						<option value="Royal Special">Royal Special</option>
					</form:select>
				</div>
			</div>
			<!-- </td>
					</tr>

					<tr>
						<td><label></label></td>
						<td> -->

			<div class="text-center">
				<input type="submit" value="Zapisz" class="save" />
			</div>
			<!-- </td>
					</tr>

				</tbody>
			</table> -->

		</form:form>
	</div>
	<div class="block-links">
		<security:authorize access="hasRole('MODERATOR')">
			<a href="${pageContext.request.contextPath}/orders/list" class="block-link">Panel
				Moderatora</a>
		</security:authorize>

		<security:authorize access="hasRole('ADMIN')">
			<a href="${pageContext.request.contextPath}/crm/list" class="block-link">Panel
				Administratora - CRM</a>
		</security:authorize>
	</div>

</body>

</html>