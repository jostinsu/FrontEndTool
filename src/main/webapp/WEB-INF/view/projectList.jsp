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
    <link rel="stylesheet" href="css/jquery.dataTables.css"/>
    <link rel="stylesheet" href="css/main.css"/>
<body>
<%@include file="header.jsp" %>
<div class="info_main">
    <div class="info_main_container">
        <nav class="info_main_nav">
            <ul class="info_main_nav_list">
                <li class="info_main_nav_list_item"><a
                        class="info_main_nav_list_item_link info_main_nav_list_item_link_Active" href="project"><i
                        class="icon-reorder"></i>个人项目</a></li>
                <li class="info_main_nav_list_item"><a class="info_main_nav_list_item_link" href="userInfo"><i
                        class=" icon-user"></i>账号信息</a></li>
            </ul>
        </nav>
        <div class="info_main_stage">
            <div class="tool_header">
                <div class="tool_header_left">
                    <h2 class="tool_header_title">项目列表</h2>
                </div>
                <div class="tool_header_right">
                    <a href="newProject" class="tool_btn">新增项目</a>
                </div>
            </div>
            <div class="info_main_content">
                <div class="info_filter">
                    <div class="info_filter_item">
                        <span class="info_filter_item_title">创建时间：</span><input type="date"  class="tool_text" id="info_fromTime"/><span class="info_filter_item_link">至</span><input type="date"  class="tool_text" id="info_toTime"/>
                    </div>
                    <div class="info_filter_item">
                        <span class="info_filter_item_title">项目名称：</span><input type="text" class="tool_text" id="info_name"/>
                    </div>
                    <div class="info_filter_item">
                        <a href="javascript:;" class="tool_btn tool_btn_Blue" id="info_table_searchBtn">查询</a>
                    </div>
                </div>
                <table id="depTable" class="display com-dataTableInit" cellspacing="0" style="display: none;">
                    <thead>
                    <tr>
                        <th style="width: 50px;">编号</th>
                        <th style="width: 100px;">创建时间</th>
                        <th style="width: 200px;">项目名称</th>
                        <th>备注</th>
                        <th style="width: 80px;">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="js/jquery.js"></script>
<script src="js/jquery.dataTables.js"></script>
<script src="js/common.js"></script>
<script src="js/main.js"></script>
<script>
    $(function(){
        fe.info.dataTable = $('#depTable').dataTable({
            "bServerSide": true,  //启动服务端分页、排序，搜索等功能
            "sAjaxSource": 'project',  //ajax请求路径
            "aaSorting" : [ [ 1, 'desc' ] ],  //初始化时默认的排序字段
            //"bProcessing": true, //当dataTable获取数据时候是否显示正在处理提示信息。
            //"bSort":true, //启动排序功能
            //"bFilter":false,  //去掉默认的搜索功能
            //"sPaginationType" : "full_numbers",  //格式化分页条
            //"iDisplayLength ":'10',  //初始化时显示的条数
            //"oLanguage":{ "sUrl": "/FrontEndTool/en_dataTables.json"},   //导入中文语言包
            //"sDom":'frtilp', //格式化布局
            //修改参数
            "fnServerParams":function(aoData){
                aoData.push(
                        { "name": "search_fromTime", "value": $('#info_fromTime').val() },
                        { "name": "search_toTime", "value": $('#info_toTime').val() },
                        { "name": "search_name", "value": $('#info_name').val() });
            },
            "fnServerData":fe.tool.retrieveData,//自定义ajax函数
            //格式化列
            "aoColumns": [
                {  "mData": "id","bSortable":false},
                {  "mData": "createTime"},
                {  "mData": "name","bSortable":false},
                {  "mData": "remark" ,"bSortable":false},
                {  "mData": "id","bSortable":false}
            ],
            "fnRowCallback" :  function(nRow, oData, iDisplayIndex, iDisplayIndexFull){
                $('td:eq(0)', nRow).html(iDisplayIndex+1);
                $('td:eq(4)', nRow).html('<i class="tool_icon icon-edit" data-id="'+oData.id+'"></i><i class="tool_icon icon-download-alt " data-id="'+oData.id+'"></i><i class="tool_icon icon-trash " data-id="'+oData.id+'"></i>');
                return nRow;
            }
        }).show();
        fe.info.dataTableEvent();
        fe.app.confirmBoxEvent();
        fe.app.remindBoxEvent();
    });
</script>
</body>
</html>
