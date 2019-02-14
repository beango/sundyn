function showCitys(cb){
	var provinceid =  document.getElementById("provinces").value;
	var options=[{"":""}];
	var url = "getCityss?province.id="+provinceid+"&m="+Math.random();
	var xhr = getXmlHttpRequest();
	xhr.open('get',url,true);
	xhr.onreadystatechange=function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				var txt = xhr.responseText;
				var jsonObj = eval("("+txt+")");
				options= eval(jsonObj.citysString);
				var obj = document.getElementById("citys");
				obj.options.length=0;
				for(var i=0;i<options.length;i++){
					obj.options.add(new Option(options[i].name,options[i].id));
				}
				obj.style.display="";
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.iframeAuto(index);
				if(cb) cb();
			}else{
			}
		}else{
		}
	};
	xhr.send(null);
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