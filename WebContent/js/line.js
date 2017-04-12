
/*  ************更多技术文章请访问：http://www.blogjava.net/JAVA-HE****************
 *
 *    文件名：line.js V 1.01
    *    
   *    作  者：何昌敏
    *
    *    时  间：2007-7
    *
    *    描  述：绘制坐标曲线图
   *
   *    备  注：
   *                1.实现了根据所提供数据，自动标刻y轴坐标。
   *                2.实现了图像清除。
   *                3.调整startx starty能实现整体位置调整。
   *
   *    感  谢：Walter Zorn提供了API ——wz_jsgraphics.js v. 3.01。
   *                
  *************更多技术文章请访问：http://www.blogjava.net/JAVA-HE*************** */
function Line(obj) {
 	this.jg = new jsGraphics(obj);
	var colors = new Array();
	colors[0] = "#0066FF";
	colors[1] = "#FF6633";
	colors[2] = "#9900FF";
	colors[3] = "#FF0066";
	colors[4] = "#066600";
	colors[5] = "#006633";
	colors[6] = "#33FFFF";
	colors[7] = "#000000";
	colors[8] = "#FFFF00";
	colors[9] = "#000033";
	colors[10] = "#CCFFFF";
	colors[11] = "#666666";
	this.start_x = 40;         // 应大于等于y_str_width
	this.y_str_width = 40;     // 坐标系的左边距
	this.x_str_tom = 10;         // x轴文字 距离坐标系底部距离。
	this.start_y = 20;         // >=this.ArrowLength*2 箭头的高度
	this.width = 500;
	this.height = 180;
	this.y_line_num = 5;
	this.IsDrawArrow = true;
	this.ArrowLength = 6;
	this.drawXYLine = function (_y, _x) {
		var y_length = _y.length;
		var x_length = _x.length;
		if (y_length != x_length) {
			alert(" X and Y length of inconsistencies, errors parameters. ");
			return;
		}
		var y_line_distance = Math.round(this.height / this.y_line_num);
		var x_line_distance = Math.round(this.width / x_length);
		this.jg.drawLine(this.start_x, this.start_y + this.height, this.start_x + this.width, this.start_y + this.height); // x
		this.jg.drawLine(this.start_x, this.start_y + this.height, this.start_x, this.start_y); // y
		this.jg.setStroke(Stroke.DOTTED);
		var _y_copy = _y.concat();
		var temp = _y;
		temp.sort(function AscSort(x, y) {
			return x == y ? 0 : (x > y ? 1 : -1);
		});
		var y_max2y_min = temp[x_length - 1] - temp[0];
		var y_min = temp[0];
		var y_value_distance = y_max2y_min / this.y_line_num;
		for (var i = 0; i < this.y_line_num; i++) {
			var y_item = this.start_y + this.height - (i + 1) * y_line_distance;
			this.jg.drawLine(this.start_x, y_item, this.start_x + this.width, y_item);
			var y_v = Math.round(y_value_distance * (i + 1) + y_min);
			this.jg.drawString(y_v, this.start_x - this.y_str_width, y_item);
		}
		for (i = 0; i < x_length; i++) {
			this.jg.setStroke(-1);
			this.jg.setColor(" #000000 ");
			var x_item_end = this.start_x + x_line_distance * (i + 1);
			this.jg.drawLine(x_item_end, this.start_y + this.height, x_item_end, this.start_y);
			this.jg.drawString(_x[i], x_item_end, this.start_y + this.height + 10);
		}
		for (i = y_length; i > 1; i--) {
			this.jg.setStroke(2);
			this.jg.setColor(" #FF0000 ");
			var x_temp_1 = this.start_x + x_line_distance * (i);
			var x_temp_2 = this.start_x + x_line_distance * (i - 1);
               // alert(_y_copy[i-1]);
               // alert(y_min);
               // alert(y_max2y_min);
			var y_temp_1 = Math.round(this.height - this.height * (_y_copy[i - 1] - y_min) / y_max2y_min + this.start_y);
			var y_temp_2 = Math.round(this.height - this.height * (_y_copy[i - 2] - y_min) / y_max2y_min + this.start_y);
			this.jg.drawLine(x_temp_1, y_temp_1, x_temp_2, y_temp_2);
		}
		if (this.IsDrawArrow) {
			this.jg.setStroke(1);
			this.jg.setColor(" #000000 ");
			this.jg.drawLine(this.start_x - this.ArrowLength, this.start_y, this.start_x, this.start_y - 2 * this.ArrowLength);
			this.jg.drawLine(this.start_x + this.ArrowLength, this.start_y, this.start_x, this.start_y - 2 * this.ArrowLength);
			this.jg.drawLine(this.start_x, this.start_y, this.start_x, this.start_y - 2 * this.ArrowLength);
			this.jg.drawLine(this.start_x + this.width, this.start_y + this.height - this.ArrowLength, this.start_x + this.width + 2 * this.ArrowLength, this.start_y + this.height);
			this.jg.drawLine(this.start_x + this.width, this.start_y + this.height + this.ArrowLength, this.start_x + this.width + 2 * this.ArrowLength, this.start_y + this.height);
			this.jg.drawLine(this.start_x + this.width, this.start_y + this.height, this.start_x + this.width + 2 * this.ArrowLength, this.start_y + this.height);
		}
		this.jg.paint();
	};
	this.clearLine = function () {
		this.jg.clear();
	};
}

