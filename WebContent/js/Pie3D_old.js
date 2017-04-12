//3D Pie 类封装
//luchunwei@hotmail.com 卢春尉

            onit=true
            num=0
            function moveup(iteam,top,txt,rec){
            temp=eval(iteam);
            tempat=eval(top)
            temptxt=eval(txt)
            temprec=eval(rec);
            at=parseInt(temp.style.top);
            temprec.style.display = "";
            if (num>15){
            temptxt.style.display = "";
            }
            if(at>(tempat-16)&&onit){
            num++
            temp.style.top=at-1;
            Stop=setTimeout("moveup(temp,tempat,temptxt,temprec)",10)
            }else{
            return
            }
            }
            function movedown(iteam,top,txt,rec){
            temp=eval(iteam)
            temptxt=eval(txt)
            temprec=eval(rec)
            clearTimeout(Stop)
            temp.style.top=top
            num=0
            temptxt.style.display = "none";
            temprec.style.display = "none";
            }
            function ontxt(iteam,top,txt,rec){
            temp = eval(iteam);
            temptxt = eval(txt);
            temprec = eval(rec)
            if (onit){
            temp.style.top = top-28;
            temptxt.style.display = "";
            temprec.style.display = "";
            }
            }
            function movereset(over){
            if (over==1){
            onit=false
            }else{
            onit=true
            }
            }

function Pie3D(pContainer,pwidth,pheight,pcaption){
this.Container=document.getElementById(pContainer);
this.width=pwidth+'px' || "400px";
this.height=pheight+'px' || "250px";
this.caption=pcaption;
this.background="<v:rect id='background' style='position:absolute;left:1px;top:1px;WIDTH:"+this.width+";HEIGHT:"+this.height+";' fillcolor='#EFEFEF' strokecolor='gray'>"+
                        //"<v:shadow on='t' type='single' color='silver' offset='4pt,4pt'/>"+
                        "</v:rect>";
this.Cakes=new Array();   //[name,qty,color]
this.Percent=new Array();
this.vTotal=0;
this.Cake3d="<v:shapetype id='Cake_3D' coordsize='21600,21600' o:spt='95' adj='11796480,5400' path='al10800,10800@0@0@2@14,10800,10800,10800,10800@3@15xe'></v:shapetype>";
this.Text3d="<v:shapetype id='3dtxt' coordsize='21600,21600' o:spt='136' adj='10800' path='m@7,l@8,m@5,21600l@6,21600e'>"+
                    "<v:path textpathok='t' o:connecttype='custom' o:connectlocs='@9,0;@10,10800;@11,21600;@12,10800' o:connectangles='270,180,90,0'/>"+
                    "<v:textpath on='t' fitshape='t'/>"+
                    "<o:lock v:ext='edit' text='t' shapetype='t'/>"+
                    "</v:shapetype>";
this.draw=function(){
this.init();
this.drawCanvas();
this.drawCake();
}
}

Pie3D.prototype.init=function(){
this.vTotal=0;
for(var i=0;i<this.Cakes.length;i++){
this.vTotal+=this.Cakes[i][1];
}
for(var i=0;i<this.Cakes.length;i++){
this.Percent[i]=this.Cakes[i][1]/this.vTotal;//document.writeln(this.Percent[i])
}
}

Pie3D.prototype.drawCanvas=function(){
var canvas=document.createElement("v:group");
canvas.style.position='absolute';
canvas.style.top='3px';
canvas.style.left='2px';
canvas.style.width=this.width;
canvas.style.height=this.height;
canvas.coordsize='21000,11500';
this.Container.innerHTML=this.Cake3d+this.Text3d+this.background;
this.Container.appendChild(canvas);//alert(this.Container.innerHTML)
this.drawLegend(canvas);//alert(this.Container.innerHTML)
}

