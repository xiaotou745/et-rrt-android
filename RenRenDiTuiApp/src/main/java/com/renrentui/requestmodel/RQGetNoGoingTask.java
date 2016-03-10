package com.renrentui.requestmodel;

/**
 * 获取未领取任务的请求类
 * 
 * @author llp
 */
public class RQGetNoGoingTask extends RQBase {
	/**
	 * 用户ID
	 */
	public String userId;
	/**
	 * 用户地理位置的经度
	 */
	public float longitude;
	/**
	 * 用户地理位置的纬度
	 */
	public float latitude;
	/**
	 * 为分页处理服务，获取任务的个数。当为0或者不填的时候，默认全部获取
	 */
	public int itemsCount = 10;
	/**
	 * 为分页处理服务，上页获取到的任务列表中给的参数。不填的时候默认从头获取
	 */
	public String nextId;
	/**
	 * 城市编码
	 */
	public String cityCode;
	/**
	 * 城市名称
	 */
	public String cityName;
	//排列顺序
	public int  orderBy;//排序方式 1是佣金，2是审核周期，3是预计用时，4是参与人数，5是发布时间（int类型）

	//
	public String platform="android";//平台

	public RQGetNoGoingTask() {
		super();
	}
	public RQGetNoGoingTask(String userId,String nextId,String cityCode,int  orderBy) {
		super();
		this.userId = userId;
		this.nextId = nextId;
		this.cityCode = cityCode;
		this.orderBy = orderBy;
	}

	public RQGetNoGoingTask(String userId, float longitude, float latitude,
			String nextId,int orderBy) {
		super();
		this.userId = userId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.nextId = nextId;
		this.orderBy = orderBy;
	}

	public RQGetNoGoingTask(String userId, float longitude, float latitude,
			int itemsCount, String nextId,int orderBy) {
		super();
		this.userId = userId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.itemsCount = itemsCount;
		this.nextId = nextId;
		this.orderBy = orderBy;
	}

	public RQGetNoGoingTask(String userId, float longitude, float latitude,
			int itemsCount, String nextId, String cityCode,int orderBy) {
		super();
		this.userId = userId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.itemsCount = itemsCount;
		this.nextId = nextId;
		this.cityCode = cityCode;
		this.orderBy = orderBy;
	}

	public RQGetNoGoingTask(String userId, float longitude, float latitude,
			int itemsCount, String nextId, String cityCode, String cityName,int orderBy) {
		super();
		this.userId = userId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.itemsCount = itemsCount;
		this.nextId = nextId;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.orderBy = orderBy;
	}

//	@Override
//	public String toString() {
//		return "RQGetNoGoingTask[userId=" + userId + ",longitude=" + longitude
//				+ ",latitude=" + latitude + ",itemsCount=" + itemsCount
//				+ ",nextId=" + nextId + ",cityCode=" + cityCode + ",cityName="
//				+ cityName + "]";
//	}


	@Override
	public String toString() {
		return "RQGetNoGoingTask{" +
				"userId='" + userId + '\'' +
				", longitude=" + longitude +
				", latitude=" + latitude +
				", itemsCount=" + itemsCount +
				", nextId='" + nextId + '\'' +
				", cityCode='" + cityCode + '\'' +
				", cityName='" + cityName + '\'' +
				", orderBy=" + orderBy +
				'}';
	}
}
