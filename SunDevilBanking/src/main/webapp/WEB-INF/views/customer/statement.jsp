<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<t:page>

	<div class="page-header">
		<h1>Account Statement: ${statementName}</h1>
		<ul class="no-bullets">
			<li><strong>${user.firstName} ${!empty user.middleName ? user.middleName: ''}
					${user.lastName}</strong></li>
			<li>${user.addressLine1}${user.addressLine2}</li>
			<li>${user.state} ${user.zipCode}</li>
			<li><strong>Balance:</strong> $${account.balance}</li>
			<li><strong>Account Number:</strong> ${account.number}</li>
		</ul>
	</div>

	<h2>Transactions:</h2>

	<table class="table table-bordered">
		<thead>
			<tr>
				<th>Txn ID</th>
				<th>Date</th>
				<th>Description</th>
				<th>Debit</th>
				<th>Credit</th>
				<th>Balance</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transactions}" var="transaction">
				<tr>
					<td>${transaction.transactionID}</td>
					<td>
						<joda:format var="updatedAt"
							value="${transaction.updatedAt}" pattern="dd MMM, yyyy HH:mm"
							style="F-" dateTimeZone="America/Phoenix" />
						${updatedAt}
					</td>
					<td>${transaction.name}</td>
					<td>${transaction.type == 'Debit' ? transaction.amount : ''}</td>
					<td>${transaction.type == 'Credit' ? transaction.amount : ''}</td>
					<td>$${transaction.balance}</td>
					<c:set var="total" scope="page" value="${transaction.balance}"/>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5">Total:</td>				
				<td><strong>$${total}</strong></td>
			</tr>
		</tbody>
	</table>

</t:page>