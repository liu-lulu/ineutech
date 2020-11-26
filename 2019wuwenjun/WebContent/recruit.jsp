<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<link rel="shortcut icon" href="img/icon.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css">
<title>第九届吴文俊人工智能科学技术奖颁奖典礼志愿者招募</title>
<style type="text/css">
	p {
    margin: 0 0 5px;
}
</style>
<script>

	$(function() {
		$('form').bootstrapValidator({

			message : 'This value is not valid',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				name : {
					validators : {
						notEmpty : {
							message : '用户名不能为空'
						}
					}
				},
				school : {
					validators : {
						notEmpty : {
							message : '学校不能为空'
						}
					}
				},
				phone : {
					verbose: false, //代表验证按顺序验证。验证成功才会下一个（验证成功才会发最后一个remote远程验证）
					validators : {
						notEmpty : {
							message : '手机号不能为空'
						},
	                     /* regexp: {
	                         regexp: /^1[3|5|8]{1}[0-9]{9}$/,
	                         message: '请输入正确的手机号码'
	                     } */
	                     phone: {
	                    	 country: 'CN',
	                    	 message: '请输入正确的电话号码'
                    	 }
                         ,remote: {
 		                	type: 'post',
 		                	url: 'check.do',
 		                	message: '该手机号已报名',
 		                	data:'',
 		                	delay: 1000
 		                }
					}
				},
				idcard : {
					verbose: false, //代表验证按顺序验证。验证成功才会下一个（验证成功才会发最后一个remote远程验证）
					validators : {
						notEmpty : {
							message : '身份证号不能为空'
						}
						
						/* ,
						creditCard: {
		                    message : '身份证号码格式错误'
		                } */
						,callback: {
                            message: '身份证号码格式错误',
                            callback: function (value, validator) {
                                if (!value) {
                                    return true
                                } else if (isCardNo(value)) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
		                ,remote: {
		                	type: 'post',
		                	url: 'check.do',
		                	message: '该身份证号已报名',
		                	data:'',
		                	delay: 1000
		                }
					}
				},
				creditcard : {
					validators : {
						notEmpty : {
							message : '银行卡号不能为空'
						},
						digits: {
							message: '银行卡号只能包含数字'
						},
						stringLength: {
							min: 16,
							max: 19,
							message: '银行卡号长度不能小于16位或超过19位'
						}
						
					}
				},
				bank : {
					validators : {
						notEmpty : {
							message : '开户行不能为空'
						}
					}
				}
			}
            ,submitHandler: function (validator, form, submitButton) {
            		$('form').submit();;
            }
		});
	}).on('success.form.bv', function(e) {//验证通过后会执行这个函数。
		e.preventDefault();
		var r=confirm("确定提交报名信息!");
    	if (r==true){
    		$.post($('form').attr('action'), $('form').serialize(), function(result) {
    			if(result=='success'){
    				alert("报名成功");
        			location.href="recruit.jsp";
    			}else{
    				alert("报名失败");
    				$(':submit').removeAttr("disabled");
    			}
    			});
    	}
    	else{
    		$(':submit').removeAttr("disabled");
    	}
    	
		
    });
	
	// 验证身份证号
    function isCardNo(card) {
        let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (reg.test(card) === false) {
            return false
        } else {
            return true
        }
    }
	
	//重写。避免微信弹窗带域名
   window.alert = function(name){
    	  var iframe = document.createElement("IFRAME");
    	  iframe.style.display="none";
    	  iframe.setAttribute("src", 'data:text/plain,');
    	  document.documentElement.appendChild(iframe);
    	  window.frames[0].window.alert(name);
    	  iframe.parentNode.removeChild(iframe);
    	 };

    	window.confirm = function (message) {
    	   var iframe = document.createElement("IFRAME");
    	   iframe.style.display = "none";
    	   iframe.setAttribute("src", 'data:text/plain,');
    	   document.documentElement.appendChild(iframe);
    	   var alertFrame = window.frames[0];
    	   var result = alertFrame.window.confirm(message);
    	   iframe.parentNode.removeChild(iframe);
    	   return result;
    	 }; 
</script>
</head>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<img width="100%" src="img/recruit.jpg" />
			<h2>
			</h2>
			
			<p class="MsoNormal" style="margin-bottom: 20px;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“吴文俊人工智能科学技术奖”由中国人工智能学会发起主办，是我国智能科学技术领域唯一以人民科学家、人工智能先驱、我国智能科学研究的开拓者和领军人、首届国家最高科学技术奖获得者、中国科学院院士、中国人工智能学会名誉理事长吴文俊先生命名，依托社会力量设立的科学技术奖，被誉为“中国智能科学技术最高奖”。
				
			</p>
			<p class="MsoNormal" style="margin-bottom: 20px;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>2019</span>中国人工智能产业年会<span>China AI Industry's Annual Meeting(CAIIAM 2019)</span>，作为吴文俊人工智能科学技术奖颁奖典礼的主题配套活动，集闭门研讨、荣誉表彰、高端论坛、产品展示、报告发布和项目路演等六大板块于一体，是国内权威性高、规模较大、品牌力强、行业影响深远的年度人工智能标志性盛会。
				
			</p>
			<p class="MsoNormal" style="margin-bottom: 20px;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为全面实施创新驱动发展战略，贯彻落实国家《新一代人工智能发展规划》，表彰获得<span>2019</span>年度吴文俊人工智能科学技术奖的学者与专家，促进人工智能技术在各行业领域赋能，大力提升我国智能科学技术创新与产业化发展水平，加快建设成为世界人工智能创新中心和应用高地，经研究，中国人工智能学会定于 <span>2019</span>年<span>11</span>月<span>29</span>日<span>—12</span>月<span>1</span>日在苏州隆重举办<span>“</span>第九届吴文俊人工智能科学技术奖颁奖典礼暨<span>2019</span>中国人工智能产业年会<span>”</span>。
				
			</p>
			<p class="MsoNormal" style="margin-bottom: 20px;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为做好大会期间近千名嘉宾的接待、服务、以及大会保障等工作，现特向各高校院所征集大会服务志愿者。
				
			</p>
			<p class="MsoNormal" style="margin-bottom: 20px;">
				相关重要事项如下：
				
			</p>
			<p class="MsoNormal">
				<B style="font-size: medium;">一、重要节点</B>
				
			</p>
			<p class="MsoNormal">
				<span>1</span>、征集时间： 即日起至<span>2019</span>年<span>11</span>月<span>18</span>日
				
			</p>
			<p class="MsoNormal">
				<span>2</span>、各校领队及骨干工作会： <span>2019</span>年<span>11月9日</span>（苏州）
				
			</p>
			<p class="MsoNormal">
				<span>3</span>、部分骨干先期现场办公：<span>2019</span>年<span>11</span>月<span>25</span>日（苏州）
				
			</p>
			<p class="MsoNormal" style="margin-bottom: 20px;">
				<span>4</span>、志愿者全体集结时间段： <span>2019</span>年<span>11</span>月<span>27</span>日<span>-12</span>月<span>1</span>日（苏州）
				
			</p>
			<p class="MsoNormal">
			</p>
			<p class="MsoNormal">
				<B style="font-size: medium;">二、招募原则</B>
				
			</p>
			<p class="MsoNormal" style="margin-bottom: 20px;">
				公开招募、自愿报名、择优选用、定岗服务。
				
			</p>
			<p class="MsoNormal">
			</p>
			<p class="MsoNormal">
				<B style="font-size: medium;">三、岗位设置</B>
				
			</p>
			<p class="MsoNormal" style="margin-bottom: 20px;">
				由各校领队及骨干工作会议讨论指派，并听从大会组委会统一调度
				
			</p>
			<p class="MsoNormal">
			</p>
			<p class="MsoNormal">
				<B style="font-size: medium;">四、志愿者福利</B>
				
			</p>
			<p class="MsoListParagraph">
				<span>1、</span>
				大会组委会将为每位志愿者按在岗时间统一发放劳务津贴
				
			</p>
			<p class="MsoListParagraph">
				<span>2、</span>
				大会组委会将统一提供志愿者在岗时间工作餐
				
			</p>
			<p class="MsoListParagraph" style="margin-bottom: 20px;">
				<span>3、</span>
				<span>本次大会结束后，由组委会评选优秀志愿者并颁发证书</span>
				
			</p>
			<p class="MsoListParagraph">
			</p>
			<p class="MsoNormal">
				联系人：
				
			</p>
			<p class="MsoNormal">
				钱学胜，<span>13916208229</span>， <span class="MsoHyperlink"><span><a href="mailto:Qianxuesheng@fudan.edu.cn">Qianxuesheng@fudan.edu.cn</a></span></span> 
				
			</p>
			
			
		</div>
	</div>
	<hr>
	<div class="row clearfix" style="margin-top: 20px;">
		<div class="col-md-12 column">
			<form class="form-horizontal" role="form" action="saveVolunteer.do" method="post" enctype="multipart/form-data">
				<div class="form-group" style="text-align: center;margin:20px 0;">
				    <label for="name" class="col-xs-12 control-label" style="font-size: large;text-align: center;">志愿者报名信息</label>
				</div>
				<div class="form-group">
				    <label for="name" class="col-xs-4 control-label" style="padding: 6px 12px;">姓名<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="name" name="name" placeholder="请输入姓名">
				    </div>
				</div>
				<div class="form-group">
				    <label for="sex" class="col-xs-4 control-label" style="padding: 6px 12px;">性别<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				     <label class="radio-inline">
				        <input type="radio" name="sex" value="男" checked>男
				    </label>
				    <label class="radio-inline">
				        <input type="radio" name="sex" value="女">女
				    </label>
				    </div>
				</div>
				<div class="form-group">
				    <label for="school" class="col-xs-4 control-label" style="padding: 6px 12px;">学校<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="school" name="school" placeholder="请输入学校">
				    </div>
				</div>
				<div class="form-group">
				    <label for="phone" class="col-xs-4 control-label" style="padding: 6px 12px;">手机<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入手机号码">
				    </div>
				</div>
				<div class="form-group">
				    <label for="degree" class="col-xs-4 control-label" style="padding: 6px 12px;">类型<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <select class="form-control" id="degree" name="degree">
				      <option value="本科">本科</option>
				      <option value="硕士">硕士</option>
				      <option value="博士">博士</option>
				      <option value="教师">教师</option>
				    </select>
				    </div>
				</div>
				<!-- <div class="form-group">
				    <label for="idcard" class="col-xs-4 control-label" style="padding: 6px 12px;">身份证号<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="idcard" name="idcard" placeholder="请输入身份证号">
				    </div>
				</div>
				<div class="form-group">
				    <label for="creditcard" class="col-xs-4 control-label" style="padding: 6px 12px;">银行卡号<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="creditcard" name="creditcard" placeholder="请输入银行卡号">
				    </div>
				</div>
				<div class="form-group">
				    <label for="bank" class="col-xs-4 control-label" style="padding: 6px 12px;">开户行<span style="color:red;">*</span></label>
				    <div class="col-xs-8">
				    <input type="text" class="form-control" id="bank" name="bank" placeholder="请输入开户行及支行名称">
				    </div>
				</div> -->
				<div class="form-group">
					<label for="coat" class="col-xs-4 control-label" style="padding: 6px 12px;">上衣尺寸<span style="color:red;">*</span></label>
					<div class="col-xs-8">
				    <select class="form-control" id="coat" name="coat">
				      <option value="S">S</option>
				      <option value="M">M</option>
				      <option value="L">L</option>
				      <option value="XL">XL</option>
				      <option value="XXL">XXL</option>
				      <option value="XXXL">XXXL</option>
				    </select>
				    </div>
				</div>
				<!-- <div class="form-group">
					<label for="pants" class="col-xs-4 control-label" style="padding: 6px 12px;">裤子尺寸<span style="color:red;">*</span></label>
					<div class="col-xs-8">
				    <select class="form-control" id="pants" name="pants">
				      <option value="26">26</option>
				      <option value="27">27</option>
				      <option value="28">28</option>
				      <option value="29">29</option>
				      <option value="30">30</option>
				      <option value="31">31</option>
				      <option value="32">32</option>
				      <option value="33">33</option>
				      <option value="34">34</option>
				      <option value="35">35</option>
				      <option value="36">36</option>
				      <option value="38">38</option>
				      <option value="40">40</option>
				      <option value="42">42</option>
				    </select>
				    </div>
				</div> -->
				
				<!-- <div class="form-group">
					 <label for="img" class="col-xs-4 control-label" style="padding: 6px 12px;">一寸正照</label>
					 <div class="col-xs-8">
					 <input type="file" id="file" name="file" />
					 </div>
				</div> -->
				
			    <!-- <div class="form-group">
				    <label class="col-xs-12 control-label" style="text-align:left;">*注：身份证号码及银行卡号，仅用以支付志愿者劳务使用</label>
			    </div> -->
				<div class="form-group">
				    <div class="col-xs-offset-4 col-xs-8">
				      <button type="submit" class="btn btn-primary" style="width: 90px;">报名</button>
				    </div>
			    </div>
			</form>
		</div>
	</div>
</div>

</body>
</html>