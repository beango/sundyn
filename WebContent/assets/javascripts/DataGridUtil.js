Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
var DataGridUtil = function() {
    var that= this;
    that.id= "dataTable";
    that.pageid= "pp";
    that.page= 1;
    that.pageSize= 10;
    that.dataUrl= "";
    that.data= null;
    that.checkallid = "checkall";
    that.formid = "";

    //是否默认选中,默认值为false
    that.IsSelected = false;

    that.dg = null;
    that.checkalltxt = "全选(本页)";
    that.uncheckalltxt = "取消全选(本页)";
    that.checkallpage = [];
    that.queryData= function (clear) {
        if (that.dg && clear){
            that.dg.datagrid("clearSelections");
            that.checkallpage = [];
        }
        var searchform = "form";
        if(that.formid!="")
            searchform = "form#" + that.formid;
        if(this.dataUrl!=""){
            $.getJSON(this.dataUrl + "?pageSize=" + this.pageSize + "&page=" + this.page, $(searchform).serialize(), function(result){
                that.bindDataGrid(result);
            });
        }
        else{
            var obj = {result:this.data.pageList};
            that.bindDataGrid(obj);
        }
    };
    that.bindDataGrid= function (data) {
        that.dg = $('#' + that.id).datagrid({
            notitle: true,
            iconCls: 'icon-edit',//图标
            width: "100%",
            height: "auto",
            nowrap: false,
            striped: true,
            border: true,
            collapsible: false,//是否可折叠的
            fit: false,//自动大小
            data: data.result,
            remoteSort: false,
            idField: 'id',

            singleSelect: false,//是否单选
            pagination: false,//分页控件
            rownumbers: true,//行号
            fitColumns: true,
            onLoadSuccess: function (xhr) {

            	if (xhr) {
            		if(that.IsSelected){
            			for(var i=0;i<xhr.rows.length;i++){
                			if (xhr.rows[i].checked) {

        						$('#'+that.id).datagrid('checkRow', i);
        					}else{
        						$('#'+that.id).datagrid('uncheckRow', i);
        					}
                		}
            		}
            		var trs = $(this).prev().find('div.datagrid-body').find('tr');
            		for(var i=0;i<xhr.rows.length;i++){
                		var row = xhr.rows[i];
                		if(row.datediffer<=0 & row.status!=-1 & row.status!=2)
                		{
                			var cells = trs[i].cells;
                			for(var j=0;j<cells.length;j++)
                			{
                				if($(cells[j]).attr("field")=="planendtime")
                				{
                					cells[j].style.cssText='background:red;color:#fff;';
                				}
                			}
                		}
                	}
    			}

                var heightMargin = $("#toolbar").height() + 30;
                var widthMargin = $(document.body).width() - $('#' + that.id).width();
                // 第一次加载时和当窗口大小发生变化时，自动变化大小
                $('#' + that.id).resizeDataGrid(heightMargin, widthMargin, 0, 0);
                $(window).resize(function () {
                    $('#' + that.id).resizeDataGrid(heightMargin, widthMargin, 0, 0);
                });
                //设置分页控件
                var p = $('#' + that.pageid);
                console.log("设置分页控件");
                $(p).pagination({
                    total: data.totalCount,
                    pageSize: that.pageSize,//每页显示的记录条数，默认为10
                    pageList: [5, 10, 15],//可以设置每页记录条数的列表
                    beforePageText: '第',//页数文本框前显示的汉字
                    afterPageText: '页    共 {pages} 页',
                    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
                    onSelectPage: function (pageNumber, pageSize) {
                        that.page = pageNumber;
                        that.pageSize = pageSize;
                        that.queryData();//翻页不清空
                    },
                    onChangePageSize: function (pageSize) {
                        that.checkallpage = [];
                        that.page = 1;
                        that.pageSize = pageSize;
                        that.queryData(true); //清空被选中的项
                    }
                });

                if(that.checkallpage.indexOf(that.page)>-1){//如果本页有全选中
                    $("#" + that.checkallid).html(that.uncheckalltxt);
                }
                else
                    $("#" + that.checkallid).html(that.checkalltxt);

                var loadingMask = document.getElementById('maskLoading');
            	//loadingMask.parentNode.removeChild(loadingMask);

            }

        });
    },
        that.checkall = function (obj) {
            var objtxt = $(obj).html();
            if(objtxt === that.checkalltxt){
                $('#dataTable').datagrid('checkAll');
                $(obj).html(that.uncheckalltxt);
                if(that.checkallpage.indexOf(that.page)===-1){
                    that.checkallpage.push(that.page);
                }
            }
            else{
                $('#dataTable').datagrid('uncheckAll');
                $(obj).html(that.checkalltxt)
                that.checkallpage.remove(that.page);
            }
        },
        that.succ=function(msg){
            var mobj = $.messager.show({
                msg:'<div class="alert alert-success">'+msg+"</div>",
                timeout:800,
                style:{
                    right:'',
                    bottom:''
                }
            });
            mobj.css("padding","0").css("height","28");
        },
        that.warn=function(msg){
            var mobj = $.messager.show({
                msg:'<div class="alert alert-warning">'+msg+"</div>",
                timeout:800,
                style:{
                    right:'',
                    bottom:''
                }
            });
            mobj.css("padding","0").css("height","28");
        }
        that.error=function(msg){
            var mobj = $.messager.show({
                msg:'<div class="alert alert-danger">'+msg+"</div>",
                timeout:800,
                style:{
                    right:'',
                    bottom:''
                }
            });
            mobj.css("padding","0").css("height","28");
        }
};