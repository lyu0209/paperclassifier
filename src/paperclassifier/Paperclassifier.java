/**
 * 这个项目用来识别SCI论文和EI论文是否属于浙江大学控制学院，以及通讯单位是否控制学院
 * 版本v1.0只具有识别SCI论文的功能。<p>
 * 
 * 用户从WOS中导出浙江大学的所有SCI论文，保存为xls格式，sheet取名为“Sheet”。
 * （建议在导出SCI论文是可选择单位为浙江大学，地址包含“control”，这样可以有效减少识别的工作量。）
 * 在运行项目前，用户需在SCI文件的最前面增加两个空列，第一列用来记录单位所属，第二列用来记录是否通讯单位。<p>
 *
 * 项目本身维护一个地址清单（文件名为Addresses.xls），其中包含3个Sheets，分别是
 * <list> useful --- 有用地址清单，其中的地址属于控制学院 
 * <list> useless --- 无用地址清单，其中的地址不属于控制学院
 * <list> undetermined --- 无法识别地址清单，其中的地址无法判断是否属于控制学院，常见的是重点实验室
 * 
 * 项目运行时选择相应的论文类型，待识别的论文清单文件，项目自带的地址清单文件即可。
 * 项目运行完毕，论文的单位排序和通讯单位情况已自动写入论文清单，但还需要用户手动识别项目无法识别的论文。
 */
package paperclassifier;


/**
 * @version 1.0
 * @author 于玲
 */
public class Paperclassifier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Call the main window
        new MainWindow();
       // new FileSelector();
    }
    

}
