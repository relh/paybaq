package com.ampvita.paybaq;

public class Debt {

	private String name;
	private String number;
	private String amount;
	private String reason;
	private int reminders;
	
	public Debt(String name, String number, String amount, String reason, int reminders) {
		this.name = name;
		this.number = number;
		this.amount = amount;
		this.reason = reason;
		this.reminders = reminders;
	}
}
