/**
 * 这个类是用来处理SCI论文的
 * 逐行读出SCI论文的作者单位，分割后逐项进行判断是否属于控制学院（CSE），以及第几单位，并记录;
 * 逐行读出SCI论文的通讯地址，分割后逐项判断是否属于控制学院（CSE），并记录。
 * 可能的结果如下：
 * 非控制论文；第一单位；第n单位
 * 通讯单位（Y）
 */
package paperclassifier;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.*;


/**
 * @version v1.0
 * @author 于玲
 */
public class ExcelProcessor {

    void sciProcessing(String fileName1, String fileName2) {
	// fileName1是待处理的SCI论文清单
        // fileName2是用来筛选的地址清单
	String fileSCI = fileName1;
        String fileAddress = fileName2;
        // 定义有用地址列表、无用地址列表和不能确定地址列表
        List<String> usefulAddress = new ArrayList<>();
        List<String> uselessAddress = new ArrayList<>();
        List<String> undeterminedAddress = new ArrayList<>();
		
        /*
        首先打开地址清单文件，将有用的地址（useful）、没用的地址（useless）和不能确定的地址（undetermined）分别读入到列表中
        usefulAddress列表中保存有用的地址
        uselessAddress列表中保存无用的地址
        undeterminedAddress列表中保存不能确定的地址
        关闭地址清单文件
        */
        try {
            // 加载Address清单文件
            HSSFWorkbook wbAddress = new HSSFWorkbook(new FileInputStream(fileAddress));
            HSSFSheet shUsefulAddress = wbAddress.getSheet("useful");            
            HSSFSheet shUselessAddress = wbAddress.getSheet("useless");
            HSSFSheet shUndeterminedAddress = wbAddress.getSheet("undetermined");
            
            // 将useful地址存放到usefulAddress列表中
            int phyRowNum = shUsefulAddress.getPhysicalNumberOfRows();          // 读出有用地址清单（useful）中的行数
            // 逐个读取每行的地址（都在第2列），并加入usefulAddress中
            for(int i=1;i<phyRowNum;i++){
                String address = shUsefulAddress.getRow(i).getCell(1).getStringCellValue();
                usefulAddress.add(address);
            }
            
            // 将useless地址存放到uselessAddress列表中
            phyRowNum = shUselessAddress.getPhysicalNumberOfRows();             // 读出无用的浙大地址清单（useless）中的行数
            // 逐个读取每行的地址（都在第2列），并加入uselessAddress中
            for(int i=1;i<phyRowNum;i++){
                String address = shUselessAddress.getRow(i).getCell(1).getStringCellValue();
                uselessAddress.add(address);
            }
            
            // 将undetermined地址存放到undeterminedAddress列表中
            phyRowNum = shUndeterminedAddress.getPhysicalNumberOfRows();             // 读出不能确定的地址清单（undetermined）中的行数
            // 逐个读取每行的地址（都在第2列），并加入undeterminedAddress中
            for(int i=1;i<phyRowNum;i++){
                String address = shUndeterminedAddress.getRow(i).getCell(1).getStringCellValue();
                undeterminedAddress.add(address);
            }            
        }catch (Exception e) {
	// TODO Auto-generated catch block
            e.printStackTrace();
	}
        
        /*
        处理SCI论文清单，每行是一条SCI论文记录
        注意：待处理的SCI论文清单最前两列必须是用来标注论文的归属以及通讯单位归属的
        逐行搜索比较所有作者地址（C1）和通讯作者（RP），并记录比较的结果        
        */
	try {
	// 打开待处理的SCI文件(excel)，加载指定的sheet
        // 注意，是xls类型的文件
        
        // 加载SCI清单文件的Sheet1
            HSSFWorkbook wbSCI = new HSSFWorkbook(new FileInputStream(fileSCI));
            HSSFSheet shSCI = wbSCI.getSheet("Sheet1");
       
	// 读取待处理SCI清单文件的条目，找到"C1"项和"RP"项        
            int coloumNum = shSCI.getRow(0).getPhysicalNumberOfCells();         //获得SCI清单第一行的总列数
            int coloumC1 = 0;         // C1所在的列   
            int coloumRP = 0;           // RP所在的列
            // 逐条读取第一行（0）的每一项，找到"C1"和"RP"项所在的列        
            for(int i=0;i<coloumNum;i++){
                switch(shSCI.getRow(0).getCell(i).getCellType()){
                    case HSSFCell.CELL_TYPE_STRING: // 字符串
                        String item = shSCI.getRow(0).getCell(i).getStringCellValue();
                        if(item.equals("C1")){
                            coloumC1 = i;
                            break;
                        }
                        if(item.equals("RP")){
                            coloumRP = i;
                            break;                        
                        }
                        break;
                    default:
                        break;
                }
            }        			
			
	/*
        逐行读取C1列的地址，然后将地址进行分解。
        对分解得到的地址依次进行判断是否为有用地址。
        如果是有用地址，则标记是第几单位（地址），然后开始下一行地址的判断
        如果都是无用的地址，则标记为非控制，然后开始下一行地址的判断
        如果无法判断是有用还是无用地址，则弹出对话框进行地址判断，并把判断的结果添加到地址列表中
        */
	
            addressProcessing addressOperator = new addressProcessing();            // 定义一个地址操作器
            int phyRowNum = shSCI.getPhysicalNumberOfRows();                        // 读取SCI清单表格的行数
            for(int i=1;i<phyRowNum;i++){
                System.out.println("现在开始判断第" + i + "行！");
        // 首先判断论文的归属（即，论文是第几单位）
            // 读出第i行中的C1项（C1项在columnC1）
                HSSFCell cell = shSCI.getRow(i).getCell(coloumC1);
                String allAddress = "";
                if(cell != null)
                    allAddress = shSCI.getRow(i).getCell(coloumC1).getStringCellValue();
            // 将alladdress分解成数个address
                List<String> allAddressList = new ArrayList<>();
                allAddressList = addressOperator.divideSciAll(allAddress);
            // 逐一判断地址情况，并返回判断结果            
            // 采用一个整数cFlag返回对比结果，可能的返回值包括
            // 1 --- 是控制论文；-1 --- 非控制论文； 0 --- 无法判断；      
                int cFlag = 0;                                                      // 标识地址的收录属性
               //boolean ifUn = false;                                           // 标识地址是可识别的
                for (int j = 0; j < allAddressList.size(); j++) {
                    cFlag = addressOperator.judgeState(allAddressList.get(j),usefulAddress,uselessAddress,undeterminedAddress);
                    // 根据cFlag的结果做进一步的操作
                    // 如果是控制的论文(cFlag is 1)，则在i行的第一列中写入“第j+1单位”
                    if(cFlag == 1){
                        StringBuilder sb = new StringBuilder();
                        sb.append("第");
                        sb.append(String.valueOf(j+1));
                        sb.append("单位");
                        String paperOrganization = sb.toString();               // 记录文章为第j+1单位
                        shSCI.getRow(i).createCell(0).setCellValue(paperOrganization);     // 写入第1行第1列单元中
                        break;
                    }
                    // 如果无法判断(cFlag=0)，则根据是否是最后一个单位来进行判断
                    else if(cFlag == 0){
                        shSCI.getRow(i).createCell(0).setCellValue("无法判断");
                        break;
                    }
                // 如果不是控制论文，则根据是否是最后一个单位来进行判断                 
                    else {
                    // 如果不是最后一个单位，则没有动作；如果是最后一个单位，则在i行的第一列中写入“非控制论文”
                        if (j == (allAddressList.size()-1)) {                    // 是最后一个单位
                            shSCI.getRow(i).createCell(0).setCellValue("非控制论文");     // 写入第i行第1列单元中
                        }
                    }          
                }
            
        // 其次判断论文的通讯单位
        // 如果是控制论文(cFlag == 1)，则继续判断通讯单位
                if(cFlag == 1){
            // 读出第i行中的RP项（RP项在columnRP）
                    cell = shSCI.getRow(i).getCell(coloumRP);
                    String rpAddress = "";
                    if(cell != null)                
                        rpAddress = cell.getStringCellValue();
            // 将rpAddress分解成数个address
                    List<String> rpList = new ArrayList<>();
                    rpList = addressOperator.divideRPAddress(rpAddress);
            
            // 逐一判断地址情况，并返回判断结果                        
                    for (int j = 0; j < rpList.size(); j++) {
                // 采用一个整数result返回对比结果，可能的返回值包括
                // 1 --- 是控制通讯；-1 --- 非控制通讯； 0 --- 无法判断 
                        int result = addressOperator.judgeState(rpList.get(j),usefulAddress,uselessAddress,undeterminedAddress);
                // 根据result的结果做进一步的操作
                // 如果是控制的通讯(result is 1)，则在i行的第二列中写入“Y”
                        if(result == 1){
                            shSCI.getRow(i).createCell(1).setCellValue("Y");     // 写入第1行第2列单元中
                            break;
                        }
                // 如果无法判断（result is 0），则在i行的第二列中写入“无法判断”                 
                        else if(result == 0) {
                            shSCI.getRow(i).createCell(1).setCellValue("无法判断");     // 写入第i行第2列单元中
                            break;
                        }
                        else{
                    // 如果不是最后一个单位，则没有动作；如果是最后一个单位，则在i行的第一列中写入“N”
                            if (j == (allAddressList.size()-1)){                    // 是最后一个单位
                                shSCI.getRow(i).createCell(1).setCellValue("N");     // 写入第i行第2列单元中
                            }
                        } 
                    }
                }
            }
        
        // 将识别结果写入excel文档中
            FileOutputStream fileOut = new FileOutputStream(fileSCI); {
                wbSCI.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }            	
	} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
            e.printStackTrace();
	} catch (IOException e) {
	// TODO Auto-generated catch block
            e.printStackTrace();
	}
		
