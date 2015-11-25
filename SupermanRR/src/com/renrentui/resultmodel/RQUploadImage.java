package com.renrentui.resultmodel;

import com.renrentui.requestmodel.RQBase;

public class RQUploadImage extends RQBase {
	public ImageInfo Result;

	public RQUploadImage(ImageInfo Result) {
		super();
		Result = Result;
	}

	public RQUploadImage() {
		super();
	}

	@Override
	public String toString() {
		return "RQUploadImage[Result=" + Result + "]";
	}
}
