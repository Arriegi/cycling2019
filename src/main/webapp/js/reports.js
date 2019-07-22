function ReportsViewModel(parts, current, currentNumber, type, from, to) {
    let self = this;
    self.current = ko.observable(current);
    self.TYPE_MONTH = "reportMonth";
    self.TYPE_YEAR = "reportYear";
    self.TYPE_CUSTOM = "reportCustom";
    self.currentNumber = ko.observable(Number(currentNumber));
    self.type = ko.observable(type);
    self.from = ko.observable(from);
    self.to = ko.observable(to);
    self.parts = ko.observableArray(parts);
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
    self.totalHours = ko.computed(function(){
        return timeConvert(self.parts().reduceRight(function(prev, current){
            return prev + current.duration;
        },0));

        /*
        return self.formatSeconds(self.parts().reduceRight(function(prev, current){
            return prev + current.duration;
        },0));

         */
    });
    self.clients = ko.computed(function(){
        let result = self.parts().reduce(function(prev, current){
            const resultIndex = prev.map(function(elem){return elem.client.id;}).indexOf(current.project.client.id);
            if (resultIndex != -1) {
                prev[resultIndex].duration += current.duration;
            } else {
                let orderIndex = -1;
                for (let iArr = 0 ; iArr < prev.length ; iArr++) {
                    if (prev[iArr].client.name > current.project.client.name) {
                        orderIndex = iArr;
                        prev.splice(iArr, 0,
                            {
                                duration: current.duration,
                                client: current.project.client
                            }
                            );
                        break;
                    }
                }
                if (orderIndex == -1) {
                    prev.push({
                        duration: current.duration,
                        client: current.project.client
                    });
                }
            }
            return prev;
        },[]);
        //return result.map((elem) => { return {duration: self.formatSeconds(elem.duration), client: elem.client}});
        return result.map((elem) => { return {duration: timeConvert(elem.duration), client: elem.client}});
    });
    self.projects = ko.computed(function(){
        let result = self.parts().reduce(function(prev, current){
            const resultIndex = prev.map(function(elem){return elem.project.id;}).indexOf(current.project.id);
            if (resultIndex != -1) {
                prev[resultIndex].duration += current.duration;
            } else {
                let orderIndex = -1;
                for (let iArr = 0 ; iArr < prev.length ; iArr++) {
                    if (prev[iArr].project.name > current.project.name) {
                        orderIndex = iArr;
                        prev.splice(iArr, 0,
                            {
                                duration: current.duration,
                                project: current.project
                            }
                        );
                        break;
                    }
                }
                if (orderIndex == -1) {
                    prev.push({
                        duration: current.duration,
                        project: current.project
                    });
                }
            }
            return prev;
        },[]);
        //return result.map((elem) => { return {duration: self.formatSeconds(elem.duration), project: elem.project}});
        return result.map((elem) => { return {duration: timeConvert(elem.duration), project: elem.project}});
    });
    self.users = ko.computed(function(){
        let result = self.parts().reduce(function(prev, current){
            const resultIndex = prev.map(function(elem){return elem.user.id;}).indexOf(current.user.id);
            if (resultIndex != -1) {
                prev[resultIndex].duration += current.duration;
            } else {
                let orderIndex = -1;
                for (let iArr = 0 ; iArr < prev.length ; iArr++) {
                    if (prev[iArr].user.name > current.user.name) {
                        orderIndex = iArr;
                        prev.splice(iArr, 0,
                            {
                                duration: current.duration,
                                user: current.user
                            }
                        );
                        break;
                    }
                }
                if (orderIndex == -1) {
                    prev.push({
                        duration: current.duration,
                        user: current.user
                    });
                }
            }
            return prev;
        },[]);
        //return result.map((elem) => { return {duration: self.formatSeconds(elem.duration), user: elem.user}});
        return result.map((elem) => { return {duration: timeConvert(elem.duration), user: elem.user}});
    });
    self.operations = ko.computed(function(){
        let result = self.parts().reduce(function(prev, current){
            const resultIndex = prev.map(function(elem){return elem.operation.id;}).indexOf(current.operation.id);
            if (resultIndex !== -1) {
                prev[resultIndex].duration += current.duration;
            } else {
                let orderIndex = -1;
                for (let iArr = 0 ; iArr < prev.length ; iArr++) {
                    if (prev[iArr].operation.name > current.operation.name) {
                        orderIndex = iArr;
                        prev.splice(iArr, 0,
                            {
                                duration: current.duration,
                                operation: current.operation
                            }
                        );
                        break;
                    }
                }
                if (orderIndex == -1) {
                    prev.push({
                        duration: current.duration,
                        operation: current.operation
                    });
                }
            }
            return prev;
        },[]);
        //return result.map((elem) => { return {duration: self.formatSeconds(elem.duration), operation: elem.operation}});
        return result.map((elem) => { return {duration: timeConvert(elem.duration), operation: elem.operation}});
    });
    self.informePOO = ko.computed(function(){
        let result = self.parts().reduce(function(prev, current){
            const resultIndex = prev.map(
                function(elem){
                    return elem.project.id + "/" + elem.operation.id + "/" + elem.user.id;
                }
                ).indexOf(current.project.id + "/" + current.operation.id + "/" + current.user.id);
            if (resultIndex != -1) {
                prev[resultIndex].duration += current.duration;
            } else {
                let orderIndex = -1;
                for (let iArr = 0 ; iArr < prev.length ; iArr++) {
                    if (prev[iArr].project.name > current.project.name) {
                        orderIndex = iArr;
                        prev.splice(iArr, 0,
                            {
                                duration: current.duration,
                                project: current.project,
                                operation: current.operation,
                                user: current.user
                            }
                        );
                        break;
                    }
                    else if ((prev[iArr].project.name == current.project.name) && (prev[iArr].operation.name > current.operation.name)) {
                        orderIndex = iArr;
                        prev.splice(iArr, 0,
                            {
                                duration: current.duration,
                                project: current.project,
                                operation: current.operation,
                                user: current.user
                            }
                        );
                        break;
                    }
                    else if ((prev[iArr].project.name == current.project.name) &&(prev[iArr].operation.name == current.operation.name) && (prev[iArr].user.name > current.user.name)) {
                        orderIndex = iArr;
                        prev.splice(iArr, 0,
                            {
                                duration: current.duration,
                                project: current.project,
                                operation: current.operation,
                                user: current.user
                            }
                        );
                        break;
                    }
                }
                if (orderIndex == -1) {
                    prev.push({
                        duration: current.duration,
                        project: current.project,
                        operation: current.operation,
                        user: current.user
                    });
                }
            }
            return prev;
        },[]);
        //return result.map((elem) => { return {duration: self.formatSeconds(elem.duration), project: elem.project}});
        return result.map((elem) => { return {duration: timeConvert(elem.duration), project: elem.project, operation: elem.operation, user: elem.user}});

    });
    self.clientsVisible = ko.observable(true);
    self.projectsVisible = ko.observable(false);
    self.usersVisible = ko.observable(false);
    self.operationsVisible = ko.observable(false);
    self.informePOOVisible = ko.observable(false);

    self.visibleParts = ko.computed(function(){
        if (self.clientsVisible()) return self.clients();
        if (self.projectsVisible()) return self.projects();
        if (self.usersVisible()) return self.users();
        if (self.operationsVisible()) return self.operations();
        return self.informePOOVisible();
    });
    self.showClients = function() {
        self.operationsVisible(false);
        self.usersVisible(false);
        self.projectsVisible(false);
        self.clientsVisible(true);
        self.informePOOVisible(false);
    };
    self.showProjects = function() {
        self.operationsVisible(false);
        self.usersVisible(false);
        self.projectsVisible(true);
        self.clientsVisible(false);
        self.informePOOVisible(false);
    };
    self.showUsers = function() {
        self.operationsVisible(false);
        self.usersVisible(true);
        self.projectsVisible(false);
        self.clientsVisible(false);
        self.informePOOVisible(false);
    };
    self.showOperations = function() {
        self.operationsVisible(true);
        self.usersVisible(false);
        self.projectsVisible(false);
        self.clientsVisible(false);
        self.informePOOVisible(false);
    };
    self.showInfomePOO = function() {
        self.operationsVisible(false);
        self.usersVisible(false);
        self.projectsVisible(false);
        self.clientsVisible(false);
        self.informePOOVisible(true);
    };
}

