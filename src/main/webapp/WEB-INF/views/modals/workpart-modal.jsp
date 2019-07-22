<%@ page import="java.time.Duration" %>
<%@ include file="/WEB-INF/views/include.jsp" %>
<div class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><spring:message code="modal.workpart.title"></spring:message></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <spring:url value="/workparts/submit" var="workpartActionUrl" />
                <form:form action="${workpartActionUrl}" modelAttribute="workpart" method="post" id="formWorkPart" onsubmit="validateForm();">

                    <div class="form-group">
                        <label for="project"><spring:message code="forms.workPart.date" /></label>
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

                    <div class="form-group">
                        <label for="project"><spring:message code="forms.workPart.filterProjectByClient" /></label>
                        <form:select path="" id="client" items="${clients}" itemValue="id" itemLabel="name" class="form-control" onChange="onChangeClient(event);"/>
                    </div>

                    <div class="form-group">
                        <label for="project"><spring:message code="forms.workPart.project" /></label>
                        <form:select path="project.id" id="project" items="${projects}" itemValue="id" itemLabel="name" class="form-control"/>
                    </div>

                    <div class="form-group">
                        <label for="operation"><spring:message code="forms.workPart.operation" /></label>
                        <form:select path="operation.id" id="operation" items="${operations}" itemValue="id" itemLabel="name" class="form-control"/>
                    </div>

                    <div class="form-group">
                        <spring:bind path="comment">
                            <label for="comment"><spring:message code="forms.workPart.comment" /></label>
                            <form:input path="comment" id="comment" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" />
                            <div class="invalid-feedback">
                                <form:errors path="comment" />
                            </div>
                        </spring:bind>
                    </div>

                    <div class="form-group">
                        <spring:bind path="duration">
                            <label for="duration"><spring:message code="forms.workPart.duration" /></label>
                            <div class="row">
                                <div class="col">
                                    <form:input path="duration" id="duration" class="form-control ${status.error ? 'is-invalid' : ''}" type="text" onfocus="event.currentTarget.backupValue = event.currentTarget.value;" onChange="validateDuration(event);" />
                                </div>
                                <div class="col-2">
                                    <div class="row">
                                        <div class="col">
                                            <button type="button" class="btn btn-light btn-sm" onclick="addMinutesToDuration('duration', 30);"><i class="far fa-plus-square"></i></button>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col">
                                            <button type="button" class="btn btn-light btn-sm" onclick="addMinutesToDuration('duration', -30);"><i class="far fa-minus-square"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="invalid-feedback">
                                <form:errors path="duration" />
                            </div>
                        </spring:bind>
                    </div>

                    <div class="form-group">
                        <div class="row">
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('00:30');">00:30</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('01:00');">01:00</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('01:30');">01:30</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('02:00');">02:00</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('02:30');">02:30</button></div>
                        </div>
                        <div class="row">
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('03:00');">03:00</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('03:30');">03:30</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('04:00');">04:00</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('04:30');">04:30</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('05:00');">05:00</button></div>
                        </div>
                        <div class="row">
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('05:30');">05:30</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('06:00');">06:00</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('06:30');">06:30</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('07:00');">07:00</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('07:30');">07:30</button></div>
                        </div>
                        <div class="row">
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('08:00');">08:00</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('08:30');">08:30</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('09:00');">09:00</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('09:30');">09:30</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('10:00');">10:00</button></div>
                        </div>
                        <div class="row">
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('10:30');">10:30</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('10:00');">11:00</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('11:30');">11:30</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('12:00');">12:00</button></div>
                            <div class="col-2 col-sm-2 m-1"><button type="button" class="btn btn-md acciones" onclick="selectDuration('12:30');">12:30</button></div>
                        </div>
                    </div>

                    <div class="form-group" style="display: none;">
                        <label for="date"><spring:message code="forms.workPart.date" /></label>
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
                <!--button type="button" class="btn btn-primary">Save changes</button-->
                <!--button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button-->
            </div>
        </div>
    </div>
</div>