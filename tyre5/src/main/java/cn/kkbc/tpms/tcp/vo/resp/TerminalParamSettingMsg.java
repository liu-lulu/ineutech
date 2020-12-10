package cn.kkbc.tpms.tcp.vo.resp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.kkbc.tpms.tcp.util.BitOperator;

public class TerminalParamSettingMsg {

	private List<ParamItem> list;

	public TerminalParamSettingMsg() {
		this.list = new ArrayList<>();
	}

	public TerminalParamSettingMsg addParam(ParamItem paramItem) {
		this.list.add(paramItem);
		return this;
	}

	public int getTotalParamCount() {
		return list.size();
	}

	public List<ParamItem> getParamList() {
		return list;
	}

	public byte[] toMsgBodyBytes() throws IOException {
		BitOperator bitOperator = new BitOperator();
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			// byte[0] 参数总数----8
			baos.write(bitOperator.integerTo1Bytes(this.list.size()));
			// byte[1-x] 参数列表
			for (ParamItem item : this.list) {
				// 参数ID(DWORD)---4*8==32
				baos.write(bitOperator.longToBytes(item.getId(), 4));
				// 参数长度(byte)---8
				baos.write(bitOperator.integerTo1Bytes(item.getLength()));
				// 参数值
				baos.write(item.getData());
			}
			return baos.toByteArray();
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
	}

	public static class ParamItem {
		// 参数ID(DWORD)
		private long id;
		// 参数值
		private byte[] data;

		public ParamItem() {
		}

		public ParamItem(long id, byte[] data) {
			super();
			this.id = id;
			this.data = data;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public int getLength() {
			return data == null ? 0 : data.length;
		}

		public byte[] getData() {
			return data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}

		@Override
		public String toString() {
			return "ParamItem [id=" + id + ", data=" + Arrays.toString(data) + "]";
		}

	}
}
