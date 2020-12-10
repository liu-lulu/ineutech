<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>接口</title>
</head>
<body>

<br/>结果说明:
<br/>total:当前测试的绑定设备的所有在线个数 success:本次下发指令的个数
<br/>
<br/>

<br/>
<br/>选择测试节目
<br/>http://localhost:8090/smg_dial/device/selectTest.do
<form action="device/selectTest.do" target="_blank" method="get">
测试id：<input name="testId" value="12">>>testId<br/>
<input type="submit" value="确定">
</form>
<br/>参数说明:
<br/>testId  >>测试id
<br/>结果说明:
<br/>state:2 选择成功



<br/>
<br>
<br/>测试阶段切换
<br/>http://localhost:8090/smg_dial/device/testPeriodChange.do
<form action="device/testPeriodChange.do" target="_blank" method="get">
测试阶段：<input name="period" value="2">>>period<br/>
<input type="submit" value="确定">
</form>
<br/>参数说明:
<br/>period  >>测试阶段：2测前问答，3开始打分，4打分结束，5测后问答，6结束测试，7拨盘引导
<br/>
<br/>

<br/>
<br>
<br/>阶段状态切换
<br/>http://localhost:8090/smg_dial/device/stateChange.do
<form action="device/stateChange.do" target="_blank" method="get">
阶段状态：<input name="periodStatus" value="1">>>periodStatus<br/>
<input type="submit" value="确定">
</form>
<br/>参数说明:
<br/>periodStatus  >>阶段状态：1暂停 2恢复
<br/>
<br/>

 <br/>
<br/>查看测试分数
<br/>http://localhost:8090/smg_dial/device/score.do
<form action="device/score.do" target="_blank" method="get">
<input type="submit" value="确定">
</form>
<br/>结果说明:
<br/>info: seatNo 座位号,score 分数 ,online 设备状态(0:不在线 1:在线)


<br/>
<br/>分数打包数据上传
<br/>http://localhost:8090/smg_dial/device/scorePackage.do
<form action="device/scorePackage.do" method="post" >
包数据：<input name="scorePackage" id="scorePackage">>>scorePackage<br/>
<input type="submit" value="确定">
</form>
<br/>参数说明:
<br/>
[{"score":100,"hardId":344,"humanId":1,"testId":12,"seatNo":1,"mac":"5C0EABF7-67AD-4A1F-B05C-3C0BD8FCF2E4","caijiTime":"2017-01-04 17:34:35"},{"score":90,"hardId":344,"humanId":1,"testId":12,"seatNo":1,"mac":"5C0EABF7-67AD-4A1F-B05C-3C0BD8FCF2E4","caijiTime":"2017-01-04 17:34:36"}]
<br/>scorePackage  >>json数据：所需参数见上行测试数据

<br/>结果说明:
<br/>state:0无数据 1成功

<br/>
<br/>查看当前用户是否已绑有脑电
<br/>http://localhost:8090/smg_dial/device/userBrain.do
<form action="device/userBrain.do" method="post" target="_blank">
人员id：<input name="humanId" id="humanId">>>humanId<br/>
<input type="submit" value="确定">
</form>
<br/>参数说明:
<br/>humanId  >>人员id

<br/>结果说明:
<br/>state:1当前没有绑定脑电 2已绑定脑电

<br/>
<br/>绑定脑电
<br/>http://localhost:8090/smg_dial/device/bindBrain.do
<form action="device/bindBrain.do" method="post" target="_blank">
人员id：<input name="humanId" id="humanId">>>humanId<br/>
脑电编号：<input name="brainNo" id="brainNo">>>brainNo<br/>
<input type="submit" value="确定">
</form>
<br/>参数说明:
<br/>humanId  >>人员id
<br/>brainNo  >>脑电编号

<br/>结果说明:
<br/>state:1绑定成功 2该编号没有对应的脑电 3该脑电已绑定到其他人员,不能重复绑定


<br/>
<br/>解除脑电绑定
<br/>http://localhost:8090/smg_dial/device/removeBind.do
<form action="device/removeBind.do" method="post" target="_blank">
人员id：<input name="humanId" id="humanId">>>humanId<br/>
<input type="submit" value="确定">
</form>
<br/>参数说明:
<br/>humanId  >>人员id

<br/>结果说明:
<br/>state:1解除成功


<br/><br/><br/><br/><br/>
</body>
</html>