package test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.psylife.hardware.HardwareElement;
import com.psylife.hardware.send.HardwareSendManager;
import com.psylife.util.HexStringUtil;

public class Test2 {

	public static void main(String[] args) {
		HardwareElement element=new HardwareElement();
		element.setPhone("02548784");
		String tt=HexStringUtil.encodeHexStr(HardwareSendManager.getInstance().getSendGetHeart(element), false);
		String dates="\n"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.println("输出:"+tt+dates);
	}

}
