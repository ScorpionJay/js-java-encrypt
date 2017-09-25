
package org.demo.controller;

import org.demo.util.EncryptUtil;
import org.demo.vo.TestVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController.java
 * @author jay
 */
@RestController
public class TestController {

	@RequestMapping(value = "test", method = RequestMethod.POST)
	public String test(@RequestBody TestVo testVo) {
		System.out.println("test");

		String str = EncryptUtil.des(testVo.getStr(), testVo.getKey());
		System.out.println(str);

		return "解密内容为:" + str;
	}
}
