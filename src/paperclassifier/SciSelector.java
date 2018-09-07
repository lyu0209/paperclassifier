/*
 * Pick the papers of CSE from all ZJU papers;
 * Identify whether the CSE is the first unit;
 * Identify whether the CSE is the correspondence unit.
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

public class SciSelector extends JFrame{
    // 定义窗口上的组建
    JPanel panel = new JPanel();
    JLabel jl = new JLabel("这个窗口用来筛选浙江大学控制学院的SCI论文");
    JLabel j2 = new JLabel("待处理的SCI论文清单最前两列必须是用来标注论文的归属以及通讯单位归属的");
    JButton jb1 = new JButton("待处理的SCI清单");
    JTextField jt1 = new JTextField("",20);
    JButton jb2 = new JButton("选择地址列表文件");
    JTextField jt2 = new JTextField("",20);
    JButton jb3 = new JButton("开始");
	
    public SciSelector(){
		
	panel.setLayout(new FlowLayout(FlowLayout.LEFT));//布局
        
	panel.add(jl);
        panel.add(j2);
	panel.add(jb1);
	panel.add(jt1);
	panel.add(jb2);
	panel.add(jt2);
        panel.add(jb3);
		
	this.add(panel);
		                
	jb1.addActionListener(new ChooseFile(jt1));
        jb2.addActionListener(new ChooseFile(jt2));
        jb3.addActionListener(new AddressSelect());
        
        this.setSize(320, 300);
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
            
	}    
    }
}