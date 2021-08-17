package manager;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import db.DB_Lib;

public class ProductAddAndDelete extends JFrame implements ActionListener {
	JButton b1, b2, b3, b4, b5;
	private JPanel p1, p2;

	public ProductAddAndDelete() {
		setTitle("상품 추가/삭제 카테고리");
		setSize(350, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setResizable(false);

		mainList();

		p2 = new JPanel();
		p2.setBackground(Color.WHITE);
		p2.setLayout(new FlowLayout());
		add(p2, BorderLayout.SOUTH);

		b5 = new JButton("취소");
		b5.setBackground(Color.WHITE);
		b5.addActionListener(this);

		p2.add(b5);

		setVisible(true);
	}

	private void mainList() {
		p1 = new JPanel();
		p1.setBackground(Color.WHITE);
		p1.setLayout(new GridLayout(2, 2, 30, 30));
		p1.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		add(p1, BorderLayout.CENTER);

		b1 = new JButton("모니터");
		b1.setBackground(new Color(1, 92, 173));
		b1.setForeground(new Color(255, 255, 255));
		b2 = new JButton("키보드");
		b2.setBackground(new Color(1, 92, 173));
		b2.setForeground(new Color(255, 255, 255));
		b3 = new JButton("마우스");
		b3.setBackground(new Color(1, 92, 173));
		b3.setForeground(new Color(255, 255, 255));
		b4 = new JButton("노트북");
		b4.setBackground(new Color(1, 92, 173));
		b4.setForeground(new Color(255, 255, 255));

		p1.add(b1);
		p1.add(b2);
		p1.add(b3);
		p1.add(b4);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new ProductAddAndDelete();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == b1) {
			new MonitorAddAndDelete("모니터 추가", 600, 400);
		} else if (obj == b2) {
			new KeyboardAddAndDelete();
		} else if (obj == b3) {
			new MouseAddAndDelete();
		} else if (obj == b4) {
			new NotebookAddAndDelete();
		} else if (obj == b5) {
			dispose();
			
		}
	}

}