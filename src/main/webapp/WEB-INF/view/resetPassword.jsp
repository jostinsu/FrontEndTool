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
                <li class="info_main_nav_list_item"><a class="info_main_nav_list_item_link info_main_nav_list_item_link_Active" href="account.html"><i class="icon-user"></i>账号信息</a></li>
            </ul>
        </nav>
        <div class="info_main_stage">
            <div class="tool_header">
                <div class="tool_header_left">
                    <div class="tool_header_title_back">
                        <a href="account.html"><i class="icon-chevron-left"></i></a>
                    </div>
                    <h2 class="tool_header_title">修改密码</h2>
                </div>
            </div>
            <div class="info_main_content">
                <form action="#" class="info_resize_form" id="info_resize_form" method="post">
                    <div class="info_resize_item">
                        <span>原密码：</span><input name="name" type="password" class="tool_text" required maxlength="16"
                                                pattern="[\w]{6,16}"/>
                    </div>
                    <div class="info_resize_item">
                        <span>新密码：</span><input id="newPassword1" name="name" type="password" class="tool_text"
                                                placeholder="请输入6到16位新密码" required maxlength="16" pattern="[\w]{6,16}"/>
                    </div>
                    <div class="info_resize_item">
                        <span>确认新密码：</span><input id="newPassword2" name="name" type="password" class="tool_text"
                                                  placeholder="请确认新密码" required maxlength="16" pattern="[\w]{6,16}"/>
                    </div>
                    <div class="info_resize_item">
                        <input type="submit" value="确定" class="tool_btn tool_btn_Blue"/>
                        <input type="reset" value="重置" class="tool_btn"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function(){
        $('#newPassword2').on('change', function () {
            if ($(this).val() != $('#newPassword1').val()) {
                $(this).get(0).setCustomValidity("两次输入的密码不匹配");
            } else {
                $(this).get(0).setCustomValidity("");
            }
        });
        fe.app.remindBoxEvent();
        var backInfo = {
            success: true,
            msg: ""
        };
        fe.tool.success(backInfo, function () {
            $.tip({
                content: "已成功修改密码"
            });
        }, function () {
            $.remindBox({
                remind: backInfo.msg || "修改密码失败"
            });
        });

    });
</script>
</body>
</html>