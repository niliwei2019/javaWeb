<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
        body, html {width: 100%;height: 100%; margin:0;font-family:"微软雅黑";}
        #l-map{height:500px;width:100%;}
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=S1ry4FdcYXbODncXKv2gEGkieH9C4E5w"></script>
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
    <title>根据起终点名称查询公交换乘</title>
</head>
<body>
<div id="driving_way">
    起点<input type="text" id="my_start"><br>
    终点<input type="text" id="my_end"><br>
<div id="r-result"></div>
    <select>
        <option value="0">最少时间</option>
        <option value="1">最少换乘</option>
        <option value="2">最少步行</option>
        <option value="3">不乘地铁</option>
    </select>
    <input type="button" id="result" value="查询"/>
</div>
<div id="l-map"></div>
</body>
</html>
<script type="text/javascript">
    // 百度地图API功能
    var map = new BMap.Map("l-map");
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 14);

    // var start = "百度大厦";
    // var end = "北京邮电大学西门";

    var routePolicy = [BMAP_TRANSIT_POLICY_LEAST_TIME,BMAP_TRANSIT_POLICY_LEAST_TRANSFER,BMAP_TRANSIT_POLICY_LEAST_WALKING,BMAP_TRANSIT_POLICY_AVOID_SUBWAYS];
    var transit = new BMap.TransitRoute(map, {
        renderOptions: {map: map,panel: "r-result"},
        policy: 0
    });
    $("#result").click(function(){
        var start=document.getElementById("my_start").value
        var end=document.getElementById("my_end").value
        // alert(start)
        // alert(end)
        map.clearOverlays();
        var i=$("#driving_way select").val();
        search(start,end,routePolicy[i]);
        function search(start,end,route){
            transit.setPolicy(route);
            transit.search(start,end);
        }
    });
</script>