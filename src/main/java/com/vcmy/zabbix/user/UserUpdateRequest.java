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


import com.vcmy.zabbix.ZabbixApiRequest;
import com.vcmy.zabbix.media.MediaObject;

import java.util.List;

/**
 * Created by Suguru Yajima on 2014/04/26.
 */
public class UserUpdateRequest extends ZabbixApiRequest {

    private Params params = new Params();

    public UserUpdateRequest() {
        setMethod("user.update");
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public class Params extends UserObject{

        private String passwd;
        private List<UserObject> usrgrps;
        private List<MediaObject> user_medias;
        public Params() {

        }
		public String getPasswd() {
			return passwd;
		}
		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}
		public List<UserObject> getUsrgrps() {
			return usrgrps;
		}
		public void setUsrgrps(List<UserObject> usrgrps) {
			this.usrgrps = usrgrps;
		}
		public List<MediaObject> getUser_medias() {
			return user_medias;
		}
		public void setUser_medias(List<MediaObject> user_medias) {
			this.user_medias = user_medias;
		}

       
    }
}
