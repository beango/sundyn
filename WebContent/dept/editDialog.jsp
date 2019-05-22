<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

		<title><s:text name='sundyn.advice.question.list' /></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
        <script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/myAjax.js"></script>
	</head>
	<body>

    <div class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label" style="width:130px;">
                <c:if test="${dept.deptType==2}"><s:text name="sundyn.dept.deptName" /> </c:if>
                <c:if test="${dept.deptType==1}"><s:text name="sundyn.guide.datingName" /> </c:if>
                <c:if test="${dept.deptType==0}"><s:text name="sundyn.guide.windowName" /></c:if>
            </label>
            <div class="layui-input-inline">
                <input type="hidden" name="deptType" id="deptType" value="${deptType}" />
                <input type="text" name="deptName" id="deptName" class="layui-input" style="width: 200px;" value="${dept.name}" />
            </div>
            <div class="layui-form-mid layui-word-aux">
                <c:if test="${dept.deptType==2}"><s:text name="sundyn.dept.xxDept" /></c:if>
                <c:if test="${dept.deptType==1}"><s:text name="sundyn.dept.xxDating" /></c:if>
                <c:if test="${dept.deptType==0}"><s:text name="sundyn.dept.xxWindow" /></c:if>
            </div>
        </div>
        <c:if test="${dept.deptType==0 }">
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:130px;"><s:text name="sundyn.guide.deviceInfo" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="reMark" class="layui-input" id="reMark" value="${dept.remark}" onblur="macCheck(this.value)" style="width: 200px;"/>
                </div>
                <div class="layui-form-mid layui-word-aux">(<s:text name="sundyn.dept.demoOrMac" />，必须唯一不能重复)</div>
            </div>
        </c:if>
        <c:if test="${dept.deptType==0}">
            <c:if test="${bind == 'true'}">
                <div class="layui-form-item">
                    <label class="layui-form-label" style="width:130px;"><s:text name="sundyn.guide.employeeCardNum" /></label>
                    <div class="layui-input-inline">
                        <input type="text" name="dept_camera_url" id="dept_camera_url" value="${dept.dept_camera_url}"  onclick="this.select()" onkeyup="employeeFindByCardnumOrName()" class="input_comm" />
                        <div class="findreslut" id="findreslut" onkeydown="employeeresultdown()">
                        </div>
                    </div>
                </div>
            </c:if>
        </c:if>
        <c:if test="${dept.deptType==0  && '0' == '1' }">
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:130px;"><s:text name="sundyn.dept.businessType" /></label>
                <div class="layui-input-inline">
                    <select id="dept_businessId">
                        <c:forEach var="business" items="${list}">
                            <option value="${business.businessId}"  <c:if test="${ dept.dept_businessId == business.businessId }"> selected="selected"</c:if>  >${business.businessName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:130px;"><s:text name="sundyn.dept.pauseInfo" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="deptPause" id="deptPause" value="${dept.ext1}"  class="input_comm" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:130px;"><s:text name="sundyn.dept.pausePic" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="deptPausePic" id="deptPausePic" value="${dept.ext2}"  class="input_comm" />
                    <form id="pic" enctype="multipart/form-data" name="pic"	action="employeeAdd.action" method="post" style="margin: 0px;padding: 0px;background-color:#e9f5fd;">
                        <input type="hidden" name="imgName" id="imgName" />
                        <input type="file" name="img" id="img" onblur="getFileName()" style="width: 140px;" />
                        <img src="<s:text name='sundyn.pic.upload'/>" width="60" height="25" onclick="pauseUpload()" class="hand" />
                    </form>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:130px;"><s:text name="sundyn.dept.logo" /></label>
                <div class="layui-input-inline">
                    <input type="text" name="deptLogoPic" id="deptLogoPic" class="input_comm" value="${dept.ext3}"/>

                    <form id="pic2" enctype="multipart/form-data" name="pic2"	action="employeeAdd.action" method="post" style="margin: 0px;padding: 0px;background-color:#e9f5fd;">
                        <input type="hidden" name="imgName2" id="imgName2" />
                        <input type="file" name="img2" id="img2" onblur="getFileName2()" style="width: 140px;" />
                        <img src="<s:text name='sundyn.pic.upload'/>" width="60" height="25" onclick="DeptLogoUpload()" class="hand" />
                    </form>
                </div>
            </div>
        </c:if>
        <c:if test="${dept.deptType==0 && sysversion!='cgs' }">
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:130px;"><s:text name="sundyn.dept.playList" /></label>
                <div class="layui-input-inline">
                    <select id="playListId" style="width: 200px;">
                        <c:forEach var="playList" items="${playListList}">
                            <option value="${playList.playListId}"  <c:if test="${playList.playListId ==  dept.dept_playListId}"> selected="selected" </c:if>  >${playList.playListName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label" style="width:130px;"><s:text name="sundyn.system.unit.AudioAndVideo"></s:text></label>
                <div class="layui-input-inline">
                    <select name="useVideo" id="useVideo" >
                        <option value="0" <c:if test="${dept.useVideo=='0'}"> selected="selected" </c:if>><s:text name="sundyn.system.unit.bothNot"/></option>
                        <option value="1" <c:if test="${dept.useVideo=='1'}"> selected="selected" </c:if>><s:text name="sundyn.system.unit.useAudio"/></option>
                        <option value="2" <c:if test="${dept.useVideo=='2'}"> selected="selected" </c:if>><s:text name="sundyn.system.unit.useVideo"/></option>
                    </select>
                </div>
            </div>
        </c:if>
        <div class="layui-form-item" style="display:none;">
            <label class="layui-form-label" style="width:130px;">所属地区:</label>
            <div class="layui-input-inline" style="width:100px;">
                <select name="provinceid" value="${province.id}" id="provinces" lay-filter="provinces">
                    <s:iterator value="provinces">
                        <option value="${id}" <s:if test="province.id==id">selected</s:if>  >${name }</option>
                    </s:iterator>
                </select>
            </div>
            <div class="layui-input-inline" style="width:100px;">
                <select  value="${cityid}" name="cityid" id="citys" >
                    <s:iterator value="citys" >
                        <option value="${id}" <s:if test="cityid==id">selected</s:if> >${name }</option>
                    </s:iterator>
                </select>
            </div>
        </div>
        <c:if test="${dept.deptType!=2 }">
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:130px;">上级:</label>
                <div class="layui-input-inline">
                        ${parentobj.name}<input type="hidden" id="fatherid" name="fatherid" value="${parentobj.id}" />
                    <%--<ul id="treeDept" class="ztree"></ul>--%>
                </div>
            </div>
        </c:if>
        <c:if test="${dept.deptType==0 && sysversion != 'cgs'}">
            <div class="layui-form-item">
                <label class="layui-form-label" style="width:130px;"><s:text name="sundyn.system.screenInfo"/></label>
                <div class="layui-input-inline">
                    <textarea name="notice" id="notice" rows="8" style="width: 450px;" class="layui-textarea">${dept.notice}</textarea>
                </div>
            </div>
        </c:if>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width:130px;"></label>
            <div class="layui-input-inline">
                <button class="layui-btn" id="ok123" onclick="deptEditItem(<%=request.getParameter("deptId")%>)">保存</button>
                <button class="layui-btn layui-btn-primary" onclick="parent.closeDialog()">取消</button></div>
        </div>
    </div>

    <script type="text/javascript" src="lib/util/deptselutil.js"></script>
    <script>
        layui.use(['form', 'layedit', 'laydate'], function(){
            var form = layui.form
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;
            form.on('select(provinces)', function(data){
                showCitys(function(){form.render('select')});
            });
        });
        var depttype = '${dept.deptType}';
        var deptPara = 0;
        if(depttype == '1'){//大厅
            deptPara = 2;
        }
        if(depttype == '0'){//大厅
            deptPara = 1;
        }
        //initTree("?depttype="+deptPara+"&isOnlyLeaf=1", "<%=request.getParameter("fatherid")%>");
    </script>
    </body>
</html>