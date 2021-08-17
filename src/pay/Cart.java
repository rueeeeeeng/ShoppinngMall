package pay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Cart extends JFrame implements ActionListener, MouseListener {
	private JLabel lblSum;
	private int sum = 0;
	private DefaultTableModel tableModel;
	private JTable table;
	private JButton btnBack, btnPay;
	private String UpdateSql;
	private String sqlCart;

	public Cart() {
		setTitle("Cart");
		setSize(630, 550);
		setLocationRelativeTo(this);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
//		setResizable(false);
//		setUndecorated(true); //타이틀바 날리기

		JPanel logoPanel = new JPanel();
		logoPanel.setBackground(Color.WHITE);
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.WHITE);

		ImageIcon imgs = new ImageIcon("Images/쇼핑몰로고.png");
		JLabel logo = new JLabel(imgs);
		logoPanel.add(logo);

		JPanel p1 = new JPanel();
//		p1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		p1.setBackground(Color.WHITE);
		p1.setBorder(new TitledBorder(new LineBorder(new Color(1, 92, 173)), "Cart")); // p2에 테두리와 타이틀 생성
		String[] header = new String[] { "상품 코드", "상품 이름", "가격" };

		tableModel = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int i, int c) { // 테이블 수정 불가
				return false;
			}
		};

		table = new JTable(tableModel);
		table.addMouseListener(this); //마우스리스너 등록
		
		table.setBackground(Color.WHITE);
		table.getTableHeader().setReorderingAllowed(false); // 테이블 열의 이동 금지
		table.getTableHeader().setResizingAllowed(false); // 테이블 사이즈 고정
//		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 오직 한개의 row만 선택 가능
//		table.setAutoCreateRowSorter(true); // 헤더 클릭했을때 자동정렬 

		sqlCart = "SELECT*FROM CART"; //장바구니 테이블 가져오는 sql
		ResultSet rs = db.DB_Lib.getResultSet(sqlCart);
		DrawTable(rs);

		JTableHeader theader = table.getTableHeader();
		theader.setBackground(new Color(1, 92, 173)); // 헤더 색상변경
		theader.setForeground(Color.WHITE); // 헤더 글자색 변경
		theader.setFont(new Font("Dialog", Font.BOLD, 11)); // 헤더 글꼴 변경

		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sc.setPreferredSize(new Dimension(600, 200)); // jtable 크기 조정
		p1.add(sc);

		JPanel p2 = new JPanel();
		p2.setBackground(Color.WHITE);

		lblSum = new JLabel("");
		Pay();
		
		p2.add(lblSum);

		JPanel p3 = new JPanel();
		p3.setBackground(Color.WHITE);
		btnBack = new JButton("뒤로가기");
		btnBack.setBackground(Color.WHITE);
		btnBack.addActionListener(this);
		btnPay = new JButton("결제하기");
		btnPay.setBackground(Color.WHITE);
		btnPay.addActionListener(this);
		p3.add(btnBack);
		p3.add(btnPay);

		add(logoPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(p1, BorderLayout.NORTH);
		mainPanel.add(p2, BorderLayout.CENTER);
		mainPanel.add(p3, BorderLayout.SOUTH);

		setVisible(true);
	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new Cart();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnBack) {// 뒤로가기 버튼 클릭
			dispose();
		} else if (obj == btnPay) { // 결제하기 버튼 클릭
			new Payment();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) { //더블클릭 시 
			int confirm = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "", JOptionPane.OK_CANCEL_OPTION);
			if (confirm == JOptionPane.OK_OPTION) {
				String sqlCode = "SELECT CODE FROM CART";
				ResultSet rs = db.DB_Lib.getResultSet(sqlCode);
				String code = "";
				try {
					while (rs.next()) {
						if (rs.getRow() == table.getSelectedRow()+1) {
							System.out.println(rs.getRow());
							System.out.println(table.getSelectedRow());
							code = rs.getString("CODE");
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				UpdateSql = "DELETE FROM CART WHERE CODE='" + code + "'";
				db.DB_Lib.executeQuery(UpdateSql);
				
				JOptionPane.showMessageDialog(null, "장바구니에서 삭제되었습니다.", "Delete", JOptionPane.WARNING_MESSAGE);
				
				//삭제 후 새로고침
				ResultSet rsCart = db.DB_Lib.getResultSet(sqlCart);
				for (int i = 0; i < tableModel.getRowCount();) {
					tableModel.removeRow(i);
				}
				DrawTable(rsCart); //테이블 새로고침
				Pay(); //가격 라벨 새로고침
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
	
	private void DrawTable(ResultSet rs) { // 테이블 그리는 메소드
		try {
			while (rs.next()) { // DB이용해 테이블 생성, 상품코드, 상품 이름, 가격
				String lib_code = rs.getString("CODE");
				String lib_name = rs.getString("NAME");
				String lib_price = rs.getString("PRICE");

				Object data[] = {lib_code, lib_name, lib_price};
				tableModel.addRow(data);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Pay() {
		String sqlPrice = "SELECT PRICE FROM CART";
		ResultSet rsPrice = db.DB_Lib.getResultSet(sqlPrice);

		// 금액 계산
		try {
			sum=0;
			while (rsPrice.next()) {
				int price = Integer.parseInt(rsPrice.getString("PRICE"));
				sum += price;
			}
			DecimalFormat df = new DecimalFormat("###,###"); // 숫자 포맷, 단위 구분 기호 표시
			lblSum.setText("현재 금액은 " + df.format(sum) + "입니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}