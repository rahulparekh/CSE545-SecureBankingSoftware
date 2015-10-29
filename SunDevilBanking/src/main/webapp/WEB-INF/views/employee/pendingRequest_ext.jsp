<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

    <title>SBS Pending Requests Internal - Approval needed from Manager</title>

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
            <h1>SBS - Welcome Manager</h1>
          </div>

          <h2>Pending Requests:</h2>
		  <br>

          <table class="table">
            <thead>
              <tr>
                <th>Name</th>
				<th>Type of Employee</th>
                <th>User ID</th>              
			    <th>Date</th>	
				<th>Issue</th>
					
				
              </tr>
            </thead>
            <tbody>
			<c:forEach var="modification" items="${pendingModifications}" varStatus="loopCounter">
              <tr>
                <td>${modification.firstName}</td>
                <td>${modification.userType}</td>
			    <td>${modification.customerID}</td>
				<td>${modification.updatedAt}</td>
				<td>Personal Details Change</td>
				<form:form method="POST"  action="requests-view-ext">
				<input type="hidden" name="modificationrequestid" value="${modification.requestid}"></input>
                <td><button type="submit" class="btn btn-success">View Details</button></td>
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
    <script src="${pageContext.servletContext.contextPath}/static/js/jsKeyboard.js"></script>
    <script src="${pageContext.servletContext.contextPath}/static/js/common.js"></script>
  </body>
</html>
