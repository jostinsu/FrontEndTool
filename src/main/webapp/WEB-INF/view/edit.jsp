<%--
  Created by IntelliJ IDEA.
  User: sujianxin
  Date: 2016/3/4
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>前端可视化布局工具</title>
    <link rel="stylesheet" href="css/font-awesome.css"/>
    <link rel="stylesheet" href="css/zTreeStyle.css"/>
    <link rel="stylesheet" href="css/jquery.snippet.css"/>
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
            <a href="javascript:;" class="tool_btn tool_btn_Blue" id="edit_saveBtn">保存</a>
            <a href="javascript:;" class="tool_btn tool_btn_Blue" id="edit_previewLayoutBtn">预览布局</a>
            <a href="javascript:;" class="tool_btn tool_btn_Blue" id="edit_previewBtn">预览</a>
            <a href="javascript:;" class="tool_btn tool_btn_Blue" id="edit_showPageCode">页面代码</a>
            <a href="javascript:;" class="tool_btn tool_btn_Blue" id="edit_showResetStyle">全局样式</a>
        </div>
        <div class="edit_topBar_other">
            <h2 class="edit_topBar_other_projectName"></h2>
            <a class="edit_topBar_other_exitBtn" href="project">退出编辑</a>
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
                    <div class="edit_element_main">
                        <c:if test="${not empty feType}">
                            <c:forEach items="${feType}" var="feType">
                                <div class="edit_element_item" data-id="${feType.id}">
                                    <div class="edit_element_title">
                                        <i class="tool_icon icon-caret-right"></i>
                                        <h3>${feType.name}</h3>
                                    </div>
                                    <div class="edit_element_content">
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="edit_body_rightAside">
            <div class="edit_block edit_attr">
                <div class="edit_block_header">
                    <span>属性设置</span><span id="elemTarget" style="display: inline-block;margin-left: 10px;"></span>
                </div>
                <div class="edit_block_body">
                    <ul class="edit_attr_header">
                        <li class="edit_attr_header_item active" id="elemAttrBtn">元素属性</li>
                        <li class="edit_attr_header_item" id="elemSourceCodeBtn">源码</li>
                    </ul>
                    <div class="edit_attr_content">
                        <div class="edit_attrSetting" id="elemAttr">
                            <div class="edit_attrSetting_header">
                                <div class="edit_attrSetting_item">
                                    <div class="edit_attrSetting_item_title">
                                        <span>属性</span>
                                    </div>
                                    <div class="edit_attrSetting_item_description">
                                        <span>属性值</span><i class="tool_icon icon-plus-sign"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="edit_attrSetting_body">
                                <div class="edit_attrSetting_item">
                                    <div class="edit_attrSetting_item_title">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                    <div class="edit_attrSetting_item_description">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                </div>
                                <div class="edit_attrSetting_item">
                                    <div class="edit_attrSetting_item_title">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                    <div class="edit_attrSetting_item_description">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                </div>
                                <div class="edit_attrSetting_item">
                                    <div class="edit_attrSetting_item_title">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                    <div class="edit_attrSetting_item_description">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                </div>
                                <div class="edit_attrSetting_item">
                                    <div class="edit_attrSetting_item_title">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                    <div class="edit_attrSetting_item_description">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                </div>
                                <div class="edit_attrSetting_item">
                                    <div class="edit_attrSetting_item_title">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                    <div class="edit_attrSetting_item_description">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                </div>
                                <div class="edit_attrSetting_item">
                                    <div class="edit_attrSetting_item_title">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                    <div class="edit_attrSetting_item_description">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                </div>
                                <div class="edit_attrSetting_item">
                                    <div class="edit_attrSetting_item_title">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                    <div class="edit_attrSetting_item_description">
                                        <input type="text" class="tool_text"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="edit_sourceSetting" id="elemCode" style="display: none;">
                            <div class="edit_editSourceCode" style="display: none">
                                <div class="edit_sourceSetting_header">
                                    <i class="tool_icon  icon-fullscreen" title="全屏显示"></i>
                                    <i class="tool_icon   icon-circle-arrow-left" title="退出编辑"></i>
                                    <i class="tool_icon   icon-eye-open" title="格式化代码"></i>
                                </div>
                                <div class="edit_sourceSetting_body edit_editSourceCode_body">
                                    <textarea id="elemSourceCode"></textarea>
                                </div>
                            </div>
                            <div class="edit_showSourceCode">
                                <div class="edit_sourceSetting_header">
                                    <i class="tool_icon  icon-fullscreen" title="全屏显示"></i>
                                    <i class="tool_icon  icon-edit" title="编辑"></i>
                                </div>
                                <div class="edit_sourceSetting_body edit_showSourceCode_body">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="edit_block edit_pageStyle" id="pageStyle">
                <div class="edit_block_header">
                    <span>样式设置</span>
                </div>
                <div class="edit_block_body">
                    <div class="edit_styleSetting">
                        <div class="edit_styleSetting_title">
                            <span>类名:</span>
                            <input type="text" class="tool_text edit_styleSetting_name"/>
                            <i class="tool_icon icon-eye-open" title="格式化代码"></i>
                        </div>
                        <div class="edit_styleSetting_content">
                            <textarea id="edit_styleSettingCode"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="edit_body_main" id="editMain">
        </div>
    </div>
