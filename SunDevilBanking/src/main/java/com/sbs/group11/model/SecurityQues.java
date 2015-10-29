package com.sbs.group11.model;
	

import org.joda.time.LocalDateTime;
	
	
	public class SecurityQues {
		
		public SecurityQues()
		{
		
		}
	
		public SecurityQues(String email, String question1, String answer1,
				String question2, String answer2, String question3, String answer3,
				LocalDateTime updatedAt) {
			super();
			this.email = email;
			this.question1 = question1;
			this.answer1 = answer1;
			this.question2 = question2;
			this.answer2 = answer2;
			this.question3 = question3;
			this.answer3 = answer3;
			this.updatedAt = updatedAt;
		}
	
	
	
		
	private String email;
	
	
	private String question1;
	
	private String answer1;
	
	
	private String question2;
	
	
	private String answer2;
	
	private String question3;
	
	
	private String answer3;
	
	
	
	private LocalDateTime updatedAt;
	
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getQuestion1() {
		return question1;
	}
	
	public void setQuestion1(String question1) {
		this.question1 = question1;
	}
	
	public String getQuestion2() {
		return question2;
	}
	
	public void setQuestion2(String question2) {
		this.question2 = question2;
	}
	
	public String getQuestion3() {
		return question3;
	}
	
	public void setQuestion3(String question3) {
		this.question3 = question3;
	}
	
	public String getAnswer1() {
		return answer1;
	}
	
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	
	public String getAnswer2() {
		return answer2;
	}
	
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	
	public String getAnswer3() {
		return answer3;
	}
	
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	@Override
	public String toString() {
		return "SecurityQues [ email="
				+ email + ", question1=" + question1 + ", answer1=" + answer1
				+ ", question2=" + question2 + ", answer2=" + answer2
				+ ", question3=" + question3 + ", answer3=" + answer3
				+ ", updatedAt=" + updatedAt + "]";
		}
	
	}
	
