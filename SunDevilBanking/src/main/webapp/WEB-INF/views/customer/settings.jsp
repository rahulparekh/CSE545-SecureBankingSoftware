<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<t:page>


<div class="page-header">
		<h1>Welcome ${fn:escapeXml(fullname)}</h1>
	</div>
          <h2>Settings:</h2>
		  <br>

          <div id="fund-transfer">
            <div class="modal-body">
            <form:form method="POST" modelAttribute="user" action="customer-setting_success">
              <form:input type="hidden" path="customerID" id="customerID"/>
				<p>
					<label>First Name:</label>
					<form:input path="firstName" id="firstName"  minlength='2' maxlength='35' required='required'/>
				</p>
				<p>
					<label>Last Name:</label>
					<form:input path="lastName" id="lastName" minlength='3' maxlength='70' required='required'/>
				</p>
				<p>
					<label>Address:</label>
					<form:input path="addressLine1" id="addressLine1" type="text" 
						Class="form-control" placeholder="ex: 1009 E University Dr" minlength='5' maxlength='50' required='required' />
				</p>
				<p>
					<label>Password:</label>
					<form:input path="Password" id="Password" type="password"
						class="form-control" placeholder="ex: user123" minlength='6' maxlength='60' required='required'/>
				</p>
				<p>
					<label>Email:</label>
					<form:input path="email" id="email" type="email" required='required'
						class="form-control" placeholder="ex:john@example.com" disabled='true' />
				</p>
				
				<p>
					<label>Phone No:</label>
					<form:input path="phone"  id="phone" type="number" required='required'
						class="form-control" placeholder="ex:986-712-345" />
				</p>
				
				<p>
					<label>Zip Code:</label>
					<form:input path="zipCode" id="zipCode" type="number" required='required'
						class="form-control" placeholder="ex:85281" />
				</p>
			
				<p>
					<label>State:</label>
					<form:input path="state" id="state" type="text" minlength='2' maxlength='2' required='required'
						class="form-control" placeholder="AZ" />
				</p>	
				<p>
					<label>Provide Access to view</label>
					<form:radiobutton path="employeeOverride"  
						 value="1"/>Yes
					<form:radiobutton path="employeeOverride"  
						 value = "0"/>No	
				</p>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success" >Submit</button>
				</div>
                          
            </form:form>
			</div>
			</div>
            
      <div id="virtualKeyboard">        
      </div>

   



  
</t:page>
