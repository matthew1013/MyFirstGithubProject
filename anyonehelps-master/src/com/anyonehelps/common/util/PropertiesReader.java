package com.anyonehelps.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class PropertiesReader {
	private static Logger logger = Logger.getLogger(PropertiesReader.class);

	/**
	 * 获取一个properties对象，如果file以'/'开始，表示文件从classpath路径下开始，如果不是，
	 * 则表示从该类的class文件所在目录下开始。
	 * 
	 * @param file
	 *            properties 文件的路径。
	 * @return properties 对象
	 */
	public static Properties read(String file) {
		InputStream in = PropertiesReader.class.getResourceAsStream(file);
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			logger.error("不能加载properties文件", e);
			throw new RuntimeException("不能加载properties文件");
		} finally {
			IOUtils.closeQuietly(in);
		}
		return properties;
	}
	
}
