package com.fw121.core.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面响应entity
 * 创建者  科帮网  
 * 创建时间	2017年11月20日
 */
public class R extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", 0);
	}
	
	public static R error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(500, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Object o) {
		R r = new R();
		r.put("data", o);
		return r;
	}

	/**
	 * 返回分页数据
	 * @param o	数据对象
	 * @param total	总数据量
     * @return
     */
	public static R pageData(Object o, Integer total) {
		R r = new R();
		r.put("data", o);
		r.put("total", total);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}