var _default = {
    elem: '#grid'
    ,height: 'full-95'
    ,method: 'get'
    ,loading: true
    ,request: {"pageName": 'pageNum', "limitName": 'pageSize'}
    ,response: {
        statusName: 'status' //数据状态的字段名称，默认：code
        ,statusCode: 200 //成功的状态码，默认：0
        ,msgName: 'message' //状态信息的字段名称，默认：msg
        ,countName: 'total' //数据总数的字段名称，默认：count
        ,dataName: 'dataList' //数据列表的字段名称，默认：data
    }
    ,limit: 10
    ,limits: [10, 20, 50]
    ,page: true //开启分页
    ,text: {none: '数据飞到外太空去了...'}
    ,unresize: false
};

function laygrid(options) {
    layui.use('table', function(){
        var table = layui.table;
        var _options = $.extend(_default, options);
        return table.render(_options);
    });
}