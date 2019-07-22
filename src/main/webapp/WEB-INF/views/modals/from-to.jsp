<%@ include file="/WEB-INF/views/include.jsp" %>
<div class="modal fade" id="modal-container-248672" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="myModalLabel">
                    <spring:message code="modal.fromToTitle" />
                </h5>
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">x</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="from"><spring:message code="modal.from" /></label>
                    <input id="from" class="form-control" type="date" value="" default="${LocalDate.now()}"/>
                </div>
                <div class="form-group">
                    <label for="to"><spring:message code="modal.to" /></label>
                    <input id="to" class="form-control" type="date" value="" default="${LocalDate.now()}"/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-bind="click: setFromTo">
                    <spring:message code="modal.aplicar" />
                </button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <spring:message code="modal.close" />
                </button>
            </div>
        </div>
    </div>
</div>