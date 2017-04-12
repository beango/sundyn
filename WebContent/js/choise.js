			  function chooseMenus(){
			    var menus=document.getElementById("CmenuNames");
			    var retVal=new Array();
		
			    for(var i=0;i<menus.options.length;i++){
			       retVal[i]=menus.options[i].value;
			   	}
			   return retVal;
			   }
			  function checkForm(ac){
			  
			   var menus=chooseMenus();
			  $.ajax({url: ac+"&menus="+menus,
					type: 'GET',
					dataType:'json',
					timeout: 20000,
					success: function(html){
				      
					    dialogAjaxDone(html);
					}
				});
							   
			 }
			   
			    function moveMenus(){
			      var e1=document.getElementById("menuNames");
			      var e2=document.getElementById("CmenuNames");
				  
			      for( var i=0;i<e1.options.length;i++){
					
					if(e1.options[i].selected){    
						var e=e1.options[i];
						e2.options.add(new Option(e.text,e.value));
						e1.remove(i);
						i--;
					}
			     }
			  }
			  function removeMenus(){
			    var e1=document.getElementById("menuNames");
			    var e2=document.getElementById("CmenuNames");
			       for( var i=0;i<e2.options.length;i++){
					    if(e2.options[i].selected){
					    var e=e2.options[i];
						e1.options.add(new Option(e.text,e.value));
					    e2.remove(i);
					    i--;
					  }
			     	}
			  }
