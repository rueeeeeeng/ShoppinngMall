package manager;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
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

public class NotebookAddAndDelete extends JFrame implements ActionListener {
	private JPanel p1, p2, p3;
	private JButton addBtn, canBtn, delBtn, reBtn;
	private String[] noteName = { "상품코드", "제품명", "크기", "무게", "램", "배터리", "가격", "운영체제", "제조사", "총 판매수" };
	private DefaultTableModel m1 = new DefaultTableModel(noteName, 0);
	private JLabel lb;
	private JTable mTable;
	private JScrollPane sp;
	private boolean check;

	public NotebookAddAndDelete() {
		setTitle("노트북 추가/삭제");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setResizable(false);

		p1 = new JPanel();
		add(p1, new BorderLayout().NORTH);

		NotebookList();

		p3 = new JPanel();
		add(p3, new BorderLayout().SOUTH);
		addBtn = new JButton("추가");
		addBtn.setBackground(Color.WHITE);

		delBtn = new JButton("삭제");
		delBtn.setBackground(Color.WHITE);

		canBtn = new JButton("취소");
		canBtn.setBackground(Color.WHITE);

		reBtn = new JButton("새로고침");
		reBtn.setBackground(Color.WHITE);

		p3.add(reBtn);
		p3.add(addBtn);
		p3.add(delBtn);
		p3.add(canBtn);

		addBtn.addActionListener(this);
		canBtn.addActionListener(this);
		delBtn.addActionListener(this);
		reBtn.addActionListener(this);

		p1.setBackground(Color.WHITE);
		p2.setBackground(Color.WHITE);
		p3.setBackground(Color.WHITE);

		setVisible(true);
	}

	private void NotebookList() {
		p2 = new JPanel();
		add(p2, BorderLayout.CENTER);

		mTable = new JTable(m1);
		JTableHeader jh = mTable.getTableHeader();
		jh.setBackground(new Color(1, 92, 173));
		jh.setForeground(new Color(255, 255, 255));

		sp = new JScrollPane(mTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(500, 250));
		p2.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 2), "   상품 추가 / 삭제   :   노트북  "));
		p2.add(sp);
		String sql = "SELECT * FROM NOTEBOOK";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				try {
					String code = rs.getString("CODE");
					String name = rs.getString("NAME");
					String inch = rs.getString("INCH");
					String weight = rs.getString("WEIGHT");
					String ram = rs.getString("RAM");
					String battery = rs.getString("BATTERY");
					String price = rs.getString("PRICE");
					String operate = rs.getString("OPERATE");
					String brand = rs.getString("BRAND");
					int bestSeller = rs.getInt("BESTSELLER");

					Object[] data = { code, name, inch, weight, ram, battery, price, operate, brand, bestSeller };
					m1.addRow(data);

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new NotebookAddAndDelete();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == addBtn) {
			new NoteAdd("상품추가 창", 280,360);
		} else if (obj == canBtn) {
			dispose();
		} else if (obj == delBtn) {
			String del = JOptionPane.showInputDialog("지우고 싶은 상품코드 입력");
			if (del.equals("")) {
				JOptionPane.showMessageDialog(null, "상품코드를 입력하세요!");
			} else {
				check = chekCode(del);
				if (check) {
					String delSql = "DELETE FROM NOTEBOOK WHERE CODE='" + del + "'";
					db.DB_Lib.executeQuery(delSql);
					JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "없는 상품코드입니다.");
				}
			}
		} else if (obj == reBtn) {
			refreshZone();
		}
	}

	private void refreshZone() {
		for (int i = 0; i < mTable.getRowCount();) {
			m1.removeRow(i);
		}
		String sql = "SELECT * FROM NOTEBOOK";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				String code = rs.getString(1);
				String inch = rs.getString(2);
				String weight = rs.getString(3);
				String ram = rs.getString(4);
				String battery = rs.getString(5);
				String price = rs.getString(6);
				String operate = rs.getString(7);
				String brand = rs.getString(8);
				int bestSeller = rs.getInt(9);
				String name = rs.getString(10);
				Object data[] = { code, name, inch, weight, ram, battery, price, operate, brand, bestSeller };
				m1.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private boolean chekCode(String del) {
		boolean check = false;
		String sql = "SELECT CODE FROM NOTEBOOK WHERE CODE = '" + del + "'";
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