<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<t:page>

	<div class="page-header">
		<h1>View Payment Requests</h1>
	</div>

	<div id="payment-requests">
		<c:if test="${!empty successMsg}">
			<div class="alert alert-success">${fn:escapeXml(successMsg)}</div>
		</c:if>
		<c:if test="${!empty failureMsg}">
			<div class="alert alert-danger">${fn:escapeXml(failureMsg)}</div>
		</c:if>
	
		<table class="table table-bordered">
    	<thead>
        	<tr>
        		<th>Date</th>
            	<th width="35%">Payment Request By:</th>
            	<th>Amount</th>
            	<th>Type</th>
            	<th>Your Account No:</th>
            	<th width="30%">Action (Please enter otp sent in the email to approve the payment)</th>
        	</tr>
    	</thead>
        <tbody>
        	<c:forEach items="${paymentrequests}" var="payment">
        	<tr>
        		<td><joda:format var="createdAt"
							value="${payment.createdAt}" pattern="dd MMM, yyyy hh:mm:ss"
							style="F-" dateTimeZone="America/Phoenix" />
					${createdAt}
				</td>
        		<td>Name: ${fn:escapeXml(payment.merchantName)} <br> Account No: ${fn:escapeXml(payment.merchantAccNumber)}</td>
        		<td>$${fn:escapeXml(payment.amount)}</td>
        		<td>${fn:escapeXml(payment.type)}</td>
        		<td>${fn:escapeXml(payment.customerAccNumber)}</td>
        		<td>
        			<c:choose>
        				<c:when test="${payment.userAccepted == 1 and payment.merchantAccepted == 1 }">
        					Payment accepted.
        				</c:when>
        				<c:when test="${payment.OTPExpiry le currentTime}">
        					OTP has expired
        				</c:when>
        				<c:otherwise>
        				<form:form action="payment-requests" method="POST" modelAttribute="transaction">
        					<input type="text" name="otp" value="" class="form-control" placeholder="OTP (required to approve)">
		        			<br><button type="submit" name="submit" value="accept" class="btn btn-success">Accept</button>		        			
		        			<input type="hidden" name="paymentrequest" value="${fn:escapeXml(payment.id)}">
		        			<button type="submit" name="submit" value="decline" class="btn btn-danger">Decline</button>		        				        				
        				</form:form>
        				</c:otherwise>
        			</c:choose>
        		</td>
        	</tr>
        	</c:forEach>
        </tbody>
        </table>
		
	</div>
</t:page>