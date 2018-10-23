<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="lib/jquery.form.min.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
        .layui-form-label{width:120px;}
    </style>
    <script type="text/javascript">
        function formPost(){
            $("#hallid").val($("#hallsele").val());
            $('form').ajaxForm({
                beforeSubmit:  validate,    // 提交前，验证
                success: function(resp) {
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
                            time: 1200
                        }, function(){
                        });
                    }
                }
            });
        }

        function validate(formData, jqForm, options) {
            var usernameValue = $('#hallno').fieldValue();
            var addressValue = $('#hallname').fieldValue();

            if (!usernameValue[0] || !addressValue[0]) {
                //alert('用户名和地址不能为空，自我介绍可以为空！');
                //return false;
            }
            return true;
        }
    </script>
</head>

<body>
<form class="layui-form" action="serialPost.action" method="post">
    <input type="hidden" name="id" id="id" value="${entity.id}" />
    <input type="hidden" name="hallid" id="hallid" value="" />
    <div class="layui-form-item">
        <label class="layui-form-label">服务厅：</label>
        <div class="layui-input-inline">
            <select id="hallsele">
                <c:forEach items="${hallList}" var="hall" varStatus="index">
                    <option <c:if test="${hall.id==entity.hallid}"> selected="selected"</c:if> value="${hall.id}">${hall.hallname}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务事项id：</label>
        <div class="layui-input-inline">
            <input type="text" name="bizid" id="bizid" class="layui-input" value="${entity.bizid}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务名称：</label>
        <div class="layui-input-inline">
            <input type="text" name="bizname" id="bizname" class="layui-input" value="${entity.bizname}" />
        </div>
    </div>
    <%--<div class="layui-form-item">
        <label class="layui-form-label">业务类型：</label>
        <div class="layui-inline">
            <input type="radio" title="办件类业务" name="biztype" id="biztype" value="1" <c:if test="${entity.biztype==1}">checked="checked"</c:if> />
            <input type="radio" title="咨询业务" name="biztype" id="biztype" value="2" <c:if test="${entity.biztype==2}">checked="checked"</c:if> />
            <input type="radio" title="出件类业务" name="biztype" id="biztype" value="3" <c:if test="${entity.biztype==3}">checked="checked"</c:if> />
            <input type="radio" title="其他类" name="biztype" id="biztype" value="4" <c:if test="${entity.biztype==4}">checked="checked"</c:if> />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">取号机分组号：</label>
        <div class="layui-input-inline">
            <input type="text" name="ticketmachineno" id="ticketmachineno" class="layui-input" value="${entity.ticketmachineno}" />
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">在线取号：</label>
            <div class="layui-input-inline">
                <input type="checkbox" title="在线取号" name="onlinenetticket" id="onlinenetticket" value="1" <c:if test="${entity.onlinenetticket==1}">checked="checked"</c:if> />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务说明：</label>
        <div class="layui-input-inline">
            <input type="text" name="bizdiscrible" id="bizdiscrible" class="layui-input" value="${entity.bizdiscrible}" style="width:524px;" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务预约说明：</label>
        <div class="layui-input-inline">
            <input type="text" name="bizappointnote" id="bizappointnote" class="layui-input" value="${entity.bizappointnote}" style="width:524px;" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">排队号前缀：</label>
        <div class="layui-input-inline">
            <input type="text" name="xcqueuenumberprefix" id="xcqueuenumberprefix" class="layui-input" value="${entity.xcqueuenumberprefix}" />
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">现场号开始号：</label>
            <div class="layui-input-inline">
                <input type="text" name="xcsnumber" id="xcsnumber" class="layui-input" value="${entity.xcsnumber}" />
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">现场号结束号：</label>
            <div class="layui-input-inline">
                <input type="text" name="xcenumber" id="xcenumber" class="layui-input" value="${entity.xcenumber}" />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">预约号打印号码前缀：</label>
        <div class="layui-input-inline">
            <input type="text" name="yyqueuenumberprefix" id="yyqueuenumberprefix" class="layui-input" value="${entity.yyqueuenumberprefix}" />
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">预约号开始号：</label>
            <div class="layui-input-inline">
                <input type="text" name="ysnumber" id="ysnumber" class="layui-input" value="${entity.ysnumber}" />
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">预约号结束号：</label>
            <div class="layui-input-inline">
                <input type="text" name="yenumber" id="yenumber" class="layui-input" value="${entity.yenumber}" />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">暂停取号标志：</label>
            <div class="layui-input-inline">
                <input type="checkbox" title="暂停取号" name="ispauseticket" id="ispauseticket" value="1" <c:if test="${entity.ispauseticket==1}">checked="checked"</c:if> />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">后续业务ID：</label>
        <div class="layui-input-inline">
            <input type="text" name="transferbizid" id="transferbizid" class="layui-input" value="${entity.transferbizid}" />
        </div>
    </div>--%>
    <%--<div class="layui-form-item">
        <label class="layui-form-label">预计办理时长：</label>
        <div class="layui-input-inline">
            <input type="text" name="expectedservicetime" id="expectedservicetime" class="layui-input" value="${entity.expectedservicetime}" />
        </div>
    </div>--%>
    <div class="layui-form-item">
        <label class="layui-form-label">超时办理时长：</label>
        <div class="layui-input-inline">
            <input type="text" name="serviceouttime" id="serviceouttime" class="layui-input" value="${entity.serviceouttime}" />
        </div>分钟
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">等候超时时长：</label>
        <div class="layui-input-inline">
            <input type="text" name="waitouttime" id="waitouttime" class="layui-input" value="${entity.waitouttime}" />
        </div>分钟
    </div>
    <%--<div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">取号验证：</label>
            <div class="layui-input-inline">
                <input type="checkbox" title="取号验证" name="isverfifyticket" id="isverfifyticket" value="1" <c:if test="${entity.isverfifyticket==1}">checked="checked"</c:if> />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">提醒位置：</label>
        <div class="layui-input-inline">
            <input type="text" name="smssendpernumbers" id="smssendpernumbers" class="layui-input" value="${entity.smssendpernumbers}" />
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">取号是否需要输入用户信息：</label>
            <div class="layui-input-inline">
                <input type="checkbox" title="是否输入" name="isinputcustomerinfo" id="isinputcustomerinfo" value="1" <c:if test="${entity.isinputcustomerinfo==1}">checked="checked"</c:if> />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">窗口信息：</label>
        <div class="layui-input-inline">
            <input type="text" name="servicescounter" id="servicescounter" class="layui-input" value="${entity.servicescounter}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">取号限制：</label>
        <div class="layui-input-inline">
            <table style="width:524px;text-align: center;">
                <tr>
                    <td></td>
                    <td>
                        周一
                    </td>
                    <td>
                        周二
                    </td>
                    <td>
                        周三
                    </td>
                    <td>
                        周四
                    </td>
                    <td>
                        周五
                    </td>
                    <td>
                        周六
                    </td>
                    <td>
                        周七
                    </td>
                </tr>
                <tr style="height: 30px;">
                    <td>上午</td>
                    <td>
                        <input type="text" name="alimitnum1" id="alimitnum1" class="layui-input" value="${entity.alimitnum1}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="alimitnum2" id="alimitnum2" class="layui-input" value="${entity.alimitnum2}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="alimitnum3" id="alimitnum3" class="layui-input" value="${entity.alimitnum3}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="alimitnum4" id="alimitnum4" class="layui-input" value="${entity.alimitnum4}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="alimitnum5" id="alimitnum5" class="layui-input" value="${entity.alimitnum5}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="alimitnum6" id="alimitnum6" class="layui-input" value="${entity.alimitnum6}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="alimitnum7" id="alimitnum7" class="layui-input" value="${entity.alimitnum7}" style="width:60px;" />
                    </td>
                </tr>
                <tr>
                    <td>下午</td>
                    <td>
                        <input type="text" name="plimitnum1" id="plimitnum1" class="layui-input" value="${entity.plimitnum1}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="plimitnum2" id="plimitnum2" class="layui-input" value="${entity.plimitnum2}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="plimitnum3" id="plimitnum3" class="layui-input" value="${entity.plimitnum3}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="plimitnum4" id="plimitnum4" class="layui-input" value="${entity.plimitnum4}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="plimitnum5" id="plimitnum5" class="layui-input" value="${entity.plimitnum5}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="plimitnum6" id="plimitnum6" class="layui-input" value="${entity.plimitnum6}" style="width:60px;" />
                    </td>
                    <td>
                        <input type="text" name="plimitnum7" id="plimitnum7" class="layui-input" value="${entity.plimitnum7}" style="width:60px;" />
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">取号时间段：</label>
        <div class="layui-input-inline">
            <table style="width:854px;text-align: center;">
                <tr>
                    <td></td>
                    <td>
                        周一
                    </td>
                    <td>
                        周二
                    </td>
                    <td>
                        周三
                    </td>
                    <td>
                        周四
                    </td>
                    <td>
                        周五
                    </td>
                    <td>
                        周六
                    </td>
                    <td>
                        周七
                    </td>
                </tr>
                <tr style="height: 30px;margin-bottom:5px;">
                    <td>上午</td>
                    <td>
                        <input type="text" name="wk1ticketworktimeam1" id="wk1ticketworktimeam1" value="${entity.wk1ticketworktimeam1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk1ticketworktimeam2" id="wk1ticketworktimeam2" value="${entity.wk1ticketworktimeam2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk2ticketworktimeam1" id="wk2ticketworktimeam1" value="${entity.wk2ticketworktimeam1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk2ticketworktimeam2" id="wk2ticketworktimeam2" value="${entity.wk2ticketworktimeam2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk3ticketworktimeam1" id="wk3ticketworktimeam1" value="${entity.wk3ticketworktimeam1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk3ticketworktimeam2" id="wk3ticketworktimeam2" value="${entity.wk3ticketworktimeam2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk4ticketworktimeam1" id="wk4ticketworktimeam1" value="${entity.wk4ticketworktimeam1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk4ticketworktimeam2" id="wk4ticketworktimeam2" value="${entity.wk4ticketworktimeam2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk5ticketworktimeam1" id="wk5ticketworktimeam1" value="${entity.wk5ticketworktimeam1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk5ticketworktimeam2" id="wk5ticketworktimeam2" value="${entity.wk5ticketworktimeam2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk6ticketworktimeam1" id="wk6ticketworktimeam1" value="${entity.wk6ticketworktimeam1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk6ticketworktimeam2" id="wk6ticketworktimeam2" value="${entity.wk6ticketworktimeam2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk7ticketworktimeam1" id="wk7ticketworktimeam1" value="${entity.wk7ticketworktimeam1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk7ticketworktimeam2" id="wk7ticketworktimeam2" value="${entity.wk7ticketworktimeam2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                </tr>
                <tr>
                    <td>下午</td>
                    <td>
                        <input type="text" name="wk1ticketworktimepm1" id="wk1ticketworktimepm1" value="${entity.wk1ticketworktimepm1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk1ticketworktimepm2" id="wk1ticketworktimepm2" value="${entity.wk1ticketworktimepm2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk2ticketworktimepm1" id="wk2ticketworktimepm1" value="${entity.wk2ticketworktimepm1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk2ticketworktimepm2" id="wk2ticketworktimepm2" value="${entity.wk2ticketworktimepm2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk3ticketworktimepm1" id="wk3ticketworktimepm1" value="${entity.wk3ticketworktimepm1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk3ticketworktimepm2" id="wk3ticketworktimepm2" value="${entity.wk3ticketworktimepm2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk4ticketworktimepm1" id="wk4ticketworktimepm1" value="${entity.wk4ticketworktimepm1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk4ticketworktimepm2" id="wk4ticketworktimepm2" value="${entity.wk4ticketworktimepm2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk5ticketworktimepm1" id="wk5ticketworktimepm1" value="${entity.wk5ticketworktimepm1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk5ticketworktimepm2" id="wk5ticketworktimepm2" value="${entity.wk5ticketworktimepm2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk6ticketworktimepm1" id="wk6ticketworktimepm1" value="${entity.wk6ticketworktimepm1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk6ticketworktimepm2" id="wk6ticketworktimepm2" value="${entity.wk6ticketworktimepm2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />
                    </td>
                    <td>
                        <input type="text" name="wk7ticketworktimepm1" id="wk7ticketworktimepm1" value="${entity.wk7ticketworktimepm1}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate" />-
                        <input type="text" name="wk7ticketworktimepm2" id="wk7ticketworktimepm2" value="${entity.wk7ticketworktimepm2}" style="width:50px;height:28px;" onclick="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="Wdate"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>--%>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <input type="submit" class="layui-btn" value="提交" onclick="formPost()" />
        </div>
    </div>
</form>
<script type="text/javascript">
    layui.use(['form', 'element'], function() {
        var form = layui.form;
    });

</script>
</body>
</html>