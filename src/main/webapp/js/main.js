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
				$.remindBox({
					remind: '请确保结束时间大于开始时间'
				});
			}else{
				fe.info.dataTable.fnDraw(true);
			}
		}else{
			$.remindBox({
				remind: '不能只填写开始时间或结束时间'
			});
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

	fe.info.dataTable.delegate('.icon-download-alt', 'click', function () {
		window.location.href = "/zip?id=" + $(this).data('id');
	});
}

//删除项目
fe.info.deleteProject = function (id) {
	fe.tool.getJSON({
		url: "deleteProject",
		data: {'id': id},
		success: function () {
			fe.info.dataTable.fnDraw(true);
		},
		error: {
			remind: "删除项目出错"
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
				url: "updateNickname",
				data: {'nickname': nickname},
				success: function (res) {
					btnWrap.hide();
					newNicknameInput.hide();
					oldNicknameText.html(nickname).show();
					editNicknameBtn.show();
					$.tip({"content": res.msg});
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
		var password = $(this).find('input[name="password"]').val();
		$(this).find('input[name="password"]').val($.md5(password));
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
		$('#password1').val($.md5($('#password1').val()));
		$('#password2').val($.md5($('#password2').val()));
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
				url: "resetRequest",
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

/*------------------------------编辑页面模块-----------------------------*/
fe.edit = {} //用户命名空间

fe.edit.initTree = function () {

	function tree_delete(treeNode) {
		var obj = {
			id: "deleteTreeNode"
		};
		switch (fe.edit.pageFlag) {
			case 'normalFolder':
				obj.title = "删除文件夹";
				obj.confirm = "删除文件夹会一并删除文件夹中的页面，确定删除该文件夹吗？";
				break;
			case 'htmlPage':
				obj.title = "删除页面";
				obj.confirm = "确定删除该页面吗?";
				break;
			case 'picPage':
				treeNode.deletePic = true;
				obj.title = "删除图片";
				obj.confirm = "确定删除该图片吗？";
				break;
		}
		$.confirmBox(obj).data('treeNode', treeNode);
	}

	function tree_rename(treeNode) {
		var obj = {
			id: "renameTreeNode",
			title: "重命名",
			placeholder: "英文字母、数字、下划线或其组合",
			inputValue: treeNode.name.split('.')[0]
		};
		switch (fe.edit.pageFlag) {
			case 'normalFolder':
				obj.content = "新的文件夹名";
				break;
			case 'htmlPage':
				obj.content = "新的图片名";
				break;
			case 'picPage':
				treeNode.renamePic = true;
				obj.content = "新的文件名";
				break;
		}
		$.inputBox(obj).data('treeNode', treeNode);
	}

	function tree_newPage(treeNode) {
		$.inputBox({
			id: "addTreeNodeForPage",
			title: "新建页面",
			content: "文件名",
			placeholder: "英文字母、数字、下划线或其组合"
		}).data('treeNode', treeNode);
	}

	function tree_newFolder(treeNode) {
		$.inputBox({
			id: "addTreeNodeForFolder",
			title: "新建文件夹",
			content: "文件夹名",
			placeholder: "英文字母、数字、下划线或其组合"
		}).data('treeNode', treeNode);
	}

	function tree_download() {
		window.location.href = "/zip?id=" + fe.edit.data.id;
	}


	$(document).on('click', function () {
		$('.edit_tree_rightMenu').remove();
	});
	$(document).delegate('.edit_tree_rightMenu li', 'click', function () {
		var treeNode = $(this).parent().data('treeNode');
		switch ($(this).html()) {
			case '删除':
				tree_delete(treeNode);
				break;
			case '重命名':
				tree_rename(treeNode);
				break;
			case '新建页面':
				tree_newPage(treeNode);
				break;
			case '新建文件夹':
				tree_newFolder(treeNode);
				break;
			case '导出':
				tree_download(treeNode);
				break;
		}
	});
};

fe.edit.deleteTreeNode = function (treeNode) {
	fe.tool.getJSON({
		url: treeNode.deletePic ? "deleteFile" : "deleteTree",
		data: {'id': treeNode.id, 'title': treeNode.title},
		success: function () {
			fe.edit.zTreeObj.removeNode(treeNode);
		},
		error: {
			remind: "删除失败"
		}
	});
};

fe.edit.beforeRenameTreeNode = function (name, treeNode) {
	var fullName = name;
	if (fe.edit.pageFlag.indexOf('Folder') == -1) {
		fullName = name + "." + treeNode.name.split('.').pop();
	}
	var obj = {
		flag: true,
		remind: '',
		fullName: fullName
	};
	if (!/^[A-Za-z0-9_]{1,255}$/g.test(name)) {
		obj = {
			flag: false,
			remind: '请输入有效的字符'
		};
	} else {
		var brotherNodes = treeNode.getParentNode().trees;
		var index = treeNode.getIndex();
		if (brotherNodes && brotherNodes.length) {
			for (var i = 0; i < brotherNodes.length; i++) {
				if (i != index && obj.fullName == brotherNodes[i].name) {
					obj = {
						flag: false,
						remind: '同级节点中已存在该文件名'
					};
					break;
				}
			}
		}
	}
	return obj;
};

fe.edit.renameTreeNode = function (treeNode) {
	var newName = $('.inputBox_body_input').val();
	var check = fe.edit.beforeRenameTreeNode(newName, treeNode);
	if (!check.flag) {
		$('.inputBox_body_inputRemind').html(check.remind);
	} else {
		fe.tool.getJSON({
			url: treeNode.renamePic ? "renameFile" : "renameTreeNodeName",
			data: {'id': treeNode.id, 'title': treeNode.title, 'name': check.fullName},
			success: function () {
				if (treeNode.renamePic) {
					var arr = treeNode.title.split('/');
					arr[arr.length - 1] = check.fullName;
					treeNode.title = arr.join('/');
				}
				treeNode.name = check.fullName;
				fe.edit.zTreeObj.updateNode(treeNode);
				$('.inputBox_cover').remove();
			},
			error: {
				remind: "重命名失败"
			}
		});
	}
};

fe.edit.beforeAddTreeNode = function (name, treeNode, isAddPage) {
	var fullName = $.trim(name);
	if (isAddPage) {
		fullName = $.trim(name) + '.html';
	}
	var obj = {
		flag: true,
		remind: '',
		fullName: fullName
	};
	if (!/^[A-Za-z0-9_]{1,255}$/g.test($.trim(name))) {
		obj = {
			flag: false,
			remind: '请输入有效的字符'
		};
	} else {
		var brotherNodes = treeNode.trees;
		if (brotherNodes && brotherNodes.length) {
			for (var i = 0; i < brotherNodes.length; i++) {
				if (obj.fullName == brotherNodes[i].name) {
					obj = {
						flag: false,
						remind: '同级节点中已存在该文件名'
					};
					break;
				}
			}
		}
	}
	return obj;
};


fe.edit.compareNode = function (name, borther, isFolder) {
	var index = -1;
	var k = 0;
	for (var i = 0; i < borther.length; i++) {
		if (isFolder == "1") {
			index = k;
			if (borther[i].isFolder == "1") {
				if (borther[i].name != "image" && name < borther[i].name) {
					index = i;
					break;
				} else {
					index = ++k;
				}
			}
		} else {
			index = -1;
			if (borther[i].isFolder == "0") {
				if (name < borther[i].name) {
					index = i;
					break;
				}
			}
		}
	}
	return index;
};

fe.edit.addTreeNodeForPage = function (treeNode) {
	var name = $('.inputBox_body_input').val();
	var check = fe.edit.beforeAddTreeNode(name, treeNode, true);
	if (!check.flag) {
		$('.inputBox_body_inputRemind').html(check.remind);
	} else {
		fe.tool.getJSON({
			url: "saveTree",
			data: {'isFolder': 0, 'name': check.fullName, 'iconSkin': 'page', "parentid": treeNode.id},
			success: function (res) {
				fe.edit.zTreeObj.addNodes(treeNode, fe.edit.compareNode(check.fullName, treeNode.trees, "0"), {
					id: res.id,
					isFolder: 0,
					name: check.fullName,
					iconSkin: "page",
					trees: [],
					pages: []
				});
				$('.inputBox_cover').remove();
			},
			error: {
				remind: "新建文件失败"
			}
		});
	}
};

fe.edit.addTreeNodeForFolder = function (treeNode) {
	var name = $('.inputBox_body_input').val();
	var check = fe.edit.beforeAddTreeNode(name, treeNode, false);
	if (!check.flag) {
		$('.inputBox_body_inputRemind').html(check.remind);
	} else {
		fe.tool.getJSON({
			url: "saveTree",
			data: {'isFolder': 1, 'name': check.fullName, 'iconSkin': 'folder', "parentid": treeNode.id},
			success: function (res) {
				fe.edit.zTreeObj.addNodes(treeNode, fe.edit.compareNode(check.fullName, treeNode.trees, "1"), {
					id: res.id,
					isFolder: 1,
					name: check.fullName,
					iconSkin: "folder",
					trees: [],
					pages: []
				});
				$('.inputBox_cover').remove();
			},
			error: {
				remind: "新建文件失败"
			}
		});
	}
};


fe.edit.treeCallback = {
	//右键点击事件
	zTreeOnRightClick: function (event, treeId, treeNode) {
		//alert(treeNode.isFolder);
		$('.edit_tree_rightMenu').remove();
		var aRightMenu = ['新建文件夹', '新建页面', '重命名', '删除'];
		if (treeNode.isFolder == "1") {
			switch (true) {
				case treeNode.tId == "tree_1":
					aRightMenu.length = 2;
					aRightMenu.push("导出");
					fe.edit.pageFlag = "rootFolder";
					break;
				case treeNode.parentTId == "tree_1" && treeNode.name == "image":
					aRightMenu[0] = "上传图片";
					aRightMenu.length = 1;
					fe.edit.pageFlag = "imagesFolder";
					break;
				default :
					fe.edit.pageFlag = "normalFolder";
					break;
			}
		} else {
			aRightMenu.shift();
			aRightMenu.shift();
			if (treeNode.getParentNode().name == "image" && treeNode.getParentNode().level == "1") {
				fe.edit.pageFlag = "picPage";
			} else {
				fe.edit.pageFlag = "htmlPage";
			}
		}
		var shtml = '<ul class="edit_tree_rightMenu" style="position: absolute;left:' + event.clientX + 'px;top:' + event.clientY + 'px;"><li>' + aRightMenu.join('</li><li>') + '</li></ul>';
		$(shtml).appendTo('body').data('treeNode', treeNode);
	}
}