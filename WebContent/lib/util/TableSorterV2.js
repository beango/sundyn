

/*使用说明  :
方法1:
    new TableSorter("tb1");
效果:
    id为tb1的table的第一行任意单元格都可以点击进行排序.

方法2:
    new TableSorter("tb1", 0, 1, 3);
效果:
    id为tb1的table的第一行0,1,3单元格可以进行点击排序.
*/

function TableSorter(table, cb, querySort)
{
    this.Table = this.$(table);
    if(this.Table.rows.length <= 1)
    {
        return;
    }

    var args = [];
    if(arguments.length > 1)
    {
        for(var x = 1; x < arguments.length; x++)
        {
            //args.push(arguments[x]);
        }
    }
    this.Init(args, cb, querySort);
}

TableSorter.prototype = {
    $ : function(element)//简写document.getElementById
    {
        return document.getElementById(element);
    },
    concat : function(){
        // concat arr1 and arr2 without duplication.
        var concat_ = function(arr1, arr2) {
            for (var i=arr2.length-1;i>=0;i--) {
                arr1.indexOf(arr2[i]) === -1 ? arr1.push(arr2[i]) : 0;
            }
        };
        return function(arr) {
            var result = arr.slice();
            for (var i=arguments.length-1;i>=1;i--) {
                concat_(result, arguments[i]);
            }
            return result;
        };
    },
    Init : function(args, cb, querySort)//初始化表格的信息和操作
    {
        this.Rows = [];
        this.Header = [];
        this.ViewState = [];
        this.LastSorted = null;
        this.NormalCss = "NormalCss";
        this.SortAscCss = "SortAscCss";
        this.SortDescCss = "SortDescCss";

        this.Header = $(this.Table).find("[sort]");
        for(var x = 0; x < (args.length ? args.length : this.Header.length); x++)
        {
            var rowIndex = args.length ? args[x] : x;
            if(rowIndex >= this.Header.length)
            {
                continue;
            }
			var sortattr = this.Header[rowIndex].getAttribute("sort");
			if(sortattr == undefined)
				continue;
            this.ViewState[rowIndex] = false;
            this.Header[rowIndex].style.cursor = "pointer";
            $(this.Header[rowIndex]).find("img").attr("src","../images/nor.png");
            this.Header[rowIndex].onclick = function(){
                var sortField = $(this).attr("sort");
                var sArr = getQueryString("sort");
                var descN = "";
                if (sArr==undefined || sArr == null || sortField!=sArr.split(",")[0])//默认按反序,或者更改排序字段
                    descN = "desc";
                else if (sArr.split(",").length!=2)
                    descN = "desc";
                else if (sArr.split(",")[1] == "desc")
                    descN = "asc";
                else if (sArr.split(",")[1] == "asc")
                    descN = "desc";
                cb(sortField+","+descN);
            };//this.GetFunction(this, "Sort", rowIndex);
            if(querySort!=undefined && querySort!='' && querySort!='null'){
                if (sortattr != null && querySort !=null && sortattr===querySort.split(",")[0]){
                    var desc = querySort.split(",")[1];
                    if(desc==="desc")
                        $(this.Header[rowIndex]).find("img").attr("src","../images/down.png");
                    else if(desc==="asc")
                        $(this.Header[rowIndex]).find("img").attr("src","../images/up.png");
                }
            }
        }
    },
    GetFunction : function(variable,method,param)//取得指定对象的指定方法.
    {
        return function()
        {
            variable[method](param);
        }
    },
    Sort : function(column)//执行排序.
    {
        if(this.LastSorted)
        {
            this.LastSorted.className = this.NormalCss;
        }
        var SortAsNumber = true;
        for(var x = 0; x < this.Rows.length && SortAsNumber; x++)
        {
            SortAsNumber = this.IsNumeric(this.Rows[x].cells[column].innerHTML);
        }
        this.Rows.sort(
        function(row1, row2)
        {
            var result;
            var value1,value2;
            value1 = row1.cells[column].innerHTML;
            value2 = row2.cells[column].innerHTML;
            if(value1 == value2)
            {
                return 0;
            }
            if(SortAsNumber)
            {
                result = parseFloat(value1) > parseFloat(value2);
            }
            else
            {
                result = value1 > value2;
            }
            result = result ? 1 : -1;
            return result;
        })
        if(this.ViewState[column])
        {
            this.Rows.reverse();
            this.ViewState[column] = false;
            this.Header[column].className = this.SortDescCss;
        }
        else
        {
            this.ViewState[column] = true;
            this.Header[column].className = this.SortAscCss;
        }
        this.LastSorted = this.Header[column];
        var frag = document.createDocumentFragment();
        for(var x = 0; x < this.Rows.length; x++)
        {
            frag.appendChild(this.Rows[x]);
        }
        this.Table.tBodies[0].appendChild(frag);
        this.OnSorted(this.Header[column], this.ViewState[column]);
    },
    IsNumeric : function(num)//验证是否是数字类型.
    {
        return /^\d+(\.\d+)?$/.test(num);
    },
    OnSorted : function(cell, IsAsc)//排序完后执行的方法.cell:执行排序列的表头单元格,IsAsc:是否为升序排序.
    {
        return;
    }
}