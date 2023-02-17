package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
@PropertySource("classpath:/config/globals.properties")
public class UploadController {

	private Environment env;

//	private final String UPFOLDER = env.getProperty("file.path").trim().replace("/", File.separator);
	private final String UPFOLDER;

	public UploadController(Environment env) {
		this.env = env;
		this.UPFOLDER = env.getProperty("file.path").trim().replace("/", File.separator);
	}

	@GetMapping("/uploadForm")
	public void uploadForm() {

		log.info("uploadForm..............");

	}

	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) throws IOException {

		for (MultipartFile files : uploadFile) {

			log.info("-------------------------------------");
			log.info("Upload File Name : " + files.getOriginalFilename());
			log.info("upload file size : " + files.getSize());

			File saveFile = new File(UPFOLDER, files.getOriginalFilename());

			try {
				files.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}

		} // for end
	}

	@GetMapping("/uploadAjax")
	public void uploadAjax() {
	}

	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {

		List<AttachFileDTO> list = new ArrayList<>();

		String uploadFolderPath = getFolder();

		File uploadPath = new File(UPFOLDER, uploadFolderPath);

		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		for (MultipartFile multipartFile : uploadFile) {

			AttachFileDTO attachDTO = new AttachFileDTO();

			String uploadFileName = multipartFile.getOriginalFilename();

			// IE 를 위한 파일 경로 설정
			// 책의 예제 이기 때문에 아래와 같이 코딩
			// 사실상 이제 모든 브라우저가 크롬 엔진 기반으로 동작하기에 아래와 같은 코딩은 필요 없으며
			// FilenameUtils.getName 을 이용해 파일 이름을 가져와도 될 듯 함
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

			attachDTO.setFileName(uploadFileName);

			// String fileName22 =
			// FilenameUtils.getName(multipartFile.getOriginalFilename());

			// File saveFile = new File(UPFOLDER, uploadFileName);

			UUID uuid = UUID.randomUUID();

			uploadFileName = uuid.toString() + "_" + uploadFileName;

			try {

				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);

				if (checkImageType(saveFile)) {
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
					attachDTO.setImage(true);
				}
				list.add(attachDTO);

			} catch (Exception e) {
				log.error(e.getMessage());
			}

		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		
		log.info("fileName : " + fileName);
		
		File file = new File(UPFOLDER +File.separator + fileName);
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("Uesr-Agent") String UserAgent, String fileName) {

		Resource res = new FileSystemResource(UPFOLDER + File.separator + fileName);

		if (res.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String resName = res.getFilename();

		String resOriginalName = resName.substring(resName.indexOf("_") + 1);
		HttpHeaders headers = new HttpHeaders();

		try {

			String downloadName = null;

			if (UserAgent.contains("Trident")) {

				log.info("IE browser");

				downloadName = URLEncoder.encode(resOriginalName, "UTF-8").replace("\\", " ");

			} else if (UserAgent.contains("Edge")) {

				log.info("Edge browser");

				downloadName = URLEncoder.encode(resOriginalName, "UTF-8");

				log.info("edge name : " + downloadName);

			} else {

				log.info("Chrom browser");

				downloadName = new String(resOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}

			headers.add("Content-Disposition",
					"attachment; filename=" + new String(resOriginalName.getBytes("UTF-8"), "ISO-8859-1"));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(res, headers, HttpStatus.OK);
	}

	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	private boolean checkImageType(File file) {

		try {
			String contentyType = Files.probeContentType(file.toPath());
			Boolean b = contentyType.startsWith("image");
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
