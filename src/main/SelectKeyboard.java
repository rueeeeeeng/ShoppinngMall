package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import pay.Cart;

public class SelectKeyboard extends JFrame implements ActionListener, KeyListener {
	private JPanel p1, p2, p3;
	private JTextField tfSearch;
	private JButton btnBack, btnSearch, btnCart, btnRefresh, btnGoCart;
	private DefaultTableModel tableModel;
	private boolean inputCheck;
	private String UpdateSql;
	private JTable table;
	private String lib_code;
	private String lib_name;
	private String lib_price;
	private boolean checkAddCart;

	public SelectKeyboard(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
//		setUndecorated(true); //타이틀바 날리기

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.WHITE);
		JPanel logoPanel = new JPanel();
		logoPanel.setBackground(Color.WHITE);

//		logoPanel.setBackground(new Color(1, 92, 173));
		ImageIcon imgs = new ImageIcon("Images/쇼핑몰로고.png");
		JLabel logo = new JLabel(imgs);
		logoPanel.add(logo);

		p1 = new JPanel();
//		p1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		p1.setBackground(Color.white);
		JLabel lblSearch = new JLabel("제조사를 입력하세요 :");
		p1.add(lblSearch);
		tfSearch = new JTextField(20);
		tfSearch.addKeyListener(this);
		p1.add(tfSearch);

		btnSearch = new JButton("Search"); // 검색 기능
		btnSearch.setHorizontalAlignment(SwingConstants.CENTER);
		btnSearch.setBackground(Color.WHITE);
		btnSearch.setPreferredSize(new Dimension(80, 30));
		btnSearch.setFocusPainted(false);
		btnSearch.setBorder(new LineBorder(Color.WHITE));
		btnSearch.addActionListener(this);
		p1.add(btnSearch);

		btnRefresh = new JButton("Refresh"); // 처음 제품들이 모두 있는 표를 보여줌(추가된 기능)
		btnRefresh.setHorizontalAlignment(SwingConstants.CENTER);
		btnRefresh.setBackground(Color.WHITE);
		btnRefresh.setPreferredSize(new Dimension(80, 30));
		btnRefresh.setFocusPainted(false);
		btnRefresh.setBorder(new LineBorder(Color.WHITE));
		btnRefresh.addActionListener(this);
		p1.add(btnRefresh);

		btnGoCart = new JButton("Cart"); // 장바구니 바로가기(추가된 기능)
		btnGoCart.setHorizontalAlignment(SwingConstants.CENTER);
		btnGoCart.setBackground(Color.WHITE);
		btnGoCart.setPreferredSize(new Dimension(80, 30));
		btnGoCart.setFocusPainted(false);
		btnGoCart.setBorder(new LineBorder(Color.WHITE));
		btnGoCart.addActionListener(this);
		p1.add(btnGoCart);

		p2 = new JPanel();
		p2.setBackground(Color.WHITE);
		p2.setBorder(new TitledBorder(new LineBorder(Color.GRAY), "키보드")); // p2에 테두리와 타이틀 생성
		String header[] = { "제품코드", "연결방식", "키배열", "제품크기", "제품무게", "가격", "브랜드" };
		tableModel = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int i, int c) { // 테이블 수정 불가
				return false;
			}
		};
		

		table = new JTable(tableModel);
		table.setBackground(Color.WHITE);
