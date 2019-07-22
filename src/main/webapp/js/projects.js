function projectDelete(id) {
    if (confirm(messageprojectDeleteConfirm)) {
        //alert("delete project "+id);
        const url = CONTEXT_PATH + "api/v1/projects/" + id;
        $.ajax({
            type: "DELETE",
            url: url,
        })
            .done(
                function (response) {
                    $('#projectRow' + id).remove();
                }
            )
            .fail(
                function () {
                    alert(messageprojectNotDeletedBecauseExistWorkpart);
                }
            );
    }
}