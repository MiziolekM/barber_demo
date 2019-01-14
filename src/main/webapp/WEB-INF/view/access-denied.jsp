<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<title>Brak dostępu</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link type="text/css" rel="stylesheet" href="/global.css" />
</head>

<body>
	<div class="main-container registration-confirmation-section">
		<header class="header">Barber</header>

		<div id="container">

			<div id="content">

				<div class="alert alert-danger" role="alert" style="text-align: center">
					Brak dostępu - Nie posiadasz dostepu do tych zasobów.
				</div>

				<a class="simple-link" href="${pageContext.request.contextPath}/">Wróć do strony domowej</a>
			</div>
		</div>
	</div>


</body>

</html>