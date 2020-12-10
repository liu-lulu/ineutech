<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit">
<link rel="shortcut icon" href="../image/favicon.ico">
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
 
</head>
       
<body class="home">


<br/>1.发布
<br/>http://localhost:8090/goods/interface/publish.do
 
<form  action="interface/publish.do" method="post" target="_blank">
desc_id:<input type="text" name="desc_id">
<input type="submit" />
</form>
<br/>参数:desc_id  >>若批量发布用逗号,分隔
<br/>
返回结果:state:0.失败 1.成功 2.请求出现未知异常 
<br/>
<br/>

<br/>2.列表
<br/>http://localhost:8090/goods/interface/list.do
 
<form  action="interface/list.do" method="post" target="_blank">
menu:<input type="text" name="menu"><br/>
pageNo:<input type="text" name="pageNo"><br/>
otherNum:<input type="text" name="otherNum"><br/>
openId:<input type="text" name="openId"><br/>
goods_state:<input type="text" name="goods_state"><br/>
brand:<input type="text" name="brand"><br/>
category:<input type="text" name="category"><br/>
other:<input type="text" name="other"><br/>
goods_property:<input type="text" name="goods_property"><br/>
<input type="submit" />
</form>
<br/>参数:menu  >>菜单：1国内 2速抢 3秒杀
<br/>参数:pageNo  >>页码(otherNum值不为0时页码从1计数)
<br/>参数:otherNum  >>第一次从other表中查的数据条数
<br/>参数:openId  >>微信openId
<br/>参数:goods_state  >>国内商品状态:1现货  2非现货
<br/>参数:brand  >>品牌
<br/>参数:category  >>类别
<br/>参数:other  >>其它
<br/>参数:goods_property  >>关键字
<br/>
返回结果:list:列表 (其中:若openId为空,表示该用户从来没关注；若openId不为空，attention=1则说明该条信息被关注,attention=0则说明取消关注)<br/>
		otherNum<br/>

<br/>
<br/>

<br/>3.关注
<br/>http://localhost:8090/goods/interface/heart.do
 
<form  action="interface/heart.do" method="post" target="_blank">
menu:<input type="text" name="menu"><br/>
openId:<input type="text" name="openId"><br/>
desc_id:<input type="text" name="desc_id"><br/>
publishDate:<input type="text" name="publishDate"><br/>
<input type="submit" />
</form>
<br/>参数:menu  >>菜单：1国内 2速抢 3秒杀
<br/>参数:openId  >>微信用户的openId 
<br/>参数:desc_id  >>desc_id
<br/>参数:publishDate  >>发布时间
<br/>
返回结果:state:1成功
<br/>

<br/>4.取消关注
<br/>http://localhost:8090/goods/interface/noheart.do
 
<form  action="interface/noheart.do" method="post" target="_blank">
menu:<input type="text" name="menu"><br/>
openId:<input type="text" name="openId"><br/>
desc_id:<input type="text" name="desc_id"><br/>
publishDate:<input type="text" name="publishDate"><br/>
<input type="submit" />
</form>
<br/>参数:menu  >>菜单：1国内 2速抢 3秒杀
<br/>参数:openId  >>微信用户的openId 
<br/>参数:desc_id  >>desc_id
<br/>参数:publishDate  >>发布时间
<br/>
返回结果:state:1成功
<br/>
<br/>

<br><br/>5.待发布列表
<br/>http://localhost:8090/goods/interface/nopublishlist.do
 
<form  action="interface/nopublishlist.do" method="post" target="_blank">
pageNo:<input type="text" name="pageNo"><br/>
content:<input type="text" name="content"><br/>
开始时间:<input type="text" name="startTime" value="2017-12-01"><br/>
结束时间:<input type="text" name="endTime" value="2017-12-20"><br/>
类型:<input type="text" name="type" value="1"><br/>
<input type="submit" />
</form>
<br/>参数:pageNo  >>页码
<br/>参数:content  >>标题内容
<br/>参数:startTime  >>开始时间(可选)
<br/>参数:endTime  >>结束时间(可选)
<br/>参数:type  >>1囯内现货  2速抢好货 3今日秒杀(可选)
<br/>
返回结果:list:列表;pageNum:总页数

