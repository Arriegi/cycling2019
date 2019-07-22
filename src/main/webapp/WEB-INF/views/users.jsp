<%--
  Created by IntelliJ IDEA.
  User: Ander
  Date: 04/04/2019
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
    <title><spring:message code="forms.users.title" /></title>
    <%@ include file="/WEB-INF/views/head.jsp" %>

</head>
<body>
<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<script src="${pageContext.request.contextPath}/js/users.js"></script>

<script>
    let messageuserDeleteConfirm = "<spring:message code="forms.users.userDeleteConfirm" />";
    let messageuserNotDeletedBecauseExistWorkpart = "<spring:message code="forms.users.messageuserNotDeletedBecauseExistWorkpart" />";

</script>
<div class="container">
    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/user/form';"><i class="fas fa-plus-square"></i></button>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th scope="col"><spring:message code="forms.users.email" /></th>
            <th scope="col"><spring:message code="forms.users.name" /></th>
            <th scope="col"><spring:message code="forms.users.rol" /></th>
            <!--th scope="col"><spring:message code="forms.users.lastLoginDate" /></th-->
            <th scope="col"><spring:message code="forms.users.registrationDate" /></th>
            <th scope="col"><spring:message code="forms.users.rate" /></th>
            <th scope="col"><spring:message code="forms.projects.acciones" /></th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <tr id="userRow${user.id}">
                <td>${user.email}</td>
                <td>${user.name}</td>
                <td>${user.role.name}</td>
                <!--td>${user.lastLoginDate}</td-->
                <td><!--${user.registrationDate}-->
                    <fmt:parseDate value="${user.registrationDate}" pattern="yyyy-MM-dd"
                                   var="parsedDate" type="date" />
                    <fmt:formatDate value="${parsedDate}" var="stdDatum"
                                    type="date" pattern="dd-MM-yyyy" />
                        ${stdDatum}
                </td>
                <td>${user.rate}</td>
                <td>
                    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/user/form/${user.id}';"><i class="fas fa-edit"></i></button>
                    <button type="button" class="btn acciones" onclick="userDelete(${user.id});"><i class="fas fa-trash"></i></button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/user/form';"><i class="fas fa-plus-square"></i></button>
</div>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>

