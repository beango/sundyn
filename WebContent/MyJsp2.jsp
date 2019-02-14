<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>固定div层拖动</title>
    <script src="js/interact.js"></script>

    <script src="js/jquery-2.1.3.min.js"></script>
    <style>
        * {
            padding: 0px;
            margin: 0px;
        }

        .draggable {
            width: 100px;
            height: 100px;
            line-height: 50px;
            min-height: 10px;
            min-width: 10px;
            margin: 0px;
            position: absolute;
            padding: 1px;
            left: 0px;
            right: 0px;
            background-color: #234b5e;
            color: white;
            border-radius: 0px;
            padding: 0px;
            float: left;
            margin: 0px;
            overflow: hidden;
            opacity: 0.8;
        }

        h4 {
            text-align: center;
            vertical-align: middle;
        }

        p.wh {
            font-size: 8px;
            position: absolute;
            bottom: 0px;
            right: 0px;
            line-height: 8px;
            padding: 2px;
        }

        p.lt {
            font-size: 8px;
            position: absolute;
            top: 0px;
            left: 0px;
            line-height: 8px;
            padding: 2px;
        }

        p.close {
            font-size: 8px;
            position: absolute;
            top: 0px;
            right: 0px;
            line-height: 8px;
            padding: 2px;
            cursor: pointer;
            display: none;
        }

        p.c {
            font-size: 12px;
            position: absolute;
            bottom: 50%;
            right: 50%;
            line-height: 12px;
            padding: 2px;
        }

        .content {
            border: 1px solid #ccc;
            width: 1000px;
            height: 600px;
            position: relative;
            margin: 0px auto;
        }

        h1 {
            text-align: center;
        }

        .left {
            background: #84acb3;
            float: left;
            width: 160px;
            height: 100%;
        }

        .right {
            background: #fff;
            float: right;
            width: 840px;
            height: 100%;
            position: relative;
        }

        .drag_l {
            background-color: #234b5e;
            border-radius: 11px;
            color: #f7ebca;
            cursor: pointer;
            margin-bottom: 10px;
            margin-left: 6px;
            padding: 8px;
            width: 128px;
            text-align: center;
        }

        .drag_l:hover {
            background-color: #577a8b;
        }

        pre {
            color: green;
            padding: 10px 15px;
            background: #f0f0f0;
            border: 1px dotted #333;
            font: 12px/1.5 Courier New;
            margin: 12px;
        }

        span {
            color: #999;
        }
    </style>
</head>
<body>
<div id="content" class="content">
    <div class="left">
        <div style="padding-top: 8px;"></div>
    </div>
    <div class="right">
    </div>
    <button onclick="getlayout()">获取布局</button>
</div>

