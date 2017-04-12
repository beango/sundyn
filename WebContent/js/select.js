/************************************
*	jsģ��Select					*

*	Author:Daviv					*

*	Date:2007-4-28					*

*	Blog:http://www.iwcn.net		*

*	Email:jxdawei#163.com			*
************************************/

var childCreate=false;

function Offset(e)
//ȡ��ǩ�ľ���λ��
{
	var t = e.offsetTop;
	var l = e.offsetLeft;
	var w = e.offsetWidth;
	var h = e.offsetHeight-2;

	while(e=e.offsetParent)
	{
		t+=e.offsetTop;
		l+=e.offsetLeft;
	}
	return {
		top : t,
		left : l,
		width : w,
		height : h
	}
}

function loadSelect(obj){

	//��һ����ȡ��Select���ڵ�λ��
	var offset=Offset(obj);
	//�ڶ����������select����
	obj.style.display="none";

	//������������һ��div��������select
	var iDiv = document.createElement("div");
		iDiv.id="selectof" + obj.name;
		iDiv.style.position = "absolute";
		iDiv.style.width=offset.width + "px";
		iDiv.style.height=offset.height + "px";
		iDiv.style.top=offset.top + "px";
		iDiv.style.left=offset.left + "px";
		iDiv.style.background="url(images/icon_select.gif) no-repeat right 4px";
		iDiv.style.border="1px solid #3366ff";
		iDiv.style.fontSize="12px";
		iDiv.style.lineHeight=offset.height + "px";
		iDiv.style.textIndent="4px";
	document.body.appendChild(iDiv);

	//���Ĳ�����select��Ĭ�ϵ�ѡ����ʾ����
	var tValue=obj.options[obj.selectedIndex].innerHTML;
	iDiv.innerHTML=tValue;

	//���岽��ģ�������
	iDiv.onmouseover=function(){//����Ƶ�
		iDiv.style.background="url(images/icon_select_focus.gif) no-repeat right 4px";
	}
	iDiv.onmouseout=function(){//�������
		iDiv.style.background="url(images/icon_select.gif) no-repeat right 4px";
	}
	iDiv.onclick=function(){//�����
		if (document.getElementById("selectchild" + obj.name)){
		//�ж��Ƿ񴴽���div
			if (childCreate){
				//�жϵ�ǰ�������ǲ��Ǵ�״̬������Ǵ򿪵ľ͹رյ����ǹرյľʹ򿪡�
				document.getElementById("selectchild" + obj.name).style.display="none";
				childCreate=false;
			}else{
				document.getElementById("selectchild" + obj.name).style.display="";
				childCreate=true;
			}
		}else{
			//��ʼһ��div������һ��div�±ߣ���options������
			var cDiv = document.createElement("div");
			cDiv.id="selectchild" + obj.name;
			cDiv.style.position = "absolute";
			cDiv.style.width=offset.width + "px";
			cDiv.style.height=obj.options.length *20 + "px";
			cDiv.style.top=(offset.top+offset.height+2) + "px";
			cDiv.style.left=offset.left + "px";
			cDiv.style.background="#f7f7f7";
			cDiv.style.border="1px solid silver";

			var uUl = document.createElement("ul");
			uUl.id="uUlchild" + obj.name;
			uUl.style.listStyle="none";
			uUl.style.margin="0";
			uUl.style.padding="0";
			uUl.style.fontSize="12px";
			cDiv.appendChild(uUl);
			document.body.appendChild(cDiv);		
			childCreate=true;
			for (var i=0;i<obj.options.length;i++){
				//��ԭʼ��select��ǩ�е�options��ӵ�li��
				var lLi=document.createElement("li");
				lLi.id=obj.options[i].value;
				lLi.style.textIndent="4px";
				lLi.style.height="20px";
				lLi.style.lineHeight="20px";
				lLi.innerHTML=obj.options[i].innerHTML;
				uUl.appendChild(lLi);
			}
			var liObj=document.getElementById("uUlchild" + obj.name).getElementsByTagName("li");
			for (var j=0;j<obj.options.length;j++){
				//Ϊli��ǩ�������¼�
				liObj[j].onmouseover=function(){
					this.style.background="gray";
					this.style.color="white";
				}
				liObj[j].onmouseout=function(){
					this.style.background="white";
					this.style.color="black";
				}
				liObj[j].onclick=function(){
					//���������飬һ�ǽ��û�ѡ��ı��浽ԭʼselect��ǩ�У�Ҫ�������ٺÿ����ݽ���Ҳ��ȡ����select��ֵ�ˡ�
					obj.options.length=0;
					obj.options[0]=new Option(this.innerHTML,this.id);
					//ͬʱ���ǰ������Ĺرյ���
					document.getElementById("selectchild" + obj.name).style.display="none";
					childCreate=false;
					iDiv.innerHTML=this.innerHTML;
				}
			}
		}
	}
}
