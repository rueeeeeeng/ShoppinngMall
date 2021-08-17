package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Lib {

	static ResultSet rs;
	
	public static Connection conn;
	public static Statement stmt;
	
	public static void init() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@114.71.137.174:53994:XE", "COLA", "0517");
			stmt = conn.createStatement();
			System.out.println("DB 연결 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 연결 오류 또는 쿼리 오류 입력");
			e.printStackTrace();
		}
	}

	//조회
	public static ResultSet getResultSet(String sql) {
		try {
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	//삽입, 수정, 삭제
	public static void executeQuery(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
