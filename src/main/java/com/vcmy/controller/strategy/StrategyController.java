package com.vcmy.controller.strategy;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.vcmy.annotation.Log;
import com.vcmy.controller.BaseController;
import com.vcmy.entity.*;
import com.vcmy.service.strategy.RuleService;
import com.vcmy.service.strategy.StrategyService;
import com.vcmy.util.PageUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @ClassName: StrategyController
 * @Description: TODO   策略接口
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/22 14:33
 */

@Api(tags = "策略-策略配置相关接口")
@RestController
public class StrategyController extends BaseController {

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private RuleService ruleService;


    /**
     * @Author liaojiexin
     * @Description 查询所有策略和他们的规则，包括搜索和高级搜索，searchValue是搜索，高级搜索则传指定参数的内容
     * @Date 2020/12/24 10:21
     * @Param [strategy]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("查询所有策略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleId",value = "高级搜索规则id（整型）",dataType="Integer"),
            @ApiImplicitParam(name = "inPortGroupId",value = "高级搜索进端口id（整型）",dataType="Integer"),
            @ApiImplicitParam(name = "outPortGroupId",value = "高级搜索出端口id（整型）",dataType="Integer")
    })
    @RequestMapping(value = "/tapapi/strategy/selectstrategy",method = RequestMethod.GET)
    public Message selectStrategy(Strategy strategy, HttpServletRequest request,Integer ruleId,Integer inPortGroupId,Integer outPortGroupId){
        //高级搜索
        if (inPortGroupId!=null){
            List<Port> inPortGroups=new ArrayList<>();
            Port port=new Port();
            port.setPortId(inPortGroupId);
            inPortGroups.add(port);
            strategy.setInPortGroup(inPortGroups);
        }
        if (outPortGroupId!=null || ruleId!=null){
            List<OutPortGroup> outPortGroups=new ArrayList<>();
            OutPortGroup outPortGroupz=new OutPortGroup();
            if (outPortGroupId!=null){
                List<Port> portList=new ArrayList<>();
                Port port=new Port();
                port.setPortId(outPortGroupId);
                portList.add(port);
                outPortGroupz.setOutPortGroup(portList);
            }
            if (ruleId!=null){
                List<Rule> ruleList=new ArrayList<>();
                Rule rule=new Rule();
                rule.setRuleId(ruleId);
                ruleList.add(rule);
                outPortGroupz.setRuleList(ruleList);
            }
            outPortGroups.add(outPortGroupz);
            strategy.setOutPortGroupList(outPortGroups);
        }

        List<Strategy> list=strategyService.selectStrategy(strategy);
        List responce=PageUtils.startPage(list,strategy.getPage(),strategy.getCount());
        return responseMessage(list.size(),responce);
    }

    /**
     * @Author liaojiexin
     * @Description 根据规则id查询规则
     * @Date 2020/12/24 10:22
     * @Param [ruleId]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("根据规则id查询规则信息")
    @ApiModelProperty(value = "规则id",name = "ruleId")
    @RequestMapping(value = "/tapapi/strategy/selectrulebyid",method = RequestMethod.GET)
    public Message selectRuleById(Integer ruleId){
        Rule rule = ruleService.selectRulebyId(ruleId);
        return Message.ok(rule);
    }

    /**
     * @Author liaojiexin
     * @Description 添加策略和规则，传一个入端口组id，多个出端口组（包含多条规则）
     * @Date 2020/12/24 10:22
     * @Param [strategy]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("添加策略和规则")
    @Log(module = "策略-策略配置",action = "添加策略")
    @RequestMapping(value = "/tapapi/strategy/insertstrategy",method = RequestMethod.POST)
    public Message insertStrategy(@RequestBody List<Strategy> strategyList) throws CloneNotSupportedException {
        for (int j=0;j<strategyList.size();j++) {
            Map<String,Object> map=strategyService.checkStrategy(strategyList.get(j),"add");
            if (map.get("msg")!=null)
                return Message.error("第"+(j+1)+"条策略及后面的策略添加失败,原因:"+map.get("msg"));
            //规则判断
            for (OutPortGroup outPortGroup:strategyList.get(j).getOutPortGroupList()) {     //出端口组
                if(outPortGroup.getRuleList()!=null && outPortGroup.getRuleList().size()>0){
                    for (Rule rule:outPortGroup.getRuleList()){
                        if (!JSONObject.toJSONString(rule).equals("{}")){
                            String result=ruleService.checkStrategyRule(rule);
                            if (!result.equals("规则正确"))
                                return Message.error("第"+(j+1)+"条策略及后面的策略添加失败,原因:"+result);
                        }
                    }
                }
            }

            //执行命令
            if(!strategyService.insertStrategy(strategyList.get(j)).equals("添加成功"))
                return Message.error("第"+(j+1)+"条策略及后面的策略添加失败，请检查第"+(j+1)+"条策略的信息");
        }
        return Message.ok();
    }

    /**
     * @Author liaojiexin
     * @Description 删除策略，传策略id
     * @Date 2020/12/24 10:24
     * @Param [strategy]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("删除策略")
    @Transactional
    @Log(module = "策略",action = "删除策略")
    @RequestMapping(value = "/tapapi/strategy/deletestrategy",method = RequestMethod.DELETE)
    public Message deleteStrategy(Integer strategyId) throws CloneNotSupportedException{
        if (!strategyService.deleteStrategy(strategyId).equals("删除成功")) //删除策略
            return Message.error("删除失败");
        return Message.ok();
    }


    /**
     * @Author liaojiexin
     * @Description 修改策略，传策略和规则所有信息，设备上修改策略只能将旧策略删除再添加一条新的
     * @Date 2020/12/24 11:17
     * @Param [strategy]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("修改策略")
    @Transactional
    @Log(module = "策略",action = "修改策略")
    @RequestMapping(value = "/tapapi/strategy/updatestrategy",method = RequestMethod.PUT)
    public Message updateStrategy(@RequestBody Strategy strategyNew) throws CloneNotSupportedException{
        Map<String,Object> map=strategyService.checkStrategy(strategyNew,"update");
        if (map.get("msg")!=null)
            return Message.error("操作失败，原因："+map.get("msg"));
        //规则判断
        for (OutPortGroup outPortGroup:strategyNew.getOutPortGroupList()) {     //出端口组
            if(outPortGroup.getRuleList()!=null && outPortGroup.getRuleList().size()>0){
                for (Rule rule:outPortGroup.getRuleList()){
                    if (!JSONObject.toJSONString(rule).equals("{}")){
                        String result=ruleService.checkStrategyRule(rule);
                        if (!result.equals("规则正确"))
                            return Message.error(result);
                    }
                }
            }
        }

        String insert=strategyService.updateStrategy(strategyNew);    //添加新策略
        if(insert=="修改成功")
            return Message.ok();
        else
            return Message.error();
    }


    /**
     * @Author liaojiexin
     * @Description 修改规则
     * @Date 2021/1/6 17:04
     * @Param
     * @return
     **/
    @ApiOperation("根据规则id修改规则")
    @Transactional
    @Log(module = "策略",action = "修改策略规则")
    @RequestMapping(value = "/tapapi/strategy/updateRule",method = RequestMethod.PUT)
    public Message updateRule(@RequestBody Rule rule) throws CloneNotSupportedException {
        String result=ruleService.checkStrategyRule(rule);
        if(!result.equals("规则正确"))
            return Message.error(result);
        else
            if (!strategyService.updateRule(rule).equals("修改成功"))
                return Message.error();
        return Message.ok();
    }

