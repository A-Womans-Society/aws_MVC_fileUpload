package model;

import java.sql.Timestamp;

public class BoardDto {
	private int num;
	private String writer;
	private String email;
	private String title;
	private String pass;
	private int readcount;
	private Timestamp regdate;
	private String content;
	private String file;

	public BoardDto() {
	}

	public BoardDto(String writer, String email, String title, String pass, int readcount, Timestamp regdate,
			String content, String file) {
		setContent(content);
		setEmail(email);
		setFile(file);
		setPass(pass);
		setReadcount(readcount);
		setTitle(title);
		setWriter(writer);
		setRegdate(regdate);
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
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

}
