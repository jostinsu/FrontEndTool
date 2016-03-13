/**
 * Created by Administrator on 2016/3/4 0004.
 */
/*------------------------------个人中心模块-----------------------------*/
fe.info = {} //个人中心命名空间

/*为项目列表的dataTable绑定事件*/
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

	fe.info.dataTable.delegate('.icon-trash', 'click', function () {
		var id = $(this).data('id');
		$.confirmBox({
			id: "info_deleteProject",
			title: "删除项目",
			confirm: "确定要删除该项目吗？"
		}).data('projectId', id);
	});

}

//删除项目
fe.info.deleteProject = function (id) {
	fe.tool.getJSON({
		url: "data.json",
		data: {'id': id},
		success: function () {
			fe.info.dataTable.fnDraw(true);
		},
		error: {
			remind: "删除部门出错"
		}
	});

}

//修改用户昵称
fe.info.account = function () {
	var newNicknameInput = $('#newNickname'),
		oldNicknameText = $('#oldNickname'),
		btnWrap = $('.info_account_item_Last').eq(0),
		editNicknameBtn = $('#editNicknameBtn');

	editNicknameBtn.on('click', function () {
		$(this).hide();
		oldNicknameText.hide();
		newNicknameInput.show();
		btnWrap.show();
	});

	$('#cancelBtn').on('click', function () {
		btnWrap.hide();
		newNicknameInput.hide();
		oldNicknameText.show();
		editNicknameBtn.show();
	});

	$('#submitBtn').on('click', function () {
		var nickname = $('#newNickname').val();
		if (!nickname) {
			$('#remind').show();
		} else {
			$('#remind').hide();
			fe.tool.getJSON({
				url: "data.json",
				data: {'nickname': nickname},
				success: function () {
					btnWrap.hide();
					newNicknameInput.hide();
					oldNicknameText.html(nickname).show();
					editNicknameBtn.show();
				},
				error: {
					"remind": "修改昵称失败！"
				}
			});
		}
	});

};

/*------------------------------用户（登录注册）模块-----------------------------*/
fe.user = {} //用户命名空间

fe.user.login = function () {
	$('#forgivePasswordBtn').on('click', function () {
		$('.user_container').addClass('turnLeft');
		$('.forgivePassword .remind').html('');
	});

	$('#registerBtn').on('click', function () {
		$('.user_container').addClass('turnRight');
		$('.register .remind').html('');
	});

	$('.login').on('submit', function () {
		$(this).find('input[name="password"]').val($.md5($(this).val()));
	});
};

fe.user.register = function () {
	$('#password2').on('change', function () {
		if ($(this).val() != $('#password1').val()) {
			$(this).get(0).setCustomValidity("两次输入的密码不匹配");
		} else {
			$(this).get(0).setCustomValidity("");
		}
	});

	$('.icon-circle-arrow-left').on('click', function () {
		$('.user_container').removeClass('turnRight');
		$('.login .remind').html('');
	});

	$('.register').on('submit', function () {
		$('#password1').val($.md5($(this).val()));
		$('#password2').val($.md5($(this).val()));
	});
};

fe.user.forgivePassword = function () {
	$('.icon-circle-arrow-right').on('click', function () {
		$('.user_container').removeClass('turnLeft');
		$('.login .remind').html('');
	});
	$('#forgivePassword_submitBtn').on('click', function () {
		var obj = fe.tool.serialize($('.forgivePassword'));
		if (/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(obj.mail)) {
			$.ajax({
				type: "post",
				url: "data.json",
				dataType: "json",
				data: obj,
				success: function (res) {
					if (res.success) {
						$('.forgivePassword_item:last-child').html('请到注册邮箱接收激活邮件，并按步骤重置密码');
						$('.user_remind').html('');
					} else {
						$('.user_remind').html(res.msg || "该邮箱非注册登记的邮箱");
						$('.forgivePassword_item:last-child').html('');
					}
				}
			});
		} else {
			$('.user_remind').html("请输入有效的邮箱地址");
		}
	});
};









