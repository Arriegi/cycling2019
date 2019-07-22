
function projectOperationBudgetDelete(id) {
    if (confirm(messageprojectOperationBudgetDeleteConfirm)) {
        //alert("delete project "+id);
        const url = CONTEXT_PATH + "api/v1/projectoperationbudget/" + id;
        $.ajax({
            type: "DELETE",
            url: url,
        })
            .done(
                function (response) {
                    $('#operationsBudgetRow' + id).remove();
                    window.location.reload();
                }
            )
            .fail(
                function () {
                    alert("projectOperationBudget in project.js: unknown error.");
                }
            );
    }
}

$(document).ready(
    function () {
        if (newProject == "S") {
            $("#budgetOperations").hide();
        }
    }
);
