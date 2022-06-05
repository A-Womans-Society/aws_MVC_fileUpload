package daeunBoard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class BoardDao {
	private static BoardDao instance = null;

	private BoardDao() {
	}

	public static BoardDao getInstance() {
		if (instance == null) {
			synchronized (BoardDao.class) {
				instance = new BoardDao();
			}
		}
		return instance;
	}

	public void insertArticle(String title, String uploadFile, String writer, String content) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement(
					"insert into \"BOARD\" (\"NUM\",\"WRITER\",\"TITLE\",\"REGDATE\",\"FILE\", \"CONTENT\", \"READCNT\") "
							+ "values(\"BOARD_SEQ\".nextval,?,?,sysdate,?,?,0)");
			pstmt.setString(1, writer);
			pstmt.setString(2, title);
			pstmt.setString(3, uploadFile);
			pstmt.setNString(4, content);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	public void insertNofile(String title, String writer, String content) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement(
					"insert into \"BOARD\" (\"NUM\",\"WRITER\",\"TITLE\",\"REGDATE\", \"CONTENT\", \"READCNT\") "
							+ "values(\"BOARD_SEQ\".nextval,?,?,sysdate,?,0)");
			pstmt.setString(1, writer);
			pstmt.setString(2, title);
			pstmt.setNString(3, content);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public BoardVo listOne(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo article = new BoardVo();
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement("update BOARD set READCNT=READCNT+1 where NUM=?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = conn.prepareStatement(
					"select \"NUM\",\"WRITER\",\"TITLE\",\"CONTENT\",\"FILE\" from BOARD where NUM=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				article = new BoardVo();
				article.setNum(rs.getInt("NUM"));
				article.setWriter(rs.getString("WRITER"));
				article.setTitle(rs.getString("TITLE"));
				article.setContent(rs.getString("CONTENT"));
				article.setFile(rs.getString("FILE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return article;
	}

	public List<BoardVo> listAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement(
					"select \"NUM\",\"WRITER\",\"TITLE\",\"REGDATE\",\"FILE\",\"READCNT\" from \"BOARD\" order by \"NUM\" desc");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVo article = new BoardVo();
				article.setNum(rs.getInt("num"));
				article.setWriter(rs.getString("writer"));
				article.setTitle(rs.getString("title"));
				article.setRegDate(rs.getTimestamp("regDate"));
				article.setFile(rs.getString("file"));
				article.setReadCnt(rs.getInt("readCnt"));
				list.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return (list.size() == 0) ? null : list;
	}

	public int counter() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement("select count(*) from BOARD");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return count;
	}

	public BoardVo update(String title, String content, String uploadFile, int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardVo article = new BoardVo();
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn
					.prepareStatement("update \"BOARD\" set \"TITLE\"=?, \"CONTENT\"=?, \"FILE\"=? where \"NUM\"=?");
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, uploadFile);
			pstmt.setInt(4, num);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return article;
	}
	
	public BoardVo updateNofile(String title, String content, int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardVo article = new BoardVo();
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn
					.prepareStatement("update \"BOARD\" set \"TITLE\"=?, \"CONTENT\"=? where \"NUM\"=?");
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, num);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return article;
	}

	public void delete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement("delete from BOARD where NUM=?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}
}
