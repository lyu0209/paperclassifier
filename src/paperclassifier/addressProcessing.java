/*
 * 用来对地址信息进行分解、比较等操作
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paperclassifier;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 于玲
 */
class addressProcessing {

    // DivideSciAll方法将SCI清单中所有作者地址分解成单个地址列表
    public static List<String> divideSciAll(String s) {
        // 首先在待处理地址的最后添加分号(;)
        String allAddress = s;
        StringBuilder str = new StringBuilder(allAddress);
        str.append(";");
        allAddress = str.toString();
        
        // 采用正则表达式多次截取两个字符"]"和";"之间的值                
        List<String> allAddressList = new ArrayList<>();
        Pattern p = Pattern.compile("](.*?);");
        Matcher m = p.matcher(allAddress);
        while (m.find()) {
            allAddressList.add(m.group(1));
        }
        return allAddressList;
    }

    public int judgeState(String s, List<String> usefulAddress, List<String> uselessAddress) {
        // 首先除去地址前后的空格
        String address = s.trim();
        // 初始化返回状态
        int value = 0;
        
        // 判断是否是浙江大学的论文        
        String zju = "Zhejiang Univ,";
        //int index = address.indexOf(zju);
        // 如果是浙江大学的论文，则判断是否控制学院论文
        //if(index != -1){
        if(address.contains(zju)){
            // 查看是否在useful地址清单中。如果在，则是控制学院论文，返回1
            if(usefulAddress.contains(address)){
                value = 1;            
            }
            // 查看是否在useless地址清单中。如果在，则不是控制学院论文，返回-1
            else if (uselessAddress.contains(address)){
                value = -1;
            }
            // 两张清单中都没有
            else{
                // 如果地址中包含control，则返回0（无法判断）
                if(address.toLowerCase().contains("control"))
                    value = 0;
                else
                    value = -1;
            }
        }
        // 如果不精确包含字符串“Zhejiang Univ,”，则不是浙江大学的单位，直接返回状态为-1
        else{
            value = -1;
        }
        
        return(value);
    }
    
    public static void main(String[] args) {
        // 测试类内方法
        String str = "[Pang, Bo; Zhao, Chao; Fu, Kaiyue; Song, Xiuling; Liu, Yushen; Li, Juan] Jilin Univ, Sch Publ Hlth, Dept Hyg Inspect, Changchun 130021, Jilin, Peoples R China; [Ding, Xiong; Wang, Guoping; Xu, Yanan; Sun, Jingjing; Wu, Wenshuai; Song, Qi; Hu, Jiumei; Mu, Ying] Zhejiang Univ, Res Ctr Analyt Instrumentat, Inst Cyber Syst & Control, State Key Lab Ind Control Technol, Hangzhou 310058, Zhejiang, Peoples R China";
        
        List<String> strs = divideSciAll(str);
        
        for (int j = 0; j < strs.size(); j++) {
            System.out.println(strs.get(j));
        }
        
        String str1 = "Han, F (reprint author), Zhejiang Univ, Affiliated Hosp 1, Coll Med, Kidney Dis Ctr, Hangzhou, Zhejiang, Peoples R China.; Han, F (reprint author), Key Lab Kidney Dis Prevent & Control Technol, 79 Qingchun Rd, Hangzhou 310003, Zhejiang, Peoples R China.; Han, F (reprint author), Natl State Adm Tradit Chinese Med, Grade Lab 3, Hangzhou, Zhejiang, Peoples R China.";
        List<String> s = divideRPAddress(str1);
        
        for(String str2 : s){
            System.out.println(str2);
        }
    }

    public static List<String> divideRPAddress(String s) {
        // 首先在待处理地址的最后添加分号(;)
        String allAddress = s;
        StringBuilder str = new StringBuilder(allAddress);
        str.append(";");
        allAddress = str.toString();
        
        // 采用正则表达式多次截取两个字符"]"和";"之间的值                
        List<String> allAddressList = new ArrayList<>();
        Pattern p = Pattern.compile("\\).(.*?).;");
        Matcher m = p.matcher(allAddress);
        while (m.find()) {
            allAddressList.add(m.group(1));
        }
        return allAddressList;
    
    }
}
