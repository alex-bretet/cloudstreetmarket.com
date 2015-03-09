package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.services.ICommunityService;
import edu.zipcloud.util.ImageUtil;

@Api(value = "users", description = "Cloudstreet Market users") // Swagger annotation
@RestController
@RequestMapping(value="/users", produces={"application/xml", "application/json"})
public class CommunityController extends CloudstreetApiWCI{

	@Autowired
	@Qualifier("communityServiceImpl")
	private ICommunityService communityService;
	
	@RequestMapping(value="/activity", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Gets public user activities", notes = "Return a page of user-activities")
	public Page<UserActivityDTO> getPublicActivities(
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"quote.date"}, direction=Direction.DESC) Pageable pageable){
		return communityService.getPublicActivity(pageable);
	}
	
	@RequestMapping(value="", method=POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a user account")
	public void createUser(@RequestBody User user){
		communityService.createUser(user, Role.ROLE_BASIC);
	}
	
	@RequestMapping(value="/login", method=POST)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Identifies the provided user")
	public void login(@RequestBody User user){
		communityService.identifyUser(user);
	}
	
	@RequestMapping(value="", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List user accounts", notes = "")
	public Page<User> getUsers(@ApiIgnore @PageableDefault(size=10, page=0) Pageable pageable){
		return communityService.findAll(pageable);
	}
	
	@RequestMapping(value="/{username}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List user accounts", notes = "")
	public User getUser(@PathVariable String username){
		return communityService.findOne(username);
	}
	
    @RequestMapping(value="/file", method=POST,produces={"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public String saveImage(@RequestPart("file") MultipartFile file, HttpServletResponse response){
    	String extension = ImageUtil.getExtension(file.getOriginalFilename());
    	String name = UUID.randomUUID().toString().concat(".").concat(extension);
    	
    	String pathToUserPictures = env.getProperty("pictures.user.path").concat("\\").concat(name);
    	
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path newPath = Paths.get(pathToUserPictures);
                Files.write(newPath, bytes, StandardOpenOption.CREATE);
                ImageUtil.createThumbnail(newPath.toFile(), 72, 72, extension);
                response.addHeader("Location", env.getProperty("pictures.user.endpoint").concat(name));
                return "Success";
            } catch (IOException e) {
                return "Fail: " + e.getMessage();
            }
        } else {
            return "Fail: the file was empty!";
        }
    }

}