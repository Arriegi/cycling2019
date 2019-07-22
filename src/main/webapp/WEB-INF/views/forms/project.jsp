<%@ page import="java.math.BigDecimal" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title><spring:message code="forms.project.title" /></title>
    <%@ include file="/WEB-INF/views/head.jsp" %>
</head>

<body>

<%@ include file="/WEB-INF/views/menu_top.jsp" %>
<%@ include file="/WEB-INF/views/header.jsp" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/comun.css" />
<script src="${pageContext.request.contextPath}/js/project.js"></script>

<script>
    let messageprojectOperationBudgetDeleteConfirm = "<spring:message code="forms.projectoperationbudget.deleteConfirm" />";
    let newProject = "${newProject}";
</script>

<script type="text/javascript">
    let projects = <c:out value="${projects}" escapeXml="false"/>;

    function validarProjecto() {
        let myProjectId = document.getElementById("id").value;
        let code = document.getElementById("code").value;
        let subcode = document.getElementById("subcode").value;
        let fusion = '' + code + subcode;
        for (let iProject = 0; iProject < projects.length; iProject++) {
            if (myProjectId != projects[iProject].id) {
                if (fusion == ('' + projects[iProject].code + projects[iProject].subcode)) {
                    alert("El codigo/subcodigo indicado ya existe.");
                    return false;
                }
            }
        }

        return true;
    }
</script>


