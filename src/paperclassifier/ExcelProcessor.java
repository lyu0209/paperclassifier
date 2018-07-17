/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
