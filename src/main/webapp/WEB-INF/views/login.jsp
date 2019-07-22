<%@ include file="/WEB-INF/views/include.jsp" %>

<html lang="es">
<%@ include file="/WEB-INF/views/head.jsp" %>
<body>
<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<script type="text/javascript">
    $().ready(
        function () {
            document.getElementById("password").focus();
        }
    );
</script>


<div class="container-fluid">
    <div class="row">
        <div class="col-md-4 offset-4">
            <form id="loginForm" role="form" method="post" action="${pageContext.request.contextPath}/login">
                <div class="form-group">
                    <label for="username">
                        Usuario
                    </label>
                    <input name="username" type="hidden" id="username"  value="<%=request.getParameter("username")%>" readonly/>
                    <input name="name" type="text" class="form-control" id="name"  value="<%=request.getParameter("name")%>" readonly/>
                </div>
                <div class="form-group">
                    <label for="password" id="lPassword">
                        Contrase&ntilde;a
                    </label>
                    <input type="password" name="password" class="form-control" id="password" value=""/>
                </div>
                <!--div class="form-group">
                    <input id="remember_me" name="remember-me" type="checkbox"/>
                    <label for="remember_me" class="inline">Recordar contrase&ntilde;a</label>
                </div-->
                <button id="btnSubmit" type="submit" class="btn btn-secondary">
                    Acceder
                </button>
            </form>
        </div>
    </div>
</div>
<script>
    var sinpass='${user.freeaccess}';
    if (sinpass != '') {
        $(document).ready(function () {
            document.getElementById("btnSubmit").style.display = 'none';
            document.getElementById("password").value = "sinpass";
            document.getElementById("password").readonly = true;
            document.getElementById("password").style.display = 'none';
            document.getElementById("lPassword").style.display = 'none';
            document.getElementById("loginForm").submit();
        });
    }

</script>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>