package com.dmitrypokhodzhay.usermanagement;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import static java.lang.System.out;

public class UserTest extends TestCase {

	private User user;
	private Date dateOfBirthd;
	private ExceptionsHandling exceptions;

	protected void setUp() throws Exception {
		// Initialize of testing class
		user = new User();
		exceptions = new ExceptionsHandling();
	}

	public void testInitUser() {
		// If no one exception
		assertEquals(false, exceptions.checkAnyException(user, true));
	}

	public void testGetFullName() {

		user.setFirstName("Dmitry");
		user.setLastName("Pokhodzhay");

		assertEquals("Dmitry Pokhodzhay", user.getFullName());
	}

	public void testGetAge() {

		Calendar dateOfBirthdCalendar = Calendar.getInstance();
		dateOfBirthdCalendar.set(1996, Calendar.OCTOBER, 16);

		dateOfBirthd = dateOfBirthdCalendar.getTime();

		user.setDateOfBirthd(dateOfBirthd);

		out.println("User" + user.getAge());

		assertEquals(2016 - 1996, user.getAge());
	}
}
