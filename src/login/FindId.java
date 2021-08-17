package login;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DB_Lib;

public class FindId extends JFrame implements ActionListener{
   private JPanel p1, p2;
   private JLabel lbTel, lbEmail;
   private JTextField tf1, tf2;
   private JButton btnOk, btnCancel;
   private String get;
   private boolean check;
   public FindId(String title, int width, int height) {
      setTitle(title);
      setSize(width, height);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(this);
      setResizable(false);
      
      
      p1 = new JPanel();
      add(p1, BorderLayout.CENTER);
      
      lbTel = new JLabel("전화번호 : ");
      p1.add(lbTel);
      
      tf1 = new JTextField(18);
      p1.add(tf1);
      
      lbEmail = new JLabel("    E-Mail : ");
      p1.add(lbEmail);
      
      tf2 = new JTextField(18);
      p1.add(tf2);
      
      p2 = new JPanel();
      add(p2, BorderLayout.SOUTH);
      
      p2.setLayout(new FlowLayout());
      
      btnOk = new JButton("OK");
      p2.add(btnOk);
      btnOk.addActionListener(this);
      
      btnCancel = new JButton("Cancel");
      p2.add(btnCancel);
      btnCancel.addActionListener(this);
      
      btnOk.setBackground(Color.WHITE);
//      btnOk.setForeground(new Color(255,255,255));
      
      btnCancel.setBackground(Color.white);
//      btnCancel.setForeground(new Color(255,255,255));
      
      
      p1.setBackground(Color.WHITE);
      p2.setBackground(Color.WHITE);
      
      
      setVisible(true);
      
   }

   public static void main(String[] args) {
      db.DB_Lib.init();
      new FindId("ID 찾기", 300, 150);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      if(obj == btnOk) {
         if(tf1.getText().equals("") || tf2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ID/전화번호를 전부 다 입력하세요.");
            tf1.setText("");
            tf2.setText("");
            tf1.requestFocus();
         } else {
            String phone = tf1.getText();
            String email = tf2.getText();
            
            check = checkTelEmail(phone, email);
         
         if(check) {
            JOptionPane.showMessageDialog(null, "ID는 " + get + "입니다.");
            dispose();
         } else {
            JOptionPane.showMessageDialog(null, "고객님이 입력하신 정보로 추청되는 ID가 존재하지 않습니다.");
            tf1.setText("");
            tf2.setText("");
            tf1.requestFocus();
         }
         }
      }else if(obj == btnCancel) {
         dispose();
      }
   }


   private boolean checkTelEmail(String phone, String email) {
      boolean check = false;
      String sql = "SELECT ID FROM CUSTOMER WHERE PHONE = '" + phone + "' AND EMAIL = '" + email + "'";
      ResultSet rs = db.DB_Lib.getResultSet(sql);
      try {
         if(rs.next()) {
            check = true;
            get = rs.getString("ID");
            
         }else {
            check = false;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return check;
   }


}