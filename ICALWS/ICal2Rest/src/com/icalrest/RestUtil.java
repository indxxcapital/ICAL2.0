package com.icalrest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.DataService.ConfigUtil;

public class RestUtil {

	public static void main(String[] args) 
	{
		System.out.println("HELLO REST UTIL ");
	}
	public static final String BASE_FILE_PATH = ConfigUtil.propertiesMap.get("BASE_FILE_PATH");
	
	public static final String INPUT_FILE_PATH 				= BASE_FILE_PATH + "input-file.csv";
	public static final String OUT_FILE_PATH				= BASE_FILE_PATH + "missing-securities.csv";
	public static final String ADD_SECURITY_FILE_PATH 		= BASE_FILE_PATH + "add-securities.csv";
	public static final String INDEX_INPUT_FILE_PATH 		= BASE_FILE_PATH + "index-input-file.csv";
	public static final String SECURITY_MAP_FILE_PATH 		= BASE_FILE_PATH + "map-securities.csv";
	public static final String ADD_SECURITY_PRICE_FILE_PATH = BASE_FILE_PATH + "security-price.csv";
	public static final String CURRENCY_INPUT_FILE_PATH 	= BASE_FILE_PATH + "currency-input-file.csv";
	public static final String CURRENCY_OUT_FILE_PATH 		= BASE_FILE_PATH + "missing-currencies.csv";
	public static final String ADD_CURRENCY_FILE_PATH 		= BASE_FILE_PATH + "add-currencies.csv";
	
//	public static final String INDEX_CLOSE_FILE_PATH = "c://temp//closing";
//	public static final String INDEX_OPEN_FILE_PATH = "c://temp//opening";
	
	public static final String INDEX_PRE_OPEN_FILE_PATH = ConfigUtil.propertiesMap.get("INDEX_PRE_OPEN_FILE_PATH");
	
	public static void writeToFile(InputStream uploadedInputStream,String uploadedFileLocation) 
	{
		try
		{
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
