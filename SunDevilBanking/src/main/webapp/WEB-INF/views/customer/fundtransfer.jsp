<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>

	<div class="page-header">
		<h1>SBS Fund Transfer</h1>
	</div>

	<h2>Transfer Funds:</h2>

	<form:form id="fund-transfer" action="fund-transfer" method="POST" modelAttribute="transaction" name="fund-transfer">
		<div class="modal-body">
			<c:if test="${!empty successMsg}">
				<div class="alert alert-success">						
					${fn:escapeXml(successMsg)}
				</div>
			</c:if>
			<c:if test="${!empty failureMsg}">
				<div class="alert alert-danger">						
					${fn:escapeXml(failureMsg)}
				</div>
			</c:if>
			<p>
				<label>Debit Account:</label> <select class="form-control"
					name="senderAccNumber" id="select-account">
					<option value="">Select an Account</option>
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${accounts}" var="account">
						<option id="acc${count}" value="${fn:escapeXml(account.number)}">${fn:escapeXml(account.name)}
							(*${fn:escapeXml(fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number)))})</option>

						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</select>
				<form:errors path="senderAccNumber" cssClass="error"
					element="label" />
			</p>
			<hr>
			<div>
				<label>Current Balance:</label>
				<div id="current-balance">
					<p id="please-select-account">
						<i> Please select an account above </i>
					</p>
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${accounts}" var="account">
						<p class="hide" id="acc${count}bal">$${fn:escapeXml(account.balance)}</p>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</div>
			</div>
			<hr>
			<div>
				<label>Transfer Type:</label><br> <span
					style="margin-right: 30px;"> <input type="radio" name="type"
					value="Internal" class="internal-transfer transfer-option-btn">
					Internal
				</span> <span><input type="radio" name="type" value="External"
					class="external-transfer transfer-option-btn"> External</span>
				<div class="type-error">
					<form:errors path="type" cssClass="error" element="label" />
				</div>
			</div>
			<div class="transfer-account-details">
				<div id="internal-transfer" class="hidden">
					<select class="form-control" name="receiverAccNumber"
						id="select-receiver-account">
						<option value="">Select a credit account</option>
						<c:set var="count" value="0" scope="page" />
						<c:forEach items="${accounts}" var="account">
							<option id="acc${count}credit"
								value="${fn:escapeXml(account.number)}">${fn:escapeXml(account.name)}
								(*${fn:escapeXml(fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number)))})</option>

							<c:set var="count" value="${count + 1}" scope="page" />
						</c:forEach>
					</select>
					<form:errors path="receiverAccNumber" cssClass="error"
						element="label" />
				</div>

				<div id="external-transfer" class="hidden">
					<p>
						<label>Beneficiery Account Number:</label> <input type="text"
							class="form-control" name="receiverAccNumberExternal"
							placeholder="eg: 11111111111111111">
					</p>
				</div>
			</div>
			<hr>
			<p>
				<label>Amount to be Transferred:</label>
				<input name="amount" type="text" class="form-control" placeholder="e.g. 10.50">
				<form:errors path="amount" cssClass="error" element="label"/>
			</p>
			<hr>
			<p>
				<label>Your Private Key: </label>
				<textarea resize="none" rows="22" cols="77" name="key" form="fund-transfer" class="form-control" placeholder="Enter your private key here"></textarea>
			</p>
		
		</div>
		<div class="modal-footer">
			<button type="submit" class="btn btn-success">Transfer Funds</button>
		</div>
	</form:form>
	<!-- /#fund-transfer -->
</t:page>