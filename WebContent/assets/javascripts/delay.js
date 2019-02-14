/**easyui加载等待效果
 *
 */

//获取浏览器页面可见高度和宽度
var _PageHeight = window.screen.height,
_PageWidth = window.screen.width;
//计算loading框距离顶部和左部的距离（loading框的宽度为215px，高度为61px）
var _LoadingTop = _PageHeight > 61 ? (_PageHeight - 61) / 2 : 0,
_LoadingLeft = _PageWidth > 1200 ? 500 : _PageWidth>1000?350: 100;
//加载gif地址
var Loadimagerul=ctx+"/js/easyui-1.5.3/themes/bootstrap/images/loading.gif";
//在页面未加载完毕之前显示的loading Html自定义内容
var _LoadingHtml ="<div id='maskLoading' style='position:absolute;z-index:100;left:0;width:100%;height:"+_PageHeight+"px;top:0;background:#FFF;filter:alpha(opacity=100);-khtml-opacity:1;-moz-opacity:1;opacity:1;'>\  <div style='position:absolute;  cursor:wait;left:"+_LoadingLeft+"px;top:200px;width:auto;height:45px;background:#fff url("+Loadimagerul+")  no-repeat scroll 5px 10px;padding:9px 5px 10px 30px;border:2px solid #ccc;color:#000;'>\  正在加载，请等待...\  </div></div>";   //呈现loading效果
document.write(_LoadingHtml);
//监听加载状态改变
document.onreadystatechange = completeLoading;
//加载状态为complete时移除loading效果
function completeLoading() {
if (document.readyState == "complete" & $("#dataTable").size()==0) {
var loadingMask = document.getElementById('maskLoading');
loadingMask.parentNode.removeChild(loadingMask);
}
}