<%@ tag
	description="Page template for all pages when user has logged in"
	language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon"
	href="${pageContext.servletContext.contextPath}/static/favicon.ico">

<title>${title} | Sun Devil Bank - Group 11</title>

<!-- Bootstrap core CSS -->
<link
	href="${pageContext.servletContext.contextPath}/static/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Bootstrap theme -->
<link
	href="${pageContext.servletContext.contextPath}/static/css/bootstrap-theme.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link
	href="${pageContext.servletContext.contextPath}/static/css/custom.css"
	rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body role="document">

	<div class="container container-main" role="main">

		<div class="row">

			<c:if test="${!fn:endsWith(pageContext.request.requestURI, 'statement.jsp')}">
			<div class="col-sm-3 col-md-2 sidebar">

				<ul class="nav nav-sidebar">
					<li
						class="${fn:endsWith(pageContext.request.requestURI, 'home.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/home/">Home</a></li>
					<li
						class="${fn:endsWith(pageContext.request.requestURI, 'creditdebit.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/home/credit-debit">Credit/Debit</a></li>
					<li class="${fn:endsWith(pageContext.request.requestURI, 'fundtransfer.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/home/fund-transfer">Fund Transfer</a></li>					
					<sec:authorize access="hasRole('ROLE_CUSTOMER')">						
					<li class="${fn:endsWith(pageContext.request.requestURI, 'customerpayments.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/home/payments">Payments</a></li>
					</sec:authorize>					
					<sec:authorize access="hasRole('ROLE_CUSTOMER')">						
					<li class="${fn:endsWith(pageContext.request.requestURI, 'customerpaymentrequests.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/home/payment-requests">Payment Requests</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_MERCHANT')">
					<li class="${fn:endsWith(pageContext.request.requestURI, 'merchantpayments.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/home/merchant-payments">Merchant Payments</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_MERCHANT')">
					<li class="${fn:endsWith(pageContext.request.requestURI, 'merchantpaymentrequests.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/home/merchant-payment-requests">Merchant Payment Requests</a></li>
					</sec:authorize>
					<li class="${fn:endsWith(pageContext.request.requestURI, 'statements.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/home/statements">Statements</a></li>
					<li class="${fn:endsWith(pageContext.request.requestURI, 'settings.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/home/customer-setting">Settings</a></li>
						
					<li class="${fn:endsWith(pageContext.request.requestURI, 'customer_change_password.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/home/password-change">Change Password</a></li>
						
					<li class="${fn:endsWith(pageContext.request.requestURI, 'logout.jsp') ? 'active':''}"><a
						href="${pageContext.servletContext.contextPath}/logout">Logout</a></li>
				</ul>

			</div>
			<!-- sidebar -->
			</c:if>

			<div class="${fn:endsWith(pageContext.request.requestURI, 'statement.jsp') ? 'col-sm-12 col-md-12':'col-sm-9 col-md-10'} main">

				<jsp:doBody />

			</div>
			<!-- /main -->

		</div>
		<!-- /row -->

	</div>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.js"></script>
	<script
		src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/additional-methods.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/static/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/static/js/validation.js"></script>
	<script
		src="${pageContext.servletContext.contextPath}/static/js/common.js"></script>
</body>
</html>
