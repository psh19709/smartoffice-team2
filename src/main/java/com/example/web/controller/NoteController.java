package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoteController {
	
	// 쪽지 화면 요청
		@GetMapping("/note")
		public String note() {
			return "note/receive-list";
		}

}
