/*
 * 这个类是用来处理SCI论文的
 * 对选中的SCI论文清单进行加工，从中筛选出属于CSE的论文;
 * 比较清单每条记录的所有作者地址项，看看是否是否属于CSE，并在记录的第一项标注结果 
 * 可能的结果如下：
 * 非控制论文；第一单位；第n单位
 */
package paperclassifier;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author kyk
 */
class ExcelProcessor {

    void sciProcessing(String s) {
	// 打开excel文件
	String fileReference = "D:/projects/paperselector/scireference.xls";
		
	try {
	// 打开待处理的原始excel文件，加载指定的sheet
            HSSFWorkbook wbSCI = new HSSFWorkbook(new FileInputStream(s));
            HSSFSheet shSCI = wbSCI.getSheet("Sheet1");
					
	// 读取待处理文件的行数
            int phyRowNumSCI = shSCI.getPhysicalNumberOfRows();
			
	// 找到待处理文件中作者地址所在的列
	// 读取待处理文件的列数
            int coloumNumSCI=shSCI.getRow(0).getPhysicalNumberOfCells();
            int coloumC1 = 0;
            for(int i=0; i<coloumNumSCI; i++) {
		String strSCI = shSCI.getRow(0).getCell(i).getStringCellValue();
		if(strSCI.equalsIgnoreCase("C1")) {
			coloumC1 = i;
			break;
			} 			
		}
			
	// 打开对应参考的excel文件，加载指定的sheet
            HSSFWorkbook wbRef = new HSSFWorkbook(new FileInputStream(fileReference));
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
