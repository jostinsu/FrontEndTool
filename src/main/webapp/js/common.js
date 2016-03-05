/**
 * Created by Administrator on 2015/10/8 0008.
 */

/*-----------------扩展在jquery下的工具函数---------------------*/
$.extend({
	/*动态生成确认框的方法*/
	confirmBox : function(option){
		var setting = $.extend({
			id:"confirmBox",
			title:"确认框",
			confirm:"您确定执行该操作吗？"
		},option||{});
		return $(' <div class="com-cover confirmBox-cover">\
			           <article id='+setting.id+' class="com-confirmBox">\
			               <header>\
			                   <h2>'+setting.title+'</h2>\
			                   <i class="icon-remove confirmBox-closeBtn"></i>\
			               </header>\
			               <section>\
			                   <i class="icon-question-sign com-active-icon"></i>\
			                   <p>'+setting.confirm+'</p>\
		                   </section>\
		                   <footer>\
		                       <a class="com-btn confirmBox-submitBtn">确定</a>\
			                   <a class="com-btn confirmBox-cancelBtn">取消</a>\
			               </footer>\
			           </article>\
			      </div>').appendTo($('body'));
	},

	/*动态生成提醒框的方法*/
	remindBox:function(option){
		var setting = $.extend({
			id:"confirmBox",
			title:"提醒框",
			remind:"您的操作出现错误！"
		},option||{});
		return $(' <div class="com-cover remindBox-cover">\
			           <article id='+setting.id+' class="com-remindBox">\
			               <header>\
			                   <h2>'+setting.title+'</h2>\
			                   <i class="icon-remove remindBox-closeBtn"></i>\
			               </header>\
			               <section>\
			                   <i class="icon-info-sign com-active-icon"></i>\
			                   <p>'+setting.remind+'</p>\
		                   </section>\
		                   <footer>\
		                       <a class="com-btn remindBox-submitBtn">确定</a>\
			               </footer>\
			           </article>\
			      </div>').appendTo($('body'));
	},
});


var fe = {}; //全局的命名空间
/*--------------------------------项目中的工具函数------------------------------*/
fe.tool = {
	/*dataTables 通过ajax后台取数据的方法*/
	retrieveData:function( sSource, aoData, fnCallback ){
		var data = fe.tool.formatArgument(aoData);
		console.log(JSON.stringify(data,null,4));
		$.ajax( {
			"type": "post",
			"url": sSource,
			"dataType": "json",
			//"data": {"paramsData":JSON.stringify(data,null,4)}, //以json格式传递
			"data": data, //以json格式传递
			"success": fnCallback,
		});
	},

	/*格式化dataTables传到后台的参数*/
	formatArgument:function(aoData){
		var i=0,
			len = aoData.length,
			obj=null,
			start = 0,
			sortCol = 0,
			mData = [],
			oSearch = {},
			oData = {};
		for(;i<len;i++){
			obj = aoData[i];
			switch(obj.name){
				case 'iDisplayStart':
					start=obj.value;
					break;
				case 'iDisplayLength':
					oData.pageSize = obj.value>=1 ? obj.value : '';
					oData.page = oData.pageSize!='' ? (start/oData.pageSize + 1) : '';
					break;
				case 'sSortDir_0':
					oData.sortDir = obj.value;
					break;
				case 'iSortCol_0':
					sortCol = obj.value;
					break;
				default :
					format(obj.name,obj);
			}
		}
		function format(name,obj){
			if(/mDataProp_/.test(name)){
				mData.push(obj.value);
			}else if(/search_/.test(name)){
				name = name.substring(7);
				//oSearch[name] = obj.value;
				oData[name] = obj.value;
			}
		}
		oData.sortCol = mData[sortCol];
		//oData.oSearch = oSearch;
		return oData;
	},

	/*通过ajax获取JSON数据的方法*/
	getJSON:function(option){
		var setting = $.extend({
			async:true,
			url:"#",
			data:null,
			loading:false,
			success:function(data){},
			error:{}
		},option||{});
		if(setting.loading){
			$('.com-loadCover').show();
		}
		console.log("传到后台的参数="+JSON.stringify(setting.data,null,4));
		$.ajax({
			type:"post",
			url:setting.url,
			async:setting.async,
			data:setting.data,
			dataType:"json",
			success:function(data){
				console.log("后台返回的参数="+JSON.stringify(data,null,4));
				if(setting.loading){
					$('.com-loadCover').hide();
				}
				if(data.success){
					setting.success(data.data);
				}else{
					setting.error.remind = data.msg;
					$.remindBox(setting.error);
				}
			}
		});
	},

	getOptionsOfSelect:function(data){
		var arr = [];
		$.each(data,function(i,value){
			arr[i] =  '<option value="'+data[i].id+'">'+data[i].name+'</option>';
		});
		return arr;
	},

	/*反序列化,将数据填入表单中*/
	deserialize :function(seletor,data){
		$(seletor + ' [name]').each(function(){
			//console.log(this.name);
			$(this).val(data[this.name]);
		});
	},

	/*序列化函数*/
	serialize :function(form,data){
		$(form).find('[name]').each(function(){
			data[this.name] = $(this).val();
		});
		return data;
	},

	formatTime:function(date){
		return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
	}

}

