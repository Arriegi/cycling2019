function ReportsViewModel(expenses, current, currentNumber, type, from, to, userId, projectId) {
    let self = this;
    self.current = ko.observable(current);
    self.TYPE_MONTH = "reportMonth";
    self.TYPE_YEAR = "reportYear";
    self.TYPE_CUSTOM = "reportCustom";
    self.currentNumber = ko.observable(Number(currentNumber));
    self.type = ko.observable(type);
    self.userId = ko.observable(userId);
    self.projectId = ko.observable(projectId);
    self.from = ko.observable(from);
    self.to = ko.observable(to);
    self.expenses = ko.observableArray(expenses);
    self.previous = function(data,event) {
        event.preventDefault();
        let urlParams = new URLSearchParams(window.location.search);
        if (self.currentNumber() != 1) {
            urlParams.set("current",(self.currentNumber()-1).toString());
        } else {
            urlParams.set("current","12");
            //urlParams.set("year",(new Date().getFullYear()-1).toString());
            urlParams.set("year",urlParams.has("year") ? Number(urlParams.get("year"))-1 : (new Date().getFullYear()-1).toString());
        }
        window.location.search = "?" + urlParams.toString();
    };
    self.next = function(data,event) {
        event.preventDefault();
        let urlParams = new URLSearchParams(window.location.search);
        if (self.currentNumber() != 12) {
            urlParams.set("current",(self.currentNumber()+1).toString());
        } else {
            urlParams.set("current","1");
            urlParams.set("year",urlParams.has("year") ? Number(urlParams.get("year"))+1 : (new Date().getFullYear()+1).toString());
        }
        window.location.search = "?" + urlParams.toString();
    };
    self.setFromTo = function() {
        let urlParams = new URLSearchParams(window.location.search);
        const from = $("#from").val();
        const to = $("#to").val();
        urlParams.set("from",from);
        urlParams.set("to",to);
        urlParams.set("type","reportCustom");
        window.location.search = "?" + urlParams.toString();
    };
    self.formatSeconds = function(seconds) {
        return new Date(seconds * 1000).toISOString().substr(11,5)
    }
    self.totalAmmount = ko.computed(function(){
        return roundTo(self.expenses().reduceRight(function(prev, current){
            return prev + current.ammount;
        },0),2);
        /*
        return self.expenses().reduceRight(function(prev, current){
            return prev + current.ammount;
        },0);
        */
    });
    self.informeExp = ko.computed(function(){
        let result = self.expenses().reduce(function(prev, current){
            const resultIndex = prev.map(
                function(elem){
                    return elem.id;
                }
                ).indexOf(current.id);
            if (resultIndex != -1) {
                prev[resultIndex].ammount += current.ammount;
            } else {
                    prev.push({
                        ammount: current.ammount,
                        project: current.project,
                        user: current.user,
                        type: current.type,
                        comment: current.comment,
                        distancia: current.distancia,
                        date: current.date
                    });
            }
            return prev;
        },[]);
        //return result.map((elem) => { return {duration: self.formatSeconds(elem.duration), project: elem.project}});
        return result.map((elem) => { return {date: printDate(elem.date), project: elem.project, user: elem.user, type: getTypeDescription(elem.type), comment: elem.comment, distancia: elem.distancia, ammount: formatAmmount(elem.ammount,2)}});

    });
    self.informeExpVisible = ko.observable(true);

    self.visibleexpenses = ko.computed(function(){
        if (self.informeExpVisible()) return self.informeExp();
        return null;
    });
    self.showInfomeExp = function() {
        self.informeExpVisible(true);
    };
}

const reportsVM = new ReportsViewModel(expenses, current, currentNumber, type, from, to, filterUserId, filterProjectId);
ko.applyBindings(reportsVM,jQuery(".expenses")[0]);

//////////////////////////////////////////////////////////
// Type filter
//////////////////////////////////////////////////////////

switch(type) {
    case "reportCustom":
        let urlParams = new URLSearchParams(window.location.search);
        const from = urlParams.get("from");
        const to = urlParams.get("to");
        $("#typeFindFilter").append('<option value="realRepotCustom">' + printDate(from)  + ' / ' + printDate(to) + '</option>');
        $("#typeFindFilter option[value=realRepotCustom]").prop("selected",true);
        break;
    case "reportYear":
        $("#typeFindFilter option[value=reportYear]").prop("selected",true);
        break;
    default:
}

