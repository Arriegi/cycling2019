<head>
    <meta charset="utf-8">
    <%@ page contentType="text/html; charset=UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Jon Arriaga">
    <title>
        <c:choose>
            <c:when test="${model.title != null}">
                <c:out value="${model.title} prueba"/>
            </c:when>
            <c:otherwise>
                <c:out value="Imputador"/>
            </c:otherwise>
        </c:choose>
    </title>
    <!-- Font Awesome -->
    <link href="${pageContext.request.contextPath}/css/all.css" rel="stylesheet">
    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/css/modern-business.css" rel="stylesheet">

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>
    <script src="${pageContext.request.contextPath}/js/lib/jquery-3.3.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/lib/knockout-3.5.0.js"></script>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>