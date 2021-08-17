package pay;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import db.DB_Lib;

public class Payment extends JFrame implements ActionListener {
	private JLabel lbPrice, lb1, lb2, lbDid;
	private JPanel p1, p2, p3, p4, p5, p6, p7;
	private JButton b1, b2, b3;
	private String[] cart = { "상품코드", "제품명", "가격" };
	private DefaultTableModel cusCart = new DefaultTableModel(cart, 0);
	private String[] show = { "종강기념 행사 전품목 10% 할인쿠폰" };
	private int sum, lastTotal = 0;
	private String price;
	private JTable c;
	private JComboBox<String> discount;
	private int money, n;
	private String account, cName, reAccount, code;
	private boolean check;
	private int p;

	public Payment() {
		setTitle("사용자 결제창");
		setSize(350, 450);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		setLayout(new BorderLayout());

		p1 = new JPanel();
		p1.setBackground(Color.white);
		add(p1, BorderLayout.NORTH);
		ImageIcon imgs = new ImageIcon("images/logo.png");
		Image img = imgs.getImage();
		Image chimg = img.getScaledInstance(130, 60, Image.SCALE_SMOOTH);
		ImageIcon chicon = new ImageIcon(chimg);
		JLabel logo = new JLabel(chicon);
		p1.add(logo);

		p2 = new JPanel();
		p2.setBackground(Color.white);
		add(p2, BorderLayout.CENTER);

		p3 = new JPanel(new GridLayout(2, 1));
		p3.setBackground(Color.white);
		add(p3, BorderLayout.SOUTH);
		p3.setBackground(Color.white);

		p4 = new JPanel();
		p4.setBackground(Color.WHITE);
		p3.add(p4);

		p5 = new JPanel();
		p5.setBackground(Color.WHITE);
		p3.add(p5);

		p6 = new JPanel();
		p7 = new JPanel();

		p6.setBackground(Color.WHITE);
		p7.setBackground(Color.WHITE);

		p2.setLayout(new GridLayout(2, 1));
		p2.add(p6);
		p2.add(p7);

		TotalPrice();
		customerPay();
		EventFrame();

		lb1 = new JLabel("Total : ");
		lbPrice = new JLabel(Integer.toString(sum) + "원 + 배송비 : 0원 -> " + sum + "원");

		b1 = new JButton("결제");
		b2 = new JButton("닫기");

		b1.setBackground(Color.WHITE);

		b2.setBackground(Color.WHITE);

		p4.add(lb1);
		p4.add(lbPrice);
		p5.add(b1);
		p5.add(b2);

		b1.addActionListener(this);
		b2.addActionListener(this);

		setVisible(true);

	}

