<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

    <title>SBS System Admin Settings - Group 11</title>

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
             <li><a href="${pageContext.servletContext.contextPath}/admin/sysadmin-home">Home</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/admin/manage-employee">Add Employees</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/admin/systemLog-sys-admin">System Log</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/admin/requests-pending">Pending Requests</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/admin/sysadmin-setting">Settings</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/admin/sys-admin-PII">Access PII</a></li>
            <li><a href="${pageContext.servletContext.contextPath}/logout">Logout</a></li>
          </ul>
          
        </div> <!-- sidebar -->

        <div class="col-sm-9 col-md-10 main">

          <div class="page-header">
            <h1>SBS - Welcome System Admin </h1>
          </div>

          <h2>Settings:</h2>
		  <br>

          <div id="fund-transfer">
            <div class="modal-body">
            <form:form method="POST" modelAttribute="user" action="sysadmin-setting_success">
            	<c:if test="${!empty successMsg}">
					<div class="alert alert-success">						
						${fn:escapeXml(successMsg)}
					</div>
				</c:if>
				<c:if test="${!empty failureMsg}">
					<div class="alert alert-danger">						
						${fn:escapeXml(failureMsg)}
						<form:errors path="*" element="div"/>
					</div>
				</c:if>
              <form:input type="hidden" path="customerID" id="customerID" value="${sysadmin.customerID}"/>
              <p>
					<label>First Name:</label>
					<form:input path="firstName" id="firstName" value="${sysadmin.firstName}" />
			  </p>
			  <p>
					<label>Last Name:</label>
					<form:input path="lastName" id="lastName" value="${sysadmin.lastName}" />
			  </p>
			   <p>
					<label>Address:</label>
					<form:input path="addressLine1" id="addressLine1" type="text" 
						Class="form-control" placeholder="ex: 1009 E University Dr" value="${sysadmin.addressLine1}" />
			  </p>
			
			   <p>
					<label>Email:</label>
					<label> ${email} </label>
					
			  </p>
				
			   <p>
					<label>Phone No:</label>
					<form:input path="phone"  id="phone" type="text"
						class="form-control" placeholder="ex:986-712-345" value="${sysadmin.phone}"/>
			  </p>
				
			   <p>
					<label>Zip Code:</label>
					<form:input path="zipCode" id="zipCode" type="text"
						class="form-control" placeholder="ex:85281" value="${sysadmin.zipCode}"/>
			   </p>
			
			   <p>
					<label>State:</label>
					<form:input path="state" id="state" type="text"
						class="form-control" placeholder="AZ" value="${sysadmin.state}" />
			   </p>	
				
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Submit</button>
				</div>

                          
            </form:form>
		
            </div><!-- /. -->
          
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
