<%--
  Created by IntelliJ IDEA.
  User: Ander
  Date: 04/04/2019
  Time: 8:39
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
    <title><spring:message code="forms.clients.title" /></title>
    <%@ include file="/WEB-INF/views/head.jsp" %>

</head>
<body>
<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<script src="${pageContext.request.contextPath}/js/clients.js"></script>

<script>
    let messageclientDeleteConfirm = "<spring:message code="forms.clients.projectDeleteConfirm" />";
    let messageclientNotDeletedBecauseExistProjects = "<spring:message code="forms.clients.messageclientNotDeletedBecauseExistProjects" />";

</script>
<div class="container">
    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/client/form';"><i class="fas fa-plus-square"></i></button>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th scope="col"><spring:message code="forms.clients.name" /></th>
            <th scope="col"><spring:message code="forms.clients.dischargeDate" /></th>
            <th scope="col"><spring:message code="forms.clients.acciones" /></th>
        </tr>
        </thead>
        <c:forEach items="${clients}" var="client">
            <tr id="clientRow${client.id}">
                <td>${client.name}</td>
                <td nowrap><!--${client.dischargeDate}-->
                    <fmt:parseDate value="${client.dischargeDate}" pattern="yyyy-MM-dd"
                                   var="parsedDate" type="date" />
                    <fmt:formatDate value="${parsedDate}" var="stdDatum"
                                    type="date" pattern="dd-MM-yyyy" />
                        ${stdDatum}
                </td>
                <td nowrap>
                    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/client/form/${client.id}';"><i class="fas fa-edit"></i></button>
                    <button type="button" class="btn acciones" onclick="clientDelete(${client.id});"><i class="fas fa-trash"></i></button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/client/form';"><i class="fas fa-plus-square"></i></button>
</div>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>

