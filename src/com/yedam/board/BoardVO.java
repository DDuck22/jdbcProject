package com.yedam.board;

import lombok.Data;

@Data
public class BoardVO {
	private int brd_no;
	private String brd_title;
	private String brd_writer;
	private String brd_content;
	private String create_date;
	private int click_cnt;

	public void toList() {
		System.out.println("번호: " + this.brd_no //
				+ " 제목: " + this.brd_title //
				+ " 작성자: " + this.brd_writer //
				+ " 작성일자: " + this.create_date //
				+ " 조회수: " + this.click_cnt);
	}
	public void toSearch() {
		System.out.println("번호: " + this.brd_no //
				+ " 제목: " + this.brd_title //
				+ " 작성자: " + this.brd_writer //
				+ " 작성일자: " + this.create_date //
				+ " 조회수: " + this.click_cnt);
		System.out.println("내용: "+this.brd_content);
	}
}
