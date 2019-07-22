<%--
  Created by IntelliJ IDEA.
  User: Ander
  Date: 27/05/2019
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
    <title><spring:message code="forms.projectoperationbudget.title" /></title>
    <%@ include file="/WEB-INF/views/head.jsp" %>

</head>
<body>
<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<script src="${pageContext.request.contextPath}/js/comun.js"></script>

<script type="text/javascript">
    let messageworkpartDurationNotValid = "<spring:message code="forms.workPart.workPartDurationNotValid" />";
</script>

<!-- Page Content -->
<div class="container">
    <spring:url value="/projectoperationbudget/submit" var="projectoperationActionUrl" />
    <form:form action="${projectoperationActionUrl}" modelAttribute="projectoperationbudget" method="post">
        <div class="form-group" style="display: none;">
            <label for="id">Id</label>
            <form:input path="id" id="id" class="form-control" type="number" />
        </div>
        <div class="form-group" style="display: none;">
            <label for="id">projectId</label>
            <form:input path="project.id" id="project" class="form-control" type="number" />
        </div>
        <div class="form-group">
            <label for="operation"><spring:message code="forms.workPart.operation" /></label>
            <form:select path="operation.id" id="operation" items="${operations}" itemValue="id" itemLabel="name" class="form-control"/>
        </div>
        <div class="form-group">
            <spring:bind path="duration">
                <label for="duration"><spring:message code="forms.projectoperationbudget.duration" /></label>
                <!--${projectoperationbudget.duration}-->
                <div class="row">
                    <div class="col">
                        <form:input path="duration" id="duration" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" onfocus="event.currentTarget.backupValue = event.currentTarget.value;" onChange="validateDurationBudget(event);"/>
                    </div>
                    <div class="col-2">
                        <div class="row">
                            <div class="col">
                                <button type="button" class="btn btn-light btn-sm" onclick="addMinutesToDuration('duration', 30);"><i class="far fa-plus-square"></i></button>
                        </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <button type="button" class="btn btn-light btn-sm" onclick="addMinutesToDuration('duration', -30);"><i class="far fa-minus-square"></i></button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="invalid-feedback">
                    <form:errors path="duration" />
                </div>
            </spring:bind>
        </div>
        <div class="form-group">
            <spring:bind path="rate">
                <label for="rate"><spring:message code="forms.projectoperationbudget.rate" /></label>
                <form:input path="rate" id="rate" class="form-control ${status.error ? 'is-invalid' : ''}" type="text"  />
                <div class="invalid-feedback">
                    <form:errors path="rate" />
                </div>
            </spring:bind>
        </div>

        <button type="submit" class="btn btn-primary"><spring:message code="forms.guardar" /></button>
        <button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/project/form/${projectoperationbudget.project.id}';"><spring:message code="forms.cancelar" /></button>
    </form:form>
</div>

<%@ include file="/WEB-INF/views/foot.jsp" %>

</body>
</html>
