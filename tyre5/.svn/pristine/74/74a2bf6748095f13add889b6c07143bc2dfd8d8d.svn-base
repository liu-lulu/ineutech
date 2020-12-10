package com.kkbc.hardware.send;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.socket.DatagramPacket;

import com.kkbc.hardware.HardwareElement;
import com.kkbc.hardware.UDPServerManager;
import com.kkbc.hardware.process.DtuOperationProcess;
import com.psylife.hardware.data.PackageData;
import com.psylife.hardware.data.parse.PackageDataBrainParse;
import com.psylife.hardware.protocol.constant.PackageCommandConstant;
import com.psylife.hardware.protocol.constant.PackageHeadConstant;
import com.psylife.util.DateUtil;
import com.psylife.util.ToolsForByte;

/**
 * 发送
 * @author Administrator
 *
 */
public class HardwareSendManager {
	
	private static HardwareSendManager instance=null;
	
	private HardwareSendManager(){}
	
	
	public static HardwareSendManager getInstance(){
		if(instance==null){
			synchronized (HardwareSendManager.class) {
				if(instance==null){
					instance=new HardwareSendManager();
				}
			}
		}
		return instance;
	}

	private PackageDataBrainParse parse=new PackageDataBrainParse();
	
	private ToolsForByte toolsForByte=new ToolsForByte();
	
	/**
	 * 发送确认收到包
	 * @param element
	 * @param rtype 原命令
	 * @param rno 原包号
	 * @return
	 */
	public int sendGetConfirm(HardwareElement element,int rtype,int rno){
		byte[]  arr=getSendGetConfirmData(element, rtype, rno);
		if(arr!=null){
			return send(element.getUdpAddress(), arr);
		}else{
			return -1;
		} 
	}
	
	/**
	 * 获取,发送确认收到包
	 * @param element 
	 * @param rtype 原命令
	 * @param rno 包号
	 * @return
	 */
	public byte[] getSendGetConfirmData(HardwareElement element,int rtype,int rno){
		try {
			PackageData data=new PackageData();
			data.setHead(PackageHeadConstant.PACKAGE_HEAD_NWX);
			data.setCommand(PackageCommandConstant.CMD_NWX_GET_SUCCESS);
			data.setNo(element.getPackageNo());
			data.setPhone(element.getPhone());
			data.setData(toolsForByte.concatAll(new byte[]{(byte)(rtype&0XFF),(byte) ((rno >>> 8) & 0xFF),(byte) (rno & 0xFF)},DateUtil.currentDateStringByNWX().getBytes("ascii")));
			data.setDataLen(data.getData().length+10);
			return parse.packageDataToByteArr(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 发送心跳包
	 * @param element
	 * @return
	 */
	public int sendGetHeart(HardwareElement element){
		byte[]  arr=getSendGetHeart(element);
		if(arr!=null){
			return send(element.getUdpAddress(), arr);
		}else{
			return -1;
		} 
	}
	
	/**
	 * 获取,发送心跳包
	 * @param element 
	 * @return
	 */
	public byte[] getSendGetHeart(HardwareElement element){
		try {
			PackageData data=new PackageData();
			data.setHead(PackageHeadConstant.PACKAGE_HEAD_NWX);
			data.setCommand(PackageCommandConstant.CMD_NWX_GET_HEART_TO_DTU);
			data.setNo(element.getPackageNo());
			data.setPhone(element.getPhone());
			data.setData(null);
			data.setDataLen(10);
			return parse.packageDataToByteArr(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	/**
	 * 发送服务器操作dtu命令
	 * @param element
	 * @param info 操作信息
	 * @param no 包号
	 * @return
	 */
	public int sendGetOperationDtu(HardwareElement element,String info,int no){
		byte[]  arr=getSendGetOperationDtuData(element,info,no);
		if(arr!=null){
			return send(element.getUdpAddress(), arr);
		}else{
			return -1;
		} 
	}
	
	/**
	 * 获取,发送服务器操作dtu命令
	 * @param element
	 * @param info 操作信息
	 * @param no 包号
	 * @return
	 */
	public byte[] getSendGetOperationDtuData(HardwareElement element,String info,int no){
		try {
			PackageData data=new PackageData();
			data.setHead(PackageHeadConstant.PACKAGE_HEAD_NWX);
			data.setCommand(PackageCommandConstant.CMD_NWX_GET_OPERATION_DTU);
			data.setNo(no);
			data.setPhone(element.getPhone());
            //FrameInfo的信息：控制命令帧序号(2)+超时时间（单位s）(1)+Port Num:2(1)+智能设备地址（预留）(2)+控制命令串(控制命令串长度(0-64bytes))
			data.setData(toolsForByte.concatAll(new byte[]{(byte) ((no >>> 8) & 0xFF),(byte) (no & 0xFF),(byte)DtuOperationProcess.INTERVAL_SEND_TIMEOUT&0XFF,2,0,0},info.getBytes("ascii")));
			data.setDataLen(data.getData().length+10);
			return parse.packageDataToByteArr(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	/**
	 * 发送
	 * @param udpAddress
	 * @param arr
	 * @return 返回
	 */
	private int send(InetSocketAddress udpAddress,byte[]  arr){
		if(UDPServerManager.getInstance().getServerChannel().isWritable()){
			DatagramPacket packet=new DatagramPacket(getByteBuf(arr), udpAddress);
			UDPServerManager.getInstance().getServerChannel().writeAndFlush(packet);	
			return 0;
		}else{
			return -2;
		}
//		return 0;
	}
	public ByteBuf getByteBuf(byte[] arr){
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(arr.length);
		byteBuf.writeBytes(arr);
		return byteBuf;
	}

}
