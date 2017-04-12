function dialog()
{
    var bgObj,msgObj;
    this.close=function(o)
    {
        document.body.removeChild(document.getElementById("bgDiv")); 
		document.getElementById(o).style.display="none";
    }
    this.show=function(o)
    {   
    	var document=window.parent
        var sWidth,sHeight; 
        var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
        sWidth=document.body.scrollWidth; 
        sHeight=document.body.scrollHeight;
        bgObj=document.createElement("div"); 
        bgObj.setAttribute('id','bgDiv'); 
        bgObj.style.position="absolute"; 
        bgObj.style.top="0"; 
        bgObj.style.background="#99a4a0"; 
        bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=70,finishOpacity=70)"; 
        bgObj.style.opacity="0.7"; 
        bgObj.style.left="0"; 
        bgObj.style.width=sWidth + "px"; 
        bgObj.style.height=sHeight+"px"; 
        bgObj.style.zIndex = "100"; 
        document.body.appendChild(bgObj);
        msgObj=document.getElementById(o);
        msgObj.style.position = "absolute"; 
        msgObj.style.top = (scrollTop+50)+"px"; 
        msgObj.style.zIndex = "101"; 
		msgObj.style.display = "";
		msgObj.style.left = (sWidth-msgObj.offsetWidth)/2+"px";
    }
    this.showcustom=function(o,top,height)
    {   
        var sWidth,sHeight; 
        var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
        sWidth=document.body.scrollWidth;
        sHeight=document.body.scrollHeight>height?document.body.scrollHeight:height;
        bgObj=document.createElement("div"); 
        bgObj.setAttribute('id','bgDiv'); 
        bgObj.style.position="absolute"; 
        bgObj.style.top="0"; 
        bgObj.style.background="#000000"; 
        bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=60)"; 
        bgObj.style.opacity="0.6"; 
        bgObj.style.left="0"; 
        bgObj.style.width=sWidth + "px"; 
        bgObj.style.height=sHeight+"px"; 
        bgObj.style.zIndex = "100"; 
        document.body.appendChild(bgObj);
        msgObj=document.getElementById(o);
        msgObj.style.position = "absolute"; 
        msgObj.style.top = (scrollTop+top)+"px"; 
        msgObj.style.zIndex = "101"; 
		msgObj.style.display = "";
		msgObj.style.left = (sWidth-msgObj.offsetWidth)/2+"px";
    }
}