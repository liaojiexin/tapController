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

package com.vcmy.zabbix.user;

import com.vcmy.zabbix.GetRequestCommonParams;
import com.vcmy.zabbix.ZabbixApiRequest;

import java.util.List;

/**
 * Created by Suguru Yajima on 2014/05/02.
 */
public class UserGetRequest extends ZabbixApiRequest {

    private Params params = new Params();

    public UserGetRequest() {
        setMethod("user.get");
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public class Params extends GetRequestCommonParams {
        private List<Integer> userids ;
        private List<Integer> mediaids;
        private List<Integer> mediatypeids;
        private List<Integer> usrgrpids;
        private String selectMedias;
        private String selectMediatypes;
        private String selectUsrgrps;        

        public Params() {
        }

      

        public List<Integer> getMediaids() {
			return mediaids;
		}



		public void setMediaids(List<Integer> mediaids) {
			this.mediaids = mediaids;
		}



		public List<Integer> getMediatypeids() {
			return mediatypeids;
		}



		public void setMediatypeids(List<Integer> mediatypeids) {
			this.mediatypeids = mediatypeids;
		}



		public List<Integer> getUsrgrpids() {
			return usrgrpids;
		}



		public void setUsrgrpids(List<Integer> usrgrpids) {
			this.usrgrpids = usrgrpids;
		}

		public String getSelectMedias() {
			return selectMedias;
		}



		public void setSelectMedias(String selectMedias) {
			this.selectMedias = selectMedias;
		}



		public String getSelectMediatypes() {
			return selectMediatypes;
		}



		public void setSelectMediatypes(String selectMediatypes) {
			this.selectMediatypes = selectMediatypes;
		}



		public String getSelectUsrgrps() {
			return selectUsrgrps;
		}



		public void setSelectUsrgrps(String selectUsrgrps) {
			this.selectUsrgrps = selectUsrgrps;
		}



		public List<Integer> getUserids () {
            return userids ;
        }

        public void setUserids (List<Integer> userids ) {
            this.userids  = userids ;
        }
    

    }

}
