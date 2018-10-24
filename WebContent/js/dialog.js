function dialog()
{
    var bgObj,msgObj;
    this.close=function(o)
    {
        document.body.removeChild(document.getElementById("bgDiv"));
		document.getElementById(o).style.display="none";
    }
    this.show=function(resp){
        layer.open({
            type: 1,
            title: '提示页',
            shadeClose: true,
            shade: 0.8,
            area: ['600px', '60%'],
            content: resp,
            success:function(ly,index){
                //layer.iframeAuto(index);
            }
        });
    }

    this.iframe=function(url, args, cb){
        var index = layer.open({
            type: 2,
            title: args && args.title ? args.title : '',
            shadeClose: true,
            shade: [0.8, '#393D49'],//遮罩层
            area: [(args && args.w) ? args.w : '600px', (args && args.h) ? args.h : '80%'],
            content: url,
            success:function(ly, index){
                if(cb)cb();
                if(args && args.full){
                    layer.full(index);
                }
                else if(args && (args.resize!=undefined || args.resize===false)){
                }
                else{
                    layer.iframeAuto(index);
                }
            }
        });
        //layer.full(index);
    }

    this.warn = function(msg, close){
        layer.msg(msg, {
            icon: 2,
            time: 800
        }, function(){
            if(close){
                closeDialog();
            }
        });
    }

    this.show1=function(o)
    {
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