package com.dmitrypokhodzhay.usermanagement;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Dmitry Pokhodzhay
 * @serial 2016_10_31_003L
 */

/*
 * First class in the project.
 */
public class User implements Runnable {

	private static final long serialVersionUID = 2016_10_31_003L;

	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirthd;

	protected Date current;

	public void updateObject() {
		current = new Date();
	}

	public User() {
		super();
		updateObject();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirthd() {
		return dateOfBirthd;
	}

	public void setDateOfBirthd(Date dateOfBirthd) {
		this.dateOfBirthd = dateOfBirthd;
	}

	public String getFullName() {

		return getFirstName().concat(" ").concat(getLastName());
	}

	public int getAge() {
		Calendar dateOfBirthdCalendar = Calendar.getInstance();
		dateOfBirthdCalendar.setTime(dateOfBirthd);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		return calendar.get(Calendar.YEAR) - dateOfBirthdCalendar.get(Calendar.YEAR);
	}

	public long getSerialVersionUID() {
		return serialVersionUID;
	}

	// For compatibility with ExceptionsHandling class
	@Override
	public void run() {
		// TODO Auto-generated method stub
		updateObject();
	}
}
