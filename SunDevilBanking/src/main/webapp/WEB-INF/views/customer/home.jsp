<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<t:page>

	<div class="page-header">
		<h1>Welcome ${fn:escapeXml(fullname)}</h1>
		<h4>Customer Id: ${fn:escapeXml(customerId) }</h4>
	</div>

	<h2>Accounts:</h2>

	<table class="table">
		<thead>
			<tr>
				<th>Name</th>
				<th>Balance</th>
				<th>Statement</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty accounts}">
				<tr><td colspan="3" class="center">No Accounts to display. Please
					contact the bank.</td></tr>
			</c:if>
			<c:forEach items="${accounts}" var="account">
				<tr>
					<td>${fn:escapeXml(account.name)}
							(*${fn:escapeXml(fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number)))})</td>
					<td>$${fn:escapeXml(account.balance)}</td>
					<td><a href="statements">View Statements</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</t:page>