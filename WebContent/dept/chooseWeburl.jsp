<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/choise.js"></script>--%>
<%--    --%>
<%--      <div style="width:200px;height:270px;">--%>
<%--		<form name="form1" method="post">--%>
<%--			<table border="0" align="center" cellpadding="0" cellspacing="1">--%>
<%--				<tr>--%>
<%--					<td height="200" width="20%" class="alt" align="center">--%>
<%--						已经选择--%>
<%--						<div align="center">--%>
<%--							<select multiple="multiple" name="CmenuNames" id="CmenuNames" style="width: 200px; height: 300px; background-color: #F5FAFA; border-style: none;">--%>
<%--							  <s:iterator value="haveList">--%>
<%--							  	 <option value="${id }">--%>
<%--									${name }--%>
<%--								</option>--%>
<%--							  </s:iterator>--%>
<%--							</select>--%>
<%--						</div>--%>
<%--					</td>--%>
<%--					<td height="200" align="left">--%>
<%--						--%>
<%--						<input type="button" value="添加 " class="btn3_mouseover" onclick="moveMenus();">--%>
<%--						<br>--%>
<%--						<br>--%>
<%--						<input type="button" value="移除 " class="btn3_mouseover" onclick="removeMenus();">--%>
<%--						<br>--%>
<%--						<br>--%>
<%--						<input type="button" value="提交" onclick="deptWeburlAdd();" class="btn3_mouseover">--%>
<%--					</td>--%>
<%--					<td height="200" width="75%" class="alt" align="left">--%>
<%--					    请选择--%>
<%--						<div align="left">--%>
<%--							<select multiple="multiple" name="menuNames" id="menuNames" style="width: 200px; height: 300px; background-color: #F5FAFA; border-style: none;">--%>
<%--								 <s:iterator value="noList">--%>
<%--							  	 <option value="${id }">--%>
<%--									${name }--%>
<%--								</option>--%>
<%--							  </s:iterator>--%>
<%--							</select>--%>
<%--						</div>--%>
<%--					</td>--%>
<%--				</tr>--%>
<%--			</table>--%>
<%--		</form>--%>
<%--	</div>--%>
<form name="form1" class="layui-form" method="post">
    <table border="0" align="center" cellpadding="0" cellspacing="1">
        <tr>
            <td height="100%" width="40%" class="alt" align="center">
            <s:text name="sundyn.weburl.bind.noChoiced" />
            <div align="left">
                <select multiple="multiple" name="menuNames" id="menuNames" style="width: 180px; height: 190px; background-color: #F5FAFA; border-style: none;">
                    <s:iterator value="noList">
                        <option value="${id }">
                                ${name }
                        </option>
                    </s:iterator>
                </select>
            </div>
        </td>
            <td height="100%" align="left">
                <input type="button" value="<s:text name="sundyn.weburl.bind.add" /> >>" class="layer-input" onclick="moveMenus();">
                <br>
                <br>
                <input type="button" value="<< <s:text name="sundyn.weburl.bind.remove" /> " class="btn3_mouseover" onclick="removeMenus();">
                <br>
                <br>
            </td>
            <td height="100%" width="40%" class="alt" align="center">
                <s:text name="sundyn.weburl.bind.choiced" />
                <div align="center">
                    <select multiple="multiple" name="CmenuNames" id="CmenuNames" style="width: 180px; height: 190px; background-color: #F5FAFA; border-style: none;">
                        <s:iterator value="haveList">
                            <option value="${id }">
                                    ${name }
                            </option>
                        </s:iterator>
                    </select>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="3" align="center;border:0px;">
                <img src="<s:text name="sundyn.pic.ok" />" onclick="deptWeburlAdd();"  style="cursor: pointer;"/>
                <img src="<s:text name="sundyn.pic.close" />"  onclick="closeDialog()"   style="cursor: pointer;" />
            </td>
        </tr>
    </table>
    <div class="layui-form-item">

    </div>
</form>