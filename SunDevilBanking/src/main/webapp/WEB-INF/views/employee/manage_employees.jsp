<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<t:page>

	<div class="page-header">
		<h1>SBS HOME - Welcome System Admin</h1>
	</div>
	
	<h2>Settings:</h2>
		  <br>

	<div id="Manage_Employees">
		<form:form method="POST"   modelAttribute="user" action = "/employee/manage_employees">

			<div class="modal-body">
				<p>
					<label>First Name:</label>
					<form:input path="firstName" id="firstName"  />
				</p>
				<p>
					<label>Last Name:</label>
					<form:input path="lastName" id="lastName" />
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
				
				<p>
					<label>Phone No:</label>
					<form:input path="phone"  id="phone" type="text"
						class="form-control" placeholder="ex:986-712-345" />
				</p>
				
				<p>
					<label>Zip Code:</label>
					<form:input path="zipCode" id="zipCode" type="text"
						class="form-control" placeholder="ex:85281" />
				</p>
			
				<p>
					<label>State:</label>
					<form:input path="state" id="state" type="text"
						class="form-control" placeholder="AZ" />
				</p>	
				
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Submit</button>
				</div>


				<div id="virtualKeyboard"></div>
			</div>
		</form:form>
</t:page>