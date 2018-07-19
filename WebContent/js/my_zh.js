//导入包
dojo.require("dojo.parser");
dojo.require("dojo.io.script");
dojo.require("dojo.io.iframe");
var djConfig = {parseOnLoad:true};
// 快速查询
// 删除快速查询
String.prototype.trim= function(){
    // 用正则表达式将前后空格
    // 用空字符串替代。
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length == 1 ? o[k] :
                ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}
function quicklyDel(data) {
	dojo.xhrGet({url:"quicklyDel.action", content:{id:data}, load:function (resp, ioArgs) {
		dojo.byId("man_zone").innerHTML = resp;
	}});
}
// 调用查询
function quicklyQuery(data) {
	window.location.href =data;
}
// 得到当前机构的下一个机构
function dept(data, selectedval) {
	dojo.xhrGet({url:"queryDeptAjax.action", content:{id:data}, load:function (resp, ioArgs) {
		dojo.byId("dept").innerHTML = resp;
		if(selectedval){
			var selectedarr = selectedval.split(",");
			for (var i=0; i< selectedarr.length; i++)
            	$("select[name=deptId]").find("option[value='"+selectedarr[i]+"']").attr("selected",true);
		}
	}});
}
// 机构查询机构展开
function deptopen(selectedval){
	var deptlist= document.getElementsByName("deptId");
	var deptId=0;
	for(var index=0; index<deptlist.length; index++) {
		if(deptId<deptlist[index].value ){
			deptId=deptlist[index].value ;
		}
	}
	dept(deptId, selectedval);
}

//增加星级
function addInput(){
    var inputText=document.getElementById("addInput");
    var inp=document.createElement("li");
    inp.style.width="100%";
    inp.style.marginTop="5px";
 	inp.innerHTML ="<input style='width:80%;' type='text' name='as' value='' class=\"input_comm\" /> <img src='images/tp_del.gif' onclick='delInput(this)' /></li>"
    inputText.appendChild(inp);
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.iframeAuto(index);
}

//删除星级
function delInput(data){
	 var inputText=document.getElementById("addInput");
	 if(inputText.childNodes.length>1){
	     inputText.removeChild(data.parentNode);
         var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
         parent.layer.iframeAuto(index);
	 }else{
		  alert("最少要一个");
	 }
}

// 得到当前机构的下一个机构
function querydept(exportexcel) {
    initDeptValue();
    var deptId = "";
    var d = $("#deptId").val().split(",");
    if(d.length>0)
    {
        for (var i=d.length-1; i--; i>-1){
            if(d[i]!=''){
                deptId = d[i];
                break;
            }
        }
    }
 	var startDate = getStartDate();
	var endDate =  getEndDate();
  	var u = "queryDeptDeal.action?deptId=" + deptId + "&startDate=" + startDate + "&endDate=" + endDate+"&deptpath=" + $("#deptId").val();
  	if(exportexcel)
     u += "&export="+exportexcel;
    window.location.href = u;
}
// 删除选定范围内的视频文件
function deleteVideoFileDeal() {
	var keyNo = document.getElementById("keyNo").value;
	var deptId = getDeptSelect();
	var startDate = getStartDate();
	var endDate =  getEndDate();
	var href = "deleteVideoFileDeal.action?keyNo="+keyNo+"&deptId=" + deptId + "&startDate=" + startDate + "&endDate=" + endDate;
    dojo.xhrPost({url:href, content:{}, load:function (resp, ioArgs) {
            alert(resp);
    }});
}
// 查看mac是否重复
function deptExistMac(data){
	dojo.xhrGet({url:"deptExistMac.action", content:{mac:data}, load:function (resp, ioArgs) {
 	     if(resp.trim().length > 0){
	     	alert(resp);
	     }
	}});
}

// 查询备注信息
/**
 * queryDemo
 *
 * @param {int}
 *            data
 */
 function queryDemo(data) {
 	dojo.xhrPost({url:"queryDemo.action", content:{id:data}, load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
 }

function queryEmployee() {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"queryPeopleyAjax.action", content:{keyword:keyword}, load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}

function queryEmployeeNoKey() {
    var keyword = document.getElementById("keyword").value;
    dojo.xhrPost({url:"queryPeopleyAjax.action", content:{}, load:function (resp, ioArgs) {
        dojo.byId("dialog").innerHTML = resp;
        var dia = new dialog();
        dia.show("dialog");
    }});
}

function queryEmployee2() {
	var keyword = document.getElementById("keyword").value;
	var deptId=0;
	var deptIds=document.getElementsByName("deptId");
	for(i=0;i<deptIds.length;i++){
	  if(deptIds[i].value>deptId){
	    deptId=deptIds[i].value;
	  }
	}
	dojo.xhrPost({url:"queryPeopleyAjax2.action", content:{keyword:keyword,deptId:deptId}, load:function (resp, ioArgs) {
		dojo.byId("employeeList").innerHTML = resp;
 	}});
}

function clearEmployee() {
	  document.getElementById("keyword").value='';
	  document.getElementById("id").value ='';
}

// 关闭弹窗口
function closeDialog() {
	try {
        layer.closeAll('iframe');
    }
    catch (e) {

    }
    try {
        parent.layer.closeAll('iframe');
    }
    catch (e) {

    }
    try{
        layer.closeAll('page');
    }
    catch (e) {

    }
}

function closeWindows() {
	$('#dialog-window').window('close');
}

// 从查询结果里选择人
function selectPeople(id, name) {
	document.getElementById("id").value = id;
	document.getElementById("keyword").value = name;
	closeDialog();// 关闭弹窗口
}
// 显示按人员查询结果
function queryEmployeeDeal(isExport) {
    var id = getEmployeeSelect();

	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
    var keyword = "";
	window.location.href = "queryPeopleyDeal.action?id=" + id + "&keyword=" + keyword + "&startDate=" + startDate + "&endDate=" + endDate + "&export=" + isExport + "&deptpath=" + $("#deptId").val();;
}
// 显示按工号查询结果
function queryEmployeeByCardNumDeal() {
// var id = document.getElementById("id").value;
	var cardNum = document.getElementById("keyword").value;
	if(cardNum==""){
		alert("请先输入工号,再查询人员");
		return false;;
	}
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	document.getElementById("keyword").value="";
	window.location.href = "queryJobNumDeal.action?cardNum=" + cardNum + "&startDate=" + startDate + "&endDate=" + endDate;
}

// 个人查询结果
function employeeDeal() {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "queryEmployeeDeal.action?startDate=" + startDate + "&endDate=" + endDate;
}
// 显示按人员查询结果并进行删除
function queryEmployeeDealForDel() {
	var id = document.getElementById("id").value;
	if(id==""){
		alert("请先查询,再选择人员");
		return false;;
	}
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "queryDealPeopleyForDel.action?id=" + id + "&startDate=" + startDate + "&endDate=" + endDate;
}
function queryResultDeal(isExport) {
	var result = document.getElementById("result").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "queryResultDeal.action?keys=" + result + "&startDate=" + startDate + "&endDate=" + endDate + "&export=" + isExport;
}
function queryResultDealTel() {
	var tel = document.getElementById("tel").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "queryResultDealTel.action?tel=" + tel + "&startDate=" + startDate + "&endDate=" + endDate;
}
function queryResultDealIdCard() {
	var tel = document.getElementById("idCard").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "queryResultDealIdCard.action?tel=" + tel + "&startDate=" + startDate + "&endDate=" + endDate;
}

// 得到所有以勾选的机构，没有选择中为空字符串
function getAllDept() {
	var input = document.getElementsByTagName("input");
	var depids = "";
	for (i = 0; i < input.length; i++) {
		if (input[i].type == "checkbox") {
			if (input[i].id.substr(0, 6) == "checkd") {
				if (input[i].checked == true) {
					depids = depids + input[i].value + ",";
				}
			}
		}
	}
	return depids;
}
// 得到所有的key,没有选中为空字符串
function getAllKey() {
	var input = document.getElementsByTagName("input");
	var keys = "";
	for (i = 0; i < input.length; i++) {
		if (input[i].type == "checkbox") {
			if (input[i].id.substr(0, 3) == "key") {
				if (input[i].checked == true) {
					keys = keys + input[i].value + ",";
				}
			}
		}
	}
	return keys;
}
// 综合查询
function queryZhDeal() {
	var employeeId = document.getElementById("id").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var deptIds = getAllDept();
	var keys = getAllKey();
	document.location.href = "queryZhDeal.action?id=" + employeeId + "&startDate=" + startDate + "&endDate=" + endDate + "&deptIds=" + deptIds + "&keys=" + keys;
}

function queryZhDeal1() {
    initDeptValue();
    var deptId = "";
    var d = $("#deptId").val().split(",");
    if(d.length>0)
    {
        for (var i=d.length-1; i--; i>-1){
            if(d[i]!=''){
                deptId = d[i];
                break;
            }
        }
    }
    var employeeId = getEmployeeSelect();
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;
    var deptIds = getDeptSelect();
    var keys = document.getElementById("result").value;
    var myURL = parseURL(location.href);
    var _newUrl = replaceUrlParams(myURL, { id: employeeId, startDate:startDate,endDate: endDate,deptIds: deptIds,keys:keys,deptpath:$("#deptId").val() });
    //document.location.href = "queryZhDeal.action?id=" + employeeId + "&startDate=" + startDate + "&endDate=" + endDate + "&deptIds=" + deptIds + "&keys=" + keys;
    document.location.href = _newUrl; //"queryZhDealAjax.action?id=" + employeeId + "&startDate=" + startDate + "&endDate=" + endDate + "&deptIds=" + deptIds + "&keys=" + keys + "&deptpath=" + $("#deptId").val();
}

// 决策分析
// 业务量
function analyseTotalAjaxDay(data,w) {
    var now = new Date();
    var nowStr = now.format("yyyy-MM-dd");
    $("#endDate").val(nowStr + " 23:59:59");
    now.setDate(now.getDate()-data);
    $("#startDate").val(now.format("yyyy-MM-dd") + " 00:00:00");
    analyseTotalAjax(w);
}

function analyseTotalAjax(w) {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var type = document.getElementById("type").value;
	dojo.xhrPost({url:"analyseTotalAjax2.action", content:{startDate:startDate, endDate:endDate, type:type, w:w}, load:function (resp, ioArgs) {
		$("#chartcontainer").html(resp);
    }});
}
// 满意量
function analyseContentAjaxDay(data,w) {
    var now = new Date();
    var nowStr = now.format("yyyy-MM-dd");
    $("#endDate").val(nowStr + " 23:59:59");
    now.setDate(now.getDate()-data);
    $("#startDate").val(now.format("yyyy-MM-dd") + " 00:00:00");
    analyseContentAjax(w);
}
function analyseContentAjax(w) {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var type = document.getElementById("type").value;
	dojo.xhrPost({url:"analyseContentAjax.action", content:{startDate:startDate, endDate:endDate, type:type, w:w}, load:function (resp, ioArgs) {
        $("#chartcontainer").html(resp);
	}});
}
// 满意率
function analyseContentRateAjaxDay(data) {
    var now = new Date();
    var nowStr = now.format("yyyy-MM-dd");
    $("#endDate").val(nowStr + " 23:59:59");
    now.setDate(now.getDate()-data);
    var startDate = now.format("yyyy-MM-dd") + " 00:00:00";
    var endDate = nowStr + " 23:59:59";
    $("#startDate").val(startDate);
    var type = $("#type").val();
	dojo.xhrPost({url:"analyseContentRateAjax.action", content:{startDate:startDate,endDate:endDate,num:data,type:type}, load:function (resp, ioArgs) {
        $("#chartcontainer").html(resp);
	}});
}

// 决策分析，首页画图
 function analyseContentRateIndexAjaxDay(data) {
	dojo.xhrPost({url:"analyseContentRateAjaxDay.action", content:{num:data}, load:function (resp, ioArgs) {
        $("#chartcontainer1").html(resp);
        dojo.xhrPost({url:"analyseResultRateAjaxDay.action", content:{num:data}, load:function (resp, ioArgs) {
            $("#chartcontainer2").html(resp);
        }});
	}});
}
function analyseContentRateAjax() {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var type = document.getElementById("type").value;
	dojo.xhrPost({url:"analyseContentRateAjax.action", content:{startDate:startDate, endDate:endDate, type:type}, load:function (resp, ioArgs) {
        $("#chartcontainer").html(resp);
	}});
}

// 按天满意度分析
function analyseContentDAjaxDay(data){
	dojo.xhrPost({url:"analyseContentDAjaxDay.action", content:{num:data}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
		document.getElementById("msg").innerHTML=json.msg;
	 	var charStr= json.list.toJSONString();
 	 	columnChat.columnChart(charStr);
	}});
}

// 满意度分析
function analyseContentDAjax(){
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var type = document.getElementById("type").value;
	dojo.xhrPost({url:"analyseContentDAjax.action", content:{startDate:startDate,endDate:endDate,type:type}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
	 	document.getElementById("msg").innerHTML=json.msg;
	 	var charStr= json.list.toJSONString();
 	 	columnChat.columnChart(charStr);
	}});
}

// 机构分析
// 业务量分析
function analyseDeptAjax(w) {
	var deptId = getDeptSelect();
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	dojo.xhrPost({url:"analyseDeptAjax123.action", content:{startDate:startDate, endDate:endDate, type:type, deptId:deptId, w:w}, load:function (resp, ioArgs) {
        $("#chartcontainer").html(resp);
	}});
}
// 机构满意量
function analyseDeptContentAjax(w) {
	var deptids = document.getElementsByName("deptId");
	var deptId = getDeptSelect();
	/*for (i = 0; i < deptids.length; i++) {
		if (eval(deptId) < eval(deptids[i].value)) {
			deptId = deptids[i].value;
		}
	}*/
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	dojo.xhrPost({url:"analyseDeptContentAjax.action", content:{startDate:startDate, endDate:endDate, type:type, deptId:deptId, w:w}, load:function (resp, ioArgs) {
        $("#chartcontainer").html(resp);
	}});
}
// 机构满意度
function analyseDeptContentRateAjax(w) {
	var deptids = document.getElementsByName("deptId");
	var deptId = getDeptSelect();
	/*for (i = 0; i < deptids.length; i++) {
		if (eval(deptId) < eval(deptids[i].value)) {
			deptId = deptids[i].value;
		}
	}*/
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	dojo.xhrPost({url:"analyseDeptContentRateAjax.action", content:{startDate:startDate, endDate:endDate, type:type, deptId:deptId, w:w}, load:function (resp, ioArgs) {
        $("#chartcontainer").html(resp);
	}});
}

// 部门分析
// 部门业务量分析
function analyseSectionAjax() {
	var sectionName = document.getElementById("sectionName").value;
 	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}

	dojo.xhrPost({url:"analyseSectionAjax.action", content:{startDate:startDate, endDate:endDate, type:type, sectionName:sectionName}, load:function (resp, ioArgs) {
	 	var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;
	 	var charStr=json.list.toJSONString();
 	 	columnChat.columnChart(charStr);
	}});
}
// 部门满意量
function analyseSectionContentAjax() {
	var sectionName = document.getElementById("sectionName").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	dojo.xhrPost({url:"analyseSectionContentAjax.action", content:{startDate:startDate, endDate:endDate, type:type, sectionName:sectionName}, load:function (resp, ioArgs) {
	 	var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;
	 	var charStr=json.list.toJSONString();
 	 	columnChat.columnChart(charStr);
	}});
}
// 部门满意度
function analyseSectionContentRateAjax() {
	var sectionName = document.getElementById("sectionName").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	dojo.xhrPost({url:"analyseSectionContentRateAjax.action", content:{startDate:startDate, endDate:endDate, type:type, sectionName:sectionName}, load:function (resp, ioArgs) {
	 	var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;
	 	var charStr=json.list.toJSONString();
 	 	columnChat.columnChart(charStr);
	}});
}

// 员工业务量
function analyseEmployeeAjax(w) {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
	 	if (types[i].checked) {
			type = types[i].value;
		}
	}
	var employeeId = getEmployeeSelect();
	/*if(employeeId == ""){
	    alert("先查询，选择人员");
	    return false;
	}*/
	dojo.xhrPost({url:"analyseEmployeeAjax.action", content:{startDate:startDate, endDate:endDate, type:type, employeeId:employeeId,w:w}, load:function (resp, ioArgs) {
        $("#chartcontainer").html(resp);
	}});
}
// 员工满意量
function analyseEmployeeContentAjax(w) {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	var employeeId = getEmployeeSelect();
	if(employeeId == ""){
	 //alert("先查询，选择人员");
	 //return false;
	}
	dojo.xhrPost({url:"analyseEmployeeContentAjax.action", content:{startDate:startDate, endDate:endDate, type:type, employeeId:employeeId, w:w}, load:function (resp, ioArgs) {
        $("#chartcontainer").html(resp);
	}});
}
// 员工满意度
function analyseEmployeeContentRateAjax(w) {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	var employeeId = getEmployeeSelect();
	if(employeeId == ""){
	 //alert("先查询，选择人员");
	 //return false;
	}
	dojo.xhrPost({url:"analyseEmployeeContentRateAjax.action", content:{startDate:startDate, endDate:endDate, type:type, employeeId:employeeId, w:w}, load:function (resp, ioArgs) {
        $("#chartcontainer").html(resp);
	}});
}

