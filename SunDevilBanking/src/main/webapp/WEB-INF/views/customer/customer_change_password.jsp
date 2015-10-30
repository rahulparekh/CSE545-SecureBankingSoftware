<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<t:page>


		<div class="page-header">
			<h1>Change Password:</h1>
		</div>

        <div id="customer-settings">

		<c:if test="${!empty successMsg}">
			<div class="alert alert-success">${fn:escapeXml(successMsg)}</div>
		</c:if>
		<c:if test="${!empty failureMsg}">
			<div class="alert alert-danger">
				${fn:escapeXml(failureMsg)}:
				<form:errors path="*" cssClass="error" />
			</div>
		</c:if>

		<div class="modal-body">
            <form:form method="POST" modelAttribute="changepassword" action="passwordSuccess">
              
				<p>
					<label>Current Password:</label>
					<form:input class="form-control" path="currentpassword" id="currentpassword"  type = "password" minlength='3' maxlength='35' required='required'/>
				</p>
				<p>
					<label>New Password:</label>
					<form:input class="form-control" path="newpassword" id="newpassword" type = "password" minlength='3' maxlength='70' required='required'/>
				</p>
				<p>
					<label>Confirm Password:</label>
					<form:input class="form-control" path="confirmpassword" id="confirmpassword" type = "password" minlength='3' maxlength='70' required='required'/>
				</p>
				
			
				<div class="modal-footer">
					<button type="submit" class="btn btn-success" >Submit</button>
				</div>
                          
            </form:form>
			</div>
			</div>
            
      <div id="virtualKeyboard">        
      </div>

   



  
</t:page>
