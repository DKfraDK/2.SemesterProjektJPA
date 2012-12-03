package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Delbehandling;
import model.Produkttype;
import service.Service;

public class OpretMellemvareDialog extends JDialog {

	private JList mellemvare_list, produkttype_list;
	private JTextField navn_txt;
	private JButton ok_btn, cancel_btn;
	
	private Controller controller = new Controller();

	/**
	 * Create the dialog.
	 */
	public OpretMellemvareDialog(JList mellemvare_list) {
		this.mellemvare_list = mellemvare_list;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel navn_lbl= new JLabel("Navn: ");
		navn_lbl.setBounds(43, 6, 40, 16);
		getContentPane().add(navn_lbl);
		
		navn_txt = new JTextField();
		navn_txt.setBounds(95, 0, 134, 28);
		getContentPane().add(navn_txt);
		navn_txt.setColumns(10);
		navn_txt.addActionListener(controller);
		
		produkttype_list = new JList();
		produkttype_list.setBounds(6, 62, 200, 210);
		produkttype_list.setListData(Service.getProdukttyper().toArray());
		getContentPane().add(produkttype_list);
		produkttype_list.addListSelectionListener(controller);
		
		JLabel lblNewLabel = new JLabel("V\u00E6lg produkttype");
		lblNewLabel.setBounds(29, 34, 177, 16);
		getContentPane().add(lblNewLabel);
		
		ok_btn = new JButton("Ok");
		ok_btn.setBounds(268, 57, 117, 29);
		getContentPane().add(ok_btn);
		ok_btn.addActionListener(controller);
		
		cancel_btn = new JButton("Cancel");
		cancel_btn.setBounds(268, 101, 117, 29);
		getContentPane().add(cancel_btn);
		cancel_btn.addActionListener(controller);
		
	}
	
	private class Controller implements ActionListener, ListSelectionListener{

		private Produkttype currentSelectedProdukttype;
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getSource() == produkttype_list){
				currentSelectedProdukttype = (Produkttype) produkttype_list.getSelectedValue();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == ok_btn){
				if(navn_txt.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Indtast navn", "Fejl", JOptionPane.ERROR_MESSAGE);
				}else{
					Service.createMellemvare(navn_txt.getText(), currentSelectedProdukttype, Service.getDag());
					mellemvare_list.setListData(Service.getMellemvarer().toArray());
					dispose();
				}
			}else if(e.getSource() == cancel_btn){
				dispose();
			}	
		}
	
	}
}