Pie3D.prototype.drawLegend=function(canvas){
var legend=document.createElement("v:rect");
legend.style.position='relative';
legend.style.top='100px';
legend.style.left='500px';
legend.style.width='20000px';
legend.style.height='1000px';
legend.stroked='false';
var captiontb=document.createElement("v:TextBox");
captiontb.inset='0pt,0pt,0pt,0pt';
captiontb.innerHTML="<table width='100%' border='0' align='center' cellspacing='0'><tr><td align='center' valign='middle'>"+
                                "<div style='font-size:12pt; '><B>"+this.caption+"</B></div>"+
                                "</td></tr></table>" ;
legend.appendChild(captiontb);
var plainarea=document.createElement("v:rect");
plainarea.style.position='relative';
plainarea.style.top='1200px';
plainarea.style.left='500px';
plainarea.style.width='20000px';
plainarea.style.height='10000px';
plainarea.strokecolor='#888';
plainarea.fillcolor='#9fc';
plainarea.onmouseover='movereset(1)';
plainarea.onmouseout='movereset(0)';
var fillStyle="<v:fill rotate='t' angle='-45' focus='100%' type='gradient'/>";
plainarea.innerHTML=fillStyle;

var legendbx=document.createElement("v:rect");
legendbx.style.position='relative';
legendbx.style.top='1400px';
legendbx.style.left='15000px';
legendbx.style.width='5000px';
legendbx.style.height='9200px';
legendbx.fillcolor='#9cf';
legendbx.stroked='t';
legendbx.strokecolor='#09f';
    fillStyle="<v:fill rotate='t' angle='-175' focus='100%' type='gradient'/>"+
                "<v:shadow on='t' type='single' color='silver' offset='1pt,1pt'/>";
legendbx.innerHTML=fillStyle;

var legendttl=document.createElement("v:rect");
legendttl.style.position='relative';
legendttl.style.top='1550px';
legendttl.style.left='15400px';
legendttl.style.width='4300px';
legendttl.style.height='900px';
legendttl.fillcolor='#afc';
legendttl.stroked='f';
legendttl.strokecolor='#000';
    fillStyle="<v:TextBox inset='2pt,0pt,0pt,0pt' style='font-size:10pt;'>"+
                "<div align='left'><font color='#000'>共:"+this.vTotal+"</font></div></v:TextBox>";
legendttl.innerHTML=fillStyle;
if(this.caption!=""){
 canvas.appendChild(legend);
}
canvas.appendChild(plainarea);
canvas.appendChild(legendbx);
canvas.appendChild(legendttl);

for(var i=0;i<this.Cakes.length;i++){topx=2500+i*768;
var legendxbg=document.createElement("v:rect");
legendxbg.id='rec'+(i+1);
legendxbg.style.position='relative';
legendxbg.style.top=topx+'px';
legendxbg.style.display='none';
legendxbg.style.left='15400px';
legendxbg.style.width='4300px';
legendxbg.style.height='900px';
legendxbg.fillcolor='#efefef';
legendxbg.strokecolor=this.Cakes[i][2];
    fillStyle="<v:fill opacity='.6' color2='fill darken(118)' o:opacity2='.6' rotate='t' method='linear sigma' focus='100%' type='gradient'/>";
legendxbg.innerHTML=fillStyle;
var legendx=document.createElement("v:rect");
legendx.style.position='relative';
legendx.style.top=(topx+50)+'px';
legendx.style.left='15500px';
legendx.style.width='600px';
legendx.style.height='700px';
legendx.fillcolor=this.Cakes[i][2];
legendx.stroked='f';
var legendxtxt=document.createElement("v:rect");
legendxtxt.style.position='relative';
legendxtxt.style.top=(topx+50)+'px';
legendxtxt.style.left='16300px';
legendxtxt.style.width='3400px';
legendxtxt.style.height='700px';
legendxtxt.filled='f';
legendxtxt.stroked='f';
    fillStyle="<v:TextBox inset='0pt,0pt,0pt,0pt' style='font-size:9pt;'><div align='left'>"+this.Cakes[i][0]+":"+xRound(this.Cakes[i][1],2)+"</div></v:TextBox>";
legendxtxt.innerHTML=fillStyle;

canvas.appendChild(legendxbg);
canvas.appendChild(legendx);
canvas.appendChild(legendxtxt);
}
}

