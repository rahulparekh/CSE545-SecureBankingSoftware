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

	<h2>Make a quick payment to a merchant!</h2>

	<div id="payment">
		<p>
			<label>Select Merchant:</label> <select class="form-control">
				<option>Select Merchant</option>
				<option>Amazon.com</option>
				<option>Google.com</option>
				<option>Apple.com</option>
			</select>
		</p>
		<hr>
		<p>
			<label>Debit Account:</label> <select class="form-control">
				<option>Select an Account</option>
				<option>${User}'sChecking Account (*1234)</option>
				<option>${User}'sSavings Account (*5678)</option>
			</select>
		</p>
		<p>
			<label>Amount to be Transferred:</label> <input type="text"
				class="form-control" placeholder="0.00">
		</p>
		<hr>
		<p>
			<label>Upload Security Token :</label> <input type="file"
				class="form-control">
		</p>
		<p>
			<label>One time password: <a href=""
				class="btn btn-xs btn-info">Generate OTP</a></label> <input type="text"
				class="form-control">
		</p>
		<div class="modal-footer">
			<button type="button" class="btn btn-success">Make Payment</button>
		</div>
	</div>
</t:page>