/*
 * Copyright © 2018 vcmy_lnf and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.vcmy.util;

import java.util.regex.Pattern;

/**
 * 项目版本：IP校验 
 * @作者：廖年丰
 * @描述：IPv4校验
 * 原理：
 * IP地址范围： 
 * 0.0.0.0～255.255.255.255，包括了mask地址。 
 * 
 * IP地址划分: 
 * A类地址：1.0.0.1～126.255.255.254 
 * B类地址：128.0.0.1～191.255.255.254 
 * C类地址：192.168.0.0～192.168.255.255 
 * D类地址：224.0.0.1～239.255.255.254 
 * E类地址：240.0.0.1～255.255.255.254 
 * 
 * 如何判断两个IP地址是否是同一个网段中: 
 * 要判断两个IP地址是不是在同一个网段，就将它们的IP地址分别与子网掩码做与运算，得到的结果一网络号，如果网络号相同，就在同一子网，否则，不在同一子网。 
 * 例：假定选择了子网掩码255.255.254.0，现在分别将上述两个IP地址分别与掩码做与运算，如下图所示： 
 * 211.95.165.24 11010011 01011111 10100101 00011000 
 * 255.255.254.0 11111111 11111111 11111110 00000000 
 * 与的结果是: 11010011 01011111 10100100 00000000 
 * 
 * 211.95.164.78 11010011 01011111 10100100 01001110 
 * 255.255.254.0 11111111 11111111 11111110 00000000 
 * 与的结果是: 11010011 01011111 10100100 00000000 
 * 可以看出,得到的结果(这个结果就是网络地址)都是一样的，因此可以判断这两个IP地址在同一个子网。 
 * 
 * 如果没有进行子网划分，A类网络的子网掩码为255.0.0.0，B类网络的子网掩码为255.255.0.0，C类网络的子网掩码为255.255.255.0，缺省情况子网掩码为255.255.255.0 
 */  
 

public class CheckIpv4SameSegment {
	
	// IpV4的正则表达式，用于判断IpV4地址是否合法  
    private static final String IPV4_REGEX = 
    		"((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})";  

    // 系统子网掩码，它与ip组成一个地址  

     
    // 1代表A类，2代表B类，3代表C类；4代表其它类型  
    public static final int IP_A_TYPE = 1;  
    public static final int IP_B_TYPE = 2;  
    public static final int IP_C_TYPE = 3;  
    public static final int IP_OTHER_TYPE = 4;  
     
    // A类地址范围：1.0.0.1---126.255.255.254  
    private static int[] IpATypeRange;  
    // B类地址范围：128.0.0.1---191.255.255.254  
    private static int[] IpBTypeRange;  
    // C类地址范围：192.168.0.0～192.168.255.255  
    private static int[] IpCTypeRange;  
     
    // A,B,C类地址的默认mask  
    private static int DefaultIpAMask;  
    private static int DefaultIpBMask;  
    private static int DefaultIpCMask;  
    
 // 初始化  
    static  
    {  
         IpATypeRange = new int[2];  
         IpATypeRange[0] = getIpV4Value("1.0.0.1");  
         IpATypeRange[1] = getIpV4Value("126.255.255.254");  
          
         IpBTypeRange = new int[2];  
         IpBTypeRange[0] = getIpV4Value("128.0.0.1");  
         IpBTypeRange[1] = getIpV4Value("191.255.255.254");  
          
         IpCTypeRange = new int[2];  
         IpCTypeRange[0] = getIpV4Value("192.168.0.0");  
         IpCTypeRange[1] = getIpV4Value("192.168.255.255");  
          
         DefaultIpAMask = getIpV4Value("255.0.0.0");  
         DefaultIpBMask = getIpV4Value("255.255.0.0");  
         DefaultIpCMask = getIpV4Value("255.255.255.0");  
    }

    
	/** 
     * 默认255.255.255.0 
     */ 
	public CheckIpv4SameSegment() {  
 
    }  
      
     /** 
     * @param mask 任意的如"255.255.254.0"等格式，如果格式不合法，抛出UnknownError异常错误 
     */  
	public CheckIpv4SameSegment(String masks) {  
	    int mask = getIpV4Value(masks);  
	      if(mask == 0)  
	      {  
	           throw new UnknownError();  
	      }  
	}  
	  

     
  
	/**
	 * 输入两个Ip及掩码位数，进行比较是否在同网段
	 * @param ip1
	 * @param ip2
	 * @param mask 位数
	 */

