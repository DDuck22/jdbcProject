package com.yedam.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.Dao;

public class BoardDao {
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	String sql = "";

	

	private void close() {
		try {
			conn.setAutoCommit(false);
			conn.commit();
			if (conn != null) {
				conn.close();
			} else if (psmt != null) {
				psmt.close();
			} else if (rs != null) {
				rs.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean add(BoardVO brd) {
		sql = "INSERT INTO tbl_board (brd_no, brd_title, brd_content, brd_writer) "//
				+ " values (board_seq.nextval,?,?,?)";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, brd.getBrd_title());
			psmt.setString(2, brd.getBrd_content());
			psmt.setString(3, brd.getBrd_writer());

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return false;
	}

	public boolean noCheck(int no) {
		sql = "select * from tbl_board where brd_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);

			rs = psmt.executeQuery();
			if (!rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean remove(int no) {
		sql = "delete from tbl_board where brd_no=?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean modify(BoardVO brd) {
		sql = "update  tbl_board " //
				+ "set     brd_title = ?, "//
				+ "        brd_content = ? "//
				+ "where   brd_no=?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, brd.getBrd_title());
			psmt.setString(2, brd.getBrd_content());
			psmt.setInt(3, brd.getBrd_no());

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return false;
	}

	public List<BoardVO> list() {
		List<BoardVO> list = new ArrayList<>();
		sql = "select * from tbl_board";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				BoardVO brd = new BoardVO();
				brd.setBrd_no(rs.getInt("brd_no"));
				brd.setBrd_title(rs.getString("brd_title"));
				brd.setBrd_writer(rs.getString("brd_writer"));
				brd.setBrd_content(rs.getString("brd_content"));
				brd.setCreate_date(rs.getString("create_date"));
				brd.setClick_cnt(rs.getInt("click_cnt"));

				list.add(brd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;

	}

	public BoardVO search(int no) {
		sql = "select * from tbl_board where brd_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);

			rs = psmt.executeQuery();
			if (rs.next()) {
				BoardVO brd = new BoardVO();

				brd.setBrd_no(rs.getInt("brd_no"));
				brd.setBrd_title(rs.getString("brd_title"));
				brd.setBrd_writer(rs.getString("brd_writer"));
				brd.setBrd_content(rs.getString("brd_content"));
				brd.setCreate_date(rs.getString("create_date"));
				brd.setClick_cnt(rs.getInt("click_cnt") + 1);

				sql = "update tbl_board set click_cnt = ? where brd_no = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, brd.getClick_cnt());
				psmt.setInt(2, no);

				psmt.executeUpdate();

				return brd;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public boolean login(String id, String pw) {
		sql = "select * from tbl_users where user_id = ? AND user_pw = ?";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
