<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:auth>    
   
  	<form class="form-signin">
	    <h2 class="form-signin-heading">Forgot Password</h2>
	    <label for="inputEmail" class="sr-only">Email address</label>
	    <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required="" autofocus="" autocomplete="off">
	    <div class="g-recaptcha" data-sitekey="6LcQrwwTAAAAAP1rFCMhODCuHWbbkgC9mJ2Qm6gz"></div>
	    <button class="btn btn-lg btn-primary btn-block" type="submit">Forgot Password</button>
	
	    <p class="center"><a href="./login">Back to Login</a></p>
	</form>
	
	<div id="virtualKeyboard"></div>
    
</t:auth>