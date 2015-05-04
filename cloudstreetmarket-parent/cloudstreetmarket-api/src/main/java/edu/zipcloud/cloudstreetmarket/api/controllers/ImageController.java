package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value = "images", description = "Cloudstreet Market uploaded images") // Swagger annotation
@RestController
@RequestMapping(value="/images")
public class ImageController extends CloudstreetApiWCI{

	@RequestMapping(value="/users/{fileName}.{extension}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get uploaded images for users")
	public InputStreamResource getUploadedImages(@PathVariable String fileName, @PathVariable String extension, HttpServletResponse response) throws IOException{
    	String pathToUserPictures = env.getProperty("pictures.user.path").concat("\\");
    	Path URI = Paths.get(pathToUserPictures.concat(fileName.concat(".").concat(extension)));
    	
    	response.setContentType(Files.probeContentType(URI));
    	response.setContentLength((int) URI.toFile().length());
    	
    	return new InputStreamResource(Files.newInputStream(URI));
	}
}