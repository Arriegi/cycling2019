function clientDelete(id) {
    if (confirm(messageclientDeleteConfirm)) {
        //alert("delete project "+id);
        const url = CONTEXT_PATH + "api/v1/clients/" + id;
        $.ajax({
            type: "DELETE",
            url: url,
        })
            .done(
                function (response) {
                    $('#clientRow' + id).remove();
                }
            )
            .fail(
                function () {
                    alert(messageclientNotDeletedBecauseExistProjects);
                }
            );
    }
}
