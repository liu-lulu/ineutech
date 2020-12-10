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
<br>以下所有接口,若获取返回值state:0说明请求失败

<br/>1.登陆接口
<br/>http://localhost:8090/caigou/interface/login.do
 
<form  action="interface/login.do" method="post" target="_blank">
用户名:<input type="text" name="name"><br/>
密码:<input type="text" name="password"><br/>
type:<input type="text" name="type"><br/>
<input type="submit" value="login"/>
</form>
<br/>参数:type  >>1:管理员 2:供应商
<br/>
返回结果:state: 1.成功 2.用户名或密码错误 3用户名或密码为空
<br/>
<br/>

<br/>2.采购接口
<br/>http://localhost:8090/caigou/interface/caigou.do
 
<form  action="interface/caigou.do" method="post" enctype="multipart/form-data" target="_blank">
caigouInfo:<input type="text" name="caigouInfo" value=""><br/>
<input type="file" name="files">
<input type="file" name="files">
<input type="submit" value="caigou"/>
</form>
<br/>参数:caigouInfo  >>json格式(可多个)-提供信息：admin_id(管理员Id),supplier_id(供应商Id),brand(品牌),model(型号),num(数量),img(图片),comment(备注),price(售价)
<br/>[{"img":"","create_time":null,"caigou_status":0,"num":10,"payment_status":0,"collection_status":0,"admin_id":1,"comment":"--香奈儿备注--","model":"XX1","brand":"香奈儿Chanel","order_id":0,"supplier_id":2,"delivery_status":0,"price":100},{"img":"","create_time":null,"caigou_status":0,"num":30,"payment_status":0,"collection_status":0,"admin_id":1,"comment":"--兰蔻备注--","model":"XX2","brand":"兰蔻Lancome","order_id":0,"supplier_id":2,"delivery_status":0,"price":200}]

<br/>
返回结果:state: 1.没有商品信息 2.成功
<br/>
<br/>

<br/>3.采购列表
<br/>http://localhost:8090/caigou/interface/caigouList.do
 
<form  action="interface/caigouList.do" method="post" target="_blank">
用户ID:<input type="text" name="userId"><br/>
角色:<input type="text" name="role"><br/>
页码:<input type="text" name="pagenum"><br/>
商品名:<input type="text" name="brand"><br/>
收款状态:<input type="text" name="collectionStatus"><br/>
采购状态:<input type="text" name="caigouStatus"><br/>
发货状态:<input type="text" name="deliveryStatus"><br/>
付款状态:<input type="text" name="paymentStatus"><br/>
供应商ID:<input type="text" name="supplierId"><br/>
<input type="submit" />
</form>
<br/>参数:userId  >>用户ID(必填)
<br/>参数:role  >>角色(必填)--1:管理员 2:供应商
<br/>参数:pagenum  >>第几页,1,2,3。。。每页15条数据
<br/>参数:brand  >>商品名(可选)
<br/>参数:collectionStatus  >>收款状态(可选): 0未收款 1已收款
<br/>参数:caigouStatus  >>采购状态(可选): 0未采购 1已采购
<br/>参数:deliveryStatus  >>发货状态(可选): 0未发货 1已发货
<br/>参数:paymentStatus  >>付款状态(可选): 0未付款 1已付款
<br/>参数:supplierId  >>供应商ID(可选)
<br/>
返回结果:info: 采购列表信息,注意:boughtCount已购数量,sendCount已发货数量,remainCount剩余数量,price售价
<br/>
<br/>

<br/>4.采购详情
<br/>http://localhost:8090/caigou/interface/caigouDetail.do
 
<form  action="interface/caigouDetail.do" method="post" target="_blank">
采购ID:<input type="text" name="orderId"><br/>
<input type="submit" />
</form>
<br/>参数:orderId  >>采购ID(必填)
<br/>
返回结果:info: 
<br/>orderInfo采购详情： payment_status付款状态：0未付款 1已付款 2已收完,pay_money总共付给供应商的钱数,pay_dingjin给供应商的定金,collection_status收款状态: 0未收款 1已收款 2已收完,collection_money总共收到采购人的钱数,collection_dingjin收到采购人的定金
<br/>packageInfo包裹详情：send_time发货时间,sign_time签收时间, status:1已发货 2已签收 3过期已删除
<br/>collectionDetails:收款信息
<br/>payDetails:付款信息
<br/>
<br/>

<br/>5.供应商列表
<br/>http://localhost:8090/caigou/interface/supplierList.do
<form  action="interface/supplierList.do" method="post" target="_blank">
<input type="submit" value="供应商列表"/>
</form>
<br/>参数:无
<br/>
返回结果:info: 供应商信息
<br/>
<br/>

<br/>6.商品列表
<br/>http://localhost:8090/caigou/interface/goodsList.do
 
