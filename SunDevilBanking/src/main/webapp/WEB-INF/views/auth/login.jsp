<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<t:auth>

	<form class="form-signin" method="post"
		action="${pageContext.servletContext.contextPath}/login">
		<div>
			<c:if test="${!empty successMsg}">
				<div class="alert alert-success">${fn:escapeXml(successMsg)}</div>
			</c:if>

			<c:if test="${!empty error}">
				<div class="alert alert-danger">${fn:escapeXml(error)}</div>
			</c:if>

			<c:if test="${!empty failureMsg}">
				<div class="alert alert-danger">${fn:escapeXml(failureMsg)}</div>
			</c:if>

			<h3>Welcome to Sun Devil Banking - Group 11</h3>
			<h2 class="form-signin-heading">Please Login</h2>

			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
				<div class="form-errors">
					Your login attempt was not successful due to:
					<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					.
				</div>
			</c:if>


			<label for="inputEmail" class="sr-only">Email address</label> <input
				name="email" type="text" id="inputEmail" class="form-control"
				placeholder="Email address" required="" autofocus=""
				autocomplete="off"> <label for="inputPassword"
				class="sr-only">Password</label> <input name="password"
				type="password" id="inputPassword" class="form-control"
				placeholder="Password" required="" autocomplete="off">
			<!-- <div class="g-recaptcha"
			data-sitekey="6LcQrwwTAAAAAP1rFCMhODCuHWbbkgC9mJ2Qm6gz"></div> -->
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>

			<p class="center">
				<a href="forgotpass">Forgot Password?</a>
			</p>
		</div>
	</form>

	<div id="virtualKeyboard"></div>

</t:auth>