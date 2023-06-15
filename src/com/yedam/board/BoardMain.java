package com.yedam.board;

import java.util.List;
import java.util.Scanner;

public class BoardMain {
	public static void main(String[] args) {
		BoardDao dao = new BoardDao();
		Scanner scn = new Scanner(System.in);
		int menu;
		while (true) {
			System.out.print("id> ");
			String id = scn.nextLine();
			System.out.print("pw> ");
			String pw = scn.nextLine();
			
			if(dao.login(id,pw)) {
				break;
			}
			System.out.println("login 실패");
		}
		while (true) {
			System.out.println("1.글등록 2.삭제 3.글내용수정 4.글목록보기 5.상세보기 6.종료");
			menu = scn.nextInt();
			scn.nextLine();
			if (menu == 1) {
				System.out.print("글제목> ");
				String title = scn.nextLine();
				System.out.print("작성자> ");
				String writer = scn.nextLine();
				System.out.print("글내용> ");
				String content = scn.nextLine();

				BoardVO brd = new BoardVO();
				brd.setBrd_title(title);
				brd.setBrd_content(content);
				brd.setBrd_writer(writer);

				if (dao.add(brd)) {
					System.out.println("등록되었습니다.");
				} else {
					System.out.println("등록에 실패했습니다.");
				}
			} else if (menu == 2) {
				System.out.print("글번호> ");
				int no = scn.nextInt();
				scn.nextLine();
				if (dao.remove(no)) {
					System.out.println("삭제되었습니다.");
				} else {
					System.out.println("해당번호가 없습니다.");
				}
			} else if (menu == 3) {
				System.out.print("글번호> ");
				int no = scn.nextInt();
				scn.nextLine();
				if(dao.noCheck(no)) {
					System.out.println("해당번호가 없습니다.");
					continue;
				}
				System.out.print("글제목> ");
				String title = scn.nextLine();
				System.out.print("글내용> ");
				String content = scn.nextLine();

				BoardVO brd = new BoardVO();
				brd.setBrd_title(title);
				brd.setBrd_content(content);
				brd.setBrd_no(no);

				if (dao.modify(brd)) {
					System.out.println("글이 수정되었습니다.");
				} else {
					System.out.println("글 수정에 실패했습니다.");
				}
			} else if (menu == 4) {
				List<BoardVO> list = dao.list();
				if(list.size()==0) {
					System.out.println("조회결과 없음");
				} else {
					for(BoardVO brd : list) {
						brd.toList();
					}
				}
			} else if (menu == 5) {
				System.out.print("글번호> ");
				int no = scn.nextInt();
				scn.nextLine();
				BoardVO brd = dao.search(no);
				if(brd==null) {
					System.out.println("조회된 글이 없습니다.");
				}else {
					brd.toSearch();
				}
			} else if (menu == 6) {
				System.out.println("종료하겠습니다.");
				break;
			}
		}
		scn.close();
	}
}
