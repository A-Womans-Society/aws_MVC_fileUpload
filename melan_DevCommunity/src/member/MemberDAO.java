package member;

import common.DBConnPool;

public class MemberDAO extends DBConnPool {
	
	public MemberDAO() {
		super();
	}
	
	public int memIdCheck(String targetMemId) {
		int result = 1;
		String query = "SELECT memId FROM member";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (rs.getString("memId").equals(targetMemId)) {
					return 0; // 아이디가 중복되면 0 반환
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result; // 아이디가 중복되지 않으면 1 반환
	}
	
	
	// 명시한 아이디와 일치하는 회원정보를 반환
	public MemberDTO getMemberDTO(String memId) {
		MemberDTO dto = new MemberDTO(); 
		String query = "SELECT * FROM member WHERE memId=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, memId);
			rs = psmt.executeQuery();
		
			if (rs.next()) { 
				dto.setMemNum(rs.getString("memNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setMemPwd(rs.getString("memPwd"));
				dto.setMemRegidate(rs.getDate("memRegidate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	// 명시한 회원고유번호와 일치하는 회원정보를 반환
	public MemberDTO getMdtoByNum(String memNum) {
		MemberDTO dto = new MemberDTO(); 
		String query = "SELECT * FROM member WHERE memNum=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, memNum);
			rs = psmt.executeQuery();
		
			if (rs.next()) { 
				dto.setMemNum(rs.getString("memNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setMemPwd(rs.getString("memPwd"));
				dto.setMemRegidate(rs.getDate("memRegidate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	// 명시한 아이디/패스워드와 일치하는 회원정보를 반환
	public MemberDTO getMemberDTO(String userId, String userPwd) {
		MemberDTO dto = new MemberDTO(); 
		String query = "SELECT * FROM member WHERE memId=? AND memPwd=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, userId);
			psmt.setString(2, userPwd);
			rs = psmt.executeQuery();
		
			if (rs.next()) { 
				dto.setMemNum(rs.getString("memNum"));
				dto.setMemId(rs.getString("memId"));
				dto.setMemPwd(rs.getString("memPwd"));
				dto.setMemRegidate(rs.getDate("memRegidate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	// 새로운 회원 등록
	public int insertMember(MemberDTO dto) {
		int applyResult = 0;
		
		try {
			String query = "INSERT INTO member (memNum, memId, memPwd, memRegidate) "
					+ " VALUES (seq_member_num.NEXTVAL, ?, ?, sysdate)";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getMemId());
			psmt.setString(2, dto.getMemPwd());

			applyResult = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("새로운 회원 등록 중 예외 발생");
			e.printStackTrace();
		}
		return applyResult;
	}
	
	// 회원 정보 삭제
	public int deleteMember(String memNum) {
		int result = 0;
		
		try {
			String query = "DELETE FROM member WHERE memNum=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, memNum);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("회원정보 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result; // 정상적으로 삭제되었다면 1 반환
	}
	
	// 회원 아이디 업데이트
	public int updateMemId(MemberDTO dto) {
		int result = 0;
		
		try { 
			String query = "UPDATE member SET "
					+ " memId=? "
					+ " WHERE memNum=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getMemId()); // 새로운 아이디
			System.out.println(dto.getMemId());
			psmt.setString(2, dto.getMemNum()); // 기존 회원 고유번호
			System.out.println(dto.getMemNum());

			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("회원 아이디 수정 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

	
	// 회원 비밀번호 업데이트
	public int updateMemPwd(MemberDTO dto) {
		int result = 0;
		
		try { 
			String query = "UPDATE member SET "
					+ " memPwd=? "
					+ " WHERE memNum=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getMemPwd()); // 새로운 비밀번호
			psmt.setString(2, dto.getMemNum()); // 기존 회원 고유번호
			
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("회원 비밀번호 수정 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	
}
