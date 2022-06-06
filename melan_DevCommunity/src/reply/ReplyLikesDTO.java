package reply;

public class ReplyLikesDTO {
	private String likeNum; // 공감 일련번호
	private String memNum; // 회원 고유번호
	private String repNum; // 댓글 고유번호
	private String taste; // 공감/비공감 상태
	
	public String getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(String likeNum) {
		this.likeNum = likeNum;
	}
	public String getMemNum() {
		return memNum;
	}
	public void setMemNum(String memNum) {
		this.memNum = memNum;
	}
	public String getRepNum() {
		return repNum;
	}
	public void setRepNum(String repNum) {
		this.repNum = repNum;
	}
	public String getTaste() {
		return taste;
	}
	public void setTaste(String taste) {
		this.taste = taste;
	}

	
	
	
	
}
