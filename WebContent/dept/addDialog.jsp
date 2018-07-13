<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
<script type="text/javascript" src="lib/layui/layui.js"></script>
<script type="text/javascript" src="js/myAjax.js"></script>
<script type="text/javascript" src="js/dojo.js"></script>
<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>

<div class="dialog">
    <div class="content">
        <div class="layui-form">
            <table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
                <tr>
                    <td width="21%" align="right" style="border-color:#FFFFFF;">
                        <c:if test="${deptType==2}"><s:text name="sundyn.column.deptName" /><s:text name="sundyn.colon" /> </c:if>
                        <c:if test="${deptType==1}"><s:text name="sundyn.column.datingName" /><s:text name="sundyn.colon" /> </c:if>
                        <c:if test="${deptType==0}"><s:text name="sundyn.column.windowName" /><s:text name="sundyn.colon" /> </c:if>
                    </td>
                    <td align="left" style="border-color:#FFFFFF;">
                        <div class="layui-input-inline">
                        <input type="hidden" name="deptType" id="deptType" value="${deptType}" />
                        <input type="text" name="deptName" id="deptName" class="layui-input" />
                        </div>
                        <c:if test="${deptType==2}"><s:text name="sundyn.dept.xxDept" /></c:if>
                        <c:if test="${deptType==1}"><s:text name="sundyn.dept.xxDating" /></c:if>
                        <c:if test="${deptType==0}"><s:text name="sundyn.dept.xxWindow" /></c:if>
                    </td>
                </tr>
                <%--					  <tr <c:if test="${deptType==2 || deptType==1 }"> style="display: none;"</c:if> >--%>
                <%--					    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.clientConnectType" /></td>--%>
                <%--					    <td align="left" style="border-color:#FFFFFF;"> --%>
                <%--					       <select name="client_type" id="client_type"  onchange="clientTypeChange(this.value)">--%>
                <%--								<option value="1">--%>
                <%--									<s:text name="sundyn.guide.comServer" />--%>
                <%--								</option>--%>
                <%--								<option selected="selected" value="2">--%>
                <%--									<s:text name="sundyn.guide.clientProgram" />--%>
                <%--								</option>--%>
                <%--							</select>--%>
                <%--							<select name="product_type" id="product_type" onchange="productTypeChange(this.value)">--%>
                <%--								<option value="-1"   >--%>
                <%--									U<s:text name="sundyn.dept.type" />--%>
                <%--								</option>--%>
                <%--								<option value="-2"  >--%>
                <%--									D<s:text name="sundyn.dept.type" />--%>
                <%--								</option>--%>
                <%--								<option value="-3" >--%>
                <%--									T01U<s:text name="sundyn.dept.type" />--%>
                <%--								</option>--%>
                <%--								<option value="-4"  >--%>
                <%--									M7<s:text name="sundyn.dept.type" />--%>
                <%--								</option>--%>
                <%--								<option value="1"  >--%>
                <%--									S<s:text name="sundyn.dept.type" />--%>
                <%--								</option>--%>
                <%--								<option value="2" >--%>
                <%--									W<s:text name="sundyn.dept.type" />--%>
                <%--								</option>--%>
                <%--								<option value="3"  >--%>
                <%--									T01<s:text name="sundyn.dept.type" />--%>
                <%--								</option>--%>
                <%-- 							</select>--%>
                <%--					    </td>--%>
                <%--					  </tr>--%>
                <tr <c:if test="${deptType==2 || deptType==1 }"> style="display: none;" </c:if>>
                    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.guide.deviceInfo" /></td>
                    <td align="left" style="border-color:#FFFFFF;">
                        <div class="layui-input-inline">
                        <input type="text" name="reMark" id="reMark" value="${remark}" class="layui-input" onblur="macCheck(this.value)" />
                        </div>
                        <span style="font-size: 9px; color: red;" id="tip">(<s:text name="sundyn.dept.demoOrMac" />)</span>
                    </td>
                </tr>
                <c:if test="${deptType==0}">
                    <c:if test="${bind == 'true'}">
                        <tr>
                            <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.guide.employeeCardNum" /></td>
                            <td align="left" style="border-color:#FFFFFF;">
                                <div class="layui-input-inline">
                                <input type="text" name="dept_camera_url" id="dept_camera_url"    onclick="this.select()" onkeyup="employeeFindByCardnumOrName()" class="input_comm" />
                                </div>
                                    <div class="findreslut" id="findreslut" onkeydown="employeeresultdown()">
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </c:if>
                <c:if test="${deptType==0 && '0'=='1' }">
                    <tr>
                        <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.businessType" /></td>
                        <td align="left" style="border-color:#FFFFFF;">
                            <select id="dept_businessId">
                                <c:forEach var="business" items="${list}">
                                    <option value="${business.businessId}">${business.businessName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${deptType==0}">
                    <tr>
                        <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.playList" /></td>
                        <td align="left" style="border-color:#FFFFFF;">
                            <div class="layui-input-inline">
                            <select id="playListId">
                                <c:forEach var="playList" items="${playListList}">
                                    <option value="${playList.playListId}">${playList.playListName}</option>
                                </c:forEach>
                            </select>
                            </div>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${deptType==0  && '0' == '1'}">
                    <tr>
                        <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.pauseInfo" /></td>
                        <td align="left" style="border-color:#FFFFFF;">
                            <input type="text" name="deptPause" id="deptPause" class="input_comm" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.pausePic" /></td>
                        <td align="left" style="border-color:#FFFFFF;">
                            <input type="text" name="deptPausePic" id="deptPausePic" class="input_comm" />

                            <form id="pic" enctype="multipart/form-data" name="pic"	action="employeeAdd.action" method="post" style="margin: 0px;padding: 0px;background-color:#e9f5fd;">
                                <input type="hidden" name="imgName" id="imgName" />
                                <input type="file" name="img" id="img" onblur="getFileName()" style="width: 140px;" />
                                <img src="<s:text name='sundyn.pic.upload'/>" width="60" height="25" onclick="pauseUpload()" class="hand" />
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.logo" /></td>
                        <td align="left" style="border-color:#FFFFFF;">
                            <input type="text" name="deptLogoPic" id="deptLogoPic" class="input_comm" />

                            <form id="pic2" enctype="multipart/form-data" name="pic2"	action="employeeAdd.action" method="post" style="margin: 0px;padding: 0px;background-color:#e9f5fd;">
                                <input type="hidden" name="imgName2" id="imgName2" />
                                <input type="file" name="img2" id="img2" onblur="getFileName()" style="width: 140px;" />
                                <img src="<s:text name='sundyn.pic.upload'/>" width="60" height="25" onclick="DeptLogoUpload()" class="hand" />
                            </form>
                        </td>
                    </tr>

                </c:if>
                <c:if test="${deptType==0}">
                    <tr >
                        <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.system.unit.AudioAndVideo"></s:text></td>
                        <td align="left" style="border-color:#FFFFFF;">
                            <div class="layui-input-inline">
                            <select name="useVideo" id="useVideo" >
                                <option value="0" <c:if test="${dept.useVideo=='0'}"> selected="selected" </c:if>><s:text name="sundyn.system.unit.bothNot"></s:text></option>
                                <option value="1" <c:if test="${dept.useVideo=='1'}"> selected="selected" </c:if>><s:text name="sundyn.system.unit.useAudio"></s:text></option>
                                <option value="2" <c:if test="${dept.useVideo=='2'}"> selected="selected" </c:if>><s:text name="sundyn.system.unit.useVideo"></s:text></option>
                            </select>
                            </div>
                        </td>
                    </tr>
                </c:if>
                <s:if test='getText("sundyn.language") eq "en"'>
                <tr  style="display:none;">
                    </s:if>
                    <s:else>
                <tr>
                    </s:else>
                    <td align="right" style="border-color:#FFFFFF;">所属地区：</td>
                    <td align="left" style="border-color:#FFFFFF;" class="layui-form-item">
                        <div class="layui-input-inline" style="width:100px;">
                        <select name="provinceid" value="${province.id}" id="provinces" lay-filter="provinces">
                            <s:iterator value="provinces">
                                <option value="${id}" <s:if test="province.id==id">selected</s:if>>${ name }</option>
                            </s:iterator>
                        </select>
                        </div>
                        <div class="layui-input-inline" style="width:100px;">
                        <select  value="${cityid}" name="cityid" id="citys">
                            <s:iterator value="citys" >
                                <option value="${id}" <s:if test="cityid==id">selected</s:if> >${name }</option>
                            </s:iterator>
                        </select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="right" style="border-color:#FFFFFF;">
                        <button class="layui-btn" onclick="addChildItem(<%=request.getParameter("deptId")%>)">保存</button>
                    </td>
                    <td align="left" style="border-color:#FFFFFF;">
                        <button class="layui-btn layui-btn-primary" onclick="parent.closeDialog()">取消</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
		</div>
<script>
    $(function(){
        layui.use(['form'], function(){
            var form = layui.form
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;
            form.on('select(provinces)', function(data){
                showCitys(function(){form.render('select')});
            });
        });
    });
</script>