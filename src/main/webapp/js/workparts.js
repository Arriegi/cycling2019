function newWorkPart() {
    $("#id").val("");
    $("#project").val("");
    $("#operation").val("");
    $("#comment").val("");
    $("#duration").val("");
    $('.modal').modal('show');

    $("#client").prop("selectedIndex", -1);

    //$("#project").focus();
    $("#client").focus();
}

function editWorkPart(id) {
    for (let iPart = 0; iPart < parts.length ; iPart++) {
        if (parts[iPart].id == id) {
            $("#id").val(parts[iPart].id);
            $("#project").val(parts[iPart].project.id);
            $("#operation").val(parts[iPart].operation.id);
            $("#comment").val(parts[iPart].comment);
            $("#duration").val(printFormatDuration(parts[iPart].duration.seconds));
            break;
        }
    }

    $("#client").prop("selectedIndex", -1);

    $('.modal').modal('show');
    $("#duration").focus();
}

function deleteWorkPart(id) {
    if (confirm(messageworkpartDeleteConfirm)) {
        //alert("delete user "+id);
        const url = CONTEXT_PATH + "api/v1/workparts/" + id;
        $.ajax({
            type: "DELETE",
            url: url,
        })
            .done(
                function (response) {
                    $('#partRow' + id).remove();
                }
            )
            .fail(
                function () {
                    alert("deleteWorkPart:" + "unknown error.");
                }
            );
    }

}

function printFormatDuration(seconds) {
    minutes = Math.trunc(seconds / 60);
    return getFormatHHMM(minutes);
}

function getFormatHHMM(minutes) {
    let phours = Math.trunc(minutes / 60);
    let pminutes = "0" + Math.trunc(minutes % 60);
    pminutes = pminutes.substr(-2);

    return phours + ":" + pminutes;
}

function validateForm() {
    let resultado = true;

    if (resultado) {
        if (document.getElementById("project").selectedIndex == -1) {
            document.getElementById("project").focus();
            alert(messageNeedProject);
            resultado = false;
        }
    }

    if (resultado) {
        if (document.getElementById("operation").selectedIndex == -1) {
            document.getElementById("operation").focus();
            alert(messageNeedOperation);
            resultado = false;
        }
    }

    if (resultado) {
        if (document.getElementById("duration").value == "") {
            document.getElementById("duration").focus();
            alert(messageNeedDuration);
            resultado = false;
        }
    }

    /*
    if (resultado) {
        document.getElementById("formWorkPart").submit();
    }

     */
    if (! resultado) {
        event.preventDefault();
    }
}
function selectDuration(duration) {
    document.getElementById("duration").value = duration;
}