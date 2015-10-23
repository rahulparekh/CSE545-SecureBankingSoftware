<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:page>

    <div class="page-header">
        <h1>Account Statements</h1>
    </div>

    <h2>Transaction search:</h2>
    <form class="transaction-form" action="#">
        <div class="form-group">
          <div class="row">
              <div class="col-xs-8">
                  <label class="control-label">Account</label>
                  <select class="form-control" id="select-account-statements" name="number">
                    <option value="">Select an Account</option>
                    <c:set var="count" value="0" scope="page" />
                    <c:forEach items="${accounts}" var="account">
                        <option id="acc${count}" value="${fn:escapeXml(account.number)}">${fn:escapeXml(account.name)}
                            (*${fn:escapeXml(fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number)))})</option>
                        
                        <c:set var="count" value="${count + 1}" scope="page" />
                    </c:forEach>
                </select>
              </div>
          </div>            
        </div>
        <div class="form-group">
          <div class="row">
            <div class="col-xs-4">
                <label class="control-label">Date From</label>
                <input type="text" class="form-control" name="title" />
            </div>

            <div class="col-xs-4 selectContainer">
                <label class="control-label">Date To</label>
                <input type="text" class="form-control" name="title" />
            </div>
          </div>
        </div>

        <button type="submit" class="btn btn-success">Submit</button>

    </form>

    <hr>

    <h2>Statements:</h2>

    <div class="row">
    <form:form id="statements-form" method="POST" modelAttribute="transaction" action="statements">
        
        <div class="col-xs-8">
            <label>Select an account:</label><br>
            <select class="form-control" id="select-account-statements2" name="number" style="display:inline-block; width:60%;">
                <option value="">Select an Account</option>
                <c:forEach items="${accounts}" var="account">
                    <option value="${fn:escapeXml(account.number)}">${fn:escapeXml(account.name)}
                        (*${fn:escapeXml(fn:substring(account.number, fn:length(account.number) - 4, fn:length(account.number)))})</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-success" style="display:inline-block">Submit</button>
            <br><br>
        </div>    
    </form:form>
    </div>

    <c:if test="${!empty statementFailureMsg}">
        <div class="alert alert-danger">                       
            ${fn:escapeXml(statementFailureMsg)}
        </div>
    </c:if>

    <table class="table table-bordered">
    <thead>
        <tr>
            <th colspan="3">Statements</th>
        </tr>
    </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty accNumber}">
                    <tr>
                        <td colspan="3">Please select an account.</td>
                    </tr> 
                </c:when>
                <c:when test="${empty statements}">
                    <tr>
                        <td colspan="3">Could not find statements for this account.</td>
                    </tr>                
                </c:when>
            </c:choose>
            <c:forEach items="${statements}" var="statement">                
                <tr>
                    <td width="70%"><a href="#">${fn:escapeXml(statement.month)} ${fn:escapeXml(statement.year)}</a></td>
                    <td class="center"><button type="button" class="btn btn-default">View</button></td>
                    <td class="center"><button type="button" class="btn btn-default">Download</button></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</t:page>