// 记录节点的编号
function regId(data) {
	document.getElementById("deptId").value = data;
	dojo.xhrPost({url:"deptReg.action", content:{deptId:data}, load:function (resp, ioArgs) {
		document.getElementById("deptView").innerHTML = resp;
	}});
}
// 添加机构节点
function addChildItem(deptId) {

	if(document.getElementById("tip").innerHTML == "mac 已经被占用"){
		alert("mac 已经被占用!");
		return false;
	}

	if (!deptId) {
		alert("请选择部门");
		return false;
	}
	if (document.getElementById("deptName").value == "") {
		alert("请输入名称");
		return false;
	}
	var deptName = document.getElementById("deptName").value;
	var reMark = document.getElementById("reMark").value;
	var client_type = 0;
	var deptType = document.getElementById("deptType").value;
	// var deptPause =document.getElementById("deptPause").value;
	// var deptPic = document.getElementById("deptPausePic").value;
	var dept_camera_url="";
	var dept_businessId="";
	var dept_playListId="";
    var deptPause =  "";
	var deptPic =  "";
	var deptLogoPic ="";
	var product_type="";
	var provinceid = "";
	var cityid = "";
	if(document.getElementById("provinces")!=null){
		provinceid=document.getElementById("provinces").value;
	}
	if(document.getElementById("citys")!=null){
		cityid=document.getElementById("citys").value;
	}
	if (document.getElementById("deptLogoPic")!=null){
	deptLogoPic = document.getElementById("deptLogoPic").value;
	}

	if (document.getElementById("deptPause")!=null){
	deptPause = document.getElementById("deptPause").value;
	}

	if (document.getElementById("deptPausePic")!=null){
	deptPic = document.getElementById("deptPausePic").value;
	}

	if(document.getElementById("dept_camera_url")!=null){
		dept_camera_url=document.getElementById("dept_camera_url").value;
	}
	if(document.getElementById("dept_businessId")!=null){
		dept_businessId=document.getElementById("dept_businessId").value;
	}
	if(document.getElementById("playListId")!=null){
		dept_playListId=document.getElementById("playListId").value;
	}
	if(document.getElementById("useVideo")!=null){
		var useVideo = document.getElementById("useVideo").value;
	}
	if(document.getElementById("notice")!=null){
		var notice = document.getElementById("notice").value;
	}
	if(document.getElementById("product_type")!=null){
		var product_type = document.getElementById("product_type").value;
	}
	dojo.xhrPost({url:"deptAddChildItem.action", content:{deptId:deptId, deptName:deptName, reMark:reMark, client_type:client_type, product_type:product_type, deptType:deptType,dept_camera_url:dept_camera_url,dept_businessId:dept_businessId,dept_playListId:dept_playListId,deptPause:deptPause,deptPic:deptPic,deptLogoPic:deptLogoPic,useVideo:useVideo,notice:notice,provinceid:provinceid,cityid:cityid}, load:function (resp, ioArgs) {
	    var _newUrl = parent.location.href;
	    if(parent.parent.qc){
                var currTab = parent.parent.qc.main.mainTabs.tabs('getSelected');
                parent.parent.qc.main.mainTabs.tabs('update', {
                    tab : currTab,
                    options : {
                        content : '<iframe class="iframe-fluid" src="'+_newUrl+'"></iframe>'
                    }
                });
            }
            else{
                parent.location.href = _newUrl;
            }
	}});
}
// 删除机构
function del() {
	var deptId = document.getElementById("deptId").value;
	if(deptId=="1"){
	alert("不能删除最高机构")
	return false;
	}
	if(!confirm("你确认要删除么？")){
	return false;
	}
	dojo.xhrPost({url:"deptDel.action", content:{deptId:deptId}, load:function (resp, ioArgs) {
		var deptList = eval(resp);
		d = new dTree("d");
		for (i = 0; i < deptList.length; i++) {
			d.add(deptList[i].id, deptList[i].fatherId, deptList[i].name, "javascript:regId(" + deptList[i].id + ")");
		}
		document.getElementById("tree").innerHTML = d;
		// 清除deptid
		document.getElementById("deptView").innerHTML = "<h3>\u90e8\u95e8\u7ba1\u7406</h3><div class='sundyn_row'>\u8bf7\u5148\u9009\u62e9\u5de6\u8fb9\u7684\u90e8\u95e8\uff0c\u518d\u8fdb\u884c\u76f8\u5e94\u7684\u64cd\u4f5c</div>";
	}});
}
// 添加机构对话框
function deptAddDialog(data, deptid, title) {
    layer.open({
        type: 2,
        title: title?title:'提示页',
        shadeClose: true,
        shade: 0.8,
        area: ['600px', '50%'],
        content: 'deptAddDialog.action?deptType='+data + "&deptId="+deptid,
        success:function(ly,index){

        }
    });
    return;
	dojo.xhrPost({url:"deptAddDialog.action", content:{deptType:data}, load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");

	}});
}
// 修改机构对话框
function deptEditDialog(title) {
	var deptId = document.getElementById("deptId").value;
	new dialog().iframe('deptEditDialog.action?deptId='+deptId, {title: title, resize:false, h:"600px"});
}

function deptGenCer(mac, batchname){
    dojo.xhrPost({url:"deptGenCer.action", content:{mac:mac, batchname:batchname}, load:function (resp, ioArgs) {
        var j = JSON.parse(resp)
            if (j.rst)
                layer.msg('生成成功', {
                    icon: 1,
                    time: 800
                }, function(){
                });
        else
                layer.msg('生成失败', {
                    icon: 1,
                    time: 800
                }, function(){
                });
        }});
}
// 修改机构
function deptEditItem(deptId) {
// if (document.getElementById("deptId").value == "") {
// alert("\u8bf7\u9009\u62e9\u90e8\u95e8");
// return false;
// }
	var deptName = document.getElementById("deptName").value;

	var reMark = "";
	if(document.getElementById("reMark"))
        reMark = document.getElementById("reMark").value;
// var client_type = document.getElementById("client_type").value;
	var client_type = 0;
	var useVideo = "";
    if(document.getElementById("useVideo"))
        useVideo = document.getElementById("useVideo").value;

	var notice = "";
    if(document.getElementById("notice"))
        notice = document.getElementById("notice").value;


	var dept_camera_url="";
	var dept_businessId="";
	var dept_playListId="";
	var dept_Pause =  "";
	var deptPic =  "";
	var deptLogoPic ="";
	var product_type ="";
	var provinceid = "";
	var cityid = "";

	if (document.getElementById("deptLogoPic")!=null){
	    deptLogoPic = document.getElementById("deptLogoPic").value;
	}
	if (document.getElementById("deptPause")!=null){
	    dept_Pause = document.getElementById("deptPause").value;
	}

	if (document.getElementById("deptPausePic")!=null){
	    deptPic = document.getElementById("deptPausePic").value;
	}
	if(document.getElementById("dept_camera_url")!=null){
		dept_camera_url=document.getElementById("dept_camera_url").value;
	}
	if(document.getElementById("dept_businessId")!=null){
		dept_businessId=document.getElementById("dept_businessId").value;
	}
	if(document.getElementById("playListId")!=null){
		dept_playListId=document.getElementById("playListId").value;
	}
	if(document.getElementById("product_type")!=null){
		product_type=document.getElementById("product_type").value;
	}
	if(document.getElementById("provinces")!=null){
		provinceid=document.getElementById("provinces").value;
	}
	if(document.getElementById("citys")!=null){
		cityid=document.getElementById("citys").value;
	}
// alert("product_type="+product_type);
	dojo.xhrPost({url:"deptEditItem.action", content:{deptId:deptId, deptName:deptName, reMark:reMark, client_type:client_type,product_type:product_type,dept_camera_url:dept_camera_url,dept_businessId:dept_businessId,dept_playListId:dept_playListId,deptPause:dept_Pause,deptPic:deptPic,deptLogoPic:deptLogoPic,useVideo:useVideo,notice:notice,provinceid:provinceid,cityid:cityid}, load:function (resp, ioArgs) {
        var _newUrl = parent.location.href;
	    if(parent.parent.qc){
                var currTab = parent.parent.qc.main.mainTabs.tabs('getSelected');
                parent.parent.qc.main.mainTabs.tabs('update', {
                    tab : currTab,
                    options : {
                        content : '<iframe class="iframe-fluid" src="'+_newUrl+'"></iframe>'
                    }
                });
            }
            else{
                parent.location.href = _newUrl;
	    }
	}});
}
// 员工管理
// 根据机构查询出来员工
function employeeManage(data) {
    document.getElementById("deptId").value = data;
    dojo.xhrPost({url:"employeeManage.action", content:{deptId:data}, load:function (resp, ioArgs) {
        document.getElementById("employeeView").innerHTML = resp;
    }});
}
function page(data) {
    var deptId = document.getElementById("deptId").value;
	var currentPage = data;
	dojo.xhrPost({url:"employeeManage.action", content:{deptId:deptId, currentPage:currentPage}, load:function (resp, ioArgs) {
		document.getElementById("employeeView").innerHTML = resp;
	}});
}
// 添加员工对话框
function employeeAddDialog(title) {
    var deptId = document.getElementById("deptId").value;
    new dialog().iframe('employeeAddDialog.action?deptId='+deptId, {title: title});
}
// 员工是否存在
 function employeeExsits(){
	var ext2 = document.getElementById("ext2").value;
	dojo.xhrPost({url:"employeeExsits.action",content:{ext2:ext2},load:function (resp, ioArgs) {
 		document.getElementById("tip").innerHTML = trim(resp);
	}});
}

function employeeCardNumExsits(){
    var CardNum = document.getElementById("CardNum").value;
    dojo.xhrPost({url:"employeeCardNumExsits.action",content:{CardNum:CardNum},load:function (resp, ioArgs) {
        document.getElementById("tipcardnum").innerHTML = trim(resp);
    }});
}
// 添加员工
function employeeAdd() {
	if(document.getElementById("tip").innerHTML=='已经存在'){
		alert("该用户已存在");
		return false;
	}
    if(document.getElementById("tipcardnum").innerHTML=='已经存在'){
        alert("卡号已经存在！");
        return false;
    }
	var Name = document.getElementById("Name").value;
	var job_desc = document.getElementById("job_desc").value;
	var CardNum = document.getElementById("CardNum").value;
	var imgName = document.getElementById("imgName").value;
	var Phone = document.getElementById("Phone").value;
	var sexs = document.getElementsByName("Sex");
	var dept = document.getElementById("deptId").value;
	var ext2= document.getElementById("ext2").value;
	// var remark=document.getElementById("remark").value;
	var remark="";
	var showDeptName = document.getElementById("showDeptName").value;
	var showWindowName = document.getElementById("showWindow").value;
	var unitName = document.getElementById("ext1").value;
	var Sex = "";
	for (i = 0; i < sexs.length; i++) {
		if (sexs[i].checked) {
			Sex = sexs[i].value;
		}
	}
	if (Sex == "") {
		Sex = "1";
	}
	dojo.xhrPost({url:"employeeAdd.action", content:{Name:Name, job_desc:job_desc, CardNum:CardNum, imgName:imgName, Sex:Sex, Phone:Phone, dept:dept,ext2:ext2,remark:remark,showDeptName:showDeptName,showWindowName:showWindowName,unitName:unitName}, load:function (resp, ioArgs) {
	    //document.getElementById("dialog").innerHTML = resp;
		parent.closeDialog();
		parent.employeeManage(document.getElementById("deptId").value);
	}});
}
// 把file名字放入input传过去
function getFileName() {
	var fileName = document.getElementById("img").value;
	if (fileName != "") {
		document.getElementById("imgName").value = fileName;
	}
}
// 把file名字放入input传过去
function getFileName2() {
	var fileName = document.getElementById("img2").value;
	if (fileName != "") {
		document.getElementById("imgName2").value = fileName;
	}
}
// 删除员工
function employeeDel(data) {
	if(confirm('确定要删除该员工吗')){
        dojo.xhrPost({url:"employeeDel.action", content:{employeeId:data}, load:function (resp, ioArgs) {
                layer.msg('删除成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    employeeManage(document.getElementById("deptId").value);
                });

        }});
    }
}
// 修改员工
function employeeEditDialog(data, title) {
     new dialog().iframe('employeeEditDialog.action?employeeId='+data, {title: title});
}

// 导出人员
function employeeExcel(){
	window.location.href="employeeExcel.action";
}

// 上传机构暂停图片
function pauseUpload() {
	dojo.io.iframe.send({url:"employeeUpload.action", method:"post", handleAs:"text", form:dojo.byId("pic"), handle:function (data, ioArgs) {
		if (data != "") {
		  // alert(data);
			document.getElementById("deptPausePic").value = "" + data;
		}
	}});
}

// 上传窗口的logo图片
function DeptLogoUpload() {
	dojo.io.iframe.send({url:"employeeUpload.action?tt=c", method:"post", handleAs:"text", form:dojo.byId("pic2"), handle:function (data, ioArgs) {
		if (data != "") {
		  // alert(data);
			document.getElementById("deptLogoPic").value = "" + data;
		}
	}});
}


// 上传图片
function employeeUpload() {
	dojo.io.iframe.send({url:"employeeUpload.action", method:"post", handleAs:"text", form:dojo.byId("pic"), handle:function (data, ioArgs) {
		if (data != "") {
			document.getElementById("imgName").value = "" + data;
            console.log('upload.img: ' + data);
			var img123 = document.getElementById("img123");
			img123.src = data;
		}
	}});
}
// 修改员工
function employeeEdit() {
	if(document.getElementById("tip").innerHTML=='该用户已存在'){
		alert("该用户已存在");
		return false;
	}
	var employeeId = document.getElementById("employeeId").value;
	var Name = document.getElementById("Name").value;
	var job_desc = document.getElementById("job_desc").value;
	var CardNum = document.getElementById("CardNum").value;
	var imgName = document.getElementById("imgName").value;
	var Phone = document.getElementById("Phone").value;
	var sexs = document.getElementsByName("Sex");
	var ext2= document.getElementById("ext2").value;
	// var remark=document.getElementById("remark").value;
	var remark="";
	var showDeptName = document.getElementById("showDeptName").value;
	var showWindowName = document.getElementById("showWindow").value;
	var unitName = document.getElementById("ext1").value;
	var Sex = "";
	for (i = 0; i < sexs.length; i++) {
		if (sexs[i].checked) {
			Sex = sexs[i].value;
		}
	}
	if (Sex == "") {
		Sex = "\u7537";
	}
	dojo.xhrPost({url:"employeeEdit.action", content:{employeeId:employeeId, Name:Name, job_desc:job_desc, CardNum:CardNum, imgName:imgName, Sex:Sex, Phone:Phone,ext2:ext2,remark:remark,showDeptName:showDeptName,showWindowName:showWindowName,unitName:unitName}, load:function (resp, ioArgs) {
        parent.employeeManage(parent.$("#deptId").val());
		parent.closeDialog();
	}});
}
function employeeOut(data) {
     if (confirm("确认要调出吗?")){
         dojo.xhrPost({url:"employeeOut.action", content:{employeeId:data}, load:function (resp, ioArgs) {
                 layer.msg('调出成功!', {
                     icon: 1,
                     time: 800
                 }, function(){
                     employeeManage(document.getElementById("deptId").value);
                 });
         }});
     }
}
function employeeOutView() {
	dojo.xhrPost({url:"employeeOutView.action", load:function (resp, ioArgs) {
		document.getElementById("employeeView").innerHTML = resp;
	}});
}
function employeeOutPage(data) {
	dojo.xhrPost({url:"employeeOutView.action", content:{currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("employeeView").innerHTML = resp;
	}});
}
// 把一个人调入一个机构
function employeeIn(data) {
	var deptId = document.getElementById("deptId").value;
	if (deptId == "") {
		alert("\u8bf7\u5148\u9009\u62e9\u5de6\u8fb9\u7684\u90e8\u95e8\uff0c\u518d\u67e5\u770b\u6d41\u52a8\u4eba\u5458");
		return false;
	}
	dojo.xhrPost({url:"employeeIn.action", content:{deptId:deptId, employeeId:data}, load:function (resp, ioArgs) {
            layer.msg('调入成功', {
                icon: 1,
                time: 800
            }, function(){
                employeeOutView();
            });
	}});
}
// 根据人员姓名，查询人员
function employeeQueryKeyword(deptId) {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"employeeQueryKeyword.action", content:{keyword:keyword, deptId:document.getElementById("deptId").value}, load:function (resp, ioArgs) {
		document.getElementById("employeeView").innerHTML = resp;
	}});
}
function keywordpage(data) {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"employeeQueryKeyword.action", content:{keyword:keyword, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("employeeView").innerHTML = resp;
	}});
}
// 密码初始化123456
function employeeReset(data) {
	dojo.xhrPost({url:"employeeReset.action", content:{employeeId:data}, load:function (resp, ioArgs) {
		alert("\u5bc6\u7801\u91cd\u7f6e\u6210\u529f");
	}});
}
// 修改按键值对话框
function keyTypeQueryDialog(data) {
	dojo.xhrPost({url:"keyTypeQueryDialog.action", content:{id:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show(resp);
	}});
}
// 修改按键
function keyTypeEdit(data) {
	var name = document.getElementById("name" + data).value;
	var isJoy = document.getElementById("isJoy" + data);
	var yes=document.getElementById("yes"+data);
	var ext1=document.getElementById("ext1"+data).value;
	if (isJoy.checked) {
		isJoy = "on";
	} else {
		isJoy = "";
	}
	if (yes.checked) {
		yes = 1;
	} else {
		yes = 0;
	}
	dojo.xhrPost({url:"keyTypeEdit.action", content:{id:data, name:name, isJoy:isJoy,yes:yes,ext1:ext1}, load:function (resp, ioArgs) {

	}});
}

