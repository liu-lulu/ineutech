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

<br/>.扫码获取商品信息
<br/>http://localhost:8090/stock/interface/getByCode.do
 
<form  action="interface/getByCode.do" method="post" target="_blank">
条形码:<input type="text" name="code"><br/>
<input type="submit" />
</form>
<br/>参数:code  >>条形码(必填)
<br/>
返回结果:info: 商品信息(空:没有该商品),
其中:barcode 条形码,brand 品牌,model 型号,in_price 进价,out_price 售价,remain_count 剩余数量,comment 备注,img 图片
<br/>
<br/>
<br/>.创建商品
<br/>http://localhost:8090/stock/interface/saveInfo.do
 
<form  action="interface/saveInfo.do" method="post" enctype="multipart/form-data" target="_blank">
条形码:<input type="text" name="code"><br/>
品牌:<input type="text" name="brand"><br/>
品名:<input type="text" name="name"><br/>
型号:<input type="text" name="model"><br/>
进价:<input type="text" name="inPrice"><br/>
售价:<input type="text" name="outPrice"><br/>
数量:<input type="text" name="count"><br/>
来源:<input type="text" name="origin"><br/>
备注:<input type="text" name="comment"><br/>
图片:<input type="file" name="files">
<input type="submit" />
</form>
<br/>参数:code  >>条形码(必填)
<br/>参数:brand  >>品牌
<br/>参数:name  >>品名
<br/>参数:model  >>型号
<br/>参数:inPrice  >>进价
<br/>参数:outPrice  >>售价
<br/>参数:count  >>入库数量
<br/>参数:origin  >>来源
<br/>参数:comment  >>备注
<br/>参数:files  >>图片
<br/>
返回结果:info: 1成功 2商品已存在
<br/>

<br/>.商品入库
<br/>http://localhost:8090/stock/interface/in.do
 
<form  action="interface/in.do" method="post" target="_blank">
商品条形码:<input type="text" name="barcode"><br/>
数量:<input type="text" name="count"><br/>
<input type="submit" />
</form>
<br/>参数:barcode  >>商品条形码(必填)
<br/>参数:count  >>入库数量
<br/>
返回结果:info:0失败  1成功
<br/>
<br/>

<br/>.商品出库
<br/>http://localhost:8090/stock/interface/out.do
 
<form  action="interface/out.do" method="post" target="_blank">
商品条形码:<input type="text" name="barcode"><br/>
数量:<input type="text" name="count"><br/>
<input type="submit" />
</form>
<br/>参数:barcode  >>商品条形码(必填)
<br/>参数:count  >>出库数量
<br/>
返回结果:info:0失败  1成功
<br/>
<br/>
<br/>

<br/>.入/出库历史信息列表(每分钟的入/出库信息)
<br/>http://localhost:8090/caigou/interface/history.do
 
<form  action="interface/history.do" method="post" target="_blank">
商品ID:<input type="text" name="goodsId"><br/>
入/出库:<input type="text" name="type"><br/>
开始时间:<input type="text" name="startTime" value="2017-09-19"><br/>
结束时间:<input type="text" name="endTime" value="2017-09-20"><br/>
页码:<input type="text" name="pagenum"><br/>
<input type="submit" />
</form>
<br/>参数:goodsId  >>商品
<br/>参数:type  >>入/出库:1 入库; 2 出库
<br/>参数:startTime  >>开始时间(可选)
<br/>参数:endTime  >>结束时间(可选)
<br/>参数:pagenum  >>第几页,1,2,3。。。每页15条数据
<br/>
返回结果:info: operate_time时间戳,count入/出库数量,remain_count剩余数量

<br/>
<br/>
<br/>.商品列表
<br/>http://localhost:8090/caigou/interface/goodsList.do
 
<form  action="interface/goodsList.do" method="post" target="_blank">
品牌:<input type="text" name="brand"><br/>
页码:<input type="text" name="pagenum"><br/>
<input type="submit" />
</form>
<br/>参数:brand  >>品牌
<br/>参数:pagenum  >>第几页,1,2,3。。。每页15条数据
<br/>
返回结果:info: 

