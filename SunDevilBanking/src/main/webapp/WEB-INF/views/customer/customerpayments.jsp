<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<t:page>

	<div class="page-header">
		<h1>Make Payment</h1>
	</div>
	
	

	<form:form id="payment" modelAttribute="paymentrequest" method="POST" action="payments" name="payment">

		<c:if test="${!empty successMsg}">
			<div class="alert alert-success">${fn:escapeXml(successMsg)}</div>
		</c:if>
		<c:if test="${!empty failureMsg}">
			<div class="alert alert-danger">${fn:escapeXml(failureMsg)}</div>
		</c:if>		
		<p>
			<label>Debit Account:</label> <select class="form-control"
				name="customerAccNumber" id="select-account">
				<option value="">Select an Account</option>
				<c:forEach items="${userAccounts}" var="account">
					<option value="${fn:escapeXml(account.number)}">${fn:escapeXml(account.name)}
						(*${fn:escapeXml(fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number)))})</option>
				</c:forEach>
			</select>
			<form:errors path="customerAccNumber" cssClass="error" element="label" />
		</p>
		<hr>
		<p>
			<label>Select Merchant:</label> <select name="merchantAccNumber" class="form-control">
				<option>Select Merchant</option>
				<c:forEach items="${merchants}" var="merchant">
					<c:forEach items="${merchant.accounts}" var="account">
					<c:if test="${account.type == 0}">	
						<option value="${fn:escapeXml(account.number)}">${fn:escapeXml(merchant.lastName)}</option>
					</c:if>					
					</c:forEach>
				</c:forEach>
			</select>
			<form:errors path="merchantAccNumber" cssClass="error" element="label" />
			<br>
		</p>
		<p>
			<label>Amount to be Transferred:</label> <input type="text"
				class="form-control" name="amount" placeholder="0.00">
			<form:errors path="amount" cssClass="error" element="label"/>
		</p>
		<hr>
		<p>
			<label>Your Private Key: </label>
			<textarea resize="none" rows="22" cols="77" name="key" form="payment" class="form-control" placeholder="Enter your private key here"></textarea>
		</p>

		
		<div class="modal-footer">
			<button type="submit" class="btn btn-success">Make Payment</button>
		</div>
	</form:form>
</t:page>