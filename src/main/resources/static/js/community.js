function post() {
    var questionId = $("#question-id").val();
    var content = $("#comment-content").val();
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("输入信息不能为空");
        return;
    }
    $.ajax({
        contentType: "application/json",
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                location.reload();
                $("#comment-section").hide();
            } else {
                alert($(response.message()));
            }
        },
        dataType: "json"
    })
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content)
}


function collaspseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //得到评论状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
    } else {

        //展开二级评论
        comments.addClass("in");
        //标记评论展开状态
        e.setAttribute("data-collapse", "in");
    }
}