	  /** 
	     * 比较两个ip地址是否在同一个网段中，如果两个都是合法地址，两个都是非法地址时，可以正常比较； 
	     * 如果有其一不是合法地址则返回false； 
	     * 注意此处的ip地址指的是如“192.168.1.1”地址 
	     * @return 
	     */  
	public static boolean checkSameSegment(String ip1,String ip2, String mask)  {  
          // 判断IPV4是否合法  
		if(ip1==null  || ip2==null){
			return false;
		} 
		else {
	        int masks = getIpV4Value(mask);
	        int ipValue1 = getIpV4Value(ip1);  
	        int ipValue2 = getIpV4Value(ip2);  
	        return (masks & ipValue1) == (masks & ipValue2); 
		}
	}

	   
	 	  

	   /** 
	   * 当没有指明mask时，使用默认mask
	   * @return 返回默认mask的校验结果
	   */  
	public static boolean checkSameSegmentByDefault(String ip1,String ip2) {  
		if(ip1.equals("") && !ipV4Validate(ip1) && ip2.equals("") && !ipV4Validate(ip2)){
			return false;
		}
	      String mask = getDefaultMaskStr(ip1);     // 获取默认的Mask 
	      return checkSameSegment(ip1,ip2,mask);  
	}
	
 
	/** 
     * 获取ip、mask 与运算的字符串结果
     * @param ipV4 
     * @param mask  如“255.255.255.0”
     * @return  返回网段，如“192.168.1.0/24”
     */ 
	public static String getSegmentString(String ipV4, String mask)  {  
        int ipValue = getIpV4Value(ipV4); 
        int masks = getIpV4Value(mask);
        String segement = trans2IpStr(masks & ipValue);
        int maskInt = maskStringToInt(mask);
        return (segement+"/" + maskInt);  
	}
	
