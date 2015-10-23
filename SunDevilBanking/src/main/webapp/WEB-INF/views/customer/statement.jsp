<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:page>

    <div class="page-header">
        <h1>Account Statement: September 2015</h1>
        <ul class="no-bullets">
            <li><strong>${user.firstName} ${!empty user.middleName ? user.middleName: ''} ${user.lastName}</strong></li>
            <li>${user.addressLine1} ${user.addressLine2}</li>
            <li>${user.state} ${user.zipCode}</li>
            <li><strong>Balance:</strong> $${account.balance}</li>
        </ul>
    </div>

    <h2>Transactions:</h2>

    <table class="table table-bordered">
    <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Debit</th>
            <th>Credit</th>
            <th>Balance</th>
        </tr>
    </thead>
        <tbody>
        	<c:if test="${!empty transactions}">
            	<c:set var="balance" value="${account.balance - transactions[0].amount}" scope="page" />
            </c:if>
            <c:forEach items="${transactions}" var="transaction">
                <tr>
                    <td>
                        ${transaction.createdAt}
                    </td>
                    <td>${transaction.name}</td>
                    <td>${transaction.type == 'Debit' ? transaction.amount : ''}</td>
                    <td>${transaction.type == 'Credit' ? transaction.amount : ''}</td>
                    <td>
                    	<c:if test="${transaction.type == 'Debit'}">
                       		 <c:set var="balance" value="${balance - transaction.amount}" scope="page" />
						</c:if> 
						<c:if test="${transaction.type == 'Credit'}">
                        	<c:set var="balance" value="${balance + transaction.amount}" scope="page" />
						</c:if>						
                    	${balance}
					</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</t:page>