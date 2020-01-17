package in.co.sdrc.newsapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.co.sdrc.newsapp.service.HomeService;

import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description="Contains endpoints to deal with home page work")
public class HomeController {

	@Autowired
	private HomeService service;

	@GetMapping("/")
	@ApiOperation(value = "The startup page of the application")
	public String home() {
		return "Welcome to SDRC Mobile app API, please hit the end point '/v2/api-docs' or '/swagger-ui.html'";
	}

	@GetMapping("/logFile/{date}")
	@ApiOperation(value = "Get the log file by passing required parameter")
	public ResponseEntity<Resource> logFile(@PathVariable String date, HttpServletRequest request) {

		// Load file as Resource
		Resource resource = service.loadFileAsResource("sdrcapp-"+ date + ".log");
		
		// Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
}
