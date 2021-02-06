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

package com.vcmy.zabbix;

import com.vcmy.zabbix.action.Action;
import com.vcmy.zabbix.alert.Alert;
import com.vcmy.zabbix.application.Application;
import com.vcmy.zabbix.configuration.Configuration;
import com.vcmy.zabbix.discoveredhost.DiscoveredHosts;
import com.vcmy.zabbix.discoveredservice.DiscoveredService;
import com.vcmy.zabbix.discoveryrule.DiscoveryRule;
import com.vcmy.zabbix.event.Event;
import com.vcmy.zabbix.graph.Graph;
import com.vcmy.zabbix.graphitem.GraphItem;
import com.vcmy.zabbix.graphprototype.GraphPrototype;
import com.vcmy.zabbix.history.History;
import com.vcmy.zabbix.host.Host;
import com.vcmy.zabbix.hostgroup.Hostgroup;
import com.vcmy.zabbix.hostinteface.HostInterface;
import com.vcmy.zabbix.hostprototype.HostPrototype;
import com.vcmy.zabbix.iconmap.IconMap;
import com.vcmy.zabbix.image.Image;
import com.vcmy.zabbix.item.Item;
import com.vcmy.zabbix.itemprototype.ItemPrototype;
import com.vcmy.zabbix.itservice.ITService;
import com.vcmy.zabbix.lldrule.LLDRule;
import com.vcmy.zabbix.maintenance.Maintenance;
import com.vcmy.zabbix.map.Map;
import com.vcmy.zabbix.media.Media;
import com.vcmy.zabbix.mediatype.MediaType;
import com.vcmy.zabbix.problem.Problem;
import com.vcmy.zabbix.proxy.Proxy;
import com.vcmy.zabbix.screen.Screen;
import com.vcmy.zabbix.screenitem.ScreenItem;
import com.vcmy.zabbix.script.Script;
import com.vcmy.zabbix.template.Template;
import com.vcmy.zabbix.templatescreen.TemplateScreen;
import com.vcmy.zabbix.templatescreenitem.TemplateScreenItem;
import com.vcmy.zabbix.trend.Trend;
import com.vcmy.zabbix.user.*;
import com.vcmy.zabbix.usergroup.UserGroup;
import com.vcmy.zabbix.webscenario.WebScenario;

/**
 * Created by Suguru Yajima on 2014/04/25.
 */
public class ZabbixApi {

    private String apiUrl;

    private String auth;

    private User user;

    public ZabbixApi(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void login(String username, String password) throws ZabbixApiException {
        user = new User(this.apiUrl);
        UserLoginRequest request = new UserLoginRequest();
        request.getParams().setPassword(password);
        request.getParams().setUser(username);

        UserLoginResponse response = user.login(request);

        this.auth = response.getResult();
        user.auth = response.getResult();
    }

    public void logout()throws ZabbixApiException {
        UserLogoutRequest request = new UserLogoutRequest(user.auth);
        UserLogoutResponse response = user.logout(request);
       // System.out.println(response);
    }

    public Hostgroup hostgroup() {

        return new Hostgroup(this.apiUrl, this.auth);
    }

    public Host host() {

        return new Host(this.apiUrl, this.auth);
    }

    public Problem problem(){
    	return new Problem(this.apiUrl, this.auth);
    }
    public Item item() {

        return new Item(this.apiUrl, this.auth);
    }

    public HostInterface hostInterface() {

        return new HostInterface(this.apiUrl, this.auth);
    }


    public UserGroup usergroup() {

        return new UserGroup(this.apiUrl, this.auth);
    }

    public Action action() {

        return new Action(this.apiUrl, this.auth);
    }

    public Alert alert() {

        return new Alert(this.apiUrl, this.auth);
    }

    public Application application() {

        return new Application(this.apiUrl, this.auth);
    }

    public Configuration configuration() {

        return new Configuration(this.apiUrl, this.auth);
    }

    public DiscoveredHosts discoverdHost() {

        return new DiscoveredHosts(this.apiUrl, this.auth);
    }

    public DiscoveredService discoveredService() {

        return new DiscoveredService(this.apiUrl, this.auth);
    }

    public DiscoveryRule discoveryRule() {

        return new DiscoveryRule(this.apiUrl, this.auth);
    }

    public Event event() {

        return new Event(this.apiUrl, this.auth);
    }

    public Graph graph() {

        return new Graph(this.apiUrl, this.auth);
    }

    public GraphItem graphItem() {

        return new GraphItem(this.apiUrl, this.auth);
    }

    public GraphPrototype graphPrototype() {

        return new GraphPrototype(this.apiUrl, this.auth);
    }

    public History history() {

        return new History(this.apiUrl, this.auth);
    }

    public Trend trend() {

        return new Trend(this.apiUrl, this.auth);
    }
    public LLDRule lldRule() {

        return new LLDRule(this.apiUrl, this.auth);
    }

    public HostPrototype hostPrototype() {

        return new HostPrototype(this.apiUrl, this.auth);
    }

    public IconMap iconMap() {

        return new IconMap(this.apiUrl, this.auth);
    }

    public Image image() {

        return new Image(this.apiUrl, this.auth);
    }

    public ItemPrototype itemPrototype() {

        return new ItemPrototype(this.apiUrl, this.auth);
    }

    public ITService itservice() {

        return new ITService(this.apiUrl, this.auth);
    }

    public Maintenance maintenance() {

        return new Maintenance(this.apiUrl, this.auth);
    }

    public Map map() {

        return new Map(this.apiUrl, this.auth);
    }

    public Media media() {

        return new Media(this.apiUrl, this.auth);
    }

    public MediaType mediaType() {

        return new MediaType(this.apiUrl, this.auth);
    }

    public Proxy proxy() {

        return new Proxy(this.apiUrl, this.auth);
    }

    public Screen screen() {

        return new Screen(this.apiUrl, this.auth);
    }

    public ScreenItem screenItem() {

        return new ScreenItem(this.apiUrl, this.auth);
    }

    public Script script() {

        return new Script(this.apiUrl, this.auth);
    }

    public Template template() {

        return new Template(this.apiUrl, this.auth);
    }

    public TemplateScreen templateScreen() {

        return new TemplateScreen(this.apiUrl, this.auth);
    }

    public TemplateScreenItem templateScreenItem() {

        return new TemplateScreenItem(this.apiUrl, this.auth);
    }

    public WebScenario webscenario() {

        return new WebScenario(this.apiUrl, this.auth);
    }

/*    public static void main(String[] args){
        String user = "Admin";
        String password = "zabbix";
        String apiUrl = "http:/zabbix/api_jsonrpc.php";
        try {
            ZabbixApi zabbixApi = new ZabbixApi(apiUrl);
            zabbixApi.login(user, password);
            System.out.println(zabbixApi.auth);
            zabbixApi.logout();
        } catch (ZabbixApiException e) {
            //logger.error(e.getMessage());
        }
    }*/

	
}
