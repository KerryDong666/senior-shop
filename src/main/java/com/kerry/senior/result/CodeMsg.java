package com.kerry.senior.result;

public class CodeMsg {
	private int code;
	private String msg;
	
	//通用错误码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");
	//登录模块 5002XX
    public static CodeMsg MOBILE_HAS_REGISTER = new CodeMsg(500201, "该手机号已经注册,不能重复注册");
    public static CodeMsg MOBILE_NON_REGISTER = new CodeMsg(500201, "手机号未注册");
    public static CodeMsg PWD_ERROR = new CodeMsg(500203, "密码错误");
	//商品模块 5003XX
	
	//订单模块 5004XX
	
	//秒杀模块 5005XX
	
	
	private CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }
	
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
