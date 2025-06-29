package com.emr.slgi.attachment;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attachment")
public class AttachmentController {
	private final AttachmentService attachmentService;
	private final String uploadDir = "/Users/Ksy/upload";
	
	@PostMapping("/insertAttachment")
	public ResponseEntity<Object> insertAttachment(
			@RequestParam("files") MultipartFile[] files,
			@RequestParam("treatmentId") int treatmentId){
		
		if(files.length==0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"생성되지 않았습니다.");
		}
		
		attachmentService.insertAttachment(files,treatmentId);
		return ResponseEntity.ok("첨부파일 업로드 성공");
	}
	
	@GetMapping("/{fileName}")
	public ResponseEntity<Object> getFile(@PathVariable("fileName") String fileName) throws IOException {
		Path path = Paths.get(uploadDir).resolve(fileName);
		Resource resource = new UrlResource(path.toUri());
		
		if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다.");
        }
		return ResponseEntity.ok()
                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resource);
	}
	
	

}
