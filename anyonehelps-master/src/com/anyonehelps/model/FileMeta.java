package com.anyonehelps.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({"bytes"})
public class FileMeta implements Serializable{
	private static final long serialVersionUID = -9120561138152097857L;
	private String fileName;
	private String saveFileName;
	private String fileSize;
	private String fileType;
	
	private String worksId; //增加产品时，返回id
	
	private byte[] bytes;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public String getWorksId() {
		return worksId;
	}
	public void setWorksId(String worksId) {
		this.worksId = worksId;
	}
	
}