</div>

<div class="tool_cover" id="edit_resetStyleCover" style="display: none;">
    <article class="tool_editBox">
        <header class="tool_editBox_header">
            <h2>全局样式</h2>
            <i class="tool_icon icon-remove"></i>
        </header>
        <div class="tool_editBox_body">
            <div class="tool_editor">
                <ul class="tool_editor_tab">
                    <li class="tool_editor_tab_item active">高亮源码</li>
                    <li class="tool_editor_tab_item">编辑</li>
                </ul>
                <div class="tool_editor_body">
                    <div class="edit_showResetStyle"></div>
                    <div class="edit_editResetStyle" style="display: none;">
                        <textarea id="editResetStyle"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </article>
</div>
<div class="tool_cover" id="edit_pageCodeCover" style="display: none;">
    <article class="tool_editBox">
        <header class="tool_editBox_header">
            <h2>页面代码</h2>
            <i class="tool_icon icon-remove"></i>
        </header>
        <div class="tool_editBox_body">
            <div class="tool_editor">
                <ul class="tool_editor_tab">
                    <li class="tool_editor_tab_item active">index.html</li>
                    <li class="tool_editor_tab_item">page.css</li>
                    <li class="tool_editor_tab_item">reset.css</li>
                </ul>
                <i id="edit_pageCode_downloadBtn" class="tool_icon icon-download-alt"></i>
                <div class="tool_editor_body">
                    <div class="edit_showHtmlCode"></div>
                    <div class="edit_showPageCss" style="display: none;"></div>
                    <div class="edit_showResetCss" style="display: none;"></div>
                </div>
            </div>
        </div>
    </article>
</div>

<script src="js/jquery.js"></script>
<script src="js/jquery.ztree.all.js"></script>
<script src="js/jquery.snippet.js"></script>
<script src="js/formatHtml.js"></script>
<script src="js/formatCss.js"></script>
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
                onClick: fe.edit.treeCallback.zTreeOnClick,
            },
            data: {
                key: {
                    'children': "trees",
                    'title': "title"
                }
            }
        };

        fe.tool.getJSON({
            url: "tree",
            data: {id: "${id}"},
            success: function (res) {
                $('.edit_topBar_other_projectName').html('项目：' + res.data.name);
                formatData(res);
                fe.edit.zTreeObj = $.fn.zTree.init($("#tree"), setting, res.data.trees);
                fe.edit.initTree();
                fe.edit.elemEvent();
                fe.edit.elemAttrEvent();
                fe.edit.styleEvent();
                fe.edit.operationEvent();
                fe.edit.resetStyleEvent();
                fe.edit.pageCodeEvent();
            },
            error: {
                remind: "获取项目结构失败"
            }
        });

        function formatData(res) {
            fe.edit.data = {
                currentPage: {
                    id: '',
                    downloadCode: '',
                    multipleCode: ''
                },
                currentNode: {
                    tId: '',
                    pageFlag: '',
                },
                currentElem: {
                    self: null,
                    parent: null,
                    wrapId: '',
                    oAttr: {},
                    oClass: {},
                    className: ''
                }
            };
            for (var node in res.data) {
                if (node != "trees") {
                    fe.edit.data[node] = res.data[node];
                }
            }
            fe.edit.data.styles[0].pageResetCssCode = fe.edit.data.styles[0].code.replace(/html|body/g, '.sjx_$&');
        }
    });
</script>
</body>
</html>
