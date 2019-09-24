package com.zt.exam.po;

public class Option {
	private int id;
	private Question question;
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Option() {

	}

	@Override
	public String toString() {
		return "Option [id=" + id + ", question=" + question + ", content="
				+ content + "]";
	}

	public Option(Question question, String content) {
		this.question = question;
		this.content = content;
	}

}
