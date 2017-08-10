package kr.co.aim.jmeter.protocol.tibrv.control.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JCheckBox;

import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextArea;

import kr.co.aim.jmeter.protocol.tibrv.TibrvSender;
import kr.co.aim.jmeter.protocol.tibrv.config.gui.TibrvConfigGui;

public class TibrvSenderGui extends AbstractSamplerGui {

	private static final long serialVersionUID = 5459829914052346013L;

	private TibrvConfigGui tibrvDefaultGui;

	// ===
	private JLabeledTextArea message = new JLabeledTextArea("Message Content");
	private JCheckBox sendRequest = new JCheckBox("SendRequest", TibrvSender.DEFAULT_SEND_REQUEST);

	public TibrvSenderGui() {
		init();
	}

	@Override
	public String getLabelResource() {
		return "Tibrv Sender";
	}
	
	@Override
	public String getStaticLabel() {
		return "Tibrv Sender";
	}

	@Override
	public void configure(TestElement element) {
		super.configure(element);
		
		tibrvDefaultGui.configure(element);
		message.setText(element.getPropertyAsString(TibrvSender.MESSAGE));
		sendRequest.setSelected(element.getPropertyAsBoolean(TibrvSender.SEND_REQUEST));
	}

	@Override
	public TestElement createTestElement() {
		TibrvSender sampler = new TibrvSender();
		modifyTestElement(sampler);
		return sampler;
	}

	@Override
	public void modifyTestElement(TestElement sampler) {
		sampler.clear();
		
		tibrvDefaultGui.modifyTestElement(sampler);
		sampler.setProperty(TibrvSender.MESSAGE, message.getText());
		sampler.setProperty(TibrvSender.SEND_REQUEST, sendRequest.isSelected());
		
		super.configureTestElement(sampler);
	}

	@Override
	public void clearGui() {
		super.clearGui();
		
		tibrvDefaultGui.clearGui();
		message.setText("");
		sendRequest.setSelected(TibrvSender.DEFAULT_SEND_REQUEST);
	}
	
	private void init() {

		setLayout(new BorderLayout(0, 5));
		setBorder(makeBorder());

		add(makeTitlePanel(), BorderLayout.NORTH);

		VerticalPanel mainPanel = new VerticalPanel();

		tibrvDefaultGui = new TibrvConfigGui(false);
		mainPanel.add(tibrvDefaultGui);

		
		mainPanel.add(sendRequest);
		
		message.setPreferredSize(new Dimension(400, 500));
		mainPanel.add(message);
		
//		System.out.println( message.getPreferredSize() );
//		System.out.println( mainPanel.getPreferredSize().getSize());
//		System.out.println( mainPanel.getPreferredSize().getHeight());

		add(mainPanel, BorderLayout.CENTER);
	}
}