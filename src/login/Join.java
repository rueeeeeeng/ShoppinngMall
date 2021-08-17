package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Join extends JFrame implements ActionListener {
   
   private JButton j1;

   
   public <E> Join(String title) {
      setTitle(title);
      setSize(500, 500);
//      setLocation(300, 300);
      setLocationRelativeTo(this);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      setLayout(new BorderLayout());
      setVisible(true);
      
      JPanel p0 = new JPanel();
      p0.setBackground(Color.white);
      add(p0,BorderLayout.NORTH);
      ImageIcon imgs = new ImageIcon("Images/쇼핑몰로고.png");
      JLabel logo = new JLabel(imgs);
      p0.add(logo);
      
      
      JPanel p = new JPanel();
      p.setBackground(Color.white);
      add(p,BorderLayout.SOUTH);
      JLabel l1= new JLabel("이름");
      JLabel l2= new JLabel("아이디");
      JLabel l3= new JLabel("패스워드");
      JLabel l4= new JLabel("계좌정보");
      JLabel l5= new JLabel("이메일");
      JLabel l6= new JLabel("폰번호");
      
      add(l1);
      add(l2);
      add(l3);
      add(l4);
      add(l5);
      add(l6);
      
      TextField t1 = new TextField();
      TextField t2 = new TextField();
      TextField t3 = new TextField();
      TextField t4 = new TextField();
      TextField t5 = new TextField();
      TextField t6 = new TextField();
      
      add(t1);
      add(t2);
      add(t3);
      add(t4);
      add(t5);
      add(t6);
      
      t3.setEchoChar('*');
      
      j1 = new JButton("확인");
      JButton j2 = new JButton("취소");
      j1.setBackground(Color.black);
      j2.setBackground(Color.black);
      j1.setForeground(Color.white);
      j2.setForeground(Color.white);
      
      add(j1);
      add(j2);
      
      JButton j3 = new JButton("중복확인");
      j3.setBackground(Color.black);
      j3.setForeground(Color.white);
      add(j3);
      
      
      String cb1[]= {"하나은행","신한은행","농협은행", "카카오뱅크","우리은행" };
      JComboBox<E> CBmenu = new JComboBox(cb1);
      CBmenu.setBounds(350, 130, 90, 30);
      CBmenu.setBackground(Color.black);
      CBmenu.setForeground(Color.white);
      add(CBmenu);
      
      l1.setBounds(40,10,40,40);
      l2.setBounds(40,50,40,40);
      l3.setBounds(40,90,60,40);
      l4.setBounds(40,130,60,40);
      l5.setBounds(40,170,60,40);
      l6.setBounds(40,210,60,40);
      t1.setBounds(120,10,200,30);
      t2.setBounds(120,50,200,30);
      t3.setBounds(120,90,200,30);
      t4.setBounds(120,130,200,30);
      t5.setBounds(120,170,200,30);
      t6.setBounds(120,210,200,30);
      j1.setBounds(125,330,80,30);
      j2.setBounds(240,330,80,30);
      j3.setBounds(350, 50, 90, 30);
      
      j1.addActionListener(this);
    
  
      
      add(p);
      
      
      
      
      
   }

   public static void main(String[] args) {
      db.DB_Lib.init();
      new Join("회원가입");
      
     
   }

@Override
public void actionPerformed(ActionEvent e) {
   Object obj = e.getSource();
   
   if(obj == j1) {
      JOptionPane.showMessageDialog(null, "회원가입 성공", "회원가입", JOptionPane.PLAIN_MESSAGE);
   }
   
}

}