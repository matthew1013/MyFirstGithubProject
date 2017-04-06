package com.anyonehelps.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jets3t.service.ServiceException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
/** 
 * 将Office文档转换为PDF. 运行该函数需要用到OpenOffice, OpenOffice下载地址为 
 * http://www.openoffice.org/ 
 *  
 * <pre> 
 * 方法示例: 
 * String sourcePath = "F:\\office\\source.doc"; 
 * String destFile = "F:\\pdf\\dest.pdf"; 
 * Converter.office2PDF(sourcePath, destFile); 
 * </pre> 
 *  
 * @param sourceFile 
 *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc, 
 *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc 
 * @param destFile 
 *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf 
 * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 0, 
 *         则表示操作成功; 返回1, 则表示转换失败 
 */  

public class Office2PdfUtil {
	private static final Logger log = Logger.getLogger(Office2PdfUtil.class);
	
	public static int office2PDF(String sourcePath, String filename, String destPath, String destFilename) {
		log.info(sourcePath + filename);
		try {  
            File inputFile = new File(sourcePath+filename);
            if (!inputFile.exists()) {  
            	log.error("file not found");
                return -1;// 找不到源文件, 则返回-1 
            }  
  
            // 如果目标路径不存在, 则新建该路径  
            File outputFile = new File(destPath+"/"+destFilename);  
            if (!outputFile.getParentFile().exists()) {  
                outputFile.getParentFile().mkdirs();  
            }  
            // connect to an OpenOffice.org instance running on port 8100  
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(  
                    "127.0.0.1", 8100);  
            connection.connect();  
  
            // convert  
            DocumentConverter converter = new OpenOfficeDocumentConverter(  
                    connection);  
            converter.convert(inputFile, outputFile);  
  
            // close the connection  
            connection.disconnect();  
            log.info("office2PDF End");
            return 0;  
        } catch (ConnectException e) {  
            e.printStackTrace();  
        }
  
        return 1;  
    }  
	
	/**
	 * 下载S3文件到临时文件下
	 * @param S3File
	 * @param tmpPath
	 * @return 0表示下载成功，1表示异常
	 */
	public static int downloadS3File(String S3File, String tmpPath, String filename){  
		log.info("download S3 file(" + S3File + ") to temp path(" + tmpPath + ")");
		
		try { 
			// 构造URL 
			URL url = new URL(S3File);
		
	        // 打开连接    
	        URLConnection con = url.openConnection();  
	        //设置请求超时为5s    
	        con.setConnectTimeout(5 * 1000);  
	        // 输入流    
	        InputStream is = con.getInputStream();  
	  
	        // 1K的数据缓冲    
	        byte[] bs = new byte[1024];  
	        // 读取到的数据长度    
	        int len;  
	        // 输出的文件流    
	        File sf = new File(tmpPath);  
	        if (!sf.exists()) {  
	            sf.mkdirs();  
	        }  
	        OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);  
	        // 开始读取    
	        while ((len = is.read(bs)) != -1) {  
	            os.write(bs, 0, len);  
	        }  
	        // 完毕，关闭所有链接    
	        os.close();  
	        is.close(); 
	        return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}  
    }  
	
	public static String S3Office2PDF(String S3File, String tmpPath, String savePath){  
		String result = null;
		String filename = S3File.substring(S3File.lastIndexOf('/')+1);
		String destFileName = getFileNameNoEx(filename)+".pdf";
		int downloadResult = downloadS3File(S3File, tmpPath, filename);
		if(downloadResult==0){
			//下载成功
			int n = office2PDF(tmpPath, filename,tmpPath, destFileName);
			if(n==0){
				//转换成功
				AmazonS3Samples amazonS3Samples = new AmazonS3Samples();
				File file = new File(tmpPath+destFileName);
				log.error("pdf:"+tmpPath+destFileName);
				if (file.exists()) { 
					String key = savePath + "/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+StringUtil.generateRandomInteger(5)+".pdf";
					try {
						amazonS3Samples.pushFile(key, file);
						result = key;
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//文件存在就删除
					File tmpFile = new File(tmpPath + "/" + filename);
		            if (tmpFile.exists()) {  
		            	tmpFile.delete();
		            }  
		            file.delete();
		        }
			}else{
				//转换失败,文件存在就删除
				File tmpFile = new File(tmpPath + "/" + filename);
	            if (tmpFile.exists()) {  
	            	tmpFile.delete();
	            }  
			}
			
			
			
		}else{
			//下载失败,文件存在就删除
			File tmpFile = new File(tmpPath + "/" + filename);
            if (tmpFile.exists()) {  
            	tmpFile.delete();
            }  
		}
		return result;
    }  

	
	/**
	 * 获取不带扩展名的文件名
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
    public static void main(String[] args) {
    	//int nResult = S3Office2PDF("https://s3-us-west-1.amazonaws.com/anyonehelps/task/249_ehLxZ_29991.docx", "/upload/converter_tmp"+"/", "converter");
    	//System.out.println(nResult);
    	/*int success = office2PDF("C:/Users/chenkanghua/Desktop/test.doc", "C:/Users/chenkanghua/Desktop/HelloWorld.pdf");
    	if(success == 0) System.out.println("成功");
    	else if(success == 1) System.out.println("失败");
    	else System.out.println("文件未找到");
    	*/
    
    }
}
