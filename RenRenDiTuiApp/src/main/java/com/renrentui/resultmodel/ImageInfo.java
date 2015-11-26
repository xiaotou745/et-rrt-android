package com.renrentui.resultmodel;

import java.io.Serializable;

public class ImageInfo implements Serializable {
	/** 文件的访问地址 */
	public String FileUrl;
	/** 保存到数据库的相对路径 */
	public String RelativePath;
	/** 上传的文件名称 */
	public String OriginalName;
	/** 上传的文件名称修改后的名称 */
	public String ModifyOriginalName;

	public ImageInfo(String fileUrl, String relativePath, String originalName,
			String modifyOriginalName) {
		super();
		FileUrl = fileUrl;
		RelativePath = relativePath;
		OriginalName = originalName;
		ModifyOriginalName = modifyOriginalName;
	}

	public ImageInfo() {
		super();
	}

	@Override
	public String toString() {
		return "ImageInfo[FileUrl=" + FileUrl + ",RelativePath=" + RelativePath
				+ ",OriginalName=" + OriginalName + ",ModifyOriginalName="
				+ ModifyOriginalName + "]";
	}
}
