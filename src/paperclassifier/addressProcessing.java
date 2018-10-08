/**
 * 这个类用来对地址信息进行分解、比较等操作
 * 包括3个方法
 * 方法divideSciAll 用来将所有作者单位信息（一个字符串）根据一定的特征进行分解，得到一个地址list
 * 方法divideRPAddress 用来将通讯作者单位信息（一个字符串）根据一定的特征进行分解，得到一个地址list
 * 方法judgeState 用来判断单个地址属于哪一个地址清单，useful or useless or undetermined
 */
package paperclassifier;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * @version v1.0
 * @author 于玲
 */
public class addressProcessing {

    // DivideSciAll方法将SCI清单中所有作者地址分解成单个地址列表
    public static List<String> divideSciAll(String s) {
        // 首先在待处理地址的最后添加分号(;)
        String allAddress = s;
        StringBuilder str = new StringBuilder(allAddress);
        str.append(";");
        allAddress = str.toString();
        
        // 采用正则表达式多次截取两个字符"]"和";"之间的值                
        List<String> allAddressList = new ArrayList<>();
        // 如果地址中没有"]"，则直接把整个alladdress加入清单中
        if(allAddress.contains("]")){
            Pattern p = Pattern.compile("](.*?);");
            Matcher m = p.matcher(allAddress);
            while (m.find()) {
                allAddressList.add(m.group(1));
            }
        }
        else{
            allAddressList.add(allAddress);
        }
        return allAddressList;
    }

    public int judgeState(String s, List<String> usefulAddress, List<String> uselessAddress, List<String> undeterminedAddress) {
        // 初始化返回状态
        int value = 2;
        
        // 如果是浙江大学的论文，且地址中包含"control"和"Hangzhou"，则继续判断；否则直接返回-1（非控制论文）
        String zju = "Zhejiang Univ,";
        if(s.contains(zju) && s.toLowerCase().contains("control") && s.contains(", Hangzhou") ){
            // 先简化地址，去掉邮编等无效信息
            int end = s.indexOf(", Hangzhou"); 
            String address = s.substring(0, end).trim();
            // 查看是否在useful地址清单中。如果在，则是控制学院论文，返回1
            if(usefulAddress.contains(address)){
                value = 1;            
            }
            // 查看是否在useless地址清单中。如果在，则不是控制学院论文，返回-1
            else if (uselessAddress.contains(address)){
                value = -1;
            }
            // 查看是否在undetermined地址清单中。如果在，则是无法判断的论文，返回0
            else if(undeterminedAddress.contains(address)){
                value = 0;
            }
            // 三张清单中都没有，进行人工识别
           else{
                // 使用showOptionDialog方法来弹出人工识别对话框
                // 设置对话框的参数
                String options[]={"是控制","不是控制","无法判断"};
                StringBuilder sb1 = new StringBuilder();
                sb1.append("需要人工识别的单位是：");
                sb1.append(s.trim());
                String reminder = sb1.toString();
                int cFlag = JOptionPane.showOptionDialog(null, reminder,"人工识别作者单位", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, "是控制");
                // 如果人工识别结果是属于控制学院，返回value为1，并将地址添加进入useful清单中
                if(cFlag == 0){
                    value = 1;
                    usefulAddress.add(address);
                }
                // 如果人工识别结果是不属于控制学院，返回value为-1，并将地址添加进入uselessful清单中
                else if(cFlag == 1){
                    value = -1;
                    uselessAddress.add(address);
                }
                // 否则，返回value为0，并将地址添加进入undetermined清单中
                else{
                    value = 0;
                    undeterminedAddress.add(address);
                }
            }
        }
        // 如果不精确包含字符串"Zhejiang Univ,"，"control"和", Hangzhou"，则不是浙江大学的单位，直接返回状态为-1
        else{
            value = -1;
        }        
        return(value);
    }
    
    public static void main(String[] args) {
        // 测试类内方法
        /*String str = "[Pang, Bo; Zhao, Chao; Fu, Kaiyue; Song, Xiuling; Liu, Yushen; Li, Juan] Jilin Univ, Sch Publ Hlth, Dept Hyg Inspect, Changchun 130021, Jilin, Peoples R China; [Ding, Xiong; Wang, Guoping; Xu, Yanan; Sun, Jingjing; Wu, Wenshuai; Song, Qi; Hu, Jiumei; Mu, Ying] Zhejiang Univ, Res Ctr Analyt Instrumentat, Inst Cyber Syst & Control, State Key Lab Ind Control Technol, Hangzhou 310058, Zhejiang, Peoples R China";
        
        List<String> strs = divideSciAll(str);
        
        for (int j = 0; j < strs.size(); j++) {
            System.out.println(strs.get(j));
        }
        
        String str1 = "Han, F (reprint author), Zhejiang Univ, Affiliated Hosp 1, Coll Med, Kidney Dis Ctr, Hangzhou, Zhejiang, Peoples R China.; Han, F (reprint author), Key Lab Kidney Dis Prevent & Control Technol, 79 Qingchun Rd, Hangzhou 310003, Zhejiang, Peoples R China.; Han, F (reprint author), Natl State Adm Tradit Chinese Med, Grade Lab 3, Hangzhou, Zhejiang, Peoples R China.";
        List<String> s = divideRPAddress(str1);
        
        for(String str2 : s){
            System.out.println(str2);
        }
        */
        String str2 = "Zhejiang Univ, Affiliated Hosp 1, Coll Med, Kidney Dis Ctr, Hangzhou, Zhejiang, Peoples R China.";
        int end = str2.indexOf(", Hangzhou"); 
        String s = str2.substring(0, end);
        System.out.println(s);
    }

    public static List<String> divideRPAddress(String s) {
        // 首先在待处理地址的最后添加分号(;)
        String allAddress = s;
        StringBuilder str = new StringBuilder(allAddress);
        str.append(";");
        allAddress = str.toString();
        
        // 采用正则表达式多次截取两个字符")"和";"之间的值                
        List<String> allAddressList = new ArrayList<>();
        if(allAddress.contains(")")){
            Pattern p = Pattern.compile("\\).(.*?).;");
            Matcher m = p.matcher(allAddress);
            while (m.find()) {
                allAddressList.add(m.group(1));
            }
        }
        else{
            allAddressList.add(allAddress);
        }
        return allAddressList;    
    }
}
