package org.zerock.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@PropertySource("classpath:/config/globals.properties")
@RequestMapping("/board/*")
public class BoardController {

	// spring 4.3 이상에서 단일 파라미터를 받는 생성자의 경우 필요한 파라미터를 자동으로 주입 가능
	// 즉, lombok 사용시 AllArgsConstructor 어노테이션을 사용한 경우 autowired 생략 가능.

	// @Autowired
	@Setter(onMethod_ = @Autowired)
	private BoardService boardService;

	@Setter(onMethod_ = @Autowired)
	private Environment env;

	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("list=============================");
		model.addAttribute("list", boardService.getList(cri));

		int total = boardService.getTotal(cri);

		log.info("total : " + total);

		model.addAttribute("pageMaker", new PageDTO(cri, total));

	}

	@GetMapping("/register")
	public void register() {

	}

	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {

		log.info("=================================");

		log.info("register: " + board);

		if (board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}

		log.info("=================================");

		boardService.register(board);

		rttr.addFlashAttribute("result", board.getBno());

		// forward, redirect의 차이
		// 간단하게 설명하면 요청 정보를 유지 vs 새로 요청 받음
		// url 정보 유지 (forward) , 변화(redirect)
		// 요청 객체 재사용 (forward), 새로운 객체(redirect)

		return "redirect:/board/list";
	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("/get or /modify");
		model.addAttribute("board", boardService.get(bno));
	}

	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr, @ModelAttribute("cri") Criteria cri) {

		log.info("modify : " + board);

		if (boardService.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}

		return "redirect:/board/list" + cri.getListLink();
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr, @ModelAttribute("cri") Criteria cri) {

		log.info("remove.... : " + bno);

		List<BoardAttachVO> attachList = boardService.getAttachList(bno);
		
		if (boardService.remove(bno)) {
			deleteFiles(attachList);
			rttr.addFlashAttribute("result", "success");
		}

		return "redirect:/board/list" + cri.getListLink();
	}

	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {

		log.info("getAttachList " + bno);

		return new ResponseEntity<>(boardService.getAttachList(bno), HttpStatus.OK);
	}

	private void deleteFiles(List<BoardAttachVO> attachList) {
		if (attachList == null || attachList.size() == 0) {
			return;
		}

		log.info("delete attach files.............................");
		log.info(attachList);
		attachList.forEach(attach -> {
			try {
				String filePath = env.getProperty("file.path").trim().replace("/", File.separator);
				Path file = Paths
						.get(filePath + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());

				Files.deleteIfExists(file);

				if (Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get(
							filePath + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_" + attach.getFileName());
					Files.delete(thumbNail);
				}

			} catch (Exception e) {

				log.error("delete file error " + e.getMessage());

			}
		});
	}
}
