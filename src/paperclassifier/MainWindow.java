/*
 * This is the main window.
 * Select different function from the main window.
 * 
 */
package paperclassifier;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.0
 * @author Yu Ling
 */
public class MainWindow extends JFrame{

    JPanel panel = new JPanel();
    JLabel jl = new JLabel("请选择待处理的事项");
    JButton jb1 = new JButton("SCI单位筛选");
    JButton jb2 = new JButton("EI单位筛选");
    
    public MainWindow() {
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));//布局
	panel.add(jl);
        panel.add(jb1);
        panel.add(jb2);
        
        this.add(panel);
        
        jb1.addActionListener(new ButtonAction("SCI"));
        
        this.setSize(320, 300);
        this.setLocation(150,75);
        this.setVisible(true);
    }
    
    public class ButtonAction implements ActionListener{
        private String paperType;
        
        public ButtonAction(String s){
            paperType = s;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(paperType){
                // 当选择“SCI单位筛选”按钮时，进入SCI选择窗口
                case "SCI":
                    new SciSelector();
                    break;
                default:
                    //...;
                    break;
            } 
            dispose();      // 关闭当前窗口
        }
    }
    
    
}
