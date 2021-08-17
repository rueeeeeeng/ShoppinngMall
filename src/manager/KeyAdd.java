package manager;

import java.awt.BorderLayout;



import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


import db.DB_Lib;

public class KeyAdd extends JFrame implements ActionListener{
   private JPanel p1, p2, p3;
   private JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9;
   private JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8;
   private JButton b1, b2;
   private boolean check;
   private String count;
   private int in;
   private String code;
   private int a;

   public KeyAdd(String title, int width, int height) {
      setTitle(title);
      setSize(width, height);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(this);
      setResizable(false);
      
      p1 = new JPanel();
      add(p1, BorderLayout.NORTH);
      p1.setBackground(Color.WHITE);
      
      lb1 = new JLabel("판매 상품 추가");
      p1.add(lb1);
      
      p2 = new JPanel();
      add(p2, BorderLayout.CENTER);
      p2.setBackground(Color.WHITE);
      
      p2.setBorder(BorderFactory.createEmptyBorder(15,15, 15,15));

      
      MainAdd();
      
      p3 = new JPanel();
      add(p3, BorderLayout.SOUTH);
      p3.setBackground(Color.WHITE);
      
      b1 = new JButton("추가");
      b2 = new JButton("취소");
      
      p3.add(b1);
      p3.add(b2);
      
      b1.setBackground(Color.WHITE);
      b2.setBackground(Color.WHITE);
      
      b1.addActionListener(this);
      b2.addActionListener(this);
      
      setVisible(true);
   }

   private void MainAdd() {
      p2.setLayout(new GridLayout(8,2,5,5));

      lb9 = new JLabel("제품명 : ");
      lb9.setHorizontalAlignment(JLabel.CENTER);
      tf8 = new JTextField(25);
      
      lb3 = new JLabel("연결 방식 : ");
      tf2 = new JTextField(25);
      lb3.setHorizontalAlignment(JLabel.CENTER);
      
      lb4 = new JLabel("키 개수 : ");
      tf3 = new JTextField(25);
      lb4.setHorizontalAlignment(JLabel.CENTER);
      
      lb5 = new JLabel("사이즈 : ");
      tf4 = new JTextField(25);
      lb5.setHorizontalAlignment(JLabel.CENTER);
      
      lb6 = new JLabel("무게 : ");
      tf5 = new JTextField(25);
      lb6.setHorizontalAlignment(JLabel.CENTER);
      
      lb7 = new JLabel("가격 : ");
      tf6 = new JTextField(25);
      lb7.setHorizontalAlignment(JLabel.CENTER);
      
      lb8 = new JLabel("제조사 : ");
      tf7 = new JTextField(25);
      lb8.setHorizontalAlignment(JLabel.CENTER);
      
      
      p2.add(lb9);
      p2.add(tf8);
      p2.add(lb3);
      p2.add(tf2);
      p2.add(lb4);
      p2.add(tf3);
      p2.add(lb5);
      p2.add(tf4);
      p2.add(lb6);
      p2.add(tf5);
      p2.add(lb7);
      p2.add(tf6);
      p2.add(lb8);
      p2.add(tf7);
      p2.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 2), " 상품 : 키보드 "));
   }

   public static void main(String[] args) {
      db.DB_Lib.init();
      new KeyAdd("상품추가 입력창", 280, 330);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      if(obj==b1) {
         if(tf2.getText().equals("") || tf3.getText().equals("") || tf4.getText().equals("") ||
               tf5.getText().equals("") || tf6.getText().equals("") || tf7.getText().equals("") || tf8.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "공백없이 입력해주세요.");
         } else {
            //String sql = "SELECT * FROM KEYBOARD ORDER BY CODE";
            //DB.executeQuery(sql);
            //(SELECT * FROM KEYBOARD ORDER BY ROWNUM DESC)
            String c = "SELECT SUBSTR(CODE, 3,2) AS CODE FROM (SELECT * FROM KEYBOARD ORDER BY ROWNUM DESC) WHERE ROWNUM = 1";
            ResultSet rs2 = db.DB_Lib.getResultSet(c);
            try {
               while(rs2.next()) {
                  count = rs2.getString("CODE");
                  System.out.println(count);
               }
            } catch (SQLException e1) {
               e1.printStackTrace();
            }
            a = Integer.parseInt(count);
            in = a + 1;
            code = "K-" + in;
            String connectWay = tf2.getText();
            String kCount = tf3.getText();
            String pSize = tf4.getText();
            String weight = tf5.getText();
            String price = tf6.getText();
            String brand = tf7.getText();
            String name = tf8.getText();
            int bestSeller  = 0;
            
               String UpdateSql = "INSERT INTO KEYBOARD VALUES" + "('" + code + "', '" + connectWay + "', '" + kCount + "', '" +
               pSize + "', '" + weight + "', '" + price + "', '" + brand + "', '" + bestSeller + "', '" + name + "')";
                
                  
               db.DB_Lib.executeQuery(UpdateSql);
               
               JOptionPane.showMessageDialog(null, "상품 추가 완료!", "Success", JOptionPane.INFORMATION_MESSAGE);
               tf2.setText("");
               tf3.setText("");
               tf4.setText("");
               tf5.setText("");
               tf6.setText("");
               tf7.setText("");
               tf8.setText("");
         }
         
         }
      else if(obj == b2) {
            dispose();
      }
   }



}