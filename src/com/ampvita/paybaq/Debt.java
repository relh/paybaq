package com.ampvita.paybaq;

public class Debt {

	private String name;
	private int number;
	private float amount;
	private String reason;
	private int reminders;
	
	public Debt(String name, int number, float amount, String reason, int reminders) {
		this.name = name;
		this.number = number;
		this.amount = amount;
		this.reason = reason;
		this.reminders = reminders;
	}
}
