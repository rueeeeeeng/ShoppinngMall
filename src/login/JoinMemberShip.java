package login;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import db.DB_Lib;

public class JoinMemberShip extends JFrame implements ActionListener {
	private JButton btnOK, btnDelete, btnId;
	private JTextField tname, tid, temail, taccount, tphone1, tphone2, tphone3, temailId;
	private boolean check;
	private JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
	private String[] bankName = { "하나은행", "신한은행", "농협은행", "카카오뱅크", "우리은행" };
	private int count = 0;
	private int myMoney;
	private JPasswordField tpw, rePw;
	private String[] site = { "naver.com", "daum.net", "hamail.com", "nate.com", "gmail.com" };
	private JComboBox<String> cb;

	public JoinMemberShip(String title) {
		setTitle(title);
		setSize(550, 420);
		setLocationRelativeTo(this);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		p1 = new JPanel();
		p1.setBackground(Color.white);
		add(p1, BorderLayout.NORTH);

		p2 = new JPanel();
		p2.setBackground(Color.WHITE);
		p2.setLayout(new GridLayout(7, 1));
		add(p2, BorderLayout.CENTER);
		// p2.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		p3 = new JPanel();
		p3.setBackground(Color.WHITE);
		add(p3, BorderLayout.SOUTH);

		p4 = new JPanel();
		p2.add(p4);
		p4.setBackground(Color.WHITE);
		p4.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 0));

		p5 = new JPanel();
		p2.add(p5);
		p5.setBackground(Color.WHITE);
		p5.setLayout(new FlowLayout(FlowLayout.LEFT, 33, 0));

		p6 = new JPanel();
		p2.add(p6);
		p6.setBackground(Color.WHITE);
		p6.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 0));

		p7 = new JPanel();
		p2.add(p7);
		p7.setBackground(Color.WHITE);
		p7.setLayout(new FlowLayout(FlowLayout.LEFT, 29, 0));

		p8 = new JPanel();
		p2.add(p8);
		p8.setBackground(Color.WHITE);
		p8.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 0));

		p9 = new JPanel();
		p2.add(p9);
		p9.setBackground(Color.WHITE);
		p9.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

		p10 = new JPanel();
		p2.add(p10);
		p10.setBackground(Color.WHITE);
		p10.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));

		ImageIcon imgs = new ImageIcon("images/logo.png");
		Image img = imgs.getImage();
		Image chimg = img.getScaledInstance(130, 60, Image.SCALE_SMOOTH);
		ImageIcon chicon = new ImageIcon(chimg);
		JLabel logo = new JLabel(chicon);
		p1.add(logo);

		JLabel lname = new JLabel("name : ");
		lname.setHorizontalAlignment(lname.CENTER);
		JLabel lid = new JLabel("id : ");
		lid.setHorizontalAlignment(lname.CENTER);
		btnId = new JButton("Confirm");
		btnId.setBackground(new Color(1, 92, 173));
		btnId.setForeground(Color.WHITE);
		JLabel lpw = new JLabel("pw : ");
		lpw.setHorizontalAlignment(lname.CENTER);
		JLabel lre = new JLabel("rePw : ");
		lre.setHorizontalAlignment(lname.CENTER);
		JLabel laccount = new JLabel("account : ");
		laccount.setHorizontalAlignment(lname.CENTER);
		JLabel lemail = new JLabel("email : ");
		lemail.setHorizontalAlignment(lname.CENTER);
		JLabel lphone = new JLabel("phone : ");
		lphone.setHorizontalAlignment(lname.CENTER);
		JLabel gol = new JLabel("@");
		gol.setHorizontalAlignment(lname.CENTER);

		cb = new JComboBox<String>(site);

		tname = new JTextField(10);
		tid = new JTextField(10);
		tpw = new JPasswordField(15);
		rePw = new JPasswordField(15);
		taccount = new JTextField(20);
		temail = new JTextField(10);
		tphone1 = new JTextField(5);
		tphone2 = new JTextField(5);
		tphone3 = new JTextField(5);
		temailId = new JTextField(10);

		JLabel lb1 = new JLabel("-");
		JLabel lb2 = new JLabel("-");

		p4.add(lname);
		p4.add(tname);

		p5.add(lid);
		p5.add(tid);
		p5.add(btnId);

		p6.add(lemail);
		p6.add(temail);
		p6.add(gol);
		p6.add(temailId);
		p6.add(cb);

		p7.add(lpw);
		p7.add(tpw);

		p8.add(lre);
		p8.add(rePw);

		p9.add(laccount);
		p9.add(taccount);

		p10.add(lphone);
		p10.add(tphone1);
		p10.add(lb1);
		p10.add(tphone2);
		p10.add(lb2);
		p10.add(tphone3);

		btnOK = new JButton("Join");
		btnDelete = new JButton("Cancel");
		btnOK.setBackground(new Color(1, 92, 173));
		btnOK.setForeground(Color.WHITE);
		btnDelete.setBackground(new Color(1, 92, 173));
		btnDelete.setForeground(Color.WHITE);

		p3.add(btnOK);
		p3.add(btnDelete);

		btnId.addActionListener(this);
		btnOK.addActionListener(this);
		btnDelete.addActionListener(this);

		cb.addActionListener(this);

		setVisible(true);

	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new JoinMemberShip("회원가입");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int index = cb.getSelectedIndex();
		Object obj = e.getSource();
		if (obj == btnOK) {
			if (tname.getText().equals("") || tid.getText().equals("") || tpw.getText().equals("")
					|| temail.getText().equals("") || taccount.getText().equals("") || tphone1.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "정보를 빠짐없이 입력해주세요!");
			} else if (!rePw.getText().equals(tpw.getText())) {
				JOptionPane.showMessageDialog(null, "입력된 패스워드가 같지 않습니다!");
				tpw.setText("");
				rePw.setText("");
			} else if (tpw.getText().length() > 12 || tpw.getText().length() < 6) {
				JOptionPane.showMessageDialog(null, "비밀번호를 6자리이상 12자리이하로 설정해주세요!");
				tpw.setText("");
				rePw.setText("");
			} else {
				if (count != 1) {
					JOptionPane.showMessageDialog(null, "아이디 중복확인을 해주세요.");
				} else {
					String name = tname.getText();
					String cuId = tid.getText();
					String cuPw = tpw.getText();
					String phone = tphone1.getText();
					String phone2 = tphone2.getText();
					String phone3 = tphone3.getText();
					String account1 = taccount.getText();
					String email = temailId.getText();
					String email2 = temail.getText();
					String femail = email2 + "@" + email;
					String fPhone = phone + "-" + phone2 + "-" + phone3;

					String sql = "INSERT INTO CUSTOMER VALUES" + "('" + name + "', '" + cuId + "', '" + cuPw + "', '"
							+ fPhone + "', '" + account1 + "', '" + femail + "')";

					db.DB_Lib.executeQuery(sql);

					int r1 = (int) (Math.random() * 4);
					String bank = bankName[r1];

					int r2 = (int) (Math.random() * 10000000 + 10000);
					myMoney = r2;

					String sql2 = "INSERT INTO ACCOUNT_INFO VALUES" + "('" + account1 + "', '" + bank + "', '" + myMoney
							+ "')";
					db.DB_Lib.executeQuery(sql2);

					JOptionPane.showMessageDialog(null, "회원가입 완료!");
					tname.setText("");
					tid.setText("");
					tpw.setText("");
					rePw.setText("");
					taccount.setText("");
					temail.setText("");
					tphone1.setText("");
					tphone2.setText("");
					tphone3.setText("");
					temailId.setText("");
				}

			}

		} else if (obj == btnDelete) {
			dispose();
		} else if (obj == btnId) {
			String id = tid.getText();
			check = overlap(id);
			if (check == true) {
				JOptionPane.showMessageDialog(null, "중복된 아이디가 있으므로 다시 입력해주세요.", "Message",
						JOptionPane.INFORMATION_MESSAGE);
				tid.setText("");
				temailId.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다!");
				count = 1;
				temailId.setText("");
			}
		}
		if (index == 0) {
			temailId.setText("naver.com");
		} else if (index == 1) {
			temailId.setText("daum.net");
		} else if (index == 2) {
			temailId.setText("hanmail.com");
		} else if (index == 3) {
			temailId.setText("nate.com");
		} else if (index == 4) {
			temailId.setText("gmail.com");
		}
	}

	private boolean overlap(String id) {
		boolean check = false;
		String sql = "SELECT ID FROM CUSTOMER WHERE ID = '" + id + "'";
		ResultSet rs = db.DB_Lib.getResultSet(sql);

		try {
			if (rs.next()) {
				check = true;
			} else {
				check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;

	}

}