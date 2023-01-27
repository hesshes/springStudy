package org.zerock.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.XmlWebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleTests {
	/*
	 * ApplicationContext 객체를 직접 불러오는 경우
	 * @RunWith , @ContextConfiguration 삭제
	 * root-context.xml 에 di 할 bean 들을 등록해야 함
	 * 이후 아래와 같이 ctx 등을 불러와서 사용
	 * ApplicationContext ctx = new GenericXmlApplicationContext(
	 * "file:src/main/webapp/WEB-INF/spring/root-context.xml");
	 * 
	 * private Restaurant res2 = ctx.getBean("restaruant", Restaurant.class);
	 */
	@Setter(onMethod_ = @Autowired)
	private Restaurant restaurant;

	@Test
	public void testExist() {
		assertNotNull(restaurant);
		log.info(restaurant);
		log.info("-----------------------------------------------");
		log.info(restaurant.getChef());

	}
}
