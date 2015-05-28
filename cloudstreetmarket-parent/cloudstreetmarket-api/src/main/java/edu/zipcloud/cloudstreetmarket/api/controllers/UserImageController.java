package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import edu.zipcloud.core.util.ImageUtil;

@Api(value = "user images", description = "Cloudstreet Market user-uploaded images") // Swagger annotation
@RestController
@RequestMapping(value="/images/users/")
public class UserImageController extends CloudstreetApiWCI{

	@RequestMapping(value="/{fileName}.{extension}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get uploaded images for users")
	public InputStreamResource get(@PathVariable String fileName, @PathVariable String extension, HttpServletResponse response) throws IOException{
    	String pathToUserPictures = env.getProperty("pictures.user.path").concat("\\");
    	Path URI = Paths.get(pathToUserPictures.concat(fileName.concat(".").concat(extension)));
    	
    	response.setContentType(Files.probeContentType(URI));
    	response.setContentLength((int) URI.toFile().length());
    	
    	return new InputStreamResource(Files.newInputStream(URI));
	}

    @RequestMapping(method=POST, produces={"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestPart("file") MultipartFile file, HttpServletResponse response){
    	String extension = ImageUtil.getExtension(file.getOriginalFilename());
    	String name = UUID.randomUUID().toString().concat(".").concat(extension);
    	
    	String pathToUserPictures = env.getProperty("pictures.user.path").concat("\\").concat(name);
    	String pathToMiniUserPictures = ImageUtil.renameToMini(pathToUserPictures);
    	
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path newPath = Paths.get(pathToUserPictures);
                Files.write(newPath, bytes, StandardOpenOption.CREATE);
                ImageUtil.createThumbnail(newPath.toFile(), 72, 72, extension);
                
                newPath = Paths.get(pathToMiniUserPictures);
                Files.write(newPath, bytes, StandardOpenOption.CREATE);
                ImageUtil.createThumbnail(newPath.toFile(), 48, 48, extension);

                response.addHeader(LOCATION_HEADER, env.getProperty("pictures.user.endpoint").concat(name));
                return "Success";
            } catch (IOException e) {
                return "Fail: " + e.getMessage();
            }
        } else {
            return "Fail: the file was empty!";
        }
    }
    
    @RequestMapping(method=DELETE, produces={"application/json"})
	public String delete(@PathVariable String fileName, @PathVariable String extension) {
    	String pathToUserPictures = env.getProperty("pictures.user.path").concat("\\");
    	Path path = Paths.get(pathToUserPictures.concat(fileName.concat(".").concat(extension)));
    	
        try {
            Files.delete(path);
            return "Success";
        } catch (IOException e) {
            return "Fail: " + e.getMessage();
        }
    }
}