<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
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

    <title>SBS Group 11 Manager - Pending Critical Transactions</title>

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
            <li><a href="${pageContext.servletContext.contextPath}/manager/manager-home">Home</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/manager/internalemployee-pending-critical-transaction">Pending and Critical Transactions</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/manager/manage-customer">Add Users</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/manager/manager-customer-search">View-Edit-Delete Users</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/manager/requests-pending-ext">Pending Requests</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/manager/manager-setting">Settings</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/logout">Logout</a></li>
          </ul>         
        </div> <!-- sidebar -->

        <div class="col-sm-9 col-md-10 main">

          <div class="page-header">
            <h1>SBS - Welcome Manager </h1>
          </div>

          <h2>Pending Critical Transactions:</h2>
		  <br>

          <table class="table">
            <thead>
              <tr>
                <th>Sender Account No.</th>
				<th>Receiver Account No.</th>
                <th>Transaction ID</th>              
			    <th>Amount</th>
			    <th>Credit/Debit</th>
              	<th>Date</th>				
              </tr>
            </thead>
            <tbody>  
               <c:if test="${!empty failureMsg}">
					<div class="alert alert-danger">						
						${fn:escapeXml(failureMsg)}
					</div>
				</c:if>
				<c:if test="${!empty sucessMsg}">
					<div class="alert alert-success">						
						${fn:escapeXml(sucessMsg)}
					</div>
				</c:if>
				
        	  <c:forEach var="transaction" items="${pendingCriticalTransaction}" varStatus="loopCounter">
        	   
              <tr>
                <td>${transaction.senderAccNumber}</td>
				<td>${transaction.receiverAccNumber}</td>
				<td>${transaction.transactionID}</td>
				<td>${transaction.amount}</td>
				<td>${transaction.type}</td>
				<joda:format var="createdAt"
							value="${transaction.createdAt}" pattern="dd MMM, yyyy HH:mm:ss"
							style="F-" dateTimeZone="America/Phoenix" />
				<td>${createdAt}</td>
				<form:form method="POST" action="critical-approve" >
				<input type="hidden" name="transactionID" value="${transaction.transactionID}"></input>
				<td><button type="submit" class="btn btn-success" name>Approve</button></td>
				</form:form>
				<form:form method="POST" action="critical-decline">
				<input type="hidden" name="transactionID" value="${transaction.transactionID}"></input>
				<td><button type="submit" class="btn btn-danger">Decline</button></td>
				</form:form>
				<form:form method="POST" action="critical-modify">
				<input type="hidden" name="transactionID" value="${transaction.transactionID}"></input>
				<input type="hidden" name="senderAccNumber" value="${transaction.senderAccNumber}"></input>
				<input type="hidden" name="receiverAccNumber" value="${transaction.receiverAccNumber}"></input>
				<input type="hidden" name="amount" value="${transaction.amount}"></input>
				<td><button type="submit" class="btn btn-primary">Modify</button></td>
				</form:form>
              </tr>
              
             </c:forEach>
             
            </tbody>
          </table>
          
        </div> <!-- /main -->

      </div> <!-- /row -->

      

    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/static/js/bootstrap.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/static/js/common.js"></script>
  </body>
</html>