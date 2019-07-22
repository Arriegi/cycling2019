<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head><title>Hello :: Spring Application</title></head>
<body>
<h1>Hello - Spring Application</h1>
<p>Kaixo <c:out value="${user.name}"/></p>
<form role="form" method="post" action="${pageContext.request.contextPath}/index.html">
<input type="submit">


</form>
</body>
</html>