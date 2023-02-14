package org.zerock.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@PropertySource("classpath:/config/globals.properties")
public class UploadController {

	@Autowired
	private Environment env;

	@GetMapping("/uploadForm")
	public void uploadForm() {

		log.info("uploadForm..............");

	}

	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) throws IOException {
		String uploadFolder = env.getProperty("file.path").replace("/", File.separator);

		for (MultipartFile files : uploadFile) {

			log.info("-------------------------------------");
			log.info("Upload File Name : " + files.getOriginalFilename());
			log.info("upload file size : " + files.getSize());

			File saveFile = new File(uploadFolder, files.getOriginalFilename());

			try {
				files.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

		} // for end
	}

	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
}
