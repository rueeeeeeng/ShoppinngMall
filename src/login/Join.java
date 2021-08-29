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
import util.Frames;

public class Join extends Frames implements ActionListener {
	private JButton btnOK, btnDelete, btnId;
	private JTextField name, id;
	private boolean check;
	private JPanel logoPanel, basePanel, btnPanel, p4, p5, p6, p7;
	private int count = 0;
	private JPasswordField pw, rePw;

	public Join(String title) {
		setTitle(title);
		setSize(400, 300);
		setLocationRelativeTo(this);
//		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		logoPanel = new JPanel(); // 로고 넣을 패널
		logoPanel.setBackground(Color.WHITE);
		add(logoPanel, BorderLayout.NORTH);

		basePanel = new JPanel(); //회원가입 구성요소 들어갈 패널
		basePanel.setBackground(Color.WHITE);
		basePanel.setLayout(new GridLayout(4, 1));
		add(basePanel, BorderLayout.CENTER);
		// p2.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		btnPanel = new JPanel(); //버튼 패널
		btnPanel.setBackground(Color.WHITE);
		add(btnPanel, BorderLayout.SOUTH);

		p4 = new JPanel(); //이름 요소 붙여넣을 패널
		basePanel.add(p4);
		p4.setBackground(Color.WHITE);
		p4.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 0));

		p5 = new JPanel(); //id 요소 붙여넣을 패널
		basePanel.add(p5);
		p5.setBackground(Color.WHITE);
		p5.setLayout(new FlowLayout(FlowLayout.LEFT, 33, 0));

		p6 = new JPanel(); //pw 요소 붙여넣을 패널
		basePanel.add(p6);
		p6.setBackground(Color.WHITE);
		p6.setLayout(new FlowLayout(FlowLayout.LEFT, 29, 0));

		p7 = new JPanel(); //pw 재입력 요소 붙여넣을 패널
		basePanel.add(p7);
		p7.setBackground(Color.WHITE);
		p7.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 0));

		//로고 이미지 붙여넣기
		ImageIcon imgs = new ImageIcon("Images/logo.png");
		Image img = imgs.getImage();
		Image chimg = img.getScaledInstance(130, 60, Image.SCALE_SMOOTH);
		ImageIcon chicon = new ImageIcon(chimg);
		JLabel logo = new JLabel(chicon);
		logoPanel.add(logo);

		JLabel lblName = new JLabel("name : ");
		lblName.setHorizontalAlignment(lblName.CENTER);
		JLabel lblId = new JLabel("id : ");
		lblId.setHorizontalAlignment(lblName.CENTER);
		
		btnId = new JButton("Confirm"); //id 중복확인 버튼
		btnId.setBackground(new Color(1, 92, 173));
		btnId.setForeground(Color.WHITE);
		JLabel lblPw = new JLabel("pw : ");
		lblPw.setHorizontalAlignment(lblName.CENTER);
		JLabel lblrePw = new JLabel("rePw : ");
		lblrePw.setHorizontalAlignment(lblName.CENTER);

		name = new JTextField(10);
		id = new JTextField(10);
		pw = new JPasswordField(15);
		rePw = new JPasswordField(15);

		p4.add(lblName);
		p4.add(name);

		p5.add(lblId);
		p5.add(id);
		p5.add(btnId);

		p6.add(lblPw);
		p6.add(pw);

		p7.add(lblrePw);
		p7.add(rePw);

		btnOK = new JButton("Join"); //회원가입 버튼 
		btnDelete = new JButton("Cancel"); //취소 버튼
		btnOK.setBackground(new Color(1, 92, 173));
		btnOK.setForeground(Color.WHITE);
		btnDelete.setBackground(new Color(1, 92, 173));
		btnDelete.setForeground(Color.WHITE);

		btnPanel.add(btnOK);
		btnPanel.add(btnDelete);

		btnId.addActionListener(this);
		btnOK.addActionListener(this);
		btnDelete.addActionListener(this);

		setVisible(true);

	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new Join("회원가입");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnOK) { //ok버튼 이벤트
			//입력하지 않은 요소가 있다면
			if (name.getText().equals("") || id.getText().equals("") || pw.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "정보를 빠짐없이 입력해주세요!");
			} else if (!rePw.getText().equals(pw.getText())) { //재입력 패스워드와 패스워드가 다를 때
				JOptionPane.showMessageDialog(null, "입력된 패스워드가 같지 않습니다!");
				pw.setText("");
				rePw.setText("");
			} else if (pw.getText().length() > 12 || pw.getText().length() < 6) { //패스워드의 길이 조건
				JOptionPane.showMessageDialog(null, "비밀번호를 6자리이상 12자리이하로 설정해주세요!");
				pw.setText("");
				rePw.setText("");
			} else {
				if (count != 1) { //id중복확인을 하지 않은 경우(이미 있는 id는 count=1이므로)
					JOptionPane.showMessageDialog(null, "아이디 중복확인을 해주세요.");
				} else { // 회원가입 실행
					String names = name.getText();
					String cuId = id.getText();
					String cuPw = pw.getText();

					String sql = "INSERT INTO CUSTOMER VALUES" + "('" + names + "', '" + cuId + "', '" + cuPw + "')";

					db.DB_Lib.executeQuery(sql);

					JOptionPane.showMessageDialog(null, "회원가입 완료!");
					name.setText("");
					id.setText("");
					pw.setText("");
					rePw.setText("");
					dispose(); //창 닫힘
				}
			}
		} else if (obj == btnDelete) { //취소 버튼
			dispose(); //창 닫힘
		} else if (obj == btnId) { //id 중복확인 버튼
			String checkId = id.getText();
			check = overlap(checkId); //중복 확인
			if (check == true) { //중복된 아이디가 있을 경우
				JOptionPane.showMessageDialog(null, "중복된 아이디가 있으므로 다시 입력해주세요.", "Message",
						JOptionPane.INFORMATION_MESSAGE);
				id.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다!");
				count = 1;
			}
		}
	}

	private boolean overlap(String id) { //id 중복확인 메소드
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