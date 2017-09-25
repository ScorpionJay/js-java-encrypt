package org.demo.vo;


/**
 * LoginVo.java
 * @author  jay
 */
public class TestVo {

	private String key;
	private String str;
	public TestVo(String key, String str) {
		super();
		this.key = key;
		this.str = str;
	}
	
	public TestVo() {
		super();
	}
	
	public String getKey(){
		return this.key;
	}
	
	public String getStr(){
		return this.str;
	}
	
}