<!-- Page Content -->
<div class="container">
    <spring:url value="/project/submit" var="projectActionUrl" />
    <form:form action="${projectActionUrl}" modelAttribute="project" method="post" onsubmit="return validarProjecto();">
        <div class="form-group" style="display: none;">
            <label for="id">Id</label>
            <form:input path="id" id="id" class="form-control" type="number" />
        </div>

        <div class="form-group">
            <spring:bind path="name">
            <label for="name"><spring:message code="forms.project.name" /></label>
            <form:input path="name" id="name" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
            <div class="invalid-feedback">
                <form:errors path="name" />
            </div>
            </spring:bind>
        </div>
        <div class="form-group row">
            <div class="col">
                <spring:bind path="code">
                    <label for="code"><spring:message code="forms.project.code" /></label>
                    <form:input path="code" id="code" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                    <div class="invalid-feedback">
                        <form:errors path="code" />
                    </div>
                </spring:bind>
            </div>
            <div class="col">
                <spring:bind path="subcode">
                    <label for="code"><spring:message code="forms.project.subcode" /></label>
                    <form:input path="subcode" id="subcode" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                    <div class="invalid-feedback">
                        <form:errors path="subcode" />
                    </div>
                </spring:bind>
            </div>
        </div>
        <div class="form-check">
            <form:checkbox path="acceptedBudget" class="form-check-input" id="accepted-budget" />
            <label class="form-check-label" for="accepted-budget"><spring:message code="forms.project.acceptedBudget" /></label>
        </div>
        <div class="form-check">
            <form:checkbox path="archived" class="form-check-input" id="archived" />
            <label class="form-check-label" for="archived"><spring:message code="forms.project.archived" /></label>
        </div>
        <div class="form-group">
            <label for="discharge-date"><spring:message code="forms.project.dischargeDate" /></label>
            <form:input path="dischargeDate" id="discharge-date" class="form-control ${status.error ? 'is-invalid' : ''}" type="date" value=""/>
            <div class="invalid-feedback">
                <form:errors path="dischargeDate" />
            </div>
        </div>
        <div class="form-group">
            <label for="client"><spring:message code="forms.project.client" /></label>
            <form:select path="client.id" id="client" items="${clients}" itemValue="id" itemLabel="name" class="form-control"/>
        </div>
        <div class="form-group">
            <spring:bind path="note">
                <label for="name"><spring:message code="forms.project.note" /></label>
                <form:input path="note" id="note" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                <div class="invalid-feedback">
                    <form:errors path="note" />
                </div>
            </spring:bind>
        </div>
        <div class="form-group row">
            <div class="col">
                <spring:message code="forms.project.tituloPrespupuestado" />
            </div>
            <div class="col">
            </div>
            <div class="col">
                <spring:message code="forms.project.tituloReal" />
            </div>
        </div>
        <div class="form-group row">
            <div class="col">
                <spring:bind path="presupProveedorImporte">
                    <label for="name"><spring:message code="forms.project.presupuestoProveedoresImporte" /></label>
                    <form:input path="presupProveedorImporte" id="presupProveedorImporte" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                    <div class="invalid-feedback">
                        <form:errors path="presupProveedorImporte" />
                    </div>
                </spring:bind>
            </div>
            <div class="col">
            </div>
            <div class="col">
                <spring:bind path="note">
                    <label for="name"><spring:message code="forms.project.realProveedoresImporte" /></label>
                    <form:input path="realProveedorImporte" id="realProveedorImporte" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                    <div class="invalid-feedback">
                        <form:errors path="realProveedorImporte" />
                    </div>
                </spring:bind>
            </div>
        </div>
        <div class="form-group row">
            <div class="col">
                <spring:bind path="presupProveedorIncremento">
                    <label for="name"><spring:message code="forms.project.presupuestoProveedoresIncremento" /></label>
                    <form:input path="presupProveedorIncremento" id="presupProveedorIncremento" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                    <div class="invalid-feedback">
                        <form:errors path="presupProveedorIncremento" />
                    </div>
                </spring:bind>
            </div>
            <div class="col">
            </div>
            <div class="col">
                <spring:bind path="note">
                    <label for="name"><spring:message code="forms.project.realProveedoresIncremento" /></label>
                    <form:input path="realProveedorIncremento" id="realProveedorIncremento" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                    <div class="invalid-feedback">
                        <form:errors path="realProveedorIncremento" />
                    </div>
                </spring:bind>
            </div>
        </div>
        <div class="form-group row">
            <div class="col">
                <label for="name"><spring:message code="forms.project.presupuestoProveedoresTotal" /></label>
                <input type="text" class="form-control" readonly value="${Math.round((project.presupProveedorImporte + (project.presupProveedorImporte * project.presupProveedorIncremento / 100.0)) * 100) / 100}">
            </div>
            <div class="col">
                <label for="name"><spring:message code="forms.project.presupuestoDifTotal" /></label>
                <input type="text" class="form-control" readonly value="${Math.round(((project.realProveedorImporte + (project.realProveedorImporte * project.realProveedorIncremento / 100.0)) - (project.presupProveedorImporte + (project.presupProveedorImporte * project.presupProveedorIncremento / 100.0))) * 100) / 100}">
            </div>
            <div class="col">
                <label for="name"><spring:message code="forms.project.realProveedoresTotal" /></label>
                <input type="text" class="form-control" readonly value="${Math.round((project.realProveedorImporte + (project.realProveedorImporte * project.realProveedorIncremento / 100.0)) * 100) / 100}">
            </div>
        </div>
        <div id="budgetOperations" class="form-group row">
            <div class="col">
                <c:if test="${operationAvailable.equals('S')}">
                    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/project/${project.id}/operationbudget/form';"><i class="fas fa-plus-square"></i></button>
                </c:if>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th scope="col"><spring:message code="forms.project.operation" /></th>
                        <th scope="col"><spring:message code="forms.project.presupuestado" /></th>
                        <th scope="col"><spring:message code="forms.projects.presupestadoTasa" /></th>
                        <th scope="col"><spring:message code="forms.projects.presupestadoImporte" /></th>
                        <th scope="col"><spring:message code="forms.projects.real" /></th>
                        <th scope="col"><spring:message code="forms.projects.realImporte" /></th>
                        <th scope="col"><spring:message code="forms.projects.difImporte" /></th>
                        <th scope="col"><spring:message code="forms.projects.acciones" /></th>
                    </tr>
                    </thead>
                    <c:set var="totalBudgetTime" value="0"/>
                    <c:set var="totalTime" value="0"/>
                    <c:set var="totalBudgetAmount" value="0"/>
                    <c:set var="totalAmount" value="0"/>
                    <c:forEach items="${operationsBudgets}" var="operationsBudget">
                        <tr id="operationsBudgetRow${operationsBudget.id}">
                            <td>
                                ${operationsBudget.operation.name}
                            </td>
                            <td>
                                ${String.format("%02d", operationsBudget.duration.toHours())}:${String.format("%02d", operationsBudget.duration.minusHours(operationsBudget.duration.toHours()).toMinutes())}
                                <c:set var="totalBudgetTime" value="${totalBudgetTime + operationsBudget.duration.seconds}"/>
                            </td>
                            <td class="importe">
                                ${operationsBudget.rate}
                            </td>
                            <td class="importe">
                                <c:set var="calculateBudgetAmount" value="${operationsBudget.duration.seconds / 3600 * operationsBudget.rate}"/>
                                <fmt:formatNumber var="formatBudgetAmount"
                                                  type="number"
                                                  value="${calculateBudgetAmount}"
                                                  minFractionDigits="2"
                                                  maxFractionDigits="2"
                                />
                                ${formatBudgetAmount}
                                <c:set var="totalBudgetAmount" value="${totalBudgetAmount + operationsBudget.duration.seconds / 3600 * operationsBudget.rate}"/>
                            </td>
                            <td>
                                <c:set var="subTotalTime" value="0"/>
                                <c:set var="subTotalAmount" value="0"/>
                                <c:forEach items="${workparts}" var="workpart">
                                    <c:choose>
                                        <c:when test="${operationsBudget.operation.id == workpart.operation.id}">
                                            <c:set var="subTotalTime" value="${subTotalTime + workpart.duration.seconds}"/>
                                            <c:set var="totalTime" value="${totalTime + workpart.duration.seconds}"/>
                                            <c:set var="subTotalAmount" value="${subTotalAmount + (workpart.duration.seconds / 3600 * workpart.user.rate)}"/>
                                            <c:set var="totalAmount" value="${totalAmount + (workpart.duration.seconds / 3600 * workpart.user.rate)}"/>
                                        </c:when>
                                    </c:choose>

                                </c:forEach>
                                <c:set var="durhrint" value="${(subTotalTime - (subTotalTime mod 3600)) / 3600}" />
                                <fmt:formatNumber var="durhr"
                                                  type="number"
                                                  value="${durhrint}"
                                                  maxFractionDigits="0"
                                                  minIntegerDigits="2"
                                />
                                <c:set var="temp" value="${(subTotalTime - (durhr *3600))}" />
                                <c:set var="durminint" value="${ (temp - (temp mod 60)) / 60 }" />
                                <fmt:formatNumber var="durmin"
                                                  type="number"
                                                  value="${durminint}"
                                                  maxFractionDigits="0"
                                                  minIntegerDigits="2"
                                />
                                ${durhr}:${durmin}
                            </td>
                            <td class="importe">
                                <fmt:formatNumber var="formatSubTotalAmount"
                                                  type="number"
                                                  value="${subTotalAmount}"
                                                  minFractionDigits="2"
                                                  maxFractionDigits="2"
                                />
                                ${formatSubTotalAmount}
                            </td>
                            <td class="importe">
                                <fmt:formatNumber var="formatDifSubTotalAmount"
                                                  type="number"
                                                  value="${subTotalAmount - calculateBudgetAmount}"
                                                  minFractionDigits="2"
                                                  maxFractionDigits="2"
                                />
                                ${formatDifSubTotalAmount}
                            </td>
                            <td>
                                <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/project/${project.id}/operationbudget/form/${operationsBudget.operation.id}';"><i class="fas fa-edit"></i></button>
                                <button type="button" class="btn acciones" onclick="projectOperationBudgetDelete(${operationsBudget.id});"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td>Total</td>
                        <td>
                            <c:set var="durhrint" value="${(totalBudgetTime - (totalBudgetTime mod 3600)) / 3600}" />
                            <fmt:formatNumber var="durhr"
                                              type="number"
                                              value="${durhrint}"
                                              maxFractionDigits="0"
                                              minIntegerDigits="2"
                            />
                            <c:set var="temp" value="${(totalBudgetTime - (durhr *3600))}" />
                            <c:set var="durminint" value="${ (temp - (temp mod 60)) / 60 }" />
                            <fmt:formatNumber var="durmin"
                                              type="number"
                                              value="${durminint}"
                                              maxFractionDigits="0"
                                              minIntegerDigits="2"
                            />
                                ${durhr}:${durmin}
                        </td>
                        <td class="importe">
                        </td>
                        <td class="importe">
                            <fmt:formatNumber var="formatTotalBudgetAmount"
                                              type="number"
                                              value="${totalBudgetAmount}"
                                              minFractionDigits="2"
                                              maxFractionDigits="2"
                            />
                            ${formatTotalBudgetAmount}
                        </td>
                        <td>
                            <c:set var="durhrint" value="${(totalTime - (totalTime mod 3600)) / 3600}" />
                            <fmt:formatNumber var="durhr"
                                              type="number"
                                              value="${durhrint}"
                                              maxFractionDigits="0"
                                              minIntegerDigits="2"
                            />
                            <c:set var="temp" value="${(totalTime - (durhr *3600))}" />
                            <c:set var="durminint" value="${ (temp - (temp mod 60)) / 60 }" />
                            <fmt:formatNumber var="durmin"
                                              type="number"
                                              value="${durminint}"
                                              maxFractionDigits="0"
                                              minIntegerDigits="2"
                            />
                                ${durhr}:${durmin}
                        </td>
                        <td class="importe">
                            <fmt:formatNumber var="formatTotalAmount"
                                              type="number"
                                              value="${totalAmount}"
                                              minFractionDigits="2"
                                              maxFractionDigits="2"
                            />
                            ${formatTotalAmount}
                        </td>
                        <td class="importe">
                            <fmt:formatNumber var="formatDifTotalAmount"
                                              type="number"
                                              value="${totalAmount - totalBudgetAmount}"
                                              minFractionDigits="2"
                                              maxFractionDigits="2"
                            />
                            ${formatDifTotalAmount}
                        </td>
                        <td></td>
                    </tr>
                </table>
                <c:if test="${operationAvailable.equals('S')}">
                    <button type="button" class="btn acciones" onclick="window.location.href='${pageContext.request.contextPath}/project/${project.id}/operationbudget/form';"><i class="fas fa-plus-square"></i></button>
                </c:if>
            </div>
        </div>
        <button type="submit" class="btn btn-primary"><spring:message code="forms.guardar" /></button>
        <button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/projects.html';"><spring:message code="forms.cancelar" /></button>
    </form:form>
</div>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>
