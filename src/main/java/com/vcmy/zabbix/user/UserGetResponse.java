package com.vcmy.zabbix.user;


import com.vcmy.zabbix.ZabbixApiResponse;
import com.vcmy.zabbix.media.MediaObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suguru Yajima on 2014/05/02.
 */
public class UserGetResponse extends ZabbixApiResponse {

    private List<Result> result = new ArrayList<Result>();

    public UserGetResponse() {
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result extends UserObject {

        private List<MediaObject> medias;


		public List<MediaObject> getMedias() {
			return medias;
		}

		public void setMedias(List<MediaObject> medias) {
			this.medias = medias;
		}

    }
 
}
