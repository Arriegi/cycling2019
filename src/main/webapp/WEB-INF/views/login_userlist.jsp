<%@ include file="/WEB-INF/views/include.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
<!DOCTYPE html>
<html lang="es">
<head>
<%@ include file="/WEB-INF/views/head.jsp" %>
</head>
<body>

<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!-- Page Content -->
<script src="${pageContext.request.contextPath}/js/comun.js"></script>

<div class="container">

    <!--h1 class="my-4"><spring:message code="forms.loginUserList.welcome" /></h1-->

        <c:if test="${param.login_error == '1'}">
            <h2 class="text-danger"><spring:message code="forms.loginUserList.userpassincorrect" /></h2>
        </c:if>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th><spring:message code="forms.loginUserList.users" /></th>
        </tr>
        </thead>
    <c:forEach items="${model.users}" var="user">
        <tr>
            <c:choose>
                <c:when test="${user.role.name=='ADMIN'}">
                    <td><!--a href="login.html?username=${user.email}">${user.email}</a-->
                        <button type="button" class="btn btn-light btn-lg btn-block" onclick="window.location.href=CONTEXT_PATH + 'login.html?username=${user.email}&name=${user.name}';">${user.name}</button>
                    </td>
                </c:when>
                <c:otherwise>
                    <td><!--a href="index.html">${user.email}</a-->
                        <!--button type="button" class="btn btn-primary btn-lg btn-block" onclick="window.location.href=CONTEXT_PATH + 'workparts.html';">${user.email}</button-->
                        <!--
                        <button type="button" class="btn btn-light btn-lg btn-block" onclick="window.location.href=CONTEXT_PATH + 'workparts/${user.id}';">${user.email}</button>
                        -->
                        <button type="button" class="btn btn-light btn-lg btn-block" onclick="window.location.href=CONTEXT_PATH + 'login.html?username=${user.email}&name=${user.name}';">${user.name}</button>
                    </td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
    </table>
</div>
<!-- /.container -->
<!--
<spring:message code="forms.loginUserList.users" />
-->
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>

</html>
