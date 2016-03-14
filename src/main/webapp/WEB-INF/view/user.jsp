<%--
  Created by IntelliJ IDEA.
  User: sujianxin
  Date: 2016/3/4
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/font-awesome.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <title>前端可视化布局工具</title>
    <style>
        html, body {
            height: 100%;
            background: #E9EAEB;
        }

        body {
            position: relative;
            background: url("/images/loginBg.png") right bottom;
        }
    </style>
</head>
<body>
<div class="user_container">
    <form class="user_side login" action="login" method="post">
        <ul class="user_content">
            <li class="login_item">
                <h2 class="login_title">用户登录</h2>
            </li>
            <li class="login_item">
                <input class="tool_text tool_text_Big" type="text" name="mail" placeholder="请输入注册邮箱" required
                       pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"/>
                <i class="icon-user user_icon"></i>
            </li>
            <li class="login_item">
                <input class="tool_text tool_text_Big" type="password" name="password" placeholder="密码(6到16位有效字符)"
                       required pattern="[\w]{6,16}"/>
                <i class="icon-lock user_icon"></i></li>
            <li class="login_item">
                <input type="submit" class="tool_btn tool_btn_Blue tool_btn_Big" value="登录"/>
                <span class="user_remind remind">${loginMsg}</span>
            </li>
            <li class="login_item">
                <a id="forgivePasswordBtn" class="login_item_leftBtn" href="javascript:;">忘记密码?</a>
                <a id="registerBtn" class="login_item_rightBtn" href="javascript:;">立即注册</a>
            </li>
        </ul>
    </form>
    <form class="user_side register" action="register" method="post">
        <ul class="user_content">
            <li class="register_item">
                <h2 class="register_title">用户注册</h2>
                <i class="icon-circle-arrow-left"></i>
            </li>
            <li class="register_item">
                <input type="text" name="mail" class="tool_text tool_text_Big" placeholder="请输入有效邮箱" required
                       pattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"/>
                <i class="icon-user user_icon"></i>
            </li>
            <li class="register_item">
                <input type="password" id="password1" name="password1" class="tool_text tool_text_Big"
                       placeholder="密码(6到16位有效字符)" required pattern="[\w]{6,16}"/>
                <i class="icon-lock user_icon"></i>
            </li>
            <li class="register_item">
                <input type="password" id="password2" name="password2" class="tool_text tool_text_Big"
                       placeholder="请确认密码" required pattern="[\w]{6,16}"/>
                <i class="icon-lock user_icon"></i>
            </li>
            <li class="register_item">
                <input type="submit" class="tool_btn tool_btn_Big tool_btn_Blue" value="注册"/>
                <span class="user_remind remind">${registerMsg}</span>
            </li>
        </ul>
    </form>
    <div class="user_side forgivePassword">
        <ul class="user_content">
            <li class="forgivePassword_item">
                <h2 class="forgivePassword_title">找回密码</h2>
                <i class="icon-circle-arrow-right"></i>
            </li>
            <li class="forgivePassword_item">请输入注册邮箱：</li>
            <li class="forgivePassword_item">
                <input type="text" name="mail" class="tool_text tool_text_Big" required/>
                <i class="icon-envelope user_icon"></i>
            </li>
            <li class="forgivePassword_item">
                <input id="forgivePassword_submitBtn" type="button" class="tool_btn tool_btn_Big tool_btn_Blue"
                       value="激话"/>
                <span class="user_remind remind"></span>
            </li>
            <li class="forgivePassword_item remind" style="color:#3587C7; display: none;"></li>
        </ul>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/jQuery.md5.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function () {
        /*  var oSearch = fe.tool.search(window.location.search);*/
        switch ("${page}") {
            case 'register':
                $('.user_container').addClass('turnRight');
                break;
        }
        fe.user.login();
        fe.user.register();
        fe.user.forgivePassword();
    });
</script>
</body>
</html>

