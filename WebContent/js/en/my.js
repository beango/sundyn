//导入包
dojo.require("dojo.parser");
dojo.require("dojo.io.script");
dojo.require("dojo.io.iframe");
var djConfig = {parseOnLoad:true};
//快速查询
//删除快速查询
String.prototype.trim= function(){  
    // 用正则表达式将前后空格  
    // 用空字符串替代。  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
}
function quicklyDel(data) {
	dojo.xhrGet({url:"quicklyDel.action", content:{id:data}, load:function (resp, ioArgs) {
		dojo.byId("man_zone").innerHTML = resp;
	}});
}
//调用查询
function quicklyQuery(data) {
	window.location.href =data;
}
//得到当前机构的下一个机构
function dept(data) {
	dojo.xhrGet({url:"queryDeptAjax.action", content:{id:data}, load:function (resp, ioArgs) {
		dojo.byId("dept").innerHTML = resp;
	}});
}
//机构查询机构展开
function deptopen(){
	var deptlist= document.getElementsByName("deptId");
	var deptId=0;
	for(var index=0; index<deptlist.length; index++) {
		if(deptId<deptlist[index].value ){
			deptId=deptlist[index].value ;
		}
	}
	dept(deptId);
}

//得到当前机构的下一个机构
function querydept() {
	var deptids = document.getElementsByName("deptId");
	var deptId = -1;
	for (i = 0; i < deptids.length; i++) {
		if (eval(deptId) < eval(deptids[i].value)) {
			deptId = deptids[i].value;
		}
	}
	var startDate = getStartDate();  
	var endDate =  getEndDate();
  	window.location.href = "queryDeptDeal.action?deptId=" + deptId + "&startDate=" + startDate + "&endDate=" + endDate;
}
//查看mac是否重复
function deptExistMac(data) {
	dojo.xhrGet({url:"deptExistMac.action", content:{mac:data}, load:function (resp, ioArgs) {
 	     if(resp.trim().length > 0){
	     	alert(resp);
	     }
		 
	}});
}

