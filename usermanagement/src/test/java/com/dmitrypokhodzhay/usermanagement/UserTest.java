package com.dmitrypokhodzhay.usermanagement;

import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;

public class UserTest extends TestCase {

	private User user;
	private Date dateOfBirthd;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		user = new User();
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
		
		int age = user.getAge();
		
		assertEquals(2016 - 1996, user.getAge());
		
		System.out.println(age);
	}

}
