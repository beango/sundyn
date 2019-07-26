<%@ page import="java.util.Date" %>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
    <title><s:text name='zx.title'/></title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/dtree.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js?<%=new Date().getTime()%>"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
</head>
<body>
<div class="place">
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<script type="text/javascript">
    function chooseMenus(){
        var menus=document.getElementById("CmenuNames");
        var retVal=new Array();

        for(var i=0;i<menus.options.length;i++){
            retVal[i]=menus.options[i].value;
        }
        return retVal;
    }
    function checkForm(ac){
        var menus=chooseMenus();
        $.ajax({url: ac+"&menus="+menus,
            type: 'GET',
            dataType:'json',
            success: function(html){
                dialogAjaxDone(html);
            }
        });
    }

    function moveMenus(){
        var e1=document.getElementById("menuNames");
        var e2=document.getElementById("CmenuNames");

        for( var i=0;i<e1.options.length;i++){

            if(e1.options[i].selected){
                var e=e1.options[i];
                e2.options.add(new Option(e.text,e.value));
                e1.remove(i);
                i--;
            }
        }
    }

    function removeMenus(){
        var e1=document.getElementById("menuNames");
        var e2=document.getElementById("CmenuNames");
        for( var i=0;i<e2.options.length;i++){
            if(e2.options[i].selected){
                var e=e2.options[i];
                e1.options.add(new Option(e.text,e.value));
                e2.remove(i);
                i--;
            }
        }
    }
    function onContextMenu(e,node){
        e.preventDefault();
        $(this).tree('select',node.target);
        if (node){
            $('#mm').empty();
            $('#mm').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
            if(!node.parentId || node.parentId === 0) //如果有子节点，允许添加下级，否则不允许
                $('#mm').menu('appendItem', {
                    text: '添加下级',
                    iconCls: 'icon-add',
                    onclick: function(){
                        $('#pp').panel('open').panel('refresh','${ctx}/common/func/addFunc1.htm?pid=' + node.id);
                    }
                });
            $('#mm').menu('appendItem', {
                text: '删除',
                iconCls: 'icon-remove',
                onclick: function(){
                    $.messager.confirm('删除', '确定删除该功能吗？！', function(r){
                        if (r){
                            $.post('${ctx}/common/func/delFunc.htm?keyIndex='+node.id, function(rst){
                                if (rst === "1"){
                                    succ("删除成功");
                                    reloadtree();
                                }
                                else{
                                    succ("删除失败");
                                }
                            });
                        }

                    });
                }
            });
        }
    }
    function reloadtree(){
        $('#tg').tree("reload");

    }
    var curStatus = "init", curAsyncCount = 0, asyncForAll = false, goAsync = false;
    var setting = {
        async: {
            enable: true,
            autoParam: ["id=ids"],//, "name=n", "level=lv"
            url: "${ctx}/authQueryJSON.action",
            dataFilter: filter,
            type: "post"
        },
        view: {
            fontCss: getFont,
            showLine: true,
            expandSpeed: "",
            addHoverDom: addHoverDom,
            //removeHoverDom: removeHoverDom,
            selectedMulti: false
        },
        edit: {
            drag: {
                isCopy: true,
                isMove: true,
                prev:true,
                next:true
            },
            enable: true,
            showRemoveBtn: false,
            //removeTitle: "删除菜单",
            renameTitle: "编辑菜单名称"
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
            onRightClick: onRightClick,
            //beforeRemove: beforeRemove,    //节点被删除之前的事件,并且根据返回值确定是否允许删除操作
            beforeRename: beforeRename,    //用于捕获节点编辑名称结束

            beforeAsync: beforeAsync,    //用于捕获异步加载之前的事件回调函数,zTree 根据返回值确定是否允许进行异步加载
            onAsyncSuccess: onAsyncSuccess,    //用于捕获异步加载出现异常错误的事件回调函数
            onAsyncError: onAsyncError,    //用于捕获异步加载正常结束的事件回调函数

            beforeDrag: beforeDrag,    //用于捕获节点被拖拽之前的事件回调函数，并且根据返回值确定是否允许开启拖拽操作
            beforeDrop: beforeDrop,    //用于捕获节点拖拽操作结束之前的事件回调函数，并且根据返回值确定是否允许此拖拽操作
            beforeDragOpen: beforeDragOpen,    //用于捕获拖拽节点移动到折叠状态的父节点后，即将自动展开该父节点之前的事件回调函数，并且根据返回值确定是否允许自动展开操作
            onDrag: onDrag,    //用于捕获节点被拖拽的事件回调函数
            onDrop: onDrop,    //用于捕获节点拖拽操作结束的事件回调函数
            onExpand: onExpand,    //用于捕获节点被展开的事件回调函数
            onClick: onClick
        }
    };
    //节点数据过滤
    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) {
            return null;
        }
        for (var i = 0, l = childNodes.length; i < l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }

    function onClick(event, treeId, treeNode, clickFlag) {
        $("#iframepage").attr("src", "authCode.action?parentId=" + treeNode.id);
        return false;
    }
    function expandNodes(nodes) {
        if (!nodes) {
            return;
        }

        curStatus = "expand";
        var zTree = jQuery.fn.zTree.getZTreeObj("zTreeMenuContent");
        if (zTree != null) {
            for (var i = 0, l = nodes.length; i < l; i++) {
                zTree.expandNode(nodes[i], true, false, false);
                if (nodes[i].isParent && nodes[i].zAsync) {
                    expandNodes(nodes[i].children);
                } else {
                    goAsync = true;
                }
            }
        }
    }

    //字体设置
    function getFont(treeId, node) {
        return node.font ? node.font : {};
    }

    ////////////////////下面是处理增删改节点//////////////////

    //节点被删除之前的事件,并且根据返回值确定是否允许删除操作
    function beforeRemove(treeId, treeNode) {

    }

    //用于捕获节点编辑名称
    function beforeRename(treeId, treeNode, newName, isCancel) {

        if (treeNode.id === "1") {
            layer.msg('根目录不能编辑！', {icon: 3});
            return true;
        } else {
            if (treeNode.name == newName) {
                return true;
            } else if (newName.length == 0) {
                layer.msg('菜单名称不能为空！', {icon: 3});
                treeNode.name = treeNode.name;
                return false;
            } else {

                var cn_pattern= /[\u4e00-\u9fa5]/g;
                var g = newName.match(cn_pattern);
                var cn_length = newName.length;
                if(g!=null){
                    var cn_numbers = g.length;
                    cn_length = cn_numbers*2+(cn_length-cn_numbers);
                }

                if(cn_length>99){
                    layer.msg('名称不能超过99个字符(1个汉字为2个字符)', {icon: 7});
                    return false;
                }else{
                    var param_data = {"album_id": treeNode.id, "album_name": newName};
                    var reData = '';
                    jQuery.ajax({
                        type: "post",
                        async: false,
                        url: "$!webPath/xxx/xxx_ajax_update.htm",
                        data: param_data,
                        success: function (save_id) {
                            reData = save_id;
                        }
                    });
                    if (reData == treeNode.id) {
                        return true;
                    } else {
                        layer.msg("修改<" + treeNode.name + ">菜单名称失败！", {icon: 3});
                        return false;
                    }
                }

            }
        }
    }

    //添加菜单
    var newCount = 1;
    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) {
            return;
        }
    };


    function dropNext(treeId, nodes, targetNode) {
        var pNode = targetNode.getParentNode();
        if (pNode && pNode.dropInner === false) {
            return false;
        } else {
            for (var i = 0, l = curDragNodes.length; i < l; i++) {
                var curPNode = curDragNodes[i].getParentNode();
                if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                    return false;
                }
            }
        }
        return true;
    }

    function dropPrev(treeId, nodes, targetNode) {
        var pNode = targetNode.getParentNode();
        if (pNode && pNode.dropInner === false) {
            return false;
        } else {
            for (var i = 0, l = curDragNodes.length; i < l; i++) {
                var curPNode = curDragNodes[i].getParentNode();
                if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                    return false;
                }
            }
        }
        return true;
    }

    function dropInner(treeId, nodes, targetNode) {
        if (targetNode && targetNode.dropInner === false) {
            return false;
        } else {
            for (var i = 0, l = curDragNodes.length; i < l; i++) {
                if (!targetNode && curDragNodes[i].dropRoot === false) {
                    return false;
                } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
                    return false;
                }
            }
        }
        return true;
    }

    //className = "dark",
    //用于捕获节点被拖拽之前的事件回调函数，并且根据返回值确定是否允许开启拖拽操作
    var log, curDragNodes, autoExpandNode;
    function beforeDrag(treeId, treeNodes) {
        //className = (className === "dark" ? "" : "dark");
        for (var i = 0, l = treeNodes.length; i < l; i++) {
            if (treeNodes[i].drag === false) {
                curDragNodes = null;
                return false;
            } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
                curDragNodes = null;
                return false;
            }
        }
        curDragNodes = treeNodes;
        return true;
    }


    //用于捕获节点被拖拽的事件回调函数
    function onDrag(event, treeId, treeNodes) {
        //className = (className === "dark" ? "" : "dark");
    }

    //用于捕获节点拖拽操作结束的事件回调函数
    function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {

        if (treeNodes.length > 0 && targetNode) {
            var dragId = treeNodes[0].id;//被拖拽菜单
            var targetId = targetNode.id;//拖拽到的目标菜单

            //判断是否同级拖动
            var treeNodes_parentId  = treeNodes[0].parentTId;
            var targetNode_parentId = targetNode.parentTId;
            var is_child_move = false;
            if(treeNodes_parentId==targetNode_parentId && moveType!="inner"){
                is_child_move = true;
            }
            var data = { "album_id" : dragId, "target_id" : targetId ,"moveType":moveType , "is_child_move" : is_child_move};
            jQuery.ajax({
                type: "post",
                async: false,
                url: "$!webPath/xxx/xxx_ajax_drag_update.htm",
                data: data,
                success: function(save_id){
                    layer.msg('移动成功！', {icon: 1});
                },
                error: function () {
                    layer.msg('服务器内部异常！', {icon: 1});
                }
            });
        }
    }

    //用于捕获节点拖拽操作结束之前的事件回调函数，并且根据返回值确定是否允许此拖拽操作
    function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
        if(targetNode){
            var targetNode_parentId = targetNode.parentTId;
            if(targetNode_parentId==null){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    //用于捕获节点被展开的事件回调函数
    function onExpand(event, treeId, treeNode) {
        if (treeNode === autoExpandNode) {
            //className = (className === "dark" ? "" : "dark");
        }
    }

    function beforeAsync() {
        layer.msg('加载中', {time: 6000,icon: 16});
        curAsyncCount++;
    }

    function onAsyncSuccess(event, treeId, treeNode, msg) {

        curAsyncCount--;
        if (curStatus == "expand") {
            expandNodes(treeNode.children);
        } else if (curStatus == "async") {
            asyncNodes(treeNode.children);
        }
        //console.info(curStatus);
        if (curAsyncCount <= 0) {
            if (curStatus != "init" && curStatus != "") {
                asyncForAll = true;
            }
            curStatus = "";
        }
        layer.closeAll();
    }

    function asyncNodes(nodes) {
        if (!nodes) {
            return;
        }
        curStatus = "async";
        var zTree = jQuery.fn.zTree.getZTreeObj("zTreeMenuContent");
        for (var i = 0, l = nodes.length; i < l; i++) {
            if (nodes[i].isParent && nodes[i].zAsync) {
                asyncNodes(nodes[i].children);
            } else {
                goAsync = true;
                zTree.reAsyncChildNodes(nodes[i], "refresh", true);
            }
        }
    }

    function asyncAll() {
        if (!check()) {
            return;
        }
        var zTree = jQuery.fn.zTree.getZTreeObj("zTreeMenuContent");
        if (asyncForAll) {

        } else {
            asyncNodes(zTree.getNodes());
            if (!goAsync) {
                curStatus = "";
            }
        }
    }

    //用于捕获异步加载正常结束的事件回调函数
    function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
        curAsyncCount--;
        if (curAsyncCount <= 0) {
            curStatus = "";
            if (treeNode != null) asyncForAll = true;
        }
    }

    //用于捕获拖拽节点移动到折叠状态的父节点后，即将自动展开该父节点之前的事件回调函数，并且根据返回值确定是否允许自动展开操作
    function beforeDragOpen(treeId, treeNode) {
        autoExpandNode = treeNode;
        return true;
    }


    //字体设置
    function getFont(treeId, node) {
        return node.font ? node.font : {};
    }

    //初始化
    var rMenu,zTree;
    $(document).ready(function () {
        layui.use(['layer'], function() {
            zTree = jQuery.fn.zTree.init($("#zTreeMenuContent"), setting);
            rMenu = $("#rMenu");
        });
    });

    function reset() {
        if (!check()) {
            return;
        }
        asyncForAll = false;
        goAsync = false;
        jQuery.fn.zTree.init($("#zTreeMenuContent"), setting);
    }

    function check() {
        if (curAsyncCount > 0) {
            //$("#demoMsg").text("正在进行异步加载，请等一会儿再点击...");
            return false;
        }
        return true;
    }


    function expandAll() {
        if (!check()) {
            return;
        }
        var zTree = jQuery.fn.zTree.getZTreeObj("zTreeMenuContent");
        if (zTree == null)
            return false;

        if (asyncForAll) {
            zTree.expandAll(true);
        } else {
            expandNodes(zTree.getNodes());
            if (!goAsync) {
                curStatus = "";
            }
        }
    }

    //鼠标右键功能
    function onRightClick(event, treeId, treeNode) {
        var x = event.clientX;
        var y = event.clientY;
        if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
            zTree.cancelSelectedNode();
            showRMenu(treeNode.level, x, y);
        } else if (treeNode && !treeNode.noR) {
            zTree.selectNode(treeNode);
            showRMenu(treeNode.level, x, y);
        }
    }

    function addAuth(){
        hideRMenu();
        var dia = new dialog();
        zTree = jQuery.fn.zTree.getZTreeObj("zTreeMenuContent");
        var nodes = zTree.getSelectedNodes();
        if (nodes.length>0){
            dia.iframe("authEdit.action?parentid=" + nodes[0].id, {title: '<s:text name="auth.rightaction.add" />', resize: false, h: "300px"});
        }
        return true;
    }

    function editAuth(){
        hideRMenu();
        var dia = new dialog();
        zTree = jQuery.fn.zTree.getZTreeObj("zTreeMenuContent");
        var nodes = zTree.getSelectedNodes();
        if (nodes.length>0){
            dia.iframe("authEdit.action?id=" + nodes[0].id, {title: '<s:text name="auth.rightaction.edit" />', resize: false, h: "300px"});
        }
        return true;
    }

    function delAuth(){
        hideRMenu();
        zTree = jQuery.fn.zTree.getZTreeObj("zTreeMenuContent");
        var nodes = zTree.getSelectedNodes();
        if (nodes.length>0){
            dojo.xhrPost({url:"authDelPost.action", content:{id:nodes[0].id}, load:function (resp, ioArgs) {
                    if(resp.trim()==""){
                        succ('<s:text name="main.delete.succ" />', function(){
                            refreshTab();
                        });
                    }
                    else{
                        error(resp);
                    }
                }});
        }
        return true;
    }

    function showRMenu(level, x, y) {
        $("#rMenu ul").show();
        if (level==0) {
            $("#m_del").hide();
            $("#m_rename").hide();
            $("#rMenu").height(30);
        } else if (level==1)  {
            $("#m_del").show();
            $("#m_rename").show();
            $("#m_add").show();
            $("#rMenu").height(90);
        } else if (level==2)  {
            $("#m_del").show();
            $("#m_rename").show();
            $("#m_add").hide();
            $("#rMenu").height(60);
        }
        rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
        $("body").bind("mousedown", onBodyMouseDown);
    }

    function hideRMenu() {
        if (rMenu) rMenu.css({"visibility": "hidden"});
        jQuery("body").unbind("mousedown", onBodyMouseDown);
    }

    function onBodyMouseDown(event){
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
            rMenu.css({"visibility" : "hidden"});
        }
    }

    // 记录节点的编号
    function selectAuth(id) {
        console.log(id);
        dojo.xhrPost({url:"authAction.action", content:{id: id}, load:function (resp, ioArgs) {
            document.getElementById("deptView").innerHTML = resp;
        }});
    }

    function iFrameHeight() {
        var ifm= document.getElementById("iframepage");
        var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;
        if(ifm != null && subWeb != null) {
            ifm.height = subWeb.body.scrollHeight;
            ifm.width = subWeb.body.scrollWidth;
        }
    }
