<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="employeeEditDialog">
	<div class="sundyn_row">
		<div class="image1">
			<img src="images/my/1.gif" />
		</div>
		<div class="image2">
			<img src="images/my/2.gif" />
		</div>
	</div>
	<h3>
		修改员工
	</h3>
	<div class="sundyn_row">
		<div class="sundyn_text24">
			<div class="sundyn_text24">
				<div class="sundyn_text21">
					员工姓名:
				</div>
				<div class="sundyn_text23">
					<input type="hidden" name="employeeId" id="employeeId"
						value="${m.Id}" />
					<input type="hidden" name="dept" id="dept" />
					<input type="text" name="Name" id="Name" value="${m.Name}" />
				</div>
			</div>
			<div class="sundyn_text24">
				<div class="sundyn_text21">
					职务:
				</div>
				<div class="sundyn_text23">
					<input type="text" name=job_desc id="job_desc"
						value="${m.job_desc}" />
				</div>
			</div>
			<div class="sundyn_text24">
				<div class="sundyn_text21">
					性别:
				</div>
				<div class="sundyn_text23">

					<input type="radio" name="Sex" id="Sex" value="男"
						<c:if test="${m.Sex=='男'}">checked="checked"  </c:if>>
					男
					<input type="radio" name="Sex" id="Sex" value="女"
						<c:if test="${m.Sex=='女'}">checked="checked"  </c:if>>
					女
				</div>
			</div>
			<div class="sundyn_text24">
				<div class="sundyn_text21">
					员工卡号:
				</div>

				<div class="sundyn_text23">
					<input type="text" name="CardNum" id="CardNum" value="${m.CardNum}" />
				</div>
			</div>
			<div class="sundyn_text24">
				<div class="sundyn_text21">
					联系方式:
				</div>
				<div class="sundyn_text23">
					<input type="text" name="Phone" id="Phone" value="${m.Phone}" />
				</div>
			</div>
			<div class="sundyn_text24">
				<div class="sundyn_text21">
					照片:
				</div>
				<div class="sundyn_text23">
					<form id="pic" enctype="multipart/form-data" name="pic"
						action="employeeAdd.action" method="post">
						<input type="hidden" name="imgName" id="imgName"
							value="${m.picture}" />
						<input type="file" name="img" id="img" onblur="getFileName()" />
						<input type="button" value="上传" onclick="employeeUpload()">
					</form>
				</div>
			</div>
		</div>
		<div class="sundyn_text242">
			<img src="${m.picture}" id="img123">
		</div>
	</div>

	<div class="sundyn_row">
		<input type="button" value="修改" onclick="employeeEdit()" />
		<input type="button" value="关闭" onclick="closeDialog()" />
	</div>
	<div class="sundyn_row">
		<div class="image1">
			<img src="images/my/3.gif" />
		</div>
		<div class="image2">
			<img src="images/my/4.gif" />
		</div>
	</div>
</div>