package com.dmitrypokhodzhay.usermanagement;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Pokhodzhay
 * @serial 2016_10_31_001L
 */

/*
 * Contains just a little set of methods for handling exceptions while testing
 * objects...
 */

class MapObject {
	public MapObject(Object object, String message) {
		if (object != null)
			this.object = object;

		if (message != null)
			this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public String getMessage() {
		return message;
	}

	private Object object;
	private String message;

}

public class ExceptionsHandling implements Runnable {

	private static final long serialVersionUID = 2016_10_31_001L;
	/*
	 * Map contains link to the object where exception happens (Object) and
	 * exception info (String) from method "getLocalizedMessage" of Exception
	 * class.
	 */
	private static Map<Object, String> lastCheckedMap;

	// Contains full information about last exception checked by one of methods
	// of this class.
	private Exception lastExecption;
	private boolean initialized;

	public ExceptionsHandling() {

		initialized = !stubAnyException(this, false, false);
	}

	/*
	 * To see exceptions that happens set appropriate parameter to true To copy
	 * info about last exception, set appropriate parameter to true
	 */
	public boolean stubAnyException(Runnable runnable, boolean output, boolean writeLastExceptionInfo) {

		if (!initialized) {
			return true;
		}

		try {
			runnable.run();
		} catch (Exception e) {

			if (output)
				out.println(e.getLocalizedMessage());

			/*
			 * Copying exception class for later access to full info about last
			 * exception...
			 */
			if (writeLastExceptionInfo)
				setLastExecption(new Exception(e));

			e = null;
			return true;
		}
		return false;
	}

	/*
	 * Creates map of link to objects and their exception messages those happens
	 * in received method (one message per object). Can show detailed info about
	 * exceptions.
	 */
	public boolean checkAnyException(Runnable runnable, boolean output) {

		try {
			runnable.run();
		} catch (Exception e) {

			/*
			 * Copying exception class for later access to full info about last
			 * exception...
			 */
			setLastExecption(new Exception(e));

			String messageCopy = new String(e.getLocalizedMessage());
			lastCheckedMap.put(e.getClass(), messageCopy);

			if (output) {
				out.println("Testing class: " + e.getClass().getName());
				out.println("Execption: " + e.getMessage());
			}

			return true;
		}

		initialized = true;

		if (!initialized) {
			out.println(this.toString() + ": error, exception handler uninitialized.");
			return true;
		}

		return false;
	}

	public Exception getLastExecption() {
		return lastExecption;
	}

	private void setLastExecption(Exception lastExecption) {
		this.lastExecption = lastExecption;
	}

	public long getSerialversionUID() {
		return serialVersionUID;
	}

	static public MapObject getLastExceptionMapEntry() {
		return new MapObject((Object) lastCheckedMap.entrySet().toArray()[0],
				(String) lastCheckedMap.entrySet().toArray()[1]);
	}

	/*
	 * All objects that will be work with ExecptionsHandling class must override
	 * "run" and use as main method (initialization method). For compatibility
	 * with ExceptionsHandling class.
	 */
	@Override
	public void run() {
		lastCheckedMap = new HashMap<Object, String>();
		lastCheckedMap.clear();
		setLastExecption(null);
	}
}