<br/>
<br/>6.下架
<br/>http://localhost:8090/goods/interface/down.do
 
<form  action="interface/down.do" method="post" target="_blank">
downInfo:<input type="text" name="downInfo"><br/>
<!-- menu:<input type="text" name="menu"><br/>
desc_id:<input type="text" name="desc_id"><br/>
publishDate:<input type="text" name="publishDate"><br/> -->
<input type="submit" />
</form>
<br>参数:downInfo >>下架信息json格式
<br/>其中参数:menu  >>菜单：1国内 2速抢 3秒杀
<br/>其中参数:desc_id  >>desc_id
<br/>其中参数:publishDate  >>发布时间
<br/>参数事例:[{"desc_id":1,"menu":"1","publishDate":"2018-01-02"},{"desc_id":2,"menu":"2","publishDate":"2018-01-03"}]
<br/>
返回结果:state:1成功
<br/>
<br/>
<br/>7.已发布列表
<br/>http://localhost:8090/goods/interface/weblist.do
 
<form  action="interface/weblist.do" method="post" target="_blank">
menu:<input type="text" name="menu"><br/>
pageNo:<input type="text" name="pageNo"><br/>
content:<input type="text" name="content"><br/>
开始时间:<input type="text" name="startTime" value="2017-12-01"><br/>
结束时间:<input type="text" name="endTime" value="2017-12-20"><br/>
是否确认完:<input type="text" name="confirm" value="1"><br/>
<input type="submit" />
</form>
<br/>参数:menu  >>菜单：1国内 2速抢 3秒杀
<br/>参数:pageNo  >>页码
<br/>参数:content  >>标题内容
<br/>参数:startTime  >>开始时间(可选)
<br/>参数:endTime  >>结束时间(可选)
<br/>参数:confirm  >>1未确认完
<br/>
返回结果:list:列表 ;pageNum:总页数,attentionNum关注人数,confirmNum已确认人数,noconfirmNum未确认人数
<br/>
<br/>

<br/>8.交换顺序
<br/>http://localhost:8090/goods/interface/changeOrder.do
 
<form  action="interface/changeOrder.do" method="post" target="_blank">
menu:<input type="text" name="menu"><br/>
desc_id:<input type="text" name="desc_id"><br/>
publishDate:<input type="text" name="publishDate"><br/>
<input type="submit" />
</form>
<br/>参数:menu  >>菜单：1国内 2速抢 3秒杀
<br/>参数:desc_id  >>信息id
<br/>参数:publishDate  >>发布时间
<br/>
返回结果:state:1成功 2该条信息已经置顶了
<br/>
<br/>
<br/>9.发布信息的关注用户
<br/>http://localhost:8090/goods/interface/heartUser.do
 
<form  action="interface/heartUser.do" method="post" target="_blank">
menu:<input type="text" name="menu"><br/>
desc_id:<input type="text" name="desc_id"><br/>
publishDate:<input type="text" name="publishDate"><br/>
pageNo:<input type="text" name="pageNo"><br/>
state:<input type="text" name="state"><br/>
<input type="submit" />
</form>
<br/>参数:menu  >>菜单：1国内 2速抢 3秒杀
<br/>参数:desc_id  >>desc_id
<br/>参数:publishDate  >>发布时间
<br/>参数:pageNo  >>页码
<br/><font color="red">参数:state  >>发布状态:2已下架(当查看的发布信息是下架时，传该参数)</font>
<br/>
返回结果:list:用户信息 ;pageNum:总页数
<br/>

<br/>
<br/>10.获取发布的所有的商品属性
<br/>http://localhost:8090/goods/interface/getproperty.do
 
<form  action="interface/getproperty.do" method="post" target="_blank">
menu:<input type="text" name="menu"><br/>
<input type="submit" />
</form>
<br/>参数:menu  >>菜单：1国内 3秒杀
<br/>
返回结果:brand:品牌 category:类别 other:其他 goods_property:商品属性(关键字)
<br/>

<br/>
<br/>
<br/>11.秒杀完
<br/>http://localhost:8090/goods/interface/miaoshaend.do
 
