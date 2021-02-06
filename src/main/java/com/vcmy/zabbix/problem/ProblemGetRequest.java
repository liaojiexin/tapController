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

import com.vcmy.zabbix.GetRequestCommonParams;
import com.vcmy.zabbix.ZabbixApiRequest;
import com.vcmy.zabbix.getselector.SelectAcknowledges;

import java.sql.Timestamp;
import java.util.List;


/**
 * Created by Suguru Yajima on 2014/05/02.
 */
public class ProblemGetRequest extends ZabbixApiRequest {

    private Params params = new Params();

    public ProblemGetRequest() {
        setMethod("problem.get");
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public class Params extends GetRequestCommonParams {
        private List<Integer> groupids;
        private List<String> eventids;
        private List<Integer> hostids;
        private List<Integer> objectids;
        private List<Integer> applicationids;
        private Integer source;
        private Integer object;
        private boolean acknowledged;
        private List<Integer> severities;
        private Object tags;
        private String recent;
        private String eventid_from;
        private String eventid_till;
        private Timestamp time_from;
        private Timestamp time_till;
        private SelectAcknowledges selectAcknowledges;
        private String selectTags;
       

        public Params() {
        }


		

		public List<Integer> getGroupids() {
			return groupids;
		}




		public void setGroupids(List<Integer> groupids) {
			this.groupids = groupids;
		}




		public List<String> getEventids() {
			return eventids;
		}




		public void setEventids(List<String> eventids) {
			this.eventids = eventids;
		}




		public List<Integer> getHostids() {
			return hostids;
		}




		public void setHostids(List<Integer> hostids) {
			this.hostids = hostids;
		}




		public List<Integer> getObjectids() {
			return objectids;
		}




		public void setObjectids(List<Integer> objectids) {
			this.objectids = objectids;
		}




		public List<Integer> getApplicationids() {
			return applicationids;
		}




		public void setApplicationids(List<Integer> applicationids) {
			this.applicationids = applicationids;
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


		public boolean isAcknowledged() {
			return acknowledged;
		}


		public void setAcknowledged(boolean acknowledged) {
			this.acknowledged = acknowledged;
		}


		public List<Integer> getSeverities() {
			return severities;
		}


		public void setSeverities(List<Integer> severities) {
			this.severities = severities;
		}


		public Object getTags() {
			return tags;
		}


		public void setTags(Object tags) {
			this.tags = tags;
		}


		public String getRecent() {
			return recent;
		}


		public void setRecent(String recent) {
			this.recent = recent;
		}


		public String getEventid_from() {
			return eventid_from;
		}


		public void setEventid_from(String eventid_from) {
			this.eventid_from = eventid_from;
		}


		public String getEventid_till() {
			return eventid_till;
		}


		public void setEventid_till(String eventid_till) {
			this.eventid_till = eventid_till;
		}


		public Timestamp getTime_from() {
			return time_from;
		}


		public void setTime_from(Timestamp time_from) {
			this.time_from = time_from;
		}


		public Timestamp getTime_till() {
			return time_till;
		}


		public void setTime_till(Timestamp time_till) {
			this.time_till = time_till;
		}


		public SelectAcknowledges getSelectAcknowledges() {
			return selectAcknowledges;
		}


		public void setSelectAcknowledges(SelectAcknowledges selectAcknowledges) {
			this.selectAcknowledges = selectAcknowledges;
		}


		public String getSelectTags() {
			return selectTags;
		}


		public void setSelectTags(String selectTags) {
			this.selectTags = selectTags;
		}
		  

    }

}
