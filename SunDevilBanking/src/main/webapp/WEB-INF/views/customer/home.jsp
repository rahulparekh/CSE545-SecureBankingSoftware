<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<t:page>

	<div class="page-header">
		<h1>SBS HOME - Welcome ${fullname}</h1>
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
			<c:forEach items="${accounts}" var="account"> 
				<tr>
					<td><a title="Go to Account" href="#">${account.name} (*${fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number))})</a></td>
					<td>$${account.balance}</td>
					<td><a href="">View Statement</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</t:page>