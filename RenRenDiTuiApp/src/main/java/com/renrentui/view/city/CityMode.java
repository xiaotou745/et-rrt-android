package com.renrentui.view.city;

public class CityMode {
	/**
	 * 区ID
	 */
	private String CountryID;
	/**
	 * 市ID
	 */
	private String CityID;
	/**
	 * 省市区名称（带…）
	 */
	private String Name;
	/**
	 * 省ID
	 */
	private String ProvinceID;
	/**
	 * 省市区全名
	 */
	private String AllName;

	public String getCountryID() {
		return CountryID;
	}

	public void setCountryID(String countryID) {
		CountryID = countryID;
	}

	public String getCityID() {
		return CityID;
	}

	public void setCityID(String cityID) {
		CityID = cityID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getProvinceID() {
		return ProvinceID;
	}

	public void setProvinceID(String provinceID) {
		ProvinceID = provinceID;
	}

	public String getAllName() {
		return AllName;
	}

	public void setAllName(String allName) {
		AllName = allName;
	}

	@Override
	public String toString() {
		return "CityMode [ProvinceID=" + ProvinceID + ", CityID=" + CityID
				+ ",CountryID=" + CountryID + ",Name=" + Name + ",AllName="
				+ AllName + "]";
	}

}
