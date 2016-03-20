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
    <link rel="stylesheet" href="css/zTreeStyle.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <style>
        html, body {
            background-color: #fff;
            height: 100%;
        }
    </style>
</head>
<body>
<div class="edit">
    <div class="edit_topBar">
        <h1 class="edit_logo">
            <img src="images/logo.png" alt="前端可视化布局工具"/>
            <span>前端可视化布局工具</span>
        </h1>
        <div class="edit_topBar_operation">
            <a href="#" class="tool_btn tool_btn_Blue">保存</a>
            <a href="#" class="tool_btn tool_btn_Blue">预览</a>
        </div>
        <div class="edit_topBar_other">
            <h2 class="edit_topBar_other_projectName">项目：${项目名字}</h2>
            <a class="edit_topBar_other_exitBtn" href="#">退出编辑</a>
        </div>
    </div>
    <div class="edit_body">
        <div class="edit_body_leftAside">
            <div class="edit_block edit_tree">
                <div class="edit_block_header">
                    <span>项目结构</span>
                </div>
                <div class="edit_block_body">
                    <ul id="tree" class="ztree"></ul>
                </div>
            </div>
            <div class="edit_block edit_element">
                <div class="edit_block_header">
                    <span>组件库</span>
                </div>
                <div class="edit_block_body">
                </div>
            </div>
            <!-- <a class="wp_cursor wp_cursor_Vertical"></a>-->
        </div>
        <div class="edit_body_rightAside">
            <div class="edit_block edit_attr">
                <div class="edit_block_header">
                    <span>属性设置区</span>
                </div>
                <div class="edit_block_body">
                </div>
            </div>
            <div class="edit_block edit_pageStyle">
                <div class="edit_block_header">
                    <span>页面样式设置区</span>
                </div>
                <div class="edit_block_body">
                </div>
            </div>
            <div class="edit_block edit_commonStyle">
                <div class="edit_block_header">
                    <span>公共样式设置区</span>
                </div>
                <div class="edit_block_body">
                </div>
            </div>
        </div>
        <div class="edit_body_main">
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/jquery.ztree.all.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function () {
        fe.app.remindBoxEvent();
        fe.app.confirmBoxEvent();
        fe.app.inputBoxEvent();

        // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
        var setting = {
            view: {
                showLine: true,
                showTitle: function (treeId, treeNode) {
                    var parent = treeNode.getParentNode();
                    if (parent) {
                        return parent.level == "1" && parent.name == "images";
                    }
                    return false;
                }
            },
            edit: {
                enable: true,
                drag: {
                    isCopy: false,
                    isMove: false,
                    prev: false,
                    next: false
                },
                showRenameBtn: false,
                showRemoveBtn: false
            },
            callback: {
                onRightClick: fe.edit.treeCallback.zTreeOnRightClick,
            },
            data: {
                key: {
                    'children': "trees",
                    'title': "title"
                }
            }
        };

        fe.tool.getJSON({
            url: "tree.json",
            success: function (res) {
                var zNodes = res.data.trees;
                fe.edit.zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
                fe.edit.initTree();
            },
            error: {
                remind: "获取项目结构失败"
            }
        });
        // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
        /* var zNodes = [
         {id:"1", isFolder:"1", name: "project", iconSkin:"folder",trees: [
         {
         id:"1", isFolder:"1", name: "image", iconSkin:"folder",trees: [
         {id:"1", isFolder:"0", name: "logo.png", iconSkin:"page",title:"/491501792@qq.com/project/images/logo.png",iconSkin: "img"},
         {id:"1", isFolder:"0", name: "topBarBg.png",iconSkin:"page", title:"/491501792@qq.com/project/images/topBarBg.png",iconSkin: "img"}
         ]
         },
         {
         id:"5", isFolder:"1", name: "html",  iconSkin:"folder",trees: [
         {id:"6", isFolder:"1", name: "xml",iconSkin:"folder",tree:[]},
         {
         id:"7", isFolder:"1", name: "user",iconSkin:"folder", trees: [
         {id:"8", isFolder:"0",iconSkin:"page", name: "login.html",tree:[]},
         {id:"9", isFolder:"0", iconSkin:"page",name: "register.html",tree:[]}
         ]
         }
         ]
         }
         ]
         }
         ];*/

        //alert(JSON.stringify(fe.edit.zTreeObj.getNodes(),null,4));
    });
</script>
</body>
</html>

