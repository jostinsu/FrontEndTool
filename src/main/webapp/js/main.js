/**
 * Created by Administrator on 2016/3/4 0004.
 */
/*------------------------------个人中心模块-----------------------------*/
fe.info = {} //个人中心命名空间

/*为部门管理的dataTable绑定事件*/
fe.info.dataTableEvent = function(){

	$('#info_table_searchBtn').on('click',search);
	$(document).on('keyup',function(ev){
		if(ev.keyCode=='13'){
			search();
		}
	});

	function search(){
		var fromTime = $('#info_fromTime').val(),
			toTime = $('#info_toTime').val();
		if(!fromTime&&!toTime||fromTime&&toTime){
			if(toTime<fromTime){
				alert("请确保结束时间大于开始时间");
			}else{
				fe.info.dataTable.fnDraw(true);
			}
		}else{
			alert("不能只填写开始时间或结束时间");
		}

	}

	//$('#dep-add-btn').on('click',function(){
	//	$(this).css('background','#ff9900');
	//	$('#dep-addBox-cover').show();
	//	$('#dep-addBox-cover [type="date"]').val(hr.tool.formatTime(new Date()));
	//	hr.tool.getJSON({
	//		url:oUrl.OG.depNameForSelect.url,
	//		success:function(data){
	//			var aHtml = hr.tool.getOptionsOfSelect(data);
	//			$('#dep-addBox-depSelect').html(aHtml.join(''));
	//		},
	//		error:{
	//			remind:'获取上级部门下拉框数据失败!'
	//		}
	//	});
	//});
	//
	//fe.info.dataTable.delegate('.icon-edit','click',function(){
	//	$(this).addClass('com-active-icon');
	//	$('#dep-editBox-cover').show();
	//	fe.info.beforeEdit($(this).data('rowIndex'));
	//});
	//
	//fe.info.dataTable.delegate('.icon-trash','click',function(){
	//	var id = $(this).data('id');
	//	$(this).addClass('com-active-icon');
	//	$.confirmBox({
	//		id:"dep-delete-confirmBox",
	//		title:"删除部门",
	//		confirm:"确定要删除该部门吗？"
	//	}).data('depId',id);
	//});
}