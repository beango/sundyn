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
<form class="layui-form" action="counterPost.action" method="post">
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
        <label class="layui-form-label">窗口号：</label>
        <div class="layui-input-inline">
            <input type="text" name="counterno" id="counterno" class="layui-input" value="${entity.counterno}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">窗口名称：</label>
        <div class="layui-input-inline">
            <input type="text" name="countername" id="countername" class="layui-input" value="${entity.countername}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">窗口分组编码：</label>
        <div class="layui-input-inline">
            <input type="text" name="busno" id="busno" class="layui-input" value="${entity.busno}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">窗口类型：</label>
        <div class="layui-inline">
            <input type="radio" title="咨询窗口" name="countertype" id="countertype" value="1" <c:if test="${entity.countertype==1}">checked="checked"</c:if> />
            <input type="radio" title="办件窗口" name="countertype" id="countertype" value="2" <c:if test="${entity.countertype==2}">checked="checked"</c:if> />
            <input type="radio" title="出件窗口" name="countertype" id="countertype" value="3" <c:if test="${entity.countertype==3}">checked="checked"</c:if> />
            <input type="radio" title="其他" name="countertype" id="countertype" value="4" <c:if test="${entity.countertype==4}">checked="checked"</c:if> />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">物理呼叫器地址：</label>
        <div class="layui-input-inline">
            <input type="text" name="pysicalcallpadaddr" id="pysicalcallpadaddr" class="layui-input" value="${entity.pysicalcallpadaddr}" />
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">LED屏地址：</label>
            <div class="layui-input-inline">
                <input type="text" name="counterledaddr" id="counterledaddr" class="layui-input" value="${entity.counterledaddr}" />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">综合LED屏地址：</label>
        <div class="layui-input-inline">
            <input type="text" name="compledaddr" id="compledaddr" class="layui-input" value="${entity.compledaddr}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">窗口叫号语音播放模式：</label>
        <div class="layui-input-inline">
            <input type="text" name="palysoundcontent" id="palysoundcontent" class="layui-input" value="${entity.palysoundcontent}" style="width:524px;" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务办理分组1：</label>
        <div class="layui-input-inline">
            <input type="text" name="serviceseriallist1" id="serviceseriallist1" class="layui-input" value="${entity.serviceseriallist1}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务办理分组2：</label>
        <div class="layui-input-inline">
            <input type="text" name="serviceseriallist2" id="serviceseriallist2" class="layui-input" value="${entity.serviceseriallist2}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务办理分组3：</label>
        <div class="layui-input-inline">
            <input type="text" name="serviceseriallist3" id="serviceseriallist3" class="layui-input" value="${entity.serviceseriallist3}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务办理分组4：</label>
        <div class="layui-input-inline">
            <input type="text" name="serviceseriallist4" id="serviceseriallist4" class="layui-input" value="${entity.serviceseriallist4}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">业务办理分组5：</label>
        <div class="layui-input-inline">
            <input type="text" name="serviceseriallist5" id="serviceseriallist5" class="layui-input" value="${entity.serviceseriallist5}" />
        </div>
    </div>
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