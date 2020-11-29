function post() {
    var questionId = $("#question-id").val();
    var content = $("#comment-content").val();
    $.ajax({
        contentType: "application/json",
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                $("#comment-section").hide();
            } else {
                alert($(response.message()));
            }
            console.log(response);
        },
        dataType: "json"
    })
}