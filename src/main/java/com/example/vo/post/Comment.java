package com.example.vo.post;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Comment {

	private int no;
	private int employeeNo;
	private int postNo;
	private String content;
	private int recommendCount;
	private String deleted;
	private Date createdDate;
	private Date updatedDate;
	
	@Builder
	private Comment(int no, int employeeNo, int postNo, String content) {
		super();
		this.no = no;
		this.employeeNo = employeeNo;
		this.postNo = postNo;
		this.content = content;
	}
	
	public void modifyContent(String content) {
		this.content = content;
	}
	
	public void increaseRecommendCount() {
		this.recommendCount += 1;
	}
	
	public void decreaseRecommendCount() {
		this.recommendCount += 1;
	}
	
	public void deleteComment() {
		this.deleted = "Y";
	}
	
	public void recoverComment() {
		this.deleted = "N";
	}
	
}
