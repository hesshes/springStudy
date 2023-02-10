package org.zerock.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@Log4j
public class ReplyMapperTests {

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper rpMapper;

	
	/*
	 * @Test public void testMapper() { log.info(rpMapper); }
	 * 
	 * @Test public void testInsert() { ReplyVO vo = new ReplyVO(); vo.setBno(102L);
	 * vo.setReply("리플 내용 테스트"); vo.setReplyer("리플 테스터");
	 * 
	 * log.info("result : " + rpMapper.insert(vo)); }
	 * 
	 * @Test public void testRead() { log.info("Reply Read Test..................");
	 * ReplyVO vo = rpMapper.read(61L); log.info(vo); }
	 * 
	 * @Test public void testRemove() { log.info("Remove...........test");
	 * log.info("result : " + rpMapper.remove(41L)); }
	 * 
	 * 
	 * @Test public void testUpdate() {
	 * log.info("update test......................"); ReplyVO vo =
	 * rpMapper.read(62L); vo.setReply("리플 내용 수정 테스트"); int result =
	 * rpMapper.update(vo); log.info("result : " + result); }
	 */
	
	@Test
	public void testGetList() {
		Criteria cri = new Criteria();
		List<ReplyVO> replies = rpMapper.getListWithPaging(cri, 102L);
		replies.forEach(reply -> log.info(reply));
	}
}