<form  action="interface/goodsList.do" method="post" target="_blank">
用户ID:<input type="text" name="userId"><br/>
页码:<input type="text" name="pagenum"><br/>
商品名:<input type="text" name="brand"><br/>
开始时间:<input type="text" name="startTime" value="2017-06-12"><br/>
结束时间:<input type="text" name="endTime" value="2017-06-23"><br/>
<input type="submit" />
</form>
<br/>参数:userId  >>用户ID(必填)
<br/>参数:pagenum  >>第几页,1,2,3。。。每页15条数据
<br/>参数:brand  >>商品名(可选)
<br/>参数:startTime  >>开始时间(可选)
<br/>参数:endTime  >>结束时间(可选)
<br/>
返回结果:info: 商品列表
<br/>
<br/>

<br/>7.物流列表
<br/>http://localhost:8090/caigou/interface/wuliuList.do
 
<form  action="interface/wuliuList.do" method="post" target="_blank">
用户ID:<input type="text" name="userId"><br/>
角色:<input type="text" name="role"><br/>
页码:<input type="text" name="pagenum"><br/>
<input type="submit" />
</form>
<br/>参数:userId  >>用户ID(必填)
<br/>参数:role  >>角色(必填)--1:管理员 2:供应商
<br/>参数:pagenum  >>第几页,1,2,3。。。每页15条数据
<br/>
返回结果:info: 物流列表信息  注意：purchase_price包裹里所有商品的总价, send_time发货时间,sign_time签收时间 ,status:1已发货 2已签收 3过期已删除
<br/>
<br/>

<br/>8.包裹详情
<br/>http://localhost:8090/caigou/interface/packageDetail.do
 
<form  action="interface/packageDetail.do" method="post" target="_blank">
包裹ID:<input type="text" name="packageId"><br/>
<input type="submit" />
</form>
<br/>参数:packageId  >>包裹ID(必填)
<br/>
返回结果:info: 包裹详情 注意：remainCount表示剩余的采购商品个数,在包裹里增加该商品数量时增加量不能超过剩余量
<br/>
<br/>

<br/>9.包裹操作(签收)
<br/>http://localhost:8090/caigou/interface/packageOperate.do
 
<form  action="interface/packageOperate.do" method="post" target="_blank">
包裹ID:<input type="text" name="packageId"><br/>
操作:<input type="text" name="type"><br/>
<input type="submit" />
</form>
<br/>参数:packageId  >>包裹ID(必填)
<br/>参数:type  >>操作类型： 2(签收)
<br/>
返回结果:info: 1成功
<br/>
<br/>

<br/>10.供应商采购
<br/>http://localhost:8090/caigou/interface/supplierCaigou.do
 
<form  action="interface/supplierCaigou.do" method="post" target="_blank">
采购ID:<input type="text" name="orderId"><br/>
数量:<input type="text" name="count"><br/>
采购价:<input type="text" name="purchase_price"><br/>
<input type="submit" />
</form>
<br/>参数:orderId  >>采购ID(必填)
<br/>参数:count  >>采购数量
<br/>参数:purchase_price  >>采购价
<br/>
返回结果:status: 1成功 2采购数量超过订购的数量
<br/>
<br/>

<br/>11.创建包裹
<br/>http://localhost:8090/caigou/interface/createPackage.do
 
<form  action="interface/createPackage.do" method="post" target="_blank">
供应商ID:<input type="text" name="userId"><br/>
包裹名:<input type="text" name="packageName"><br/>
快递单号:<input type="text" name="expressNo"><br/>
备注:<input type="text" name="comment"><br/>
<input type="submit" />
</form>
<br/>参数:userId  >>供应商ID(必填)
<br/>参数:packageName  >>包裹名
<br/>参数:expressNo  >>快递单号
<br/>参数:comment  >>备注
<br/>
返回结果:info: package_id
<br/>
<br/>

<br/>12.包裹列表
<br/>http://localhost:8090/caigou/interface/baoguoList.do
 
<form  action="interface/baoguoList.do" method="post" target="_blank">
供应商ID:<input type="text" name="userId"><br/>
页码:<input type="text" name="pagenum"><br/>
<input type="submit" />
</form>
<br/>参数:userId  >>供应商ID(必填)
<br/>参数:pagenum  >>页码
<br/>
返回结果:info: 包裹列表
<br/>
<br/>

<br/>13.编辑包裹里的商品
<br/>http://localhost:8090/caigou/interface/editPackage.do
 
