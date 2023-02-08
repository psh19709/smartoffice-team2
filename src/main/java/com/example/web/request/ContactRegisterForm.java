package com.example.web.request;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRegisterForm {
	
	@NotBlank(message = "이름을 입력해주세요.")
	@Length(max = 20, message = "이름은 20글자 이하로 입력하세요.")
	private String name;
	
	private String share;
	
	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "유효한 이메일 형식이 아닙니다.")
	private String email;
	
	private Date birth;
	private String company;
	private String dept;
	private String position;
	private String address;
	private String photo;
	private String url;
	private String memo;

}
