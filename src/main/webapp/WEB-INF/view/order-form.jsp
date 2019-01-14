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
	<title>Zaktualizuj</title>

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
	<div class="main-container orders-form-section">
		<header class="header">Zmień zamówienie</header>
		<h1 class="custom-page-header">
			Zaktualizuj Zamówienie
		</h1>
		<div id="container">

			<form:form action="updateOrder" class="form-horizontal" modelAttribute="dtoOrder" method="POST">

				<!-- potrzebujemy powiazac dane z customerem  id-->
				<form:hidden path="price" />
				<form:hidden path="userName" />
				<form:hidden path="idOrder" />

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
				<!-- <table>
					<tbody>

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
							</c:forEach>
						</form:select>
					</div>
				</div>
				<!-- </td>

						</tr>
						<tr>
							<td> -->
				<div class="form-group">
					<label class="col-sm-4 control-label">Usługa:</label>
					<!-- </td>
							<td> -->
					<form:errors path="service" cssClass="error" />
					<div class="col-sm-8 right-column">
						<form:select path="service" class="form-control">
							<c:forEach items="${prepService}" var="ser">
								<option value="${ser}">${ser}</option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<!-- </td>
						</tr>
						<tr>
							<td> -->
				<div class="form-group">
					<label class="col-sm-4 control-label">Status:</label>
					<!-- </td>
							<td> -->
					<form:errors path="status" cssClass="error" />
					<div class="col-sm-8 right-column">
						<form:input path="status" class="form-control" />
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
				<!--	</td>
				 </tr>

					</tbody>
				</table> -->

			</form:form>

			<div style=""></div>

			<p>
				<a class="simple-link" href="${pageContext.request.contextPath}/orders/list">Wróć do
					listy zamówień</a>
			</p>

		</div>
	</div>
</body>

</html>