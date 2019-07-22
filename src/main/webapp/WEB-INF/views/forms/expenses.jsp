<%@ page import="eus.jarriaga.cycling.business.entities.Expense" %>
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
<script src="${pageContext.request.contextPath}/js/expenses.js"></script>
<script src="${pageContext.request.contextPath}/js/expense.js"></script>

<script type="text/javascript">
    let selectedDate = "${selectedDate}";
    let expenses = <c:out value="${JSONexpenses}" escapeXml="false"/>;
    let generalConfigurations = <c:out value="${JSONgeneralConfigurations}" escapeXml="false"/>;

    let messageexpenseDeleteConfirm = "<spring:message code="forms.expense.expenseDeleteConfirm" />";
    let messageexpenseAmmountNotValid = "<spring:message code="forms.expense.expenseAmmountNotValid" />";
    let messageNeedProject = "<spring:message code="forms.expense.needProject" />";
    let messageNeedComment = "<spring:message code="forms.expense.needComment" />";
    let messageNeedDistancia = "<spring:message code="forms.expense.needDistancia" />";
    let messageNeedAmmount = "<spring:message code="forms.expense.needAmmount" />";


    $().ready(
            function () {
            }
    );
</script>

<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-4">
            <div class="btn-group" role="group" aria-label="Basic example">
                <a class="btn btn-default" href="${pageContext.request.contextPath}/expenses/${userId}?date=${previousDate}"><i class="fa fa-chevron-left"></i></a>
                <a class="btn btn-default" href="${pageContext.request.contextPath}/expenses/${userId}"><i><spring:message code="forms.expense.today" /></i></a>
                <a class="btn btn-default" href="${pageContext.request.contextPath}/expenses/${userId}?date=${nextDate}"><i class="fa fa-chevron-right"></i></a>
            </div>
        </div>
        <div class="col-4">
            <h2 class="report"><!--<c:out value="${selectedDate}" />-->
                <spring:message code="forms.expense.title" />
                <fmt:parseDate value="${selectedDate}" pattern="yyyy-MM-dd"
                               var="parsedDate" type="date" />
                <fmt:formatDate value="${parsedDate}" var="stdDatum"
                                type="date" pattern="dd-MM-yyyy" />
                <c:out value="${stdDatum}" />
            </h2>
        </div>
    </div>

    <div class="row">
        <button type="button" class="btn acciones" onclick="newExpense();"><i class="fas fa-plus-square"></i></button>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th scope="col"><spring:message code="forms.expense.type" /></th>
                <th scope="col"><spring:message code="forms.expense.project" /></th>
                <th scope="col"><spring:message code="forms.expense.comment" /></th>
                <th scope="col"><spring:message code="forms.expense.distance" /></th>
                <th scope="col"><spring:message code="forms.expense.ammount" /></th>
                <th scope="col"><spring:message code="forms.expense.acciones" /></th>
                </th>
            </tr>
            </thead>
            <c:forEach items="${expenses}" var="expense">
                <tr id="expenseRow${expense.id}">
                    <td>
                        <c:choose>
                            <c:when test="${expense.type==2}">
                                Kilometraje
                            </c:when>
                            <c:otherwise>
                                Dietas
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${expense.project.name}</td>
                    <td>${expense.comment}</td>
                    <td class="numerico">
                        <c:choose>
                            <c:when test="${expense.type==2}">
                                ${expense.distancia}
                            </c:when>
                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="importe" id="ammount${expense.id}">
                        <c:set var="calculateAmount" value="${expense.ammount}"/>
                        <fmt:formatNumber var="formatAmount"
                                          type="number"
                                          value="${calculateAmount}"
                                          minFractionDigits="2"
                                          maxFractionDigits="2"
                        />
                        ${formatAmount}
                        <!--${expense.ammount}-->
                    </td>
                    <td nowrap>
                        <button type="button" class="btn acciones" onclick="editExpense(${expense.id});"><i class="fas fa-edit"></i></button>
                        <button type="button" class="btn acciones" onclick="deleteExpense(${expense.id});"><i class="fas fa-trash"></i></button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <button type="button" class="btn acciones" onclick="newExpense();"><i class="fas fa-plus-square"></i></button>
    </div>
    <%@ include file="/WEB-INF/views/modals/expense-modal.jsp" %>
</div>
<%@ include file="/WEB-INF/views/foot.jsp" %>
</body>
</html>
