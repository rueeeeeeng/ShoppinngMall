package manager;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import db.DB_Lib;

public class MonitorAddAndDelete extends JFrame implements ActionListener {
	private JPanel p1, p2, p3;
	private JButton btnAdd, btnCancel, btnDel, btnRe;
	private JLabel lbAdd;
	private String[] product = { "모니터", "마우스", "키보드", "노트북" };
	private String[] monitorName = { "상품코드", "제조사", "모델명", "해상도", "무게", "가격", "총 판매수" };
	private DefaultTableModel m1 = new DefaultTableModel(monitorName, 0);
	private JTable mTable;
	private boolean check;
	private JScrollPane sp;

	public MonitorAddAndDelete(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setResizable(false);

		ProductList();

		MonitorList();

		p3 = new JPanel();
		add(p3, BorderLayout.SOUTH);

		btnRe = new JButton("새로고침");
		p3.add(btnRe);
		btnRe.setBackground(Color.WHITE);
		btnRe.addActionListener(this);

		btnAdd = new JButton("추가");
		p3.add(btnAdd);
		btnAdd.setBackground(Color.WHITE);
		btnAdd.addActionListener(this);

		btnDel = new JButton("삭제");
		p3.add(btnDel);
		btnDel.setBackground(Color.WHITE);
		btnDel.addActionListener(this);

		btnCancel = new JButton("취소");
		p3.add(btnCancel);
		btnCancel.setBackground(Color.WHITE);
		btnCancel.addActionListener(this);

		p1.setBackground(Color.WHITE);
		p2.setBackground(Color.WHITE);
		p3.setBackground(Color.WHITE);

		setVisible(true);
	}

	private void MonitorList() {
		p2 = new JPanel();
		add(p2, BorderLayout.CENTER);

		mTable = new JTable(m1);

		JTableHeader jh = mTable.getTableHeader();
		jh.setBackground(new Color(1, 92, 173));
		jh.setForeground(new Color(255, 255, 255));

		sp = new JScrollPane(mTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(500, 250));
		p2.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 2), "   상품 추가 / 삭제   :   모니터  "));
		p2.add(sp);
		String sql = "SELECT * FROM MONITOR";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				try {
					String code = rs.getString("CODE");
					String brand = rs.getString("BRAND");
					String mName = rs.getString("NAME");
					String resolution = rs.getString("RESOLUTION");
					String weight = rs.getString("WEIGHT");
					String price = rs.getString("PRICE");
					String bestSeller = rs.getString("BESTSELLER");

					Object[] data = { code, brand, mName, resolution, weight, price, bestSeller };
					m1.addRow(data);

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void ProductList() {
		p1 = new JPanel();
		add(p1, BorderLayout.NORTH);

	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new MonitorAddAndDelete("모니터 추가", 600, 400);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnAdd) {
			new MonitorAdd("상품추가 입력창", 270, 290);
		} else if (obj == btnCancel) {
			dispose();
		} else if (obj == btnDel) {
			String del = JOptionPane.showInputDialog("지우고 싶은 상품코드 입력");
			if (del.equals("")) {
				JOptionPane.showMessageDialog(null, "상품코드를 입력하세요!");
			} else {
				check = chekCode(del);
				if (check) {
					String delSql = "DELETE FROM MONITOR WHERE CODE='" + del + "'";
					db.DB_Lib.executeQuery(delSql);
					JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "없는 상품코드입니다.");
				}
			}
		} else if (obj == btnRe) {
			refreshZone();
		}
	}

	private void refreshZone() {
		for (int i = 0; i < mTable.getRowCount();) {
			m1.removeRow(i);
		}
		String sql = "SELECT*FROM MONITOR";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				String mcode = rs.getString(1);
				String brand = rs.getString(2);
				String mname = rs.getString(3);
				String resolution = rs.getString(4);
				String weight = rs.getString(5);
				String price = rs.getString(6);
				int bestSeller = rs.getInt(7);
				Object data[] = { mcode, brand, mname, resolution, weight, price, bestSeller };
				m1.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private boolean chekCode(String del) {
		boolean check = false;
		String sql = "SELECT CODE FROM MONITOR WHERE CODE = '" + del + "'";
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