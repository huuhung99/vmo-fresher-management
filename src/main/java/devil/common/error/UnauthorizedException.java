package devil.common.error;

import devil.common.enums.ResponseCode;

public class UnauthorizedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ResponseCode code;

	public UnauthorizedException(ResponseCode code, String s) {
		super(s);
		this.code = code;
	}
	
	public ResponseCode getCode() {
		return this.code;
	}
}
