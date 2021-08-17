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

public class MonitorAdd extends JFrame implements ActionListener{
   private JPanel p1, p2, p3;
   private JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7;
   private JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7;
   private JButton b1, b2;
   private boolean check;

   public MonitorAdd(String title, int width, int height) {
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
      p2.setLayout(new GridLayout(6,2,5,5));
      lb2 = new JLabel("상품 코드 : ");
      tf1 = new JTextField(25);
      lb2.setHorizontalAlignment(JLabel.CENTER);
      
      lb3 = new JLabel("제조사 : ");
      tf2 = new JTextField(25);
      lb3.setHorizontalAlignment(JLabel.CENTER);
      
      lb4 = new JLabel("모델명 : ");
      tf3 = new JTextField(25);
      lb4.setHorizontalAlignment(JLabel.CENTER);
      
      lb5 = new JLabel("해상도 : ");
      tf4 = new JTextField(25);
      lb5.setHorizontalAlignment(JLabel.CENTER);
      
      lb6 = new JLabel("무게 : ");
      tf5 = new JTextField(25);
      lb6.setHorizontalAlignment(JLabel.CENTER);
      
      lb7 = new JLabel("가격 : ");
      tf6 = new JTextField(25);
      lb7.setHorizontalAlignment(JLabel.CENTER);

      
      p2.add(lb2);
      p2.add(tf1);
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

      p2.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 2), " 상품 : 모니터 "));
   }

   public static void main(String[] args) {
      db.DB_Lib.init();
      new MonitorAdd("상품추가 입력창", 270, 290);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      if(obj==b1) {
         if(tf1.getText().equals("") || tf2.getText().equals("") || tf3.getText().equals("") || tf4.getText().equals("") ||
            tf5.getText().equals("") || tf6.getText().equals("")) {
               JOptionPane.showMessageDialog(null, "공백없이 입력해주세요.");
            } else {
               String code = tf1.getText();
               check = overlap(code);
               if(check) {
                  JOptionPane.showMessageDialog(null, "중복된 상품코드가 있으므로 다시 입력해주세요.", "Message", JOptionPane.INFORMATION_MESSAGE);
                  tf1.setText("");
                  tf2.setText("");
                  tf3.setText("");
                  tf4.setText("");
                  tf5.setText("");
                  tf6.setText("");
                  tf1.requestFocus();
               } else {
                  code = tf1.getText();
                  String brand = tf2.getText();
                  String mname = tf3.getText();
                  String resolution = tf4.getText();
                  String weight = tf5.getText();
                  String price = tf6.getText();
                  int bestSeller  = 0;
                  
                     String UpdateSql = "INSERT INTO MONITOR VALUES" + "('" + code + "', '" + brand + "', '" + mname + "', '" +
                     resolution + "', '" + weight + "', '" + price + "', '" + bestSeller +  "')";
                      
                        
                     db.DB_Lib.executeQuery(UpdateSql);
                     
                     JOptionPane.showMessageDialog(null, "상품 추가 완료!", "Success", JOptionPane.INFORMATION_MESSAGE);
                     tf1.setText("");
                     tf2.setText("");
                     tf3.setText("");
                     tf4.setText("");
                     tf5.setText("");
                     tf6.setText("");
            }
         
         
         
            tf1.requestFocus();
         } }else if(obj == b2) {
            dispose();
      }
   }

   private boolean overlap(String code) {
      boolean check = false;
      String sql = "SELECT CODE FROM MONITOR WHERE CODE = '" + code + "'";
      ResultSet rs = db.DB_Lib.getResultSet(sql);
      
      try {
         if(rs.next()) {
            check = true;
         } else {
            check = false;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } return check;
      
   }

}