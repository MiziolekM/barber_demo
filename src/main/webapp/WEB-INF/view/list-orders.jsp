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

	<head>
		<title>Zamówienia</title>

		<!-- referencja odwołanie do arkusa style -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link type="text/css" rel="stylesheet" href="/global.css" />
	</head>

<body>

	<div class="main-container orders-list-section">
		<header class="header">Zarządzanie zamówieniami</header>

		<form:form class="form-inline search-form" action="search" method="POST">
			<div class="form-group">
				<label class="control-label" for="theSearchName">Szukaj zamówienia:</label>
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
						<th>Status</th>
						<th>Rodzaj usługi</th>
						<th>Cena</th>
						<th>Data</th>
						<th>Akcja</th>
					</tr>


					<!-- petla do wypisywania userow -->
					<c:forEach var="tempOrder" items="${orders}">

						<!-- właściwie nie wiem dlaczego id_user a nie idUser, możliwe że dlatego iż jest to własnie id PK -->

						<!-- konsturowanie "update" link z uzytkownikiem_id -->
						<c:url var="updateLink" value="/orders/showFormForUpdate">
							<c:param name="idOrder" value="${tempOrder.idOrder}" />
						</c:url>

						<!-- konsturowanie "delete" link z uzytkownikiem_id -->
						<c:url var="deleteLink" value="/orders/delete">
							<c:param name="idOrder" value="${tempOrder.idOrder}" />
						</c:url>

						<tr>
							<td>${tempOrder.user.userName}</td>
							<td>${tempOrder.status}</td>
							<td>${tempOrder.service}</td>
							<td>${tempOrder.price}</td>
							<td>
								<fmt:formatDate value="${tempOrder.date}" pattern="MM-dd HH:mm" />
							</td>

							<td>
								<!-- wyswietl update link --> <a href="${updateLink}">Zaktualizuj</a>
								|
								<!-- wyswietl delete link i powiadomienie czy na pewno --> <a href="${deleteLink}" onclick="if (!(confirm ('Jesteś pewien, że chcesz usunąć tego użtkownika?'))) return false">Usuń</a>
							</td>
						</tr>

					</c:forEach>

				</table>

			</div>
			<p>
				<a class="simple-link" href="${pageContext.request.contextPath}/">Wróć do strony
					domowej</a>
			</p>

		</div>
	</div>
</body>

</html>