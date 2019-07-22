<%--
  Created by IntelliJ IDEA.
  User: Ander
  Date: 04/04/2019
  Time: 9:04
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
    <title><spring:message code="forms.client.title" /></title>
    <%@ include file="/WEB-INF/views/head.jsp" %>

</head>
<body>
<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!-- Page Content -->
<div class="container">
    <spring:url value="/client/submit" var="clientActionUrl" />
    <form:form action="${clientActionUrl}" modelAttribute="client" items="client" method="post">
        <div class="form-group" style="display: none;">
            <label for="id">Id</label>
            <form:input path="id" id="id" class="form-control" type="number" value=""/>
        </div>

        <div class="form-group">
            <spring:bind path="name">
                <label for="name"><spring:message code="forms.client.name" /></label>
                <form:input path="name" id="name" class="form-control ${status.error ? 'is-invalid' : ''}" type="text"  />
                <div class="invalid-feedback">
                    <form:errors path="name" />
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <label for="discharge-date"><spring:message code="forms.client.dischargeDate" /></label>
            <form:input path="dischargeDate" id="discharge-date" class="form-control ${status.error ? 'is-invalid' : ''}" type="date" value=""/>
            <div class="invalid-feedback">
                <form:errors path="dischargeDate" />
            </div>
        </div>
        <button type="submit" class="btn btn-primary"><spring:message code="forms.guardar" /></button>
        <button type="button" class="btn btn-primary" onclick="window.location.href='${pageContext.request.contextPath}/clients.html';"><spring:message code="forms.cancelar" /></button>
    </form:form>
</div>

<%@ include file="/WEB-INF/views/foot.jsp" %>

</body>
</html>