function printDate(date) {
    let resultado;
    let miDate = new Date(date);
    resultado = zDateFormat(miDate.getDate()) + "-" + zDateFormat((miDate.getMonth()+1)) + "-" + miDate.getFullYear();
    return resultado;
}

function zDateFormat(val) {
    let tmpVal = ("0" + val);
    tmpVal = tmpVal.substr(tmpVal.length-2);
    return tmpVal
}

let savCustomSelectValue = null;
$("#typeFindFilter").on("focus", function () {
    savCustomSelectValue = $("#typeFindFilter").val();
});

$("#typeFindFilter").on("change",function(){
    const type = $(this).find("option:selected").attr("value");
    const filterUserId = $("#userFilter").find("option:selected").attr("value");
    const filterProjectId = $("#projectFilter").find("option:selected").attr("value");
    if ((type != "reportCustom") && (type != "realRepotCustom")) {
        window.location.search = "?type=" + type + "&filterUserId=" + filterUserId + "&filterProjectId=" + filterProjectId;
    }
    else if (type == "realRepotCustom") {
    }
    else {
        $("#modal-container-248672").modal("show");
        if($("#id option[value='realRepotCustom']").length != 0) {
            $("#typeFindFilter option[value=realRepotCustom]").prop("selected",true);
        }
        else {
            $("#typeFindFilter option[value="+savCustomSelectValue+"]").prop("selected", true);
        }

        let urlParams = new URLSearchParams(window.location.search);

        let from = urlParams.get("from")
        if ((from !== undefined) && (from !== null) ) {
            $("#from").val(from);
        }

        let to = urlParams.get("to")
        if ((to !== undefined) && (to !== null) ) {
            $("#to").val(to);
        }


        $("#from").focus();
    }
});


$("#modal-container-248672").on('shown.bs.modal', function () {
        $('#from').focus();
    }
);

console.log("JON");

//////////////////////////////////////////////////////////
// Type user
//////////////////////////////////////////////////////////
if ((filterUserId !== undefined) && (filterUserId !== null) && (filterUserId !== "")) {
    $("#userFilter option[value="+filterUserId+"]").prop("selected", true)
}

$("#userFilter").on("change",function(){
        //const filterUserId = $(this).find("option:selected").attr("value");
        const type = $("#typeFindFilter").find("option:selected").attr("value");
        const filterUserId = $("#userFilter").find("option:selected").attr("value");
        const filterProjectId = $("#projectFilter").find("option:selected").attr("value");
        window.location.search = "?type="+type+"&filterUserId=" + filterUserId+"&filterProjectId=" + filterProjectId;
}
);

//////////////////////////////////////////////////////////
// Type project
//////////////////////////////////////////////////////////
if ((filterProjectId !== undefined) && (filterProjectId !== null) && (filterProjectId !== "")) {
    $("#projectFilter option[value="+filterProjectId+"]").prop("selected", true)
}

$("#projectFilter").on("change",function(){
        const type = $("#typeFindFilter").find("option:selected").attr("value");
        const filterUserId = $("#userFilter").find("option:selected").attr("value");
        const filterProjectId = $("#projectFilter").find("option:selected").attr("value");
        window.location.search = "?type="+type+"&filterUserId=" + filterUserId+"&filterProjectId=" + filterProjectId;
    }
);

//////////////////////////////////////////////////////////
// Excel
//////////////////////////////////////////////////////////
function downloadExcel() {
    //alert("downloadExcel");
    let urlParams = new URLSearchParams(window.location.search);

    let type = urlParams.get("type");
    let current = urlParams.get("current");
    let year = urlParams.get("year");
    let from = urlParams.get("from");
    let to = urlParams.get("to");
    let filterUserId = urlParams.get("filterUserId");
    let filterProjectId = urlParams.get("filterProjectId");

    let newUrlParams = new URLSearchParams();
    if (type !== null) {
        newUrlParams.append("type", type)
    }
    if (current !== null) {
        newUrlParams.append("current", current)
    }
    if (year !== null) {
        newUrlParams.append("year", year)
    }
    if (from !== null) {
        newUrlParams.append("from", from)
    }
    if (to !== null) {
        newUrlParams.append("to", to)
    }
    if (filterUserId !== null) {
        newUrlParams.append("filterUserId", filterUserId)
    }
    if (filterProjectId !== null) {
        newUrlParams.append("filterProjectId", filterProjectId)
    }

    let url = CONTEXT_PATH + "api/v1/reports/download/excelExpense" + "?" +  newUrlParams.toString();


    document.getElementById('myDownloadFile').src = url;
}