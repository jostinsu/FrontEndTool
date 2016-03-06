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
    <title>前端可视化布局工具</title>
    <link rel="stylesheet" href="css/font-awesome.css"/>
    <link rel="stylesheet" href="css/main.css"/>
<body>
<header class="info_header">
    <div class="info_header_container">
        <h1 class="info_header_title">
            <img src="images/logo.png" alt="前端可视化布局工具"/>
            <span>前端可视化布局工具</span>
        </h1>
        <div class="info_header_aside">
            <span class="info_header_aside_title">欢迎您 : 用户名</span>
            <a href="javascript:;" class="info_header_aside_btn">退出</a>
        </div>
    </div>
</header>
<div class="info_main">
    <div class="info_main_container">
        <nav class="info_main_nav">
            <ul class="info_main_nav_list">
                <li class="info_main_nav_list_item"><a class="info_main_nav_list_item_link" href="projectList.html"><i class="icon-reorder"></i>个人项目</a></li>
                <li class="info_main_nav_list_item"><a class="info_main_nav_list_item_link info_main_nav_list_item_link_Active" href="account.html"><i class=" icon-user"></i>账号信息</a></li>
            </ul>
        </nav>
        <div class="info_main_stage">
            <div class="tool_header">
                <div class="tool_header_left">
                    <h2 class="tool_header_title">个人账户</h2>
                </div>
            </div>
            <div class="info_main_content">
                <div class="info_account_form">
                    <div class="info_account_item">
                        <span>注册邮箱：</span><strong>491501792@qq.com</strong>
                    </div>
                    <div class="info_account_item">
                        <span>昵称：</span>
                        <div class="info_account_item_content">
                            <strong id="oldNickname">苏健鑫</strong>
                            <input id="newNickname" type="text" class="tool_text" placeholder="请输入昵称" name="nickname"
                                   style="display: none;"/> <b id="remind" class="info_account_item_remind"
                                                               style="display: none;">请输入有效字符</b>
                            <i id="editNicknameBtn" class="info_account_item_edit icon-pencil tool_icon"></i>
                        </div>
                    </div>
                    <div class="info_account_item">
                        <span>密码：</span><strong class="info_account_item_password">********</strong> <a href="resetPassword.html" class="info_account_item_edit"><i class="icon-pencil tool_icon"></i></a>
                    </div>
                    <div class="info_account_item">
                        <span>注册时间：</span><strong>2016-03-20</strong>
                    </div>
                    <div class="info_account_item info_account_item_Last">
                        <input id="submitBtn" type="button" class="tool_btn tool_btn_Blue" value="确定"/>
                        <input id="cancelBtn" type="button" class="tool_btn" value="取消"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function(){
        fe.info.account();
        fe.app.remindBoxEvent();
    });
</script>
</body>
</html>
