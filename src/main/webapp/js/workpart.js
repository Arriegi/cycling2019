function onChangeClient() {
    //alert("Proba");
    let clienteId = event.currentTarget.value;

    $('#project').empty();

    for (let iProject = 0 ; iProject < projects.length ; iProject++) {
        let addProject = true;
        if ((clienteId !== undefined) && (clienteId !== null) && (clienteId != "")) {
            if (projects[iProject].client.id != clienteId) {
                addProject = false;
            }
        }
        if (addProject) {
            $('#project').append('<option value="' + projects[iProject].id + '">' + projects[iProject].name + '</option>');
        }
    }
}