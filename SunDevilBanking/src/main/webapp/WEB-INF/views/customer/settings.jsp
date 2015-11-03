<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<t:page>


		<div class="page-header">
			<h1>Your Settings:</h1>
		</div>

        <div id="customer-settings">

		<c:if test="${!empty successMsg}">
			<div class="alert alert-success">${fn:escapeXml(successMsg)}</div>
		</c:if>
		<c:if test="${!empty failureMsg}">
			<div class="alert alert-danger">
				${fn:escapeXml(failureMsg)}
				${fieldErrors}
			</div>
		</c:if>

		<div class="modal-body">
            <form:form method="POST" modelAttribute="user" action="customer-setting">
              	<form:input type="hidden" path="customerID" id="customerID" value="${customer.customerID}"/>
				<p>
					<label>First Name:</label>
					<form:input class="form-control" path="firstName" id="firstName"  minlength='2' maxlength='35' required='required' value="${customer.firstName}"/>
				</p>
				<p>
					<label>Last Name:</label>
					<form:input class="form-control" path="lastName" id="lastName" minlength='3' maxlength='70' required='required' value="${customer.lastName}"/>
				</p>
				<p>
					<label>Address:</label>
					<form:input path="addressLine1" id="addressLine1" type="text" 
						Class="form-control" placeholder="ex: 1009 E University Dr" minlength='5' maxlength='50' required='required' value="${customer.addressLine1}" />
				</p>
				
				
				<p>
					<label>Phone No:</label>
					<form:input path="phone"  id="phone" type="number" required='required'
						class="form-control" placeholder="ex:986-712-345"  value="${customer.phone}"/>
				</p>
				
				<p>
					<label>Zip Code:</label>
					<form:input path="zipCode" id="zipCode" type="number" required='required'
						class="form-control" placeholder="ex:85281"  value="${customer.zipCode}" />
				</p>
			
				<p>
					<label>State:</label>
					<form:input path="state" id="state" type="text" minlength='2' maxlength='2' required='required'
						class="form-control" placeholder="AZ"  value="${customer.state}"/>
				</p>	
				
				<p>
					<label>Provide Access to Transactions</label>
					<input type="radio" name="employeeOverride"  
						 value="1" ${customer.employeeOverride == 1 ? 'checked' : ''}/>Yes
					<input type="radio" name="employeeOverride"  
						 value = "0" ${customer.employeeOverride == 1 ? '' : 'checked'}/>No	
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
