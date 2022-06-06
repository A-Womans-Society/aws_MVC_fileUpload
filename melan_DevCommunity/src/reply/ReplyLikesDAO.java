package reply;

import common.DBConnPool;

public class ReplyLikesDAO extends DBConnPool{

	public ReplyLikesDAO() {
		super();
	}
	
	// 회원고유번호와 댓글번호에 해당하는 ldto를 반환
	public ReplyLikesDTO getLdto(String memNum, String repNum) {
		ReplyLikesDTO ldto = new ReplyLikesDTO();	
		String query = "SELECT * FROM replyLikes WHERE memNum=? and repNum=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, memNum);
			psmt.setString(2, repNum);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				ldto.setLikeNum(rs.getString(1));
				ldto.setMemNum(rs.getString(2));
				ldto.setRepNum(rs.getString(3));
				ldto.setTaste(rs.getString(4));
			} else {
				ldto = null;
			}
		} catch (Exception e) {
			System.out.println("회원고유번호와 댓글번호에 해당하는 ldto를 반환 중 예외 발생");
			e.printStackTrace();
		}		
		
		return ldto;
	}
	
	// 회원고유번호와 댓글번호에 대한 공감상태를 반환
	public String getTaste(String memNum, String repNum) {
		String taste = "";
		
		String query = "SELECT taste FROM replyLikes WHERE memNum=? and repNum=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, memNum);
			psmt.setString(2, repNum);
			rs = psmt.executeQuery();
			rs.next();
			taste = rs.getString("taste");
			
		} catch (Exception e) {
			System.out.println("회원고유번호와 댓글번호에 대한 공감상태를 반환 중 예외 발생");
			e.printStackTrace();
		}		
		
		return taste;
	}
	

	// 새로운 공감 등록
	public int insertLike(ReplyLikesDTO ldto) {
		int applyResult = 0;
		
		try {
			String query = "INSERT INTO replyLikes( "
					+ " likeNum, memNum, repNum, taste) "
					+ " VALUES ( "
					+ " seq_replyLikes_num.NEXTVAL, ?, ?, ? )";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, ldto.getMemNum());
			psmt.setString(2, ldto.getRepNum());
			psmt.setString(3, ldto.getTaste());
			
			applyResult = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("새로운 공감 등록 중 예외 발생");
			e.printStackTrace();
		}
		return applyResult;
	}	
	
	// 지정한 댓글일련번호에 해당하는 ldto 모두 삭제
	public void deleteReplyLikes(String repNum) {		
		try {
			String query = "DELETE FROM replyLikes WHERE repNum=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, repNum);
			psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("지정한 댓글일련번호에 해당하는 ldto 모두 삭제 중 예외 발생");
			e.printStackTrace();
		}
	}
}
