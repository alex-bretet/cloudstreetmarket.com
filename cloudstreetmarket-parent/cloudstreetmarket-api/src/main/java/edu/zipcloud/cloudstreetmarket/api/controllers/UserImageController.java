/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import edu.zipcloud.cloudstreetmarket.shared.util.Constants;
import edu.zipcloud.core.util.ImageUtil;

@Api(value = "user images", description = "Cloudstreet Market user-uploaded images") // Swagger annotation
@RestController
@RequestMapping(value="/images/users")
public class UserImageController extends CloudstreetApiWCI{
	
	private static final Logger logger = LogManager.getLogger(UserImageController.class);
	
	@RequestMapping(value="/{fileName}.{extension}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get uploaded images for users")
	public InputStreamResource get(@PathVariable String fileName, @PathVariable String extension, HttpServletResponse response) throws IOException{
    	String pathToUserPictures = env.getProperty("user.home").concat(env.getProperty("pictures.user.path")+File.separator);
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
    	
    	String pathToUserPictures = env.getProperty("user.home").concat(env.getProperty("pictures.user.path")).concat(File.separator).concat(name);
    	String pathToMiniUserPictures = ImageUtil.renameToMini(pathToUserPictures);
    	String pathToBigUserPictures = ImageUtil.renameToBig(pathToUserPictures);
    	
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path newPath = Paths.get(pathToUserPictures);

                Files.write(newPath, bytes, StandardOpenOption.CREATE);
                ImageUtil.createThumbnail(newPath.toFile(), 72, 72, extension);
                
                newPath = Paths.get(pathToMiniUserPictures);
                Files.write(newPath, bytes, StandardOpenOption.CREATE);
                ImageUtil.createThumbnail(newPath.toFile(), 48, 48, extension);

                newPath = Paths.get(pathToBigUserPictures);
                Files.write(newPath, bytes, StandardOpenOption.CREATE);
                ImageUtil.createThumbnail(newPath.toFile(), 125, 125, extension);
                
            	logger.info("User {} uploads a new profile picture: {}", getPrincipal().getUsername(), name);
            	
                response.addHeader(Constants.LOCATION_HEADER, env.getProperty("pictures.user.endpoint").concat(name));
                return "Success";
            } catch (IOException e) {
                return "Fail: " + e.getMessage();
            }
        } else {
            return "Fail: the file was empty!";
        }
    }
    
    @RequestMapping(method=DELETE, produces={"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String delete(@PathVariable String fileName, @PathVariable String extension) {
    	String pathToUserPictures = env.getProperty("user.home").concat(env.getProperty("pictures.user.path")).concat(File.separator);
    	Path path = Paths.get(pathToUserPictures.concat(fileName.concat(".").concat(extension)));
    	
        try {
            Files.delete(path);
            return "Success";
        } catch (IOException e) {
            return "Fail: " + e.getMessage();
        }
    }
}