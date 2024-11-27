package fr.cyu.coffeeclasses.spring.controller.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/css/pages")
public class CssController {
	@Autowired
	private ResourceLoader resourceLoader;

	@GetMapping("**.css")
	public ResponseEntity<String> serveCssFile(HttpServletRequest request) throws IOException {
		// Extract the requested CSS path
		String cssPath = request.getRequestURI().replace("/css/pages/", "");
		Resource cssFile = resourceLoader.getResource("classpath:static/css/pages/" + cssPath);

		if (cssFile.exists() && cssFile.isReadable()) {
			// If the file exists, serve it as CSS
			Path filePath = cssFile.getFile().toPath();
			String cssContent = Files.readString(filePath);
			return ResponseEntity.ok()
					.contentType(MediaType.valueOf("text/css"))
					.body(cssContent);
		} else {
			// If the file does not exist, serve an empty CSS fallback
			return ResponseEntity.ok()
					.contentType(MediaType.valueOf("text/css"))
					.body("/* Empty CSS fallback */");
		}
	}
}
