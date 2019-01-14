<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>

<html>

<head>
	<title>Lista użytkowników</title>

	<!-- referencja odwołanie do arkusa style -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link type="text/css" rel="stylesheet" href="/global.css" />
</head>

<body>
	<div class="main-container customers-list-section">
		<header class="header">CRM - Customer Relationship Manager</header>
		<!-- <div id = "wrapper">
			<div id = "header">
				<h2>CRM - Customer Relationship Manager</h2>
			</div>
		</div> -->
		<form:form action="search" class="form-inline search-form" method="POST">
			<div class="form-group">
				<label class="control-label" for="theSearchName">Szukaj użytkownika:</label>
				<input type="text" class="form-control" id="theSearchName" name="theSearchName" />
			</div>
			<input type="submit" value="Szukaj" class="add-button btn btn-primary" />
		</form:form>

		<div id="container">

			<div id="content">

				<!-- przycisk: Dodaj Użytkonika -->
				<!--<input type = "button" value="Dodaj Użytkonika"
							onclick="window.location.href='showFormForAdd'; return false;"
							class = "add-button"
							/>-->

				<!-- tabela htmlowa -->

				<table class="table table-striped">
					<tr>
						<th>Nazwa użytkownika</th>
						<th>Imię</th>
						<th>Nazwisko</th>
						<th>Numer Telefonu</th>
						<th>Email</th>
						<th>Akcja</th>
					</tr>


					<!-- petla do wypisywania userow -->
					<c:forEach var="tempUser" items="${users}">

						<!-- właściwie nie wiem dlaczego id_user a nie idUser, możliwe że dlatego iż jest to własnie id PK -->

						<!-- konsturowanie "update" link z uzytkownikiem_id -->
						<c:url var="updateLink" value="/crm/showFormForUpdate">
							<c:param name="idUser" value="${tempUser.idUser}" />
						</c:url>

						<!-- konsturowanie "delete" link z uzytkownikiem_id -->
						<c:url var="deleteLink" value="/crm/delete">
							<c:param name="idUser" value="${tempUser.idUser}" />
						</c:url>

						<tr>
							<td> ${tempUser.userName}</td>
							<td> ${tempUser.firstName}</td>
							<td> ${tempUser.lastName}</td>
							<td> ${tempUser.phoneNumber}</td>
							<td> ${tempUser.email}</td>

							<td>
								<!-- wyswietl update link -->
								<a href="${updateLink}">Zaktualizuj</a>
								|
								<!-- wyswietl delete link i powiadomienie czy na pewno -->
								<a href="${deleteLink}" onclick="if (!(confirm ('Jesteś pewien, że chcesz usunąć tego użtkownika?'))) return false">Usuń</a>
							</td>
						</tr>

					</c:forEach>

				</table>

			</div>
			<p>
				<a class="simple-link" href="${pageContext.request.contextPath}/">Wróć do
					strony domowej</a>
			</p>

		</div>
	</div>
</body>

</html>