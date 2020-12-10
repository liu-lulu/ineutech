var ParamSettings = function() {

	var $valInput = $("#paramValueInput");
	var $phoneInput = $("#terminalPhone");
	var initRadioCheckEvent = function() {
		$(":input[name='paramType']").change(function() {
			$valInput.val($(this).attr('data-demo'));
			$("#settings-body").find("span.tips-name").text($(this).attr('data-name'));
			$("#settings-body").find("span.tips-id").text($(this).attr('data-id'));
			$("#settings-body").find("span.tips-type").text($(this).attr('data-type'));
		});
	}

	var initSessionItemClickEvent = function() {
		$("a.sessionItem").click(function() {
			if ($(this).hasClass('disabled'))
				return false;
			$("a.sessionItem.active").removeClass('active');
			$(this).addClass('active');
			$phoneInput.val($(this).attr('data-phone'));
			return false;
		});
	}

	var initRefreshTerminalsBtnEvent = function() {
		$("#refreshTerminalsBtn").click(function() {
			$.get("terminal/list.do", {}, function(data) {
				buildSessionItems(data);
			}, "JSON");
			return false;
		});
	}
	function buildSessionItems(data) {
		console.info(data);
		$("#sessionItems a.sessionItem").remove();
		$(data).each(function() {
			var el = "";
			if (this.authenticated) {
				el = $('<a href="#" data-phone="' + this.terminalPhone + '" class="list-group-item sessionItem">' + this.terminalPhone + '</a>');
			} else {
				el = $('<a class="list-group-item sessionItem disabled">/' + this.remoteAddr.address + ':' + this.remoteAddr.port + '&nbsp;<span class="label label-danger">未鉴权</span></a>');
			}
			$("#sessionItems").append(el);
		});
		initSessionItemClickEvent();
	}

	var initSubmitEvent = function() {
		$("#submitBtn").click(function() {
			if (!$("#terminalPhone").val()) {
				alert("填写手机号");
				return false;
			}
			if (!$("#paramValueInput").val()) {
				alert("填写参数值");
				return false;
			}
			var params = {
				id : $(":input[name='paramType']:checked").attr('data-id'),
				phone : $("#terminalPhone").val(),
				values : $("#paramValueInput").val()
			};
			$.post("terminal/" + params.phone + ".do", params, function(data) {
				if (data.success) {
					alert("命令发送成功");
				} else {
					alert(data.msg);
				}
			}, "JSON");
			return false;
		});
	}

	var timer = undefined;
	var timerIndex = 1;
	function startTimer() {
		var $btn = $("#queryTerminalParamsBtn");
		$btn.addClass("disabled");
		timer = setInterval(function() {
			$btn.text("查询终端参数 " + (timerIndex++) + " s")
		}, 1000);
	}

	function stopTimer() {
		$("#queryTerminalParamsBtn").removeClass("disabled");
		if (timer) {
			clearInterval(timer);
			timerIndex=1;
			timer = undefined;
		}
	}

	var initQueryBtnEvent = function() {
		var $btn = $("#queryTerminalParamsBtn");
		$btn.click(function() {
			if(timer != undefined){
				return false;
			}
			if (!$("#terminalPhone").val()) {
				alert("填写手机号");
				return false;
			}
			startTimer();
			var $t=$("#reslutText");
			var $tbl=$("#resultTable");
			$tbl.find("tbody").empty();
			
			var url = "terminal/params.do";
			$.get(url, {
				phone : $("#terminalPhone").val()
			}, function(data) {
				stopTimer();
				
				$("#tipsDiv").removeClass('sr-only');
				$t.text("");
				
				
				if(data.success){
					$(data.params).each(function(){
						var tr=$('<tr><td>'+this.cmd+'</td><td>'+this.value+'</td><td>'+this.hexValue+'</td></tr>');
						$tbl.find("tbody").append(tr);
					});
				}else{
					$t.text(data.msg);
				}
			}, "JSON");
			return false;
		});
	}

	return {
		init : function() {
			initRadioCheckEvent();
			initSessionItemClickEvent();
			initRefreshTerminalsBtnEvent();
			initSubmitEvent();
			initQueryBtnEvent();
		}
	};
}();

jQuery(document).ready(function() {
	ParamSettings.init();
});