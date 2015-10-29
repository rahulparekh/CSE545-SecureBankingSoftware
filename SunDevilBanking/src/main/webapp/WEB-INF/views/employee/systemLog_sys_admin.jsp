<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

    <title>SBS System Admin - View System Log - Group 11</title>

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
            <h1>SBS - Welcome SysAdmin</h1>

          <h2>System Log:</h2>
		  <br>
			 <table class="table">
            <thead>
              <tr>
                <th>TimeStamp</th>
				<th>Name</th>
				<th>Type</th>
                <th>Action</th>
                           			
              </tr>
            </thead>
            <tbody>
        	  <c:forEach var="log" items="${systemlogs}" >
              <tr>
                <td>${log.timeStamp}</td>
                <td>${log.firstName}</td>
                <td>${log.userType}</td>
				<td>${log.action}</td>
              </tr>
             </c:forEach>
            </tbody>
          </table>
          
        </div> <!-- /main -->

      </div> <!-- /row -->

      <div id="virtualKeyboard">        
      </div>

    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jsKeyboard.js"></script>
    <script src="js/common.js"></script>
  </body>
</html>
