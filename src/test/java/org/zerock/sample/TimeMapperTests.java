package org.zerock.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.mapper.TimeMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TimeMapperTests {
	@Setter(onMethod_ = @Autowired)
	private TimeMapper tm;

	@Test
	public void testGetTime() {
		log.info(tm.getClass().getName());
		log.info(tm.getTime());
	}

	@Test
	public void testGetTime2() {
		log.info("timeMapper getTime2() Call!!!");
		log.info(tm.getTime2() + "TESTESTEWST");
	}

}
