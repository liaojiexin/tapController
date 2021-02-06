package com.vcmy.service.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vcmy.common.Device;
import com.vcmy.common.serivce.SSHConfig;
import com.vcmy.dao.*;
import com.vcmy.entity.*;
import com.vcmy.entity.config.ConfigInterface;
import com.vcmy.service.strategy.RuleService;
import com.vcmy.service.strategy.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: StrategyServiceImpl
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/22 14:34
 */
@Service
public class StrategyServiceImpl implements StrategyService {

    @Autowired
    private Device device;

    @Autowired
    private SSHConfig sshConfig;

    @Autowired
    private StrategyDao strategyDao;

    @Autowired
    private RuleDao ruleDao;

    @Autowired
    private PortDao portDao;

    @Autowired
    private OutportGroupDao outportGroupDao;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private PortLineDao portLineDao;

    @Override
    public List<Strategy> selectStrategy(Strategy strategy) {
        List<Strategy> lists=strategyDao.selectStrategy(strategy);
        for (Strategy list:lists) {     //查出端口组和集合
            List<OutPortGroup> outPortGroupList=list.getOutPortGroupList();
            for (int i=outPortGroupList.size()-1;i>-1;i--) {
                OutPortGroup outPortGroup=outportGroupDao.selectByPrimaryKey(outPortGroupList.get(i));
                outPortGroupList.remove(i);
                outPortGroupList.add(outPortGroup);
            }
        }
        return lists;
    }

    @Override
    @Transactional
    public String insertStrategy(Strategy strategy) throws CloneNotSupportedException {
        //判断策略合法性
//        Map<String,Object> resultMap=checkStrategy(strategy,"add");

        ConfigInterface configInterface=new ConfigInterface();
        configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());

        //执行命令
        for (Port inPort:strategy.getInPortGroup()) {   //将入端口组分成一个个入端口
            String portName=portDao.selectPortNameById(inPort.getPortId());   //端口id查端口名称
            for(OutPortGroup outPortGroup:strategy.getOutPortGroupList()){  //将出端口组集合分为一个个出端口组
                for(Rule rule:outPortGroup.getRuleList()){
                    if(rule.equals("") || JSONObject.toJSONString(rule).equals("{}")){//如果规则为空
                        rule=null;
                    }
                    Integer cookie=strategyDao.selectMaxid();
                    String result=sshConfig.insertStrategy(configInterface,portName,outPortGroup, rule,cookie);    //执行命令
                    if(result.indexOf("ovs-ofctl:")>-1){        //完全失败
                        return "添加失败";
                    }
                    if(result.indexOf("normalization changed ofp_match, details:")>-1){     //一部分插入，一部分失败
                        sshConfig.deleteStrategy(configInterface,portName,outPortGroup,rule);   //删除插入一半的策略
                        return "添加失败";
                    }
                }
            }
        }

