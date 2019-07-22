<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <c:if test="${userrol == 'ADMIN'}">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/console.html">IMPUTADOR (${username})</a>
        </c:if>
        <c:if test="${userrol == 'USER'}">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/consoleUser.html">IMPUTADOR (${username})</a>
        </c:if>
        <c:if test="${(userrol.equals('')) || (userrol == null)}">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">IMPUTADOR</a>
        </c:if>
        <!--${userrol}-->


        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <!--
                    <a class="nav-link" href="${pageContext.request.contextPath}/swagger-ui.html"><spring:message code="menu.top.swagger" /></a>
                    -->
                    <c:if test="${userrol == 'ADMIN'}">
                        <a class="navbar-brand aaa" href="${pageContext.request.contextPath}/logout">logout</a>
                    </c:if>
                    <c:if test="${userrol == 'USER'}">
                        <a class="navbar-brand aaa" href="${pageContext.request.contextPath}/logout">logout</a>
                    </c:if>
                </li>
            </ul>
        </div>
    </div>
</nav>
