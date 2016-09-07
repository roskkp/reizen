package com.reizen.domain;

import java.util.Arrays;

import com.google.gson.annotations.Expose;

public class FileMeta {

    private String fileName;
    private String fileSize;
    private String fileType;
 
    @Expose private byte[] bytes; // 바인딩 에러 처리 

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

	@Override
	public String toString() {
		return "FileMeta [fileName=" + fileName + ", fileSize=" + fileSize + ", fileType=" + fileType + ", bytes="
				+ Arrays.toString(bytes) + "]";
	}

}