/*--------------------------------项目中的公共运用函数------------------------------*/
/*
fe.app = {} //公共的实现方法
/!*主导航的事件绑定函数*!/
fe.app.nav = function(){
	$('.nav dt').on('click',function(){
		if(!$(this).siblings('dd').hasClass('dd-active')){
			$(this).siblings('dd').toggle();
		}
	});
	$('.nav dl').delegate('dd','click',function(){
		$('.nav dt').removeClass('dt-active');
		$('.nav dd').removeClass('dd-active');
		$(this).addClass('dd-active').siblings('dt').addClass('dt-active');
	});
	$('.face').on('click',function(){
		$.remindBox({
			remind:'该系统暂无提供登录用户功能，可后期添上，如有登录功能，则登录者信息将保留在这头像中!'
		});
	});
}

/!*为确认框绑定事件*!/
fe.app.confirmBoxEvent = function(){

	/!*为确认框绑定关闭事件*!/
	$(document).delegate('.confirmBox-cancelBtn, .confirmBox-closeBtn','click',function(){
		switch($('.com-confirmBox').attr('id')){
			case 'dep-delete-confirmBox':
				$('.icon-trash').removeClass('com-active-icon');
				break;
			case 'job-delete-confirmBox':
				$('.icon-trash').removeClass('com-active-icon');
				break;
		}
		$('.confirmBox-cover').remove();
	});

	/!*为确认框绑定“确认”事件*!/
	$(document).delegate('.confirmBox-submitBtn','click',function(){
		var confirmBox = $('.com-confirmBox'),
			id=confirmBox.attr('id');
		switch(id){
			case 'dep-delete-confirmBox':
				fe.dep.delete($('.confirmBox-cover').data('depId'));
				break;
			case 'job-delete-confirmBox':
				fe.job.delete($('.confirmBox-cover').data('jobId'));
				break;
		}
		$('.confirmBox-cover').remove();
	});
}

/!*为提醒框绑定事件*!/
fe.app.remindBoxEvent = function(){
	/!*为提醒框绑定关闭事件*!/
	$(document).delegate('.remindBox-submitBtn,.remindBox-closeBtn','click',function(){
		var remindBox = $('.com-remindBox'),
			id=remindBox.attr('id');
		switch(id){
			case 'dep-deleteFail-remindBox':
				$('.icon-trash').removeClass('com-active-icon');
				break;
			case 'job-deleteFail-remindBox':
				$('.icon-trash').removeClass('com-active-icon');
				break;
		}
		$('.remindBox-cover').remove();
	});
}*/
