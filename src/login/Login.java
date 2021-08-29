package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import db.DB_Lib;
import main.CustomerMain;
import manager.ProductAddAndDelete;
import util.ButtonUtil;
import util.Frames;

public class Login extends Frames implements ActionListener, KeyListener {

	private JButton btnOK;
	private JButton btnFindID, btnFindPW;
	private JButton btnJoin;
	private JLabel lbl1, lbl2;
	private JTextField tfID;
	private JPasswordField tfPW;
	private JPanel p1, p2, p3, p2_left, p2_right;
	private int check;
	private JPanel p2_center;

	public Login(String title, int width, int height) {
		setTitle(title); // 제목
		setSize(width, height); // 너비, 높이
		setLocationRelativeTo(this); // 위치
//		setResizable(false); //창 사이즈 조정 금지
//   	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램이 끝나면 닫고 종료한다 

		// 레이아웃
		setLayout(new BorderLayout());

		p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder());

		ImageIcon imgs = new ImageIcon("Images/쇼핑몰로고.png");
		JLabel lbl = new JLabel(imgs);
		p1.add(lbl);
		add(p1, BorderLayout.NORTH);
		p1.setBackground(Color.WHITE);

		p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		p2.setBackground(Color.WHITE);
		
		p2_left = new JPanel();
		p2_left.setBackground(Color.white);
		p2_left.setLayout(new GridLayout(2, 2));
		p2_left.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		lbl1 = new JLabel("ID :", JLabel.CENTER);
		tfID = new JTextField(10);
		tfID.addKeyListener(this);
		lbl2 = new JLabel("PW :", JLabel.CENTER);
		tfPW = new JPasswordField(10);
		tfPW.addKeyListener(this);

		p2_left.add(lbl1);
		p2_left.add(lbl2);
		p2.add(p2_left, BorderLayout.WEST);

		p2_center = new JPanel();
		p2_center.setLayout(new BorderLayout());
		p2_center.add(tfID, BorderLayout.NORTH);
		p2_center.add(tfPW,BorderLayout.SOUTH);
		p2_center.setBackground(Color.WHITE);
		
		p2_center.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 5));
		p2.add(p2_center);
		
		p2_right = new JPanel();
		p2_right.setLayout(new BorderLayout());

		btnOK = new ButtonUtil("OK");
		btnOK.addActionListener(this);
		p2_right.add(btnOK);

		p2.add(p2_right, BorderLayout.EAST);
		add(p2);

		p3 = new JPanel();
		p3.setBorder(BorderFactory.createEmptyBorder(10, 0,10, 0));
		btnFindID = new ButtonUtil("ID 찾기");
		btnFindID.addActionListener(this);
		btnFindPW = new ButtonUtil("PW 찾기");
		btnFindPW.addActionListener(this);
		p3.add(btnFindID);
		p3.add(btnFindPW);
		btnJoin = new ButtonUtil("회원가입");
		btnJoin.addActionListener(this);
		p3.add(btnJoin);
		p3.setBackground(Color.WHITE);

		add(p3, BorderLayout.SOUTH);

		setVisible(true);
	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new Login("고객 로그인", 380, 250);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnOK) {
			login();

		} else if (obj == btnFindID) {
			new FindId("ID 찾기", 300, 150);
		} else if (obj == btnFindPW) {
			new FindPw("pw 찾기", 350, 200);
		} else if (obj == btnJoin) {
			new Join("회원가입");
		}
	}

	private int checkIDPW(String id, String pw) {
		int check = 0;
		String sqlCus = "SELECT * FROM CUSTOMER WHERE ID = '" + id + "' AND PW = '" + pw + "'";
		String sqlAdmin = "SELECT * FROM ADMIN WHERE ID = '" + id + "' AND PW = '" + pw + "'";

		ResultSet rs1 = db.DB_Lib.getResultSet(sqlCus);
		ResultSet rs2 = db.DB_Lib.getResultSet(sqlAdmin);

		try {
			if (rs1.next()) { // 고객 로그인
				check = 1;
			} else if (rs2.next()) { // 관리자 로그인
				check = 2;
			} else { // 로그인 실패
				check = 3;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return check;
	}

	public void keyPressed(KeyEvent e) { // 엔터 눌렀을때도 검색될 수 있도록 함
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			login();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void login() {
		String id = tfID.getText();
		String pw = tfPW.getText();
		check = checkIDPW(id, pw);
		if (check == 1) { // 고객 로그인 성공 시
			System.out.println("로그인 성공");
			String Sname = "SELECT name FROM CUSTOMER WHERE ID ='" + id + "'";
			ResultSet rsName = db.DB_Lib.getResultSet(Sname);
			try { // 사용자 이름 가져오기
				while (rsName.next()) {
					Sname = rsName.getString(1);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, Sname + "님 로그인 성공했습니다");
			String sql = "INSERT INTO CONNECTUSER VALUES('" + id + "')";
			ResultSet rs = db.DB_Lib.getResultSet(sql); // 수정
			new CustomerMain("메인", 500, 400);
			dispose();
		} else if (check == 2) { // 관리자 로그인 성공시
			JOptionPane.showMessageDialog(null, "관리자 로그인 성공했습니다");
			new ProductAddAndDelete();
			dispose();
		} else { // 로그인 실패
			System.out.println("로그인 실패");
			JOptionPane.showMessageDialog(null, "로그인 실패했습니다.");
			tfID.setText("");
			tfPW.setText("");
			tfID.requestFocus();
		}
	}
}