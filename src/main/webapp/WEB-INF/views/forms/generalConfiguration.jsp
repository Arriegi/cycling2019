<%--
  Created by IntelliJ IDEA.
  User: Ander
  Date: 25/06/2019
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
    <title><spring:message code="forms.generalConfiguration.title" /></title>
    <%@ include file="/WEB-INF/views/head.jsp" %>

</head>
<body>
<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!-- Page Content -->
<div class="container">
    <spring:url value="/generalConfiguration/submit" var="generalConfigurationActionUrl" />
    <form:form action="${generalConfigurationActionUrl}" modelAttribute="generalConfiguration" items="generalConfiguration" method="post">
        <div class="form-group" style="display: none;">
            <label for="id">Id</label>
            <form:input path="id" id="id" class="form-control" type="number" value=""/>
        </div>

        <div class="form-group">
            <spring:bind path="description">
                <label for="description"><spring:message code="forms.generalConfiguration.description" /></label>
                <form:input path="description" id="description" class="form-control ${status.error ? 'is-invalid' : ''}" type="text"  />
                <div class="invalid-feedback">
                    <form:errors path="description" />
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="value">
                <label for="value"><spring:message code="forms.generalConfiguration.value" /></label>
                <form:input path="value" id="description" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                <div class="invalid-feedback">
                    <form:errors path="value" />
                </div>
            </spring:bind>
        </div>
        <button type="submit" class="btn btn-primary"><spring:message code="forms.guardar" /></button>
        <button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/generalConfigurations.html';"><spring:message code="forms.cancelar" /></button>
    </form:form>
</div>

<%@ include file="/WEB-INF/views/foot.jsp" %>

</body>
</html>
