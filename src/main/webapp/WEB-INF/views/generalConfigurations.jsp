<%--
  Created by IntelliJ IDEA.
  User: Ander
  Date: 25/06/2019
  Time: 10:25
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

<script src="${pageContext.request.contextPath}/js/generalConfigurations.js"></script>

<script>
</script>
<div class="container">
    <!--
    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/generalConfiguration/form';"><i class="fas fa-plus-square"></i></button>
    -->
    <table class="table table-bordered">
        <thead>
        <tr>
            <th scope="col"><spring:message code="forms.generalConfiguration.description" /></th>
            <th scope="col"><spring:message code="forms.generalConfiguration.value" /></th>
            <th scope="col"><spring:message code="forms.generalConfiguration.acciones" /></th>
        </tr>
        </thead>
        <c:forEach items="${generalConfigurations}" var="generalConfiguration">
            <tr id="operationRow${generalConfiguration.id}">
                <td>${generalConfiguration.description}</td>
                <td>${generalConfiguration.value}</td>
                <td nowrap>
                    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/generalConfiguration/form/${generalConfiguration.id}';"><i class="fas fa-edit"></i></button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <!--
    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/generalConfiguration/form';"><i class="fas fa-plus-square"></i></button>
    -->
</div>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>