	private void EventFrame() {
		lb1 = new JLabel("쿠폰 : ");
		p7.add(lb1);

		p6.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 2), "Payment Product"));

		discount = new JComboBox<String>(show);
		p7.add(discount);
		discount.addActionListener(this);

		p7.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 2), "Discount!"));

		lb2 = new JLabel("50000원 이상 구매 시 무료배송!");
		p7.add(lb2);

		lbDid = new JLabel("");
		p7.add(lbDid);

	}

	private void TotalPrice() {
		String sql = "SELECT PRICE FROM CART";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				price = rs.getString("PRICE");
				p = Integer.parseInt(price);
				sum += p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void customerPay() {
		c = new JTable(cusCart);
		String sql = "SELECT * FROM CART";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				String code = rs.getString("CODE");
				String name = rs.getString("NAME");
				int price = rs.getInt("PRICE");
				Object[] data = { code, name, price };
				cusCart.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p6.add(c);

	}

	public static void main(String[] args) {
		DB_Lib.init();
		new Payment();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		int index = discount.getSelectedIndex();
		if (obj == b1) {
			FindInfo();
			FindAccount();
			BankBalance();
			int i = JOptionPane.showConfirmDialog(null,
					"계좌 : " + cName + " " + account + "\n고객님의 잔고는 현재 " + money + "원입니다.\n결제하시겠습니까?", "Message",
					JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.YES_OPTION) {
				if (money < lastTotal) {
					JOptionPane.showMessageDialog(null, "잔고가 부족합니다!", "Message", JOptionPane.INFORMATION_MESSAGE);
				} else {
					n = money - lastTotal;
					MinusMoney();
					JOptionPane.showMessageDialog(null, "결제가 완료되었습니다!\n현재 고객님의 잔고는 " + Integer.toString(n) + "원입니다.");
					keyFind();
					moFind();
					mosFind();
					noFine();
					DeleteCart();

				}
			}
		} else if (obj == b2) {
			dispose();
		} else if (index == 0) {
			DisTotal();
		}
	}

	private void noFine() {
		String sql = "SELECT BESTSELLER,CODE FROM NOTEBOOK WHERE CODE IN (SELECT CODE FROM CART)";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			if (rs.next()) {
				String c = rs.getString("CODE");
				int best = rs.getInt("BESTSELLER");
				int s = best + 1;
				String sql2 = "UPDATE NOTEBOOK SET BESTSELLER = '" + s + "' WHERE CODE ='" + c + "'";
				db.DB_Lib.executeQuery(sql2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void mosFind() {
		String sql = "SELECT BESTSELLER,CODE FROM MOUSE WHERE CODE IN (SELECT CODE FROM CART)";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			if (rs.next()) {
				String c = rs.getString("CODE");
				int best = rs.getInt("BESTSELLER");
				int s = best + 1;
				String sql2 = "UPDATE MOUSE SET BESTSELLER = '" + s + "' WHERE CODE = '" + c + "'";
				db.DB_Lib.executeQuery(sql2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void moFind() {
		String sql = "SELECT BESTSELLER, CODE FROM MONITOR WHERE CODE IN (SELECT CODE FROM CART)";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			if (rs.next()) {
				String c = rs.getString("CODE");
				int best = rs.getInt("BESTSELLER");
				int s = best + 1;
				String sql2 = "UPDATE MONITOR SET BESTSELLER = '" + s + "' WHERE CODE = '" + c + "'";
				db.DB_Lib.executeQuery(sql2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void keyFind() {
		String sql = "SELECT BESTSELLER, CODE FROM KEYBOARD WHERE CODE IN (SELECT CODE FROM CART)";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			if (rs.next()) {
				String c = rs.getString("CODE");
				int best = rs.getInt("BESTSELLER");
				int s = best + 1;
				String sql2 = "UPDATE KEYBOARD SET BESTSELLER = '" + s + "' WHERE CODE = '" + c + "'";
				db.DB_Lib.executeQuery(sql2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void DeleteCart() {
		String sql = "DELETE FROM CART";
		db.DB_Lib.executeQuery(sql);

	}

	private void MinusMoney() {
		String sql = "UPDATE ACCOUNT_INFO SET MONEY = '" + n + "'" + "WHERE ACCOUNT = '" + account + "'";
		db.DB_Lib.executeQuery(sql);
	}

	private void FindInfo() {
		String sql = "SELECT ACCOUNT FROM CUSTOMER WHERE ID = (SELECT ID FROM CONNECTUSER)";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				account = rs.getString("ACCOUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void FindAccount() {
		String sql = "SELECT CNAME, ACCOUNT FROM ACCOUNT_INFO WHERE ACCOUNT = '" + account + "'";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				cName = rs.getString("CNAME");
				reAccount = rs.getString("ACCOUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void BankBalance() {
		String sql = "SELECT MONEY FROM ACCOUNT_INFO WHERE ACCOUNT = '" + account + "'";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				money = rs.getInt("MONEY");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void DisTotal() {
		lastTotal = (sum - sum * 1 / 10);
		lbDid.setText("할인적용가  " + sum + "원 -> " + lastTotal + "원");
		if (sum < 50000) {
			lbPrice.setText(lastTotal + "원 + 배송비 : 3000원 -> " + lastTotal + 3000);
		} else {
			lbPrice.setText(lastTotal + "원 : 배송비 : 0원 -> " + lastTotal);
		}
	}

}