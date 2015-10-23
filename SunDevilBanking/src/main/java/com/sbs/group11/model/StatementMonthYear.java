package com.sbs.group11.model;


/**
 * StatementMonthYear: This is not a hibernate model! Just a helper Pojo
 */
public class StatementMonthYear {

	public StatementMonthYear(String month, String year) {
		super();
		this.month = month;
		Year = year;
	}

	/** The month. */
	private String month;
	
	/** The Year. */
	private String Year;

	public String getMonth() {
		return month;
	}

	/**
	 * Sets the month.
	 *
	 * @param month the new month
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public String getYear() {
		return Year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(String year) {
		Year = year;
	}

	@Override
	public String toString() {
		return "StatementMonthYear [month=" + month + ", Year=" + Year + "]";
	}
}
