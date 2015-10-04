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
package edu.zipcloud.core.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

public class ImageUtil {
	
	public static String getExtension(String fileName){
		if(fileName.contains(".")){
			return fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		}
		throw new IllegalArgumentException("The provided file name is missing an extension!");
	}
	
	public static void createThumbnail(File sourceImage, int width, int height, String extension) throws IOException {
		  BufferedImage img = ImageIO.read(sourceImage); // load image
		  BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY,Mode.AUTOMATIC, 
				  width, height, Scalr.OP_ANTIALIAS);
		  
		   //convert bufferedImage to outpurstream 
		  ByteArrayOutputStream os = new ByteArrayOutputStream();
		  ImageIO.write(thumbImg,extension.toLowerCase(),os);
		  ImageIO.write(thumbImg, extension.toLowerCase(), sourceImage);
	}
	
	public static String renameToMini(String fileName){
		String ext = "."+getExtension(fileName);
		return fileName = fileName.substring(0, fileName.lastIndexOf(ext))+"-mini"+ext;
	}
	
	public static String renameToBig(String fileName){
		String ext = "."+getExtension(fileName);
		return fileName = fileName.substring(0, fileName.lastIndexOf(ext))+"-big"+ext;
	}
}
