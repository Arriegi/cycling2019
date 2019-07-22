<%--
  Created by IntelliJ IDEA.
  User: Ander
  Date: 04/04/2019
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
    <title><spring:message code="forms.user.title" /></title>
    <%@ include file="/WEB-INF/views/head.jsp" %>

</head>
<body>
<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<!-- Page Content -->
<div class="container">
    <spring:url value="/user/submit" var="userActionUrl" />
    <form:form action="${userActionUrl}" modelAttribute="user" items="user" method="post">
        <div class="form-group" style="display: none;">
            <label for="id">Id</label>
            <form:input path="id" id="id" class="form-control" type="number" value=""/>
        </div>

        <div class="form-group">
            <spring:bind path="email">
                <label for="email"><spring:message code="forms.user.email" /></label>
                <form:input path="email" id="email" class="form-control ${status.error ? 'is-invalid' : ''}" type="text"  />
                <div class="invalid-feedback">
                    <form:errors path="email" />
                </div>
            </spring:bind>
        </div>

        <div class="form-group" id="divPassword">
            <spring:bind path="password">
                <label for="password"><spring:message code="forms.user.password" /></label>
                <form:password path = "password" class="form-control" />
                <div class="invalid-feedback">
                    <form:errors path="password" />
                </div>
            </spring:bind>
        </div>


        <div class="form-group">
            <spring:bind path="name">
                <label for="name"><spring:message code="forms.user.name" /></label>
                <form:input path="name" id="name" class="form-control ${status.error ? 'is-invalid' : ''}" type="text"  />
                <div class="invalid-feedback">
                    <form:errors path="name" />
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <label for="role"><spring:message code="forms.user.role" /></label>
            <form:select path="role.id" id="role" items="${roles}" itemValue="id" itemLabel="name" class="form-control"/>
        </div>

        <div class="form-check">
            <spring:bind path="name">
                <input type="checkbox" class="form-check-input" id="chkfreeaccess" onchange="changeFreeAccess();">
                <label for="freeaccess"><spring:message code="forms.user.freeaccess" /></label>
                <form:input path="freeaccess" id="freeaccess" class="form-control ${status.error ? 'is-invalid' : ''}" type="hidden"  />
            </spring:bind>
        </div>

        <div class="form-group">
            <label for="registration-date"><spring:message code="forms.user.registrationDate" /></label>
            <form:input path="registrationDate" id="registration-date" class="form-control ${status.error ? 'is-invalid' : ''}" type="date" value=""/>
            <div class="invalid-feedback">
                <form:errors path="registrationDate" />
            </div>
        </div>

        <div class="form-group">
            <spring:bind path="rate">
                <label for="rate"><spring:message code="forms.user.rate" /></label>
                <form:input path="rate" id="rate" class="form-control ${status.error ? 'is-invalid' : ''}" type="text"  />
                <div class="invalid-feedback">
                    <form:errors path="rate" />
                </div>
            </spring:bind>
        </div>

        <button type="submit" class="btn btn-primary"><spring:message code="forms.guardar" /></button>
        <button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/users.html';"><spring:message code="forms.cancelar" /></button>
    </form:form>
</div>

<script>
    $(document).ready(function () {
        if (document.getElementById("freeaccess").value != '') {
            document.getElementById("chkfreeaccess").checked = true;
            document.getElementById("divPassword").style.display = "none";
        }
    });

    function changeFreeAccess() {
        let proba = event.currentTarget.value;
        if (event.currentTarget.checked) {
            document.getElementById("password").value = "sinpass";
            document.getElementById("divPassword").style.display = "none";
            document.getElementById("freeaccess").value = "1";
        }
        else {
            document.getElementById("password").value = document.getElementById("email").value;
            document.getElementById("divPassword").style.display = "";
            document.getElementById("freeaccess").value = "";
            alert("Se ha puesto como contrasena el propio email, indique una nueva contrasena si quiere cambiarla.");
        }
    }
</script>


<%@ include file="/WEB-INF/views/foot.jsp" %>

</body>
</html>

