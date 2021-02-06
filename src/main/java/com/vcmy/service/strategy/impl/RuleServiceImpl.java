package com.vcmy.service.strategy.impl;

import com.vcmy.dao.RuleDao;
import com.vcmy.entity.Rule;
import com.vcmy.service.strategy.RuleService;
import com.vcmy.util.CheckIpv4SameSegment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: StrategyRuleServiceImpl
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/22 15:28
 */

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleDao ruleDao;

    @Override
    public Rule selectRulebyId(Integer ruleId) {
        Rule rule = ruleDao.selectByPrimaryKey(ruleId);
        return rule;
    }

    /**
     * ovs没有匹配内层ip的功能， 所以这里有些互斥条件，
     * 如果选了MPLS(这个属于2.5层的标签封装协议)，
     * 那么就不能再往下选了，即ip和协议源目端口都将不生效
     * */
    @Override
    public String checkStrategyRule(Rule rule) {
        //格式判断
        String result = "规则正确";
        String trueMacAddress = "^([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}$";

        //ovs没有匹配内层ip的功能， 所以这里有些互斥条件，如果选了MPLS(这个属于2.5层的标签封装协议)，那么就不能再往下选了，即ip和协议源目端口都将不生效
        if ((rule.getMplsTc()!=null ||rule.getMplsLabel()!=null)
                && (!StringUtils.isBlank(rule.getSourceIp()) || !StringUtils.isBlank(rule.getDestinationIp())
                || !StringUtils.isBlank(rule.getProtocol()) || rule.getSourcePort()!=null || rule.getDestinationPort()!=null
                || !StringUtils.isBlank(rule.getTcpFlag()) || !StringUtils.isBlank(rule.getUpdateDestinationIp())
                || !StringUtils.isBlank(rule.getUpdateSourceIp())|| rule.getMessageTruncate()!=null)){
            result="（MPLS标签、MPLS tc）与（匹配域中的三层、匹配域中的四层、动作中的修改源/目的Ip、报文截短 ）冲突,不能同时填写";
            return result;
        }

        if (!StringUtils.isBlank(rule.getProtocol()) && !rule.getProtocol().equals("TCP") && !StringUtils.isBlank(rule.getTcpFlag())){
            result="四层中的TCP flag无效，三层协议必须为TCP协议才能生效";
            return result;
        }

        if( !StringUtils.isBlank(rule.getProtocol()) && (rule.getSourcePort()!=null || rule.getDestinationPort()!=null)){
            result="四层的端口无效，必须选择三层的协议";
            return result;
        }

        //匹配域
        if (rule.getVlanRange()!=null && (rule.getVlanRange()<0 || rule.getVlanRange()>4095)){
            result="匹配域中二层VLAN范围应该在0-4095之间";
            return result;
        }
        if(rule.getMplsTc()!=null && (rule.getMplsTc()<0 || rule.getMplsTc()>7)){
            result="匹配域中二层MPLS tc范围应该在0-7之间";
            return result;
        }
        if(!StringUtils.isBlank(rule.getSourceMac()) && !rule.getSourceMac().matches(trueMacAddress)){
            result="匹配域中二层源MAC地址格式不正确，正确格式应该由6个字节（十六进制）组成，例如:48:5D:60:61:3D:C5";
            return result;
        }
        if(!StringUtils.isBlank(rule.getDestinationMac()) && !rule.getDestinationMac().matches(trueMacAddress)){
            result="匹配域中二层目的MAC地址格式不正确，正确格式应该由6个字节（十六进制）组成，例如:48:5D:60:61:3D:C5";
            return result;
        }
        if(!StringUtils.isBlank(rule.getSourceIp())){
            int ind= rule.getSourceIp().indexOf("/");
            if(ind<0){
                result="匹配域中三层源Ip地址格式不正确，请带上掩码，例如：10.0.219.31/16";
                return result;
            }
            String ip= rule.getSourceIp().substring(0,ind);  //ip
            String mask= rule.getSourceIp().substring(ind+1);        //掩码
            if(!CheckIpv4SameSegment.ipV4Validate(ip)){
                result="匹配域中三层源Ip地址格式不正确，正确格式应该由4组0-255之间的数字组成,并且带掩码，例如：10.0.219.31/16";
                return result;
            }
            if(!CheckIpv4SameSegment.maskValidate(mask)){
                result="匹配域中三层源Ip地址不正确，请检查目标网段掩码，例如：10.0.219.31/16";
                return result;
            }
        }
        if(!StringUtils.isBlank(rule.getDestinationIp()))
        {
            int ind= rule.getDestinationIp().indexOf("/");
            String ip= rule.getDestinationIp().substring(0,ind);  //ip
            String mask= rule.getDestinationIp().substring(ind+1);        //掩码
            if(!CheckIpv4SameSegment.ipV4Validate(ip)){
                result="匹配域中三层目的Ip地址格式不正确，正确格式应该由4组0-255之间的数字组成，并且带掩码，例如：10.0.219.31/16";
                return result;
            }
            if(!CheckIpv4SameSegment.maskValidate(mask)){
                result="匹配域中三层目的Ip地址不正确，请检查目标网段掩码，例如：10.0.219.31/16";
                return result;
            }
        }
        if(rule.getSourcePort()!=null && (rule.getSourcePort()<0 || rule.getSourcePort()>4095)){
            result="匹配域中四层源端口范围应该在0-4095之间";
            return result;
        }
        if(rule.getDestinationPort()!=null && (rule.getDestinationPort()<0 || rule.getDestinationPort()>4095)){
            result="匹配域中四层目的端口范围应该在0-4095之间";
            return result;
        }
        //动作
        if(!StringUtils.isBlank(rule.getUpdateSourceMac()) && !rule.getUpdateSourceMac().matches(trueMacAddress)){
            result="动作中源MAC地址格式不正确，正确格式应该由6个字节（十六进制）组成，例如:48:5D:60:61:3D:C5";
            return result;
        }
        if(!StringUtils.isBlank(rule.getUpdateDestinationMac()) && !rule.getUpdateDestinationMac().matches(trueMacAddress)){
            result="动作中目的MAC地址格式不正确，正确格式应该由6个字节（十六进制）组成，例如:48:5D:60:61:3D:C5";
            return result;
        }
        if(!StringUtils.isBlank(rule.getUpdateSourceIp())&& !CheckIpv4SameSegment.ipV4Validate(rule.getUpdateSourceIp())){
            result="动作中源Ip地址格式不正确，正确格式应该由4组0-255之间的数字组成，例如：10.0.219.31";
            return result;
        }
        if(!StringUtils.isBlank(rule.getUpdateDestinationIp())&& !CheckIpv4SameSegment.ipV4Validate(rule.getUpdateDestinationIp())){
            result="动作中目的Ip地址格式不正确，正确格式应该由4组0-255之间的数字组成，例如：10.0.219.31";
            return result;
        }

        return result;
    }

}

