/**
 * 这个类用来选择待识别的文件，必是xls格式的excel文件；
 * 还可以选择用来判断地址的地址清单文件，建议采用项目自带的Address.xls文件;
 * 如果采用其他的地址清单文件，需满足一下条件：
 * <ul>
 * <li> 文件必须是xls格式的excel文件
 * <li> 文件中包括三个sheets，分别命名为useful, useless, undetermined
 * <li> 每张表包含2列，第一列是序号，第二列是地址列
 * </ul>
 */
package paperclassifier;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

/**
 * @version 1.0
 * @author Yu Ling
 */

public class FileSelector extends JFrame{
    Container container1 = getContentPane();           //设置一个容器    
    
    // define panels and the modules
    
    private JLabel jl = new JLabel("这个窗口用来选择待处理的论文");
    private JPanel panel1 = new JPanel(new GridLayout(1,1));    // use to put j2    
    private JLabel j2 = new JLabel("注意：待处理论文清单的最前两列是用来标注单位排名和通讯单位的");
    
    private JPanel panel2 = new JPanel(new GridLayout(1,3));    // use to put j2
    private JLabel j3 = new JLabel("请选择待处理论文类型");
    private JRadioButton radio1 = new JRadioButton("SCI");// 定义一个单选按钮
    private JRadioButton radio2 = new JRadioButton("EI");// 定义一个单选按钮 
    private ButtonGroup group = new ButtonGroup();
    
    private JPanel panel3 = new JPanel(new GridLayout(1,2));    // use to put jb1 and jt1
    private JButton jb1 = new JButton("请选择待处理的论文：");    
    private JTextField jt1 = new JTextField("",20);
    
    private JPanel panel4 = new JPanel(new GridLayout(1,2));    // use to put jb2 and jt2
    private JButton jb2 = new JButton("请选择地址清单文件：");
    private JTextField jt2 = new JTextField("",20);
    
    private JPanel panel5 = new JPanel(new GridLayout(1,1));    // use to put jb3
    private JButton jb3 = new JButton("开始");
	
    public FileSelector(){
        //将整个容器设置为5行1列的网格布局,网格布局管理器x,y代表行和列
        container1.setLayout(new GridLayout(5,1));
        //panel.setBorder(BorderFactory.createTitledBorder("这个窗口用来选择待处理的论文"));// 定义一个面板的边框显示条
		
	//panel.setLayout(new FlowLayout(FlowLayout.LEFT));//布局
        
	//panel.add(jl);
        panel1.add(j2);
        panel2.add(j3);
        panel2.add(radio1);
        panel2.add(radio2);
        //ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
	panel3.add(jb1);
	panel3.add(jt1);
	panel4.add(jb2);
	panel4.add(jt2);
        panel5.add(jb3);
		
	//this.add(panel);
	container1.add(panel1);
        container1.add(panel2);
        container1.add(panel3);
        container1.add(panel4);
        container1.add(panel5);
        
	jb1.addActionListener(new ChooseFile(jt1));
        jb2.addActionListener(new ChooseFile(jt2));
        jb3.addActionListener(new AddressSelect());
        
        setTitle("文件选择窗口");
        this.setSize(500, 300);
        this.setLocation(150,75);
        this.setVisible(true);
        // 实现按掉右上角的×后整个程序自动退出
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public class ChooseFile implements ActionListener{
        private JTextField jt;
        
        public ChooseFile(JTextField jtext){
            jt = jtext;
        }
       	@Override
       	public void actionPerformed(ActionEvent e){
            JFileChooser jf = new JFileChooser();  
            jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
            jf.showDialog(new JLabel(), "选择");  
            File f =  jf.getSelectedFile();//使用文件类获取选择器选择的文件 
            String s = f.getAbsolutePath();//返回路径名  
            jt.setText(s);
        }
    }
    
    public class AddressSelect implements ActionListener{
        @Override
  	public void actionPerformed(ActionEvent e){
            String sciFile = jt1.getText();
            String addressFile = jt2.getText();
            ExcelProcessor ep = new ExcelProcessor();
            ep.sciProcessing(sciFile,addressFile);
            dispose();      // 关闭当前窗口
	}    
    }
}