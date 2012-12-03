package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Behandling;
import model.Delbehandling;
import service.Service;

public class BehandlingPane extends JPanel {

	private Controller controller = new Controller();
	private JButton fjernBehandling_btn, opretBehandling_btn;
	private JList behandling_list;
	private JTextArea info_txtArea;
	
	/**
	 * Create the panel.
	 */
	public BehandlingPane() {
		setLayout(null);
				
		behandling_list = new JList();
		behandling_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		behandling_list.setListData(Service.getBehandling().toArray());
		behandling_list.setBounds(6, 6, 200, 288);
		add(behandling_list);
		behandling_list.addListSelectionListener(controller);
		
		fjernBehandling_btn = new JButton("Fjern behandling");
		fjernBehandling_btn.setBounds(260, 6, 140, 29);
		add(fjernBehandling_btn);
		fjernBehandling_btn.addActionListener(controller);
		
		opretBehandling_btn = new JButton("Opret behandling");
		opretBehandling_btn.setBounds(260, 37, 140, 29);
		add(opretBehandling_btn);
		opretBehandling_btn.addActionListener(controller);
		
		info_txtArea = new JTextArea();
		info_txtArea.setEditable(false);
		info_txtArea.setBounds(218, 102, 226, 192);
		add(info_txtArea);
		
		JLabel infoOmValgt_lbl = new JLabel("Info om valgt behandling");
		infoOmValgt_lbl.setBounds(239, 74, 177, 16);
		add(infoOmValgt_lbl);

	}
	
	private class Controller implements ActionListener, ListSelectionListener{

		private Behandling currentSelectedBehandling;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == fjernBehandling_btn){
				if(Service.deleteBehandling(currentSelectedBehandling)){
					behandling_list.setListData(Service.getBehandling().toArray());
				}else{
					JOptionPane.showMessageDialog(null, "Behandlingen du fors¿ger at fjerne er tilknyttet til en produkttype", "Fejl", JOptionPane.ERROR_MESSAGE);
				}
			}else if(e.getSource() == opretBehandling_btn){
				new OpretBehandlingDialog(behandling_list);
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			currentSelectedBehandling = (Behandling) behandling_list.getSelectedValue();
			info_txtArea.setText("");
			for(Delbehandling d : currentSelectedBehandling.getDelbehandlinger()){
				info_txtArea.setText(info_txtArea.getText() + d + "\n");
			}
		}

	}

}