        // 更新地址清单
        try {
            HSSFWorkbook wbAddress = new HSSFWorkbook(new FileInputStream(fileAddress));
            HSSFSheet shUsefulAddress = wbAddress.getSheet("useful");            
            HSSFSheet shUselessAddress = wbAddress.getSheet("useless");
            HSSFSheet shUndeterminedAddress = wbAddress.getSheet("undetermined");

            // 将usefulAddress列表中的地址存放到useful sheet中
            int phyRowNum = shUsefulAddress.getPhysicalNumberOfRows();          // 读出有用地址清单（useful）中的行数
            // 从usefulAddress列表中新增的地址开始，逐个读取地址并写入useful sheet中（第2列）
            for(int i=phyRowNum-1;i<usefulAddress.size();i++){
                String address = usefulAddress.get(i);
                shUsefulAddress.createRow(i+1).createCell(0).setCellValue(i+1);
                shUsefulAddress.getRow(i+1).createCell(1).setCellValue(address);                 
            }
            
            // 将uselessAddress列表中的地址存放到useless sheet中
            phyRowNum = shUselessAddress.getPhysicalNumberOfRows();          // 读出useless 地址清单中的行数
            // 从uselessAddress列表中新增的地址开始，逐个读取地址并写入useless sheet中（第2列）
            for(int i=phyRowNum-1;i<uselessAddress.size();i++){
                String address = uselessAddress.get(i);
                shUselessAddress.createRow(i+1).createCell(0).setCellValue(i+1);
                shUselessAddress.getRow(i+1).createCell(1).setCellValue(address);                 
            }
            
            // 将undeterminedAddress列表中的地址存放到undetermined sheet中
            phyRowNum = shUndeterminedAddress.getPhysicalNumberOfRows();          // 读出有用地址清单（useful）中的行数
            // 从undeterminedAddress列表中新增的地址开始，逐个读取地址并写入undetermined sheet中（第2列）
            for(int i=phyRowNum-1;i<undeterminedAddress.size();i++){
                String address = undeterminedAddress.get(i);
                shUndeterminedAddress.createRow(i+1).createCell(0).setCellValue(i+1);
                shUndeterminedAddress.getRow(i+1).createCell(1).setCellValue(address);                 
            }
            
            // write into the excell file
            FileOutputStream fileOut = new FileOutputStream(fileAddress); {
            wbAddress.write(fileOut);
            fileOut.flush();
            fileOut.close();
            }     
        } catch (IOException e) {
	// TODO Auto-generated catch block
            e.printStackTrace();
	}
        System.out.println("It's over, sucessful!");
    }
    
}
