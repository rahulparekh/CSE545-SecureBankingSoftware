<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

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
			<tr>
				<td><a title="Go to Account" href="#">${fullname}'s Checking
						Account (*1234)</a></td>
				<td>$200.10</td>
				<td><a href="">View Statement</a></td>
			</tr>
			<tr>
				<td><a title="Go to Account" href="#">${fullname}'s Savings
						Account (*5678)</a></td>
				<td>$7500.47</td>
				<td><a href="">View Statement</a></td>
			</tr>
		</tbody>
	</table>

</t:page>