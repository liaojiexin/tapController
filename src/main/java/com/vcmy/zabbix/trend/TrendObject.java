package com.vcmy.zabbix.trend;

/**
 * History objects differ depending on the item's type of information.</br>
 * They are created by the Zabbix server and cannot be modified via the API.
 * <p/>
 * Created by Suguru Yajima on 2014/06/03.
 */
public class TrendObject {

    /**公共数据部分*/
    //收到该值的时间
    private Long clock;
    //相关项的ID
    private Integer itemid;
    //收到值时纳秒
    private Integer ns;
    //每小时最大值
    private String value_max;
    //每小时最小值
    private String value_min;
    //每小时平均值
    private String value_avg;

    public TrendObject() {
    }

	public Long getClock() {
		return clock;
	}

	public void setClock(Long clock) {
		this.clock = clock;
	}

	public Integer getItemid() {
		return itemid;
	}

	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}

	public Integer getNs() {
		return ns;
	}

	public void setNs(Integer ns) {
		this.ns = ns;
	}

	public String getValue_max() {
		return value_max;
	}

	public void setValue_max(String value_max) {
		this.value_max = value_max;
	}

	public String getValue_min() {
		return value_min;
	}

	public void setValue_min(String value_min) {
		this.value_min = value_min;
	}

	public String getValue_avg() {
		return value_avg;
	}

	public void setValue_avg(String value_avg) {
		this.value_avg = value_avg;
	}
	
}