        //数据库操作
        //规则
        strategy.setFlowStatistics("0");    //流量默认给0
        if (strategyDao.insertSelective(strategy)==1){
            for (OutPortGroup outPortGroup:strategy.getOutPortGroupList()) {//添加出端口组集合
                outportGroupDao.insertSelective(outPortGroup,strategy.getStrategyId());
                if (outPortGroup.getRuleList()!=null &&outPortGroup.getRuleList().size()>0
                        && !JSONObject.toJSONString(outPortGroup.getRuleList()).equals("[{}]")){
                    for (Rule rule:outPortGroup.getRuleList()){     //添加规则
                        rule.setOutPortGroup(outPortGroup);
                        ruleDao.insertSelective(rule);
                    }
                }
                for (Port outPort: outPortGroup.getOutPortGroup()) {    //添加出端口组
                    portDao.insertStrategyOutPort(outPortGroup.getOutPortGroupId(),outPort.getPortId());
                }
                //修改端口信息，端口只能为出或入端口，不能同时为出端口或者入端口，前端如果某个端口已经被选为出端口则，不能选做入端口
                //出端口
                for (Port outPort:outPortGroup.getOutPortGroup()) {
                    Port port=new Port();
                    port.setPortId(outPort.getPortId());
                    port.setType(1);
                    portDao.updatePort(port);
                }
            }
            for (Port inPort:strategy.getInPortGroup()) {   //添加进端口组
                portDao.insertStrategyInPort(strategy.getStrategyId(),inPort.getPortId());
                //连线表插入
                for (OutPortGroup outPortGroup:strategy.getOutPortGroupList()){
                    for (Port ouPort:outPortGroup.getOutPortGroup()){
                        PortLine portLine=new PortLine();
                        portLine.setInPortId(inPort.getPortId());
                        portLine.setOutPortId(ouPort.getPortId());
                        portLine.setStrategyId(strategy.getStrategyId());
                        portLineDao.insertSelective(portLine);
                        if (outPortGroup.getRuleList()!=null &&outPortGroup.getRuleList().size()>0
                                && !JSONObject.toJSONString(outPortGroup.getRuleList()).equals("[{}]")){
                            for (Rule rule:outPortGroup.getRuleList()){
                                portLineDao.insertLineRule(portLine.getLineId(),rule.getRuleId());
                            }
                        }
                    }
                }
                //修改端口信息，端口只能为出或入端口，不能同时为出端口或者入端口，前端如果某个端口已经被选为出端口则，不能选做入端口
                //入端口
                Port port =new Port();
                port.setPortId(inPort.getPortId());
                port.setType(0);
                portDao.updatePort(port);
            }
        }
        return "添加成功";
    }

    @Override
    @Transactional
    public String deleteStrategy(Integer strategyId) throws CloneNotSupportedException {
        Strategy strategy=new Strategy();
        strategy.setStrategyId(strategyId);
        Strategy str=strategyService.selectByPrimaryKey(strategy);      //查询出该策略的信息
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());

        //执行命令
        for (Port inPort:str.getInPortGroup()) {    //将入端口组分割出来
            String portName=inPort.getPortName();
            for (OutPortGroup outPortGroup:str.getOutPortGroupList()) { //出端口组集合
                if (outPortGroup.getRuleList()!=null && outPortGroup.getRuleList().size()>0){   //有规则
                    for (Rule rule:outPortGroup.getRuleList()) {    //规则
                        String s =sshConfig.deleteStrategy(configInterface,portName,outPortGroup,rule);
                        if(s.indexOf("ovs-ofctl:")>-1)
                            return "删除失败";
                    }
                }else { //无规则
                    String s =sshConfig.deleteStrategy(configInterface,portName,outPortGroup,null);
                    if(s.indexOf("ovs-ofctl:")>-1)
                        return "删除失败";
                }
            }
        }

        //数据库操作
        //删除入端口组
        for (Port inPort:str.getInPortGroup()){
            //入端口处理（查询该条策略的入端口是否在别的策略被使用，为被使用则改变端口状态为未使用）
            int inCount=portDao.selectInPortCount(inPort.getPortId(),strategy.getStrategyId());
            if (inCount<1){
                Port port=new Port();
                port.setPortId(inPort.getPortId());
                port.setType(-1);       //0入端口，1出端口，-1未使用
                portDao.updatePort(port);   //修改入端口为未使用状态
            }
            portDao.deleteInPort(inPort.getPortId(),strategy.getStrategyId());   //删除strategy_port中的入端口组
        }
        //出端口组集合处理
        //删除出端口组集合
        for(OutPortGroup outPortGroup:str.getOutPortGroupList()){
            //删除规则
            if (outPortGroup.getRuleList()!=null && !JSONObject.toJSONString(outPortGroup.getRuleList()).equals("[{}]")){
                for (Rule rule:outPortGroup.getRuleList()){
                    //数据库删除规则
                    if(rule.getRuleId()!=null)
                        portLineDao.deleteLineRule(rule.getRuleId());   //删除连线_规则表
                    if (rule.getRuleId()!=null && ruleDao.deleteByPrimaryKey(rule.getRuleId())!=1)//按照规则id删除规则
                        return "删除失败";
                }
            }

            //删除出端口组
            for (Port outPort:outPortGroup.getOutPortGroup()){
                //出端口处理（查询该条策略的出端口是否在别的策略被使用，为被使用则改变端口状态为未使用）
                int outCount=portDao.selectOutPortCount(outPort.getPortId(),strategy.getStrategyId());
                if(outCount<1){
                    Port port=new Port();
                    port.setPortId(outPort.getPortId());
                    port.setType(-1);   //0入端口，1出端口，-1未使用
                    portDao.updatePort(port);   //修改出端口为未使用状态
                }
                for (Port inPort:str.getInPortGroup()){
                    portLineDao.deletePortLine(inPort.getPortId(),outPort.getPortId(),strategyId);  //删除端口连线
                }
                portDao.deleteOutPort(outPort.getPortId(),outPortGroup.getOutPortGroupId());  //删除outportgroup_port中的出端口组
            }

            //删除出端口组集合
            outportGroupDao.deleteByPrimaryKey(outPortGroup.getOutPortGroupId());
        }

        strategyDao.deleteByPrimaryKey(str.getStrategyId());        //删除策略
        return "删除成功";
    }

    @Override
    @Transactional
    public String updateStrategy(Strategy strategyNew) throws CloneNotSupportedException {
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());

        Strategy strategyOld=strategyService.selectByPrimaryKey(strategyNew);      //查询出旧策略的信息
        //删除策略执行命令
        for (Port inPort:strategyOld.getInPortGroup()) {    //将入端口组分割出来
            String portName=inPort.getPortName();
            for (OutPortGroup outPortGroup:strategyOld.getOutPortGroupList()) { //出端口组集合
                if (outPortGroup.getRuleList()!=null && outPortGroup.getRuleList().size()>0){   //有规则
                    for (Rule rule:outPortGroup.getRuleList()) {    //规则
                        String s =sshConfig.deleteStrategy(configInterface,portName,outPortGroup,rule);
                        if(s.indexOf("ovs-ofctl:")>-1)
                            return "删除失败";
                    }
                }else { //无规则
                    String s =sshConfig.deleteStrategy(configInterface,portName,outPortGroup,null);
                    if(s.indexOf("ovs-ofctl:")>-1)
                        return "删除失败";
                }
            }
        }

        //添加新策略执行命令
        for (Port inPort:strategyNew.getInPortGroup()) {   //将入端口组分成一个个入端口
            String portName=portDao.selectPortNameById(inPort.getPortId());   //端口id查端口名称
            for(OutPortGroup outPortGroup:strategyNew.getOutPortGroupList()){  //将出端口组集合分为一个个出端口组
                for(Rule rule:outPortGroup.getRuleList()){
                    if(rule.equals("") || JSONObject.toJSONString(rule).equals("{}")){//如果规则为空
                        rule=null;
                    }
                    Integer cookie=strategyDao.selectMaxid();
                    String result=sshConfig.insertStrategy(configInterface,portName,outPortGroup, rule,cookie);    //执行命令
                    if(result.indexOf("ovs-ofctl:")>-1){        //完全失败
                        return "添加失败";
                    }
                    if(result.indexOf("normalization changed ofp_match, details:")>-1){     //一部分插入，一部分失败
                        sshConfig.deleteStrategy(configInterface,portName,outPortGroup,rule);   //删除插入一半的策略
                        return "添加失败";
                    }
                }
            }
        }

        //修改数据库，策略id和规则id，和出端组id不能变-----------------------------------------------
        //入端口组处理,先删除所有入端口组，然后再加入
        for (Port inPort:strategyOld.getInPortGroup()){
            //入端口处理（查询该条策略的入端口是否在别的策略被使用，为被使用则改变端口状态为未使用）
            int inCount=portDao.selectInPortCount(inPort.getPortId(),strategyOld.getStrategyId());
            if (inCount<1){
                Port port=new Port();
                port.setPortId(inPort.getPortId());
                port.setType(-1);       //0入端口，1出端口，-1未使用
                portDao.updatePort(port);   //修改入端口为未使用状态
            }
            portDao.deleteInPort(inPort.getPortId(),strategyOld.getStrategyId());   //删除strategy_port中的入端口组
        }
        for (Port inPort:strategyNew.getInPortGroup()) {   //添加进端口组
            portDao.insertStrategyInPort(strategyNew.getStrategyId(),inPort.getPortId());
            //修改端口信息，端口只能为出或入端口，不能同时为出端口或者入端口，前端如果某个端口已经被选为出端口则，不能选做入端口
            //入端口
            Port port =new Port();
            port.setPortId(inPort.getPortId());
            port.setType(0);
            portDao.updatePort(port);
        }

        //出端口组集合处理，查询修改的新策略中所有的出端口组，根据出端口组id来判断旧策略里面哪些出端口组是被删掉的
        //先做一个删除出端口组的操作（删除id不存在新端口组里面的旧出端口组）
        List<OutPortGroup> outPortGroupList=new ArrayList<>();  //存放组相同id的出端口组，给后面的规则处理做准备
        for (int i=strategyOld.getOutPortGroupList().size()-1;i>-1;i--){    //把要删除的出端口组整理出来,现在strategyOld中的出端口组都是要删掉的
            for (OutPortGroup outPortGroup1:strategyNew.getOutPortGroupList()){
                if (strategyOld.getOutPortGroupList().get(i).getOutPortGroupId()==outPortGroup1.getOutPortGroupId()){    //新策略的出端口组id等于旧策略的出端口组id代表修改的出端口组
                    outPortGroupList.add(strategyOld.getOutPortGroupList().get(i));
                    strategyOld.getOutPortGroupList().remove(i);
                }else {
                    strategyOld.getOutPortGroupList().add(strategyOld.getOutPortGroupList().get(i));
                    strategyOld.getOutPortGroupList().remove(i);
                }
            }
        }
        //删除
        for(OutPortGroup outPortGroup:strategyOld.getOutPortGroupList()){
            //删除规则
            if (outPortGroup.getRuleList()!=null && !JSONObject.toJSONString(outPortGroup.getRuleList()).equals("[{}]")){
                for (Rule rule:outPortGroup.getRuleList()){
                    //数据库删除规则
                    if(rule.getRuleId()!=null)
                        portLineDao.deleteLineRule(rule.getRuleId());   //删除连线_规则表
                    if (rule.getRuleId()!=null && ruleDao.deleteByPrimaryKey(rule.getRuleId())!=1)//按照规则id删除规则
                        return "删除失败";
                }
            }
            //删除出端口组
            for (Port outPort:outPortGroup.getOutPortGroup()){
                //出端口处理（查询该条策略的出端口是否在别的策略被使用，为被使用则改变端口状态为未使用）
                int outCount=portDao.selectOutPortCount(outPort.getPortId(),strategyOld.getStrategyId());
                if(outCount<1){
                    Port port=new Port();
                    port.setPortId(outPort.getPortId());
                    port.setType(-1);   //0入端口，1出端口，-1未使用
                    portDao.updatePort(port);   //修改出端口为未使用状态
                }
                for (Port inPort:strategyOld.getInPortGroup()){
                    portLineDao.deletePortLine(inPort.getPortId(),outPort.getPortId(),strategyOld.getStrategyId());  //删除端口连线
                }
                portDao.deleteOutPort(outPort.getPortId(),outPortGroup.getOutPortGroupId());  //删除outportgroup_port中的出端口组
            }
            //删除出端口组集合
            outportGroupDao.deleteByPrimaryKey(outPortGroup.getOutPortGroupId());
        }

        //出端口组中规则处理，新出端口组集合中，对应已存在的出端口组的规则进行判断，如果里面的规则有id则表明为修改的id
        //把不存在新出端口组中的旧规则给删掉 ,以下处理后outPortGroupList中的规则都为要删除的规则
        for (OutPortGroup outPortGroup:outPortGroupList){       //outPortGroupList存放有id并且新旧策略中都存在的出端口组，表示这些都是要修改的出端口组（并非新增加的）
            for(OutPortGroup outPortGroup1:strategyNew.getOutPortGroupList()){      //新策略
                if (outPortGroup1.getOutPortGroupId()==outPortGroup.getOutPortGroupId()){       //同个出端口组
                    //规则处理
                    if(outPortGroup1.getRuleList()!=null &&outPortGroup1.getRuleList().size()>0
                            && !JSONObject.toJSONString(outPortGroup1.getRuleList()).equals("[{}]")){
                        for (int i=outPortGroup.getRuleList().size()-1;i>-1;i--){     //旧策略中的规则
                            for(Rule rule:outPortGroup1.getRuleList()){
                                if (!JSONObject.toJSONString(rule).equals("{}")){
                                    if (rule.getRuleId()==outPortGroup.getRuleList().get(i).getRuleId()){       //同个规则
                                        outPortGroup.getRuleList().remove(i);
                                    }else {
                                        outPortGroup.getRuleList().add(outPortGroup.getRuleList().get(i));
                                        outPortGroup.getRuleList().remove(i);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //删除
            if (outPortGroup.getRuleList()!=null && !JSONObject.toJSONString(outPortGroup.getRuleList()).equals("[{}]")){
                for (Rule rule:outPortGroup.getRuleList()){
                    //数据库删除规则
                    if(rule.getRuleId()!=null)
                        portLineDao.deleteLineRule(rule.getRuleId());   //删除连线_规则表
                    if (rule.getRuleId()!=null && ruleDao.deleteByPrimaryKey(rule.getRuleId())!=1)//按照规则id删除规则
                        return "删除失败";
                }
            }
        }

        //上面的步骤把删除掉的出端口组和规则进行删除，接下来利用新策略对数据库进行修改，id存在及修改，id不存在则插入
        for(OutPortGroup outPortGroup:strategyNew.getOutPortGroupList()){
            if (outPortGroup.getOutPortGroupId()==null){        //新增的出端口组，做插入操作
                outportGroupDao.insertSelective(outPortGroup,strategyNew.getStrategyId());
                if (outPortGroup.getRuleList()!=null &&outPortGroup.getRuleList().size()>0
                        && !JSONObject.toJSONString(outPortGroup.getRuleList()).equals("[{}]")){
                    for (Rule rule:outPortGroup.getRuleList()){     //添加规则
                        rule.setOutPortGroup(outPortGroup);
                        ruleDao.insertSelective(rule);
                    }
                }
                for (Port outPort: outPortGroup.getOutPortGroup()) {    //添加出端口组
                    portDao.insertStrategyOutPort(outPortGroup.getOutPortGroupId(),outPort.getPortId());
                }
                //修改端口信息，端口只能为出或入端口，不能同时为出端口或者入端口，前端如果某个端口已经被选为出端口则，不能选做入端口
                //出端口
                for (Port outPort:outPortGroup.getOutPortGroup()) {
                    Port port=new Port();
                    port.setPortId(outPort.getPortId());
                    port.setType(1);
                    portDao.updatePort(port);
                }
                for (Port inPort:strategyNew.getInPortGroup()) {
                    //连线表插入
                    for (Port ouPort:outPortGroup.getOutPortGroup()){
                        PortLine portLine=new PortLine();
                        portLine.setInPortId(inPort.getPortId());
                        portLine.setOutPortId(ouPort.getPortId());
                        portLine.setStrategyId(strategyNew.getStrategyId());
                        portLineDao.insertSelective(portLine);
                        if (outPortGroup.getRuleList()!=null &&outPortGroup.getRuleList().size()>0
                                && !JSONObject.toJSONString(outPortGroup.getRuleList()).equals("[{}]")){
                            for (Rule rule:outPortGroup.getRuleList()){
                                portLineDao.insertLineRule(portLine.getLineId(),rule.getRuleId());
                            }
                        }
                    }
                }
            }else {     //修改的出端口组，做修改操作
                //修改规则
                if (outPortGroup.getRuleList()!=null &&outPortGroup.getRuleList().size()>0
                        && !JSONObject.toJSONString(outPortGroup.getRuleList()).equals("[{}]")){
                    for (Rule rule:outPortGroup.getRuleList()){
                        if (!JSONObject.toJSONString(rule).equals("{}")){
                            if (rule.getRuleId()==null){    //规则id为空，插入新规则
                                rule.setOutPortGroup(outPortGroup);
                                ruleDao.insertSelective(rule);
                            }else {     //修改规则
                                rule.setOutPortGroup(outPortGroup);
                                ruleDao.updateByPrimaryKeySelective(rule);
                            }
                        }
                    }
                }
                //修改出端口组，删掉再添加
                //删除出端口组
                for (Port outPort:outPortGroup.getOutPortGroup()){
                    //出端口处理（查询该条策略的出端口是否在别的策略被使用，为被使用则改变端口状态为未使用）
                    int outCount=portDao.selectOutPortCount(outPort.getPortId(),strategyNew.getStrategyId());
                    if(outCount<1){
                        Port port=new Port();
                        port.setPortId(outPort.getPortId());
                        port.setType(-1);   //0入端口，1出端口，-1未使用
                        portDao.updatePort(port);   //修改出端口为未使用状态
                    }
                    for (Port inPort:strategyNew.getInPortGroup()){
                        portLineDao.deletePortLine(inPort.getPortId(),outPort.getPortId(),strategyNew.getStrategyId());  //删除端口连线
                    }
                    portDao.deleteOutPort(outPort.getPortId(),outPortGroup.getOutPortGroupId());  //删除outportgroup_port中的出端口组
                }
                //添加出端口组
                for (Port outPort: outPortGroup.getOutPortGroup()) {    //添加出端口组
                    portDao.insertStrategyOutPort(outPortGroup.getOutPortGroupId(),outPort.getPortId());
                    //连线表插入
                    for (Port inPort:strategyNew.getInPortGroup()){
                        for (Port ouPort:outPortGroup.getOutPortGroup()){
                            PortLine portLine=new PortLine();
                            portLine.setInPortId(inPort.getPortId());
                            portLine.setOutPortId(ouPort.getPortId());
                            portLine.setStrategyId(strategyNew.getStrategyId());
                            portLineDao.insertSelective(portLine);
                            if (outPortGroup.getRuleList()!=null &&outPortGroup.getRuleList().size()>0
                                    && !JSONObject.toJSONString(outPortGroup.getRuleList()).equals("[{}]")){
                                for (Rule rule:outPortGroup.getRuleList()){
                                    if (!JSONObject.toJSONString(rule).equals("{}"))
                                        portLineDao.insertLineRule(portLine.getLineId(),rule.getRuleId());
                                }
                            }
                        }
                    }
                }
                //修改端口信息，端口只能为出或入端口，不能同时为出端口或者入端口，前端如果某个端口已经被选为出端口则，不能选做入端口
                //出端口
                for (Port outPort:outPortGroup.getOutPortGroup()) {
                    Port port=new Port();
                    port.setPortId(outPort.getPortId());
                    port.setType(1);
                    portDao.updatePort(port);
                }
            }
        }

        return "修改成功";
    }

    @Override
    public Strategy selectByPrimaryKey(Strategy strategy) {
        Strategy s=strategyDao.selectByPrimaryKey(strategy.getStrategyId());
        //查出端口组集合
        List<OutPortGroup> outPortGroupList=s.getOutPortGroupList();
        for (int i=outPortGroupList.size()-1;i>-1;i--) {
            OutPortGroup outPortGroup=outportGroupDao.selectByPrimaryKey(outPortGroupList.get(i));
            outPortGroupList.remove(i);
            outPortGroupList.add(outPortGroup);
        }
        return s;
    }

    /**
     * @Author liaojiexin
     * @Description 检查策略合法性（添加type=add和修改接口type=update），入端口+匹配域决定策略唯一性
     * @Date 2021/1/4 14:43
     * @Param [strategy, add]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    @Transactional
    public Map<String, Object> checkStrategy(Strategy strategy, String type) {
        Map<String,Object> map=new HashMap<>();
        //检查端口，端口要么为出端口要么为入端口   修改增加都一样
        //入端口
        List<Port> inPorts=strategy.getInPortGroup();
        for(Port inPort:inPorts){
            int portType=portDao.selectPortType(inPort);       //0入端口，1出端口，-1未使用
            if(portType==1)
                map.put("msg","入端口组中的端口已被使用为出端口");
        }
        //出端口
        List<OutPortGroup> outPortGroups=strategy.getOutPortGroupList();
        for (OutPortGroup outPortGroup:outPortGroups){
            List<Port> outPorts=outPortGroup.getOutPortGroup();
            for (Port outPort:outPorts){
                int portType=portDao.selectPortType(outPort);   //0入端口，1出端口，-1未使用
                if(portType==0)
                    map.put("msg","出端口组中的端口已被使用为入端口");
            }
        }

        //匹配域+入端口判断
        //1.不同策略，判断当前新增的策略是否和已经存在的策略有重复
        List<Strategy> strategyList=strategyService.selectStrategy(null);  //查出所有
        if (type=="update"){        //把自己那条策略排除
            for (int i=strategyList.size()-1;i>-1;i--){
                if (strategyList.get(i).getStrategyId()!=strategy.getStrategyId()){
                    strategyList.add(strategyList.get(i));
                }
                strategyList.remove(i);
            }
        }
        for(Strategy strategy1:strategyList){
            for (OutPortGroup outPortGroup:strategy1.getOutPortGroupList()){
                for (Rule rule:outPortGroup.getRuleList()){
                    //新增加的规则
                    for (OutPortGroup outPortGroup1:strategy.getOutPortGroupList()){
                        for (Rule rule1:outPortGroup1.getRuleList()){
                            if (rule.equals(rule1)){    //规则相同
                                for(Port port:strategy.getInPortGroup()){
                                    for (Port port1:strategy1.getInPortGroup()){
                                        if (port.getPortId()==port1.getPortId()){
                                            map.put("msg","策略id"+strategy1.getStrategyId()+"中已经有存在进端口和匹配域相同的策略,请直接修改对应策略的出端口组");
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //2.同个策略里面,判断规则是否一样,修改增加都一样
        if (map.get("msg")==null){
            List<Rule> ruleList=new ArrayList<>();
            for (int i=0;i<strategy.getOutPortGroupList().size();i++){
                for (Rule rule:strategy.getOutPortGroupList().get(i).getRuleList()){
                    for (Rule rule1:ruleList){
                        if (rule1.equals(rule)){
                            map.put("msg","该策略中第"+(i+1)+"个出端口组中的规则匹配域已经重复出现,请直接在已经存在的规则上添加出端口");
                            break;
                        }
                    }
                    ruleList.add(rule);
                }
            }
        }

        return map;
    }

    @Override
    public void updateStrategyFlowStatistics(Strategy strategy) {
        strategyDao.updateByPrimaryKeySelective(strategy);
    }

    /**
     * @Author liaojiexin
     * @Description 根据规则id修改策略规则，思路：先查询规则涉及的策略并查询出来，再删除设备上的策略并利用新规则进行添加，最后修改数据库上的规则
     * @Date 2021/1/6 17:10
     * @Param [rule]
     * @return java.lang.String
     **/
    @Override
    @Transactional
    public String updateRule(Rule rule) throws CloneNotSupportedException {
        //查出该条规则相关的策略
        Strategy strategy=new Strategy();
        List<OutPortGroup> outPortGroups=new ArrayList<>();
        OutPortGroup outPortGroup=new OutPortGroup();
        if (rule.getRuleId()!=null){
            List<Rule> ruleList=new ArrayList<>();
            rule.setRuleId(rule.getRuleId());
            ruleList.add(rule);
            outPortGroup.setRuleList(ruleList);
            outPortGroups.add(outPortGroup);
        }
        strategy.setOutPortGroupList(outPortGroups);
        List<Strategy> list=strategyService.selectStrategy(strategy);//只用拿第一条策略，因为一条规则只用再一条策略里面
        strategy=list.get(0);

        //删除设备上的策略
        //执行命令
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());
        for (Port inPort:strategy.getInPortGroup()) {    //将入端口组分割出来
            String portName=inPort.getPortName();
            for (OutPortGroup outPortGroup1:strategy.getOutPortGroupList()) { //出端口组集合
                if (outPortGroup1.getRuleList()!=null && outPortGroup1.getRuleList().size()>0){   //有规则
                    for (Rule rule1:outPortGroup1.getRuleList()) {    //规则
                        String s =sshConfig.deleteStrategy(configInterface,portName,outPortGroup1,rule1);
                        if(s.indexOf("ovs-ofctl:")>-1)
                            return "删除失败";
                    }
                }else { //无规则
                    String s =sshConfig.deleteStrategy(configInterface,portName,outPortGroup1,null);
                    if(s.indexOf("ovs-ofctl:")>-1)
                        return "删除失败";
                }
            }
        }

        //将策略里面这条规则修改掉
        for (OutPortGroup outPortGroup1:strategy.getOutPortGroupList()){
            if (outPortGroup1.getRuleList()!=null && outPortGroup1.getRuleList().size()>0
                    &&!JSONObject.toJSONString(outPortGroup1.getRuleList()).equals("[{}]")){
                for (int i=outPortGroup1.getRuleList().size()-1;i>-1;i--){
                    if (outPortGroup1.getRuleList().get(i).getRuleId()==rule.getRuleId()){
                        outPortGroup1.getRuleList().add(rule);
                        outPortGroup1.getRuleList().remove(outPortGroup1.getRuleList().get(i));
                    }
                }
            }
        }

        //设备添加新策略
        //执行命令
        for (Port inPort:strategy.getInPortGroup()) {   //将入端口组分成一个个入端口
            String portName=portDao.selectPortNameById(inPort.getPortId());   //端口id查端口名称
            for(OutPortGroup outPortGroup1:strategy.getOutPortGroupList()){  //将出端口组集合分为一个个出端口组
                for(Rule rule1:outPortGroup1.getRuleList()){
                    if(rule1.equals("") || JSONObject.toJSONString(rule1).equals("{}")){//如果规则为空
                        rule1=null;
                    }
                    Integer cookie=strategyDao.selectMaxid();
                    String result=sshConfig.insertStrategy(configInterface,portName,outPortGroup1, rule1,cookie);    //执行命令
                    if(result.indexOf("ovs-ofctl:")>-1){        //完全失败
                        return "添加失败";
                    }
                    if(result.indexOf("normalization changed ofp_match, details:")>-1){     //一部分插入，一部分失败
                        sshConfig.deleteStrategy(configInterface,portName,outPortGroup1,rule1);   //删除插入一半的策略
                        return "添加失败";
                    }
                }
            }
        }

        //修改数据库中的规则
        ruleDao.updateByPrimaryKeySelective(rule);

        return "修改成功";
    }
}
