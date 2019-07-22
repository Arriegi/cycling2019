<%@ include file="/WEB-INF/views/include.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
<!DOCTYPE html>
<html lang="es">

<%@ include file="/WEB-INF/views/head.jsp" %>

<body>

<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!-- Page Content -->
<div class="container main">
    <form method="POST" action="${pageContext.request.contextPath}/media/upload" enctype="multipart/form-data">
        <div class="form-group">
            <label for="type"><spring:message code="form.type" /></label>
            <select class="form-control" id="type" name="type">
                <option value="pattern"><spring:message code="form.pattern" /></option>
                <option value="object3d"><spring:message code="form.object3d" /></option>
                <option value="image"><spring:message code="form.image" /></option>
                <option value="video"><spring:message code="form.video" /></option>
            </select>
        </div>
        <div class="form-group">
            <label for="manual">Manuales</label>
            <select class="form-control" id="manual" name="manual">
                <c:forEach items="${model.manuals}" var="manual">
                    <option value="${manual.id}">${manual.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="file">Seleccione archivo</label>
            <input type="file" class="form-control-file" id="file" name="file">
        </div>
        <button type="submit" class="btn btn-primary">Subir</button>
    </form>
</div>
</body>
</html>