package org.zerock.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;

	@Test
	public void testGetList() {
		log.info("testGetList() ========================================");
		boardMapper.getList().forEach(board -> log.info(board));
	}

	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setWriter("newbie");

		boardMapper.insert(board);

		log.info("testInsert() ========================================");
		log.info(board);

	}

	@Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글 select Key");
		board.setContent("새로 작성하는 내용 select Key");
		board.setWriter("newbie");

		boardMapper.insertSelectKey(board);

		log.info("testInsertSelectKey() ========================================");
		log.info(board);

	}

	@Test
	public void testRead() {
		log.info("testRead() ========================================");
		BoardVO board = boardMapper.read(2L);

		log.info(board);
		System.out.println("testRead() : " + board.toString());

	}

	@Test
	public void testDelete() {
		log.info("testDelete() ========================================");
		int count = boardMapper.delete(1L);

		log.info("delete result : " + count);
	}

	@Test
	public void testUpdate() {
		log.info("testUpdate()================================");

		BoardVO board = new BoardVO();
		board.setContent("내용 수정");
		board.setTitle("제목 수정");
		board.setBno(2L);
		board.setWriter("modifer");
		
		int result = boardMapper.update(board);

		log.info("test update result : " + result);
	}

}
