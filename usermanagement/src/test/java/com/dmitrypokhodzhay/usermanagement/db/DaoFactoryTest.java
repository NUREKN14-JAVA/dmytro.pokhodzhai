package com.dmitrypokhodzhay.usermanagement.db;

import static org.junit.Assert.*;

import junit.framework.TestCase;
public class DaoFactoryTest extends TestCase{


	public void testGetUserDao() {
		try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			assertNotNull("DaoFactoryinstance is null", daoFactory);
			UserDao userDao = daoFactory.getUserDao();
			assertNotNull("UserDao instance is null", userDao);
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}

}
