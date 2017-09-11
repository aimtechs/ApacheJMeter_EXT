package kr.co.aim.jmeter.protocol.tibrv;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvMsg;

public class TibrvSender extends TibrvSampler implements Interruptible {

	private static final long serialVersionUID = 5029062524802446398L;

	private static final Logger log = LoggerFactory.getLogger(TibrvSender.class);

	public final static String MESSAGE = "TibrvSender.Message";

	public static final String SEND_REQUEST = "TibrvSender.SendRequest";
	public static final boolean DEFAULT_SEND_REQUEST = false;

	public TibrvSender() {
		super();
	}

	@Override
	public SampleResult sample(Entry e) {
		SampleResult result = new SampleResult();
		result.setSampleLabel(getName());
		result.setSuccessful(false);
		result.setResponseCode("500");

//		try {
//			initTibrvRvdTransport();
//		} catch (TibrvException ex) {
//			log.error("fail initialize TibrvRvdTransport : ", ex);
//			result.setResponseMessage(ex.toString());
//			return result;
//		}

		String data = getMessage();

		result.setSampleLabel(getTitle());

		// result sample start
		result.sampleStart();
		try {
			
			initTibrvRvdTransport();

			TibrvMsg msg = new TibrvMsg();
			msg.setSendSubject(getSubject());
			msg.update(getDataField(), data);
			
			result.setSamplerData(data);
			result.setDataType(SampleResult.TEXT);

			if (getSendRequest()) {
				TibrvMsg response = transport.sendRequest(msg, getSendReqTimeout());
				if (response == null) {
					throw new Exception("fail tib sendRequest. sendRequest timeout : " + getSendReqTimeout());
				}
				else {
					result.setResponseData(response.getField(getDataField()).data.toString(), null);
				}
			} else {
				transport.send(msg);
			}
			
			result.setResponseCodeOK();
			result.setResponseMessage("OK");
			result.setSuccessful(true);

		} catch (TibrvException ex) {
			log.error(ex.getMessage(), ex);
			result.setResponseCode("000");
			result.setResponseMessage(ex.toString());
			result.setSuccessful(false);
		} catch (Exception ee) {
			log.error(ee.getMessage(), ee);
			result.setResponseCode("000");
			result.setResponseMessage(ee.toString());
			result.setSuccessful(false);
		} finally {
			// result sample end
			result.sampleEnd();
			destroyTibrvRvdTransport();
		}

		return result;
	}

	@Override
	public boolean interrupt() {
		return true;
	}

	public String getMessage() {
		return getPropertyAsString(MESSAGE);
	}

	public boolean getSendRequest() {
		return getPropertyAsBoolean(SEND_REQUEST);
	}
}
