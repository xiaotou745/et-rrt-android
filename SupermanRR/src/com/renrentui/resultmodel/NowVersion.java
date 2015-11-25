package com.renrentui.resultmodel;

import java.io.Serializable;

public class NowVersion implements Serializable {
	/** 最新版本号 */
	public String version;
	/** 该版本是否强制更新 */
	public String isMust;
	/** 下载地址 */
	public String updateUrl;
	/** 版本升级提示信息 */
	public String message;

	public NowVersion(String version, String isMust, String updateUrl,
			String message) {
		super();
		this.version = version;
		this.isMust = isMust;
		this.updateUrl = updateUrl;
		this.message = message;
	}

	public NowVersion() {
		super();
	}

	@Override
	public String toString() {
		return "NowVersion[version=" + version + ",isMust=" + isMust
				+ ",updateUrl=" + updateUrl + ",message=" + message + "]";
	}
}
