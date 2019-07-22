function operationDelete(id) {
    if (confirm(messageoperationDeleteConfirm)) {
        //alert("delete operation "+id);
        const url = CONTEXT_PATH + "api/v1/operations/" + id;
        $.ajax({
            type: "DELETE",
            url: url,
        })
            .done(
                function (response) {
                    //window.location.reload();
                    $('#operationRow' + id).remove();
                }
            )
            .fail(
                function () {
                    alert(messageoperationNotDeletedBecauseExistWorkpart);
                }
            );
    }
}
