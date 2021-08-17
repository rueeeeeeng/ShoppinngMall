package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import db.DB_Lib;
import util.ButtonUtil;
import util.Frames;

public class Start extends Frames implements ActionListener {

	private JButton btnManagerLogin;
	private JButton btnUserLogin;
	private JPanel p1, p2;
	private ButtonUtil btnStart;

	public Start(String title, int width, int height) {
		setTitle(title); // 제목
		setSize(width, height); // 너비, 높이
		setLocationRelativeTo(this);
//		setResizable(false);
//      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램이 끝나면 닫고 종료한다

		// 레이아웃
		setLayout(new BorderLayout());

		p1 = new JPanel();
		p1.setBackground(Color.WHITE);

		ImageIcon imgs = new ImageIcon("Images/쇼핑몰로고.png");
		JLabel lbl = new JLabel(imgs);
		p1.add(lbl);

		p2 = new JPanel();
		p2.setBackground(Color.WHITE);
		p2.setLocation(25, 30);
/*
		btnManagerLogin = new JButton("Manager");
//      btnManagerLogin.setBackground(Color.WHITE);
		btnManagerLogin.addActionListener(this);
		btnManagerLogin.setBorder(new LineBorder(Color.WHITE));
		btnManagerLogin.setPreferredSize(new Dimension(70, 35));


		btnUserLogin = new JButton("Customer");
//      btnUserLogin.setBackground(Color.WHITE);
		btnUserLogin.setBorder(new LineBorder(Color.WHITE));
		btnUserLogin.addActionListener(this);
		btnUserLogin.setPreferredSize(new Dimension(70, 35));

		p2.add(btnManagerLogin);
		p2.add(btnUserLogin);
*/
		
		btnStart = new ButtonUtil("START");
		btnStart.addActionListener(this);
		btnStart.setBorder(new LineBorder(Color.WHITE));
		p2.add(btnStart);
		add(p1, BorderLayout.NORTH);
		add(p2);

		setVisible(true);

	}

	public static void main(String args[]) {
		db.DB_Lib.init();
		new Start("", 350, 200);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==btnStart) {
			dispose();
			new Login("고객 로그인", 400, 230);
		}
	}
}