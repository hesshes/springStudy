package org.zerock.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zerock.domain.ReplyVO;

import com.google.gson.Gson;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Log4j
public class ReplyControllerTests {

	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx;
	
	private MockMvc mock;
	
	@Before
	public void setup() {
		this.mock = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	/*
	 * @Test public void testNew() throws Exception { ReplyVO vo = new ReplyVO();
	 * vo.setBno(102L); vo.setReply("리플 테스트 내용"); vo.setReplyer("리플 작성자");
	 * 
	 * String jStr = new Gson().toJson(vo);
	 * 
	 * mock.perform(post("/replies/new").contentType(MediaType.APPLICATION_JSON).
	 * content(jStr)) .andExpect(status().is(200));
	 * 
	 * }
	 */
	
	@Test
	public void testRemove() throws Exception {
		mock.perform(get("/replies/42").contentType(MediaType.TEXT_PLAIN_VALUE))
		.andExpect(status().is(200));
	}
}