    /**
     * @Author liaojiexin
     * @Description 策略导出
     * @Date 2020/12/24 15:55
     * @Param
     * @return
     **/
/*    @ApiOperation("策略导出")
    @Log(module = "策略-策略配置",action = "导出策略")
    @RequestMapping(value = "/tapapi/strategy/strategyexport", method = RequestMethod.POST)
    public void strategyExport(@RequestBody Strategy strategy,HttpServletResponse response) throws Exception{
        //查询出要导出的信息
        List<Strategy> strategyList=strategyService.selectStrategy(strategy);       //查询到的策略
        List<Strategy> strategyList1=new ArrayList<>();     //要导出的数据
        for (int i=strategyList.size()-1;i>-1;i--) {
            if(!StringUtils.isBlank(strategyList.get(i).getRule())){
                String[] ruleIds=strategyList.get(i).getRule().split(",");    //各条规则
                List<StrategyRule> strategyRuleList=new ArrayList<>();
                for (String ruleid:ruleIds) {
                    StrategyRule strategyRule=strategyRuleService.selectRulebyId(Integer.parseInt(ruleid));
                    strategyRuleList.add(strategyRule);

                    Strategy strategy1=new Strategy();
                    strategy1.setStrategyId(strategyList.get(i).getStrategyId());
                    strategy1.setRule(strategyList.get(i).getRule());
                    strategy1.setInPortGroup(strategyList.get(i).getInPortGroup());
                    strategy1.setOutPortGroup(strategyList.get(i).getOutPortGroup());
                    strategy1.setStrategyRuleList(strategyRuleList);
                    strategyList1.add(strategy1);
                }
            }else {
                strategyList.remove(strategyList.get(i));
            }
        }

        ExcelData data = new ExcelData();
        data.setName("信息数据");
        // 添加表头
        List<String> titles = new ArrayList();
        titles.add("策略ID");
        titles.add("入端口组");
        titles.add("规则组");
        titles.add("出端口组");
        titles.add("规则id");
        titles.add("VLAN范围");
        titles.add("源mac地址");
        titles.add("目的mac地址");
        titles.add("MPLS标签");
        titles.add("MPLS tc");
        titles.add("源IP");
        titles.add("目的IP");
        titles.add("协议");
        titles.add("源端口");
        titles.add("目的端口");
        titles.add("TCP flag");
        titles.add("VLAN动作");
        titles.add("VLAN Id");
        titles.add("MPLS动作");
        titles.add("动作MPLS标签");
        titles.add("修改源IP地址");
        titles.add("修改目的IP地址");
        titles.add("修改源MAC地址");
        titles.add("修改目的MAC地址");
        titles.add("报文截短");
        data.setTitles(titles);
        // 添加列
        List<List<Object>> rows = new ArrayList();
        List<Object> row = null;
        for (int i = 0; i < strategyList1.size(); i++) {
            row = new ArrayList();
            row.add(strategyList1.get(i).getStrategyId());
            row.add(strategyList1.get(i).getInPortGroup());
            row.add(strategyList1.get(i).getRule());
            row.add(strategyList1.get(i).getOutPortGroup());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getRuleId());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getVlanRange());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getSourceMac());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getDestinationMac());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getMplsLabel());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getMplsTc());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getSourceIp());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getDestinationIp());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getProtocol());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getSourcePort());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getDestinationPort());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getTcpFlag());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getVlanAction());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getVlanId());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getMplsAction());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getMplsActionLabel());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getUpdateSourceIp());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getUpdateDestinationIp());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getUpdateSourceMac());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getUpdateDestinationMac());
            row.add(strategyList1.get(i).getStrategyRuleList().get(0).getMessageTruncate());
            rows.add(row);
        }

        data.setRows(rows);

        SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = fdate.format(new Date()) + ".xls";
        ExcelUtils.exportExcel(response, fileName, data);
    }*/

