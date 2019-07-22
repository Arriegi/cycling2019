function validateDuration(event) {
    let valido = true;
    let imputedTime = event.currentTarget.value;
    let matchImputedTime = imputedTime.match(/^(\d{1,2})(:(\d{2})){0,1}$/);
    //if (imputedTime.match(/^\d{1,2}(:\d{2}){0,1}$/)) {
    if (matchImputedTime !== null) {
        if (matchImputedTime[1] < 0) {
            valido = false;
        }
        else if (matchImputedTime[1] > 23) {
            valido = false;
        }
        else if (matchImputedTime[3] < 0) {
            valido = false;
        }
        else if (matchImputedTime[3] > 59) {
            valido = false;
        }
    }
    else {
        valido = false;
    }
    if (! valido) {
        alert(messageworkpartDurationNotValid);
        event.currentTarget.value = event.currentTarget.backupValue;
        event.currentTarget.focus();

    }
    return valido;
}

function validateDurationBudget(event) {
    let valido = true;
    let imputedTime = event.currentTarget.value;
    let matchImputedTime = imputedTime.match(/^(\d{1,4})(:(\d{2})){0,1}$/);
    //if (imputedTime.match(/^\d{1,2}(:\d{2}){0,1}$/)) {
    if (matchImputedTime !== null) {
        if (matchImputedTime[1] < 0) {
            valido = false;
        }
        else if (matchImputedTime[3] < 0) {
            valido = false;
        }
        else if (matchImputedTime[3] > 59) {
            valido = false;
        }
    }
    else {
        valido = false;
    }
    if (! valido) {
        alert(messageworkpartDurationNotValid);
        event.currentTarget.value = event.currentTarget.backupValue;
        event.currentTarget.focus();

    }
    return valido;
}

function addMinutesToDuration(elementId, minsToAdd) {
    let time = document.getElementById(elementId).value;

    if ((time === null) || (time == "")) {
        time = "0:00";
    }

    if (time.indexOf(":") == -1) {
        time = time + ":00";
    }

    function D(J) {
        return (J < 10 ? '0' : '') + J;
    };
    var piece = time.split(':');
    var mins = piece[0] * 60 + +piece[1] + +minsToAdd;

    if (mins >= 0) {
        //document.getElementById(elementId).value = D(mins % (24 * 60) / 60 | 0) + ':' + D(mins % 60);
        document.getElementById(elementId).value = D(mins / 60 | 0) + ':' + D(mins % 60);
    }
}

function roundTo(value, numberOfDecimals) {
    return Math.round(value * Math.pow(10,numberOfDecimals)) / Math.pow(10,numberOfDecimals);
}

function formatAmmount(value, numberOfDecimals) {
    return value.toFixed(numberOfDecimals);
}