<br/><br/>
<br/>.库存流水(时间段内商品总共的入出库数量)
<br/>http://localhost:8090/caigou/interface/goodsOperateInfoPage.do
 
<form  action="interface/goodsOperateInfoPage.do" method="post" target="_blank">
品牌:<input type="text" name="brand"><br/>
开始时间:<input type="text" name="startTime" value="2017-09-19"><br/>
结束时间:<input type="text" name="endTime" value="2017-09-20"><br/>
最低库存:<input type="text" name="remainLow" ><br/>
最高库存:<input type="text" name="remainHigh" ><br/>
最低价格:<input type="text" name="priceLow" ><br/>
最高价格:<input type="text" name="priceHigh" ><br/>
排序属性:<input type="text" name="column" ><br/>
顺序:<input type="text" name="order" ><br/>
页码:<input type="text" name="pagenum" value="1"><br/>
<input type="submit" />
</form>
<br/>参数:brand  >>品牌
<br/>参数:startTime  >>开始时间(可选)
<br/>参数:endTime  >>结束时间(可选)
<br/>参数:remainLow  >>最低库存(可选)
<br/>参数:remainHigh  >>最高库存(可选)
<br/>参数:priceLow  >>最低价格(可选)
<br/>参数:priceHigh  >>最高价格(可选)
<br/>参数:column  >>排序属性(可选):1进价 2售价 3库存 4入库量 5出库量
<br/>参数:order  >>顺序(可选):1升序 2降序
<br/>参数:pagenum  >>第几页,1,2,3。。。每页15条数据(若不传该参数或该参数值为空，则查询所有数据)
<br/>
返回结果:info: last_time最近操作时间,incount入库数量,outcount出库数量,remain_count剩余数量
<br/>

<br/><br/>
<br/>.操作历史(时间段内每天总共的入出库数量)
<br/>http://localhost:8090/caigou/interface/goodsHistoryDayInfo.do
 
<form  action="interface/goodsHistoryDayInfo.do" method="post" target="_blank">
商品ID:<input type="text" name="goodsId"><br/>
开始时间:<input type="text" name="startTime" value="2017-09-19"><br/>
结束时间:<input type="text" name="endTime" value="2017-09-20"><br/>
页码:<input type="text" name="pagenum" value="1"><br/>
<input type="submit" />
</form>
<br/>参数:goodsId  >>商品ID
<br/>参数:startTime  >>开始时间(可选)
<br/>参数:endTime  >>结束时间(可选)
<br/>参数:pagenum  >>第几页,1,2,3。。。每页15条数据(若不传该参数或该参数值为空，则查询所有数据)
<br/>
返回结果:info: last_time操作时间,incount入库数量,outcount出库数量
<br/>

<br/><br/>
<br/>.操作历史(时间段内每天的入/出库数量)
<br/>http://localhost:8090/caigou/interface/goodsHistoryInfoPage.do
 
<form  action="interface/goodsHistoryInfoPage.do" method="post" target="_blank">
品牌:<input type="text" name="brand"><br/>
开始时间:<input type="text" name="startTime" value="2017-09-19"><br/>
结束时间:<input type="text" name="endTime" value="2017-09-20"><br/>
页码:<input type="text" name="pagenum" value="1"><br/>
<input type="submit" />
</form>
<br/>参数:brand  >>品牌
<br/>参数:startTime  >>开始时间(可选)
<br/>参数:endTime  >>结束时间(可选)
<br/>参数:pagenum  >>第几页,1,2,3。。。每页15条数据(若不传该参数或该参数值为空，则查询所有数据)
<br/>
返回结果:info: last_time最近操作时间,type 1入2出,count数量,remain_count剩余数量
<br/>


<br/>
<br/>

<br/>.获取操作日期
<br/>http://localhost:8090/stock/interface/getDate.do
 
<form  action="interface/getDate.do" method="post" target="_blank">
<input type="submit" />
</form>
<br/>
返回结果:info:
<br/>
</body>
</html>