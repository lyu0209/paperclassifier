/*
 * 这个类是用来处理SCI论文的
 * 对选中的SCI论文清单进行加工，从中筛选出属于CSE的论文;
 * 比较清单每条记录的所有作者地址项，看看是否是否属于CSE，并在记录的第一项标注结果 
 * 可能的结果如下：
 * 非控制论文；第一单位；第n单位
 */
package paperclassifier;

import java.io.*;
import org.apache.poi.hssf.usermodel.*;


/**
 *
 * @author kyk
 */
class ExcelProcessor {

    void sciProcessing(String s1, String s2) {
	// s1是待处理的SCI论文清单
        // s2是用来筛选的地址清单
	String fileSCI = s1;
        String fileAddress = s2;
		
	try {
	// 打开待处理的SCI文件(excel)，加载指定的sheet
        // 注意，是xls类型的文件
        
        // 加载SCI清单文件的Sheet1
            HSSFWorkbook wbSCI = new HSSFWorkbook(new FileInputStream(fileSCI));
            HSSFSheet shSCI = wbSCI.getSheet("Sheet1");
	
        // 加载Address清单文件
            HSSFWorkbook wbAddress = new HSSFWorkbook(new FileInputStream(fileAddress));
            HSSFSheet shUsefulAddress = wbAddress.getSheet("useful");
            HSSFSheet shUselessAddress = wbAddress.getSheet("useless");
        
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
        
			
			
	// 打开对应参考的excel文件，加载指定的sheet
            HSSFWorkbook wbRef = new HSSFWorkbook(new FileInputStream(fileSCI));
            HSSFSheet shRef = wbRef.getSheet("address");

	// 读取参考地址(有用的地址)的行数
            int phyRowNumRef = shRef.getPhysicalNumberOfRows();
		
	} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
            e.printStackTrace();
	} catch (IOException e) {
	// TODO Auto-generated catch block
            e.printStackTrace();
	}
		


    }
    
}
