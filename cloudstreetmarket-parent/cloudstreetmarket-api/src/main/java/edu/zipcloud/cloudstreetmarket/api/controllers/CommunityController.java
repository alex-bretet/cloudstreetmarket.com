package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.core.enums.Role.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import edu.zipcloud.cloudstreetmarket.core.dtos.UserActivityDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.UserDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.Role;
import edu.zipcloud.cloudstreetmarket.core.services.CommunityService;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.core.util.ImageUtil;

@Api(value = "users", description = "Cloudstreet Market users") // Swagger annotation
@RestController
@RequestMapping(value="/users", produces={"application/xml", "application/json"})
public class CommunityController extends CloudstreetApiWCI{

	@Autowired
	private CommunityService communityService;

	@Autowired
	private SocialUserService socialUserService;
	
	@RequestMapping(value="/activity", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Gets public user activities", notes = "Return a page of user-activities")
	public Page<UserActivityDTO> getPublicActivities(
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"date"}, direction=Direction.DESC) Pageable pageable){
		return communityService.getPublicActivity(pageable);
	}
	
	@RequestMapping(value="", method=POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a user account")
	public void createUser(@RequestBody User user, 
								@RequestHeader(value="Spi", required=false) String guid, 
									@RequestHeader(value="OAuthProvider", required=false) String provider,
										HttpServletResponse response) throws IllegalAccessException{
		if(isNotBlank(guid)){
			if(isUnknownToProvider(guid, provider) || socialUserService.isSocialUserAlreadyRegistered(guid)){
				throw new AccessDeniedException(guid+" @ "+ provider);
			}
			user = communityService.createUser(user, new Role[]{ROLE_BASIC, ROLE_OAUTH2});
			socialUserService.bindSocialUserToUser(guid, user, provider);
		}
		else{
			user = communityService.createUser(user, ROLE_BASIC);
		}
		communityService.signInUser(user);
		response.setHeader(MUST_REGISTER_HEADER, FALSE);
	}

	@RequestMapping(value="/login", method=POST)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Identifies the provided user")
	public void login(@RequestBody User user, 
							@RequestHeader(value="Spi", required=false) String guid, 
								@RequestHeader(value="OAuthProvider", required=false) String provider, 
									HttpServletResponse response){
		user = communityService.identifyUser(user);
		if(isNotBlank(guid)){
			socialUserService.bindSocialUserToUser(guid, user, provider);
		}
    	communityService.signInUser(user);
	}
	
	@RequestMapping(value="", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List user accounts", notes = "")
	public Page<UserDTO> getUsers(@ApiIgnore @PageableDefault(size=10, page=0) Pageable pageable){
		return communityService.getAll(pageable);
	}
	
	@RequestMapping(value="/{username}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Details one account", notes = "")
	public UserDTO getUser(@PathVariable String username){
		return communityService.getUser(username);
	}
	
	@RequestMapping(value="/{username}", method=DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete user account", notes = "")
	public void deleteUser(@PathVariable String username){
		communityService.delete(username);
	}

    @RequestMapping(value="/file", method=POST,produces={"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public String saveImage(@RequestPart("file") MultipartFile file, HttpServletResponse response){
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

	private boolean isUnknownToProvider(String guid, String providerId) {
		Preconditions.checkArgument(isNotBlank(providerId), "The OAuth2 provider is necessary!");
		
		Set<String> results = socialUserService.findUserIdsConnectedTo(providerId, Sets.newHashSet(guid));
		if(results==null || results.isEmpty()){
			return true;
		}
		return false;
	}
}