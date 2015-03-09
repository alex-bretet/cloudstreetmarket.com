package edu.zipcloud.util;

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

}
