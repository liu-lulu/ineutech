package cn.kkbc.tpms.common.exception;

public class ParamSettingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParamSettingException() {
		super();
	}

	public ParamSettingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParamSettingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParamSettingException(String message) {
		super(message);
	}

	public ParamSettingException(Throwable cause) {
		super(cause);
	}

}
