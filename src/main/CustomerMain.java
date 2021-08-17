package main;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


import db.DB_Lib;
import event.Event;
import login.Login;

public class CustomerMain extends JFrame implements ActionListener {
	private JPanel p1, p2, p3, p4, p5, p6, p7, p8;
	private JButton btnOut, btnMonitor, btnMouse, btnKey, btnNote, btnEvent, btnCart;
	private JLabel lbName, lbMeet, lbImage, lb1;
	private String[] product = { "모니터", "마우스", "키보드", "노트북" };
	private String[] colName = { "제조사", "모델명", "가격", "총 판매수" };
	private String[] keyName = { "코드", "제조사", "가격", "총 판매수" };
	private String[] mouName = { "제품명", "제조사", "가격", "총 판매수" };
	private String[] noteName = { "코드", "제조사", "가격", "총 판매수" };
	private DefaultTableModel m4 = new DefaultTableModel(noteName, 0);
	private DefaultTableModel m = new DefaultTableModel(colName, 0);
	private DefaultTableModel m2 = new DefaultTableModel(keyName, 0);
	private DefaultTableModel m3 = new DefaultTableModel(mouName, 0);
	private ImageIcon img;
	private JToolBar tool;
	private ImageIcon com, icon;
	private int imageWidth, imagaHeight, count;
	private JTable table, table2, table3, table4;
	private String userName, userId;

