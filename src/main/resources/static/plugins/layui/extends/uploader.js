var _default = {
    elem: "#uploader",
    accept: "images",
    exts: 'jpg|png|jpeg',
    url: "/web/oss/upload.web",
    data: {"picType": "ROOT"},
    number: 1,
    size: 20480,
    done: function (result, index, upload) { // 接口返回的数据放在了data中
        if (result.status == SUCCESS) {
            $("#img").attr("src", result.body);
            $("#img_url").val(result.body);
        } else {
            layer.msg(result.message);
        }
    },
    fail: function (index, upload) {
        layer.msg("图片上传失败");
    }
};
function uploader(options) {
    layui.use(['upload'], function () {
        var _options = $.extend(_default, options);
        return layui.upload.render(_options);
    });
}