function timeConvert(n) {
    let num = n;
    let hours = (num / 3600);
    let rhours = Math.floor(hours);
    let minutes = (hours - rhours) * 60;
    let rminutes = Math.round(minutes);

    let composeTime = "";

    if (rhours < 10) {
        composeTime += "0" + rhours;
    }
    else {
        composeTime += rhours;
    }

    composeTime += ":";

    composeTime += ("0" + rminutes).slice(-2);

    return composeTime;
}

//$.ajax(CONTEXT_PATH + "api/v1/workparts/from/");

const reportsVM = new ReportsViewModel(parts, current, currentNumber, type, from, to);
ko.applyBindings(reportsVM,jQuery(".parts")[0]);


switch(type) {
    case "reportCustom":
        let urlParams = new URLSearchParams(window.location.search);
        const from = urlParams.get("from");
        const to = urlParams.get("to");
        $(".custom-select").append('<option value="realRepotCustom">' + printDate(from)  + ' / ' + printDate(to) + '</option>');
        $(".custom-select option[value=realRepotCustom]").prop("selected",true);
        break;
    case "reportYear":
        $(".custom-select option[value=reportYear]").prop("selected",true);
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
$(".custom-select").on("focus", function () {
    savCustomSelectValue = $(".custom-select").val();
});

$(".custom-select").on("change",function(){
    const type = $(this).find("option:selected").attr("value");
    if ((type != "reportCustom") && (type != "realRepotCustom")) {
        window.location.search = "?type=" + type;
    }
    else if (type == "realRepotCustom") {
    }
    else {
        $("#modal-container-248672").modal("show");
        if($("#id option[value='realRepotCustom']").length != 0) {
            $(".custom-select option[value=realRepotCustom]").prop("selected",true);
        }
        else {
            $(".custom-select option[value="+savCustomSelectValue+"]").prop("selected", true);
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


function downloadExcel() {
    //alert("downloadExcel");
    let urlParams = new URLSearchParams(window.location.search);

    let type = urlParams.get("type");
    let current = urlParams.get("current");
    let year = urlParams.get("year");
    let from = urlParams.get("from");
    let to = urlParams.get("to");

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

    let url = CONTEXT_PATH + "api/v1/reports/download/excel" + "?" +  newUrlParams.toString();


    document.getElementById('myDownloadFile').src = url;
}
