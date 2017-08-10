package kr.co.aim.jmeter.protocol.tibrv;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.testelement.ThreadListener;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvRvdTransport;
import com.tibco.tibrv.TibrvTransport;

@SuppressWarnings("serial")
public abstract class TibrvSampler extends AbstractSampler implements ThreadListener, Interruptible {

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
	@Override
	public void threadStarted() {
	}
	
	@Override
	public void threadFinished() {
		destroy();
	}
	
	@Override
	public boolean interrupt() {
		destroy();
		return true;
	}
	
	private void destroy() {
		if (transport != null && transport.isValid()) {
			transport.destroy();
			transport = null;
		}
	}
}