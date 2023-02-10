package com.example.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentListDto {

	private String name;
	private String content;
	private int recommendCount;
	private Date createdDate;
}