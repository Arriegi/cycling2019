<%@ page import="java.time.Duration" %>
<%@ include file="/WEB-INF/views/include.jsp" %>
<div class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><spring:message code="modal.expense.title"></spring:message></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <spring:url value="/expenses/submit" var="expenseActionUrl" />
                <form:form action="${expenseActionUrl}" modelAttribute="expense" method="post" id="formExpense" onsubmit="validateForm();">

                    <div class="form-group">
                        <label for="project"><spring:message code="forms.expense.date" /></label>
                            <!--${selectedDate}-->
                        <fmt:parseDate value="${selectedDate}" pattern="yyyy-MM-dd"
                                       var="parsedDate" type="date" />
                        <fmt:formatDate value="${parsedDate}" var="stdDatum"
                                        type="date" pattern="dd-MM-yyyy" />
                        <c:out value="${stdDatum}" />

                    </div>
                    <div class="form-group" style="display: none;">
                        <label for="id">Id</label>
                        <form:input path="id" id="id" class="form-control" type="number" />
                    </div>

                    <div class="form-group" style="display: none;">
                        <label for="user.id">User Id</label>
                        <form:input path="user.id" id="userId" class="form-control" type="number" />
                    </div>

                    <div class="form-group row">
                        <input type="hidden" id="type" value="">
                        <div class="col radio">
                            <label><input id="typeDiets" type="radio" name="type" value="1" onchange="onChangeFormatFormAsDiets();"><spring:message code="modal.expense.diets"></spring:message></label>
                        </div>
                        <div class="col radio">
                            <label><input id="typeDistance" type="radio" name="type" value="2" onchange="onchangeFormatFormAsDistance();"><spring:message code="modal.expense.distance"></spring:message></label>
                        </div>

                    </div>

                    <div class="form-group">
                        <label for="project"><spring:message code="forms.expense.project" /></label>
                        <form:select path="project.id" id="project" items="${projects}" itemValue="id" itemLabel="name" class="form-control"/>
                    </div>

                    <div class="form-group">
                        <spring:bind path="comment">
                            <label for="comment"><spring:message code="forms.expense.comment" /></label>
                            <form:input path="comment" id="comment" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                            <div class="invalid-feedback">
                                <form:errors path="comment" />
                            </div>
                        </spring:bind>
                    </div>

                    <div class="form-group" id="groupDistancia">
                        <spring:bind path="distancia">
                            <label for="distancia"><spring:message code="forms.expense.distance" /></label>
                            <form:input path="distancia" id="distancia" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" onchange="onChangeDistance();" />
                            <div class="invalid-feedback">
                                <form:errors path="distancia" />
                            </div>
                        </spring:bind>
                    </div>

                    <div class="form-group" id="groupRate">
                        <spring:bind path="rate">
                            <label for="rate"><spring:message code="forms.expense.rate" /></label>
                            <form:input path="rate" id="rate" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                            <div class="invalid-feedback">
                                <form:errors path="rate" />
                            </div>
                        </spring:bind>
                    </div>

                    <div class="form-group">
                        <spring:bind path="ammount">
                            <label for="ammount"><spring:message code="forms.expense.ammount" /></label>
                            <form:input path="ammount" id="ammount" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" onfocus="event.currentTarget.backupValue = event.currentTarget.value;" onChange="validateAmmount(event);" />
                            <div class="invalid-feedback">
                                <form:errors path="ammount" />
                            </div>
                        </spring:bind>
                    </div>

                    <div class="form-group" style="display: none;">
                        <label for="date"><spring:message code="forms.expense.date" /></label>
                        <form:input path="date" id="date" class="form-control ${status.error ? 'is-invalid' : ''}" type="date" value=""/>
                        <div class="invalid-feedback">
                            <form:errors path="date" />
                        </div>
                    </div>

                    <!--button type="button" class="btn btn-primary" onclick="validateForm();"><spring:message code="forms.guardar" /></button-->
                    <button type="submit" class="btn btn-primary" accesskey="<spring:message code="modal.accesskey.save" />"><spring:message code="modal.save" /></button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="modal.close" /></button>
                </form:form>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>