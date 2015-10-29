<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
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

    <title>SBS Government - Group 11</title>

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

         
        
            
         
          
        </div> <!-- sidebar -->

        <div class="col-sm-9 col-md-10 main">

          <div class="page-header">
            <h1>SBS- User Details after Modification Request</h1>
          </div>
		  <br>

          <div id="fund-transfer">
          
            <div class="modal-body">
               <p><label>Customer ID:${PIIUser.customerID}</label></p>
              <p><label>FirstName:${PIIUser.firstName}</label></p>
             
              <p><label>LastName:${PIIUser.lastName}</label></p>
              <p>
                  <label>Address1: ${PIIUser.addressLine1}</label>
              <p>
	             
                  
			                    
         
                    <p><label>State: ${PIIUser.state}</label></p>
                    <p><label>ZipCode: ${PIIUser.zipCode}</label></p>
                    
                  <p>
                    <label>Phone No.: ${PIIUser.phone}</label>
                    
                  </p>
				  <p>
                    <label>Email ID.: ${PIIUser.email}</label>
                    
                  </p>
			
           			     
			   
			</div>   
			<table>
			<tr>
            <form:form method="POST" action="decline-govt"> 
            <input type = hidden name="PIImodifiedUserID" value="${PIIUser.customerID}" ></input>
			<td><button type="submit" class="btn btn-danger"">Decline</button></td>
			</form:form>
			<form:form method="POST" action="back-modification-govt"> 
			<td><button type="submit" class="btn btn-primary">Back</button></td>
			</form:form>
                        
            <form:form method="POST"  action="approve-modification-govt">             
               <input type = hidden name="PIImodifiedUserID" value="${PIIUser.customerID}" ></input>
               <td><button type="submit" class="btn btn-success">Approve</button></td>
               </form:form>
            
			</tr>  
			</table>
              
              <br>
			  <br>
              
              </div>
              <div class="transfer-account-details">
                <div id="internal-transfer" class="hidden">
                  <select class="form-control">
                    <option> </option>
                    <option>Customer Account</option>
                    <option>Merchant Account</option>
                  </select>
                </div>

                
              
                           
            </div>
            
          </div><!-- /. -->
          
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

		