</script>
<style type="text/css">
    div#rMenu{
        position: fixed;
        visibility:hidden;
        top:0;
        background-color: #fff;
        text-align: left;
        border: 1px solid #c9caca;
        box-shadow: 0 0 2px #c9caca;
        padding: 2px 0;
        z-index: 999;
    }

    div#rMenu ul li{
        margin: 1px 0;
        padding: 5px;
        cursor: pointer;
        list-style: none;
        height: 20px;
        background-color: #fff;
    }
    div#rMenu ul li:hover{
        background: #ddd;
    }
</style>
<div id="rMenu" style="width: 120px;height: 90px;font-size: 12px;" >
    <ul>
        <li id="m_add" onclick="addAuth();">
            <i class="fa fa-plus fa-lg" aria-hidden="true"></i>
            <span style="color:#1681ff;">
                <s:text name="auth.rightaction.add" />
            </span>
        </li>
        <li id="m_rename" onclick="editAuth();">
            <i class="fa fa-edit fa-lg" aria-hidden="true"></i>
            <span style="color:#1681ff;">
                <s:text name="auth.rightaction.edit" />
            </span>
        </li>
        <li id="m_del" onclick="delAuth();">
            <i class="fa fa-close fa-lg" aria-hidden="true"></i>
            <span style="color:#1681ff;">
                <s:text name="auth.rightaction.delete" />
            </span>
        </li>
    </ul>
</div>
<div id="man_zone">
    <div class="center_04">
        <div class="center_04_left">
            <div class="kuang">
                <ul id="zTreeMenuContent" class="ztree"></ul>
            </div>
        </div>
        <input type="hidden" id="deptId" />
        <div class="center_04_right" id="deptView">
            <iframe src="about:blank" id="iframepage" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" onLoad="iFrameHeight()"></iframe>
        </div>
    </div>
</div>
</body>
</html>
