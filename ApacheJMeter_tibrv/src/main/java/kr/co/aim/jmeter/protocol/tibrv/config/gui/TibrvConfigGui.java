package kr.co.aim.jmeter.protocol.tibrv.config.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;

import kr.co.aim.jmeter.protocol.tibrv.TibrvSampler;

public class TibrvConfigGui extends AbstractConfigGui {

	private static final long serialVersionUID = 4794622004325101956L;
	
	protected JLabeledTextField service = new JLabeledTextField("Service");
	protected JLabeledTextField network = new JLabeledTextField("Network");
	protected JLabeledTextField daemon = new JLabeledTextField("Daemon");
	protected JLabeledTextField subject = new JLabeledTextField("Subject");

	protected JLabeledTextField dataField = new JLabeledTextField("DataField");
	protected JLabeledTextField sendReqTimeout = new JLabeledTextField("SendReqTimeout");
//	protected final JCheckBox local = new JCheckBox("_Local", TibrvSampler.DEFAULT_LOCAL);

	private boolean displayName = true;

	public TibrvConfigGui() {
		this(true);
	}

	public TibrvConfigGui(boolean displayName) {
		this.displayName = displayName;
		init();
	}

	@Override
	public String getLabelResource() {
		return "Tibrv Config Defaults";
	}

	@Override
	public String getStaticLabel() {
		return "Tibrv Config Defaults";
	}

	private void init() {
		setLayout(new BorderLayout(0, 5));

		if (displayName) {
			setBorder(makeBorder());
			add(makeTitlePanel(), BorderLayout.NORTH);
		}

		JPanel mainPanel = new VerticalPanel();
		mainPanel.add(makeCommonPanel());
		add(mainPanel);
	}

	@Override
	public void configure(TestElement element) {
		service.setText(element.getPropertyAsString(TibrvSampler.SERVICE));
		network.setText(element.getPropertyAsString(TibrvSampler.NETWORK));
		daemon.setText(element.getPropertyAsString(TibrvSampler.DAEMON));
		subject.setText(element.getPropertyAsString(TibrvSampler.SUBJECT));

		dataField.setText(element.getPropertyAsString(TibrvSampler.DATA_FIELD));
		sendReqTimeout.setText(element.getPropertyAsString(TibrvSampler.SEND_REQ_TIMEOUT));
//		local.setSelected(element.getPropertyAsBoolean(TibrvSampler.LOCAL));
	}

	@Override
	public TestElement createTestElement() {
		ConfigTestElement element = new ConfigTestElement();
		modifyTestElement(element);
		return element;
	}

	@Override
	public void modifyTestElement(TestElement element) {
		configureTestElement(element);

		element.setProperty(TibrvSampler.SERVICE, service.getText());
		element.setProperty(TibrvSampler.NETWORK, network.getText());
		element.setProperty(TibrvSampler.DAEMON, daemon.getText());
		element.setProperty(TibrvSampler.SUBJECT, subject.getText());

		element.setProperty(TibrvSampler.DATA_FIELD, dataField.getText());
		element.setProperty(TibrvSampler.SEND_REQ_TIMEOUT, sendReqTimeout.getText());
//		element.setProperty(TibrvSampler.LOCAL, local.isSelected());
	}

	@Override
	public void clearGui() {

		service.setText("");
		network.setText("");
		daemon.setText("");
		subject.setText("");

		dataField.setText(TibrvSampler.DEFAULT_DATA_FIELD);
		sendReqTimeout.setText(TibrvSampler.DEFAULT_SEND_REQ_TIMEOUT_STRING);
//		local.setSelected(TibrvSampler.DEFAULT_LOCAL);
	}

	public JPanel makeCommonPanel() {
		GridBagConstraints gridBagConstraints, gridBagConstraintsCommon;

		gridBagConstraintsCommon = new GridBagConstraints();
		gridBagConstraintsCommon.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsCommon.anchor = GridBagConstraints.WEST;
		gridBagConstraintsCommon.weightx = 0.5;

		gridBagConstraintsCommon.gridx = 0;
		gridBagConstraintsCommon.gridy = 0;

		// -----------------
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.weightx = 0.5;

		JPanel serverSettings = new JPanel(new GridBagLayout());
		serverSettings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Connection"));

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		serverSettings.add(service, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		serverSettings.add(network, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		serverSettings.add(daemon, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		serverSettings.add(subject, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		serverSettings.add(dataField, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		serverSettings.add(sendReqTimeout, gridBagConstraints);

		// gridBagConstraints.gridx = 1;
		// gridBagConstraints.gridy = 3;
		// serverSettings.add(local, gridBagConstraints);

		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
		jPanel.add(serverSettings, gridBagConstraintsCommon);
		return jPanel;

	}

}
