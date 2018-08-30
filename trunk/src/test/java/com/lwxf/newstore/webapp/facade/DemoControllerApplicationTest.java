package com.lwxf.newstore.webapp.facade;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.newstore.webapp.WebAppStart;
import com.lwxf.newstore.webapp.baseservice.cache.RedisUtils;
import com.lwxf.newstore.webapp.common.uniquecode.UniquneCodeGenerator;
import com.lwxf.newstore.webapp.controller.api.demo.DemoController;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-08-14 9:31
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoControllerApplicationTest {
	@Autowired
	private MockMvc mvc;
	@Test
	public void testUniquneCodeGenerator() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/api/demos/createUniquneCode").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
