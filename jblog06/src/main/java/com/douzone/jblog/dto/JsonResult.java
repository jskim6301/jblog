package com.douzone.jblog.dto;

public class JsonResult {
	private String result;
	private Object data;
	private String message;
	
	private JsonResult(Object data) {
		this.result = "success";
		this.data = data;
	}
	
	private JsonResult(String message) {
		this.result = "fail";
		this.message = message;
	}
	
	public static JsonResult success(Object data) {
		return new JsonResult(data);
	}
	
	public static JsonResult fail(String message) {
		return new JsonResult(message);
	}

	public String getResult() {
		return result;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}
	
	
}
