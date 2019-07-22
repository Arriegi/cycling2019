<%--
  Created by IntelliJ IDEA.
  User: Ander
  Date: 01/04/2019
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
    <title><spring:message code="forms.operations.title" /></title>
    <%@ include file="/WEB-INF/views/head.jsp" %>

</head>
<body>
<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<script src="${pageContext.request.contextPath}/js/operations.js"></script>

<script>
    let messageoperationDeleteConfirm = "<spring:message code="forms.operations.operationDeleteConfirm" />";
    let messageoperationNotDeletedBecauseExistWorkpart = "<spring:message code="forms.operations.messageoperationNotDeletedBecauseExistWorkpart" />";
</script>
<div class="container">
    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/operation/form';"><i class="fas fa-plus-square"></i></button>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th scope="col"><spring:message code="forms.operation.operation" /></th>
                <th scope="col"><spring:message code="forms.operation.description" /></th>
                <th scope="col"><spring:message code="forms.operations.acciones" /></th>
            </tr>
        </thead>
    <c:forEach items="${operations}" var="operation">
        <tr id="operationRow${operation.id}">
            <td>${operation.name}</td>
            <td>${operation.description}</td>
            <td nowrap>
                <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/operation/form/${operation.id}';"><i class="fas fa-edit"></i></button>
                <button type="button" class="btn acciones" onclick="operationDelete(${operation.id});"><i class="fas fa-trash"></i></button>
            </td>
        </tr>
    </c:forEach>
    </table>
    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/operation/form';"><i class="fas fa-plus-square"></i></button>
</div>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>
