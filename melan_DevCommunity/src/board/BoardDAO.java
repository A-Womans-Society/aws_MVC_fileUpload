package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.DBConnPool;

public class BoardDAO extends DBConnPool{
	
	public BoardDAO() {
		super();
	}

	// 전체 게시물 목록을 내림차순으로 반환
	public List<BoardDTO> boardList() {
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {			
			String query = "SELECT * FROM board ORDER BY boardNum DESC";
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setBoardNum(rs.getString(1));
				dto.setMemNum(rs.getString(2));
				dto.setMemId(rs.getString(3));
				dto.setTitle(rs.getString(4));
				dto.setContent(rs.getString(5));
				dto.setPostdate(rs.getDate(6));
				dto.setOfile(rs.getString(7));
				dto.setSfile(rs.getString(8));
				dto.setDowncount(rs.getInt(9));
				dto.setVisitcount(rs.getInt(10));
				dto.setRepcount(rs.getInt(11));
				
				boardList.add(dto);
			}
		} catch (Exception e) {
			System.out.println("전체 게시물 목록을 내림차순으로 반환 중 예외 발생");
			e.printStackTrace();
		}		
		return boardList;
	}
	
	// 검색 조건에 맞는 게시물 개수 반환
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		
		String query = "SELECT COUNT(*) FROM board";
		// 검색 조건이 있다면 WHERE절로 추가
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " LIKE '%" + map.get("searchWord") + "%' ";
		}
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("검색 조건에 맞는 게시물 개수 반환 중 예외 발생");
			e.printStackTrace();
		}
		return totalCount;
	}
	
	// 검색 조건에 맞는 게시물 목록을 반환(페이징 기능 지원)
	public List<BoardDTO> selectListPage(Map<String, Object> map) {
		List<BoardDTO> boardlist = new ArrayList<>();
		
		String query = "SELECT * FROM ( "
					 + "	SELECT Tb.*, ROWNUM rNum FROM ( "
					 + " 		SELECT * FROM board ";
		
		// 검색 조건이 있다면 WHERE절로 추가
		if (map.get("searchWord") != null) {
			if (map.get("searchField").equals("memId")) {
				query += " WHERE " + map.get("searchField") + " = '" + map.get("searchWord") + "' ";
			} else {
				query += " WHERE " + map.get("searchField") + " LIKE '%" + map.get("searchWord") + "%' ";
			}
		}
		
		query += "			ORDER BY boardNum DESC "
				+ "		) Tb "
				+ "	) "
				+ " WHERE rNum BETWEEN ? AND ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setBoardNum(rs.getString("boardNum"));
				dto.setMemNum(rs.getString("memNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setDowncount(rs.getInt("downcount"));
				dto.setVisitcount(rs.getInt("visitcount"));
				dto.setRepcount(rs.getInt("repcount"));
				
				boardlist.add(dto);
			}
		} catch (Exception e) {
			System.out.println("검색 조건에 맞는 게시물 목록을 반환 중 예외 발생");
			e.printStackTrace();
		}
		return boardlist;
	}
	
	
	// 새로운 게시물 등록
	public int insertBoard(BoardDTO dto) {
		int applyResult = 0;
		
		try {
			String query = "INSERT INTO board ( "
					+ " boardNum, memNum, memId, title, content, ofile, sfile) "
					+ " VALUES ( "
					+ " seq_board_num.NEXTVAL, ?, ?, ?, ?, ?, ?)";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getMemNum());
			psmt.setString(2, dto.getMemId());
			psmt.setString(3, dto.getTitle());
			psmt.setString(4, dto.getContent());
			psmt.setString(5, dto.getOfile());
			psmt.setString(6, dto.getSfile());
			
			applyResult = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("새로운 게시물 등록 중 예외 발생");
			e.printStackTrace();
		}
		return applyResult;
	}
	
	// 주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환
	public BoardDTO selectView(String boardNum) {
		BoardDTO dto = new BoardDTO(); 
		
		String query = "SELECT * FROM board WHERE boardNum=?"; 
		
		try {
			psmt = con.prepareStatement(query); 
			psmt.setString(1, boardNum); 
			rs = psmt.executeQuery(); 
			
			if (rs.next()) {
				dto.setBoardNum(rs.getString(1));
				dto.setMemNum(rs.getString(2));
				dto.setMemId(rs.getString(3));
				dto.setTitle(rs.getString(4));
				dto.setContent(rs.getString(5));
				dto.setPostdate(rs.getDate(6));
				dto.setOfile(rs.getString(7));
				dto.setSfile(rs.getString(8));
				dto.setDowncount(rs.getInt(9));
				dto.setVisitcount(rs.getInt(10));
				dto.setRepcount(rs.getInt(11));
			}
		} catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		return dto;
	}

	// 주어진 일련번호에 해당하는 게시물의 조회수를 1 증가시킴
	public void updateVisitCount(String boardNum) {
		String query = "UPDATE board SET " 
				+ " visitcount = visitcount + 1 "
				+ " WHERE boardNum = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, boardNum);
			psmt.executeQuery();
		} catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	// 주어진 일련번호에 해당하는 게시물의 다운로드 횟수를 1 증가시킴
	public void downCountPlus(String boardNum) {
		String query = "UPDATE board SET " 
				+ " downcount = downcount + 1 "
				+ " WHERE boardNum = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, boardNum);
			psmt.executeQuery();
		} catch (Exception e) {
			System.out.println("다운로드 횟수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	
	// 지정한 일련번호의 게시물 삭제
	public int deletePost(String boardNum) {
		int result = 0;
		
		try {
			String query = "DELETE FROM board WHERE boardNum=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, boardNum);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result; // 정상적으로 삭제되었다면 1 반환
	}
	
	// 지정한 회원 아이디가 작성한 게시물 삭제
	public int deletePostByMemId(String memId) {
		int result = 0;
		
		try {
			String query = "DELETE FROM board WHERE memId=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, memId);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("해당 게시물 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result; // 정상적으로 삭제되었다면 1이상을 반환
	}
	
	// 게시글 데이터를 받아 DB에 저장되어 있던 내용을 갱신(파일 업로드 지원)
	public int updatePost(BoardDTO dto) {
		int result = 0;
		
		try { 
			String query = "UPDATE board SET "
					+ " title=?, content=?, ofile=?, sfile=? "
					+ " WHERE boardNum=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getOfile());
			psmt.setString(4, dto.getSfile());
			psmt.setString(5, dto.getBoardNum());
			
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	// 신규 아이디와 기존 아이디를 받아 게시글 작성자 아이디 컬럼도 업데이트
	public int updatePostByMemId(String newId, String memId) {
		int result = 0;
		
		try { 
			String query = "UPDATE board SET "
					+ " memId=? "
					+ " WHERE memId=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, newId); // 새로운 아이디
			psmt.setString(2, memId); // 기존 아이디
			
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
}
