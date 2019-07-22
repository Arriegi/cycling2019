$(document).ready(
  function () {
      getDistanceRate();
  }
);

let generalConfigurationDistanceRate = 0;

function getDistanceRate() {

    for (let iConf = 0 ; iConf < generalConfigurations.length ; iConf++) {
        if (generalConfigurations[iConf].idConfiguration == "TASA_KILOMETRAJE") {
            generalConfigurationDistanceRate = generalConfigurations[iConf].value;
            break;
        }
    }
}

function newExpense() {
    formatFormAsDiets();

    $("#type").val(typeDiets);

    $("#id").val("");
    $("#project").val("");
    $("#comment").val("");
    $("#ammount").val("");
    $("#distancia").val("");
    $("#rate").val("");
    $('.modal').modal('show');
    //$("#project").focus();
    $("#typeDiets").focus();

}

function formatFormAsDiets() {
    $("#typeDiets").prop("checked", true);
    $("#groupDistancia").hide();
    $("#groupRate").hide();
    $("#ammount").prop("readonly", false);
}

function onChangeFormatFormAsDiets() {
    formatFormAsDiets();

    $("#type").val(typeDiets);

    $("#ammount").val("");
}

function formatFormAsDistance() {
    $("#typeDistance").prop("checked", true);
    $("#groupDistancia").show();
    $("#groupRate").show();
    $("#rate").prop("readonly", true);
    $("#ammount").prop("readonly", true);
}

function onchangeFormatFormAsDistance() {
    formatFormAsDistance();

    $("#type").val(typeDistance);

    $("#rate").val(generalConfigurationDistanceRate);

    $("#distancia").val("");

    $("#ammount").val("");
}

function editExpense(id) {
    for (let iExpense = 0; iExpense < expenses.length ; iExpense++) {
        if (expenses[iExpense].id == id) {
            if (expenses[iExpense].type == typeDistance) {
                formatFormAsDistance();
                $("#distancia").val(expenses[iExpense].distancia);
                $("#rate").val(expenses[iExpense].rate);
            }
            else {
                formatFormAsDiets();
            }
            $("#id").val(expenses[iExpense].id);
            $("#project").val(expenses[iExpense].project.id);
            $("#comment").val(expenses[iExpense].comment);
            $("#ammount").val(printFormatAmmount(expenses[iExpense].ammount));
            break;
        }
    }
    $('.modal').modal('show');
    $("#ammount").focus();
}

function deleteExpense(id) {
    if (confirm(messageexpenseDeleteConfirm)) {
        //alert("delete user "+id);
        const url = CONTEXT_PATH + "api/v1/expenses/" + id;
        $.ajax({
            type: "DELETE",
            url: url,
        })
            .done(
                function (response) {
                    $('#expenseRow' + id).remove();
                }
            )
            .fail(
                function () {
                    alert("deleteExpense:" + "unknown error.");
                }
            );
    }

}

function printFormatAmmount(expense) {
    return expense;
}

function validateAmmount(event) {
    let valido = true;
    let imputedAmmount = event.currentTarget.value;
    let matchImputedAmmount = imputedAmmount.match(/^(\d+)(.(\d{1,2})){0,1}$/);
    if (matchImputedAmmount == null) {
        valido = false;
    }
    if (! valido) {
        alert(messageexpenseAmmountNotValid);
        event.currentTarget.value = event.currentTarget.backupValue;
        event.currentTarget.focus();

    }
    return valido;
}

function validateForm() {
    let resultado = true;

    let type = document.getElementById("type").value;

    if (resultado) {
        if (document.getElementById("project").selectedIndex == -1) {
            document.getElementById("project").focus();
            alert(messageNeedProject);
            resultado = false;
        }
    }

    if (resultado) {
        if (document.getElementById("comment").value == "") {
            document.getElementById("comment").focus();
            alert(messageNeedComment);
            resultado = false;
        }
    }

    if (type == typeDistance) {
        if (document.getElementById("distancia").value == "") {
            document.getElementById("distancia").focus();
            alert(messageNeedDistancia);
            resultado = false;
        }

    }

    if (resultado) {
        if (document.getElementById("ammount").value == "") {
            document.getElementById("ammount").focus();
            alert(messageNeedAmmount);
            resultado = false;
        }
    }

    /*
    if (resultado) {
        document.getElementById("formExpense").submit();
    }

     */
    if (! resultado) {
        event.preventDefault();
    }
}

function onChangeDistance() {

    $("#ammount").val($("#distancia").val() * $("#rate").val());
}