	public CustomerMain(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(this);

		CustomerToolBar();
		add(tool, BorderLayout.NORTH);

		p1 = new JPanel();
		add(p1, BorderLayout.CENTER);
		p1.setBackground(Color.WHITE);

		p1.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 2), " Product Category "));

		p8 = new JPanel();
		add(p8, BorderLayout.SOUTH);
		p8.setBackground(Color.WHITE);

		p1.setLayout(new BorderLayout());

		p2 = new JPanel();
		p1.add(p2, BorderLayout.NORTH);
		p2.setBackground(Color.WHITE);

		p3 = new JPanel();
		p1.add(p3, BorderLayout.CENTER);
		p3.setBackground(Color.WHITE);

		p3.setLayout(new GridLayout(4, 1, 15, 15));

		MainProduct();
		MonitorSeller();
		MouserSeller();
		NotebookSeller();
		KeyboardSeller();

		setVisible(true);
	}

	private void KeyboardSeller() {
		table2 = new JTable(m2);
		String sql2 = "SELECT CODE, BRAND, PRICE, BESTSELLER FROM KEYBOARD WHERE BESTSELLER=(SELECT MAX(BESTSELLER) FROM KEYBOARD)";
		ResultSet rs2 = db.DB_Lib.getResultSet(sql2);
		try {
			while (rs2.next()) {
				String code = rs2.getString("CODE");
				String brand = rs2.getString("BRAND");
				String price = rs2.getString("PRICE");
				String bestSeller = rs2.getString("BESTSELLER");

				Object[] data2 = { code, brand, price, bestSeller };
				m2.addRow(data2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p4 = new JPanel();
		p4.add(table2);
		p3.add(p4);
		p4.setBackground(Color.WHITE);
		p4.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 1), " 상품 : 키보드 "));
		// p4.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 40 , 40));

	}

	private void MouserSeller() {
		table3 = new JTable(m3);
		String sql3 = "SELECT NAME, BRAND, PRICE, BESTSELLER FROM MOUSE WHERE BESTSELLER=(SELECT MAX(BESTSELLER) FROM MOUSE)";
		ResultSet rs = db.DB_Lib.getResultSet(sql3);
		try {
			while (rs.next()) {
				String name = rs.getString("NAME");
				String brand = rs.getString("BRAND");
				String price = rs.getString("PRICE");
				String bestSeller = rs.getString("BESTSELLER");
				Object[] data3 = { name, brand, price, bestSeller };
				m3.addRow(data3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p5 = new JPanel();
		p5.add(table3);
		p3.add(p5);
		p5.setBackground(Color.WHITE);
		p5.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 1), " 상품 : 마우스 "));

	}

	private void NotebookSeller() {
		table4 = new JTable(m4);
		String sql4 = "SELECT CODE, BRAND, PRICE, BESTSELLER FROM NOTEBOOK WHERE BESTSELLER=(SELECT MAX(BESTSELLER) FROM NOTEBOOK)";
		ResultSet rs = db.DB_Lib.getResultSet(sql4);
		try {
			while (rs.next()) {
				String code = rs.getString("CODE");
				String brand = rs.getString("BRAND");
				String price = rs.getString("PRICE");
				String bestSeller = rs.getString("BESTSELLER");
				Object[] data4 = { code, brand, price, bestSeller };

				m4.addRow(data4);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p6 = new JPanel();
		p6.add(table4);
		p6.setBackground(Color.WHITE);
		p6.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 1), " 상품 : 노트북 "));
		p3.add(p6);

	}

	private void MonitorSeller() {
		table = new JTable(m);
		String sql1 = "SELECT NAME, BRAND, PRICE, BESTSELLER FROM MONITOR WHERE BESTSELLER=(SELECT MAX(BESTSELLER) FROM MONITOR)";
		ResultSet rs = db.DB_Lib.getResultSet(sql1);
		try {
			while (rs.next()) {
				try {
					String brand = rs.getString("BRAND");
					String name = rs.getString("NAME");
					String price = rs.getString("PRICE");
					String bestSeller = rs.getString("BESTSELLER");
					Object[] data = { name, brand, price, bestSeller };
					m.addRow(data);

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p7 = new JPanel();
		p7.add(table);
		p7.setBackground(Color.WHITE);
		p7.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 1), " 상품 : 모니터 "));
		p3.add(p7);
	}

	/*
	 * private void MainImage() { icon = new ImageIcon("images/logo.png");
	 * 
	 * Image chImg = icon.getImage();
	 * 
	 * Image newImg = chImg.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
	 * ImageIcon sImg = new ImageIcon(newImg);
	 * 
	 * lbImage = new JLabel(sImg); p1.add(lbImage);
	 * 
	 * }
	 */

	private void MainProduct() {
		btnMonitor = new JButton("모니터");
		btnMonitor.setBackground(Color.WHITE);
		btnMonitor.addActionListener(this);
		// btnMonitor.setForeground(new Color(255,255,255));

		btnMouse = new JButton("마우스");
		btnMouse.setBackground(Color.WHITE);
		btnMouse.addActionListener(this);
		// btnMouse.setForeground(new Color(255,255,255));

		btnKey = new JButton("키보드");
		btnKey.setBackground(Color.WHITE);
		btnKey.addActionListener(this);
		// btnKey.setForeground(new Color(255,255,255));

		btnNote = new JButton("노트북");
		btnNote.setBackground(Color.WHITE);
		btnNote.addActionListener(this);
		// btnNote.setForeground(new Color(255,255,255));

		btnEvent = new JButton("이벤트");
		btnEvent.setBackground(Color.WHITE);
		btnEvent.addActionListener(this);
		// btnEvent.setForeground(new Color(255,255,255));

		p2.add(btnMonitor);
		p2.add(btnMouse);
		p2.add(btnKey);
		p2.add(btnNote);
		p2.add(btnEvent);

	}

	private void CustomerToolBar() {
		tool = new JToolBar("고객 툴바");
		tool.setBackground(Color.WHITE);

		ImageIcon imgs2 = new ImageIcon("Images/쇼핑몰로고.png");
		Image img2 = imgs2.getImage();
		Image chimg = img2.getScaledInstance(100, 40, Image.SCALE_SMOOTH);
		ImageIcon chicon = new ImageIcon(chimg);
		JLabel logo = new JLabel(chicon);

		lbName = new JLabel(userName + "님 환영합니다.");
		NameFind();

		img = new ImageIcon("Images/cart.png");
		Image icon = img.getImage();
		Image changeImg = icon.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		ImageIcon newImg = new ImageIcon(changeImg);

		btnCart = new JButton(newImg);
		btnCart.setBackground(Color.WHITE);
		btnCart.addActionListener(this);

		btnOut = new JButton("로그아웃");
		btnOut.setBackground(Color.WHITE);
		// btnOut.setForeground(new Color(255,255,255));
		btnOut.addActionListener(this);

		tool.add(logo);
		tool.add(lbName);
		tool.addSeparator();
		tool.add(btnCart);
		tool.addSeparator();
		tool.add(btnOut);

	}

	private void NameFind() {
		IDfind();
		String sql = "SELECT NAME FROM CUSTOMER WHERE ID='" + userId + "'";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				userName = rs.getString("NAME");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql2 = "SELECT * FROM CONNECTUSER";
		ResultSet rs2 = db.DB_Lib.getResultSet(sql2);
		try {
			while (rs2.next()) {
				count = rs2.getRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (count == 0) {
			lbName.setText("로그인 해주세요.");
		} else {
			lbName.setText(userName + "님 환영합니다.");
		}
	}

	private void IDfind() {
		String sql = "SELECT ID FROM CONNECTUSER";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				userId = rs.getString("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new CustomerMain("메인", 500, 400);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnCart) {
			new pay.Cart();
		} else if (obj == btnOut) {
			if (lbName.getText().equals("로그인 해주세요.")) {
				JOptionPane.showMessageDialog(null, "로그인 해주세요!");
			} else {
				String sql = "DELETE FROM CONNECTUSER";
				db.DB_Lib.executeQuery(sql);
				JOptionPane.showMessageDialog(null, "로그아웃 되었습니다!");
				lbName.setText("로그인 해주세요.");
				dispose();
				new Login("고객 로그인", 350, 230);
			}

		} else if (obj == btnNote) { // 노트북 상품창
			new SelectNotebook("노트북", 650, 550);
		} else if (obj == btnMouse) { // 마우스 상품창
			new SelectMouse("마우스", 650, 550);
		} else if (obj == btnMonitor) { // 모니터 상품창
			new SelectMonitor("모니터", 650, 550);
		} else if (obj == btnKey) { // 키보드 상품창
			new SelectKeyboard("키보드", 650, 550);
		} else if (obj == btnEvent) {
			new Event("이벤트창", 620, 680);
		}
	}

}