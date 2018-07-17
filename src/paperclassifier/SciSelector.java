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
    // 加载待处理的文件
    JPanel panel = new JPanel();
    JLabel jl = new JLabel("这个窗口用来筛选浙江大学控制学院的SCI论文");
    JButton jb1 = new JButton("SCI清单");
    JTextField jt1 = new JTextField("",20);
    JButton jb2 = new JButton("开始");
    JTextField jt2 = new JTextField("",20);
	
    public SciSelector(){
		
	panel.setLayout(new FlowLayout(FlowLayout.LEFT));//布局
        
	panel.add(jl);
	panel.add(jb1);
	panel.add(jt1);
	panel.add(jb2);
	panel.add(jt2);
		
	this.add(panel);
		                
	jb1.addActionListener(new ChooseFile());
        jb2.addActionListener(new AddressSelect());
        
        this.setSize(320, 300);
        this.setLocation(150,75);
        this.setVisible(true);
    }
    
    public class ChooseFile implements ActionListener{
       	@Override
       	public void actionPerformed(ActionEvent e){
            JFileChooser jf = new JFileChooser();  
            jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
            jf.showDialog(new JLabel(), "选择");  
            File f =  jf.getSelectedFile();//使用文件类获取选择器选择的文件 
            String s = f.getAbsolutePath();//返回路径名  
            jt1.setText(s);
        }
    }
    
    public class AddressSelect implements ActionListener{
        @Override
  	public void actionPerformed(ActionEvent e){
            String s = jt1.getText();
            ExcelProcessor ep = new ExcelProcessor();
            ep.sciProcessing(s);
	}    
    }

    public static void main(String args[])
    {
        new SciSelector();
    }
}
