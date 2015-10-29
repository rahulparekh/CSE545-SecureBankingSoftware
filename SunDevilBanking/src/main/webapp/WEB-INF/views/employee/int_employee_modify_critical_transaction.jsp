<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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

    <title>SBS Group 11 Manager - Modify Critical Transactions</title>

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
          
        </div> <!-- sidebar -->

        <div class="col-sm-9 col-md-10 main">

          <div class="page-header">
            <h1>SBS - Welcome Manager </h1>
          </div>

          <h2>Modify Critical Transactions:</h2>
		  <br>
        <form:form method="POST"  modelAttribute="modificationTransaction" action="modificiation-critical-transction-approve">
          <div id="fund-transfer">
            <div class="modal-body">
              <p>
                    <label>Sender Account No.:           ${modificationTransaction.senderAccNumber}</label>
                    <form:input type="hidden" class="form-control" path="senderAccNumber" maxlength='17' placeholder="ex: 123456789"/>
                  </p>                
              <hr>
             
              <hr>
              <div>
                <p>
                    <label>Receiver Account No.:     ${modificationTransaction.recieverAccNumber} </label>
                    <form:input type="hidden" class="form-control" path = "recieverAccNumber" maxlength='17' placeholder="ex: 123456789"/>
                  </p>   
                </div>

              </div>
              <hr>
              <p>
                <label>Amount to Transfer:</label>
                <form:input type="text" path="amount" class="form-control" placeholder="ex: 0.00"/>
              </p>
            </div>
            <div class="modal-footer">  
			<tr>
			  <input type="hidden" name="modifytransactionID"  value="${modificationTransaction.transactionID}"></input>
              <td><button type="submit" class="btn btn-success">Approve</button></td>
              </form:form>
              <form:form method="GET" action="internalemployee-pending-critical-transaction"> 
			  <td><button type="submit" class="btn btn-primary">Back</button></td>
			  </form:form>
			  </tr>
            </div>
			
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
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/additional-methods.js"></script>
    <script src="${pageContext.servletContext.contextPath}/static/js/jsKeyboard.js"></script>
    <script src="${pageContext.servletContext.contextPath}/static/js/common.js"></script>
  </body>
</html>
