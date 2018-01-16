function showCitys(){
//	alert('123');
	var provinceid =  document.getElementById("provinces").value;
	var options=[{"":""}];
	var url = "getCityss?province.id="+provinceid+"&m="+Math.random();
//	alert(url);
	 //step1 获得XmlHttpRequest对象
	var xhr = getXmlHttpRequest();
	//step2 发送请求
	xhr.open('get',url,true);
	xhr.onreadystatechange=function(){
		//step4 获取服务器返回的数据，更新页面
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				var txt = xhr.responseText;
				var jsonObj = eval("("+txt+")");
//				alert(jsonObj.citysString);
				options= eval(jsonObj.citysString);
				var obj = document.getElementById("citys");
				obj.options.length=0;
//				alert(options.length);
				for(var i=0;i<options.length;i++){

					obj.options.add(new Option(options[i].name,options[i].id)); //这个兼容IE与firefox
					}
				obj.style.display="";
//				alert(txt);
			}else{
//				$('username_msg').innerHTML = '正在验证...';
			}
		}else{
//			$('username_msg').innerHTML = '正在验证...';
		}
	};
	xhr.send(null);
//	alert(options.length);
//	alert('333');
//	alert(provinceid);
}
function getXmlHttpRequest(){
	var xhr = null;
	if((typeof XMLHttpRequest)!='undefined'){
		xhr = new XMLHttpRequest();
	}else {
		xhr = new ActiveXObject('Microsoft.XMLHttp');
	}
	return xhr;
}