package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardServiceTests {

	@Setter(onMethod_ = @Autowired)
	BoardService boardService;

	/*
	 * @Test public void testExist() { log.info(boardService);
	 * assertNotNull(boardService); }
	 * 
	 * @Test public void testRegist() { BoardVO board = new BoardVO();
	 * board.setContent("새로운 글 내용입니다."); board.setWriter("tester");
	 * board.setTitle("새로운 글 제목입니다."); boardService.register(board);
	 * log.info("생성된 게시물 번호 : " + board.getBno());
	 * 
	 * }
	 */

	@Test
	public void testGetList() {
		boardService.getList(new Criteria(2,10)).forEach(board -> log.info(board));
	}
//
//	@Test
//	public void testGet() {
//		log.info(boardService.get(5L));
//	}
//
//	@Test
//	public void testRemove() {
//		log.info("Remove Result : " + boardService.remove(5L));
//	}
//
//	@Test
//	public void testUpdate() {
//		BoardVO board = new BoardVO();
//		board.setBno(5L);
//		board.setContent("게시글 수정 내용");
//		board.setTitle("게시글 수정 제목");
//		board.setWriter("modifier");
//		log.info("Update Result : " + boardService.modify(board));
//
//	}
}
