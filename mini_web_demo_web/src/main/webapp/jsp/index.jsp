<%--
  Created by IntelliJ IDEA.
  User: wyliuchundong
  Date: 2015/2/1
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
<h1>计算首页</h1>
<form method="POST" action="/minidemo/calcVehicle">
  <span>计算Vehicle TravelTime和Delay</span>
  <button style="color: darkturquoise;" type="submit">计算</button>
</form>
<form method="POST" action="/minidemo/calcSegment">
  <span>计算Segment TravelTime和Delay</span>
  <button style="color: green" type="submit">计算</button>
</form>
</body>
</html>
