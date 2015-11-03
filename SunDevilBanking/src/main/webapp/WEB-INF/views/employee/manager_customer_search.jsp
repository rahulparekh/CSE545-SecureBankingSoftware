<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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

    <title>SBS Group 11 Manager Home</title>

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
      <li><a href="${pageContext.servletContext.contextPath}/manager/password-manager-change">Change Password</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/logout">Logout</a></li>
          </ul>
        </div> <!-- sidebar -->

        <div class="col-sm-9 col-md-10 main">

          <div class="page-header">
            <h1>SBS - Welcome Manager</h1>
          </div>

          <h2>Search By:</h2>
		  <br>

          <div id="sysadmin">
          <form:form method="POST" modelAttribute="empSearch">
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
              <label>Customer ID:</label>
              <form:input  path = "employeeID" type="text" class="form-control" placeholder="ex: 123456789" required="required" maxlength="11" minlength="11"></form:input>
            </p>
			<div class="modal-footer" >                
              <button type="submit" class="btn btn-success">Search</button>
            </div>
		   </form:form>
          </div><!-- /payment -->
          
        </div> <!-- /main -->

      </div> <!-- /row -->

      <div id="virtualKeyboard">        
      </div>

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