//查询备注信息
/**
 * queryDemo
 * @param {int} data 
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
function clearEmployee() {
	  document.getElementById("keyword").value='';
	  document.getElementById("id").value ='';
}

//关闭弹窗口
function closeDialog() {
	var dia = new dialog();
	dia.close("dialog");
}
//从查询结果里选择人
function selectPeople(id, name) {
	document.getElementById("id").value = id;
	document.getElementById("keyword").value = name;
	closeDialog();//关闭弹窗口
}
//显示按人员查询结果
function queryEmployeeDeal() {
	var id = document.getElementById("id").value;
	if(id==""){
		alert("请先查询,再选择人员");
		return false;;
	}
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "queryPeopleyDeal.action?id=" + id + "&startDate=" + startDate + "&endDate=" + endDate;
}

//显示按人员查询结果并进行删除
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
function queryResultDeal() {
	var result = document.getElementById("result").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "queryResultDeal.action?id=" + result + "&startDate=" + startDate + "&endDate=" + endDate;
}
//得到所有以勾选的机构，没有选择中为空字符串
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
//得到所有的key,没有选中为空字符串
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
//综合查询
function queryZhDeal() {
	var employeeId = document.getElementById("id").value;
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var deptIds = getAllDept();
	var keys = getAllKey();
	document.location.href = "queryZhDeal.action?id=" + employeeId + "&startDate=" + startDate + "&endDate=" + endDate + "&deptIds=" + deptIds + "&keys=" + keys;
}
//决策分析
//业务量
function analyseTotalAjaxDay(data) {
	
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	dojo.xhrPost({url:"analyseTotalAjaxDay.action", content:{num:data}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;  
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}

function analyseTotalAjax() {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var type = document.getElementById("type").value;
	dojo.xhrPost({url:"analyseTotalAjax.action", content:{startDate:startDate, endDate:endDate, type:type}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;  
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
//满意量
function analyseContentAjaxDay(data) {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	dojo.xhrPost({url:"analyseContentAjaxDay.action", content:{num:data}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;  
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
function analyseContentAjax() {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var type = document.getElementById("type").value;
	dojo.xhrPost({url:"analyseContentAjax.action", content:{startDate:startDate, endDate:endDate, type:type}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;  
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
//满意率
function analyseContentRateAjaxDay(data) {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	dojo.xhrPost({url:"analyseContentRateAjaxDay.action", content:{num:data}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;  
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
           
//决策分析，首页画图
 function analyseContentRateIndexAjaxDay(data) {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jindutiaoindex.gif\" />";
	dojo.xhrPost({url:"analyseContentRateAjaxDay.action", content:{num:data}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
		var chart =json.list;
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
function analyseContentRateAjax() {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var type = document.getElementById("type").value;
	dojo.xhrPost({url:"analyseContentRateAjax.action", content:{startDate:startDate, endDate:endDate, type:type}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;  
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
//机构分析
//业务量分析
function analyseDeptAjax() {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	var deptids = document.getElementsByName("deptId");
	var deptId = -1;
	for (i = 0; i < deptids.length; i++) {
		if (eval(deptId) < eval(deptids[i].value)) {
			deptId = deptids[i].value;
		}
	}
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	dojo.xhrPost({url:"analyseDeptAjax123.action", content:{startDate:startDate, endDate:endDate, type:type, deptId:deptId}, load:function (resp, ioArgs) {
	 	var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;  
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
//机构满意量
function analyseDeptContentAjax() {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	var deptids = document.getElementsByName("deptId");
	var deptId = -1;
	for (i = 0; i < deptids.length; i++) {
		if (eval(deptId) < eval(deptids[i].value)) {
			deptId = deptids[i].value;
		}
	}
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	dojo.xhrPost({url:"analyseDeptContentAjax.action", content:{startDate:startDate, endDate:endDate, type:type, deptId:deptId}, load:function (resp, ioArgs) {
	 	var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;  
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
//机构满意度
function analyseDeptContentRateAjax() {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	var deptids = document.getElementsByName("deptId");
	var deptId = -1;
	for (i = 0; i < deptids.length; i++) {
		if (eval(deptId) < eval(deptids[i].value)) {
			deptId = deptids[i].value;
		}
	}
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	dojo.xhrPost({url:"analyseDeptContentRateAjax.action", content:{startDate:startDate, endDate:endDate, type:type, deptId:deptId}, load:function (resp, ioArgs) {
	 	var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;  
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
//员工业务量
function analyseEmployeeAjax() {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
	 	if (types[i].checked) {
			type = types[i].value;
		}
	}
	var employeeId = document.getElementById("id").value;
	dojo.xhrPost({url:"analyseEmployeeAjax.action", content:{startDate:startDate, endDate:endDate, type:type, employeeId:employeeId}, load:function (resp, ioArgs) {
	 	var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg;  
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.drawXYLine(y, x);
	}});
}
//员工满意量
function analyseEmployeeContentAjax() {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	var employeeId = document.getElementById("id").value;
	dojo.xhrPost({url:"analyseEmployeeContentAjax.action", content:{startDate:startDate, endDate:endDate, type:type, employeeId:employeeId}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg; 
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
//员工满意度
function analyseEmployeeContentRateAjax() {
	document.getElementById("LineDiv").innerHTML = "<img src=\"images/jundutiao.gif\" />";
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var types = document.getElementsByName("type");
	var type = "day";
	for (i = 0; i < types.length; i++) {
		if (types[i].checked) {
			type = types[i].value;
		}
	}
	var employeeId = document.getElementById("id").value;
	dojo.xhrPost({url:"analyseEmployeeContentRateAjax.action", content:{startDate:startDate, endDate:endDate, type:type, employeeId:employeeId}, load:function (resp, ioArgs) {
		var json =  resp.parseJSON();
		var chart =json.list;
	 	document.getElementById("msg").innerHTML=json.msg; 
		var y = new Array();
		var x = new Array();
		for (i = 0; i < chart.length; i++) {
			y[i] = chart[i].num;
			x[i] = chart[i].serviceDate;
		}
		document.getElementById("LineDiv").innerHTML = "";
		var myline = new Line("LineDiv");
		myline.clearLine();
		myline.drawXYLine(y, x);
	}});
}
//记录节点的编号
function regId(data) {
	document.getElementById("deptId").value = data;
	dojo.xhrPost({url:"deptReg.action", content:{deptId:data}, load:function (resp, ioArgs) {
		document.getElementById("deptView").innerHTML = resp;
	}});
}
//添加机构节点
function addChildItem() {
	if (document.getElementById("deptId").value == "") {
		alert("\u8bf7\u9009\u62e9\u90e8\u95e8");
		return false;
	}
	if (document.getElementById("deptName").value == "") {
		alert("\u8bf7\u8f93\u5165\u673a\u6784\u540d\u79f0");
		return false;
	}
	var deptId = document.getElementById("deptId").value;
	var deptName = document.getElementById("deptName").value;
	var reMark = document.getElementById("reMark").value;
	var client_type = document.getElementById("client_type").value;
	var deptType = document.getElementById("deptType").value;
	//var deptPause =document.getElementById("deptPause").value;
	//var deptPic = document.getElementById("deptPausePic").value;
	var dept_camera_url="";
	var dept_businessId="";
	var dept_playListId="";
    var deptPause =  "";
	var deptPic =  "";
	var deptLogoPic =""
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
	dojo.xhrPost({url:"deptAddChildItem.action", content:{deptId:deptId, deptName:deptName, reMark:reMark, client_type:client_type, deptType:deptType,dept_camera_url:dept_camera_url,dept_businessId:dept_businessId,dept_playListId:dept_playListId,deptPause:deptPause,deptPic:deptPic,deptLogoPic:deptLogoPic}, load:function (resp, ioArgs) {
		var deptList = eval(resp);
		d = new dTree("d");
		for (i = 0; i < deptList.length; i++) {
			d.add(deptList[i].id, deptList[i].fatherId, deptList[i].name, "javascript:regId(" + deptList[i].id + ")");
		}
		document.getElementById("tree").innerHTML = d;
		closeDialog();
	}});
}
//删除机构
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
		//清除deptid
		document.getElementById("deptView").innerHTML = "<h3>\u90e8\u95e8\u7ba1\u7406</h3><div class='sundyn_row'>\u8bf7\u5148\u9009\u62e9\u5de6\u8fb9\u7684\u90e8\u95e8\uff0c\u518d\u8fdb\u884c\u76f8\u5e94\u7684\u64cd\u4f5c</div>";
	}});
}
//添加机构对话框
function deptAddDialog(data) {
	dojo.xhrPost({url:"deptAddDialog.action", content:{deptType:data}, load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//修改机构对话框
function deptEditDialog() {
	var deptId = document.getElementById("deptId").value;
	dojo.xhrPost({url:"deptEditDialog.action", content:{deptId:deptId}, load:function (resp, ioArgs) {
		dojo.byId("dialog").innerHTML = resp;
		
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//修改机构
function deptEditItem() {
	if (document.getElementById("deptId").value == "") {
		alert("\u8bf7\u9009\u62e9\u90e8\u95e8");
		return false;
	}
	var deptId = document.getElementById("deptId").value;
	var deptName = document.getElementById("deptName").value;
	var reMark = document.getElementById("reMark").value;
	var client_type = document.getElementById("client_type").value;
	
	var dept_camera_url="";
	var dept_businessId="";
	var dept_playListId="";
	var dept_Pause =  "";
	var deptPic =  "";
	var deptLogoPic =""
	
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
	dojo.xhrPost({url:"deptEditItem.action", content:{deptId:deptId, deptName:deptName, reMark:reMark, client_type:client_type,dept_camera_url:dept_camera_url,dept_businessId:dept_businessId,dept_playListId:dept_playListId,deptPause:dept_Pause,deptPic:deptPic,deptLogoPic:deptLogoPic}, load:function (resp, ioArgs) {
		var deptList = eval(resp);
		d = new dTree("d");
		for (i = 0; i < deptList.length; i++) {
			d.add(deptList[i].id, deptList[i].fatherId, deptList[i].name, "javascript:regId(" + deptList[i].id + ")");
		}
		document.getElementById("tree").innerHTML = d;
		closeDialog();
		var data = document.getElementById("deptId").value;
		regId(data);//显示修改后的信息
	}});
}
//员工管理
//根据机构查询出来员工
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
//添加员工对话框
function employeeAddDialog() {
	dojo.xhrPost({url:"employeeAddDialog.action", load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//员工是否存在
 function employeeExsits(){
	var ext2 = document.getElementById("ext2").value;
	dojo.xhrPost({url:"employeeExsits.action",content:{ext2:ext2},load:function (resp, ioArgs) {
 		document.getElementById("tip").innerHTML = trim(resp);
	}});
}
//添加员工
function employeeAdd() {
	if(document.getElementById("tip").innerHTML=='该用户已存在'){
		alert("该用户已存在");
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
	//var remark=document.getElementById("remark").value;
	var remark="";
	var showDeptName = document.getElementById("showDeptName").value;
	var showWindowName = document.getElementById("showWindow").value;
	var Sex = "";
	for (i = 0; i < sexs.length; i++) {
		if (sexs[i].checked) {
			Sex = sexs[i].value;
		}
	}
	if (Sex == "") {
		Sex = "\u7537";
	}
	dojo.xhrPost({url:"employeeAdd.action", content:{Name:Name, job_desc:job_desc, CardNum:CardNum, imgName:imgName, Sex:Sex, Phone:Phone, dept:dept,ext2:ext2,remark:remark,showDeptName:showDeptName,showWindowName:showWindowName}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		closeDialog();
		employeeManage(document.getElementById("deptId").value);
	}});
}
//把file名字放入input传过去
function getFileName() {
	var fileName = document.getElementById("img").value;
	if (fileName != "") {
		document.getElementById("imgName").value = fileName;
	}
}
//把file名字放入input传过去
function getFileName2() {
	var fileName = document.getElementById("img2").value;
	if (fileName != "") {
		document.getElementById("imgName2").value = fileName;
	}
}
//删除员工
function employeeDel(data) {
	dojo.xhrPost({url:"employeeDel.action", content:{employeeId:data}, load:function (resp, ioArgs) {
		employeeManage(document.getElementById("deptId").value);
	}});
}
//修改员工
function employeeEditDialog(data) {
	dojo.xhrPost({url:"employeeEditDialog.action", content:{employeeId:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//上传机构暂停图片
function pauseUpload() {
	dojo.io.iframe.send({url:"employeeUpload.action", method:"post", handleAs:"text", form:dojo.byId("pic"), handle:function (data, ioArgs) {
		if (data != "") {
		  // alert(data);
			document.getElementById("deptPausePic").value = "" + data;
		}
	}});
}

//上传窗口的logo图片
function DeptLogoUpload() {
	dojo.io.iframe.send({url:"employeeUpload.action?tt=c", method:"post", handleAs:"text", form:dojo.byId("pic2"), handle:function (data, ioArgs) {
		if (data != "") {
		  // alert(data);
			document.getElementById("deptLogoPic").value = "" + data;
		}
	}});
}


//上传图片
function employeeUpload() {
	dojo.io.iframe.send({url:"employeeUpload.action", method:"post", handleAs:"text", form:dojo.byId("pic"), handle:function (data, ioArgs) {
		if (data != "") {
			document.getElementById("imgName").value = "" + data;
			var img123 = document.getElementById("img123");
			img123.src = data;
		}
	}});
}
//修改员工
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
	//var remark=document.getElementById("remark").value;
	var remark="";
	var showDeptName = document.getElementById("showDeptName").value;
	var showWindowName = document.getElementById("showWindow").value;
	var Sex = "";
	for (i = 0; i < sexs.length; i++) {
		if (sexs[i].checked) {
			Sex = sexs[i].value;
		}
	}
	if (Sex == "") {
		Sex = "\u7537";
	}
	dojo.xhrPost({url:"employeeEdit.action", content:{employeeId:employeeId, Name:Name, job_desc:job_desc, CardNum:CardNum, imgName:imgName, Sex:Sex, Phone:Phone,ext2:ext2,remark:remark,showDeptName:showDeptName,showWindowName:showWindowName}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		closeDialog();
		employeeManage(document.getElementById("deptId").value);
	}});
}
function employeeOut(data) {
	dojo.xhrPost({url:"employeeOut.action", content:{employeeId:data}, load:function (resp, ioArgs) {
		employeeManage(document.getElementById("deptId").value);
	}});
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
//把一个人调入一个机构
function employeeIn(data) {
	var deptId = document.getElementById("deptId").value;
	if (deptId == "") {
		alert("\u8bf7\u5148\u9009\u62e9\u5de6\u8fb9\u7684\u90e8\u95e8\uff0c\u518d\u67e5\u770b\u6d41\u52a8\u4eba\u5458");
		return false;
	}
	dojo.xhrPost({url:"employeeIn.action", content:{deptId:deptId, employeeId:data}, load:function (resp, ioArgs) {
		alert("\u5df2\u7ecf\u8c03\u5165\u8be5\u90e8\u95e8\uff0c\u8bf7\u70b9\u51fb\u67e5\u770b");
	}});
}
//根据人员姓名，查询人员
function employeeQueryKeyword() {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"employeeQueryKeyword.action", content:{keyword:keyword}, load:function (resp, ioArgs) {
		document.getElementById("employeeView").innerHTML = resp;
	}});
}
function keywordpage(data) {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"employeeQueryKeyword.action", content:{keyword:keyword, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("employeeView").innerHTML = resp;
	}});
}
//密码初始化123456
function employeeReset(data) {
	dojo.xhrPost({url:"employeeReset.action", content:{employeeId:data}, load:function (resp, ioArgs) {
		alert("\u5bc6\u7801\u91cd\u7f6e\u6210\u529f");
	}});
}
//修改按键值对话框
function keyTypeQueryDialog(data) {
	dojo.xhrPost({url:"keyTypeQueryDialog.action", content:{id:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//修改按键
function keyTypeEdit(data) {
	var name = document.getElementById("name" + data).value;
	var isJoy = document.getElementById("isJoy" + data);
	var yes=document.getElementById("yes"+data);
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
	dojo.xhrPost({url:"keyTypeEdit.action", content:{id:data, name:name, isJoy:isJoy,yes:yes}, load:function (resp, ioArgs) {
		alert("\u4fee\u6539\u6210\u529f");
	}});
}
//添加用户对话框
function managerAddDialog() {
	dojo.xhrPost({url:"managerAddDialog.action", load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//验证用户不为空
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
	dojo.xhrPost({url:"managerExist.action",content:{name:name},load:function (resp, ioArgs) {
 		document.getElementById("tip").innerHTML = resp;
	}});
	
}
//添加用户
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
		closeDialog();
		managerQueryAjax();
	}});
}
//修改用户对话框
function managerEditDialog(data) {
	dojo.xhrPost({url:"managerEditDialog.action", content:{id:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//修改用户
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
		closeDialog();
		managerQueryAjax();
	}});
}
function managerQueryAjax() {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"managerQueryAjax.action", content:{name:keyword}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
function managerPage(data) {
	var name = document.getElementById("keyword").value;
	dojo.xhrPost({url:"managerQueryAjax.action", content:{name:name, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
function managerDel(data) {
	if(data==1){
		alert("不能删除此超级用户");
	}else{
	dojo.xhrPost({url:"managerDel.action", content:{id:data}, load:function (resp, ioArgs) {
		managerQueryAjax();
	}});
	}
}
//角色管理
function powerQueryAjax() {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"powerQueryAjax.action", content:{keyword:keyword}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
//分页
function powerPage(data) {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"powerQueryAjax.action", content:{keyword:keyword, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
//删除
function powerDel(data) {
	dojo.xhrPost({url:"powerDel.action", content:{id:data}, load:function (resp, ioArgs){
		alert(resp);
		powerQueryAjax();
	}});
}
//添加角色对话框
function powerAddDialog() {
	dojo.xhrPost({url:"powerAddDialog.action", load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//判断该角色名是否存在
function powerExist(){
	var name = document.getElementById("name").value;
	dojo.xhrPost({url:"powerExist.action",content:{name:name},load:function (resp, ioArgs) {
		document.getElementById("tip").innerHTML = resp;
	}});
}
//添加角色
function powerAdd() {
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
		baseSet = 0;
	} else {
		baseSet = 1;
	}
	var dataManage = document.getElementById("dataManage");
	if (dataManage.checked) {
		dataManage = 0;
	} else {
		dataManage = 1;
	}
	var deptId = document.getElementById("deptId").value;
	dojo.xhrPost({url:"powerAdd.action", content:{name:name, baseSet:baseSet, dataManage:dataManage, deptId:deptId}, load:function (resp, ioArgs) {
		closeDialog();
		powerQueryAjax();
	}});
}
//修改角色对话框
function powerEditDialog(data) {
	dojo.xhrPost({url:"powerEditDialog.action", content:{id:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
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
		closeDialog();
		powerQueryAjax();
	}});
}
//汇总
//机构汇总
function totalDeptDeal() {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "totalDeptDeal.action?startDate=" + startDate + "&endDate=" + endDate;
}
//大厅汇总
function totalDatingDeal() {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	window.location.href = "totalDatingDeal.action?startDate=" + startDate + "&endDate=" + endDate;
}
//窗口汇总
function totalWindowDeal() {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var deptId = document.getElementById("deptId").value;
	window.location.href = "totalWindowDeal.action?startDate=" + startDate + "&endDate=" + endDate + "&deptId=" + deptId;
}
//个人汇总
function totalPersonAjax(data) {
	dojo.xhrPost({url:"totalPersonAjax.action", content:{id:data}, load:function (resp, ioArgs) {
		document.getElementById("window").innerHTML = resp;
	}});
}
function totalPersonDeal() {
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var deptids = document.getElementsByName("deptId");
	var deptId = -1;
	for (i = 0; i < deptids.length; i++) {
		if (eval(deptId) < eval(deptids[i].value)) {
			deptId = deptids[i].value;
		}
	}
	window.location.href = "totalPersonDeal.action?startDate=" + startDate + "&endDate=" + endDate + "&deptId=" + deptId;
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


//打印
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
//判断浏览器是否是ie
function isIe() {
	if (window.ActiveXObject) {
		return true;
	} else {
		return false;
	}
}
//上传图片对话框
function basePicAjax(data) {
	document.getElementById("img1").src = "upload/2.jpg";
	dojo.xhrPost({url:"basePicAjax.action", content:{fileName:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//基本设置上传图片
function basePicUpload() {
	dojo.io.iframe.send({url:"basePicUpload.action", method:"post", handleAs:"text", form:dojo.byId("pic123"), handle:function (data, ioArgs) {
		closeDialog();
	}});
	window.location.reload();
}
//管理员提示类型
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
//播放查詢
function playQueryAjax() {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"playQueryAjax.action", content:{keyword:keyword}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
//播放翻页面
function playPage(data) {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"playQueryAjax.action", content:{keyword:keyword, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
//播放 添加对话框
function playAddDialog() {
	dojo.xhrPost({url:"playAddDialog.action", load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//上传
function playupload() {
	dojo.io.iframe.send({url:"playUpload.action", method:"post", handleAs:"text", form:dojo.byId("pic"), handle:function (data, ioArgs) {
		if (data != "") {
			document.getElementById("playSource").value = "" + data;
		}
	}});
}
//添加播放
function playAdd() {
	var playName = document.getElementById("playName").value;
	var playType = document.getElementById("playType").value;
	var playSource = document.getElementById("playSource").value;
	var playTimes=document.getElementById("playTimes").value;
	var playIndex=document.getElementById("playIndex").value;
	var patrn=/^[0-9]{1,20}$/;
	if (!patrn.exec(playIndex)){alert("序列只能为数字");document.getElementById("playIndex").focus();return false;}
 	dojo.xhrPost({url:"playAdd.action", content:{playName:playName, playType:playType, playSource:playSource,playTimes:playTimes,playIndex:playIndex}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		closeDialog();
		playQueryAjax();
	}});
}
//播放修改对话框
function playEditDialog(data) {
	dojo.xhrPost({url:"playEditDialog.action", content:{playId:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//播放修改
function playEdit(data) {
	var playName = document.getElementById("playName").value;
	var playType = document.getElementById("playType").value;
	var playSource = document.getElementById("playSource").value;
	var playId = document.getElementById("playId").value;
	var playTimes=document.getElementById("playTimes").value;
	var playIndex=document.getElementById("playIndex").value;
	var patrn=/^[0-9]{1,20}$/;
	if (!patrn.exec(playIndex)){alert("序列只能为数字");document.getElementById("playIndex").focus();return false;}
	dojo.xhrPost({url:"playEdit.action", content:{playName:playName, playType:playType, playSource:playSource, playId:playId,playTimes:playTimes,playIndex:playIndex}, load:function (resp, ioArgs) {
		closeDialog();
		playQueryAjax();
	}});
}
//播放删除
function playDel(data) {
	dojo.xhrPost({url:"playDel.action", content:{playId:data}, load:function (resp, ioArgs) {
		playQueryAjax();
	}});
}
//播放列表查询
function playListQueryAjax() {
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"playListQueryAjax.action", content:{keyword:keyword}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
function playListPage(data){
	var keyword = document.getElementById("keyword").value;
	dojo.xhrPost({url:"playListQueryAjax.action", content:{keyword:keyword, currentPage:data}, load:function (resp, ioArgs) {
		document.getElementById("man_zone").innerHTML = resp;
	}});
}
//播放列表 添加对话框
function playListAddDialog() {
	dojo.xhrPost({url:"playListAddDialog.action", load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//播放列表添加
function playListAdd() {
	var playListName = document.getElementById("playListName").value;
	var playListDescription = document.getElementById("playListDescription").value;
	var playIds = getAllKey();
	dojo.xhrPost({url:"playListAdd.action", content:{playListName:playListName, playListDescription:playListDescription, playIds:playIds}, load:function (resp, ioArgs) {
		playListQueryAjax();
		closeDialog();
	}});
}
//播放列表修改
function playListEditDialog(data) {
	dojo.xhrPost({url:"playListEditDialog.action", content:{playListId:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
	}});
}
//生成在线升级包
function playListCreateUpdateFile(data){
	document.getElementById("pbar").src="images/update_processbar.gif";
	dojo.xhrPost({url:"playListCreateUpdateFile.action", content:{playListId:data}, load:function (resp, ioArgs) {
		 document.getElementById("pbar").src="images/update_processend.gif";
		 alert("生成成功");
	}});
}


//给文本框值
function setvalue() {
	document.getElementById("playIds").value = getAllKey();
}
//播放列表修改
function playListEdit() {
	var playListName = document.getElementById("playListName").value;
	var playListDescription = document.getElementById("playListDescription").value;
	var playIds = document.getElementById("playIds").value;
	var playListId = document.getElementById("playListId").value;
	dojo.xhrPost({url:"playListEdit.action", content:{playListId:playListId, playListName:playListName, playListDescription:playListDescription, playIds:playIds}, load:function (resp, ioArgs) {
		var flag=confirm("是否生生成升级包");
		if (flag){
			playListCreateUpdateFile(playListId);
		} 
		closeDialog();
		playListQueryAjax();
	}});
}
//播放列表删除
function playListDel(data) {
	dojo.xhrPost({url:"playListDel.action", content:{playListId:data}, load:function (resp, ioArgs) {
	    alert(resp);
		playListQueryAjax();
	}});
}
//更新M7程序
function playListUpdateDialog(data) {
	dojo.xhrPost({url:"playListUpdateDialog.action", content:{playListId:data}, load:function (resp, ioArgs) {
		document.getElementById("dialog").innerHTML = resp;
		var dia = new dialog();
		dia.show("dialog");
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
 
 
//得到月初时间
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
		startDate.value = "".concat(year).concat("-").concat(mm).concat("-").concat("01").concat(" ").concat("00:00:00");
	}
	var endDate = document.getElementById("endDate");
	if (endDate != null) {
		endDate.value = "".concat(year).concat("-").concat(mm).concat("-").concat(dd).concat(" ").concat("23:59:59");;
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
  var f=document.getElementById("man_nav_5");
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
function htmltoexcel(tableid) {//整个表格拷贝到EXCEL中 
     var curTbl = document.getElementById(tableid);
     var d=document.createElement("DIV");
     d=document.getElementById(tableid);
      
      try {
        var oXL = new ActiveXObject("Excel.Application"); 	
      } catch (e) {
      	alert("导出失败,请确认你安装了excel，并设置ie安全级别为底");
      	return false;
      }
      //创建AX对象excel 
     var oWB = oXL.Workbooks.Add(); 
     //获取workbook对象 
         var oSheet = oWB.ActiveSheet; 
     //激活当前sheet 
     var sel = document.body.createTextRange(); 
     sel.moveToElementText(d); 
     //把表格中的内容移到TextRange中 
     sel.select(); 
     //全选TextRange中内容 
     sel.execCommand("Copy"); 
     //复制TextRange中内容  
     oSheet.Paste(); 
     //粘贴到活动的EXCEL中       
     oXL.Visible = true; 
     //设置excel可见属性 
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
//首页调用js
// 评价信息详细
function indexDetail(deptIds,keys){
	 var now=new Date();
	 y=now.getFullYear();
	 m=now.getMonth()+1;
	 d=now.getDate();
	 m=m<10?"0"+m:m;
	 d=d<10?"0"+d:d;
	 //startDate=y+"-"+m+"-01 00:00:00"
	 //endDate=y+"-"+m+"-"+d+" "+"12:59:59"
	 startDate="";
	 endDate="";
     document.location.href="queryZhDeal.action?id=0&startDate="+startDate+"&endDate="+endDate+"&deptIds="+deptIds+"&keys="+keys+"&currentPage=1";
}
//首页饼图详细

function indexCake(deptIds){
	 var now=new Date();
	 y=now.getFullYear();
	 m=now.getMonth()+1;
	 d=now.getDate();
	 m=m<10?"0"+m:m;
	 d=d<10?"0"+d:d;
	 //startDate=y+"-"+m+"-01 00:00:00"
	 //endDate=y+"-"+m+"-"+d+" "+"12:59:59"
	 startDate="";
	 endDate="";
     document.location.href="queryZhDeal.action?id=0&startDate="+startDate+"&endDate="+endDate+"&deptIds="+deptIds+"&keys=";
}
//鼠标经过时选择当前
function selectCurrent(data){
	var d=document.getElementById("findreslut");
	var child=d.getElementsByTagName("div") 
	for(var index=0; index<child.length; index++){
		child[index].className="";
	}
	data.className="hover" ;
}
var flagemployeefind=0; //全局变量，判断是否是从查询结果中返回，如果是为1 ，不是为0
//选择员工卡号
function setcardnum(data){
	var d=document.getElementById("findreslut");
	d.innerHTML="";
	d.style.display="none";
	document.getElementById("dept_camera_url").value=data;
	document.getElementById("dept_camera_url").focus();
 	flagemployeefind=1;
}
//查询员工信息
function employeeFindByCardnumOrName(){
	if(flagemployeefind == 0){
		if(event.keyCode!=40 && event.keyCode!=38){
			var keyword=document.getElementById("dept_camera_url").value;
			dojo.xhrPost({url:"employeeFindByCardnumOrName.action",content:{keyword:keyword},load:function (resp, ioArgs){
				document.getElementById("findreslut").innerHTML=resp;
				document.getElementById("findreslut").style.display='block';
			}});
		}
		//按向下方向键
		if (event.keyCode==40){
	 		var d=document.getElementById("findreslut");
	 		d.focus();
	 		var child=d.getElementsByTagName("div") 
	 		child[0].className="hover" ;
	 	}
	 	//按向下方向键
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
 	    //得到当前选中的项 i
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
  			//清除选中
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
  			//清除选中
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
 //留言板
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
 //切换 ，选中当前
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
//上传图片
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
		}
		closeDialog();
	}});
}


//上传m7模板
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
//删除模板
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
//预浏模板
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
//预览模板k3,k4,k5,k6
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
//删除星级
function starDel(data){
	 var stars=document.getElementById("stars");
	 if(stars.childNodes.length>2){
		  stars.removeChild(data.parentNode);
		 }else{
		  alert("最少要两个");
	 }
}
//增加星级
function starAdd(){
	 var stars=document.getElementById("stars");
	 if(stars.childNodes.length>=10){
		   alert("最多10个");
		   return 0;
	 }
 	 var star=document.createElement("li");
	 star.innerHTML ="<b>满意率:</b><input type='text'   class='input_comm' name='prate' /><b>以上</b><input type='text'    class='input_comm' name='pgrade'/><b>分</b><input type='text'   class='input_comm' name='pstar' /><b>星</b>  <img src='images/tp_add.gif'  onclick='starAdd()'/> <img src='images/tp_del.gif' onclick='starDel(this)' />"
 	 stars.appendChild(star);
}
//保存设置
function sundynSetSave(){
    var camera="" //是否使用监控
	var k7=""//是否使用未评价按键
	var star=""//是否使用自定义星级
	var bind=""//是否绑定用户名
	var title="";//存放单位名称
	var logo="";//存放logo
	var buttom="";//用于存m7底部显示文件
	var requestAddress = "";//用于存放不满意评价信息的访问地址
	var sam="";//上午上班时间
	var eam="";//上午下班时间
	var spm="";//下午上班时间
	var epm="";//下午下班时间
	var prates="";//存放满意率
	var pgrades="";//存放10分等级
	var pstars="";//存放星级
	//得到系统设置
	if(document.getElementById("camera").checked){
	  camera="true";
	}else{
	  camera="false";
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
	//得到标题，logo
	title=document.getElementById("title").value;
	logo=document.getElementById("logo").value;
	buttom = document.getElementById("buttom").value;
	requestAddress = document.getElementById("requestAddress").value;
	
	//得到上下班时间
	sam=document.getElementById("sam").value;
	eam=document.getElementById("eam").value;
	spm=document.getElementById("spm").value;
	epm=document.getElementById("epm").value;
	//得到星级
	var stars=document.getElementById("stars");
	var prate="101";
	var pgrade="11";
	var pstar="**********";
	for(var i=0;i<stars.childNodes.length;i++){
	   //alert(stars.childNodes[i].childNodes[5].value);
	    if( eval(stars.childNodes[i].childNodes[1].value)<=eval(prate)){
	     	prate=stars.childNodes[i].childNodes[1].value;
	     	prates=prates+prate+",";
	    }else{
	        document.getElementById("content1").style.display="none";
	        document.getElementById("content2").style.display="none";
	        document.getElementById("content3").style.display="none";
	        document.getElementById("content4").style.display="none";
	        document.getElementById("content5").style.display="block";
	        stars.childNodes[i].childNodes[1].focus();
	        alert("下面的数据，只能小于等于上面的数据");
	        return 0;
 	    }
	    if( eval(stars.childNodes[i].childNodes[3].value)<=eval(pgrade)){
	     	pgrade= stars.childNodes[i].childNodes[3].value;
	     	pgrades=pgrades+pgrade+",";
	    }else{
	        document.getElementById("content1").style.display="none";
	        document.getElementById("content2").style.display="none";
	        document.getElementById("content3").style.display="none";
	        document.getElementById("content4").style.display="none";
	        document.getElementById("content5").style.display="block";
	        stars.childNodes[i].childNodes[3].focus();
	        alert("下面的数据，只能小于等于上面的数据");
	        return 0;
 	    }
	    //if( stars.childNodes[i].childNodes[5].value<=pstar){
	     	pstar=stars.childNodes[i].childNodes[5].value;
	     	pstars=pstars+pstar+",";
	   // }else{
	      //  document.getElementById("content1").style.display="none";
	       // document.getElementById("content2").style.display="none";
	        //document.getElementById("content3").style.display="none";
	       // document.getElementById("content4").style.display="none";
	        //
	       // document.getElementById("content5").style.display="block";
	       // stars.childNodes[i].childNodes[5].focus();
	        //alert("下面的数据，只能小于等于上面的数据");
	      //  return 0;
 	   // }
	}
  	 dojo.xhrPost({url:"baseSetSave.action",content:{camera:camera,k7:k7,star:star,bind:bind,title:title,logo:logo,sam:sam,eam:eam,spm:spm,epm:epm,prates:prates,pgrades:pgrades,pstars:pstars,buttom:buttom,requestAddress:requestAddress},load:function (resp, ioArgs){
			  alert(resp);
	 }});
	 
}
//查看投票
function voteResult(data){
  dojo.xhrPost({url:"voteDialog.action",content:{voteId:data},load:function (resp, ioArgs){
	     document.getElementById("dialog").innerHTML = resp;
		 var dia = new dialog();
		 dia.show("dialog");
 }});
}
//使用当前投票选项
function voteUse(data){
 dojo.xhrPost({url:"voteUse.action",content:{voteId:data},load:function (resp, ioArgs){
	document.getElementById("vote").innerHTML=resp;
 }});
}
//删除投票
function voteDel(data){
  dojo.xhrPost({url:"voteDel.action",content:{voteId:data},load:function (resp, ioArgs){
	document.getElementById("vote").innerHTML=resp;
 }});
}
//添加投票
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
  //清理数据
  document.getElementById("voteTitle").value="";
  for(var i=0;i<voteSelect.childNodes.length;i++){
     voteSelect.childNodes[i].childNodes[1].value="";
  }
  document.getElementById("voteNum").checked =false;
}
//投票取消
function voteCancel(){
  ans=window.confirm('你确认取消所有投票信息？');
 //alert (ans);                      
 if (ans==true)
  {
  document.getElementById("voteTitle").value="";
  var voteSelect=document.getElementById("voteSelect");
  for(var i=0;i<voteSelect.childNodes.length;i++){
     voteSelect.childNodes[i].childNodes[1].value="";
  }
  document.getElementById("voteNum").checked =false;
  //撤消所有的投票功能
   dojo.xhrPost({url:"voteCancle.action",content:{},load:function (resp, ioArgs){
	document.getElementById("vote").innerHTML=resp;
  }});

   }
}

//删除投票选项
function voteSelectDel(data){
	 var voteSelect=document.getElementById("voteSelect");
	 if(voteSelect.childNodes.length>2){
		  voteSelect.removeChild(data.parentNode);
		 }else{
		  alert("最少要两个");
	 }
}
//增加投票选项
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
//保存业务类型
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
//删除评价信息		
function delAppraise(){
  if (window.confirm("你确定要进行删除所选评价信息？")){
	  var tempIds="";
	  var ids = document.getElementsByTagName("input");
	   for (i = 1; i < ids.length; i++) {
	  	  if (ids[i].checked==true){
 	  	  	tempIds =tempIds+(ids[i].value).substring(0,ids[i].value.length-2)+",";
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
//全部删除
function appraiseDelAll(data){
    if (window.confirm("你确定要进行删除所有评价信息？")){
	    dojo.xhrPost({url:"appriesDelDealDelAll.action?"+data ,load:function (resp, ioArgs){
	       alert(resp);
	 	   window.location.reload();
		}});
	}
}

