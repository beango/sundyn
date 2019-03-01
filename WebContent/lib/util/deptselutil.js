var getQueryString = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

//初始化
var rMenu,zTree;
function initTree(param, seleVal, check, _onclick, _oncheck){
    var treeDataURL = "authDeptTree.action";
    treeDataURL = treeDataURL + (param ? param : "");
    var setting = {
        check: {
            enable: check===undefined?true:check,
            chkStyle: "radio",
            radioType : "all"
        },
        async: {
            enable: true,
            autoParam: ["id=ids"],//, "name=n", "level=lv"
            url: treeDataURL,//"authDeptTree.action"+(deptTreeParam?deptTreeParam:""),
            type: "post"
        },
        view: {
            fontCss: getFont,
            showLine: true,
            showIcon: false,
            expandSpeed: "",
            selectedMulti: true,addDiyDom: addDiyDom
        },
        data: {
            keep: {
                parent: true
            },
            simpleData: {
                enable: false
            }
        },
        callback: {
            onAsyncSuccess: function(){
                console.log("callbs:" + seleVal)
                if (seleVal!=null && seleVal!='null'){console.log("callbs２:" + seleVal)
                    var node = zTree.getNodeByParam("id", seleVal);
                    if(node){
                        zTree.checkNode(node, true, null, null);
                    }
                }
            },
            onClick: _onclick===undefined?onClick:_onclick,
            onCheck: _oncheck===undefined?onCheck:_oncheck
        }
    };
    var IDMark_A = "_a";
    function addDiyDom(treeId, treeNode) {
        return;
        var aObj = $("#" + treeNode.tId + IDMark_A);
        if (treeNode.id[0] == 'e') {
            var editStr = "<input type='checkbox' class='checkboxBtn' id='checkbox_" +treeNode.id+ "' onfocus='this.blur();'></input>";
            aObj.before(editStr);
            var btn = $("#checkbox_"+treeNode.id);
            if (btn) btn.bind("change", function() {checkAccessories(treeNode, btn);});
        }
    }

    var treeDept = $("#treeDept");
    if (treeDept.length > 0){
        zTree = jQuery.fn.zTree.init(treeDept, setting);

        treeDept.hover(function () {
            if (!treeDept.hasClass("showIcon")) {
                treeDept.addClass("showIcon");
            }
        }, function() {
            treeDept.removeClass("showIcon");
        });
    }
}

function addDiyDom(treeId, treeNode) {
    var spaceWidth = 8;
    var switchObj = $("#" + treeNode.tId + "_switch"),
        icoObj = $("#" + treeNode.tId + "_ico");
    switchObj.remove();
    icoObj.before(switchObj);

    if (treeNode.level > 0) {
        var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
        switchObj.before(spaceStr);
    }
}

//字体设置
function getFont(treeId, node) {
    return node.font ? node.font : {};
}

function onClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDept");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDept"),
        nodes = zTree.getCheckedNodes(true),
        v = "";
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i].name + ",";
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);

    var cityObj = $("#deptSel");
    if (cityObj==null || cityObj.length==0)
        cityObj = btnObj;

    if (v=="")
        v = "全部";
    cityObj.val(v);
    if (btnVal != null && $(btnVal).length>0){
        v = "";
        for (var i=0, l=nodes.length; i<l; i++) {
            v += nodes[i].id + ",";
        }
        if (v.length > 0 ) v = v.substring(0, v.length-1);
        btnVal.val(v);
    }
    hideMenu();
}

function getCheck() {
    var zTree = $.fn.zTree.getZTreeObj("treeDept"),
        nodes = zTree.getCheckedNodes(true),
        v = "";
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i].id + ",";
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);
    return v;
}

function getCheckName() {
    var zTree = $.fn.zTree.getZTreeObj("treeDept"),
        nodes = zTree.getCheckedNodes(true),
        v = "";
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i].name + ",";
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);
    return v;
}

var btnObj = null;
var btnVal = null;
function showDeptTree(btnSele, _btnValue, seleVal) {
    var cityObj = $("#deptSel");
    if (cityObj.length==0)
        cityObj = $(btnSele);
    btnObj =cityObj;
    btnVal = $(_btnValue);
    var cityOffset = cityObj.offset();

    $("#treeContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
    $("#treeContent").show()
    $("body").bind("mousedown", onBodyDown);
    if(seleVal){
        var node = zTree.getNodeByParam("id", seleVal);
        if (node!=null){
            node.checked = true;
            zTree.updateNode(node);
        }
    }
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "deptSel" || event.target.id == "menuContent" || $(event.target).parents("#treeContent").length>0)) {
        hideMenu();
    }
}

function hideMenu() {
    $("#treeContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}





