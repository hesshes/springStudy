package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.ExVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/ex")
@Log4j
public class ExController {

	@GetMapping(value = "/getText", produces = "text/plain;charset=UTF-8")
	public String getText() {
		log.info("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);

		return "안녕하세요";
	}

	@GetMapping(value = "/getEx", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ExVO getEx() {
		return new ExVO(112, "스타", "로드");
	}

	@GetMapping(value = "/getEx2")
	public ExVO getEx2() {
		return new ExVO(112, "로켓", "라쿤");
	}

	@GetMapping(value = "/getList")
	public List<ExVO> getList() {
		return IntStream.range(1, 10).mapToObj(i -> new ExVO(i, i + " First", i + " Last"))
				.collect(Collectors.toList());
	}

	@GetMapping(value = "/getMap")
	public Map<String, ExVO> getMap() {
		Map<String, ExVO> map = new HashMap<>();
		map.put("First", new ExVO(111, "그루트", "주니어"));

		return map;
	}

	@GetMapping(value = "/check", params = { "height", "weight" })
	public ResponseEntity<ExVO> check(Double height, Double weight) {
		ExVO vo = new ExVO(0, "" + height, "" + weight);

		ResponseEntity<ExVO> result = null;

		if (height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		} else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}

		return result;
	}

	@GetMapping("/produt/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {

		return new String[] { "category : " + cat, "product : " + pid };

	}

	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("Convert...........ticket : " + ticket);
		return ticket;
	}
}
