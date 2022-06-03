package member;

public class MemberDTO {
	
	private String memNum;
	private String memId;
	private String memPwd;
	private java.sql.Date memRegidate;
	
	public MemberDTO() {
		
	}
	
	public String getMemNum() {
		return memNum;
	}
	public void setMemNum(String memNum) {
		this.memNum = memNum;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPwd() {
		return memPwd;
	}
	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}
	public java.sql.Date getMemRegidate() {
		return memRegidate;
	}
	public void setMemRegidate(java.sql.Date memRegidate) {
		this.memRegidate = memRegidate;
	}
	
	
		
	
}
