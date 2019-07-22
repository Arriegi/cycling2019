<%@ include file="/WEB-INF/views/include.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
        <!DOCTYPE html>
<html lang="es">

<%@ include file="/WEB-INF/views/head.jsp" %>

<body>

<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!-- Page Content -->

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/comun.css" />

<script src="${pageContext.request.contextPath}/js/comun.js"></script>
<script src="${pageContext.request.contextPath}/js/expense.js"></script>
<script type="text/javascript">
    let expenses = <c:out value="${expenses}" escapeXml="false"/>;

    let current = "<c:out value="${current}" />";
    let currentNumber = "<c:out value="${currentNumber}" />";
    let type = "<c:out value="${type}" />";
    let from = "<c:out value="${from}" />";
    let to = "<c:out value="${to}" />";
    let filterUserId = "<c:out value="${filterUserId}" />";
    let filterProjectId = "<c:out value="${filterProjectId}" />";

    $().ready(
        function () {
            let urlParams = new URLSearchParams(window.location.search);
            const type = urlParams.get("type");
            if (type == "reportCustom") {
                $("#previousLink").hide();
                $("#nextLink").hide();
            }
            else {
                $("#previousLink").show();
                $("#nextLink").show();
            }
        }
    );
</script>
<div class="container expenses">
    <div class="row">
          <div class="col-4">
                  <div class="btn-group" role="group" aria-label="Basic example">
                          <a id="previousLink" class="btn btn-default" href="#" data-bind="click: previous"><i class="fa fa-chevron-left"></i></a>
                          <a id="nextLink" class="btn btn-default" href="#" data-bind="click: next"><i class="fa fa-chevron-right"></i></a>
                  </div>
          </div>
        <div class="col-4">
            <h3 class="report"><c:out value="${current} ${year}" /> </h3>
        </div>
        <div class="col-4">
            <select id="typeFindFilter" class="custom-select">
                <option value="reportMonth"><spring:message code="reportMonth" /></option>
                <option value="reportYear"><spring:message code="reportYear" /></option>
                <option value="reportCustom"><spring:message code="reportCustom" /></option>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="col-4">
        </div>
        <div class="col-4">
        </div>
        <div class="col-4">
            <select id="userFilter" class="custom-select">
                <option value="">No filtrar por usuario</option>
                <c:forEach items="${users}" var="user">
                    <option value="${user.id}">${user.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="col-4">
        </div>
        <div class="col-4">
        </div>
        <div class="col-4">
            <select id="projectFilter" class="custom-select">
                <option value="">No filtrar por proyecto</option>
                <c:forEach items="${projects}" var="project">
                    <option value="${project.id}">${project.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
        <div class="row">
            <p>
                <span class="titulua"><spring:message code="reportExpense.totalAmmount" /> </span>
                <span class="hours" data-bind="text: totalAmmount"></span>
            </p>
        </div>
            <nav>
                <div class="nav nav-tabs" id="nav-tab" role="tablist">
                    <a class="nav-item nav-link active" id="nav-informeExp-tab" data-toggle="tab" href="#nav-informeExp" role="tab" aria-controls="nav-informeExp" aria-selected="true">
                        <spring:message code="reportExpense.informeExp" />
                    </a>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-informeExp" role="tabpanel" aria-labelledby="nav-informeExp-tab">
                    <table class="table table-striped">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col"><spring:message code="reportExpense.date" /></th>
                            <th scope="col"><spring:message code="reportExpense.user" /></th>
                            <th scope="col"><spring:message code="reportExpense.type" /></th>
                            <th scope="col"><spring:message code="reportExpense.project" /></th>
                            <th scope="col"><spring:message code="reportExpense.comment" /></th>
                            <th scope="col"><spring:message code="reportExpense.distancia" /></th>
                            <th scope="col"><spring:message code="reportExpense.ammount" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- ko foreach: informeExp -->
                        <tr>
                            <td data-bind="text: date"></td>
                            <td data-bind="text: user.name"></td>
                            <td data-bind="text: type"></td>
                            <td data-bind="text: project.name"></td>
                            <td data-bind="text: comment"></td>
                            <td data-bind="text: distancia" class="numerico"></td>
                            <td data-bind="text: ammount" class="importe"></td>
                        </tr>
                        <!-- /ko -->
                        </tbody>
                    </table>
                </div>
                <!--iframe src="/reports/excel"></iframe-->
                <iframe id="myDownloadFile" style="display:none;"></iframe>
                <button type="button" class="btn btn-default acciones" onclick="downloadExcel();"><spring:message code="reportExpense.downloadexcel" /></button>

            </div>
    <%@ include file="/WEB-INF/views/modals/from-to.jsp" %>
    </div>
</div>


<script src="${pageContext.request.contextPath}/js/reportsExpenses.js"></script>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>

