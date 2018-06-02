<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>数据同步</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="/plugins/layui/css/layui.css">
</head>
<body>
<form class="layui-form" style="margin: 15px;">
    <div class="layui-form-item">
        <label class="layui-form-label">源数据</label>
        <div class="layui-input-inline">
            <select name="from">
                <#list fromDbs as db>
                    <option value="${db}">${db}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">目标数据源</label>
        <div class="layui-input-inline">
            <select name="to">
                <#list toDbs as db>
                    <option value="${db}">${db}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">数据库</label>
        <div class="layui-input-inline">
            <select name="db">
                <#list databases as db>
                    <option value="${db}">${db}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">授权码</label>
            <div class="layui-input-inline">
                <input id="token" name="token" autocomplete="off" lay-verify="required" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="sync_form">开始同步</button>
        </div>
    </div>
</form>
<script src="/plugins/jquery/jquery-3.1.1.min.js"></script>
<script src="/plugins/layui/layui.js"></script>
<script type="text/javascript">
    var timer;
    layui.use(['element', 'form'], function () {
        var form = layui.form;
        form.on('submit(sync_form)', function (data) {
            layer.confirm("数据同步是一个耗时操作，请耐心等待", {icon: 7, title: "系统提示"}, function(index){
                layer.close(index);
                layer.load(4);
                $.ajax({
                    url: '/sync',
                    type: 'POST',
                    data: data.field,
                    headers: {
                        "Authorization": window.btoa(data.field.token)
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.status === 200) {
                            timer = setInterval(function(){
                                getResult(result.body);
                            }, 2000 + Math.random() * 1000);
                        } else {//请求失败
                            layer.closeAll();
                            layer.msg(result.message, {
                                icon: 5,
                                time: 1000
                            });
                        }
                    }
                });
            });
            return false;
        });
    });

    function getResult(sync) {
        $.ajax({
            url: '/record/' + sync,
            type: 'GET',
            dataType: 'json',
            success: function (result) {
                if (result.status === 200) {
                    if (result.body.status == "S") {
                        clearInterval(timer);
                        layer.closeAll();
                        layer.msg("同步成功");
                    } else if (result.body.status == "F") {
                        clearInterval(timer);
                        layer.closeAll();
                        layer.msg("同步失败，请联系管理员处理");
                    }
                } else {//请求失败
                    layer.closeAll();
                    layer.msg(result.message, {
                        icon: 5,
                        time: 1000
                    });
                }
            }
        });
    }
</script>
</body>
</html>