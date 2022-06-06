package model2.mvcboard;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;
//extends DBConnPool{ // 커넥션 풀 상속
public class MVCBoardDAO extends DBConnPool { // 커넥션 풀 상속

	public MVCBoardDAO() {
	}

	// 검색 조건에 맞는 게시물 수 반환
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		// 쿼리문 준비
		String query = "SELECT COUNT(*) FROM MVCBOARD";
		// 검색 조건이 있다면 WHERE 절로 추가
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " LIKE '%" + map.get("searchWord") + "%' ";
		}
		try {
			stmt = con.createStatement(); // 쿼리문 생성
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1); // 검색된 게시물 개수 저장
		} catch (Exception e) {
			System.out.println("게시물 카운트 중 예외 발생");
			e.printStackTrace();
		}
		return totalCount; // 게시쿨 개수를 서블릿으로 반환
	}

	// 검색 조건에 맞는 게시물 목록을 반환(페이징 기능 지원)
	public List<MVCBoardDTO> selectListPage(Map<String, Object> map) {
		List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
		// 쿼리문 준비
		String query = "SELECT * FROM ( "
				 + "	SELECT Tb.*, ROWNUM rNum FROM ( "
				 + " 		SELECT * FROM mvcboard ";
		// 검색 조건이 있다면 WHERE 절로 추가
		if (map.get("searchWord") != null) {
			query += "WHERE" + map.get("searchField") 
			+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += "ORDER BY idx DESC" 
		+ " )Tb " 
		+ ")" 
		+ "WHERE rNUM BETWEEN ? AND ?";// 게시물 구간은 인파라이터로

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery(); // 쿼리문 실행

			// 반환된 게시물 목록을 List 컬렉션에 추가
			while (rs.next()) {
				MVCBoardDTO dto = new MVCBoardDTO();

				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
				// 목록에 저장 LIST는 필수
				board.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return board; // 목록 반환
	}

	// 글쓰기 처리 메서드 추가
	public int insertWrite(MVCBoardDTO dto) { //게시글 데이터를 받아 db에 추가. 파일 업로드 지원.
		// 웹 페이지(Write.jsp)에서 전송한 폼값을 서블릿(writecontroller)이 받아 dto에 저장 후 dao로 전달해줄 것
		int result = 0;
		try {
			String query = "INSERT INTO MVCBOARD (idx, name, title, content, ofile, sfile, pass) VALUES (SEQ_BOARD_NUM.NEXTVAL, ?,?,?,?,?,?)";
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass());
			result = psmt.executeUpdate();
		}
		catch(Exception e){
		System.out.println("게시물 조회 중 예외 발생");
		e.printStackTrace();
	}return result; // writecontroller 서블릿으로 결과 반환
}
	
	// 주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환
	public MVCBoardDTO selectView(String idx) {
		MVCBoardDTO dto = new MVCBoardDTO(); //dto객체 생성
		String query = "SELECT * FROM MVCBOARD WHERE IDX = ?"; //쿼리문 템플릿 준비
		
		try {
			psmt = con.prepareStatement(query); //쿼리문 준비
			psmt.setString(1, idx); // 파라미터 설정
			rs = psmt.executeQuery(); //쿼리문 실행
			//결과 dto 객체에 저장하기 
			if( rs.next()) {
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
			}
		}
		catch(Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		
		return dto; //결과 반환
	}
	
	// 주어진 일련번호에 해당하는 게시물의 조회수를 1 증가 시키기
	public void updateVisitCount(String idx) { //일련번호를 인수로 받아 조회
		String query = "UPDATE MVCBOARD SET VISITCOUNT = VISITCOUNT + 1 WHERE IDX =? ";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			psmt.executeQuery();
		}
		catch(Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}	//반환할 DTO없음
	}
	
	// 다운로드 횟수를 1 증가시키기 - 일련번호를 받아 증가.
	public void downCountPlus(String idx) {
		String sql = "UPDATE MVCBOARD SET DOWNCOUNT = DOWNCOUNT + 1 WHERE NUM = ? ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, idx);
			psmt.executeUpdate();
		}	catch(Exception e) {}
	}
	
	// 입력한 비밀번호가 지정한 일련번호의 게시물의 비밀번호와 일치하는지 확인
	public boolean confirmPassword(String pass, String idx) {
		boolean isCorr = true;
		try {
			String sql = "SELECT COUNT (*) FROM MVCBOARD WHERE PASS =? AND IDX =?";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pass);
			psmt.setString(2, idx);
			psmt.executeQuery();
			rs.next(); 
			if(rs.getInt(1) == 0) {
				isCorr = false;
			}
		}
			catch(Exception e ) {
				isCorr = false;
				e.printStackTrace();
			}
			return isCorr;
		}
	
		// 지정한 일련번호의 게시물을 삭제
		public int deletePost (String idx) {
			int result =0;
			try {
			String query = "DELET FROM MVCBOARD WHERE IDX=?";	
			
			}
		}
	
}
