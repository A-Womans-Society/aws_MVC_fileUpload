package reply;

public class ReplyDTO {
	
	private String repNum; // 댓글 일련번호
	private String boardNum; // 게시물 고유번호
	private String memNum; // 회원 고유번호
	private String content; // 댓글 내용
	private int likeCount; // 좋아요 갯수
	private String createDate; // 댓글 생성일(초단위)
	private String updateDate; // 댓글 수정일(초단위)
	
	public String getRepNum() {
		return repNum;
	}
	public void setRepNum(String repNum) {
		this.repNum = repNum;
	}
	public String getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(String boardNum) {
		this.boardNum = boardNum;
	}
	public String getMemNum() {
		return memNum;
	}
	public void setMemNum(String memNum) {
		this.memNum = memNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