//		table.getColumn("운영체제").setPreferredWidth(130);
//		table.getColumn("제조사").setPreferredWidth(60);
		table.getTableHeader().setReorderingAllowed(false); // 테이블 열의 이동 금지
		table.getTableHeader().setResizingAllowed(false); // 테이블 사이즈 고정
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 오직 한개의 row만 선택 가능
		table.setAutoCreateRowSorter(true); // 헤더 클릭했을때 자동정렬


		String sql = "SELECT*FROM KEYBOARD";
		ResultSet rs = db.DB_Lib.getResultSet(sql);

		DrawTable(rs);
		JTableHeader theader = table.getTableHeader();
		theader.setBackground(new Color(1, 92, 173)); // 헤더 색상변경
		theader.setForeground(Color.WHITE); // 헤더 글자색 변경
		theader.setFont(new Font("Dialog", Font.BOLD, 11)); // 헤더 글꼴 변경

		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sc.setPreferredSize(new Dimension(600, 260)); // jtable 크기 조정

		p2.add(sc);

		p3 = new JPanel();
		p3.setBackground(Color.WHITE);
		btnBack = new JButton("뒤로가기");
		btnBack.setBackground(Color.WHITE);
		btnBack.addActionListener(this);
		p3.add(btnBack);

		btnCart = new JButton("장바구니에 담기");
		btnCart.setBackground(Color.white);
		btnCart.addActionListener(this);
		p3.add(btnCart);

		add(logoPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(p1, BorderLayout.NORTH);
		mainPanel.add(p2);
		mainPanel.add(p3, BorderLayout.SOUTH);
		setVisible(true);
	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new SelectKeyboard("키보드", 650, 550);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnBack) { // 뒤로가기
			dispose();
		} else if (obj == btnSearch) { // 검색버튼, 검색결과에 맞는 제품들만 새로 그리기
			Search();
		} else if (obj == btnRefresh) { // 처음으로
			for (int i = 0; i < tableModel.getRowCount();) {
				tableModel.removeRow(i);
			}
			String sql = "SELECT*FROM KEYBOARD";
			ResultSet rs = db.DB_Lib.getResultSet(sql);
			DrawTable(rs);
		} else if (obj == btnCart) { // 장바구니 버튼
			boolean check = AddCart();
			if(check) {
				JOptionPane.showMessageDialog(null, "장바구니에 등록되었습니다.", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			else { //아무것도 선택하지 않고 장바구니 버튼을 눌렀을 때
				JOptionPane.showMessageDialog(null, "선택되지 않았습니다.", "Message", JOptionPane.WARNING_MESSAGE);
			}
		} else if (obj == btnGoCart) {
			db.DB_Lib.init();
			new Cart();
		}
	}

	private void Search() { // 검색 메소드
		if (tfSearch.getText().equals("")) { // 검색어를 입력하지 않았을 때
			JOptionPane.showMessageDialog(null, "검색어를 입력해주세요", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else { // 검색어를 입력했을때
			String brand = tfSearch.getText().toUpperCase();
//			SearchPW(brand);

			String sql = "SELECT * FROM KEYBOARD WHERE BRAND ='" + brand + "'";
			ResultSet rs = db.DB_Lib.getResultSet(sql);

			boolean check = inputCheck(rs); // 검색한 브랜드가 DB에 존재하는지 확인하는 메소드
			if (check) {
				for (int i = 0; i < tableModel.getRowCount();) {
					tableModel.removeRow(i);
				}
				DrawTable(rs);
				
				tfSearch.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "존재하지 않는 브랜드입니다.", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private boolean inputCheck(ResultSet rs) { // 검색한 브랜드가 DB에 존재하는지 확인하는 메소드
		try {
			if (rs.next()) {
				inputCheck = true;
			} else {
				inputCheck = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inputCheck;
	}

	private void DrawTable(ResultSet rs) { // 테이블 그리는 메소드
		try {
			while (rs.next()) { // DB이용해 테이블 생성
				String lib_code = rs.getString(1);
				String lib_connectWay = rs.getString(2);
				String lib_K_count = rs.getString(3);
				String lib_P_size = rs.getString(4);
				String lib_weight = rs.getString(5);//
				String lib_price = rs.getString(6) +"원";//
				String lib_brand = rs.getString(7);//

				Object data[] = { lib_code, lib_connectWay, lib_K_count, lib_P_size, lib_weight, lib_price, lib_brand };
				tableModel.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Boolean AddCart() {
		checkAddCart = false;
		String sql = "SELECT CODE, PRICE, NAME FROM KEYBOARD";
		ResultSet rs = db.DB_Lib.getResultSet(sql);
		try {
			if (table.isRowSelected(table.getSelectedRow())) {
				while (rs.next()) {
					if (rs.getRow() == table.getSelectedRow() + 1) {
						lib_code = rs.getString("CODE");
						lib_name = rs.getString("NAME");
						lib_price = rs.getString("PRICE");
					}
				}
				// 장바구니에 넣기
				UpdateSql = "INSERT INTO CART VALUES" + "('" + lib_code + "', '" + lib_name + "', " + lib_price + ")";
				db.DB_Lib.executeQuery(UpdateSql);
				checkAddCart = true;
			} else
				checkAddCart = false;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("에러 발생");
		}
		return checkAddCart;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) { // 엔터 눌렀을때도 검색될 수 있도록 함
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			Search();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}