function keyTypeEditAll() {
	var r=/^[-]?\d+$/;
	var ext1=document.getElementById("ext11").value;
	var ext2=document.getElementById("ext12").value;
	var ext3=document.getElementById("ext13").value;
	var ext4=document.getElementById("ext14").value;
	var ext5=document.getElementById("ext15").value;
	var ext6=document.getElementById("ext16").value;
	var ext7=document.getElementById("ext17").value;

	if(r.test(ext1)&&r.test(ext2)&&r.test(ext3)&&r.test(ext4)&&r.test(ext5)&&r.test(ext6)&&r.test(ext7)){
	    if((ext1 && ext1>10) || (ext2 && ext2>10) || (ext3 && ext3>10) || (ext4 && ext4>10) || (ext5 && ext5>10) || (ext6 && ext6>10) || (ext7 && ext7>10))
        {
            alert("权值最大为10");
            return;
        }
        var idx = 1;
	    var ids = "", names = "", isJoys="", yess = "", ext1s = "";
	    while (idx<=7){
            var name = document.getElementById("name" + idx).value;
            var isJoy = document.getElementById("isJoy" + idx);
            var yes=document.getElementById("yes"+idx);
            var ext1=document.getElementById("ext1"+idx).value;
            if (isJoy.checked) {
                isJoy = "on";
            } else {
                isJoy = "";
            }
            if (yes.checked) {
                yes = 1;
            } else {
                yes = 0;
            }
            ids += idx+",";
            names += name + ",";
            isJoys += isJoy + ",";
            yess += yes + ",";
            ext1s += ext1 + ",";
	        idx++;
        }
        if(("," + yess).indexOf(",1,")==-1)
        {
            alert("至少选择一个评价按键！");
            return;
        }
        dojo.xhrPost({url:"keyTypeEdit.action", content:{ids:ids, names:names, isJoys:isJoys,yess:yess,ext1s:ext1s}, load:function (resp, ioArgs) {
                alert(resp);
            }});
		/* keyTypeEdit(1);
		 keyTypeEdit(2);
		 keyTypeEdit(3);
		 keyTypeEdit(4);
		 keyTypeEdit(5);
		 keyTypeEdit(6);
		 keyTypeEdit(7);
		 */
	}else{
		alert("权值非法，只能为整数" + (ext1>1));
	}
}

