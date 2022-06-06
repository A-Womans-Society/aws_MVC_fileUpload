package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnPool {
	public Connection con;
	public Statement stmt;
	public PreparedStatement psmt;
	public ResultSet rs;
	
	public DBConnPool() {
		try {
			//커넥션 풀(Datasource) 열기
			Context initCtx = new InitialContext(); // InitialContext라는 객체는 실제 객체를 연결해주는 context고, 시작점임
			Context ctx = (Context)initCtx.lookup("java:comp/env"); // lookup메서드에 이름을 건네 원하는 객체 찾음, 루트디렉터
			DataSource source = (DataSource)ctx.lookup("dbcp_myOracle"); // 앞서 설정한 데이터소스(커넥션 풀)
			
			// 커넥션 풀을 통해 연결 얻기
			con = source.getConnection();
			
			System.out.println("DB 커넥션 풀 연결 성공");
		} catch (Exception e)	{
			System.out.println("DB 커넥션 풀 연결 실패");
			e.printStackTrace();
		}
	}
	// 연결 해제 (자원 반납)
	
	public void close() {
		try {
			if(psmt != null)psmt.close();
			if(stmt != null) stmt.close();
			if(con != null) con.close(); // 자동으로 커넥션 풀로 반납됨.
			if(rs != null) rs.close();
			System.out.println("커넥션 풀 자원 반납");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