	/** 
     * 获取ip、mask 与运算的字符串结果
     * @param ipV4 
     * @param mask  如“24”
     * @return  返回网段，如“192.168.1.0/24”
     */ 
	public static String getSegmentString(String ipV4, int mask)  {  
		
		try {
	        int ipValue = getIpV4Value(ipV4); 
	        String maskStr = maskIntToString(mask);
	        int masks = getIpV4Value(maskStr);
	        String segement = trans2IpStr(masks & ipValue);
	        return (segement+"/" + mask); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
     * 获取ip 与默认mark，运算的字符串结果
     * @param ipV4 10.0.0.1
     * @param mask  如A类
     * @return  返回网段，如“10.0.0.0/8”
     */ 
	  public static String getDefaultSegmentString(String ipV4)  { 
		  String segement = null;
		  String maskStr = null;
		  if( !ipV4.equals("") ){
	        int ipValue = getIpV4Value(ipV4); 
	        maskStr = getDefaultMaskStr(ipV4);
	       try{
		        int masks = getIpV4Value(maskStr);
	        	segement = trans2IpStr(masks & ipValue);
	       }
	       catch (Exception e) {
	    	   e.printStackTrace();
		}
	        
        } 
        return (segement+"/" + maskStringToInt(maskStr));  
	}
	
	
		 /** 
	     * 获取ip、默认mask 与运算的二进制结果值
	     * @param ipV4 
	     * @return  32bit值 
	     */  
	public int getSegmentValue(String ipV4)  {  
          int ipValue = getIpV4Value(ipV4); 
          int mask = getDefaultMaskValue(ipV4);
          return (mask & ipValue);  
	}
	    
	    /** 
	     * 获取ip、mask 与运算的二进制结果值
	     * @param ipV4 
	     * @param mask 
	     * @return  32bit值的网段 
	     */  
	public static int getSegmentValue(String ip, String mask)  {  
		if(ip.equals("") && !ipV4Validate(ip) ){
			return 0;
		}
          int ipValue = getIpV4Value(ip); 
          int masks = getIpV4Value(mask);
          return (masks & ipValue);  
	} 
	
	/**
	 * 校验mask的合法性，掩码格式如“255.255.255.0”
	 * @param mask 掩码
	 * @return 
	 */
	public static boolean maskValidate(String mask){

		String pattern =
				"(254|252|248|240|224|192|128|0)\\.0\\.0\\.0|255\\.(254|252|248|240|224|192|128|0)\\.0\\.0|255\\.255\\.(254|252|248|240|224|192|128|0)\\.0|255\\.255\\.255\\.(255|254|252|248|240|224|192|128|0)|^[1-9]$|^2\\d$|^3[0-2]$"; 
		return v4Validate(mask, pattern);
	}
	
	/**
	 * 将掩码字符转换成位数，如将“255.255.255.0”转换为24
	 * @param mask 掩码
	 * @return 返回掩码位数
	 * 掩码无效则返回0
	 */
	public static int maskStringToInt(String mask){
		if(maskValidate(mask)){
			int iMask = getIpV4Value(mask);
			int countOf1 = 0;
			int tag =1;
			while(tag!=0){
				if((tag & iMask) != 0){
					countOf1++;
				}
					tag = tag<<1;
			}
				return countOf1;
			
		}else{
			
			return 0;
		}
		
	}
	
	/**
	 * 将掩码位数转为掩码字符串，如24位转为“255.255.255.0”
	 * @param maskBit 掩码位数
	 * @return 返回掩码字符串
	 */
	public static String maskIntToString(int maskBit){
		
		if(maskBit > 32){
			return null;
		}
		String tag1 = "";
		
		for(int i=0;i<maskBit;i++){
			tag1=tag1.concat("1");
		}
		String tag2 = "";
		for (int n=(32-maskBit);n>0;n--){
			tag2 = tag2.concat("0");
		}
		String tag = tag1+tag2;

		int x = 0;
		for(char c:tag.toCharArray())
			x=x*2+(c == '1'?1:0);

		String mask = trans2IpStr(x);
		
			return mask;  
	}
	
	
	     
     /** 
      * 判断ipV4是否合法，通过正则表达式方式进行判断 
      * @param ipv4 IPv4地址
      */  
	public static boolean ipV4Validate(String ipv4)  {  
          return v4Validate(ipv4,IPV4_REGEX);  
	}  
	private static boolean v4Validate(String addr,String regex)  {  
           if(addr == null)  
           {  
               return false;  
          }  
          else  
          {  
               return Pattern.matches(regex, addr.trim());  
          }  
	}  
	
	/** 
     * 比较两个ip地址，如果两个都是合法地址，则1代表ip1大于ip2，-1代表ip1小于ip2,0代表相等； 
     * 如果有其一不是合法地址，如ip2不是合法地址，则ip1大于ip2，返回1，反之返回-1；两个都是非法地址时，则返回0； 
     * 注意此处的ip地址指的是如“192.168.1.1”地址，并不包括mask 
     * @return 
     */  
	public static int compareIpV4s(String ip1,String ip2) {  
		if(ip1.equals("") && !ipV4Validate(ip1) && ip2.equals("") && !ipV4Validate(ip2)){
			return 0;
		}
          int result = 0;  
          int ipValue1 = getIpV4Value(ip1);     // 获取ip1的32bit值  
          int ipValue2 = getIpV4Value(ip2); // 获取ip2的32bit值  
          if(ipValue1 > ipValue2)  
          {  
               result =  -1;  
          }  
          else   
          {  
               result = 1;  
          }  
          return result;  
    }  
	
	/** 
     * 检测ipV4 的类型，包括A类，B类，C类，其它（C,D和广播）类等 
     * @param ipV4 
     * @return     返回1代表A类，返回2代表B类，返回3代表C类；返回4代表D类 
     */  
	public static int checkIpV4Type(String ipV4)  {
		if(ipV4.equals("") && !ipV4Validate(ipV4)){
			return 0;
		}
          int inValue = getIpV4Value(ipV4);  
          if(inValue >= IpCTypeRange[0] && inValue <= IpCTypeRange[1])  
          {  
               return IP_C_TYPE;  
          }  
          else if(inValue >= IpBTypeRange[0] && inValue <= IpBTypeRange[1])  
          {  
               return IP_B_TYPE;  
          }  
          else if(inValue >= IpATypeRange[0] && inValue <= IpATypeRange[1])  
          {  
               return IP_A_TYPE;  
          }  
          return IP_OTHER_TYPE;  
	}  
	
	 /** 
     * 获取默认mask值，如果IpV4是A类地址，则返回{@linkplain #DefaultIpAMask}， 
     * 如果IpV4是B类地址，则返回{@linkplain #DefaultIpBMask}，以此类推 
     * @param anyIpV4 任何合法的IpV4 
     * @return mask 32bit值 
     */  
	public static int getDefaultMaskValue(String anyIpV4)  {  
		if(anyIpV4.equals("") && !ipV4Validate(anyIpV4) ){
			return 0;
		}
          int checkIpType = checkIpV4Type(anyIpV4);  
          int maskValue = 0;  
          switch (checkIpType)  
          {  
               case IP_C_TYPE:  
                    maskValue = DefaultIpCMask;  
                    break;  
               case IP_B_TYPE:  
                    maskValue = DefaultIpBMask;  
                    break;  
               case IP_A_TYPE:  
                    maskValue = DefaultIpAMask;  
                    break;  
               default:  
                    maskValue = DefaultIpCMask;  
          }  
          return maskValue;  
	}  
	   
	
	 /** 
     * 获取默认mask地址，A类地址对应255.0.0.0，B类地址对应255.255.0.0， 
     * C类及其它对应255.255.255.0 
     * @param anyIp 
     * @return mask 字符串表示 
     */  
	public static String getDefaultMaskStr(String anyIp)  {  
		if(anyIp.equals("") && !ipV4Validate(anyIp) ){
			return null;
		}
          return trans2IpStr(getDefaultMaskValue(anyIp));  
    }  
	
	 /** 
     * 将32bit格式，相位偏移， 进行拼接,并转码
     * @param ipValue   32bit
     * @return 返回如“192.168.1.1”、“255.255.255.0”等格式的字符串 
     */  
    public static String trans2IpStr(int ipValue)  {  
          // 保证每一位地址都是正整数  
          return ((ipValue >> 24) & 0xff) + "." + ((ipValue >> 16) & 0xff) + "." + ((ipValue >> 8) & 0xff) + "." + (ipValue & 0xff);  
    }  
    
    
    /**
     * 将分片后的 bytes进行转码拼接
     * @param ipBytes ip字节数组
     * @return 
     * 返回如“192.168.1.1”、“255.255.255.0”等格式的字符
     */
    public static String trans2IpV4Str(byte[] ipBytes)  
    {  
         // 保证每一位地址都是正整数  
         return (ipBytes[0] & 0xff) + "." + (ipBytes[1] & 0xff) + "." + (ipBytes[2] & 0xff) + "." + (ipBytes[3] & 0xff);  
    }  
  
    /**
     * 将ip或者mask分片，二进制转码，进行|或,相位偏移，组合成32bit
     * @param ipOrMask IP字符
     * @return 32bit
     */
    public static int getIpV4Value(String ipOrMask)  {  
    	if(ipOrMask.equals("") && !ipV4Validate(ipOrMask) ){
			return 0;
		}
          byte[] addr = getIpV4Bytes(ipOrMask);  
          int address1  = addr[3] & 0xFF;  
          address1 |= ((addr[2] << 8) & 0xFF00);  
          address1 |= ((addr[1] << 16) & 0xFF0000);  
          address1 |= ((addr[0] << 24) & 0xFF000000);  
          return address1;  
    }  
    
    /**
     * 将IP地址按.进行分片，0xff低8位转二进制
     * @param ipOrMask IP字符
     * @return 分片后的字节数组
     */
    public static byte[] getIpV4Bytes(String ipOrMask) {  
    	if(ipOrMask.equals("") && !ipV4Validate(ipOrMask) ){
			return new byte[4];
		}
         try  
         {  
              String[] addrs = ipOrMask.split("\\.");  
              int length = addrs.length;  
              byte[] addr = new byte[length];  
              for (int index = 0; index < length; index++)  
              {  
                   addr[index] = (byte) (Integer.parseInt(addrs[index]) & 0xff);  
              }  
              return addr;  
         }  
         catch (Exception e)  
         {  
        	 e.printStackTrace();
         }  
         return new byte[4];  
    }  
    
    
    /**
     * 当startIp和endIp在同网段，且startIp < endIp时，
     * 校验currentIp是否在startIp和endIp之间
     * @param startIp 起始IP
     * @param endIp 结束IP
     * @param currentIp 当前IP
     * @param mask 掩码
     */
	public static boolean IsIp3ButweenIp1AndIp2(String startIp,String endIp,String currentIp , String mask)  { 
		
		if(startIp.equals("") && !ipV4Validate(startIp) && endIp.equals("") && !ipV4Validate(endIp)
				&& currentIp.equals("") && !ipV4Validate(currentIp)){
			return false;
		}
		if(checkSameSegment(startIp, endIp, mask) && compareIpV4s(endIp, startIp)>=0
				&& checkSameSegment(startIp, currentIp, mask) 
				&& ((compareIpV4s(currentIp, startIp)) >= 0) && (compareIpV4s(currentIp, endIp) <= 0)){			
			return true;
		}else{
            return false;
			
		}
	}
     
}