// 添加用户对话框
function managerAddDialog() {
	dojo.xhrPost({url:"managerAddDialog.action", load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
// 添加低等级用户对话框
function lowerManagerAddDialog(title) {
    var dia = new dialog();
    dia.iframe("lowerManagerAddDialog.action",{title:title,h:"300px",resize:false});
}
// 验证用户不为空
function managerCheck(){
	var name = document.getElementById("name").value;
	if(name==""){
		alert("用户名不能为空");
		return false;
	}
	var realname = document.getElementById("realname").value;
	if(realname==""){
		alert("真实姓名不能为空");
		return false;
	}
	return true;
}
function managerExist(){
	var name = document.getElementById("name").value;
    var id = $("#id").val();
	dojo.xhrPost({url:"managerExist.action",content:{id:id, name:name},load:function (resp, ioArgs) {
 		document.getElementById("tip").innerHTML = resp;
	}});

}
// 添加用户
function managerAdd() {
	if(document.getElementById("tip").innerHTML=='该用户已经存在 '){
		alert("该用户已经存在");
		return false;
	}
	var check=managerCheck();
	if(!check){
		return false;
	}
	var name = document.getElementById("name").value;
	var realname = document.getElementById("realname").value;
	var remark = document.getElementById("remark").value;
	var ext1 = document.getElementById("ext1").value;
	var ext2 = document.getElementById("ext2").value;
	var userGroupId = document.getElementById("userGroupId").value;
	dojo.xhrPost({url:"managerAdd.action", content:{name:name, realname:realname, remark:remark, ext1:ext1, ext2:ext2, userGroupId:userGroupId}, load:function (resp, ioArgs) {
		if (resp.trim() == "") {
            layer.msg('添加成功', {
                icon: 1,
                time: 800
            }, function(){
                parent.closeDialog();
                parent.lowerManagerQueryAjax();
            });
        }
        else{
            layer.msg(resp, {
                icon: 2,
                time: 800
            }, function(){

            });
        }
	}});
}
// 修改用户对话框
function managerEditDialog(data, title) {
    var dia = new dialog();
    dia.iframe("managerEditDialog.action?id="+data, {title: title, resize: false, h: "300px"});
}
// 修改用户
function managerEdit() {
	if(document.getElementById("tip").innerHTML=='该用户已经存在'){
		alert("该用户已经存在");
		return false;
	}
	var check=managerCheck();
	if(!check){
		return false;
	}
	var id = document.getElementById("id").value;
	var name = document.getElementById("name").value;
	var realname = document.getElementById("realname").value;
	var remark = document.getElementById("remark").value;
	var ext1 = document.getElementById("ext1").value;
	var ext2 = document.getElementById("ext2").value;
	var userGroupId = document.getElementById("userGroupId").value;
	dojo.xhrPost({url:"managerEdit.action", content:{id:id, name:name, realname:realname, remark:remark, ext1:ext1, ext2:ext2, userGroupId:userGroupId}, load:function (resp, ioArgs) {
            if(resp.trim()==""){
                layer.msg('修改成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    parent.closeDialog();
                    parent.lowerManagerQueryAjax();
                });
            }
            else{
                layer.msg(resp, {
                    icon: 2,
                    time: 800
                }, function(){

                });
            }
	}});
}
function managerQueryAjax() {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"managerQueryAjax.action", content:{name:keyword}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}

function lowerManagerQueryAjax() {
	var keyword = document.getElementById("keyword").value;
    var myURL = parseURL(location.href);
    var _newUrl = replaceUrlParams(myURL, { name: keyword });
    if(parent.qc && parent.qc.main.mainTabs){
        var currTab = parent.qc.main.mainTabs.tabs('getSelected');
        parent.qc.main.mainTabs.tabs('update', {
            tab : currTab,
            options : {
                content: '<iframe class="iframe-fluid" src="'+_newUrl+'"></iframe>'
            }
        });
    }
    else{
        location.href = _newUrl;
    }

    return;
	dojo.xhrPost({url:"lowerManagerQueryAjax.action", content:{name:keyword}, load:function (resp, ioArgs) {
		document.getElementById("layui-form").innerHTML = resp;
	}});
}
function managerPage(data) {
	var name = document.getElementById("keyword").value;
	dojo.xhrPost({url:"managerQueryAjax.action", content:{name:name, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
function lowerManagerPage(data) {
	var name = document.getElementById("keyword").value;
	dojo.xhrPost({url:"lowerManagerQueryAjax.action", content:{name:name, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
function managerDel(data) {
	var managerId=document.getElementById("managerId").value;
	if(data==1){
		alert("不能删除此超级用户！");
	}else if(managerId==data){
		alert("不能删除自己！");
	}else{
        if (confirm("确认要删除吗?")){
            dojo.xhrPost({url:"managerDel.action", content:{id:data}, load:function (resp, ioArgs) {
                    layer.msg('删除成功', {
                        icon: 1,
                        time: 800
                    }, function(){
                        lowerManagerQueryAjax();
                    });
            }});
        }
	}
}
// 重置密码
function managerReset(data) {
	if (confirm("确认要重置该用户密码吗?")){
        dojo.xhrPost({url:"managerReset.action", content:{id:data}, load:function (resp, ioArgs) {
                layer.msg('密码已重置为初始密码', {
                    icon: 1,
                    time: 800
                }, function(){
                    closeDialog();
                });
        }});
    }
}
// 角色管理
function powerQueryAjax() {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"powerQueryAjax.action", content:{keyword:keyword}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
// 查找权限低于自身角色的角色
function lowerPowerQueryAjax() {
    var keyword = document.getElementById("keyword").value;
	/*var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"lowerPowerQueryAjax.action", content:{name:keyword}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});*/
	refreshTab({name: keyword});
}
// 分页
function powerPage(data) {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"powerQueryAjax.action", content:{keyword:keyword, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
function lowerPowerPage(data) {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"lowerPowerQueryAjax.action", content:{keyword:keyword, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
// 删除
function powerDel(data) {
	if (confirm("确认要删除吗?")){
        dojo.xhrPost({url:"powerDel.action", content:{id:data}, load:function (resp, ioArgs){
                layer.msg('删除成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    lowerPowerQueryAjax();
                });
        }});
    }
}
// 添加角色对话框
function powerAddDialog(title) {
    new dialog().iframe("powerAddDialog.action", {title: title, h:"500px",resize:false});
}
// 判断该角色名是否存在
function powerExist(){
	var name = document.getElementById("name").value;
    var id = document.getElementById("id").value;
	dojo.xhrPost({url:"powerExist.action",content:{id:id, name:name},load:function (resp, ioArgs) {
		document.getElementById("tip").innerHTML = resp;
	}});
}
// 添加角色
function powerAdd() {
	var name = document.getElementById("name").value;
	if(name==""){
		alert("角色名不能为空");
		return false;
	}
	if (document.getElementById("tip").innerHTML=="该角色名存在") {
		alert("该角色名存在");
		return false;
	}
	var baseSet = document.getElementById("baseSet");
	if (baseSet.checked) {
		baseSet = 1;
	} else {
		baseSet = 0;
	}
	var dataManage = document.getElementById("dataManage");
	if (dataManage.checked) {
		dataManage = 1;
	} else {
		dataManage = 0;
	}
	var deptId = document.getElementById("deptId").value;
	dojo.xhrPost({url:"powerAdd.action", content:{name:name, baseSet:baseSet, dataManage:dataManage, deptId:deptId}, load:function (resp, ioArgs) {
            if (resp.trim()==""){
                layer.msg('角色添加成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    parent.closeDialog();
                    parent.refreshTab();
                });
            }
            else{
                layer.msg(resp, {
                    icon: 2,
                    time: 800
                }, function(){
                });
            }
	}});
}
// 修改角色对话框
function powerEditDialog(data, title) {
    new dialog().iframe("powerEditDialog.action?id="+data, {title: title, h:"500px",resize:false});
}
 function powerEdit() {
	var id = document.getElementById("id").value;
	var name = document.getElementById("name").value;
	if(name==""){
		alert("用户名不能为空");
		return false;
	}
	if (document.getElementById("tip").innerHTML=="该角色名存在") {
		alert("该角色名存在");
		return false;
	}
	var baseSet = document.getElementById("baseSet");
	if (baseSet.checked) {
		baseSet = 1;
	} else {
		baseSet = 0;
	}
	var dataManage = document.getElementById("dataManage");
	if (dataManage.checked) {
		dataManage = 1;
	} else {
		dataManage = 0;
	}
	var deptId = document.getElementById("deptId").value;
	dojo.xhrPost({url:"powerEdit.action", content:{id:id, name:name, baseSet:baseSet, dataManage:dataManage, deptId:deptId}, load:function (resp, ioArgs) {
		if(resp.trim()==""){
            layer.msg('修改成功', {
                icon: 1,
                time: 800
            }, function(){
                parent.closeDialog();
                parent.refreshTab();
            });
        }
        else{
            layer.msg(resp, {
                icon: 2,
                time: 800
            }, function(){
            });
        }
	}});
}
// 汇总
// 机构汇总
function totalDeptDeal(isExport) {
    initDeptValue();
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "totalDeptDeal.action?startDate=" + startDate + "&endDate=" + endDate + "&deptId=" + getDeptSelect() + "&export=" + isExport + "&deptpath=" + $("#deptId").val();
}
// 大厅汇总
function totalDatingDeal(isExport) {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
    initDeptValue();
	window.location.href = "totalDatingDeal.action?startDate=" + startDate + "&endDate=" + endDate + "&deptId=" + getDeptSelect() + "&export=" + isExport + "&deptpath=" + $("#deptId").val();
}
// 窗口汇总
function totalWindowDeal(isExport) {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var deptId = getDeptSelect();
    initDeptValue();
	window.location.href = "totalWindowDeal.action?startDate=" + startDate + "&endDate=" + endDate + "&deptId=" + deptId + "&export=" + isExport + "&deptpath=" + $("#deptId").val();
}
// 个人汇总
function totalPersonAjax(data) {
	dojo.xhrPost({url:"totalPersonAjax.action", content:{id:data}, load:function (resp, ioArgs) {
		document.getElementById("window").innerHTML = resp;
	}});
}
// 部门汇总
function totalSectionDeal() {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "totalSectionDeal.action?startDate=" + startDate + "&endDate=" + endDate;
}
function totalPersonDeal(isExport) {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var deptids = document.getElementsByName("deptId");
    initDeptValue();
    var deptId = getDeptSelect();
    var eid = getEmployeeSelect();
	window.location.href = "totalPersonDeal.action?startDate=" + startDate + "&endDate=" + endDate + "&deptId=" + deptId + "&employeeId=" + eid + "&export=" + isExport +"&deptpath=" + $("#deptId").val();
}
function totalBusinessDeal() {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var deptids = document.getElementsByName("deptId");
	var deptId = -1;
	for (i = 0; i < deptids.length; i++) {
		if (eval(deptId) < eval(deptids[i].value)) {
			deptId = deptids[i].value;
		}
	}
	window.location.href = "totalBusinessDeal.action?startDate=" + startDate + "&endDate=" + endDate + "&deptId=" + deptId;
}

// 满意度统计
function totalDDeal(){
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var businessId =document.getElementById("businessId").value;
	window.location.href = "totalDDeal.action?startDate=" + startDate + "&endDate=" + endDate+"&businessId="+businessId ;
}



// 打印
function SetPrintSettings() {
	factory.printing.SetMarginMeasure(2);
	factory.SetPageRange(false, 1, 3);
	factory.printing.printer = "HP DeskJet 870C";
	factory.printing.copies = 2;
	factory.printing.collate = true;
	factory.printing.paperSize = "A4";
	factory.printing.paperSource = "Manual feed";
	factory.printing.header = "This is MeadCo";
	factory.printing.footer = "Advanced Printing by ScriptX";
	factory.printing.portrait = false;
	factory.printing.leftMargin = 1;
	factory.printing.topMargin = 1;
	factory.printing.rightMargin = 1;
	factory.printing.bottomMargin = 1;
}
function printsetup() {// 打印页面设置
	pr.style.display = "none";
	wb.execwb(8, 1);
	pr.style.display = "block";
}
function printpreview() {// 打印页面预览
	pr.style.display = "none";
	wb.execwb(7, 1);
	pr.style.display = "block";
}
function printit() {
	pr.style.display = "none";
	if (confirm("\u786e\u5b9a\u6253\u5370\u5417\uff1f")) {
		wb.execwb(6, 6);
	}
	pr.style.display = "block";
}
// 判断浏览器是否是ie
function isIe() {
	if (window.ActiveXObject) {
		return true;
	} else {
		return false;
	}
}
// 上传图片对话框
function basePicAjax(data) {
	dojo.xhrPost({url:"basePicAjax.action", content:{fileName:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
// 验证上传图片不为空
function basePicAjaxCheck() {
	if(document.getElementById("img").value==""){
		alert("请选泽图片");
	    return false;
	}else{
	    document.getElementById("pic123").submit();
	}

}

// 基本设置上传图片
function basePicUpload() {
	dojo.io.iframe.send({url:"basePicUpload.action", method:"post", handleAs:"text", form:dojo.byId("pic123"), handle:function (data, ioArgs) {
		closeDialog();
	}});
	window.location.reload();
}
// 根据部门显示在线员工
function baseOnLineEmployee2Ajax(data) {
	document.getElementById("deptId").value = data;
	dojo.xhrPost({url:"baseOnLineEmployee2Ajax.action", content:{deptId:data}, load:function (resp, ioArgs) {
		document.getElementById("employeeView").innerHTML = resp;
	}});
}
// 根据部门显示在线员工分页
function onLinePage(data){
	var deptId=document.getElementById("deptId").value ;
	var currentPage=data;
	dojo.xhrPost({url:"baseOnLineEmployee2Ajax.action", content:{deptId:deptId,currentPage:currentPage}, load:function (resp, ioArgs) {
		document.getElementById("employeeView").innerHTML = resp;
	}});
}
// 管理员提示类型
function tipChange(data) {
	if (data == 0) {
		document.getElementById("ext1").disabled = true;
		document.getElementById("ext2").disabled = true;
		document.getElementById("ext1").className="textDisable";
		document.getElementById("ext2").className="textDisable";

 	} else {
		if (data == 1) {
			document.getElementById("ext1").disabled = false;
			document.getElementById("ext2").disabled = true;
			document.getElementById("ext1").className="";
			document.getElementById("ext2").className="textDisable";
		} else {
			if (data == 2) {
				document.getElementById("ext1").disabled = true;
				document.getElementById("ext2").disabled = false;
				document.getElementById("ext1").className="textDisable";
				document.getElementById("ext2").className="";
			} else {
				if (data == 3) {
					document.getElementById("ext1").disabled = false;
					document.getElementById("ext2").disabled = false;
					document.getElementById("ext1").className="";
					document.getElementById("ext2").className="";
				}
			}
		}
	}
}
// 播放查詢
function playQueryAjax() {
	var keyword = document.getElementById("keyword").value;
	refreshTab({keyword:keyword});
}
// 播放翻页面
function playPage(data) {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"playQueryAjax.action", content:{keyword:keyword, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
// 播放 添加对话框
function playAddDialog(title) {
     new dialog().iframe("playAddDialog.action",{title:title});
}
// 上传
function playupload() {
	dojo.io.iframe.send({url:"playUpload.action", method:"post", handleAs:"text", form:dojo.byId("pic"), handle:function (data, ioArgs) {
		if (data != "") {
			document.getElementById("playSource").value = "" + data;
		}
	}});
}

function playTypeChange(data) {
    if(data=="text"){
        document.getElementById("txt1").style.display="";
        document.getElementById("txt2").style.display="";
        document.getElementById("other").style.display="none";
    }else{
        document.getElementById("txt1").style.display="none";
        document.getElementById("txt2").style.display="none";
        document.getElementById("other").style.display="";
    }
}
// 添加播放
function playAdd() {
	var playName = document.getElementById("playName").value;
	var playType = document.getElementById("playType").value;
	var playSource = document.getElementById("playSource").value;
	var playTimes=document.getElementById("playTimes").value;
	var playIndex=document.getElementById("playIndex").value;
	var playTitle=document.getElementById("playTitle").value;

	var playContent = document.getElementById("playContent").value;
	var patrn=/^[0-9]{1,20}$/;
	if (!patrn.exec(playIndex)){alert("序列只能为数字");document.getElementById("playIndex").focus();return false;}
 	dojo.xhrPost({url:"playAdd.action", content:{playName:playName, playType:playType, playSource:playSource,playTimes:playTimes,playIndex:playIndex,playTitle:playTitle,playContent:playContent}, load:function (resp, ioArgs) {
            if (resp.trim() == "") {
                layer.msg('添加成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    parent.closeDialog();
                    parent.refreshTab();
                });
            }
            else{
                layer.msg(resp, {
                    icon: 2,
                    time: 800
                }, function(){
                });
            }
	}});
}
// 播放修改对话框
function playEditDialog(playId, title) {
     new dialog().iframe("playEditDialog.action?playId="+playId, {title: title});
}
// 播放修改
function playEdit(data) {
	var playName = document.getElementById("playName").value;
	var playType = document.getElementById("playType").value;
	var playSource = document.getElementById("playSource").value;
	var playId = document.getElementById("playId").value;
	var playTimes=document.getElementById("playTimes").value;
	var playIndex=document.getElementById("playIndex").value;
	var playTitle=document.getElementById("playTitle").value;
// var playContent=FCKeditorAPI.GetInstance("playContent").GetXHTML(true);
	var playContent = document.getElementById("playContent").value;
	var patrn=/^[0-9]{1,20}$/;
	if (!patrn.exec(playIndex)){alert("序列只能为数字");document.getElementById("playIndex").focus();return false;}
	dojo.xhrPost({url:"playEdit.action", content:{playName:playName, playType:playType, playSource:playSource, playId:playId,playTimes:playTimes,playIndex:playIndex,playTitle:playTitle,playContent:playContent}, load:function (resp, ioArgs) {
            if (resp.trim() == "") {
                layer.msg('修改成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    parent.closeDialog();
                    parent.refreshTab();
                });
            }
            else{
                layer.msg(resp, {
                    icon: 2,
                    time: 800
                }, function(){
                });
            }
	}});
}
// 播放删除
function playDel(data) {
     if (confirm("确定要删除吗?")){
         dojo.xhrPost({url:"playDel.action", content:{playId:data}, load:function (resp, ioArgs) {
                 layer.msg('删除成功', {
                     icon: 1,
                     time: 800
                 }, function(){
                     refreshTab();
                 });
             }});
     }
}
// 播放列表查询
function playListQueryAjax() {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"playListQueryAjax.action", content:{keyword:keyword}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
// 播放列表查询 Android
function playListQueryAjaxAndroid() {
	var keyword = document.getElementById("keyword").value;
    refreshTab({keyword:keyword});
}
function playListPage(data){
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"playListQueryAjax.action", content:{keyword:keyword, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
// 播放列表 添加对话框
function playListAddDialog() {
	dojo.xhrPost({url:"playListAddDialog.action", load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
// 播放列表 添加对话框 android
function playListAddDialogAndroid(title) {
     new dialog().iframe("playListAddDialogAndroid.action",{title: title});
}

// 播放列表添加
function playListAdd() {
	var playListName = document.getElementById("playListName").value;
	var playListDescription = document.getElementById("playListDescription").value;
	var playIds = getAllKey();
	dojo.xhrPost({url:"playListAdd.action", content:{playListName:playListName, playListDescription:playListDescription, playIds:playIds}, load:function (resp, ioArgs) {
		playListQueryAjax();
		closeDialog();
	}});
}
// 播放列表添加
function playListAddAndroid() {
	var playListName = document.getElementById("playListName").value;
	var playListDescription = document.getElementById("playListDescription").value;
	var playIds = getAllKey();
	dojo.xhrPost({url:"playListAddAndroid.action", content:{playListName:playListName, playListDescription:playListDescription, playIds:playIds}, load:function (resp, ioArgs) {
            if (resp.trim() == "") {
                layer.msg('添加成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    parent.closeDialog();
                    parent.refreshTab();
                });
            }
            else{
                layer.msg(resp, {
                    icon: 2,
                    time: 800
                }, function(){
                });
            }
        }});
}

// 播放列表修改
function playListEditDialog(data) {
	dojo.xhrPost({url:"playListEditDialog.action", content:{playListId:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
// 播放列表修改Android
function playListEditDialogAndroid(playListId, title) {
     new dialog().iframe("playListEditDialogAndroid.action?playListId="+playListId, {title: title});
}
// 生成在线升级包
function playListCreateUpdateFile(data){
	alert(playListCreateUpdateFile);
	document.getElementById("pbar").src="images/update_processbar.gif";
	var playIds = getAllKey();
	dojo.xhrPost({url:"playListCreateUpdateFile.action", content:{playListId:data,playIds:playIds}, load:function (resp, ioArgs) {
		 document.getElementById("pbar").src="images/update_processend.gif";
		 alert("Bin生成成功");
		 closeDialog();
	}});
}
// 生成在线升级包,Zip格式
function playListCreateUpdateZip(data){
	document.getElementById("pbar").src="images/update_processbar.gif";
	var playIds = getAllKey();
	dojo.xhrPost({url:"playListCreateUpdateZip.action", content:{playListId:data,playIds:playIds}, load:function (resp, ioArgs) {
		document.getElementById("pbar").src="images/update_processend.gif";
		alert("Zip生成成功");
		closeDialog();
	}});
}

// 生成在线升级包,Zip格式和Bin格式
function playListCreateUpdateZipFile(data){
	document.getElementById("pbar").src="images/update_processbar.gif";
	var playIds = getAllKey();
	dojo.xhrPost({url:"playListCreateUpdateZip.action", content:{playListId:data,playIds:playIds}, load:function (resp, ioArgs) {
		document.getElementById("pbar").src="images/update_processend.gif";
            layer.msg('生成升级成功', {
                icon: 1,
                time: 800
            }, function(){
            });
	}, error:function(){
		alert("系统错误");
	}});
}

// 给文本框值
function setvalue() {
	document.getElementById("playIds").value = getAllKey();
}

// 播放列表修改
function playListEdit() {
	var ShowEmployeePage=1;
	 var Type=0;
	 var Version=document.getElementById("Version").value;
	 var Welcometime=document.getElementById("Welcometime").value;
	 var Approvertime=document.getElementById("Approvertime").value;
	 var Shutdownhh=document.getElementById("Shutdownhh").value;
	 var Shutdownmm=document.getElementById("Shutdownmm").value;
	 var Boothh=document.getElementById("Boothh").value;
	 var Bootmm=document.getElementById("Bootmm").value;
	// alert("Bootmm="+Bootmm);
	 ShowEmployeePage= document.getElementById("ShowEmployeePage").value;
	 // alert("ShowEmployeePage="+ShowEmployeePage);
	 var IP=document.getElementById("IP").value;
	 var Port=document.getElementById("Port").value;
	 var temp2=document.getElementsByName("Type");
	 if(temp2[0].checked){Type=temp2[0].value }
	 if(temp2[1].checked){Type=temp2[1].value }
	var playListName = document.getElementById("playListName").value;
	var playListDescription = document.getElementById("playListDescription").value;
	var playIds = document.getElementById("playIds").value;
	var playListId = document.getElementById("playListId").value;
	dojo.xhrPost({url:"playListEdit.action", content:{playListId:playListId, playListName:playListName, playListDescription:playListDescription, playIds:playIds,Version:Version,Approvertime:Approvertime,Welcometime:Welcometime,Shutdownhh:Shutdownhh,Shutdownmm:Shutdownmm,Boothh:Boothh,Bootmm:Bootmm,ShowEmployeePage:ShowEmployeePage,IP:IP,Port:Port,Type:Type}, load:function (resp, ioArgs) {
	    layer.msg('保存成功', {
                icon: 1,
                time: 800
            }, function(){
                parent.closeDialog();
                parent.refreshTab();
	    });
	}});
}
// 播放列表删除
function playListDel(data) {
	dojo.xhrPost({url:"playListDel.action", content:{playListId:data}, load:function (resp, ioArgs) {
	    alert(resp);
		playListQueryAjax();
	}});
}
// 播放列表删除Android
function playListDelAndroid(data) {
	if(data == 1){
		alert("默认列表不可删除！");
		return false;
	}

	if (confirm("确认要删除吗?")){
        dojo.xhrPost({url:"playListDelAndroid.action", content:{playListId:data}, load:function (resp, ioArgs) {
                layer.msg(resp, {
                    icon: 1,
                    time: 800
                }, function(){
                    refreshTab();
                });
        }});
    }
}
// 更新M7程序
function playListUpdateDialog(data) {
	dojo.xhrPost({url:"playListUpdateDialog.action", content:{playListId:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}

// 更新M7程序 Android
function playListUpdateDialogAndroid(data, title) {
     new dialog().iframe("playListUpdateDialogAndroid.action?playListId="+ data, {title: title});
}
// 配置m7 Config.xml 文件
function playListConfigDialog(data){
 dojo.xhrPost({url:"playListConfigDialog.action", content:{playListId:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
 }});
}
// 配置m7 Config.xml 文件 Android
function playListConfigDialogAndroid(data, title){
     new dialog().iframe("playListConfigDialogAndroid.action?playListId="+data, {title: title});
}

// 保存m7 Config.xml 文件
function playListConfigSave(){
 var rotate="";
 var showEmployeePage=1;
 var temp=document.getElementsByName("rotate");
 if(temp[0].checked){rotate=temp[0].value }
 if(temp[1].checked){rotate=temp[1].value }
 var hh=document.getElementById("hh").value;
 var mm=document.getElementById("mm").value;
 var update=document.getElementById("update").value;
 var CheckDelay=document.getElementById("CheckDelay").value;
 var NetDelay=document.getElementById("NetDelay").value;
 var version=document.getElementById("version").value;
 var ip=document.getElementById("ip").value;
 var port=document.getElementById("port").value;
 var approvertime=document.getElementById("approvertime").value;
 var xmlHttpOvertime=document.getElementById("xmlHttpOvertime").value;
 var showtimecontroltime=document.getElementById("showtimecontroltime").value;
 var showwelcometime=document.getElementById("showwelcometime").value;
 var readcardtime=document.getElementById("readcardtime").value;
 var playListId=document.getElementById("playListId").value;
 var temp2=document.getElementsByName("showEmployeePage");
 if(temp2[0].checked){showEmployeePage=temp2[0].value }
 if(temp2[1].checked){showEmployeePage=temp2[1].value }
 dojo.xhrPost({url:"playListConfigSave.action",
     content:{playListId:playListId,rotate:rotate,hh:hh,mm:mm,update:update,CheckDelay:CheckDelay,NetDelay:NetDelay,version:version,ip:ip,port:port,approvertime:approvertime,xmlHttpOvertime:xmlHttpOvertime,showtimecontroltime:showtimecontroltime,showwelcometime:showwelcometime,readcardtime:readcardtime,showEmployeePage:showEmployeePage},
     load:function (resp, ioArgs) {
 		 alert("保存成功")
 		 closeDialog();
 }});

}



// 保存m7 Config.xml 文件 Android版
function playListConfigSaveAndroid2(){
 var ShowEmployeePage=1;
 var Type=0;
 var Version=document.getElementById("Version").value;
 var Welcometime=document.getElementById("Welcometime").value;
 var Approvertime=document.getElementById("Approvertime").value;
 var Shutdownhh=document.getElementById("Shutdownhh").value;
 var Shutdownmm=document.getElementById("Shutdownmm").value;
 var Boothh=document.getElementById("Boothh").value;
 var Bootmm=document.getElementById("Bootmm").value;
// alert("Bootmm="+Bootmm);
 ShowEmployeePagep= document.getElementById("ShowEmployeePage").value;
// alert("ShowEmployeePage="+ShowEmployeePage);
 var IP=document.getElementById("IP").value;
 var Port=document.getElementById("Port").value;
 var temp2=document.getElementsByName("Type");
 if(temp2[0].checked){Type=temp2[0].value }
 if(temp2[1].checked){Type=temp2[1].value }
// var temp=document.getElementsByName("rotate");
// if(temp[0].checked){rotate=temp[0].value }
// if(temp[1].checked){rotate=temp[1].value }

 var playListId=document.getElementById("playListId").value;

 dojo.xhrPost({url:"playListConfigSaveAndroid.action",
	 content:{playListId:playListId,Version:Version,Approvertime:Approvertime,Welcometime:Welcometime,Shutdownhh:Shutdownhh,Shutdownmm:Shutdownmm,Boothh:Boothh,Bootmm:Bootmm,ShowEmployeePage:ShowEmployeePage,IP:IP,Port:Port,Type:Type},
	 load:function (resp, ioArgs) {
 		 alert("保存成功");
 }});


}
//保存m7 Config.xml 文件 Android版
function playListConfigSaveAndroid(){
 var ShowEmployeePage=1;
 var Type=0;
 var Version=document.getElementById("Version").value;
 var Welcometime=document.getElementById("Welcometime").value;
 var Approvertime=document.getElementById("Approvertime").value;
 var Shutdownhh=document.getElementById("Shutdownhh").value;
 var Shutdownmm=document.getElementById("Shutdownmm").value;
 var Boothh=document.getElementById("Boothh").value;
 var Bootmm=document.getElementById("Bootmm").value;
// alert("Bootmm="+Bootmm);
 var temp=document.getElementsByName("ShowEmployeePage");
 if(temp[0].checked){ShowEmployeePage=temp[0].value }
 if(temp[1].checked){ShowEmployeePage=temp[1].value }
// alert("ShowEmployeePage="+ShowEmployeePage);
 var IP=document.getElementById("IP").value;
 var Port=document.getElementById("Port").value;
 var temp2=document.getElementsByName("Type");
 if(temp2[0].checked){Type=temp2[0].value }
 if(temp2[1].checked){Type=temp2[1].value }
// var temp=document.getElementsByName("rotate");
// if(temp[0].checked){rotate=temp[0].value }
// if(temp[1].checked){rotate=temp[1].value }

 var playListId=document.getElementById("playListId").value;

 dojo.xhrPost({url:"playListConfigSaveAndroid.action",
	 content:{playListId:playListId,Version:Version,Approvertime:Approvertime,Welcometime:Welcometime,Shutdownhh:Shutdownhh,Shutdownmm:Shutdownmm,Boothh:Boothh,Bootmm:Bootmm,ShowEmployeePage:ShowEmployeePage,IP:IP,Port:Port,Type:Type},
	 load:function (resp, ioArgs) {
         layer.msg('修改成功', {
             icon: 1,
             time: 800
         }, function(){
             closeDialog();
         });
 }});


}

function  playListUpdateDeal(){
    var imgName= document.getElementById("imgName").value;
    if(imgName ==""){
     alert("请上传文件");
     return 0;
    }
    imgName=imgName.substring(imgName.length-3);
    if( imgName.toUpperCase()!= "ZIP"){
     alert("必须上传ZIP文件")
     return 0;
    }
    dojo.io.iframe.send({url:"playListUpdateDeal.action", method:"post", handleAs:"text", form:dojo.byId("temp"), handle:function (data, ioArgs) {
 		 alert(data);
		closeDialog();
	}});



}
// 更新M7，生成升级包，Andriod版本
function  playListUpdateDeal2(){
	var imgName= document.getElementById("imgName").value;
	if(imgName ==""){
		alert("请上传文件");
		return 0;
	}
	imgName=imgName.substring(imgName.length-3);
	if( imgName.toUpperCase()!= "ZIP"){
		alert("必须上传ZIP文件")
		return 0;
	}
	dojo.io.iframe.send({url:"playListUpdateDeal2.action", method:"post", handleAs:"text", form:dojo.byId("temp"), handle:function (data, ioArgs) {
		alert(data);
		closeDialog();
	}});



}
// 更新M7，生成升级包，Andriod版本和WinCE版本一块生成
function  playListUpdateDeal3(){
	var imgName= document.getElementById("imgName").value;
	if(imgName ==""){
		alert("请上传文件");
		return 0;
	}
	imgName=imgName.substring(imgName.length-3);
	if( imgName.toUpperCase()!= "ZIP"){
		alert("必须上传ZIP文件")
		return 0;
	}
	dojo.io.iframe.send({url:"playListUpdateDeal.action", method:"post", handleAs:"text", form:dojo.byId("temp"), handle:function (data, ioArgs) {
	}});
	dojo.io.iframe.send({url:"playListUpdateDeal2.action", method:"post", handleAs:"text", form:dojo.byId("temp"), handle:function (data, ioArgs) {
 		 alert("Zip、Bin更新成功");
		closeDialog();
	}});


}


// 得到月初时间
function getdate() {
	var dt = new Date();
	var year = dt.getFullYear();
	var mm = dt.getMonth() + 1;
	var dd = dt.getDate();
	if (("" + mm).length == 1) {
		mm = "0".concat(mm);
	}
	if (("" + dd).length == 1) {
		dd = "0".concat(dd);
	}
	var startDate = document.getElementById("startDate");
	if (startDate != null) {
	  if(startDate.value ==""){
	     startDate.value = "".concat(year).concat("-").concat(mm).concat("-").concat("01").concat(" ").concat("00:00:00");
	  }
	}
	var endDate = document.getElementById("endDate");
	if (endDate != null) {
		if(endDate.value ==""){
         endDate.value = "".concat(year).concat("-").concat(mm).concat("-").concat(dd).concat(" ").concat("23:59:59");;
		}
	}
}
window.onload = getdate;
function quicklyAddDialog(){
	dojo.xhrPost({url:"quicklyAddDialog.action", load:function (resp, ioArgs) {
	    document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}

function queryExcel(){
    initDeptValue();
    var deptId = "";
    var d = $("#deptId").val().split(",");
    if(d.length>0)
    {
        for (var i=d.length-1; i--; i>-1){
            if(d[i]!=''){
                deptId = d[i];
                break;
            }
        }
    }
    var employeeId = getEmployeeSelect();
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;
    var deptIds = getDeptSelect();
    var keys = $("#result").val()?$("#result").val():"";
    //var myURL = parseURL(location.href);
    //var _newUrl = replaceUrlParams(myURL, { id: employeeId, startDate:startDate,endDate: endDate,deptIds: deptIds,keys:keys,deptpath:$("#deptId").val() });
    //document.location.href = "queryZhDeal.action?id=" + employeeId + "&startDate=" + startDate + "&endDate=" + endDate + "&deptIds=" + deptIds + "&keys=" + keys;
    //document.location.href = _newUrl;

 /* var deptIds=document.getElementById("deptIds").value;
  var startDate=document.getElementById("startDate").value;
  var endDate=document.getElementById("endDate").value;
  var keys=document.getElementById("keys").value;
  var id=document.getElementById("id").value;*/
  window.location.href="queryExcel.action?deptIds="+deptIds+"&startDate="+startDate+"&endDate="+endDate+"&keys="+keys+"&id="+employeeId
}
// 导出M7在线信息为Excel
function m7InfoExcel(){
	var deptIds=document.getElementById("deptIds").value;
	var startDate=document.getElementById("startDate").value;
	var endDate=document.getElementById("endDate").value;
	var keys=document.getElementById("keys").value;
	var id=document.getElementById("id").value;
	window.location.href="queryExcel.action?deptIds="+deptIds+"&startDate="+startDate+"&endDate="+endDate+"&keys="+keys+"&id="+id
}

function quicklyAdd(){
	var quicklyQueryName=document.getElementById("quicklyQueryName").value;
	if(quicklyQueryName==""){
		alert("请输入快速查询名");
		return false;
	}
	var url=window.location.href;
	dojo.xhrPost({url:"quicklyAdd.action",content:{quicklyQueryName:quicklyQueryName,url:url},load:function (resp, ioArgs){
		closeDialog();
		alert("添加成功");
	}});
}
function fullScreen(){
  var   wsh   =   new   ActiveXObject("WScript.Shell");
  wsh.sendKeys("{F11}") ;
  var f=document.getElementById("man_nav_6");
  if(f.className=='bg_image_onclick'){
  	f.className='bg_image';
  }else{
  	f.className='bg_image_onclick';
  }
}
// 帮助系统
function helper(){
   var f=document.getElementById("man_nav_6");
  if(f.className=='bg_image_onclick'){
  	f.className='bg_image';
  }else{
  	f.className='bg_image_onclick';
  }
}

function getStartDate(){
	return   document.getElementById("startDate").value;
}
function getEndDate(){
	return   document.getElementById("endDate").value;
}
function trim(str)   { return str.replace(/(^\s*)|(\s*$)/g, ""); }

function  managerQx(){
	dojo.xhrPost({url:"managerQx.action",load:function (resp, ioArgs){
 		 if(trim(resp)=="noqx"){
		 	alert("你没有权限访问此功能");
		 }else{
		 	window.top.frames['manFrame'].location = 'deptView.action';
		 }
	}});
}
function attendanceQueryAjax(){
	var startDate="";
	var endDate="";
	if(document.getElementById("startDate")!=null){
	 startDate=document.getElementById("startDate").value;
	}
	if(document.getElementById("endDate")!=null){
	 endDate=document.getElementById("endDate").value;
	}
	dojo.xhrPost({url:"attendanceQueryAjax.action",content:{startDate:startDate,endDate:endDate},load:function (resp, ioArgs){
		document.getElementById("man_zone").innerHTML=resp;
	}});
}

function attendancePage(data){
	var startDate="";
	var endDate="";
	if(document.getElementById("startDate")!=null){
	 startDate=document.getElementById("startDate").value;
	}
	if(document.getElementById("endDate")!=null){
	 endDate=document.getElementById("endDate").value;
	}
	dojo.xhrPost({url:"attendanceQueryAjax.action",content:{startDate:startDate,endDate:endDate,currentPage:data},load:function (resp, ioArgs){
		document.getElementById("man_zone").innerHTML=resp;
	}});
}
function attendanceNO(){
	var startDate="";
	var endDate="";
	if(document.getElementById("startDate")!=null){
	 startDate=document.getElementById("startDate").value;
	}
	if(document.getElementById("endDate")!=null){
	 endDate=document.getElementById("endDate").value;
	}
	dojo.xhrPost({url:"attendanceNO.action",content:{startDate:startDate,endDate:endDate},load:function (resp, ioArgs){
		document.getElementById("man_zone").innerHTML=resp;
	}});
}
function htmltoexcel(tableid) {// 整个表格拷贝到EXCEL中
     var curTbl = document.getElementById(tableid);
     var d=document.createElement("DIV");
     d=document.getElementById(tableid);

      try {
        var oXL = new ActiveXObject("Excel.Application");
      } catch (e) {
      	alert("导出失败,请确认你安装了excel，并设置ie安全级别为底");
      	return false;
      }
      // 创建AX对象excel
     var oWB = oXL.Workbooks.Add();
     // 获取workbook对象
         var oSheet = oWB.ActiveSheet;
     // 激活当前sheet
     var sel = document.body.createTextRange();
     sel.moveToElementText(d);
     // 把表格中的内容移到TextRange中
     sel.select();
     // 全选TextRange中内容
     sel.execCommand("Copy");
     // 复制TextRange中内容
     oSheet.Paste();
     // 粘贴到活动的EXCEL中
     oXL.Visible = true;
     // 设置excel可见属性
 }
 function  printHTML(path,Data)
  {
      var   fso,   f,   r
      var   ForReading   =   1,   ForWriting   =   2;
      fso   =   new   ActiveXObject("Scripting.FileSystemObject")
      var   objShell   =   new   ActiveXObject("wscript.shell");
       f   =   fso.OpenTextFile("c:\\print.htm",   ForWriting,   true)
      var html= "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd\"\>\r\n";
      html=html+"<!-- saved from url=(0043)http://www.zxbc.cn/html/20080418/33444.html --\>\r\n";
      html=html+"<HTML xmlns=\"http://www.w3.org/1999/xhtml\"\>\r\n"
      html=html+"<BODY\>\r\n";
      html=html+"afdasdfasdf\r\n";
      html=html+"</BODY\>\r\n"
      html=html+"</HTML\>\r\n"
      html=html+"";
      f.Write(html);
      f.Close();
      window.showModalDialog('c:\\print.htm','打印',"resizable=no;help=no;center:yes;scroll=no;status=yes;dialogHeight=768px;dialogWidth=1024px");

   }
// 首页调用js
// 评价信息详细
function indexDetail(deptIds,keys,name){
//	 var now=new Date();
//	 y=now.getFullYear();
//	 m=now.getMonth()+1;
//	 d=now.getDate();
//	 m=m<10?"0"+m:m;
//	 d=d<10?"0"+d:d;
	 // startDate=y+"-"+m+"-01 00:00:00"
	 // endDate=y+"-"+m+"-"+d+" "+"12:59:59"
	 var startDate="";
	 var endDate="";

     var url="queryZhDeal.action?id=0&startDate="+startDate+"&endDate="+endDate+"&deptIds="+deptIds+"&keys="+keys+"&currentPage=1";
    addTabMenu("首页",name,url,null,true);
}
// 首页饼图详细

function indexCake(deptIds){
	 var now=new Date();
	 y=now.getFullYear();
	 m=now.getMonth()+1;
	 d=now.getDate();
	 m=m<10?"0"+m:m;
	 d=d<10?"0"+d:d;
	 // startDate=y+"-"+m+"-01 00:00:00"
	 // endDate=y+"-"+m+"-"+d+" "+"12:59:59"
	 startDate="";
	 endDate="";
    var url = "queryZhDeal.action?id=0&startDate="+startDate+"&endDate="+endDate+"&deptIds="+deptIds+"&keys=";
    addTabMenu('首页',"满意饼图",url,null,true);
}
// 鼠标经过时选择当前
function selectCurrent(data){
	var d=document.getElementById("findreslut");
	var child=d.getElementsByTagName("div")
	for(var index=0; index<child.length; index++){
		child[index].className="";
	}
	data.className="hover" ;
}
var flagemployeefind=0; // 全局变量，判断是否是从查询结果中返回，如果是为1 ，不是为0
// 选择员工卡号
function setcardnum(data){
	var d=document.getElementById("findreslut");
	d.innerHTML="";
	d.style.display="none";
	document.getElementById("dept_camera_url").value=data;
	document.getElementById("dept_camera_url").focus();
 	flagemployeefind=1;
}
// 查询员工信息
function employeeFindByCardnumOrName(){
	if(flagemployeefind == 0){
		if(event.keyCode!=40 && event.keyCode!=38){
			var keyword=document.getElementById("dept_camera_url").value;
			dojo.xhrPost({url:"employeeFindByCardnumOrName.action",content:{keyword:keyword},load:function (resp, ioArgs){
				document.getElementById("findreslut").innerHTML=resp;
				document.getElementById("findreslut").style.display='block';
			}});
		}
		// 按向下方向键
		if (event.keyCode==40){
	 		var d=document.getElementById("findreslut");
	 		d.focus();
	 		var child=d.getElementsByTagName("div")
	 		child[0].className="hover" ;
	 	}
	 	// 按向下方向键
		if (event.keyCode==38){
	 		var d=document.getElementById("findreslut");
	 		d.focus();
	 		var child=d.getElementsByTagName("div")
	 		child[4].className="hover" ;
	 	}
 	}
 	flagemployeefind=0;
}
function  employeeresultdown(){
 	    // 得到当前选中的项 i
 		var d=document.getElementById("findreslut");
  		var child=d.getElementsByTagName("div")
  		var i=0;
  		for(var index=0; index<child.length; index++) {
  			if ( child[index].className=="hover") {
  				 i=index;
  				 break;
  			}
  		}
  		if (event.keyCode==40){
  			// 清除选中
	  		for(var index=0; index<child.length; index++) {
	  			 child[index].className="";
	  		}
  			if (i<4){
  				child[i+1].className="hover";
  			}else{
  				document.getElementById("dept_camera_url").focus();
  			}
  		}
  		if (event.keyCode==38){
  			// 清除选中
	  		for(var index=0; index<child.length; index++) {
	  			 child[index].className="";
	  		}
  			if (i==0){
  				document.getElementById("dept_camera_url").focus();
  			}else{
  				child[i-1].className="hover";
  			}
  		}
  		if (event.keyCode==13){
  			for(var index=0; index<child.length; index++) {
  				 if(child[index].className=="hover"){
  				 	document.getElementById("dept_camera_url").value= document.getElementById("e"+index).value
			 		var d=document.getElementById("findreslut");
					d.innerHTML="";
					d.style.display="none";
					break;
  				 }
 	  		}
 	  		document.getElementById("dept_camera_url").focus();
 	  		flagemployeefind=1;
  		}
 }
 function showFAQ(data){
 	var faq=document.getElementById("faq");
 	var ul=	faq.getElementsByTagName("ul");
 	for(var index=0; index<ul.length; index++) {
 		ul[index].style.display="none";
 	}
 	document.getElementById("c"+data).style.display="block";
 }
 // 留言板
 function lybAdd(){
	dojo.xhrPost({url:"lybAdd.action", load:function (resp, ioArgs) {
	    document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
 }

  function lybQueryAjax(){
	dojo.xhrPost({url:"lybQueryAjax.action", load:function (resp, ioArgs) {
	    document.getElementById("man_zone").innerHTML = resp;
	}});
 }

 function lybAddDeal(){
 	var lybTitle=document.getElementById("lybTitle").value;
 	var lybAsk=document.getElementById("lybAsk").value;
 	dojo.xhrPost({url:"lybAddDeal.action",content:{lybTitle:lybTitle,lybAsk:lybAsk},load:function (resp, ioArgs){
			closeDialog();
			lybQueryAjax();
	}});

 }
 function lybDel(data){
 	 	dojo.xhrPost({url:"lybDel.action",content:{id:data},load:function (resp, ioArgs){
			lybQueryAjax();
	}});
 }
 function lybEdit(data){
	dojo.xhrPost({url:"lybEdit.action",content:{id:data}, load:function (resp, ioArgs) {
	    document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
 }
  function lybEditDeal(){
 	var lybId=document.getElementById("lybId").value;
 	var lybAnswer=document.getElementById("lybAnswer").value;
 	dojo.xhrPost({url:"lybEditDeal.action",content:{lybId:lybId,lybAnswer:lybAnswer},load:function (resp, ioArgs){
			closeDialog();
			lybQueryAjax();
	}});
 }
 // 切换 ，选中当前
 function showN(data){
    for (var i=0;i<=20;i++){
       var top=document.getElementById("top"+i);
       var content=document.getElementById("content"+i);
       if(top!=null  && content!=null ){
          if(i==data){
             top.className="current";
             content.style.display="block";
          }else{
          	 top.className="";
             content.style.display="none";
          }
        }
    }
 }
// 上传图片
function baseUploadPic(){
 	dojo.xhrPost({url:"baseUploadPic.action",load:function (resp, ioArgs){
	    document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
function baseUploadPicDeal(){
	dojo.io.iframe.send({url:"baseUploadPicDeal.action", method:"post", handleAs:"text", form:dojo.byId("pic123"), handle:function (data, ioArgs) {
 		if (data != "") {
			document.getElementById("logo").value = "" + data;
			var img123 = document.getElementById("picLogo");
			img123.src = data;
            $('#picLogo').parent().show();
		}
		closeDialog();
	}});
}


// 上传m7模板
function baseUploadTemp(){
 	dojo.xhrPost({url:"baseUploadTemp.action",load:function (resp, ioArgs){
	    document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}

function baseUploadTempDeal(){
	dojo.io.iframe.send({url:"baseUploadTempDeal.action", method:"post", handleAs:"text", form:dojo.byId("temp"), handle:function (data, ioArgs) {
 		if (data != "") {
  			var info=data.parseJSON();
  			 var temps=document.getElementById("temps");
  			 var temp=document.createElement("li");
  			 temp.innerHTML ="<img style='width:191px; height:129px;' src='m7temp/"+info.tempUrl+ "/k.jpg' /> <br/>";
  			 temp.innerHTML =temp.innerHTML + ""+info.tempName+"<input type='button' value='预览' onclick='basePreviewTemp(this,\""+info.tempUrl+"\")' />    <input type='button' value='删除' onclick='baseDelTemp(this)' />";
  			 temps.appendChild(temp);
 		}
		closeDialog();
	}});
}
// 删除模板
function baseDelTemp(data){
   var temps=document.getElementById("temps");
   for(var i=0;i<temps.childNodes.length;i++){
      if(data.parentNode ==temps.childNodes[i]){
        dojo.xhrPost({url:"baseDelTemp.action",content:{index:i},load:function (resp, ioArgs){
		    alert(resp);
		}});
        break;
      }
    }
  temps.removeChild(data.parentNode);
}
// 预浏模板
function basePreviewTemp(data,tempUrl){
	  var temps=document.getElementById("temps");
	  for(var i=0;i<temps.childNodes.length;i++){
	      if(data.parentNode ==temps.childNodes[i]){
	        dojo.xhrPost({url:"basePreviewTemp.action",content:{index:i,tempUrl:tempUrl},load:function (resp, ioArgs){
			    document.getElementById("dialog").innerHTML = resp;
				var dia = new dialog();
				dia.show("dialog");
			}});
	        break;
	      }
	   }
}
// 预览模板k3,k4,k5,k6
function basePreviewTempK(k){
    document.getElementById("k").value=k;
    document.getElementById("picTemp").src="m7temp/"+document.getElementById("tempUrl").value+"/"+k+"/k.jpg";
}
function baseSelectTemp(){
     var index=document.getElementById("index").value;
     var k=document.getElementById("k").value;
     var playListId=document.getElementById("playListId").value;
	 dojo.xhrPost({url:"baseSelectTemp.action",content:{index:index,k:k,playListId:playListId},load:function (resp, ioArgs){
	 	var temp=resp.parseJSON();
 	    closeDialog();
        var temps=document.getElementById("temps");
         for(var i=0;i<temps.childNodes.length;i++){
          temps.childNodes[i].className="";
         }
        temps.childNodes[index].className="curentTemp"
        var o=temps.childNodes[index].getElementsByTagName("img")[0];
        o.src="m7temp/"+temp.tempUrl+"/"+temp.k+"/k.jpg"
 	 }});
}
// 删除星级
function starDel(data){
	 var stars=document.getElementById("stars");
	 if(stars.childNodes.length>2){
		  stars.removeChild(data.parentNode);
		 }else{
		  alert("最少要两个");
	 }
}
// 增加星级
function starAdd(){
	 var stars=$("#stars");
	 if(stars.find("li").length>=10){
		   alert("最多10个");
		   return 0;
	 }
 	 var star=document.createElement("li");
	 star.innerHTML ="<b>满意率：</b><input type='text'   class='input_comm' name='prate' /><b>以上</b><input type='text' style=\"display:none;\" class='input_comm' value='0' name='pgrade'/><b style=\"display:none;\">分</b><input type='text'   class='input_comm' name='pstar' /><b>星</b>  <img src='images/tp_add.gif'  onclick='starAdd()'/> <img src='images/tp_del.gif' onclick='starDel(this)' />"
 	 stars[0].appendChild(star);
}
// 保存设置
function sundynSetSave(){
    var camera="" // 是否使用监控
	var k7=""// 是否使用未评价按键
	var star=""// 是否使用自定义星级
	var bind=""// 是否绑定用户名
	var guide=""// 是否绑定用户名
	var title="";// 存放单位名称
	var logo="";// 存放logo
	var buttom="";// 用于存m7底部显示文件
	var requestAddress = "";// 用于存放不满意评价信息的访问地址
	var standard="";
	var sam="";// 上午上班时间
	var eam="";// 上午下班时间
	var spm="";// 下午上班时间
	var epm="";// 下午下班时间
	var prates="";// 存放满意率
	var pgrades="";// 存放10分等级
	var pstars="";// 存放星级
	var  tipLanguage="";
	var est1="";
	var est2="";
	var est3="";
	var est4="";
	var est5="";
	var est6="";
	var est7="";
	var est8="";
	var est9="";
	var coun=0;
	// 得到系统设置
	if(document.getElementById("est1").checked){
		est1="true";
		coun = coun+1;
	}else{
		est1 = "false";
	}
	if(document.getElementById("est2").checked){
		est2="true";
		coun = coun+1;
	}else{
		est2 = "false";
	}
	if(document.getElementById("est3").checked){
		est3="true";
		coun = coun+1;
	}else{
		est3 = "false";
	}
	if(document.getElementById("est4").checked){
		est4="true";
		coun = coun+1;
	}else{
		est4 = "false";
	}
	if(document.getElementById("est5").checked){
		est5="true";
		coun = coun+1;
	}else{
		est5 = "false";
	}
	if(document.getElementById("est6").checked){
		est6="true";
		coun = coun+1;
	}else{
		est6 = "false";
	}
	if(document.getElementById("est7").checked){
		est7="true";
		coun = coun+1;
	}else{
		est7 = "false";
	}
	if(document.getElementById("est8").checked){
		est8="true";
		coun = coun+1;
	}else{
		est8 = "false";
	}
	if(document.getElementById("est9").checked){
		est9="true";
		coun = coun+1;
	}else{
		est9="false";
	}
	if(coun>5){
		alert('员工信息配置最多可以选择五个选项');
		return;
	}
	if(document.getElementById("k7").checked){
	  k7="true";
	}else{
	  k7="false";
	}
	if(document.getElementById("star").checked){
	  star="true";
	}else{
	  star="false";
	}
	if(document.getElementById("bind").checked){
	  bind="true";
	}else{
	  bind="false";
	}
	if(document.getElementById("guide").checked){
	  guide="true";
	}else{
	  guide="false";
	}
	tipLanguage=document.getElementById("tipLanguage").value;
	// 得到标题，logo
	title=document.getElementById("title").value;
	logo=document.getElementById("logo").value;
	buttom = document.getElementById("buttom").value;
	requestAddress = document.getElementById("requestAddress").value;
	standard=document.getElementById("standard").value;
	// 得到上下班时间
	sam=document.getElementById("sam").value;
	eam=document.getElementById("eam").value;
	spm=document.getElementById("spm").value;
	epm=document.getElementById("epm").value;
	// 得到星级
	var stars=document.getElementById("stars");
	var prate="101";
	var pgrade="11";
	var pstar="**********";
	var prateN = document.getElementsByName("prate");
	var pgradeN = document.getElementsByName("pgrade");
	var pstarN = document.getElementsByName("pstar")
	for(var i=0;i<prateN.length;i++){
	    if(prateN[i].value=="")
        {
            alert("满意率不能为空");
            return 0;
        }
        if(pstarN[i].value=="")
        {
            alert("星级不能为空");
            return 0;
        }
	    if(eval(prateN[i].value)<=eval(prate)){
	     	prate=prateN[i].value;
	     	prates=prates+prate+",";
	    }else{
	        /*document.getElementById("content1").style.display="none";
	        document.getElementById("content2").style.display="none";
	        document.getElementById("content3").style.display="none";
	        document.getElementById("content4").style.display="none";
	        document.getElementById("content5").style.display="block";*/
	        pgradeN[i].focus();
	        alert("下面的数据，只能小于等于上面的数据");
	        return 0;
 	    }
	    if( eval(pgradeN[i].value)<=eval(pgrade)){
	     	pgrade= pgradeN[i].value;
	     	pgrades=pgrades+pgrade+",";
	    }else{
	        document.getElementById("content1").style.display="none";
	        document.getElementById("content2").style.display="none";
	        document.getElementById("content3").style.display="none";
	        document.getElementById("content4").style.display="none";
	        document.getElementById("content5").style.display="block";
	        pgradeN[i].focus();
	        alert("下面的数据，只能小于等于上面的数据");
	        return 0;
 	    }
	    // if( stars.childNodes[i].childNodes[5].value<=pstar){
	     	pstar=pstarN[i].value;
	     	pstars=pstars+pstar+",";
	   // }else{
	      // document.getElementById("content1").style.display="none";
	       // document.getElementById("content2").style.display="none";
	        // document.getElementById("content3").style.display="none";
	       // document.getElementById("content4").style.display="none";
	        //
	       // document.getElementById("content5").style.display="block";
	       // stars.childNodes[i].childNodes[5].focus();
	        // alert("下面的数据，只能小于等于上面的数据");
	      // return 0;
 	   // }
	}
  	 dojo.xhrPost({url:"baseSetSave.action",content:{camera:camera,k7:k7,star:star,bind:bind,guide:guide,tipLanguage:tipLanguage,title:title,logo:logo,sam:sam,eam:eam,spm:spm,epm:epm,prates:prates,pgrades:pgrades,pstars:pstars,buttom:buttom,requestAddress:requestAddress,standard:standard,est1:est1,est2:est2,est3:est3,est4:est4,est5:est5,est6:est6,est7:est7,est8:est8,est9:est9},load:function (resp, ioArgs){
             layer.msg(resp, {
                 icon: 1,
                 time: 800
             }, function(){
             });
	 }});
}
// 查看投票
function voteResult(data){
  dojo.xhrPost({url:"voteDialog.action",content:{voteId:data},load:function (resp, ioArgs){
	     document.getElementById("dialog").innerHTML = resp;
		 var dia = new dialog();
		 dia.show("dialog");
 }});
}
// 使用当前投票选项
function voteUse(data){
 dojo.xhrPost({url:"voteUse.action",content:{voteId:data},load:function (resp, ioArgs){
	document.getElementById("vote").innerHTML=resp;
 }});
}
// 删除投票
function voteDel(data){
  dojo.xhrPost({url:"voteDel.action",content:{voteId:data},load:function (resp, ioArgs){
	document.getElementById("vote").innerHTML=resp;
 }});
}
// 添加投票
function voteAdd(){
  var voteTitle=document.getElementById("voteTitle").value;
  if(voteTitle==""){alert("投票标题不能为空");document.getElementById("voteTitle").focus(); return 0;}
  var voteSelect=document.getElementById('voteSelect');
  var voteSelects="";
  var voteNum=0;
  for(var i=0;i<voteSelect.childNodes.length;i++){
     if(voteSelect.childNodes[i].childNodes[1].value== ''){
       voteSelect.childNodes[i].childNodes[1].focus();
       alert("投票选项不能为空");
       return 0;
     }
     voteSelects=voteSelects+voteSelect.childNodes[i].childNodes[1].value+","
  }
  if (document.getElementById("voteNum").checked  ){
    	voteNum=1;
  	}else{
        voteNum=0;
  }
  dojo.xhrPost({url:"voteAdd.action",content:{voteSelects:voteSelects,voteNum:voteNum,voteTitle:voteTitle},load:function (resp, ioArgs){
	document.getElementById("vote").innerHTML=resp;
  }});
  // 清理数据
  document.getElementById("voteTitle").value="";
  for(var i=0;i<voteSelect.childNodes.length;i++){
     voteSelect.childNodes[i].childNodes[1].value="";
  }
  document.getElementById("voteNum").checked =false;
}
// 投票取消
function voteCancel(){
  ans=window.confirm('你确认取消所有投票信息？');
 // alert (ans);
 if (ans==true)
  {
  document.getElementById("voteTitle").value="";
  var voteSelect=document.getElementById("voteSelect");
  for(var i=0;i<voteSelect.childNodes.length;i++){
     voteSelect.childNodes[i].childNodes[1].value="";
  }
  document.getElementById("voteNum").checked =false;
  // 撤消所有的投票功能
   dojo.xhrPost({url:"voteCancle.action",content:{},load:function (resp, ioArgs){
	document.getElementById("vote").innerHTML=resp;
  }});

   }
}

// 删除投票选项
function voteSelectDel(data){
	 var voteSelect=document.getElementById("voteSelect");
	 if(voteSelect.childNodes.length>2){
		  voteSelect.removeChild(data.parentNode);
		 }else{
		  alert("最少要两个");
	 }
}
// 增加投票选项
function voteSelectAdd(){
	 var voteSelect=document.getElementById("voteSelect");
	 if(voteSelect.childNodes.length>9){
	  alert("最多十个选项");
	  return 0;
	 }
 	 var select=document.createElement("li");
	 select.innerHTML =" 	选项：<input type='text' name='voteSelect'   /><img src='images/tp_add.gif' onclick='voteSelectAdd()' /><img src='images/tp_del.gif' onclick='voteSelectDel(this)' />"
 	 voteSelect.appendChild(select);
}
// 保存业务类型
function businessUpdate(){
   	var businessId=document.getElementsByName("businessId");
	var businessName=document.getElementsByName("businessName");
	var businessDescription=document.getElementsByName("businessDescription");
	var businessIsUse=document.getElementsByName("businessIsUse");
	var businessIds="";
	var businessNames="";
	var businessDescriptions="";
	var businessIsUses="";
	for(var i=0;i<businessId.length;i++){
	   businessIds=businessIds+businessId[i].value+",";
	}
	for(var i=0;i<businessName.length;i++){
	   businessNames=businessNames+businessName[i].value+",";
	}
	for(var i=0;i<businessDescription.length;i++){
	   businessDescriptions=businessDescriptions+businessDescription[i].value+",";
	}
	for(var i=0;i<businessIsUse.length;i++){
 	   businessIsUses=businessIsUses+businessIsUse[i].checked+",";
	}
	dojo.xhrPost({url:"businessUpdate.action",content:{businessIds:businessIds,businessNames:businessNames,businessDescriptions:businessDescriptions,businessIsUses:businessIsUses},load:function (resp, ioArgs){
	   alert(resp);
	}});
}
function businessCancel(){
	window.location.reload();
}
// 点左边显示右边
function businessReg(data) {
	document.getElementById("businessId").value = data;
	dojo.xhrPost({url:"businessReg.action", content:{id:data}, load:function (resp, ioArgs) {
		document.getElementById("deptView").innerHTML = resp;
	}});
}
// 添加业务类型
function businessAddDialog(){
	dojo.xhrPost({url:"businessAddDialog.action",   load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
// 添加业务类型
function businessAdd() {
	if (document.getElementById("businessName").value == "") {
		alert("问卷标题不能为空");
		document.getElementById("businessName").focus();
		return false;
	}
	var businessName=document.getElementById("businessName").value ;
	var businessDescription=document.getElementById("businessDescription").value ;
    var businessFatherId=document.getElementById("businessId").value;
	dojo.xhrPost({url:"businessAdd.action", content:{businessName:businessName, businessDescription:businessDescription, businessFatherId:businessFatherId}, load:function (resp, ioArgs) {
		var businessList = eval(resp);
		d = new dTree("d");
		for (i = 0; i < businessList.length; i++) {
			d.add(businessList[i].businessId, businessList[i].businessFatherId, businessList[i].businessName, "javascript:businessReg(" + businessList[i].businessId + ")");
		}
		document.getElementById("tree").innerHTML = d;
		closeDialog();
	}});
}
// 修改业务类型对话框
function businessEditDialog(data){
	dojo.xhrPost({url:"businessEditDialog.action",content:{businessId:data},load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
// 修改业务类型
function businessEdit(){
    var businessName=document.getElementById("businessName").value ;
	var businessDescription=document.getElementById("businessDescription").value ;
    var businessId=document.getElementById("businessId").value;
	dojo.xhrPost({url:"businessEdit.action",content:{businessId:businessId,businessName:businessName,businessDescription:businessDescription},load:function (resp, ioArgs) {
		var businessList = eval(resp);
		d = new dTree("d");
		for (i = 0; i < businessList.length; i++) {
			d.add(businessList[i].businessId, businessList[i].businessFatherId, businessList[i].businessName, "javascript:businessReg(" + businessList[i].businessId + ")");
		}
		document.getElementById("tree").innerHTML = d;
		closeDialog();
	}});
}
// 删除业务类型
function businessDel(data){
    if(data==1){
     alert("不允许删除根节点")
     return 0;
    }
	dojo.xhrPost({url:"businessDel.action",content:{businessId:data},load:function (resp, ioArgs) {
		var businessList = eval(resp);
		d = new dTree("d");
		for (i = 0; i < businessList.length; i++) {
			d.add(businessList[i].businessId, businessList[i].businessFatherId, businessList[i].businessName, "javascript:businessReg(" + businessList[i].businessId + ")");
		}
		document.getElementById("tree").innerHTML = d;
		closeDialog();
	}});
}
// 导出问卷
function businessExportDc(){
  	window.location.href="businessExportDc.action";
}
// 导出表头
function businessExportTitle(){
  	window.location.href="businessExportTitle.action";
}
// 导入数据
function businessImportDialog(){
	dojo.xhrPost({url:"businessImportDialog.action", load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
// 处理导入数据
function businessImport(){
    var img=document.getElementById("img").value;
    if(img==""){
      alert("请选择导入文件");
      return  0;
    }
	dojo.io.iframe.send({url:"businessImport.action", method:"post", handleAs:"text", form:dojo.byId("temp"), handle:function (data, ioArgs){
	       if(data=="导入成功"){
	         alert(data);
 			 closeDialog();
	       }else{
	         alert(data);
	       }
	}});
}
// 手工输入问卷
function businessWj(){
	window.location.href="businessWj.action"
}
// 处理问卷信息
function businessWjDeal(){
	 var cardNum=document.getElementById("cardNum").value;
	 var mac=document.getElementById("mac").value;
	 var input=document.getElementsByTagName("input");
	 var id =document.getElementById("id").value;
	 for(var i=0;i<input.length;i++){
	    if(input[i].value==""){
		    alert("调查数据不能为空");
		    input[i].focus()
		    return 0;
	    }
 	 }
 	 var business="";
 	 for(var i=0;i<input.length;i++){
	   if(input[i].name.substring(0,8)=="business"){
 	     business=business+input[i].value+","
 	   }
 	 }
 	 dojo.xhrPost({url:"businessWjDeal.action",content:{cardNum:cardNum,mac:mac,business:business,id:id},load:function (resp, ioArgs) {
 	     if(resp == "写入成功"){
 	       alert(resp);
 	       businessWjCancel()
 	     }else{
 	       alert(resp);
 	     }

	}});
}
// 重写数据
function businessWjCancel(){
 	 var input=document.getElementsByTagName("input");
	 for(var i=0;i<input.length;i++){
 	 if(input[i].name.substring(0,8)=="business"){
 	   	input[i].value="";
 	   }
 	 }
}
function onlyNum(data){
  this.value="";
  if ((event.keyCode>=49 &&  event.keyCode<=53) || (event.keyCode>=97 &&  event.keyCode<=101)){
   event.returnValue=true;
  }else{
    alert("只能输入1,2,3,4,5,单个数字");
    event.returnValue=false;
  }
}
// 导出问卷调查选择对话框
function businessListDialog(){
	dojo.xhrPost({url:"businessListDialog.action",load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
function businessTitleDialog(){
	dojo.xhrPost({url:"businessTitleDialog.action",load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
function businessWjDialog(){
	dojo.xhrPost({url:"businessWjDialog.action",load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}


// 删除评价信息
function delAppraise(){
  if (window.confirm("你确定要进行删除所选评价信息？")){
	  var tempIds="";
	  var ids = document.getElementsByTagName("input");
	   for (i = 1; i < ids.length; i++) {
	  	  if (ids[i].checked==true){
 	  	  	tempIds =tempIds+ ids[i].value +",";
 	  	  }
	}
	if (tempIds.indexOf(",")>0){
	  tempIds = tempIds.substring(0,tempIds.length-1);
	}
	    dojo.xhrPost({url:"appriesDelDealDel.action",content:{ids:tempIds},load:function (resp, ioArgs){
	    alert(resp);
	   window.location.reload();
	}});
	  }
}

function appriesDelDeal() {
	var employeeId = document.getElementById("id").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var deptIds = getAllDept();
	var keys = getAllKey();
	document.location.href = "appriesDelDeal.action?id=" + employeeId + "&startDate=" + startDate + "&endDate=" + endDate + "&deptIds=" + deptIds + "&keys=" + keys;
}
// 全部删除
function appraiseDelAll(data){
    if (window.confirm("你确定要进行删除所有评价信息？")){
	    dojo.xhrPost({url:"appriesDelDealDelAll.action?"+data ,load:function (resp, ioArgs){
	       alert(resp);
	 	   window.location.reload();
		}});
	}
}
// 备份数据
function baseBackUP(){
  if(confirm("是否备份数据")){
	  dojo.xhrPost({url:"baseBackUP.action",load:function (resp, ioArgs){
		       alert("备份成功");
	   }});
  }
}
// 还原数据对话框
function baseRestoreDialog(){
      dojo.xhrPost({url:"baseRestoreDialog.action",load:function (resp, ioArgs){
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	  }});
}


// 还原数据
function baseRestore(data){
  if(confirm("是否还原数据")){
	  dojo.xhrPost({url:"baseRestore.action",content:{fileName:data},load:function (resp, ioArgs){
		       alert("还原成功");
	   }});
  }
}
// 删除备份文件
function baseDelRestore(data){
  if(confirm("是否删除")){
	  dojo.xhrPost({url:"baseDelRestore.action",content:{fileName:data},load:function (resp, ioArgs){
		       document.getElementById("dialog").innerHTML = resp;
	  }});
  }
}
// js验证开始
function isNumber(oNum){
  var strP=/^\d+(\.\d+)?$/;
  if(!strP.test(oNum))alert("不是数字，请从新输入");
}
function keyIsNumber(data){
  var strP=/^\d+(\.\d+)?$/;
  if(!strP.test(data.value)){alert("不是数字，请从新输入");data.value="";}
}

 function   isIP(str){
  var   ip   =   /^([1-9]|[1-9]\d|1\d{2}|2[0-1]\d|22[0-3])(\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])){3}$/;
  if(!ip.test(str))alert("不是合法IP，请从新输入");
  }
function isUrl(str_url){
   var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
                + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        var re = new RegExp(strRegex);
        if(! re.test(str_url)){alert("不是合法升级地址，请从新输入")}
}


// js验证结束
function getMac(){
try{
 var locator = new ActiveXObject ("WbemScripting.SWbemLocator");
}catch(e){
alert("创建Active对象失败,请设置IE  ");
return false;
}


 var service = locator.ConnectServer("."); // 连接本机服务器
 var properties = service.ExecQuery("SELECT * FROM Win32_NetworkAdapterConfiguration");
// 查询使用SQL标准
 var e = new Enumerator (properties);
 var p = e.item ();
 var mac=p.MACAddress ;
 mac=mac.replace(new RegExp(":","gm"),"");
 document.getElementById("reMark").value=mac;
}
// 改变客户端类型，如果为1不显示产品类型，如果为2显示产品类型
function clientTypeChange(data){
  if(data == 1){
     document.getElementById("product_type").style.display="none";
     document.getElementById("tip").innerHTML="请手工输入设备号 "
  }else{
  	 document.getElementById("product_type").style.display="block";
  }
}
// 改变客户端类型，如果为1不显示产品类型，如果为2显示产品类型
function productTypeChange(data){
  if(data < 0){
     document.getElementById("tip").innerHTML="(<a href=\"#\" onclick=\"getMac()\" >自动获取mac地址</a>) "
  }else{
     document.getElementById("tip").innerHTML="(手工输入设备号) "
  }
}
// 向导功能
function baseGuide(data){
	  dojo.xhrPost({url:"baseGuide.action",content:{operate:data},load:function (resp, ioArgs){
	      if(resp == 'true'){
	        var url=window.location.href;
	       // alert(url);
	          if(url.indexOf("queryIndex.action")>0){
	             window.location.href="baseHelp.action?help=14"
	          }
	           if(url.indexOf("deptView.action")>0){
	               var deptType=document.getElementById("deptType").value;
	               if(deptType == 0){
	                 window.location.href="baseHelp.action?help=15"
	               }
	          }
	          if(url.indexOf("employeeView.action")>0){
	                 window.location.href="baseHelp.action?help=16"
	          }
	      }
	  }});
}
// 机构向导
function deptGuide(){
  var tip= document.getElementById("istip");
  if(tip.checked){
     dojo.xhrPost({url:"baseGuide.action",content:{operate:"false"},load:function (resp, ioArgs){
     }});
  }
  window.location.href="deptView.action"
}

function deptGuideSave(){
var tip= document.getElementById("istip");
  if(tip.checked){
     dojo.xhrPost({url:"baseGuide.action",content:{operate:"false"},load:function (resp, ioArgs){
     }});
  }else{
     dojo.xhrPost({url:"baseGuide.action",content:{operate:"true"},load:function (resp, ioArgs){
     }});
  }
}

function employeeGuide(){
  var tip= document.getElementById("istip");
  if(tip.checked){
     dojo.xhrPost({url:"baseGuide.action",content:{operate:"false"},load:function (resp, ioArgs){
     }});
  }
  window.location.href="employeeView.action"
}
function endGuide(){
     dojo.xhrPost({url:"baseGuide.action",content:{operate:"false"},load:function (resp, ioArgs){
     }});
   window.location.href="queryIndex.action"
}
function startGuide(){
     dojo.xhrPost({url:"baseGuide.action",content:{operate:"true"},load:function (resp, ioArgs){
     }});
     window.location.href="queryIndex.action"
}
function guideSimpleOne(){
     var multi="";
     var m=document.getElementById("multi");
     if(m.checked){multi="false"}
     var m2=document.getElementById("multi2");
     if(m2.checked){multi="true"}
     window.location.href="guideSimpleOne.action?multi="+multi;
}

function guideSimpleTwo(){//
     var id =document.getElementById("id").value;
     var name=document.getElementById("name").value;
    dojo.xhrPost({url:"guideSimpleOnePost.action",content:{id:id,name:name},load:function (resp, ioArgs){
            window.location.href="guideSimpleTwo.action?id="+id+"&name="+encodeURI(name);
        }});
     //window.location.href="guideSimpleTwo.action?id="+id+"&name="+encodeURI(name)
}
function guideSimpleThree(){
     var id =document.getElementById("id").value;
     var name=document.getElementById("name").value;
     var remark=document.getElementById("remark").value;
    dojo.xhrPost({url:"guideSimpleTwoPost.action",content:{id:id,name:name,remark:remark},load:function (resp, ioArgs){
            window.location.href="guideSimpleThree.action?id="+id+"&name="+encodeURI(name)+"&remark="+encodeURI(remark)
    }});

}
function guideEdit(data){
	dojo.xhrPost({url:"guideEdit.action",content:{id:data},load:function (resp, ioArgs){
	    var dept=resp.parseJSON();
	    document.getElementById("id").value=dept.id;
	    document.getElementById("name").value=dept.name;
	    document.getElementById("remark").value=dept.remark;
// document.getElementById("client_type").value=dept.client_type
	    document.getElementById("client_type").value=0;
    }});
}
function guideAjax(){
    var fatherId=document.getElementById("fatherId").value;
	dojo.xhrPost({url:"guideAjax.action",content:{fatherId:fatherId},load:function (resp, ioArgs){
	   document.getElementById("deptList").innerHTML=resp;
    }});
}
function quideSaveOrUpdate(){
  if(document.getElementById("name").value==""){
     alert("窗口名称不能为空");
     document.getElementById("name").focus;
  	 return  false;
  }
  if(document.getElementById("remark").value==""){
     alert("设备信息不能为空");
     document.getElementById("remark").focus;
  	 return  false;
  }
  if((document.getElementById("remark").value>=0 && document.getElementById("remark").value<=255) || document.getElementById("remark").value.length== 12   ){

  }else{
     alert("设备信息不合法，请输入从新输入");
     document.getElementById("remark").focus;
  	 return  false;
  }
  var id=		document.getElementById("id").value;
  var name=	    document.getElementById("name").value ;
  var remark=	    document.getElementById("remark").value ;
// var client_type= document.getElementById("client_type").value
  var client_type=	    0;
  var fatherId =	    document.getElementById("fatherId").value;
  alert("保存成功");
  dojo.xhrPost({url:"quideSaveOrUpdate.action",content:{id:id,name:name,remark:remark,client_type:client_type,fatherId:fatherId},load:function (resp, ioArgs){
	    document.getElementById("id").value="";
	    document.getElementById("name").value="";
	    document.getElementById("remark").value="";
// document.getElementById("client_type").value=2;
	    guideAjax();
  }});
}
function guideReset(){
	if(document.getElementById("name")!=null){
		document.getElementById("name").value="";
	}
	if(document.getElementById("remark")!=null){
		document.getElementById("remark").value="";
	}

}
function guideDel(data){
	dojo.xhrPost({url:"guideDel.action",content:{id:data},load:function (resp, ioArgs){
	   guideMultiAjax();
    }});
}
function guideSimpleFour(){
    var fatherId=document.getElementById("fatherId").value;
    window.location.href="guideSimpleFour.action?fatherId="+fatherId
}
function guideSimpleEmployeeEdit(data){
    dojo.xhrPost({url:"guideSimpleEmployeeEdit.action",content:{id:data},load:function (resp, ioArgs){
	    var employee=resp.parseJSON();
	    document.getElementById("id").value=employee.Id;
	    document.getElementById("name").value=employee.Name;
	    document.getElementById("ext2").value=employee.ext2;
	    document.getElementById("job_desc").value=employee.job_desc;
	    document.getElementById("cardnum").value=employee.CardNum;
	    document.getElementById("img123").src=employee.picture;
	    if(employee.Sex=='1') {
	      document.getElementById("sex1").checked=true;
	      document.getElementById("sex2").checked=false;
	    }else{
	      document.getElementById("sex1").checked=false;
	      document.getElementById("sex2").checked=true;
	    }
    }});
}
function guideSimpleEmployeeDel(data){
	dojo.xhrPost({url:"guideSimpleEmployeeDel.action",content:{id:data},load:function (resp, ioArgs){
	    guideSimpleEmployeeAjax();
    }});
}
function guideSimpleEmployeeSaveOrUpdate(){
	var id=document.getElementById("id").value;
	var deptId=document.getElementById("deptId").value;
	var name=document.getElementById("name").value;
	var cardNum=document.getElementById("cardnum").value;
	var ext2=document.getElementById("ext2").value;
	var job_desc=document.getElementById("job_desc").value;
	var imgName=document.getElementById("imgName").value;
	var sex="";
	if(document.getElementById("sex1").checked){
	   sex="1"
	}else{
	   sex="0"
	}
	if(document.getElementById("name").value== ""){
	  alert("请输入员工姓名");
	  document.getElementById("name").focus();
	  return false;
	}
	if(document.getElementById("cardnum").value== ""){
	  alert("请输入员工卡号");
	  document.getElementById("cardnum").focus();
	  return false;
	}
	dojo.xhrPost({url:"guideSimpleEmployeeSaveOrUpdate.action",content:{id:id,deptId:deptId,name:name,sex:sex,cardNum:cardNum,imgName:imgName,ext2:ext2,job_desc:job_desc},load:function (resp, ioArgs){
	   document.getElementById("id").value="";
	   document.getElementById("name").value="";
	   document.getElementById("ext2").value="";
	   document.getElementById("job_desc").value="";
	   document.getElementById("cardnum").value="";
	   document.getElementById("sex1").checked=true;
	   document.getElementById("imgName").value="images/employee_head_photo.gif";
       guideSimpleEmployeeAjax();
    }});
}

function guideSimpleEmployeeReset(){
	var id=document.getElementById("id").value;
	if (id == ""){
	   document.getElementById("name").value="";
	   document.getElementById("cardnum").value="";
	   document.getElementById("sex1").checked=true;
	   document.getElementById("imgName").value="";
	   document.getElementById("ext2").value="";
	   document.getElementById("job_desc").value="";
 	}else{
 		 dojo.xhrPost({url:"guideSimpleEmployeeEdit.action",content:{id:id},load:function (resp, ioArgs){
			     var employee=resp.parseJSON();
			     document.getElementById("id").value=employee.Id;
			     document.getElementById("name").value=employee.Name;
 			     document.getElementById("cardnum").value=employee.CardNum;
			     document.getElementById("img123").src=employee.picture;
			     document.getElementById("ext2").value=employee.ext2;
			     document.getElementById("job_desc").value=employee.job_desc;
			     if(employee.Sex=='1') {
			       document.getElementById("sex1").checked=true;
			       document.getElementById("sex2").checked=false;
			     }else{
			       document.getElementById("sex1").checked=false;
			       document.getElementById("sex2").checked=true;
			     }
		    }});
	}


}

function downvideo(videofile){
        dojo.xhrPost({url:"downloadVideo.action",content:{videofile:videofile},load:function (resp, ioArgs){
            console.log(resp);
            }});
}
function guideSimpleEmployeeAjax(){
     var deptId=document.getElementById("deptId").value;
	dojo.xhrPost({url:"guideSimpleEmployeeAjax.action",content:{deptId:deptId},load:function (resp, ioArgs){
 	   document.getElementById("employeeList").innerHTML=resp;
    }});
}
function guideBack(){
      window.history.back();
}
function guideComplete(){
	dojo.xhrPost({url:"guideComplete.action", load:function (resp, ioArgs){
        parent.closeTabMenu('配置向导')
    }});
}
function registerReg(){
	var serTxt=document.getElementById("serTxt").value;
	window.location.href="registerReg.action?serTxt="+serTxt;
}
function guideMultiDept(){
	var id=document.getElementById("id").value;
	var deptType=2;
	var deptTypes=document.getElementsByName("deptType");
	for(var i=0;i<deptTypes.length;i++){
		if(deptTypes[i].checked){deptType=deptTypes[i].value;}
	}
	if(deptType==-1){
		window.location.href="guideSimpleFour.action?fatherId="+id+"&deptType="+deptType;
	}else{
		window.location.href="guideMultiDept.action?id="+id+"&deptType="+deptType;
	}

}
function guideMultiDeptAdd(){
 if(document.getElementById("name").value==""){
     alert("窗口名称不能为空");
     document.getElementById("name").focus;
  	 return  false;
  }
  if(document.getElementById("remark")!=null){
	  if(document.getElementById("remark").value==""){
	     alert("设备信息不能为空");
	     document.getElementById("remark").focus;
	  	 return  false;
	  }
	  if((document.getElementById("remark").value>=0 && document.getElementById("remark").value<=255) || document.getElementById("remark").value.length== 12   ){

	  	}else{
	     alert("设备信息不合法，请输入从新输入");
	     document.getElementById("remark").focus;
	  	 return  false;
	  }
  }
	var id=document.getElementById("id").value;
	var fatherId=document.getElementById("fatherId").value;
	var deptType=document.getElementById("deptType").value;
	var name=document.getElementById("name").value;
	var remark="";
	var client_type="";

	if(document.getElementById("remark") != null){
		  remark=document.getElementById("remark").value;

	}

// if(document.getElementById("client_type") != null){
// client_type=document.getElementById("client_type").value;
// }
 	 alert("保存成功");
 	dojo.xhrPost({url:"guideMultiDeptSaveOrUpdate.action",content:{id:id,fatherId:fatherId,deptType:deptType,name:name,remark:remark,client_type:client_type}, load:function (resp, ioArgs){
		setTimeout("document.getElementById('name').focus();",2000);
		document.getElementById("id").value="";
	    document.getElementById("name").value="";
	    if(document.getElementById("remark") != null){
			 document.getElementById("remark").value="";
		}
// if(document.getElementById("client_type") != null){
// document.getElementById("client_type").value="2";
// }
	    guideMultiAjax();
    }});
}
function guideMultiAjax(){
	var fatherId= document.getElementById("fatherId").value;
	var deptType= document.getElementById("deptType").value;
	dojo.xhrPost({url:"guideMultiAjax.action",content:{fatherId:fatherId,deptType:deptType},load:function (resp, ioArgs){
	   document.getElementById("deptList").innerHTML=resp;
    }});
}

function guideMultiSelect(){
	var fatherId=document.getElementById("fatherId").value;
	var deptType=document.getElementById("deptType").value;
	var deptList=document.getElementById("deptList");
	var ul=deptList.getElementsByTagName("ul");
	var li=ul[0].getElementsByTagName("li");
	if(li.length==0){
	  if(deptType==0){
	    alert("请添加窗口");
	  }else if(deptType==1){
	    alert("请添加大厅");
	  }else if(deptType==2){
	    alert("请添加机构");
	  }
	 return 0;
	}
    window.location.href="guideMultiSelect1.action?fatherId="+fatherId+"&deptType="+deptType
}
function guideDeptTypeAjax(data){
	var deptType=-1;
	var deptTypes=document.getElementsByName("deptType");
	for(var i=0;i<deptTypes.length;i++){
		if(deptTypes[i].checked){deptType=deptTypes[i].value;}
	}
    if(deptType!=-1){
    	dojo.xhrPost({url:"guideDeptTypeAjax.action",content:{id:data},load:function (resp, ioArgs){
	   document.getElementById("type").innerHTML=resp;
    }});
    }

}
 function topImg(){
 	      var deptType=document.getElementById("deptType").value;
 	      var topimg=document.getElementById("topimg");
 	      if(deptType==2){
 	      	topimg.src="images/guide_top1.gif"
 	      }else if (deptType==1){
 	      	topimg.src="images/guide_top2.gif"
 	      }else if (deptType==0){
 	      	topimg.src="images/guide_top3.gif"
 	      }else{
 	      	topimg.src="images/guide_top4.gif"
 	      }
}
function threeSwap(data){
    var img=["","images/three_dh1.gif","images/three_dh2.gif","images/three_dh3.gif"];
    var img_=["","images/three_dh1_.gif","images/three_dh2_.gif","images/three_dh3_.gif"];
    for (var i=1;i<=3;i++){
      var img_temp=document.getElementById("img"+i);
      var three=document.getElementById("three"+i);
          if(i==data){
             img_temp.src=img[i];
             three.style.display="block";
          }else{
          	 img_temp.src=img_[i];
             three.style.display="none";
          }
    }
 }
function hidden(){
   var hidden=document.getElementById("three211");
   if(hidden.style.height=="50px")
       {hidden.style.height="25px";}
     else
       {hidden.style.height="50px";}
}
function threeLoad(){
   if(document.getElementById("img").value==""){
     alert("请选择上传文件");
     return 0;
   }
   document.getElementById("msg").innerHTML="<img src='images/jindutiao_comm.gif'  />";
   dojo.io.iframe.send({url:"threeLoad.action", method:"post", handleAs:"text", form:dojo.byId("pic"), handle:function (data, ioArgs) {
	document.getElementById("msg").innerHTML=data;
	}});
}
function threeSuit(){
  var big  =document.getElementById("big").value;
  var small=document.getElementById("small").value;
  var step =document.getElementById("step").value;


  document.getElementById("msg2").innerHTML="<img src='images/jindutiao_comm.gif'  />";
  dojo.xhrPost({url:"threeSuit.action",content:{big:big,small:small,step:step},load:function (resp, ioArgs){
 	  document.getElementById("msg2").innerHTML=resp;
  }});
}

function threeQueryDeal() {
	var employeeId = document.getElementById("id").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var deptIds = getAllDept();
	var keys = getAllKey();
	document.location.href = "threeQueryDeal.action?id=" + employeeId + "&startDate=" + startDate + "&endDate=" + endDate + "&deptIds=" + deptIds + "&keys=" + keys;
}

function threeExcel(){
  var deptIds=document.getElementById("deptIds").value;
  var startDate=document.getElementById("startDate").value;
  var endDate=document.getElementById("endDate").value;
  var keys=document.getElementById("keys").value;
  var id=document.getElementById("id").value;
  window.location.href="threeExcel.action?deptIds="+deptIds+"&startDate="+startDate+"&endDate="+endDate+"&keys="+keys+"&id="+id
}
function getRand(data){
	dojo.xhrPost({url:"baseSession.action",content:{sessionName:data},load:function (resp, ioArgs){
	 	document.getElementById("rand").value=resp;
	 	document.getElementById("name").value='admin';
	 	document.getElementById("password").value='a';
	 	submitlogin();
	}});
}
function autoDeal2() {
// 获取员工号
	var employeeId = document.getElementById("id").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var amUp = "";
	var pmUp = "";
	var pmDown="";
	var amDown ="";
	amUp=document.getElementById("amUp").value;
	amDown=document.getElementById("amDown").value;
	pmUp=document.getElementById("pmUp").value;
	pmDown= document.getElementById("pmDown").value;
	var key = document.getElementById("key").value;
	var num = document.getElementById("num").value;
	var deptId = document.getElementById("deptId").value;
	var weekend=document.getElementById("weekend").value;
// var deptIds=205;
	var deptIds = getAllDept();
// alert("deptIds="+deptIds);
// alert("employeeId="+employeeId+"num="+num+"startDate="+startDate+"amUp"+amUp+"key="+key);
	document.location.href = "autoDeal2?weekend="+weekend+"&employeeId=" + employeeId +"&deptId="+deptId+ "&startDate=" + startDate + "&endDate=" + endDate + "&deptIds=" + deptIds + "&key=" + key+ "&amUp=" + amUp+ "&amDown=" + amDown+ "&pmUp=" + pmUp+"&pmDown=" + pmDown+"&num="+num;
}
// ================================================================
// weburl 信息查询添加框
function weburToAdd(data, title) {
    new dialog().iframe("weburlToAdd.action", {title:title, full:true,w:'100%',h:'100%'});
}

// 添加信息查询
function weburlAdd(){
	var ue = UE.getEditor('weburl');
	var webname = document.getElementById("webname").value;
	var weburl = ue.getContent();//document.getElementById("weburl").value;
	dojo.xhrPost({url:"weburlAdd.action", content:{name:webname,url:weburl}, load:function (resp, ioArgs) {
            if (resp.trim() == "") {
                alert('添加成功');
                parent.closeDialog();
                parent.refreshTab();
            }
            else{
                alert(resp);
            }
	}});
}
// weburl 信息查询更新框
function weburlToUpate(data, title) {
    new dialog().iframe("weburlToUpdate.action?id=" + data, {title:title, full:true,w:'100%',h:'100%'});
}
// 更新信息查询
function weburlUpate(){
	var ue = UE.getEditor('weburl');
	var webname = document.getElementById("webname").value;
	var weburl = ue.getContent();
	var id = document.getElementById("uid").value;
	dojo.xhrPost({url:"weburlUpdate.action", content:{name:webname,url:weburl,id:id}, load:function (resp, ioArgs) {
	    if (resp.trim() == "") {
            alert('修改成功');
            parent.closeDialog();
            parent.refreshTab();
        }
        else{
            alert(resp);
        }
	}});
}

// 删除 信息查询
function weburlDelete(data){
    if (confirm("确认要删除吗?")){
        dojo.xhrPost({url:"weburlDelete.action", content:{id:data}, load:function (resp, ioArgs) {
                layer.msg('删除成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    refreshTab();
                });
        }});
    }
}
// weburl 信息查询 分页
function weburlPageAjax(data) {
	$('#pp').pagination('select');
}

// weburl 信息查询 分页
function weburlPage(data) {
	weburlPageAjax(data);
}


// ================================================================
// notice 通知公告添加框
function noticToAdd(title) {
    new dialog().iframe("noticeToAdd.action", {title: title,w:'100%',h:"100%",full:true});
}

// 添加 通知公告
function noticAdd(){
	var noticeTitle = document.getElementById("noticeTitle").value;
	var noticeContent = document.getElementById("noticeContent").value;
	if (noticeTitle.length==0){
	    new dialog().warn("标题不能为空!");
	    return;
    }
    if (noticeContent.length==0){
        new dialog().warn("内容不能为空!");
        return;
    }
	dojo.xhrPost({url:"noticeAdd.action", content:{title:noticeTitle,content:noticeContent}, load:function (resp, ioArgs) {
            if (resp.trim() == "") {
                layer.msg('添加成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    parent.closeDialog();
                    parent.refreshTab();
                });
            }
            else{
                layer.msg(resp, {
                    icon: 2,
                    time: 800
                }, function(){
                });
            }
	}});
}
// weburl 通知公告 更新框
function noticToUpate(data, title) {
    new dialog().iframe("noticeToUpdate.action?id="+data,{title:title,w:'100%',h:"100%",full:true});
}
// 更新通知公告
function noticUpate(){
	var noticeTitle = document.getElementById("noticeTitle").value;
	var noticeContent = document.getElementById("noticeContent").value;
	var id = document.getElementById("nid").value;
    if (!id){
        new dialog().warn("系统错误,请重试!");
        return;
    }
    if (noticeTitle.length==0){
        new dialog().warn("标题不能为空!");
        return;
    }
    if (noticeContent.length==0){
        new dialog().warn("内容不能为空!");
        return;
    }

	dojo.xhrPost({url:"noticeUpdate.action", content:{title:noticeTitle,content:noticeContent,id:id}, load:function (resp, ioArgs) {
            if (resp.trim() == "") {
                layer.msg('更新成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    parent.closeDialog();
                    parent.refreshTab();
                });
            }
            else{
                layer.msg(resp, {
                    icon: 2,
                    time: 800
                }, function(){
                });
            }
	}});
}

// 删除 通知公告
function noticDelete(data){
    if (confirm("确实要删除吗?")){
        dojo.xhrPost({url:"noticeDelete.action", content:{id:data}, load:function (resp, ioArgs) {
                layer.msg('删除成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    refreshTab();
                });
            }});
    }
}

// advice 通知公告 分页
function noticPageAjax(data) {
// var name = document.getElementById("keyword").value;
	dojo.xhrPost({url:"noticeListAjax.action", content:{currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
// notic 通知公告
function noticPage(data) {
// var name = document.getElementById("keyword").value;
// dojo.xhrPost({url:"noticeList.action", content:{currentPage:data},
// load:function (resp, ioArgs) {
// document.getElementById("man_zone").innerHTML = resp;
// }});
	noticPageAjax(data);
}

// ================================================================
// advice 意见调查 添加框
function adviceToAdd(title) {
    new dialog().iframe("adviceToAdd.action", {title: title});
}
// 添加 意见调查
function adviceAdd(){
// alert('error');
	var aq = document.getElementById("adviceQuestion").value;
	var as = document.getElementsByName("as");
	var ac ="";
	if(as != null){
		for(var i=0;i<as.length;i++){
			ac = ac +as[i].value+";";
		}
	}
	dojo.xhrPost({url:"adviceAdd.action", content:{aq:aq,answerString:ac}, load:function (resp, ioArgs) {
		parent.closeDialog();
		parent.refreshTab();
	}});
}
// 意见调查 更新框
function adviceToUpate(data, title) {
    new dialog().iframe("adviceToUpdate.action?id=" + data, {title:title});
}
// 更新意见调查
function adviceUpate(){
	var aq = document.getElementById("adviceQuestion").value;
	var id = document.getElementById("aid").value;
	var as = document.getElementsByName("as");
	var ac ="";
	if(as != null){
		for(var i=0;i<as.length;i++){
			ac = ac +as[i].value+";";
		}
	}
	dojo.xhrPost({url:"adviceUpdate.action", content:{aq:aq,answerString:ac,id:id}, load:function (resp, ioArgs) {
		parent.closeDialog();
		parent.refreshTab();
	}});
}
// 删除 意见调查
function adviceDelete(data){
	if(confirm("确定要删除吗?")){
        dojo.xhrPost({url:"adviceDelete.action", content:{id:data}, load:function (resp, ioArgs) {
                layer.msg('删除成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    refreshTab();
                });
            }});
    }
}
// 意见调查 分页
function advicePageAjax(data) {
// var name = document.getElementById("keyword").value;
	dojo.xhrPost({url:"adviceListAjax.action", content:{currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}

// 查看mac是否已经被占用
function macCheck(data){
// alert(data);
	dojo.xhrGet({url:"checkMacBinding.action", content:{mac:data}, load:function (resp, ioArgs) {
		if(resp.trim().length > 0){
// alert(resp);
	     	document.getElementById("tip").innerHTML=resp.trim();
	     	if(resp.trim()=="mac 已经被占用"){
	     		alert(resp.trim());
	     	}
	     }
	}});
}

function noData(id){
	alert('没有可以导出的数据。');
}
function bindWeburlDialog(title){
	var deptId = document.getElementById("deptId").value;
	//new dialog().iframe("toBindWeburl.action?deptId="+deptId, {title:title});
	//return;
	dojo.xhrPost({url:"toBindWeburl.action", content:{deptId:deptId}, load:function (resp, ioArgs) {
            layer.open({
                type: 1,
                title: title,
                shadeClose: true,
                shade: 0.8,
                //width:500,height:500,
                area:'800px',
                content: resp,
                success:function(ly,index){
                    //layer.iframeAuto(index);
                }
            });
	}});
}
function deptWeburlAdd(){
	var deptId = document.getElementById("deptId").value;
	var menus=document.getElementById("CmenuNames");
    var retVal=new Array();

    for(var i=0;i<menus.options.length;i++){
       retVal[i]=menus.options[i].value;
   	}
    var ids = retVal.toString();
	dojo.xhrPost({url:"deptWeburlAdd.action", content:{deptId:deptId,retVal:ids}, load:function (resp, ioArgs) {
            if (resp.trim() == "") {
                layer.msg('添加成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    closeDialog();
                    refreshTab();
                });
            }
            else{
                layer.msg(resp, {
                    icon: 2,
                    time: 800
                }, function(){
                });
            }
	}});
}
//图片或者视频预览
function toshow(videofile,path1,id){
	  var a = document.getElementById(id);
	  var file1 = videofile;
	  videofile = videofile.toLowerCase();
	  videofile = videofile.substring(videofile.lastIndexOf('.')+1);
	  if(videofile=="jpg"){
			a.href="showPic.jsp?pic="+file1;
	 		a.target="_bank";
 		}else{
 			a.href="query/videoPlay2.jsp?videofile="+file1;
 	 		a.target="_bank";
 	 	}
	  }
// ================================================================
// notice 通知公告添加框
function batchToAdd(id,title) {
    new dialog().iframe("batchToAdd.action?id="+id, {title: title, h:"120px"});
}

// 添加 通知公告
function batchAdd(){
    var id = document.getElementById("id").value;
    var batchid = document.getElementById("batchid").value;
    var batchdate = document.getElementById("batchdate").value;
    var batchname = document.getElementById("batchname").value;
    dojo.xhrPost({url:"batchAdd.action", content:{id:id, batchid:batchid,batchdate:batchdate,batchname:batchname}, load:function (resp, ioArgs) {
        var msg = JSON.parse(resp);
        console.log(msg);
        if(msg.succ){
            layer.msg(msg.errmsg, {
                icon: 1,
                time: 800
            }, function(){
                parent.closeDialog();
                parent.refreshTab();
            });
        }
        else{
            layer.msg(msg.errmsg, {
                icon: 2,
                time: 1500
            }, function(){
            });
        }
    }});
}

// 删除 通知公告
function batchDelete(data){
    if (confirm("确实要删除吗?")){
        dojo.xhrPost({url:"batchDelete.action", content:{id:data}, load:function (resp, ioArgs) {
                layer.msg(resp, {
                    icon: 1,
                    time: 800
                }, function(){
                    refreshTab();
                });
            }});
    }
}

// weburl 通知公告 更新框
function batchToUpate(data, title) {
    new dialog().iframe("batchToUpate.action?id="+data,{title:title});
}

function devicelist(){
    location.href="deviceList.action";
}

function deviceQuery(){
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;
    var batchno = document.getElementById("batchno").value;
    var mac = document.getElementById("mac").value;
    window.location.href = "deviceList.action?startDate=" + startDate + "&endDate=" + endDate + "&batchno=" + batchno + "&mac=" + mac;
}

function deviceToAdd(id,title) {
    new dialog().iframe("deviceToAdd.action?id="+id, {title: title, resize:false, h:"300px"});
}

function deviceDelete(data){
    if (confirm("确实要删除吗?")){
        dojo.xhrPost({url:"deviceDelete.action", content:{id:data}, load:function (resp, ioArgs) {
            console.log("批次删除"+resp);
                layer.msg('删除成功', {
                    icon: 1,
                    time: 800
                }, function(){
                    refreshTab();
                });
            }});
    }
}

function deviceAdd(){
    var id = document.getElementById("id").value;
    var mac = document.getElementById("mac").value;
    var batchid = document.getElementById("batchid").value;
    var ctime = document.getElementById("ctime").value;
    dojo.xhrPost({url:"deviceAdd.action", content:{id:id, mac:mac,batchid:batchid,ctime:ctime}, load:function (resp, ioArgs) {
            var msg = JSON.parse(resp);
            if(msg.succ){
                layer.msg(msg.errmsg, {
                    icon: 1,
                    time: 800
                }, function(){
                    parent.closeDialog();
                    parent.refreshTab();
                });
            }
            else{
                layer.msg(msg.errmsg, {
                    icon: 2,
                    time: 1500
                }, function(){
                });
            }
        }});
}
function showRemark(content){
	  layer.alert(content, {
	    skin: 'layui-layer-lan'
	    ,closeBtn: 0
	    ,shift: 4 //动画类型
	  });
}

function refreshTab(param){
    var myURL = location.href;//parseURL(location.href);
    if (param){
        myURL = parseURL(location.href);
        myURL = replaceUrlParams(myURL, param);
    }
    if(parent.qc && parent.qc.main.mainTabs){
        var currTab = parent.qc.main.mainTabs.tabs('getSelected');
        parent.qc.main.mainTabs.tabs('update', {
            tab : currTab,
            options : {
                content : '<iframe class="iframe-fluid" onload="CreatNodeAll()" src="'+myURL+'"></iframe>'
            }
        });
    }
    else{
        location.href = myURL;
    }
}

function initPager(_count, _curr, _limit){
    layui.use('laypage', function(){
        var laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'pp', //注意，这里的 test1 是 ID，不用加 # 号
            layout:['limit','prev', 'page', 'next','count'],
            count: _count, //数据总数，从服务端得到
            curr: _curr,
            limit: _limit,
            jump: function(obj, first){
                if(!first){
                    var myURL = parseURL(location.href);
                    var _newUrl = replaceUrlParams(myURL, { pageSize: obj.limit, currentPage:obj.curr });
                    if(parent.qc&&parent.qc.main.mainTabs){
                        var currTab = parent.qc.main.mainTabs.tabs('getSelected');
                        console.log("创建层1");
                        parent.qc.main.mainTabs.tabs('update', {
                            tab : currTab,
                            options : {
                                content : '<iframe class="iframe-fluid" onload="parent.parent.CreatNodeAll()" onreadystatechange="CancelAll();" src="'+_newUrl+'"></iframe>'
                            }
                        });
                    }
                    else{
                        location.href = _newUrl;
                    }
                }
                else{

                }
            }
        });
    });
}
