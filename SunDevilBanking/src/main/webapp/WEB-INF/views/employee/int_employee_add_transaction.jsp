<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${pageContext.servletContext.contextPath}/static/favicon.ico">

    <title>SBS Group 11 Regular Employee - Add Transactions</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.servletContext.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="${pageContext.servletContext.contextPath}/static/css/bootstrap-theme.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.servletContext.contextPath}/static/css/custom.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body role="document">

    <div class="container container-main" role="main">

      <div class="row">
        
        <div class="col-sm-3 col-md-2 sidebar">

          <ul class="nav nav-sidebar">
             <li><a href="${pageContext.servletContext.contextPath}/regular/int-employee-home">Home</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/regular/addTransaction">Add Transaction</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/regular/internalemployee-pendingtransaction">Pending Transactions</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/regular/int-employee-customer-search">View-Edit Users</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/regular/int-employee-setting">Settings</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/logout">Logout</a></li>
          </ul>
          
         </div>

        <div class="col-sm-9 col-md-10 main">

          <div class="page-header">
            <h1>SBS Group 11 - Welcome Regular Employee </h1>
          </div>

          <h2>Add Transaction</h2>

	<form:form id="fund-transfer" action="addTransactionSuccess" method="POST" modelAttribute="transaction">
		<div class="modal-body">
			<c:if test="${!empty successMsg}">
				<div class="alert alert-success">						
					${fn:escapeXml(successMsg)}
				</div>
			</c:if>
			<c:if test="${!empty failureMsg}">
				<div class="alert alert-danger">						
					${fn:escapeXml(failureMsg)}
				</div>
			</c:if>
			<p>
				<label>Debit Account:</label> <select class="form-control"
					name="senderAccNumber" id="select-account">
					<option value="">Select an Account</option>
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${accounts}" var="account">
						<option id="acc${count}" value="${fn:escapeXml(account.number)}">${fn:escapeXml(account.name)}
							(*${fn:escapeXml(fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number)))})</option>

						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</select>
				<form:errors path="senderAccNumber" cssClass="error"
					element="label" />
			</p>
			<hr>
			<div>
				<label>Current Balance:</label>
				<div id="current-balance">
					<p id="please-select-account">
						<i> Please select an account above </i>
					</p>
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${accounts}" var="account">
						<p class="hide" id="acc${count}bal">$${fn:escapeXml(account.balance)}</p>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</div>
			</div>
			<hr>
			<div>
				<label>Transfer Type:</label><br> <span
					style="margin-right: 30px;"> <input type="radio" name="type"
					value="Internal" class="internal-transfer transfer-option-btn">
					Internal
				</span> <span><input type="radio" name="type" value="External"
					class="external-transfer transfer-option-btn"> External</span>
				<div class="type-error">
					<form:errors path="type" cssClass="error" element="label" />
				</div>
			</div>
			<div class="transfer-account-details">
				<div id="internal-transfer" class="hidden">
					<select class="form-control" name="receiverAccNumber"
						id="select-receiver-account">
						<option value="">Select a credit account</option>
						<c:set var="count" value="0" scope="page" />
						<c:forEach items="${accounts}" var="account">
							<option id="acc${count}credit"
								value="${fn:escapeXml(account.number)}">${fn:escapeXml(account.name)}
								(*${fn:escapeXml(fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number)))})</option>

							<c:set var="count" value="${count + 1}" scope="page" />
						</c:forEach>
					</select>
					<form:errors path="receiverAccNumber" cssClass="error"
						element="label" />
				</div>

				<div id="external-transfer" class="hidden">
					<p>
						<label>Beneficiery Account Number:</label> <input type="text"
							class="form-control" name="receiverAccNumberExternal"
							placeholder="eg: 11111111111111111">
					</p>
				</div>
				<hr>
			<p>
				<label>Amount to be Transferred:</label>
				<input name="amount" type="text" class="form-control" placeholder="e.g. 10.50">
				<form:errors path="amount" cssClass="error" element="label"/>
			</p>
			</div>
			<hr>
		
		</div>
		<div class="modal-footer">
			<button type="submit" class="btn btn-success">Transfer Funds</button>
		</div>
	</form:form>
                  </div> <!-- /main -->

      </div> <!-- /row -->
	


    </div> <!-- /container -->



    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/static/js/bootstrap.min.js"></script>
	<script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.js"></script>
	<script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/additional-methods.js"></script>
	<script src="${pageContext.servletContext.contextPath}/static/js/validation.js"></script>
    <script src="${pageContext.servletContext.contextPath}/static/js/common.js"></script>
  </body>
</html>