<script>
    (function () {
        var data = [
            '001',
            '002',
            '003',
            '004',
            '005',
            '006',
            '007',
            '008'
        ];
        var dh = 100;
        data.map(function (t, i) {
            $('.left').append('<div class="drag_l" id="' + i + '" >' + t + '</div>')
        });

        $(function () {
            $('.drag_l').click(function () {
                $('.right').append('<div class="draggable" id="' + $(this).attr('id') + '"  style="background:' + getRandomColor() + ';transform: translate(100px, 100px);" data-x="100" data-y="100">\n' +
                    '            <h4>' + $(this).text() + '</h4>\n' +
                    '            <p class="lt">左0上0px</p>\n' +
                    '            <p class="wh">153,,,,×163px</p>\n' +
                    '            <p class="close">关闭</p>\n' +
                    '        </div>')
                $(this).css("display", "none");
            });
            data.map(function (t, i) {
                var x = i*100;
                var y = 0;
                console.log(x, y);
                $('.right').append('<div class="draggable" id="' + i + '"  style="background:' + getRandomColor() + ';transform: translate('+x+'px, '+y+'px);" data-x="'+x+'" data-y="'+y+'">\n' +
                    '            <h4>' + t + '</h4>\n' +
                    '            <p class="lt">左'+x+'上'+y+'px</p>\n' +
                    '            <p class="wh">100\u00D7100px</p>\n' +
                    '            <p class="close">关闭</p>\n' +
                    '        </div>')
            });
            mydrag();
        });
        var every_x = [null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null];
        var every_y = [null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null];

        var status1=true,status2=true,status3=true,status4=true;

        function removeByValue(arr, val) {
            for (var i = 0; i < arr.length; i++) {
                if (arr[i] == val) {
                    arr.splice(i, 1);
                    break;
                }
            }
        }

        Array.prototype.insert = function (index, item) {
            this.splice(index, 1, item);
        };
        var getRandomColor = function () {
            return '#' +
                (function (color) {
                    return (color += '0123456789abcdef'[Math.floor(Math.random() * 16)])
                    && (color.length == 6) ? color : arguments.callee(color);
                })('');
        }

        var mydrag = function () {
            interact('.draggable')
                .draggable({
                    inertia: false,
                    restrict: {
                        restriction: "parent",
                        endOnly: true,
                        elementRect: {top: 0, left: 0, bottom: 1, right: 1}
                    },
                    maxPerElement: 100,
                    onmove: dragMoveListener,
                    onend: function (event) {
                        var textEl = event.target.querySelector('p.lt');
                        textEl && (textEl.textContent =
                            position(event));
                        var closeEl = event.target.querySelector('p.close');
                        closeEl.style.display = 'block';
                        drag_s(event);
                        myclose(event);
                    }
                })
                .resizable({
                    // resize from all edges and corners
                    edges: {left: true, right: true, bottom: true, top: true},
                    // keep the edges inside the parent
                    restrictEdges: {
                        outer: 'parent',
                        endOnly: true
                    },
                    // minimum size
                    restrictSize: {
                        min: {width: 0, height: 0}
                    },
                    inertia: false
                })
                .on('resizemove', function (event) {
                    var s = $(event.target);
                    var target = event.target,
                        x = (parseFloat(target.getAttribute('data-x')) || 0),
                        y = (parseFloat(target.getAttribute('data-y')) || 0),
                        xr=(parseFloat(target.getAttribute('data-x')) || 0)+s.width(),
                        yb=(parseFloat(target.getAttribute('data-y')) || 0)+s.height();
                    x += event.deltaRect.left;
                    y += event.deltaRect.top;
                    xr += event.deltaRect.right;
                    yb += event.deltaRect.bottom;

                    var s = $(event.target);
                    var sid = s.attr('id');
                    var ns = parseInt(sid);

                    var i = ns * 2
                    every_y[i] = null;
                    every_x[i] = null;
                    every_y[i + 1] = null;
                    every_x[i + 1] = null;

                    for (ax in every_x) {
                        if ((parseInt(every_x[ax]) - 3) <= x && x <= (parseInt(every_x[ax]) + 3)) {

                            x = parseInt(every_x[ax]);
                        }else {
                        }
                    }
                    for (ay in every_y) {
                        if ((parseInt(every_y[ay]) - 3) <= y && y <= (parseInt(every_y[ay]) + 3)) {
                            y = parseInt(every_y[ay]);
                        }else {
                        }
                    }

                    for (a_x in every_x) {
                        if ((parseInt(every_x[a_x]) - 3) <= xr && xr <= (parseInt(every_x[a_x]) + 3)) {
                            xr = parseInt(every_x[a_x]);
                        }else {
                        }
                    }
                    for (a_y in every_y) {
                        if ((parseInt(every_y[a_y]) - 3) <= yb&& yb <= (parseInt(every_y[a_y]) + 3)) {
                            yb = parseInt(every_y[a_y]);
                        }else {
                        }
                    }

                    target.style.width = (xr-x) + 'px';
                    target.style.height =(yb-y) + 'px';

                    target.style.webkitTransform = target.style.transform =
                        'translate(' + x + 'px,' + y + 'px)';

                    target.setAttribute('data-x', x);
                    target.setAttribute('data-y', y);
                    target.querySelector('p.wh').textContent =(xr-x) + '\u00D7' +(yb-y) + 'px';
                    var textEl = event.target.querySelector('p.lt');

                    textEl && (textEl.textContent = position(event));
                })
                .on('resizeend', function (event) {
                    drag_s(event)
                });

            function dragMoveListener(event) {
                var target = event.target,
                    // keep the dragged position in the data-x/data-y attributes
                    x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx,
                    y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;

                var s = $(event.target)
                var sid = s.attr('id');
                var ns = parseInt(sid);

                var i = ns * 2
                every_y[i] = null;
                every_x[i] = null;
                every_y[i + 1] = null;
                every_x[i + 1] = null;

                for (ax in every_x) {
                    if ((parseInt(every_x[ax]) - 3) <= x && x <= (parseInt(every_x[ax]) + 3)) {
                        x = parseInt(every_x[ax]);
                    }
                }
                for (ay in every_y) {
                    if ((parseInt(every_y[ay]) - 3) <= y && y <= (parseInt(every_y[ay]) + 3)) {
                        y = parseInt(every_y[ay]);
                    }
                }

                for (a_x in every_x) {
                    if ((parseInt(every_x[a_x]) - 3) <= (x+s.width()) && (x+s.width()) <= (parseInt(every_x[a_x]) + 3)) {
                        x = parseInt(every_x[a_x])-s.width();
                    }
                }
                for (a_y in every_y) {
                    if ((parseInt(every_y[a_y]) - 3) <= (y+s.height()) && (y+s.height()) <= (parseInt(every_y[a_y]) + 3)) {
                        y = parseInt(every_y[a_y])-s.height();
                    }
                }
                // translate the element
                target.style.webkitTransform =
                    target.style.transform =
                        'translate(' + x + 'px, ' + y + 'px)';
                // update the posiion attributes
                target.setAttribute('data-x', x);
                target.setAttribute('data-y', y);
            }

            function position(e) {
                return '左' + e.target.getAttribute('data-x') + "上" + e.target.getAttribute('data-y') + 'px'
            }

            function myclose(event) {
                $(event.target).find(".close").click(function () {
                    $('.left').find('#' + $(this).parent().attr('id')).css('display', 'block');
                    $(this).parent().remove();
                    drag_c(event)
                    // removeByValue(every_x, parseFloat(event.target.getAttribute('data-x')));
                })
            }

            function drag_s(event) {
                var s = $(event.target)
                var sid = s.attr('id');
                var ns = parseInt(sid);
                var i = ns * 2
                // top  right  bottom left   //data-y  data-x+width  data-y+height  data-x
                every_y.insert(i, parseFloat(s.attr('data-y')));
                every_x.insert(i, parseFloat(s.attr('data-x')) + parseFloat(s.width()));
                every_y.insert(i + 1, parseFloat(s.attr('data-y')) + parseFloat(s.height()));
                every_x.insert(i + 1, parseFloat(s.attr('data-x')));
                //console.log(every_x, every_y)
            }

            function drag_c(event) {
                var s = $(event.target)
                var sid = s.attr('id');
                var ns = parseInt(sid);
                var i = ns * 2
                every_y.insert(i, 0);
                every_x.insert(i, 0);
                every_y.insert(i + 1, 0);
                every_x.insert(i + 1, 0);
            };
            window.dragMoveListener = dragMoveListener;
        };
    })()

    function getlayout(){
        var items = $(".right .draggable");
        for (var i=0; i<items.length; i++){
            var x = $(items[i]).attr("data-x"),
                y = $(items[i]).attr("data-y"),
                w = $(items[i]).width(),
                h = $(items[i]).height();
            console.log('x:', x, " y:", y, " width:", w, " height:", h);
            console.log()
        }
    }
</script>
</body>
</html>