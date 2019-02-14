<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:forEach items="${weburls}" var="webUrl" varStatus="index">
    <tr>
        <td style="text-align: center;width:32px;">
                ${index.index+1}
        </td>
        <td style="text-align: center;width:232px;">
                ${webUrl.name}
        </td>
        <td style="text-align: center;">
                ${webUrl.url}
        </td>
        <td style="text-align: center;width:132px;">
            <a href="javascript:weburlToUpate('${webUrl.id}');"><s:text name='sundyn.modifyOrupdate'/></a>
            <a href="javascript:weburlDelete('${webUrl.id}');"><s:text name='sundyn.del'/></a>
        </td>
    </tr>
</c:forEach>
