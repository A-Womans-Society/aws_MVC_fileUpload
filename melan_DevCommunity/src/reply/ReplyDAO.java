package reply;

import java.util.ArrayList;
import java.util.List;

import common.DBConnPool;

public class ReplyDAO extends DBConnPool{
	
	public ReplyDAO() {
		super();
	}
	
	// 전체 댓글 목록을 내림차순으로 반환
	public List<ReplyDTO> replyList() {
		List<ReplyDTO> replyList = new ArrayList<ReplyDTO>();
		
		try {			
			String query = "SELECT * FROM reply ORDER BY repNum DESC";
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				ReplyDTO dto = new ReplyDTO();
				
				dto.setRepNum(rs.getString(1));
				dto.setBoardNum(rs.getString(2));
				dto.setMemNum(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setLikeCount(rs.getInt(5));
				dto.setCreateDate(rs.getString(6));
				dto.setUpdateDate(rs.getString(7));
				
				replyList.add(dto);
			}
		} catch (Exception e) {
			System.out.println("전체 댓글 목록을 내림차순으로 반환 중 예외 발생");
			e.printStackTrace();
		}		
		return replyList;
	}
	
	
	// 게시글 번호에 해당하는 댓글 목록을 내림차순으로 반환
	public List<ReplyDTO> replyListByBN(String boardNum) {
		List<ReplyDTO> replyList = new ArrayList<ReplyDTO>();
		
		try {			
			String query = "SELECT * FROM reply WHERE boardNum=? ORDER BY createDate DESC";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, boardNum);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				ReplyDTO dto = new ReplyDTO();
				
				dto.setRepNum(rs.getString(1));
				dto.setBoardNum(rs.getString(2));
				dto.setMemNum(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setLikeCount(rs.getInt(5));
				dto.setCreateDate(rs.getString(6));
				dto.setUpdateDate(rs.getString(7));
				
				replyList.add(dto);
			}
		} catch (Exception e) {
			System.out.println("게시글 번호에 해당하는 댓글 목록을 내림차순으로 반환 중 예외 발생");
			e.printStackTrace();
		}		
		return replyList;
	}	
	
	
	// 모든 댓글 개수 반환
	public int selectCount() {
		int totalCount = 0;
		
		String query = "SELECT COUNT(*) FROM reply";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("모든 댓글 개수 반환 중 예외 발생");
			e.printStackTrace();
		}
		return totalCount;
	}	
	
	// 게시물 고유번호에 해당하는 댓글 개수 반환
	public int selectCount(String boardNum) {
		int totalCount = 0;
		
		String query = "SELECT COUNT(*) FROM reply WHERE boardNum=?";

		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, boardNum);
			rs = psmt.executeQuery();
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("게시물 고유번호에 해당하는 댓글 개수 반환 중 예외 발생");
			e.printStackTrace();
		}
		return totalCount;
	}
	
	// 회원고유번호에 해당하는 회원 아이디를 반환
	public List<String> getMemId(String memNum) {
		List<String> memIdList = new ArrayList<String>();
		
		try {			
			String query = "SELECT memId FROM member INNER JOIN reply ON member.memNum = reply.memNum";
			
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				memIdList.add(rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println("회원고유번호에 해당하는 회원 아이디를 반환 중 예외 발생");
			e.printStackTrace();
		}		
		return memIdList;
	}

	// 댓글 고유번호에 해당하는 회원고유번호를 반환
	public String getMemNum(String repNum) {
		String memNum = "";
		
		try {			
			String query = "SELECT memNum FROM reply WHERE repNum=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, repNum);
			rs = psmt.executeQuery();
			rs.next();
			memNum = rs.getString("memNum");
		} catch (Exception e) {
			System.out.println("댓글 고유번호에 해당하는 회원고유번호를 반환 중 예외 발생");
			e.printStackTrace();
		}		
		return memNum;
	}

	// 댓글 고유번호에 해당하는 rdto를 반환
	public ReplyDTO getRdto(String repNum) {
		ReplyDTO rdto = new ReplyDTO();
		
		try {			
			String query = "SELECT * FROM reply WHERE repNum=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, repNum);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				rdto.setRepNum(rs.getString(1));
				rdto.setBoardNum(rs.getString(2));
				rdto.setMemNum(rs.getString(3));
				rdto.setContent(rs.getString(4));
				rdto.setLikeCount(rs.getInt(5));
				rdto.setCreateDate(rs.getString(6));
				rdto.setUpdateDate(rs.getString(7));
			}
		} catch (Exception e) {
			System.out.println("댓글 고유번호에 해당하는 rdto를 반환 중 예외 발생");
			e.printStackTrace();
		}		
		return rdto;
	}	
	
	// 새로운 댓글 등록
	public int insertReply(ReplyDTO dto) {
		int applyResult = 0;
		
		try {
			String query = "INSERT INTO reply( "
					+ " repNum, boardNum, memNum, content, likeCount, createDate) "
					+ " VALUES ( "
					+ " seq_reply_num.NEXTVAL, ?, ?, ?, ?, to_char(sysdate, 'yyyy-mm-dd HH24:MI:SS'))";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getBoardNum());
			psmt.setString(2, dto.getMemNum());
			psmt.setString(3, dto.getContent());
			psmt.setInt(4, dto.getLikeCount());
			
			applyResult = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("새로운 댓글 등록 중 예외 발생");
			e.printStackTrace();
		}
		return applyResult;
	}	
	
	// 주어진 일련번호에 해당하는 댓글의 공감수를 1 증가시킴
	public int upLikeCount(String repNum) {
		int rs = 0;
		
		String query = "UPDATE reply SET " 
				+ " likeCount = likeCount + 1 "
				+ " WHERE repNum = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, repNum);
			rs = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("댓글의 공감수 1 증가 중 예외 발생");
			e.printStackTrace();
		}
		return rs;
	}
	
	// 주어진 일련번호에 해당하는 댓글의 공감수를 1 감소시킴
	public int downLikeCount(String repNum) {
		int rs = 0;
		
		String query = "UPDATE reply SET " 
				+ " likeCount = likeCount - 1 "
				+ " WHERE repNum = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, repNum);
			rs = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("댓글의 공감수 1 감소 중 예외 발생");
			e.printStackTrace();
		}
		return rs; // 감소 성공 시 1 반환
	}
	
	// 지정한 일련번호의 댓글 삭제
	public int deleteReply(String repNum) {
		int result = 0;
		
		try {
			String query = "DELETE FROM reply WHERE repNum=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, repNum);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("댓글 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result; // 정상적으로 삭제되었다면 1 반환
	}
	
}