<form  action="interface/miaoshaend.do" method="post" target="_blank">
desc_id:<input type="text" name="desc_id"><br/>
publishDate:<input type="text" name="publishDate"><br/>
<input type="submit" />
</form>
<br/>参数:desc_id  >>desc_id
<br/>参数:publishDate  >>发布时间
<br/>
返回结果:state:1成功
<br/>


<br/>
<br/>12.用户关注的信息列表
<br/>http://localhost:8090/goods/interface/userheartlist.do
 
<form  action="interface/userheartlist.do" method="post" target="_blank">
openId:<input type="text" name="openId"><br/>
pageNo:<input type="text" name="pageNo"><br/>
<input type="submit" />
</form>
<br/>参数:openId  >>openId
<br/>参数:pageNo  >>页码
<br/>
返回结果:list:用户信息(其中：type：1囯内现货  3今日秒杀；state=2是指该信息已下架) ;pageNum:总页数
<br/>

<br/>
<br/>
<br/>13.管理员确认关注信息的用户
<br/>http://localhost:8090/goods/interface/confirmUser.do
 
<form  action="interface/confirmUser.do" method="post" target="_blank">
menu:<input type="text" name="menu"><br/>
desc_id:<input type="text" name="desc_id"><br/>
publishDate:<input type="text" name="publishDate"><br/>
openId:<input type="text" name="openId"><br/>
state:<input type="text" name="state"><br/>
<input type="submit" />
</form>
<br/>参数:menu  >>菜单：1国内  3秒杀
<br/>参数:desc_id  >>desc_id
<br/>参数:publishDate  >>发布时间
<br/>参数:openId  >>openId
<br/><font color="red">参数:state  >>发布状态:2已下架(当查看的发布信息是下架时，传该参数)</font>
<br/>
返回结果:state:1成功
<br/>


<br/>
<br/>
<br/>14.修改发布信息的关注基数
<br/>http://localhost:8090/goods/interface/base.do
 
<form  action="interface/base.do" method="post" target="_blank">
menu:<input type="text" name="menu"><br/>
desc_id:<input type="text" name="desc_id"><br/>
publishDate:<input type="text" name="publishDate"><br/>
base:<input type="text" name="base"><br/>
<input type="submit" />
</form>
<br/>参数:menu  >>菜单：1国内  3秒杀
<br/>参数:desc_id  >>desc_id
<br/>参数:publishDate  >>发布时间
<br/>参数:base  >>关注基数
<br/>
返回结果:state:1成功
<br/>


<br/>15.关注列表
<br/>http://localhost:8090/goods/interface/attentionList.do
 
<form  action="interface/attentionList.do" method="post" target="_blank">
menu:<input type="text" name="menu"><br/>
pageNo:<input type="text" name="pageNo"><br/>
微信账号:<input type="text" name="wx_name"><br/>
是否确认完:<input type="text" name="confirm" value="1"><br/>
<input type="submit" />
</form>
<br/>参数:menu  >>菜单：1国内  3秒杀
<br/>参数:pageNo  >>页码
<br/>参数:wx_name  >>微信账号
<br/>参数:confirm  >>1未确认
<br/>
返回结果:list:列表 ;pageNum:总页数
<br/>
<br/>

<br/>
<br/>15.删除待发布信息
<br/>http://localhost:8090/goods/interface/del.do
 
<form  action="interface/del.do" method="post" target="_blank">
desc_id:<input type="text" name="desc_id"><br/>
menu:<input type="text" name="menu"><br/>
state:<input type="text" name="state"><br/>
<input type="submit" />
</form>
<br/>参数:desc_id  >>desc_id
<br/>参数:menu  >>菜单：1国内  3秒杀
<br/>参数:state  >>状态
<br/>
返回结果:state:1成功

<% 
	String str_test = "7{4HVM5}8]%}BVGO$}XA0C9_ee1c7b01-23ff-47af-8ee1-820ba7155f1b_a017f239-3dd4-492b-9ef0-08dc36ae2f75_768*577.jpg"; 
%> 
<%-- <img alt="" width="1000" height="500" src="http://osardem8v.bkt.clouddn.com/<%=java.net.URLEncoder.encode(str_test) %>"> --%>
</body>
</html>