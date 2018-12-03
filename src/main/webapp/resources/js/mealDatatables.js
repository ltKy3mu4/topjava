function filter() {
    var parent = $('#filter-node');

    var startDate = parent.find('#startDate').val();
    var endDate = parent.find("#endDate").val();
    var startTime = parent.find("#startTime").val();
    var endTime = parent.find("#endTime").val();
    console.log(startDate +" "+ endDate + " "+startTime+" "+endTime);
    console.log(({startDate:startDate, endDate:endDate, startTime:startTime, endTime:endTime}));
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        dateType : 'JSON',
        data: ({startDate:startDate, endDate:endDate, startTime:startTime, endTime:endTime}),
        contentType: "application/json"
    }).done(function (data) {
        console.log("filter");
        // $("#editRow").modal("hide");
        // updateTable();
        console.log(data);
        datatableApi.clear().rows.add(data).draw();
        successNoty("Saved");
    });

}