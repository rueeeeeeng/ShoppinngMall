package manager;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import db.DB_Lib;

public class KeyboardAddAndDelete extends JFrame implements ActionListener, MouseListener, KeyListener {
	private JPanel p1, p2, p3;
	private JButton addBtn, canBtn, delBtn, newBtn, btnSearch;
	private String[] KeyboardName = { "상품코드", "제품명", "제조사", "키 개수", "사이즈", "무게", "가격", "브랜드", "총 판매수" };
	private DefaultTableModel m1 = new DefaultTableModel(KeyboardName, 0) {
		public boolean isCellEditable(int i, int c) {
			return false;
		}
	};
	private JLabel lb, lb2;
	private JTable mTable;
	private String get;
	private JScrollPane sp;
	private boolean check;
	private JTextField tf1;
	private String[] s;
	private String sel;

	public KeyboardAddAndDelete() {
		setTitle("키보드 추가/삭제");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setResizable(false);

		p1 = new JPanel();
		add(p1, new BorderLayout().NORTH);

		lb2 = new JLabel("상품코드 검색 : ");
		tf1 = new JTextField(20);
		btnSearch = new JButton("검색");
		btnSearch.setBackground(Color.WHITE);

		p1.add(lb2);
		p1.add(tf1);
		p1.add(btnSearch);

		tf1.addKeyListener(this);

		btnSearch.addActionListener(this);

		// c = getContentPane();

		KeyboardList();

		p3 = new JPanel();
		add(p3, new BorderLayout().SOUTH);
		newBtn = new JButton("새로고침");
		newBtn.setBackground(Color.WHITE);
		addBtn = new JButton("추가");
		addBtn.setBackground(Color.WHITE);
		// delBtn = new JButton("삭제");
		// delBtn.setBackground(Color.WHITE);
		canBtn = new JButton("취소");
		canBtn.setBackground(Color.WHITE);
		p3.add(newBtn);
		p3.add(addBtn);
		p3.add(canBtn);
		addBtn.addActionListener(this);
		canBtn.addActionListener(this);
		newBtn.addActionListener(this);

		p1.setBackground(Color.WHITE);
		p2.setBackground(Color.WHITE);
		p3.setBackground(Color.WHITE);

		setVisible(true);
	}

	private void KeyboardList() {
		p2 = new JPanel();
		add(p2, BorderLayout.CENTER);
		p2.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173), 2), "   상품 추가 / 삭제   :   키보드  "));

		mTable = new JTable(m1);

		mTable.getTableHeader().setReorderingAllowed(false);

		JTableHeader jh = mTable.getTableHeader();
		jh.setBackground(new Color(1, 92, 173));
		jh.setForeground(new Color(255, 255, 255));

		sp = new JScrollPane(mTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(500, 250));
		p2.add(sp);

		mTable.addMouseListener(this);
		String sql = "SELECT * FROM KEYBOARD";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				try {
					String code = rs.getString("CODE");
					String name = rs.getString("NAME");
					String connectway = rs.getString("CONNECTWAY");
					String kcount = rs.getString("KCOUNT");
					String psize = rs.getString("PSIZE");
					String weight = rs.getString("WEIGHT");
					String price = rs.getString("PRICE");
					String brand = rs.getString("BRAND");
					String bestSeller = rs.getString("BESTSELLER");

					Object[] data = { code, name, brand, kcount, psize, weight, price, brand, bestSeller };
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
		new KeyboardAddAndDelete();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == addBtn) {
			new KeyAdd("상품추가 입력창", 400, 330);
		} else if (obj == canBtn) {
			dispose();
		} else if (obj == newBtn) {
			refreshZone();

		} else if (obj == btnSearch) {
			for (int i = 0; i < mTable.getRowCount();) {
				m1.removeRow(i);
			}
			String search = tf1.getText();
			String sql = "SELECT * FROM KEYBOARD WHERE CODE LIKE '%" + search + "%'";
			ResultSet rs = db.DB_Lib.getResultSet(sql);
			try {
				while (rs.next()) {
					String kcode = rs.getString(1);
					String name = rs.getString(9);
					String kconnect = rs.getString(2);
					String kcount = rs.getString(3);
					String ksize = rs.getString(4);
					String weight = rs.getString(5);
					String price = rs.getString(6);
					String brand = rs.getString(7);
					int bestSeller = rs.getInt(8);
					Object data[] = { kcode, name, kconnect, kcount, ksize, weight, price, brand, bestSeller };
					m1.addRow(data);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	private void refreshZone() {
		for (int i = 0; i < mTable.getRowCount();) {
			m1.removeRow(i);
		}
		String sql = "SELECT*FROM KEYBOARD";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			while (rs.next()) {
				String kcode = rs.getString(1);
				String name = rs.getString(9);
				String kconnect = rs.getString(2);
				String kcount = rs.getString(3);
				String ksize = rs.getString(4);
				String weight = rs.getString(5);
				String price = rs.getString(6);
				String brand = rs.getString(7);
				int bestSeller = rs.getInt(8);
				Object data[] = { kcode, name, kconnect, kcount, ksize, weight, price, brand, bestSeller };
				m1.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private boolean chekCode(String del) {
		boolean check = false;
		String sql = "SELECT CODE FROM KEYBOARD WHERE CODE = '" + del + "'";
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			String sql = "SELECT CODE FROM KEYBOARD";
			int row = mTable.getSelectedRow();
			int col = mTable.getSelectedColumn();
			ResultSet rs = db.DB_Lib.getResultSet(sql);
			try {
				while (rs.next()) {
					if (rs.getRow() == row + 1) {
						sel = rs.getString("CODE");
					}
				}
				int ans = JOptionPane.showConfirmDialog(null, "선택한 상품을 삭제하시겠습니까?", "Cancel", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if (ans == JOptionPane.YES_OPTION) {
					String sql2 = "DELETE FROM KEYBOARD WHERE CODE = '" + sel + "'";
					db.DB_Lib.executeQuery(sql2);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			for (int i = 0; i < mTable.getRowCount();) {
				m1.removeRow(i);
			}
			String search = tf1.getText();
			String sql = "SELECT * FROM KEYBOARD WHERE CODE LIKE '%" + search + "%'";
			ResultSet rs = db.DB_Lib.getResultSet(sql);
			try {
				while (rs.next()) {
					String kcode = rs.getString(1);
					String name = rs.getString(9);
					String kconnect = rs.getString(2);
					String kcount = rs.getString(3);
					String ksize = rs.getString(4);
					String weight = rs.getString(5);
					String price = rs.getString(6);
					String brand = rs.getString(7);
					int bestSeller = rs.getInt(8);
					Object data[] = { kcode, name, kconnect, kcount, ksize, weight, price, brand, bestSeller };
					m1.addRow(data);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}