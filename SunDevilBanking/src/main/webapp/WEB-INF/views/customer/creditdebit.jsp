<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>

	<div class="page-header">
		<h1>SBS Credit / Debit Funds</h1>
	</div>

	<div id="add-withdraw">
		<form:form id="credit-debit-form" method="POST" modelAttribute="transaction" action="credit-debit">
			<div>
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
				<div class="modal-header">
					<h4 class="modal-title">Add / Withdraw</h4>
				</div>
				<div class="modal-body">
					<p>
						<label>Account:</label> 
						<select class="form-control" id="select-account" name="number">
							<option value="">Select an Account</option>
							<c:set var="count" value="0" scope="page" />
							<c:forEach items="${accounts}" var="account">
								<option id="acc${count}" value="${fn:escapeXml(account.number)}">${fn:escapeXml(account.name)}
									(*${fn:escapeXml(fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number)))})</option>
								
								<c:set var="count" value="${count + 1}" scope="page" />
							</c:forEach>
						</select>
						<form:errors path="receiverAccNumber" cssClass="error" element="label"/>
					</p>
					<hr>
					<label>Current Balance:</label>
					<div id="current-balance">
						<p id="please-select-account"><i> Please select an account above </i></p>
						<c:set var="count" value="0" scope="page" />
						<c:forEach items="${accounts}" var="account">
							<p class="hide" id="acc${count}bal">$${fn:escapeXml(account.balance)}</p>
							<c:set var="count" value="${count + 1}" scope="page" />
						</c:forEach>
					</div>
					<hr>
					<p>
						<label>Amount to be Credited / Debited (in USD):</label>
						<input name="amount" type="text" class="form-control" placeholder="e.g. 10.50">
						<form:errors path="amount" cssClass="error" element="label"/>
					</p>
					<hr>
					<div>
						<label>Transaction Type:</label><br>
						<span style="margin-right:30px;"><input type="radio" name="type" value="Credit"> Credit</span>
						<span><input type="radio" name="type" value="Debit"> Debit</span>
						<div class="type-error"><form:errors path="type" cssClass="error" element="label"/></div>
					</div>
				</div>
				<div class="modal-footer">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
					<button type="submit" class="btn btn-success">Submit</button>
				</div>
			</div>

		</form:form>
		<!-- /form -->
	</div>
	<!-- /#add-withdraw -->
</t:page>