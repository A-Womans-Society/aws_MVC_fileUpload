package daeunBoard;

import java.sql.Timestamp;

public class BoardVo {
	int num;
	String title;
	String content;
	String file;
	String writer;
	Timestamp regDate;
	int readCnt;
	
	public BoardVo() {}
	
	

	public BoardVo(int num, String title, String content, String file, String writer, Timestamp regDate, int readCnt) {
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.file = file;
		this.writer = writer;
		this.regDate = regDate;
		this.readCnt = readCnt;
	}



	public int getReadCnt() {
		return readCnt;
	}



	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}



	public int getNum() {
		return num;
	}



	public void setNum(int num) {
		this.num = num;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getFile() {
		return file;
	}



	public void setFile(String file) {
		this.file = file;
	}



	public String getWriter() {
		return writer;
	}



	public void setWriter(String writer) {
		this.writer = writer;
	}



	public Timestamp getRegDate() {
		return regDate;
	}



	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}



	@Override
	public String toString() {
		return "BoardVo [num=" + num + ", title=" + title + ", content=" + content + ", file=" + file + ", writer="
				+ writer + ", regDate=" + regDate + ", readCnt=" + readCnt + "]";
	}

	
}
