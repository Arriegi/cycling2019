<%@ page import="eus.jarriaga.cycling.business.entities.WorkPart" %>
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

<script src="${pageContext.request.contextPath}/js/comun.js"></script>
<script src="${pageContext.request.contextPath}/js/workpart.js"></script>
<script src="${pageContext.request.contextPath}/js/workparts.js"></script>

<script type="text/javascript">
    let selectedDate = "${selectedDate}";
    let parts = <c:out value="${JSONparts}" escapeXml="false"/>;
    let projects = <c:out value="${JSONprojects}" escapeXml="false"/>;

    let messageworkpartDeleteConfirm = "<spring:message code="forms.workPart.workPartDeleteConfirm" />";
    let messageworkpartDurationNotValid = "<spring:message code="forms.workPart.workPartDurationNotValid" />";
    let messageNeedProject = "<spring:message code="forms.workPart.needProject" />";
    let messageNeedOperation = "<spring:message code="forms.workPart.needOperation" />";
    let messageNeedDuration = "<spring:message code="forms.workPart.needDuration" />";

    $().ready(
            function () {
                /*
                for (let iPart = 0; iPart < parts.length; iPart++) {
                    $('#duration' + parts[iPart].id).html(printFormatDuration(parts[iPart].duration.seconds));
                }
                */
            }
    );
</script>

<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-4">
            <div class="btn-group" role="group" aria-label="Basic example">
                <a class="btn btn-default" href="${pageContext.request.contextPath}/workparts/${userId}?date=${previousDate}"><i class="fa fa-chevron-left"></i></a>
                <a class="btn btn-default" href="${pageContext.request.contextPath}/workparts/${userId}"><i><spring:message code="forms.workPart.today" /></i></a>
                <a class="btn btn-default" href="${pageContext.request.contextPath}/workparts/${userId}?date=${nextDate}"><i class="fa fa-chevron-right"></i></a>
            </div>
        </div>
        <div class="col-6">
            <h2 class="report"><!--<c:out value="${selectedDate}" />-->
                <spring:message code="forms.workPart.title" />
                <fmt:parseDate value="${selectedDate}" pattern="yyyy-MM-dd"
                               var="parsedDate" type="date" />
                <fmt:formatDate value="${parsedDate}" var="stdDatum"
                                type="date" pattern="dd-MM-yyyy" />
                <c:out value="${stdDatum}" />
            </h2>
        </div>
    </div>

    <div class="row">
        <button type="button" class="btn acciones" onclick="newWorkPart();"><i class="fas fa-plus-square"></i></button>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th scope="col"><spring:message code="forms.workPart.project" /></th>
                <th scope="col"><spring:message code="forms.workPart.operation" /></th>
                <th scope="col"><spring:message code="forms.workPart.comment" /></th>
                <th scope="col"><spring:message code="forms.workPart.duration" /></th>
                <th scope="col"><spring:message code="forms.workPart.acciones" /></th>
                </th>
            </tr>
            </thead>
            <c:set var="totalDuration" value="0"/>
            <c:forEach items="${parts}" var="part">
                <tr id="partRow${part.id}">
                    <td>${part.project.name}</td>
                    <td>${part.operation.name}</td>
                    <td>${part.comment}</td>
                    <td id="duration${part.id}" class="duration">
                            <!--${part.duration.seconds}-->
                            ${part.duration.toHours()}:${String.format("%02d", part.duration.minusHours(part.duration.toHours()).toMinutes())}
                            <c:set var="totalDuration" value="${totalDuration + part.duration.seconds}"/>
                    </td>
                    <td>
                        <button type="button" class="btn acciones" onclick="editWorkPart(${part.id});"><i class="fas fa-edit"></i></button>
                        <button type="button" class="btn acciones" onclick="deleteWorkPart(${part.id});"><i class="fas fa-trash"></i></button>
                    </td>
                </tr>
            </c:forEach>
            <tr id="partRow${part.id}">
                <td><spring:message code="forms.workPart.total" /></td>
                <td></td>
                <td></td>
                <td class="duration">
                    <c:set var="durhrint" value="${(totalDuration - (totalDuration mod 3600)) / 3600}" />
                    <fmt:formatNumber var="durhr"
                                      type="number"
                                      value="${durhrint}"
                                      maxFractionDigits="0"
                                      minIntegerDigits="2"
                    />
                    <c:set var="temp" value="${(totalDuration - (durhr *3600))}" />
                    <c:set var="durminint" value="${ (temp - (temp mod 60)) / 60 }" />
                    <fmt:formatNumber var="durmin"
                                      type="number"
                                      value="${durminint}"
                                      maxFractionDigits="0"
                                      minIntegerDigits="2"
                    />
                    ${durhr}:${durmin}

                </td>
                <td></td>
            </tr>
        </table>
        <button type="button" class="btn acciones" onclick="newWorkPart();"><i class="fas fa-plus-square"></i></button>
    </div>
    <%@ include file="/WEB-INF/views/modals/workpart-modal.jsp" %>
</div>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>