    /**
     * @Author liaojiexin
     * @Description 导入策略    //还未完成
     * @Date 2020/12/25 9:24
     * @Param
     * @return
     **/
/*@ApiOperation("导入策略")
    @RequestMapping(value = "/tapapi/strategy/strategyimport",method = RequestMethod.POST)
    public void strategyImport(@RequestBody Map<String, Object> search){
        String fileName = search.get("fileName").toString();
        String base64 = search.get("file").toString();

//        [base64文件流转MultipartFile](https://blog.csdn.net/hcg012/article/details/102395540)

        MultipartFile multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(base64);
        try {
            InputStream inputStream = multipartFile.getInputStream();
            //进行版本选择解析方式
            Workbook wb = null;
            if (fileName.endsWith(".xlsx")) {
                wb = new XSSFWorkbook(inputStream);

            } else if (fileName.endsWith(".xls")) {
                wb = new HSSFWorkbook(inputStream);
            }
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum() + 1;
            //行循环开始
            for (int i = 1; i < rowNum; i++) {
                //行
                Row row = sheet.getRow(i);
                //每行的最后一个单元格位置
                int cellNum = row.getLastCellNum();
                String cellValue = null;
                //列循环开始
                for (int j = 0; j < cellNum; j++) {

                    Cell cell = row.getCell(Short.parseShort(j + ""));
                    if (null != cell) {
                        // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                cellValue = cell.getNumericCellValue() + ",";
                                break;
                            case HSSFCell.CELL_TYPE_STRING:
                                cellValue = StringUtils.trim(cell.getStringCellValue()) + ",";
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                break;
                            default:
                                cellValue = ",";
                                break;
                        }
                    } else {
                        cellValue = ",";
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}