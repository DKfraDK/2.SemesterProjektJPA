package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Mellemvare;
import service.Service;

public class MellemvarePane extends JPanel {

	private Controller controller = new Controller();
	private JButton opretMellemvare_btn, fjernMellemvare_btn;
	private JList mellemvare_list;
	private JTextArea info_txtArea;
	
	/**
	 * Create the panel.
	 */
	public MellemvarePane() {
		setLayout(null);
		
		mellemvare_list = new JList();
		mellemvare_list.setBounds(6, 6, 200, 288);
		mellemvare_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mellemvare_list.setListData(Service.getMellemvarer().toArray());
		add(mellemvare_list);
		mellemvare_list.addListSelectionListener(controller);
		
		fjernMellemvare_btn = new JButton("Fjern mellemvare");
		fjernMellemvare_btn.setBounds(260, 6, 140, 29);
		add(fjernMellemvare_btn);
		fjernMellemvare_btn.addActionListener(controller);
		
		opretMellemvare_btn = new JButton("Opret mellemvare");
		opretMellemvare_btn.setBounds(260, 37, 140, 29);
		add(opretMellemvare_btn);
		opretMellemvare_btn.addActionListener(controller);
		
		info_txtArea = new JTextArea();
		info_txtArea.setEditable(false);
		info_txtArea.setBounds(218, 102, 226, 192);
		add(info_txtArea);
		
		JLabel infoOmValgt_lbl = new JLabel("Info om valgt mellemvare");
		infoOmValgt_lbl.setBounds(233, 74, 160, 16);
		add(infoOmValgt_lbl);
		
	}
	
	private class Controller implements ActionListener, ListSelectionListener{

		private Mellemvare currentSelectedMellemvare;
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			currentSelectedMellemvare = (Mellemvare) mellemvare_list.getSelectedValue();
			info_txtArea.setText(currentSelectedMellemvare.getProdukttype() + "\n" + currentSelectedMellemvare.getProdukttype().getBehandling());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == opretMellemvare_btn){
				new OpretMellemvareDialog(mellemvare_list);
			}else if(e.getSource() == fjernMellemvare_btn){
				Service.deleteMellemvare(currentSelectedMellemvare);
				mellemvare_list.setListData(Service.getMellemvarer().toArray());
			}
		}
		
	}
}
