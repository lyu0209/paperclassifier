/*
 * 这是主窗口，用户在这个窗口选择识别SCI论文或者EI论文
 * v1.0版没有实现EI论文识别的跳转
 */
package paperclassifier;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.0
 * @author 于玲
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
        // 实现按掉右上角的×后整个程序自动退出
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
                case "EI":
                    // new EiSelector();
                    break;
                default:
                    //...;
                    break;
            } 
        }
    }
}
