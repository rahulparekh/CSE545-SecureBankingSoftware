<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${pageContext.servletContext.contextPath}/static/favicon.ico">

    <title>SBS Security Questions - Group 11</title>

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
      
        
	<h2>Change Password</h2>
          <form:form class="settings-form" method = "POST" modelAttribute="changepassword" action="${pageContext.servletContext.contextPath}/passwordSuccessful">
            <div>
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
          
            
            <div class="form-group">
              <div class="row">
              	
              	  <div class="col-xs-12 col-sm-4">
                      <label class="control-label">Email Addreess</label>
                       <form:input  path = "email"  class="form-control" placeholder="Email address" required="required" autofocus="" autocomplete="off" type="email" id="email" ></form:input>
	 
                  </div>
           
                  <div class="col-xs-12 col-sm-4">
                      <label class="control-label">New Password*</label>
                      <form:input path="Newpassword" id="password" type="password" class="form-control" name="fname" />
                  </div>
                  <div class="col-xs-12 col-sm-4">
                      <label class="control-label">Confirm Password*</label>
                      <form:input path="Confirmpassword" id="password" type="password" class="form-control" name="fname" />
                  </div>
              </div>            
            </div>

            <button type="submit" class="btn btn-success">Submit</button>
			</div>
          </form:form>

         
          
        </div> <!-- /main -->

      </div> <!-- /row -->

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









