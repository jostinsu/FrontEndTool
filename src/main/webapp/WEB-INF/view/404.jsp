<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/6 0006
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>前端可视化布局工具</title>
    <meta http-equiv="refresh" content="10;url=<c:url value="/" />">
    <style>
        body, div {
            margin: 0;
            padding: 0;
        }

        body {
            background: url("<c:url value="/" />images/error_bg.jpg") repeat-x scroll 0 0 #67ACE4;
        }

        #container {
            margin: 0 auto;
            padding-top: 50px;
            text-align: center;
            width: 560px;
        }

        #container img {
            border: medium none;
            margin-bottom: 50px;
        }

        #container .error {
            height: 200px;
            position: relative;
        }

        #container .error img {
            bottom: -50px;
            position: absolute;
            right: -50px;
        }

        #container .msg {
            margin-bottom: 25px;
        }

        #cloud {
            background: url("<c:url value="/" />images/error_cloud.png") repeat-x scroll 0 0 transparent;
            bottom: 0;
            height: 170px;
            position: absolute;
            width: 100%;
        }
    </style>
</head>
<body>
<div id="container"><img class="png" src="<c:url value="/" />images/404.png"/> <img class="png msg"
                                                                                    src="<c:url value="/" />images/404_msg.png"/>
    <p><a href="<c:url value="/" />"><img class="png" src="<c:url value="/" />images/404_to_index.png"/></a></p>
</div>
<div id="cloud" class="png"></div>
</body>
</html>
<head>
