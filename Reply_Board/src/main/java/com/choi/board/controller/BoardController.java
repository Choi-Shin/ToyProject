package com.choi.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.choi.board.common.Board;

@Controller
public class BoardController {
	
	public List<Board> 게시판목록수집하다() {
		List<Board> 목록 = new ArrayList<Board>();
		return 목록;
	}
}
