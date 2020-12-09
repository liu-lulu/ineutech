$(function(b) {
	$("input[name='username']").focus();
	$("#loginform")
			.validate(
					{
						rules : {
							username : {
								required : true,
								remote : {
									url : "../user/searchByUserName.htm",
									type : "post",
									cache : false,
									dataType : "json",
									data : {
										username : function() {
											return $("#username").val()
										}
									}
								}
							},
							password : {
								required : true,
								remote : {
									url : "../user/checkUser.htm",
									type : "post",
									cache : false,
									dataType : "json",
									data : {
										username : function() {
											return $("#username").val()
										}
									}
								}
							},
							remember : {
								required : false
							}
						},
						messages : {
							username : {
								required : "用户名必填.",
								remote : "用户名不存在."
							},
							password : {
								required : "密码必填.",
								remote : "用户名或密码错误."
							}
						},
						submitHandler : function(a) {
							a.submit()
						}
					});
	$("#loginform input").keypress(function (e){
            if (e.which == 13) {
                if ($('#loginform').validate().form()) {
                	$('#loginform').submit();
                }
                return false;
            }
});

});