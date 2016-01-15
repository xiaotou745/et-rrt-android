package com.renrentui.resultmodel;

import java.io.Serializable;

public class ImageInfo implements Serializable {
	public String remark;//异常信息，如果为空则表示成功
	/** 文件的访问地址 */
	public String fileUrl;
	/** 保存到数据库的相对路径 */
	public String relativePath;
	/** 上传的文件名称 */
	public String originalName;
	/** 上传的文件名称修改后的名称 */
	public String modifyOriginalName;

	public ImageInfo(String remark, String fileUrl, String relativePath, String originalName, String modifyOriginalName) {
		super();
		this.remark = remark;
		this.fileUrl = fileUrl;
		this.relativePath = relativePath;
		this.originalName = originalName;
		this.modifyOriginalName = modifyOriginalName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public void setModifyOriginalName(String modifyOriginalName) {
		this.modifyOriginalName = modifyOriginalName;
	}

	public String getRemark() {
		return remark;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public String getOriginalName() {
		return originalName;
	}

	public String getModifyOriginalName() {
		return modifyOriginalName;
	}

	public ImageInfo() {
		super();
	}

	@Override
	public String toString() {
		return "ImageInfo{" +
				"remark='" + remark + '\'' +
				", fileUrl='" + fileUrl + '\'' +
				", relativePath='" + relativePath + '\'' +
				", originalName='" + originalName + '\'' +
				", modifyOriginalName='" + modifyOriginalName + '\'' +
				'}';
	}
}
