/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Suguru Yajima
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.vcmy.zabbix.problem;


/**
 * Created by Suguru Yajima on 2014/05/08.
 */
public class ProblemObject {

    private String eventid;
    private Integer source;
    private Integer object;
    private String objectid;
    private Integer ns;
    private String r_eventid;
    private Integer r_ns;
    private Long clock;
    private Long r_clock;
    private String userid;
    private String name;
    private Integer severity;

    
    
	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getObject() {
		return object;
	}
	public void setObject(Integer object) {
		this.object = object;
	}
	public String getObjectid() {
		return objectid;
	}
	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
	public Integer getNs() {
		return ns;
	}
	public void setNs(Integer ns) {
		this.ns = ns;
	}
	public String getR_eventid() {
		return r_eventid;
	}
	public void setR_eventid(String r_eventid) {
		this.r_eventid = r_eventid;
	}
	public Integer getR_ns() {
		return r_ns;
	}
	public void setR_ns(Integer r_ns) {
		this.r_ns = r_ns;
	}
	public Long getClock() {
		return clock;
	}
	public void setClock(Long clock) {
		this.clock = clock;
	}
	public Long getR_clock() {
		return r_clock;
	}
	public void setR_clock(Long r_clock) {
		this.r_clock = r_clock;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSeverity() {
		return severity;
	}
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}
}
