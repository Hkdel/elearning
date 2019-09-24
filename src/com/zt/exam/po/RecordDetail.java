package com.zt.exam.po;

public class RecordDetail {
	private int id;
	private Record record;
	private Question question;
	private String questionAnswer;
	private String answer;
	private Double score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public RecordDetail() {

	}

	public RecordDetail(Record record, Question question, String questionAnswer,
			String answer, Double score) {
		this.record = record;
		this.question = question;
		this.questionAnswer = questionAnswer;
		this.answer = answer;
		this.score = score;
	}

}
