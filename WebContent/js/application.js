Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
Array.prototype.unique2 = function()
{
    var n = {},r=[]; //n为hash表，r为临时数组
    for(var i = 0; i < this.length; i++) //遍历当前数组
    {
        if (!n[this[i]]) //如果hash表中没有当前项
        {
            n[this[i]] = true; //存入hash表
            r.push(this[i]); //把当前数组的当前项push到临时数组里面
        }
    }
    return r;
}

var Common = {

    //EasyUI用DataGrid用日期格式化
    TimeFormatter: function (value, rec, index) {
        if (value == undefined) {
            return "";
        }
        /*json格式时间转js时间格式*/
        value = value.substr(1, value.length - 2);
        var obj = eval('(' + "{Date: new " + value + "}" + ')');
        var dateValue = obj["Date"];
        if (dateValue.getFullYear() < 1900) {
            return "";
        }
        var val = dateValue.format("yyyy-mm-dd HH:MM");
        return val.substr(11, 5);
    },
    DateTimeFormatter: function (value, rec, index) {
        if (value == undefined) {
            return "";
        }
        /*json格式时间转js时间格式*/
        value = value.substr(1, value.length - 2);
        var obj = eval('(' + "{Date: new " + value + "}" + ')');
        var dateValue = obj["Date"];
        if (dateValue.getFullYear() < 1900) {
            return "";
        }

        return dateValue.format("yyyy-mm-dd HH:MM");
    },

    //EasyUI用DataGrid用日期格式化
    DateFormatter: function (value, rec, index) {
        if (value == undefined) {
            return "";
        }
        /*json格式时间转js时间格式*/
        value = value.substr(1, value.length - 2);
        var obj = eval('(' + "{Date: new " + value + "}" + ')');
        var dateValue = obj["Date"];
        if (dateValue.getFullYear() < 1900) {
            return "";
        }

        return dateValue.format("yyyy-mm-dd");
    },
    TitleFormatter : function (value, rec, index) {
        if (value.length > 10) value = value.substr(0, 8) + "...";
        return value;
    },
    LongTitleFormatter: function (value, rec, index) {
        if (value.length > 15) value = value.substr(0, 12) + "...";
        return value;
    }
};

function parseURL(url) {
    var a = document.createElement('a');
    a.href = url;
    return {
        source: url,
        protocol: a.protocol.replace(':', ''),
        host: a.hostname,
        port: a.port,
        query: a.search,
        params: (function () {
            var ret = {},
                seg = a.search.replace(/^\?/, '').split('&'),
                len = seg.length, i = 0, s;
            for (; i < len; i++) {
                if (!seg[i]) { continue; }
                s = seg[i].split('=');
                ret[s[0]] = s[1];
            }
            return ret;

        })(),
        file: (a.pathname.match(/\/([^\/?#]+)$/i) || [, ''])[1],
        hash: a.hash.replace('#', ''),
        path: a.pathname.replace(/^([^\/])/, '/$1'),
        relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [, ''])[1],
        segments: a.pathname.replace(/^\//, '').split('/')
    };
}

//替换myUrl中的同名参数值
function replaceUrlParams(myUrl, newParams) {
    /*
    for (var x in myUrl.params) {
        for (var y in newParams) {
            if (x.toLowerCase() == y.toLowerCase()) {
                myUrl.params[x] = newParams[y];
            }
        }
    }
    */

    for (var x in newParams) {
        var hasInMyUrlParams = false;
        for (var y in myUrl.params) {
            if (x.toLowerCase() == y.toLowerCase()) {
                myUrl.params[y] = newParams[x];
                hasInMyUrlParams = true;
                break;
            }
        }
        //原来没有的参数则追加
        if (!hasInMyUrlParams) {
            myUrl.params[x] = newParams[x];
        }
    }
    var _result = myUrl.protocol + "://" + myUrl.host + ":" + myUrl.port + myUrl.path + "?";

    for (var p in myUrl.params) {
        _result += (p + "=" + myUrl.params[p] + "&");
    }

    if (_result.substr(_result.length - 1) == "&") {
        _result = _result.substr(0, _result.length - 1);
    }

    if (myUrl.hash != "") {
        _result += "#" + myUrl.hash;
    }
    return _result;
}


function renderchild(form, value, level, deptpath, type){
    var def = "";
    if(deptpath){
        def = deptpath[0];
        deptpath.remove(def);
    }

    dojo.xhrGet({url:"queryDeptAjax2.action", content:{id: value, level: level, type:type}, load:function (resp, ioArgs) {
            var i=level+1;
            var d = $("#deptitem" + i);
            while(d && d.length>0){
                d.remove();
                i++;
                d = $("#deptitem" + i);
            }
            if(resp.trim().length==0)
                return;
            $(".layui-select-cus").find("div:last").before(resp);
            if(def) {
                $("select[name=deptId"+(level+1)+"]").val(def);
            }
            form.render('select');
            form.on("select(queryDept"+(level+1)+")",function(data2){
                renderchild(form, data2.value, level+1, deptpath, type);
                initDeptValue();
            });

            if(def!=null && def!="") {
                renderchild(form, def, level+1, deptpath, type);
                initDeptValue();
            }
        }});
}

function initDeptValue(){
    var i=0;
    var d = $("#deptitem" + i);
    var v = "";
    while(d.length>0){
        v += "," + d.find("select").val();
        i++;
        d = $("#deptitem" + i);
    }
    if(v.length>0 && v[0]==",")
        v=v.substr(1);
    $("#deptId").val(v);
}

function getDeptSelect(){
    var v = "";
    var i=0;
    var d = $("#deptitem" + i);
    while(d.length>0){
        var sele = d.find("select");
        if(sele.attr("datatype")=="dept" && sele.val()!="")
            v = sele.val();
        i++;
        d = $("#deptitem" + i);
    }
    return v;
}

function getEmployeeSelect(){
    var v = "";
    var i=0;
    var d = $("#deptitem" + i);
    while(d.length>0){
        var sele = d.find("select");
        if(sele.attr("datatype")=="employee")
            v = sele.val();
        i++;
        d = $("#deptitem" + i);
    }
    return v;
}