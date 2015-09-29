<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page>

	<div class="page-header">
		<h1>SBS Credit / Debit Funds</h1>
	</div>

	<h2>Accounts:</h2>

	<table class="table">
		<thead>
			<tr>
				<th>Name</th>
				<th>Balance</th>
				<th>Credit / Debit</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><a title="Go to Account" href="#">${user}'s Checking
						Account (*1234)</a></td>
				<td>$200.10</td>
				<td><a class="add-withdraw" href="">Add / Withdraw Funds</a></td>
			</tr>
			<tr>
				<td><a title="Go to Account" href="#">${user}'s Savings
						Account (*5678)</a></td>
				<td>$7500.47</td>
				<td><a class="add-withdraw" href="">Add / Withdraw Funds</a></td>
			</tr>
		</tbody>
	</table>

	<div id="add-withdraw" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Add / Withdraw</h4>
				</div>
				<div class="modal-body">
					<p>
						<label>Account:</label> <select class="form-control">
							<option>Select an Account</option>
							<option>${user}'s Checking Account (*1234)</option>
							<option>${user}'s Savings Account (*5678)</option>
						</select>
					</p>
					<hr>
					<p>
						<label>Current Balance:</label>
					<p>
						<i> Please select an account </i>
					</p>
					</p>
					<hr>
					<p>
						<label>Amount to be Credited / Debited (in USD):</label> <input
							type="text" class="form-control" placeholder="0.00">
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success">Credit Funds</button>
					<button type="button" class="btn btn-danger">Debit Funds</button>
					<button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</t:page>