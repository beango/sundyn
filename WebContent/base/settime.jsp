<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title><s:text name='zx.title'/></title>
		<style type="text/css">
		   table tr td {
	        font-size: 12px;
           }
		</style>
		<script  language="javascript">
		  function check() {
              var patrn=/^[0-9][0-9]:[0-9][0-9]$/;
              var sam=document.getElementById("sam").value;
              var eam=document.getElementById("eam").value;
              var spm=document.getElementById("spm").value;
              var epm=document.getElementById("epm").value;
              if(!patrn.test(sam)){
                alert("上午上班时间不合法，正确应该为00:00");
                 return false;
               }
             if(!patrn.test(eam)){
                alert("上午下班时间不合法，正确应该为00:00");
                return false;
              }
              if(!patrn.test(spm)){
                alert("下午上班时间不合法，正确应该为00:00");
                return false;
              }
              if(!patrn.test(epm)){
                alert("下午下班时间不合法，正确应该为00:00");
                return false;
              }
              document.getElementById("f").submit();
           }
		</script>
	</head>
	<body>
		<div id="man_zone">
		<form action="baseTimeSave.action" method="get" onsubmit="return check123123();" id="f">
		   <div class="fengge">&nbsp;</div>
           <div align="center"><img src="images/time.gif" /></div>
		   <div style="width:728px;" class="kuang">
			     <div class="fengge"></div>
				 <div style="background-image: url('images/title2_bg.gif');background-repeat: no-repeat;width: 708px;height: 20px;"><div style="float: left;width: 200px;text-align: left;margin-left:20px;font-size: 12px;color: #000;font-weight: bold;">上午上班时间</div> </div>
				 <div style="width:706px;border-left:1px solid #95c3e7;border-right:1px solid #95c3e7; padding-top:10px;" >
			       <table width="100%" height="63" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
			            <tr>
			              <td style="border-color:#FFFFFF;" width="16%" align="right"  >上午上班时间：</td>
			              <td style="border-color:#FFFFFF;" colspan="5"><label>
			                	<input type="text" value="${sam}" size="50"	name="sam" id="sam"  />
			              </label></td>
			            </tr>
			            <tr>
			              <td style="border-color:#FFFFFF;" width="16%" align="right"  >上午下班时间：</td>
			              <td style="border-color:#FFFFFF;" colspan="5"><label>
			                	<input type="text" value="${eam}" size="50"	name="eam" id="eam"  />
			              </label></td>
			            </tr>
			       </table>   
	             </div>
				<div style="background-image: url('images/title2_bg.gif');background-repeat: no-repeat;width: 708px;height: 20px;"><div style="float: left;width: 500px;text-align: left;margin-left:20px;font-size: 12px;color: #000;font-weight: bold;">下午上班时间</div> </div>
				<div style="width:706px;border-left:1px solid #95c3e7;border-right:1px solid #95c3e7; padding-top:10px;" >
			      <table width="100%" height="63" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
			            <tr>
			              <td style="border-color:#FFFFFF;" width="16%" align="right"  >下午上班时间：</td>
			              <td style="border-color:#FFFFFF;" colspan="5"><label>
			                	<input type="text" value="${spm}" size="50"	name="spm" id="spm"  />
			              </label></td>
			            </tr>
			            <tr>
			              <td style="border-color:#FFFFFF;" width="16%" align="right"  >下午下班时间：</td>
			              <td style="border-color:#FFFFFF;" colspan="5"><label>
			                	<input type="text" value="${epm}" size="50"	name="epm" id="epm"  />
			              </label></td>
			            </tr>
			       </table>    
				 </div>
 				 <div class="center_04_right_03" style="width:708px;">
 					  <img src="images/12_08_13.jpg" onclick="  check();" class="hand" />&nbsp;
					  <img src="images/12_09_15.jpg" onclick="f.reset()" class="hand" />
				 </div>
				 <div class="fenge">&nbsp;</div>
		</div>
		</form>	 
	</div>
	<div style="float: left;"><img height="7" width="763" border="0" src="images/man_zone_bottom.gif"/></div>
 	</body>
</html>
