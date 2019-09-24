package com.zt.exam.po;

import java.util.List;

public class ExamQuestion {
	private Question question;
	private List<Option> options;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

}
