/* 
**************************** 
*��  ��url�н�ȡ���� ������* 
**************************** 
*������˵��: 
* urlId:Ҫ��ȡ������ 
* splitStr, isTop:��ѡ�� 
* ����ֵ���ͣ��ַ��� 
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
* ������ ���<=>ȫ�� ������* 
**************************** 
*������˵��: 
* str:Ҫת�����ַ��� 
* flag:��ǣ�Ϊ��ʱ��תȫ��Ϊ�ǣ�ʱȫת�� 
* ����ֵ���ͣ��ַ��� 
**************************** 
*/ 
function DBC2SBC(str,flag) { 
var i; 
var result=''; 
if (str.length<=0) {alert('�ַ�����������');return false;} 
for(i=0;i<str.length;i++) 
{ str1=str.charCodeAt(i); 
if(str1<125&&!flag) 
result+=String.fromCharCode(str.charCodeAt(i)+65248); 
else 
result+=String.fromCharCode(str.charCodeAt(i)-65248); 
} 
return result; 
} 
