
// -service 5420 -daemon tcp:7500 a.b.c
/*
 * tibrvlisten - generic Rendezvous subscriber
 *
 * Examples:
 *
 * Listen to every message published on subject a.b.c:
 *  java tibrvlisten a.b.c
 *
 * Listen to every message published on subjects a.b.c and x.*.Z:
 *  java tibrvlisten a.b.c "x.*.Z"
 *
 * Listen to every system advisory message:
 *  java tibrvlisten "_RV.*.SYSTEM.>"
 *
 * Listen to messages published on subject a.b.c using port 7566:
 *  java tibrvlisten -service 7566 a.b.c
 *
 */

import java.util.Date;
import java.util.Random;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgCallback;
import com.tibco.tibrv.TibrvRvdTransport;
import com.tibco.tibrv.TibrvTransport;

public class tibrvlisten implements TibrvMsgCallback {

	String service = null;
	String network = null;
	String daemon = null;

	TibrvTransport transport = null;

	public tibrvlisten(String args[]) {
		// parse arguments for possible optional
		// parameters. These must precede the subject
		// and message strings
		int i = get_InitParams(args);

		// we must have at least one subject
		if (i >= args.length)
			usage();

		// open Tibrv in native implementation
		try {
			Tibrv.open(Tibrv.IMPL_NATIVE);
		} catch (TibrvException e) {
			System.err.println("Failed to open Tibrv in native implementation:");
			e.printStackTrace();
			System.exit(0);
		}

		// Create RVD transport

		try {
			transport = new TibrvRvdTransport(service, network, daemon);
		} catch (TibrvException e) {
			System.err.println("Failed to create TibrvRvdTransport:");
			e.printStackTrace();
			System.exit(0);
		}

		// Create listeners for specified subjects
		while (i < args.length) {
			// create listener using default queue
			try {
				new TibrvListener(Tibrv.defaultQueue(), this, transport, args[i], null);
				System.err.println("Listening on: " + args[i]);
			} catch (TibrvException e) {
				System.err.println("Failed to create listener:");
				e.printStackTrace();
				System.exit(0);
			}
			i++;
		}

		// dispatch Tibrv events
		while (true) {
			try {
				Tibrv.defaultQueue().dispatch();
			} catch (TibrvException e) {
				System.err.println("Exception dispatching default queue:");
				e.printStackTrace();
				System.exit(0);
			} catch (InterruptedException ie) {
				System.exit(0);
			}
		}
	}
	
	Random rnd = new Random();

	public void onMsg(TibrvListener listener, TibrvMsg msg) {
		System.out.println((new Date()).toString() + ": subject=" + msg.getSendSubject() + ", reply="
				+ msg.getReplySubject() + ", message=" + msg.toString());
		System.out.flush();

		TibrvMsg reply = new TibrvMsg();
		try {

			String data = "";
			if (msg.toString().contains("lot3")) {
				data = "<data>" + 
									msg.get("xmlData").toString() + 
									"<RD>" + "data_" + rnd.nextInt(100) + "</RD>" + 
									"<response>FAIL</response>" + 
								"</data>";
			} else {
				data = "<data>" + 
									msg.get("xmlData").toString() + 
									"<RD>" + "data_" + rnd.nextInt(100) + "</RD>" + 
									"<response>OK</response>" + 
								"</data>";
			}

			reply.setReplySubject(msg.getReplySubject());
			reply.update("xmlData", data);

			transport.sendReply(reply, msg);
			System.out.println(reply);

		} catch (TibrvException e) {
			e.printStackTrace();
		}

	}

	// print usage information and quit
	void usage() {
		System.err.println("Usage: java tibrvlisten [-service service] [-network network]");
		System.err.println("            [-daemon daemon] <subject-list>");
		System.exit(-1);
	}

	int get_InitParams(String[] args) {
		int i = 0;
		while (i < args.length - 1 && args[i].startsWith("-")) {
			if (args[i].equals("-service")) {
				service = args[i + 1];
				i += 2;
			} else if (args[i].equals("-network")) {
				network = args[i + 1];
				i += 2;
			} else if (args[i].equals("-daemon")) {
				daemon = args[i + 1];
				i += 2;
			} else
				usage();
		}
		return i;
	}

	public static void main(String args[]) {
		new tibrvlisten(args);
	}

}
