package mvcfileboard;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDao {
	private static DataSource ds = null;
	{
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/myOracle");
			}catch(Exception e) {
				System.err.println("Connection 실패");
		}
	}
		public Connection getConnection() throws SQLException{
			return ds.getConnection();
		}
		
		private static BoardDao instance = null;
		public BoardDao() {	}
		
		public static BoardDao getInstance() {
			if(instance == null) {
				synchronized(BoardDao.class) {
					instance = new BoardDao();
				}
			}
			return instance;
		}

	public boolean insertArticle(BoardDto dto) {
		boolean ret = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
	
		try {
			conn = ds.getConnection();
			String sql = "insert into \"MVC_BOARD\" values (MVC_BOARD_SEQ.nextval,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getWriter());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getPass());
			pstmt.setInt(4, dto.getReadcount());		
			pstmt.setTimestamp(5, dto.getRegdate());
			pstmt.setString(6, dto.getContent());
			pstmt.setString(7, dto.getFilename());
			
			pstmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
		return ret;
		
	}
	
	public int getArticleCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select count(*) from \"MVC_BOARD\"");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				x = rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
			}
		}
		return x;
	}
	
	public List<BoardDto> getArticles(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardDto> articleList = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select * from \"MVC_BOARD\" order by \"NUM\" asc");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				articleList = new ArrayList<BoardDto>();
				do {
					BoardDto dto = new BoardDto();
					dto.setNum(rs.getInt("num"));
					dto.setWriter(rs.getString("writer"));		
					dto.setSubject(rs.getString("subject"));
					dto.setPass(rs.getString("pass"));					
					dto.setReadcount(rs.getInt("readcount"));
					dto.setRegdate(rs.getTimestamp("regdate"));
					dto.setContent(rs.getString("content"));
					dto.setFilename(rs.getString("filename"));
					articleList.add(dto);
				} while(rs.next());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
			}
		}
		return articleList;
	}
	
	public BoardDto getArticle(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto dto = new BoardDto();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("update \"MVC_BOARD\" set \"READCOUNT\"=\"READCOUNT\"+1 where \"NUM\"=?");
			pstmt.setInt(1, num);	
			pstmt.executeQuery();
			pstmt.close();
			
			pstmt = conn.prepareStatement("select * from \"MVC_BOARD\" where \"NUM\"=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setNum(rs.getInt("num"));
				dto.setWriter(rs.getString("writer"));
				dto.setSubject(rs.getString("subject"));
				dto.setPass(rs.getString("pass"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRegdate(rs.getTimestamp("regdate"));
				dto.setContent(rs.getString("content"));
				dto.setFilename(rs.getString("filename"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
			}
		}
		return dto;
	}

	public BoardDto updateGetArticle(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto dto = new BoardDto();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select * from \"MVC_BOARD\" where \"NUM\"=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setNum(rs.getInt("num"));
				dto.setWriter(rs.getString("writer"));
				dto.setSubject(rs.getString("subject"));
				dto.setPass(rs.getString("pass"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRegdate(rs.getTimestamp("regdate"));
				dto.setContent(rs.getString("content"));
				dto.setFilename(rs.getString("filename"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
			}
		}
		return dto;
	}
	
	public int updateArticle(BoardDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbpassword = "";
		int result = -1; //결과값
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select \"PASS\" from \"MVC_BOARD\" where \"NUM\"=?");
			pstmt.setInt(1, dto.getNum());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbpassword = rs.getString("pass"); //비밀번호 비교
				if(dbpassword.equals(dto.getPass())) {
					String sql = "update \"MVC_BOARD\" set \"WRITER\"=?, \"SUBJECT\"=?, \"CONTENT\"=? where \"NUM\"=?";
					pstmt.close();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, dto.getWriter());					
					pstmt.setString(2, dto.getSubject());
					pstmt.setString(3, dto.getContent());	
					pstmt.setInt(4, dto.getNum());
					pstmt.executeQuery();
					result = 1; //수정 성공
				} else {
					result = 0; //수정 실패
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
			}
		}
		return result;
	}
	
	public int deleteArticle(int num, String pass) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbpassword = "";
		int result = -1; //결과값
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select \"PASS\" from \"MVC_BOARD\" where \"NUM\"=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbpassword = rs.getString("pass"); //비밀번호 비교
				if(dbpassword.equals(pass)) {
					pstmt.close();
					pstmt = conn.prepareStatement("delete from \"MVC_BOARD\" where \"NUM\"=?");
					pstmt.setInt(1, num);
					pstmt.executeQuery();
					result = 1; //수정 성공
				} else {
					result = 0; //수정 실패
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
			}
		}
		return result;
	}

}
