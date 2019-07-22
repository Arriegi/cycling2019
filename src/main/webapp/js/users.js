function userDelete(id) {
    if (confirm(messageuserDeleteConfirm)) {
        //alert("delete user "+id);
        const url = CONTEXT_PATH + "api/v1/users/" + id;
        $.ajax({
            type: "DELETE",
            url: url,
        })
            .done(
                function (response) {
                    $('#userRow' + id).remove();
                }
            )
            .fail(
                function () {
                    alert(messageuserNotDeletedBecauseExistWorkpart);
                }
            );
    }
}
