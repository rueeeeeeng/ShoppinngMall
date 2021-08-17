package util;

import javax.swing.JFrame;

public class Frames extends JFrame{
	
	public Frames() {
//		this.setResizable(false); //창 사이즈 조정 금지
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램이 끝나면 닫고 종료한다 
//		setUndecorated(true); //타이틀바 날리기
	}
	
}
