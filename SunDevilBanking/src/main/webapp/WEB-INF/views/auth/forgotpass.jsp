<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:auth>    
   
  	<form:form class="form-signin" method = "POST" modelAttribute="emailSearch" action="${pageContext.servletContext.contextPath}/securityquestions">
	    <div>
	    <c:if test="${!empty failureMsg}">
					<div class="alert alert-danger">						
						${fn:escapeXml(failureMsg)}
					</div>
		</c:if>
	    
	    <h2 class="form-signin-heading">Forgot Password</h2>
	    <label for="inputEmail" class="sr-only">Email address</label>
	     <form:input  path = "email" class="form-control" placeholder="Email address" required="Required" autofocus="" autocomplete="off" type="email" id="email" ></form:input>
	  	
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	    <button class="btn btn-lg btn-primary btn-block" type="submit" >Forgot Password</button>
	
	    <p class="center"><a href="./login">Back to Login</a></p>
	    </div>
	</form:form>
	
	<div id="virtualKeyboard"></div>
    
</t:auth>