<form  action="interface/editPackage.do" method="post" target="_blank">
商品信息:<input type="text" name="goodsInfo"><br/>
<input type="submit" />
</form>
<br/>参数:goodsInfo  >>商品信息：package_id(包裹id),order_id(商品id),order_detail_id(购买详情id),brand(商品名),model(型号),count(数量),purchase_price(采购价)
<br/>[{"order_detail_id":1,"packageDetail_id":0,"create_time":null,"count":1,"purchase_price":500,"model":"XX1","package_id":1,"brand":"香奈儿Chanel","order_id":5},{"order_detail_id":2,"packageDetail_id":0,"create_time":null,"count":1,"purchase_price":510,"model":"XX2","package_id":1,"brand":"兰蔻Lancome","order_id":5}]

<br/>
返回结果:info:
<br/>
<br/>

<br/>13.包裹发货
<br/>http://localhost:8090/caigou/interface/sendPackage.do
 
<form  action="interface/sendPackage.do" method="post" target="_blank">
包裹Id:<input type="text" name="packageId"><br/>
快递号:<input type="text" name="expressNo"><br/>
备注:<input type="text" name="comment"><br/>
<input type="submit" />
</form>
<br/>参数:packageId  >>包裹id
<br/>参数:expressNo  >>快递号
<br/>参数:comment  >>备注
<br/>
返回结果:info:
<br/>
<br/>

<br/>14.剩余的可加入包裹的采购商品信息(加入包裹界面)
<br/>http://localhost:8090/caigou/interface/remainGood.do
 
<form  action="interface/remainGood.do" method="post" target="_blank">
采购ID:<input type="text" name="orderId"><br/>
<input type="submit" />
</form>
<br/>参数:orderId  >>采购ID(必填)
<br/>
返回结果:info:注意：count为剩余可用个数
<br/>
<br/>

<br/>15.采购商品加入包裹
<br/>http://localhost:8090/caigou/interface/goodsToPackage.do
 
<form  action="interface/goodsToPackage.do" method="post" target="_blank">
商品信息:<input type="text" name="goodsInfo"><br/>
<input type="submit" />
</form>
<br/>参数:goodsInfo  >>商品信息：package_id(包裹id),order_id(商品id),order_detail_id(购买详情id),brand(商品名),model(型号),count(数量),purchase_price(采购价)
<br/>[{"order_detail_id":1,"packageDetail_id":0,"create_time":null,"count":1,"purchase_price":500,"model":"XX1","package_id":1,"brand":"香奈儿Chanel","order_id":5},{"order_detail_id":2,"packageDetail_id":0,"create_time":null,"count":1,"purchase_price":510,"model":"XX2","package_id":1,"brand":"兰蔻Lancome","order_id":5}]

<br/>
返回结果:info:
<br/>
<br/>

<br/>16.收付款
<br/>http://localhost:8090/caigou/interface/payOperate.do
 
<form  action="interface/payOperate.do" method="post" target="_blank">
商品id:<input type="text" name="orderId"><br/>
收付款类型:<input type="text" name="type"><br/>
付款类型:<input type="text" name="payType"><br/>
钱数:<input type="text" name="money"><br/>
备注:<input type="text" name="comment"><br/>
<input type="submit" />
</form>
<br/>参数:orderId  >>商品id
<br/>参数:type  >>1:收款 2:付款
<br/>参数:payType  >>付款类型: 0未付 1已付 2付完 3定金
<br/>参数:money  >>钱数
<br/>参数:comment  >>备注

<br/>
返回结果:info:
<br/>
<br/>
<br/>17.删除采购信息
<br/>http://localhost:8090/caigou/interface/delOrder.do
 
<form  action="interface/delOrder.do" method="post" target="_blank">
采购ID:<input type="text" name="orderId"><br/>
<input type="submit" />
</form>
<br/>参数:orderId  >>采购ID(必填)
<br/>
返回结果:info: 删除的采购商品信息
<br/>
<br/>

<br/>18.历史商品列表
<br/>http://localhost:8090/caigou/interface/goodsExistList.do
 
<form  action="interface/goodsExistList.do" method="post" target="_blank">
商品名:<input type="text" name="brand"><br/>
页码:<input type="text" name="pagenum"><br/>
<input type="submit" />
</form>
<br/>参数:brand  >>商品名(可选)
<br/>参数:pagenum  >>页码(必填)
<br/>
返回结果:info: 商品信息
<br/>
<br/>

<br/>19.一键加入包裹
<br/>http://localhost:8090/caigou/interface/oneKeyToPack.do
 
<form  action="interface/oneKeyToPack.do" method="post" target="_blank">
采购ID:<input type="text" name="orderId"><br/>
包裹ID:<input type="text" name="packageId"><br/>
<input type="submit" />
</form>
<br/>参数:orderId  >>采购ID(必填)
<br/>参数:packageId  >>包裹ID(必填)
<br/>
返回结果:status: 1成功 2没有剩余商品
<br/>
<br/>
<br/>

<br/>
</body>
</html>