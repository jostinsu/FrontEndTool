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
};

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
		switch (fe.edit.data.currentNode.pageFlag) {
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
		switch (fe.edit.data.currentNode.pageFlag) {
			case 'normalFolder':
				obj.content = "新的文件夹名";
				break;
			case 'htmlPage':
				obj.content = "新的文件名";
				break;
			case 'picPage':
				treeNode.renamePic = true;
				obj.content = "新的图片名";
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
				fe.edit.savePage(false, fe.edit.download);
				break;
		}
	});
};

fe.edit.download = function () {
	var treeNode = fe.edit.zTreeObj.getNodeByTId(fe.edit.data.currentNode.tId);
	if (fe.edit.data.currentNode.pageFlag == "rootFolder") {
		window.location.href = "/zip?id=" + fe.edit.data.id;
	} else {
		var parents = fe.tool.getTreeNodeParents(treeNode);
		parents[parents.length - 1] = '';
		var path = parents.join('/');
		window.location.href = "/zipTree?id=" + fe.edit.data.id + "&treeid=" + treeNode.id + "&path=" + path;
	}
}

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
	if (fe.edit.data.currentNode.pageFlag.indexOf('Folder') == -1) {
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
			data: {'isFolder': 0, 'name': check.fullName, 'iconSkin': 'page'},
			success: function (res) {
				$('.inputBox_cover').remove();
				fe.edit.zTreeObj.addNodes(treeNode, fe.edit.compareNode(check.fullName, treeNode.trees, "0"), {
					id: res.treeid,
					isFolder: 0,
					name: check.fullName,
					iconSkin: "page",
					trees: [],
					pages: [{
						id: res.pageid,
						multipleCode: '<style id="pageCss"></style><div class="sjx_html" style="height: 100%"><div class="sjx_additional_wrap" id="basicBody" data-is-container="1" style="height: 100%;overflow: auto;box-sizing: border-box;" ondragover="fe.drag.elemDragOver(event,this)" ondrop="fe.drag.elemDrop(event,this)" ondragleave="fe.drag.elemDragLeave(event,this)"><div class="sjx_additional_header"><span class="sjx_additional_icon">container</span></div><div class="sjx_additional_body"><div class="sjx_body"></div></div></div></div>'
					}]
				});
				var newTreeNode = fe.edit.zTreeObj.getNodesByParam('id', res.treeid, treeNode)[0];
				fe.edit.zTreeObj.selectNode(newTreeNode);
				fe.edit.initPage(newTreeNode);
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
			data: {'isFolder': 1, 'name': check.fullName, 'iconSkin': 'folder'},
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

fe.edit.initPage = function (treeNode) {
	if (fe.edit.data.currentNode.tId) {
		fe.edit.savePage();
	}
	fe.edit.initElem();
	fe.edit.initElemAttr();
	fe.edit.initElemStyle();
	$('#editMain').html('<style id="resetCss">' + fe.edit.data.styles[0].pageResetCssCode + '</style>' + treeNode.pages[0].multipleCode);
	fe.edit.data.currentPage.id = treeNode.pages[0].id;
	fe.edit.data.currentNode.tId = treeNode.tId;
};

fe.edit.treeCallback = {
	//右键点击事件
	zTreeOnRightClick: function (event, treeId, treeNode) {
		//alert(treeNode.isFolder);
		$('.edit_tree_rightMenu').remove();
		var aRightMenu = ['新建文件夹', '新建页面', '重命名', '删除', '导出'];
		if (treeNode.isFolder == "1") {
			switch (true) {
				case treeNode.tId == "tree_1":
					aRightMenu.splice(2, 2);
					fe.edit.data.currentNode.pageFlag = "rootFolder";
					break;
				case treeNode.parentTId == "tree_1" && treeNode.name == "image":
					aRightMenu[0] = "上传图片";
					aRightMenu.length = 1;
					fe.edit.data.currentNode.pageFlag = "imagesFolder";
					break;
				default :
					fe.edit.data.currentNode.pageFlag = "normalFolder";
					break;
			}
		} else {
			aRightMenu.splice(0, 2);
			if (treeNode.iconSkin == 'img') {
				fe.edit.data.currentNode.pageFlag = "picPage";
				aRightMenu.pop();
			} else {
				fe.edit.data.currentNode.pageFlag = "htmlPage";
			}
		}
		var menuHtml = '<ul class="edit_tree_rightMenu" style="position: absolute;left:' + event.clientX + 'px;top:' + event.clientY + 'px;"><li>' + aRightMenu.join('</li><li>') + '</li></ul>';
		$(menuHtml).appendTo('body').data('treeNode', treeNode);
	},

	zTreeOnClick: function (event, treeId, treeNode) {
		if (treeNode.iconSkin == "page") {
			fe.edit.initPage(treeNode);
		}
	}
};

fe.edit.elemEvent = function () {
	//获取元素
	$('.edit_element_main').delegate('.edit_element_title', 'click', function () {
		var icon = $(this).find('.tool_icon');
		var content = $(this).siblings('.edit_element_content');
		var id = $(this).parent().data('id');
		if (icon.hasClass('icon-caret-right')) {
			icon.removeClass('icon-caret-right').addClass('icon-caret-down');
			if (!content.find('.tool_element_item').size()) {
				getElementData(id, content);
			}
			content.slideDown();
		} else {
			icon.removeClass('icon-caret-down').addClass('icon-caret-right');
			content.slideUp();
		}
	});

	function getElementData(id, content) {
		fe.tool.getJSON({
			url: "elementData" + id + ".json",
			data: {'id': id},
			success: function (res) {
				for (var i = 0, len = res.data.length; i < len; i++) {
					$('<div class="tool_element_item" id="basic' + fe.tool.getRandomNumber() + '" draggable="true" ondragstart="fe.drag.elemDragStart(event,this)"><span>&lt;' + res.data[i].icon + '&gt;</span> <h4>' + res.data[i].name + '</h4></div>')
						.appendTo(content)
						.data({
							"id": res.data[i].id,
							"code": res.data[i].code,
							"icon": res.data[i].icon,
							"isContainer": res.data[i].isContainer,
							"isBlock": res.data[i].isBlock
						});
				}
			},
			error: {
				remind: "获取组件数据失败"
			}
		});
	}

	$('.edit_body_main').delegate('.sjx_additional_wrap', 'mousemove', function (ev) {
		$(this).css({
			"borderColor": "#FF9900"
		});
		ev.stopPropagation();
	});

	$('.edit_body_main').delegate('.sjx_additional_wrap', 'mouseout', function () {
		$(this).css({
			"borderColor": "#ccc"
		});
	});

	$('.edit_body_main').delegate('.sjx_additional_removeBtn', 'click', function (ev) {
		$(this).parents('.sjx_additional_wrap').eq(0).remove();
		ev.stopPropagation();
	});


	$('.edit_body_main').delegate('.sjx_additional_wrap', 'click', function (ev) {
		fe.edit.initElem(this.id);
		fe.edit.initElemAttr();
		fe.edit.initElemStyle();
		fe.edit.showElemAttr();
		fe.edit.showElemStyle();
		ev.stopPropagation();
	});
};

fe.edit.initElem = function (id) {
	$('#editMain').find('.sjx_additional_header').css('backgroundColor', '#ccc');
	$("#" + id).find('.sjx_additional_header').eq(0).css('backgroundColor', '#FF9900');
	fe.edit.data.currentElem.wrapId = id;
	fe.edit.data.currentElem.parent = $("#" + id).find('.sjx_additional_body');
	fe.edit.data.currentElem.self = fe.edit.data.currentElem.parent.children().eq(0);
}

fe.edit.initElemAttr = function () {
	$('#elemAttrBtn').addClass('active').siblings().removeClass('active');
	$('#elemAttr').show();
	$('#elemCode').hide();
	$('#elemAttr').find('.tool_text').val('');
	fe.edit.data.currentElem.oAttr = {};
};

fe.edit.initElemStyle = function () {
	$('.edit_styleSetting_name').val('');
	$('#edit_styleSettingCode').val('');
	fe.edit.data.currentElem.className = '';
	fe.edit.data.currentElem.oClass = {};
}

fe.edit.showElemAttr = function () {
	$('#elemTarget').html(fe.edit.data.currentElem.self.get(0).nodeName.toLowerCase());
	fe.edit.data.currentElem.oAttr = fe.tool.match(fe.edit.data.currentElem.parent.html());
	fe.edit.setElemAttr(fe.edit.data.currentElem.oAttr);
};

fe.edit.setElemAttr = function (oAttr) {
	var attrSettingItem = $('.edit_attrSetting_item').find('input').val('').end();
	var len = attrSettingItem.length;
	var count = 0;
	for (var item in oAttr) {
		var arr = [item, oAttr[item]];
		if (count++ < len - 1) {
			attrSettingItem.eq(count).find('input').each(function (i) {
				$(this).val(arr[i]);
			});
		} else {
			$('.edit_attrSetting_body').append('<div class="edit_attrSetting_item"> <div class="edit_attrSetting_item_title"> <input type="text" class="tool_text" value="' + arr[0] + '"/> </div> <div class="edit_attrSetting_item_description"> <input type="text" class="tool_text" value="' + arr[1] + '"/> </div> </div>')
		}
	}
};

fe.edit.styleEvent = function () {

	$('#pageStyle .icon-eye-open').on('click', function () {
		if ($('#edit_styleSettingCode').val()) {
			var pageCssCode = fe.edit.formatPageCssCode($('#edit_styleSettingCode').val());
			$('#edit_styleSettingCode').val(pageCssCode);
		}
	});

	$('#edit_styleSettingCode').on('keyup', function () {
		var className = $('.edit_styleSetting_name').val();
		var classCodntent = $('#edit_styleSettingCode').val();
		classCodntent = removeSpace(classCodntent);
		if (!className) {
			$.remindBox({
				remind: "请先输入类名"
			});
		} else {
			fixClassContent(classCodntent);
			if (fe.edit.data.currentElem.className) {
				if (className == fe.edit.data.currentElem.className) {
					refreshClass(className, classCodntent);
				} else {
					deleteClass(fe.edit.data.currentElem.className);
					addClass(className, classCodntent);
				}
			} else {
				addClass(className, classCodntent);
			}
			fe.edit.data.currentElem.className = className;
			$('#pageCss').html(getLastPageStyle());
		}
	});

	function fixClassContent(classCodntent) {
		initElem();
		var oStyle = markObject(classCodntent);
		for (var item in oStyle) {
			switch (true) {
				case item.indexOf('width') != -1:
					fixForWidth();
					break;
				case item.indexOf('position') != -1:
					if (oStyle[item] == "absolute" || oStyle[item] == "fixed") {
						fixForPosition(oStyle[item]);
					}
					break;
				case item.indexOf('overflow') != -1 || item.indexOf('float') != -1 || item.indexOf('top') != -1 || item.indexOf('right') != -1 || item.indexOf('bottom') != -1 || item.indexOf('left') != -1:
					fixInParent(item, oStyle[item]);
					break;
			}
		}
	}

	function initElem() {
		if (fe.edit.data.currentElem.self.hasClass('pos')) {
			fe.edit.data.currentElem.self.removeClass('pos');
		}
		$('#' + fe.edit.data.currentElem.wrapId).removeAttr('style');
	}

	function removeSpace(classCodntent) {
		var format = new formathtmljscss(classCodntent, 2, 'format');
		format.removeSpace();
		return format.source;
	}

	function markObject(classCodntent) {
		var arr = classCodntent.split(';')
		var obj = {}
		for (var i = 0, len = arr.length - 1; i < len; i++) {
			var arrSec = arr[i].split(':');
			obj[arrSec[0]] = arrSec[1];
		}
		return obj;
	}

	function fixForWidth() {
		$('#' + fe.edit.data.currentElem.wrapId).css('display', 'inline-block');
	}

	function fixForPosition(value) {
		$('#' + fe.edit.data.currentElem.wrapId).css('position', value);
		if (!fe.edit.data.currentElem.self.hasClass('pos')) {
			fe.edit.data.currentElem.self.addClass('pos');
		}
	}

	function fixInParent(attr, value) {
		$('#' + fe.edit.data.currentElem.wrapId).css(attr, value);
	}

	function addClass(className, classCodntent) {
		fe.edit.data.currentElem.oClass[className] = classCodntent;
		fe.edit.data.currentElem.self.addClass(className);
	}

	function deleteClass(className) {
		fe.edit.data.currentElem.oClass[className] = 'delete';
		fe.edit.data.currentElem.self.removeClass(className);
	}

	function refreshClass(className, classCodntent) {
		fe.edit.data.currentElem.oClass[className] = classCodntent;
	}

	function getLastPageStyle() {
		var oClass = fe.edit.data.currentElem.oClass;
		var pageStyleCode = [];
		for (var item in oClass) {
			if (oClass[item].indexOf('delete') == -1) {
				pageStyleCode.push('.' + item + '{' + oClass[item] + '}');
			}
		}
		return pageStyleCode.join('');
	}
};

fe.edit.showElemStyle = function () {
	var className = fe.edit.data.currentElem.self.attr('class');
	className = getClassName(className);
	fe.edit.data.currentElem.oClass = fe.edit.getPageClass($('#pageCss').html());
	if (className) {
		fe.edit.data.currentElem.className = className;
		if (fe.edit.data.currentElem.oClass[className]) {
			var styleCode = fe.edit.data.currentElem.oClass[className];
			$('.edit_styleSetting_name').val(className);
			$('#edit_styleSettingCode').val(fe.edit.formatPageCssCode(styleCode));
		}
	}

	function getClassName(className) {
		if (className) {
			var arr = className.replace(/^\s+|\s+$/, '').split('\s+');
			for (var i = 0, len = arr.length; i < len; i++) {
				if (arr[i] != "pos") {
					return arr[i];
				}
			}
		}
		return '';
	}
};

fe.edit.formatPageCssCode = function (styleCode) {
	var format = new formathtmljscss(styleCode, 2, 'format');
	format.removeSpace();
	return format.source.trim().replace(/;|:/g, function (match) {
		switch (match) {
			case ':':
				return ': ';
			case ';':
				return ';\n';
		}
	});
}

fe.edit.getPageClass = function (sHtml) {
	var obj = {}
	var arr = [];
	if (sHtml) {
		sHtml.replace(/\.([\w-_]+)/g, function ($0, $1) {
			arr.push($1);
			return $0;
		}).replace(/\{([^\}]+)\}/g, function ($0, $1) {
			arr.push($1);
			return $0;
		});
		for (var i = 0, len = arr.length / 2; i < len; i++) {
			obj[arr[i]] = arr[len + i];
		}
	}
	return obj;
}

fe.edit.getRelativePath = function (treeNode) {
	var parents = fe.tool.getTreeNodeParents(treeNode);
	var relativePath = '';
	if (parents.length != 1) {
		parents.shift();
		var count = 0;
		while (++count < parents.length) {
			relativePath += "../";
		}
	}
	return relativePath;
};

fe.edit.getPageCssCode = function () {
	var pageCss = '';
	if ($('#editMain').find("#pageCss").html()) {
		pageCss = $('#editMain').find("#pageCss").html();
	}
	return pageCss;
}

fe.edit.getDownloadCode = function (currentNode) {
	var relativePath = fe.edit.getRelativePath(currentNode);
	var pageCss = fe.edit.getPageCssCode();
	var simplePage = $('#editMain').clone().find('#resetCss').remove().end()
		.find('.sjx_additional_header').remove().end()
		.find('.sjx_additional_body').each(function () {
			$(this).children().eq(0).unwrap().unwrap();
		}).end();
	return '<html lang="zh-cn">' +
		'<head>' +
		'<meta charset="UTF-8">' +
		'<title>' + fe.edit.data.name + '</title>' +
		'<link rel="stylesheet" href="' + relativePath + 'css/reset.css">' +
		'<style id="pageCss">' + pageCss + '</style>' +
		'</head>' +
		'<body>' + simplePage.find(".sjx_body").html() + '</body>' +
		'</html>';
};

fe.edit.savePage = function (remind, callback) {
	if (fe.edit.data.currentNode.tId) {
		var currentNode = fe.edit.zTreeObj.getNodeByTId(fe.edit.data.currentNode.tId);
		fe.edit.data.currentPage.downloadCode = fe.edit.getDownloadCode(currentNode);
		fe.edit.data.currentPage.multipleCode = $('#editMain').clone().find('#resetCss').remove().end().html();
		fe.tool.getJSON({
			url: "data.json",
			data: {
				'id': fe.edit.data.currentPage.id,
				'multipleCode': fe.edit.data.currentPage.multipleCode,
				'downloadCode': fe.edit.data.currentPage.downloadCode
			},
			success: function () {
				currentNode.pages[0].multipleCode = fe.edit.data.currentPage.multipleCode;
				fe.edit.zTreeObj.updateNode(currentNode);
				if (remind) {
					$.topTip({
						content: "成功保存页面"
					});
				}
				if (callback) {
					callback();
				}
			},
			error: {
				remind: "保存页面失败"
			}
		});
	} else {
		$.remindBox({
			'remind': "请先进入相应的页面"
		});
	}
};

fe.edit.operationEvent = function () {
	$('#edit_saveBtn').on('click', function () {
		if (fe.edit.data.currentNode.tId) {
			if ($(this).attr('disabled')) {
				return false;
			} else {
				fe.edit.savePage(true);
			}
		} else {
			$.remindBox({
				remind: "请先进入相应页面，再执行保存操作"
			});
		}
	});

	var flag = true;
	$('#edit_previewBtn').on('click', function () {
		if (fe.edit.data.currentNode.tId) {
			var that = this,
				time = 300;
			if (!$(that).attr('disabled') && flag) {
				flag = false;
				if (that.innerHTML == "预览") {
					//fe.edit.saveElemAttr();  //预览前先保存操作元素的属性
					//fe.edit.saveElemCode();  //预览前先保存操作元素的代码
					fe.edit.data.currentPage.multipleCode = $('#editMain').html();
					reduceCode();
					$('#edit_saveBtn').attr('disabled', "disabled");
					$('#edit_previewLayoutBtn').attr('disabled', "disabled");
					$('.edit_body_leftAside').animate({
						marginLeft: -265
					}, time);
					$('.edit_body_rightAside').animate({
						marginRight: -250
					}, time);
					setTimeout(function () {
						that.innerHTML = "返回编辑";
						flag = true;
					}, time);
				} else {
					$('#editMain').empty().html(fe.edit.data.currentPage.multipleCode);
					//由于editMain重新填充内容，所以需重新指定fe.edit.data.currentElem.parent及fe.edit.data.currentElem.self
					fe.edit.data.currentElem.parent = $("#" + fe.edit.data.currentElem.wrapId).find('.sjx_additional_body');
					fe.edit.data.currentElem.self = fe.edit.data.currentElem.parent.children().eq(0);
					$('.edit_body_leftAside').animate({
						marginLeft: 0
					}, time);
					$('.edit_body_rightAside').animate({
						marginRight: 0
					}, time);
					setTimeout(function () {
						that.innerHTML = "预览";
						$('#edit_saveBtn').removeAttr('disabled');
						$('#edit_previewLayoutBtn').removeAttr('disabled');
						flag = true;
					}, time);
				}
			}
		} else {
			$.remindBox({
				remind: "请先进入相应页面，再执行预览操作"
			});
		}
	});


	var layoutFlag = true;
	$('#edit_previewLayoutBtn').on('click', function () {
		if (fe.edit.data.currentNode.tId) {
			var that = this,
				time = 300;
			if (!$(that).attr('disabled') && layoutFlag) {
				layoutFlag = false;
				if (that.innerHTML == "预览布局") {
					//fe.edit.saveElemAttr();  //预览布局前先保存操作元素的属性
					//fe.edit.saveElemCode();  //预览布局前先保存操作元素的代码
					$('#edit_saveBtn').attr('disabled', "disabled");
					$('#edit_previewBtn').attr('disabled', "disabled");
					$('.edit_body_leftAside').animate({
						marginLeft: -265
					}, time);
					$('.edit_body_rightAside').animate({
						marginRight: -250
					}, time);
					setTimeout(function () {
						that.innerHTML = "返回编辑";
						layoutFlag = true;
					}, time);
				} else {
					$('.edit_body_leftAside').animate({
						marginLeft: 0
					}, time);
					$('.edit_body_rightAside').animate({
						marginRight: 0
					}, time);
					setTimeout(function () {
						that.innerHTML = "预览布局";
						$('#edit_saveBtn').removeAttr('disabled');
						$('#edit_previewBtn').removeAttr('disabled');
						layoutFlag = true;
					}, time);
				}
			}
		} else {
			$.remindBox({
				remind: "请先进入相应页面，再执行预览布局操作"
			});
		}
	});

	function reduceCode() {
		$('.sjx_additional_header').remove();
		$('.sjx_additional_body').each(function () {
			$(this).children().eq(0).unwrap().unwrap();
		});
	}

	$('#edit_showPageCode').on('click', function () {
		if (fe.edit.data.currentNode.tId) {
			var currentNode = fe.edit.zTreeObj.getNodeByTId(fe.edit.data.currentNode.tId);
			var downloadCode = fe.edit.getDownloadCode(currentNode).replace(/<style id="pageCss">([^<^>]*)<\/style>/, '').replace(/<link rel="stylesheet" href="([^<^>]*)css\/reset\.css">/g, function ($0, $1) {
				return $0 + '<link rel="stylesheet" href="' + $1 + 'css/page.css">';
			});
			fe.edit.formatHtmlCode(downloadCode, $('.edit_showHtmlCode'));
			fe.edit.formatCssCode($('#pageCss').html(), $('.edit_showPageCss'));
			fe.edit.formatCssCode(fe.edit.data.styles[0].code, $('.edit_showResetCss'));
			$('#edit_pageCodeCover').show();
			$('#edit_pageCodeCover .tool_editor_tab_item:eq(0)').addClass('active').siblings().removeClass('active');
			$('#edit_pageCodeCover .tool_editor_body').children().eq(0).show().siblings().hide();
		} else {
			$.remindBox({
				remind: "请选进入相应的页面",
			});
		}
	});

	$('#edit_showResetStyle').on('click', function () {
		$('#edit_resetStyleCover').show();
		$('#edit_resetStyleCover .tool_editor_tab_item:eq(0)').addClass('active').siblings().removeClass('active');
		$('#edit_resetStyleCover .tool_editor_body').children().eq(0).show().siblings().hide();
		fe.edit.formatCssCode(fe.edit.data.styles[0].code, $('.edit_showResetStyle'));
	});
};

fe.edit.pageCodeEvent = function () {
	$('#edit_pageCodeCover').find('.icon-remove').on('click', function () {
		$('#edit_pageCodeCover').hide();
	});

	$('#edit_pageCodeCover .tool_editor_tab').delegate('.tool_editor_tab_item', 'click', function () {
		$(this).addClass('active').siblings().removeClass('active');
		$('#edit_pageCodeCover .tool_editor_body').children().eq($(this).index()).show().siblings().hide();
	});

	$('#edit_pageCode_downloadBtn').on('click', function () {
		if (fe.edit.data.currentNode.tId) {
			fe.edit.download();
		}
	});
};

fe.edit.resetStyleEvent = function () {
	$('#edit_resetStyleCover').find('.icon-remove').on('click', function () {
		saveResetCss();
		refleshPage();
		$('#edit_resetStyleCover').hide();
	});

	$('#edit_resetStyleCover .tool_editor_tab').delegate('.tool_editor_tab_item', 'click', function () {
		$(this).addClass('active').siblings().removeClass('active');
		if (this.innerHTML == "编辑") {
			$('#editResetStyle').val(fe.tool.formatCss(fe.edit.data.styles[0].code));
			$('#edit_resetStyleCover .tool_editor_body').children().eq(1).show().siblings().hide();
		} else {
			saveResetCss();
			fe.edit.formatCssCode(fe.edit.data.styles[0].code, $('.edit_showResetStyle'));
			$('#edit_resetStyleCover .tool_editor_body').children().eq(0).show().siblings().hide();
		}
	});

	function saveResetCss() {
		fe.edit.data.styles[0].code = $('#editResetStyle').val();
		fe.edit.data.styles[0].pageResetCssCode = fe.edit.data.styles[0].code.replace(/html|body/g, '.sjx_$&');
		fe.tool.getJSON({
			url: "data.json",
			data: {'id': fe.edit.data.styles[0].id, 'code': fe.edit.data.styles[0].code, 'name': "reset.css"},
			success: function () {

			},
			error: {
				remind: "更新全局样式表出错"
			}
		});
	}

	function refleshPage() {
		if (fe.edit.data.currentNode.tId) {
			var treeNode = fe.edit.zTreeObj.getNodeByTId(fe.edit.data.currentNode.tId);
			$('#editMain').html('<style id="resetCss">' + fe.edit.data.styles[0].pageResetCssCode + '</style>' + treeNode.pages[0].multipleCode);
		}
	}
};

fe.edit.formatHtmlCode = function (sHtml, parent) {
	parent.find('.snippet-container').remove();
	$('<pre>' + fe.tool.replaceHtml(HTMLFormat(sHtml, '&nbsp;&nbsp;')) + '</pre>').appendTo(parent)
		.snippet("HTML", {
			style: "fe",
			showNum: false,
			startCollapsed: false,
			menu: false
		});
}

fe.edit.formatCssCode = function (style, parent) {
	var styleCode = fe.tool.formatCss(style);
	parent.find('.snippet-container').remove();
	$('<pre>' + styleCode + '</pre>').appendTo(parent)
		.snippet("CSS", {
			style: "fe",
			showNum: false,
			startCollapsed: false,
			menu: false
		});
}

fe.edit.elemAttrEvent = function () {
	var isEditCode = false;
	$('#elemSourceCodeBtn').on('click', function () {
		$(this).addClass('active').siblings().removeClass('active');
		$('#elemCode').show().children().eq(1).show().siblings().hide();
		$('#elemAttr').hide();
		var editIcon = $('.edit_showSourceCode').find('.icon-edit').show();
		if (fe.edit.data.currentElem.parent) {
			if ($("#" + fe.edit.data.currentElem.wrapId).data('isContainer') == 0) {
				if (isEditCode) {
					enterEditStatus();
				} else {
					fe.edit.formatHtmlCode(fe.edit.data.currentElem.parent.html(), $('.edit_showSourceCode_body'));
				}
			} else {
				editIcon.hide();
				fe.edit.formatHtmlCode(getSimpleCode(fe.edit.data.currentElem.parent.clone()), $('.edit_showSourceCode_body'));
			}
		}
	});

	function getSimpleCode(parent) {
		parent.find('.sjx_additional_header').remove();
		parent.find('.sjx_additional_body').each(function () {
			$(this).children().eq(0).unwrap().unwrap();
		});
		return parent.html();
	}

	function enterEditStatus() {
		isEditCode = true;
		$('#elemSourceCode').val(HTMLFormat(fe.edit.data.currentElem.parent.html(), '     '));
		$('.edit_showSourceCode').hide();
		$('.edit_editSourceCode').show();
	}

	function exitEditStatus() {
		isEditCode = false;
		fe.edit.formatHtmlCode(fe.edit.data.currentElem.parent.html(), $('.edit_showSourceCode_body'));
		$('.edit_editSourceCode').hide();
		$('.edit_showSourceCode').show();
	}

	$('.edit_sourceSetting_header .icon-edit').on('click', function () {
		if (fe.edit.data.currentElem.parent) {
			enterEditStatus();
		} else {
			$.remindBox({
				remind: "请先选择需要操作的组件"
			});
		}
	});

	$('.edit_sourceSetting_header .icon-circle-arrow-left').on('click', exitEditStatus);

	$('.edit_sourceSetting_header .icon-eye-open').on('click', function () {
		$('#elemSourceCode').val(HTMLFormat(fe.edit.data.currentElem.parent.html(), '     '));
	});

	$('.edit_sourceSetting_header .icon-fullscreen').on('click', function () {
		if (fe.edit.data.currentElem.parent) {
			alert("全屏");
		} else {
			$.remindBox({
				remind: "请先选择需要操作的组件"
			});
		}
	});

	$('#elemSourceCode').on('keyup', fe.edit.saveElemCode);
	$('#elemAttrBtn').on('click', function () {
		$(this).addClass('active').siblings().removeClass('active');
		$('#elemAttr').show();
		$('#elemCode').hide();
		if (fe.edit.data.currentElem.parent) {
			fe.edit.setElemAttr(fe.tool.match(fe.edit.data.currentElem.parent.html()));
		}
	});

	$('.edit_attrSetting_body').delegate('.tool_text', 'keyup', fe.edit.saveElemAttr);

	$('#elemAttr .icon-plus-sign').on('click', function () {
		if (fe.edit.data.currentElem.parent) {
			$('.edit_attrSetting_body').append('<div class="edit_attrSetting_item"> <div class="edit_attrSetting_item_title"> <input type="text" class="tool_text"/> </div> <div class="edit_attrSetting_item_description"> <input type="text" class="tool_text"/> </div> </div>');
		} else {
			$.remindBox({
				remind: "请先选择需要操作的组件"
			});
		}
	});

};

fe.edit.saveElemCode = function () {
	if (fe.edit.data.currentElem.parent && $("#" + fe.edit.data.currentElem.wrapId).data('isContainer') == 0) {
		fe.edit.data.currentElem.parent.html($('#elemSourceCode').val());
	}
};

fe.edit.saveElemAttr = function () {
	if (fe.edit.data.currentElem.self) {
		var newAttr = {};
		var item = '';
		var removeAttr = [];
		$('.edit_attrSetting_item').find('input').each(function (i) {
			if (i % 2 == 0) {
				if ($(this).val()) {
					item = $(this).val();
				} else {
					item = ""
				}
			} else {
				if (item) {
					newAttr[item] = $(this).val();
				}
			}
		});

		for (var item in fe.edit.data.currentElem.oAttr) {
			if (newAttr[item] == undefined) {
				removeAttr.push(item);
			}
		}

		removeElemAttr(removeAttr);
		resetElemAttr(newAttr);
		fe.edit.data.currentElem.oAttr = newAttr;

		function removeElemAttr(aAttr) {
			for (var i = 0; i < aAttr.length; i++) {
				var result = fe.edit.data.currentElem.self.removeAttr(aAttr[i]);
				if (!result) {
					fe.edit.data.currentElem.self.removeProp(aAttr[i]);
				}
			}
		}

		function resetElemAttr(newAttr) {
			for (var item in newAttr) {
				var result = '';
				if (newAttr[item]) {
					result = fe.edit.data.currentElem.self.attr(item, newAttr[item]);
					if (!result) {
						fe.edit.data.currentElem.self.prop(item, newAttr[item]);
					}
				} else {
					result = fe.edit.data.currentElem.self.attr(item, item);
					if (!result) {
						fe.edit.data.currentElem.self.prop(item, item);
					}
				}
			}
		}

	}
};

fe.drag = {
	childrenIds: [],
	elemDragStart: function (ev, that) {
		var dt = ev.dataTransfer;
		dt.effectAllowed = 'all';
		dt.setData('id', that.id);
		fe.drag.childrenIds.length = 0;
		fe.drag.childrenIds.push(that.id);
		$(that).find('.sjx_additional_wrap').each(function (i, elem) {
			fe.drag.childrenIds.push(elem.id);
		});
		$(that).css({
			"borderColor": "#ccc"
		});
		ev.stopPropagation();
	},
	elemDragOver: function (ev, that) {
		var flag = true;
		if ($(that).data('isContainer') == "0") {
			flag = false;
		} else {
			for (var i = 0, len = fe.drag.childrenIds.length; i < len; i++) {
				if (fe.drag.childrenIds[i] == that.id) {
					flag = false;
					break;
				}
			}
		}
		if (!flag) {
			ev.dataTransfer.dropEffect = 'none';
		} else {
			$(that).css({
				"borderColor": "#FF9900"
			});
		}
		ev.preventDefault();
		ev.stopPropagation();
	},
	elemDragLeave: function (ev, that) {
		$(that).css({
			"borderColor": "#ccc"
		});
	},
	elemDrop: function (ev, that) {
		var parent = $(that).find('.sjx_additional_body:eq(0)').children().eq(0),
			elemId = ev.dataTransfer.getData('id');
		var newElemId = elemId;
		var insertId = getInsertId(ev.pageY, parent);
		if (elemId.indexOf("new") != -1) {
			if (insertId) {
				$('#' + newElemId).insertBefore($('#' + insertId));
			} else {
				$('#' + newElemId).appendTo(parent);
			}
		} else {
			newElemId = 'new' + fe.tool.getRandomNumber();
			var newElem = createNewElem(newElemId);
			if (insertId) {
				newElem.insertBefore($('#' + insertId));
			} else {
				newElem.appendTo(parent);
			}
		}
		$(that).css({
			"borderColor": "#ccc"
		});
		fe.edit.initElem(newElemId);
		fe.edit.initElemAttr();
		fe.edit.initElemStyle();
		fe.edit.showElemAttr();
		fe.edit.showElemStyle();
		ev.preventDefault();
		ev.stopPropagation();

		function createNewElem(nweElemId) {
			var newElem = null;
			if ($('#' + elemId).data('isBlock') == "1") {
				newElem = $('<div class="sjx_additional_wrap" id="' + nweElemId + '" draggable="true" data-is-container="' + $('#' + elemId).data('isContainer') + '"  ondragstart="fe.drag.elemDragStart(event,this)" ondragover="fe.drag.elemDragOver(event,this)" ondrop="fe.drag.elemDrop(event,this)" ondragleave="fe.drag.elemDragLeave(event,this)">' +
					'<div class="sjx_additional_header">' +
					'<span class="sjx_additional_icon">' + $('#' + elemId).data('icon') + '</span><i class="tool_icon icon-remove sjx_additional_removeBtn"></i>' +
					'</div>' +
					'<div class="sjx_additional_body">' +
					$('#' + elemId).data('code') +
					'</div>');
			} else {
				newElem = $('<span class="sjx_additional_wrap" style="display:inline-block;margin-right:5px;" id="' + nweElemId + '" draggable="true" data-is-container="' + $('#' + elemId).data('isContainer') + '"  ondragstart="fe.drag.elemDragStart(event,this)" ondragover="fe.drag.elemDragOver(event,this)" ondrop="fe.drag.elemDrop(event,this)" ondragleave="fe.drag.elemDragLeave(event,this)">' +
					'<div class="sjx_additional_header">' +
					'<span class="sjx_additional_icon">' + $('#' + elemId).data('icon') + '</span><i class="tool_icon icon-remove sjx_additional_removeBtn"></i>' +
					'</div>' +
					'<div class="sjx_additional_body">' +
					$('#' + elemId).data('code') +
					'</span>');
			}
			return newElem;
		}

		function getInsertId(pageY, parent) {
			var aTop = [],
				insertId = null;
			parent.children('.sjx_additional_wrap').each(function (i, elem) {
				aTop.push({
					top: $(this).offset().top,
					id: this.id
				});
			});
			for (var i = 0, len = aTop.length; i < len; i++) {
				if (pageY <= aTop[i].top) {
					insertId = aTop[i].id;
					break;
				}
			}
			return insertId;
		}
	},
};
