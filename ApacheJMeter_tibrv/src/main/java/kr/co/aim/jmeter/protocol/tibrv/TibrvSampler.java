package kr.co.aim.jmeter.protocol.tibrv;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Interruptible;
//import org.apache.jmeter.testelement.TestStateListener;
//import org.apache.jmeter.testelement.ThreadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvRvdTransport;
import com.tibco.tibrv.TibrvTransport;

@SuppressWarnings("serial")
public abstract class TibrvSampler extends AbstractSampler implements Interruptible { // ,
																																											// ThreadListener,
																																											// TestStateListener
																																											// {
	private static final Logger log = LoggerFactory.getLogger(TibrvSampler.class);
	
	public static final String SERVICE = "TibrvSample.SERVICE";
	public static final String NETWORK = "TibrvSample.NETWORK";
	public static final String DAEMON = "TibrvSample.DAEMON";
	public static final String SUBJECT = "TibrvSample.SUBJECT";

	public static final String DATA_FIELD = "TibrvSample.DataField";
	public static final String DEFAULT_DATA_FIELD = "xmlData";

	public static final String SEND_REQ_TIMEOUT = "TibrvSample.Timeout";
	public static final double DEFAULT_SEND_REQ_TIMEOUT = 0;
	public static final String DEFAULT_SEND_REQ_TIMEOUT_STRING = Double.toString(DEFAULT_SEND_REQ_TIMEOUT);

	public static final String LOCAL = "TibrvSampler.Local";
	public static final boolean DEFAULT_LOCAL = false;

	protected transient TibrvTransport transport = null;

	public TibrvSampler() {
	}

	protected void initTibrvRvdTransport() throws TibrvException {
		Tibrv.open(Tibrv.IMPL_NATIVE);
		transport = new TibrvRvdTransport(getService(), getNetwork(), getDaemon());
	}

	protected void destroyTibrvRvdTransport() {
		// System.out.println("destroy " + Thread.currentThread().getName() + " " +
		// transport.hashCode());
		if (transport != null && transport.isValid()) {
			transport.destroy();
			transport = null;

			try {
				Tibrv.close();
			} catch (TibrvException e) {
				log.error("Tibrv.close Error.", e);
			}
		}
	}

	protected String getTitle() {
		return this.getName();
	}

	public String getService() {
		return getPropertyAsString(SERVICE);
	}

	public String getNetwork() {
		return getPropertyAsString(NETWORK);
	}

	public String getDaemon() {
		return getPropertyAsString(DAEMON);
	}

	public String getSubject() {
		return getPropertyAsString(SUBJECT);
	}

	// -------------------------
	public String getDataField() {

		String df = getPropertyAsString(DATA_FIELD, DEFAULT_DATA_FIELD);

		if (df == null || "".equals(df)) {
			return DEFAULT_DATA_FIELD;
		}

		return df;
	}

	public double getSendReqTimeout() {
		return getPropertyAsDouble(SEND_REQ_TIMEOUT);
	}

	public boolean getLocal() {
		return getPropertyAsBoolean(LOCAL);
	}

	// --------------------------------------------------------
	// @Override
	// public void threadStarted() {
	// System.out.println("threadStarted " + Thread.currentThread().getName());
	// }
	//
	// @Override
	// public void threadFinished() {
	// destroy();
	// }
	//
	// @Override
	// public boolean interrupt() {
	// System.out.println("interrupt " + Thread.currentThread().getName() + " " +
	// transport.hashCode());
	// destroy();
	// return true;
	// }

	// --------------------------------------------------------
	// @Override
	// public void testStarted() {
	// System.out.println("testStarted " + Thread.currentThread().getName() + " "
	// + transport);
	// }
	//
	// @Override
	// public void testStarted(String host) {
	// System.out.println("testStarted(String host) " +
	// Thread.currentThread().getName() + " " + transport.hashCode());
	// }
	//
	// @Override
	// public void testEnded() {
	// System.out.println("testEnded " + Thread.currentThread().getName() + " " +
	// transport);
	// }
	//
	// @Override
	// public void testEnded(String host) {
	// System.out.println("testEnded(String host) " +
	// Thread.currentThread().getName() + " " + transport.hashCode());
	// }

}
