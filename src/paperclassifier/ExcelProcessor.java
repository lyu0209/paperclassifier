/*
 * 这个类是用来处理SCI论文的
 * 对选中的SCI论文清单进行加工，从中筛选出属于CSE的论文;
 * 比较清单每条记录的所有作者地址项，看看是否是否属于CSE，并在记录的第一项标注结果 
 * 可能的结果如下：
 * 非控制论文；第一单位；第n单位
 */
package paperclassifier;

import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;


/**
 *
 * @author kyk
 */
class ExcelProcessor {

    void sciProcessing(String fileName1, String fileName2) {
	// fileName1是待处理的SCI论文清单
        // fileName2是用来筛选的地址清单
	String fileSCI = fileName1;
        String fileAddress = fileName2;
        // 定义有用地址列表和无用地址列表
        List<String> usefulAddress = new ArrayList<>();
        List<String> uselessAddress = new ArrayList<>();
		
        /*
        首先打开地址清单文件，将有用的地址（useful）和没用的地址（useless）分别读入到两个列表中
        usefulAddress列表中保存有用的地址
        uselessAddress列表中保存无用的地址
        关闭地址清单文件
        */
        try {
            // 加载Address清单文件
            HSSFWorkbook wbAddress = new HSSFWorkbook(new FileInputStream(fileAddress));
            HSSFSheet shUsefulAddress = wbAddress.getSheet("useful");            
            HSSFSheet shUselessAddress = wbAddress.getSheet("useless");
            
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
        }catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
            e.printStackTrace();
	} catch (IOException e) {
	// TODO Auto-generated catch block
            e.printStackTrace();
	}
        
        /*
        
        */
	try {
	// 打开待处理的SCI文件(excel)，加载指定的sheet
        // 注意，是xls类型的文件
        
        // 加载SCI清单文件的Sheet1
            HSSFWorkbook wbSCI = new HSSFWorkbook(new FileInputStream(fileSCI));
            HSSFSheet shSCI = wbSCI.getSheet("Sheet1");
       
	// 读取待处理SCI清单文件的条目，找到"C1"项        
            //获得SCI清单第一行的总列数
            int coloumNum = shSCI.getRow(0).getPhysicalNumberOfCells();
            int coloumC1;         // C1所在的列    
            // 逐条读取第一行（0）的每一项，找到"C1"项所在的列        
            for(int i=0;i<coloumNum;i++){
                switch(shSCI.getRow(0).getCell(i).getCellType()){
                    case HSSFCell.CELL_TYPE_STRING: // 字符串
                        String item = shSCI.getRow(0).getCell(i).getStringCellValue();
                        if(item.equals("C1")){
                            coloumC1 = i;
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
            HSSFWorkbook wbRef = new HSSFWorkbook(new FileInputStream(fileSCI));
            HSSFSheet shRef = wbRef.getSheet("address");

	// 读取参考地址(有用的地址)的行数
            //int phyRowNumRef = shRef.getPhysicalNumberOfRows();
		
	} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
            e.printStackTrace();
	} catch (IOException e) {
	// TODO Auto-generated catch block
            e.printStackTrace();
	}
		


    }
    
}
