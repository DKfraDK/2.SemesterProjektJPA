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

import model.Produkttype;
import service.Service;

public class ProdukttypePane extends JPanel {

	private Controller controller = new Controller();
	private JList produkttype_list;
	private JButton fjernProdukttype_btn, opretProdukttype_btn;
	private JTextArea info_txtArea;
	
	/**
	 * Create the panel.
	 */
	public ProdukttypePane() {
		setLayout(null);
		
		produkttype_list = new JList();
		produkttype_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		produkttype_list.setListData(Service.getProdukttyper().toArray());
		produkttype_list.setBounds(6, 6, 200, 288);
		add(produkttype_list);
		produkttype_list.addListSelectionListener(controller);
	
		fjernProdukttype_btn = new JButton("Fjern produkttype");
		fjernProdukttype_btn.setBounds(260, 6, 140, 29);
		add(fjernProdukttype_btn);
		fjernProdukttype_btn.addActionListener(controller);
		
		opretProdukttype_btn = new JButton("Opret produkttype");
		opretProdukttype_btn.setBounds(260, 37, 140, 29);
		add(opretProdukttype_btn);
		opretProdukttype_btn.addActionListener(controller);
		
		info_txtArea = new JTextArea();
		info_txtArea.setEditable(false);
		info_txtArea.setBounds(218, 102, 226, 192);
		add(info_txtArea);
		
		JLabel infoOmValgt_lbl = new JLabel("Info om valgt produkttype");
		infoOmValgt_lbl.setBounds(239, 74, 177, 16);
		add(infoOmValgt_lbl);
	}
	
	private class Controller implements ActionListener, ListSelectionListener{

		private Produkttype currentSelectedProdukttype;
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			currentSelectedProdukttype = (Produkttype) produkttype_list.getSelectedValue();
			info_txtArea.setText("" + currentSelectedProdukttype.getBehandling());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == opretProdukttype_btn){
				new OpretProdukttypeDialog(produkttype_list);
			}else if(e.getSource() == fjernProdukttype_btn){
				if(Service.deleteProdukttype(currentSelectedProdukttype)){
					produkttype_list.setListData(Service.getProdukttyper().toArray());
				}else{
					JOptionPane.showMessageDialog(null, "Produkttypen du fors¿ger at fjerne er tilknyttet til en mellemvare", "Fejl", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
	}

}
