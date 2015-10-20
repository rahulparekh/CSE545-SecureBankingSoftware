<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:page>

	<div class="page-header">
		<h1>SBS HOME - Welcome ${user}</h1>
	</div>
	
	<h2>Settings:</h2>
		  <br>

	<div id="Manage_Employees">
		<form:form method="POST" modelAttribute="user" action="/manage-employees">

			<div class="modal-body">
				<p>
					<form:label path="firstName">First Name:</form:label>
					<form:input path="firstName" id="firstName" />
				</p>
				<p>
					<label>Address:</label>
					<form:input path="addressLine1" id="addressLine1" type="text"
						Class="form-control" placeholder="ex: 1009 E University Dr" />
				</p>
				<p>
					<label>Password:</label>
					<form:input path="Password" id="Password" type="password"
						class="form-control" placeholder="ex: user123" />
				</p>
				<p>
					<label>Email:</label>
					<form:input path="email" id="email" type="text"
						class="form-control" placeholder="ex:john@example.com" />
				</p>

				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Submit</button>
				</div>


				<div id="virtualKeyboard"></div>
			</div>
		</form:form>
</t:page>