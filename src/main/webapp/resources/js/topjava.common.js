function makeEditable() {
    $(".delete").click(function () {
        deleteRow($(this).parents(".table-row").attr("id"));
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    $(".edit").click(function () {
        $(this).modal();
    })

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});

    $('input:checkbox').click( function () {
        var id = $(this).parents(".table-row").attr('id');
        console.log("status changing detected")
        if (this.checked) {
            console.log("checked");
            sendStatus("checked",id);
        }
        else {
            console.log("unchecked");
            sendStatus("unchecked",id);
        }
        changeStyleOfInactive();
    });

}

function changeStyleOfInactive(){
    var parent;
    $('input:checkbox').each(function(){
        parent = $(this).parents(".table-row");
        if ($(this).checked){
            if (parent.hasClass("unselected-user")){
                parent.removeClass("unselected-user");
            }
        }
        else {
            parent.addClass("unselected-user");
        }
    });
}

function add() {
    $("#detailsForm").find(":input").val("");
    $("#editRow").modal();
}


function sendStatus(status, id) {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "changeUserStatus",
        dateType: 'JSON',
        data: ({state: status, id: id}),
        contentType: "application/json"
    }).done(function (data) {
       updateTable();
        successNoty("Status was changed");
    });
}


function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE"
    }).done(function () {
        console.log("1");
        updateTable();
        successNoty("Deleted");
    });
}


function updateTable() {
    console.log("updated")
    $.get(ajaxUrl, function (data) {
        console.log(data);
        datatableApi.clear().rows.add(data).draw();
    });
}

function save() {
    let form = $("#detailsForm");
    console.log(form.serialize());
    console.log(ajaxUrl);
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize()
    }).done(function () {
        console.log("sent");
        $("#editRow").modal("hide");
        updateTable();
        successNoty("Saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}