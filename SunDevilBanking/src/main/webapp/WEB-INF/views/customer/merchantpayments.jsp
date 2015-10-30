<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<t:page>

	<div class="page-header">
		<h1>Merchant Payments</h1>
	</div>

	<form:form id="payment" modelAttribute="paymentrequest" method="POST" action="merchant-payments">
		<c:if test="${!empty successMsg}">
			<div class="alert alert-success">${fn:escapeXml(successMsg)}</div>
		</c:if>
		<c:if test="${!empty failureMsg}">
			<div class="alert alert-danger">${fn:escapeXml(failureMsg)}</div>
		</c:if>
		<p>
			<label>Customer Account Number:</label> <input name="customerAccNumber" type="text"
				class="form-control" placeholder="eg: 11111111111111111">
			<form:errors path="customerAccNumber" cssClass="error"
				element="label" />
		</p>
		<p>
			<label>Amount:</label> <input type="text" name="amount"
				class="form-control" placeholder="eg. 10.50">
			<form:errors path="amount" cssClass="error" element="label"/>
		</p>

		<p>
			<label>Account:</label> <select class="form-control"
				id="select-account" name="merchantAccNumber">
				<option value="">Select an Account</option>
				<c:set var="count" value="0" scope="page" />
				<c:forEach items="${accounts}" var="account">
					<option id="acc${count}" value="${fn:escapeXml(account.number)}">${fn:escapeXml(account.name)}
						(*${fn:escapeXml(fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number)))})</option>

					<c:set var="count" value="${count + 1}" scope="page" />
				</c:forEach>
			</select>
			<form:errors path="merchantAccNumber" cssClass="error"
				element="label" />
		</p>

		<div>
			<label>Transaction Type:</label><br>
			<span style="margin-right:30px;"><input type="radio" name="type" value="Credit"> Credit To Customer</span>
			<span><input type="radio" name="type" value="Debit"> Debit From Customer</span>
			<div class="type-error"><form:errors path="type" cssClass="error" element="label"/></div>
		</div>
		
		<p>
		
		
		<br>
		
		<div class="modal-footer">
			<button type="submit" class="btn btn-success">Make Payment</button>
		</div>
	</form:form>
	<!-- /payment -->


</t:page>