package cn.tedu.store.entity;

public class City {
	private Integer id;
	private String provinceName;
	private String cityCode;
	private String cityName;

	public City() {
		super();
	}

	public City(Integer id, String provinceName, String cityCode, String cityName) {
		super();
		this.id = id;
		this.provinceName = provinceName;
		this.cityCode = cityCode;
		this.cityName = cityName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", provinceName=" + provinceName + ", cityCode=" + cityCode + ", cityName=" + cityName
				+ "]";
	}

}
