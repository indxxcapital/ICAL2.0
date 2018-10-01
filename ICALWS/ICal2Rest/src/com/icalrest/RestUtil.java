package com.icalrest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RestUtil {

	public static void main(String[] args) 
	{
		System.out.println("HELLO REST UTIL ");
	}
	
	public static final String INPUT_FILE_PATH = "c://temp/"+ "input-file.csv";
	public static final String OUT_FILE_PATH = "c://temp/"+ "missing-securities.csv";
	public static final String ADD_SECURITY_FILE_PATH = "c://temp/"+ "add-securities.csv";
	public static final String INDEX_INPUT_FILE_PATH = "c://temp/"+ "index-input-file.csv";
	public static final String SECURITY_MAP_FILE_PATH = "c://temp/"+ "map-securities.csv";
	public static final String INDEX_CLOSE_FILE_PATH = "c://temp//closing";
	public static final String INDEX_OPEN_FILE_PATH = "c://temp//opening";
	public static final String INDEX_PRE_CLOSE_FILE_PATH = "c://temp//preopening//";
	public static final String ADD_SECURITY_PRICE_FILE_PATH = "c://temp/"+ "security-price.csv";
	
	public static final String CURRENCY_INPUT_FILE_PATH = "c://temp/"+ "currency-input-file.csv";
	public static final String CURRENCY_OUT_FILE_PATH = "c://temp/"+ "missing-currencies.csv";
	public static final String ADD_CURRENCY_FILE_PATH = "c://temp/"+ "add-currencies.csv";
	
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
