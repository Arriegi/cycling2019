<%@ include file="/WEB-INF/views/include.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
        <!DOCTYPE html>
<html lang="es">

<%@ include file="/WEB-INF/views/head.jsp" %>

<body>

<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!-- Page Content -->
<script type="text/javascript">
    let parts = <c:out value="${parts}" escapeXml="false"/>;
    let current = "<c:out value="${current}" />";
    let currentNumber = "<c:out value="${currentNumber}" />";
    let type = "<c:out value="${type}" />";
    let from = "<c:out value="${from}" />";
    let to = "<c:out value="${to}" />";

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
<div class="container parts">
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
            <p>
                <span class="titulua"><spring:message code="report.totalHours" /> </span>
                <span class="hours" data-bind="text: totalHours"></span>
            </p>
        </div>
            <nav>
                <div class="nav nav-tabs" id="nav-tab" role="tablist">
                    <a class="nav-item nav-link active" id="nav-clients-tab" data-toggle="tab" href="#nav-clients" role="tab" aria-controls="nav-clients" aria-selected="true">
                        <spring:message code="report.clients" />
                    </a>
                    <a class="nav-item nav-link" id="nav-projects-tab" data-toggle="tab" href="#nav-projects" role="tab" aria-controls="nav-projects" aria-selected="false">
                        <spring:message code="report.projects" />
                    </a>
                    <a class="nav-item nav-link" id="nav-users-tab" data-toggle="tab" href="#nav-users" role="tab" aria-controls="nav-users" aria-selected="false">
                        <spring:message code="report.users" />
                    </a>
                    <a class="nav-item nav-link" id="nav-operations-tab" data-toggle="tab" href="#nav-operations" role="tab" aria-controls="nav-operations" aria-selected="false">
                        <spring:message code="report.operations" />
                    </a>
                    <a class="nav-item nav-link" id="nav-informePOO-tab" data-toggle="tab" href="#nav-informePOO" role="tab" aria-controls="nav-informePOO" aria-selected="false">
                        <spring:message code="report.informePOO" />
                    </a>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-clients" role="tabpanel" aria-labelledby="nav-clients-tab">
                    <table class="table table-striped">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col"><spring:message code="report.clients" /></th>
                            <th scope="col"><spring:message code="report.totalHours" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- ko foreach: clients -->
                        <tr>
                            <td data-bind="text: client.name"></td>
                            <td data-bind="text: duration"></td>
                        </tr>
                        <!-- /ko -->
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="nav-projects" role="tabpanel" aria-labelledby="nav-projects-tab">
                    <table class="table table-striped">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col"><spring:message code="report.projects" /></th>
                            <th scope="col"><spring:message code="report.totalHours" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- ko foreach: projects -->
                        <tr>
                            <td data-bind="text: project.name"></td>
                            <td data-bind="text: duration"></td>
                        </tr>
                        <!-- /ko -->
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="nav-users" role="tabpanel" aria-labelledby="nav-users-tab">
                    <table class="table table-striped">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col"><spring:message code="report.users" /></th>
                            <th scope="col"><spring:message code="report.totalHours" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- ko foreach: users -->
                        <tr>
                            <td data-bind="text: user.name"></td>
                            <td data-bind="text: duration"></td>
                        </tr>
                        <!-- /ko -->
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="nav-operations" role="tabpanel" aria-labelledby="nav-operations-tab">
                    <table class="table table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col"><spring:message code="report.operations" /></th>
                                <th scope="col"><spring:message code="report.totalHours" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- ko foreach: operations -->
                            <tr>
                                <td data-bind="text: operation.name"></td>
                                <td data-bind="text: duration"></td>
                            </tr>
                            <!-- /ko -->
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="nav-informePOO" role="tabpanel" aria-labelledby="nav-informePOO-tab">
                    <table class="table table-striped">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col"><spring:message code="report.projects" /></th>
                            <th scope="col"><spring:message code="report.operations" /></th>
                            <th scope="col"><spring:message code="report.users" /></th>
                            <th scope="col"><spring:message code="report.totalHours" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- ko foreach: informePOO -->
                        <tr>
                            <td data-bind="text: project.name"></td>
                            <td data-bind="text: operation.name"></td>
                            <td data-bind="text: user.name"></td>
                            <td data-bind="text: duration"></td>
                        </tr>
                        <!-- /ko -->
                        </tbody>
                    </table>
                </div>
                <!--iframe src="/reports/excel"></iframe-->
                <iframe id="myDownloadFile" style="display:none;"></iframe>
                <button type="button" class="btn btn-default acciones" onclick="downloadExcel();"><spring:message code="report.downloadexcel" /></button>

            </div>
    <%@ include file="/WEB-INF/views/modals/from-to.jsp" %>
    </div>
</div>


<script src="${pageContext.request.contextPath}/js/reports.js"></script>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>