Pie3D.prototype.drawCake=function(){

	var k1 =180;
	var zIndex = 10;
	var rotates;
	var adjs;
	var _left=0;
	var _top=0;
	Height=parseInt(this.height);
	Width=parseInt(this.width);


for(var i=0;i<this.Cakes.length;i++){

var  k2= 360 * this.Percent[i] /2;
	rotates = k1 + k2;
	if (rotates>= 360)
		rotates = rotates - 360;
	adjs = ( -11796480 * this.Percent[i] + 5898240 );

	var k5 = Math.PI * 2 * ( 180 - ( rotates - 180 ) ) / 360;
	var R = Height / 2;
	var txt_x = _left + Height / 8 - 30 + R + R * Math.sin(k5) * 0.7;
	var txt_y = _top + Height / 14 - 39 + R + R * Math.cos(k5) * 0.7 * 0.5;
var cakepie=document.createElement("div");
cakepie.style.cursor='hand';
cakepie.innerHTML="<v:shape id='cake"+(i+1)+"' type='#Cake_3D' title='"+this.Cakes[i][0]+":"+xRound(this.Cakes[i][1],2)+"比例:"+xRound(this.Percent[i]*100,2)+"%'"+
                            " style='position:absolute;left:"+(_left + Height / 8)+"px;top:"+(_top + Height / 14)+"px;WIDTH:"+Height+"px;HEIGHT:"+Height+"px;rotation:"+rotates+";z-index:"+zIndex+"' adj='"+adjs+",0'"+
                            " fillcolor='"+this.Cakes[i][2]+"' onmouseover='moveup(cake"+(i+1)+","+(_top + Height / 14)+",txt"+(i+1)+",rec"+(i+1)+")'; onmouseout='movedown(cake"+(i+1)+","+(_top + Height / 14)+",txt"+(i+1)+",rec"+(i+1)+");'>"+
                            "<v:fill opacity='60293f' color2='fill lighten(120)' o:opacity2='60293f' rotate='t' angle='-135' method='linear sigma' focus='100%' type='gradient'/>"+
                            "<o:extrusion v:ext='view' on='t' backdepth='16' rotationangle='60' viewpoint='0,0'viewpointorigin='0,0' skewamt='0' lightposition='-50000,-50000' lightposition2='50000'/>"+
                            "</v:shape>";
cakepie.innerHTML+="<v:shape id='txt"+(i+1)+"' type='#3dtxt' style='position:absolute;left:"+txt_x+"px;top:"+txt_y+"px;z-index:20;display:none;width:50; height:12;'"+
                            " fillcolor='red' onmouseover='ontxt(cake"+(i+1)+","+(_top + Height / 14)+",txt"+(i+1)+",rec"+(i+1)+")'>"+
                            "<v:fill opacity='60293f' color2='fill lighten(120)' o:opacity2='60293f' rotate='t' angle='-135' method='linear sigma' focus='100%' type='gradient'/>"+
                            "<v:textpath style='v-text-kern:t' trim='t' fitpath='t' string='"+this.Cakes[i][0]+":"+xRound(this.Percent[i]*100,2)+"%'/> "+
                            //"<o:extrusion v:ext='view' backdepth='1pt' on='t' lightposition='0,0' lightposition2='0,0'/>"+
                            "</v:shape>";
                            //alert(cakepie.innerHTML)
this.Container.appendChild(cakepie);

    k1= k1 + k2 * 2;

    if (k1 >= 360)
	    k1 = k1 - 360;

    if (k1 > 180)
	    zIndex = zIndex + 1;
    else
	    zIndex = zIndex - 1;
}
}

/*  
*    xRound(num,n):数值格式化函数，Dight要  
*    格式化的  数字，How要保留的小数位数。  
*/  
function  xRound(num,n)  
{  
           num  =  Math.round  (num*Math.pow(10,n))/Math.pow(10,n);  
           return  num;  
}