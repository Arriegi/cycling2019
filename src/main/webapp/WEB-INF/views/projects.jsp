<%--
  Created by IntelliJ IDEA.
  User: Ander
  Date: 03/04/2019
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
    <title><spring:message code="forms.projects.title" /></title>
    <%@ include file="/WEB-INF/views/head.jsp" %>

</head>
<body>
<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<script src="${pageContext.request.contextPath}/js/projects.js"></script>

<script>
    let messageprojectDeleteConfirm = "<spring:message code="forms.projects.projectDeleteConfirm" />";
    let messageprojectNotDeletedBecauseExistWorkpart = "<spring:message code="forms.projects.messageprojectNotDeletedBecauseExistWorkpart" />";

</script>
<div class="container">
    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/project/form';"><i class="fas fa-plus-square"></i></button>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th scope="col"><spring:message code="forms.projects.proyecto" /></th>
            <th scope="col"><spring:message code="forms.projects.codigo" /></th>
            <th scope="col"><spring:message code="forms.projects.presupuestoaceptado" /></th>
            <th scope="col"><spring:message code="forms.projects.archivado" /></th>
            <th scope="col"><spring:message code="forms.projects.fechaalta" /></th>
            <th scope="col"><spring:message code="forms.projects.cliente" /></th>
            <th scope="col"><spring:message code="forms.operations.acciones" /></th>
        </tr>
        </thead>
        <c:forEach items="${projects}" var="project">
            <tr id="projectRow${project.id}">
                <td>${project.name}</td>
                <td nowrap>${project.code}${project.subcode}</td>
                <td>${project.acceptedBudget}</td>
                <td>${project.archived}</td>
                <td nowrap><!--${project.dischargeDate}-->
                    <fmt:parseDate value="${project.dischargeDate}" pattern="yyyy-MM-dd"
                                   var="parsedDate" type="date" />
                    <fmt:formatDate value="${parsedDate}" var="stdDatum"
                                    type="date" pattern="dd-MM-yyyy" />
                    ${stdDatum}
                </td>
                <td>${project.client.name}</td>
                <td nowrap>
                    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/project/form/${project.id}';"><i class="fas fa-edit"></i></button>
                    <button type="button" class="btn acciones" onclick="projectDelete(${project.id});"><i class="fas fa-trash"></i></button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/project/form';"><i class="fas fa-plus-square"></i></button>
</div>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>
