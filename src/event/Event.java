package event;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Event extends JFrame {
   public Event(String title, int width, int height) {
      setTitle(title); // 제목
      setSize(width, height); // 너비, 높이
      setLocationRelativeTo(this); // 위치
//      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램이 끝나면 닫고 종료한다 
   
      // 레이아웃
      setLayout(new BorderLayout());
      
      JPanel p1 = new JPanel();
      p1.setBorder(BorderFactory.createEmptyBorder());
      
      ImageIcon imgs1 = new ImageIcon("Images/로고.png");
      JLabel lbl1 = new JLabel(imgs1);
      p1.add(lbl1);
      add(p1, BorderLayout.NORTH);
      p1.setBackground(Color.WHITE);
      
      
      JPanel p2 = new JPanel();
      p2.setLayout(new GridLayout(3, 1));
      p2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
      
      ImageIcon imgs2 = new ImageIcon("Images/이벤트1.png");
      JLabel lbl2 = new JLabel(imgs2);
      p2.add(lbl2);
      add(p2);
      p2.setBackground(Color.WHITE);
      p2.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173)), "이벤트"));
      
      JPanel p3 = new JPanel();
      p3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
      
      ImageIcon imgs3 = new ImageIcon("Images/이벤트2.png");
      JLabel lbl3 = new JLabel(imgs3);
      p2.add(lbl3);
      add(p2);
      p2.setBackground(Color.WHITE);
      
      JPanel p4 = new JPanel();
      p4.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
      
      ImageIcon imgs4 = new ImageIcon("Images/이벤트3.png");
      JLabel lbl4 = new JLabel(imgs4);
      p2.add(lbl4);
      add(p2);
      p2.setBackground(Color.WHITE);
      
      setVisible(true); // 실행 결과 창을 눈에 보이게 한다
   }
   public static void main(String[] args) {
      new Event("이벤트창", 620, 680);
   }

}