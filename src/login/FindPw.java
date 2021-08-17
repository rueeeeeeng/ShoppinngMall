package login;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class FindPw extends JFrame implements ActionListener {
	private JPanel p1, p2;
	private JLabel lblID, lblemail;
	private JTextField tfID;
	private JTextField tfEmail;
	private JButton btnOK, btnCancel;
	private String get;

	public FindPw(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
//		setLocation(800, 300);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 레이아웃
		setLayout(new BorderLayout());
		p1 = new JPanel(new GridLayout(2, 2, 10, 10));
		p1.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 40)); // 빈공간의 크기 설정
		p1.setBackground(Color.WHITE);
		lblID = new JLabel("ID : ");
		lblID.setHorizontalAlignment(JLabel.CENTER); // 가운데정렬
		lblemail = new JLabel("Email : ");
		lblemail.setHorizontalAlignment(JLabel.CENTER);

		tfID = new JTextField(20);
//		tfID.setHorizontalAlignment(JTextField.RIGHT); //오른쪽정렬
		tfEmail = new JTextField(20);
//		tfPW.setHorizontalAlignment(JTextField.LEFT);

		p1.add(lblID);
		p1.add(tfID);
		p1.add(lblemail);
		p1.add(tfEmail);

		p2 = new JPanel();
		btnOK = new JButton("OK");
		btnOK.setBackground(Color.WHITE);
		btnOK.addActionListener(this);
		btnCancel = new JButton("Cancel");
		btnCancel.setBackground(Color.WHITE);
		btnCancel.addActionListener(this);
		p2.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		p2.setBackground(Color.WHITE);
		p2.add(btnOK);
		p2.add(btnCancel);

		add(p1, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);

		setVisible(true);
	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new FindPw("pw 찾기", 350, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnOK) {
			if (tfID.getText().equals("") || tfEmail.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "아이디와 이메일을 입력하세요", "메시지", JOptionPane.WARNING_MESSAGE);
				
				tfID.setText("");
				tfEmail.setText("");
			} else {
				String id = tfID.getText();
				String email = tfEmail.getText();

				boolean check = SearchPW(id, email);
				if (check) {
					JOptionPane.showMessageDialog(null, "비밀번호는 " + get + "입니다.");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "비밀번호가 존재하지 않습니다.", "메시지", JOptionPane.WARNING_MESSAGE);
					tfID.setText("");
					tfEmail.setText("");
				}
			}

		} else if (obj == btnCancel) {
			dispose();
		}
	}

	private boolean SearchPW(String id, String email) {
		boolean check = false;
		String sql = "SELECT PW FROM CUSTOMER WHERE ID ='" + id + "' AND EMAIL = '" + email + "'";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			if (rs.next()) {
				check = true;
				get = rs.getString("PW");

			} else {
				check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

}