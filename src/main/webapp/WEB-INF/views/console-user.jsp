<%--
  Created by IntelliJ IDEA.
  User: Ander
  Date: 01/04/2019
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include.jsp" %>
<html>
<head>
    <title>Consola administrador</title>
    <%@ include file="/WEB-INF/views/head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<div class="container">
    <div class="row">
        <ul class="list-group col-12">
            <li class="list-group-item"><button type="button" class="btn btn-light btn-lg btn-block" onclick="window.location.href='${pageContext.request.contextPath}/workparts/${userId}';">Partes de trabajo</button></li>
            <li class="list-group-item"><button type="button" class="btn btn-light btn-lg btn-block" onclick="window.location.href='${pageContext.request.contextPath}/expenses/${userId}';">Gastos</button></li>
        </ul>
    </div>
</div>
<%@ include file="/WEB-INF/views/foot.jsp" %>

</body>
</html>
