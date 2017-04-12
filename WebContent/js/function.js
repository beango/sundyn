/* 
**************************** 
*　  从url中截取参数 　　　* 
**************************** 
*　参数说明: 
* urlId:要截取参数名 
* splitStr, isTop:可选项 
* 返回值类型：字符串 
**************************** 
*/ 
function SQ(urlId, splitStr, isTop)
{
    var Url = !isTop ? window.location.href : top.window.location.href;
    var u = Url.indexOf(splitStr == null ? '?' : splitStr);
    var g = u >= 0 ? Url.substring(u + 1) : '';
    if(g != '')
    {
        if(urlId == null) 
        {
            return g;
        }
        else 
        {
            var gg = g.split('&');
            var str = urlId + '=';
            for(xm = 0; xm < gg.length; xm++)
            {
                if (gg[xm].indexOf(str) == 0) 
                {
                    return ((gg[xm].replace(str, '')).split('#')[0]);
                }
            }
        }
    }
    return '';
}

/* 
**************************** 
* 　　　 半角<=>全角 　　　* 
**************************** 
*　参数说明: 
* str:要转换的字符串 
* flag:标记，为０时半转全，为非０时全转半 
* 返回值类型：字符串 
**************************** 
*/ 
function DBC2SBC(str,flag) { 
var i; 
var result=''; 
if (str.length<=0) {alert('字符串参数出错');return false;} 
for(i=0;i<str.length;i++) 
{ str1=str.charCodeAt(i); 
if(str1<125&&!flag) 
result+=String.fromCharCode(str.charCodeAt(i)+65248); 
else 
result+=String.fromCharCode(str.charCodeAt(i)-65248); 
} 
return result; 
} 
