package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Behandling;
import model.Delbehandling;
import service.Service;

public class OpretBehandlingDialog extends JDialog {
	
	private JTextField navn_txt;
	private Controller controller = new Controller();
	private JList delbehandlinger_list, valgteDelbehandlinger_list, behandling_list;
	private JButton add_btn, remove_btn, ok_btn, cancel_btn;

	/**
	 * Create the dialog.
	 */
	public OpretBehandlingDialog(JList behandling_list) {
		this.behandling_list = behandling_list;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		delbehandlinger_list = new JList();
		delbehandlinger_list.setBounds(6, 59, 158, 168);
		getContentPane().add(delbehandlinger_list);
		delbehandlinger_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		delbehandlinger_list.setListData(Service.getDelbehandlinger().toArray());
		delbehandlinger_list.addListSelectionListener(controller);
		
		JLabel navn_lbl = new JLabel("Navn:");
		navn_lbl.setBounds(140, 6, 44, 16);
		getContentPane().add(navn_lbl);
		
		navn_txt = new JTextField();
		navn_txt.setBounds(187, 0, 134, 28);
		getContentPane().add(navn_txt);
		navn_txt.setColumns(10);
		
		valgteDelbehandlinger_list = new JList();
		valgteDelbehandlinger_list.setBounds(286, 59, 158, 168);
		getContentPane().add(valgteDelbehandlinger_list);
		valgteDelbehandlinger_list.addListSelectionListener(controller);
		
		ok_btn = new JButton("Ok");
		ok_btn.setBounds(95, 239, 117, 29);
		getContentPane().add(ok_btn);
		ok_btn.addActionListener(controller);
		
		cancel_btn = new JButton("Cancel");
		cancel_btn.setBounds(241, 239, 117, 29);
		getContentPane().add(cancel_btn);
		cancel_btn.addActionListener(controller);
		
		add_btn = new JButton("-->");
		add_btn.setBounds(176, 93, 98, 29);
		getContentPane().add(add_btn);
		add_btn.addActionListener(controller);
		
		remove_btn = new JButton("<--");
		remove_btn.setBounds(176, 122, 98, 29);
		getContentPane().add(remove_btn);
		remove_btn.addActionListener(controller);
		
		JLabel delbehandlinger_lbl = new JLabel("Delbehandlinger");
		delbehandlinger_lbl.setBounds(16, 31, 117, 16);
		getContentPane().add(delbehandlinger_lbl);
		
		JLabel valgteDelbehandlinger_lbl = new JLabel("Tilf\u00F8jede delbahendlinger");
		valgteDelbehandlinger_lbl.setBounds(272, 31, 172, 16);
		getContentPane().add(valgteDelbehandlinger_lbl);
	}
	
	private class Controller implements ActionListener, ListSelectionListener{
		
		private Delbehandling currentSelectedDelbehandlingFraDelbehandlinger;
		private Delbehandling currentSelectedDelbehandlingFraValgteDelbehandlinger;
		private ArrayList<Delbehandling> valgteDelbehandlinger = new ArrayList<Delbehandling>();
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getSource() == delbehandlinger_list){
				currentSelectedDelbehandlingFraDelbehandlinger = (Delbehandling) delbehandlinger_list.getSelectedValue();
			}else if(e.getSource() == valgteDelbehandlinger_list){
				currentSelectedDelbehandlingFraValgteDelbehandlinger = (Delbehandling) valgteDelbehandlinger_list.getSelectedValue();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == add_btn){
				valgteDelbehandlinger.add(currentSelectedDelbehandlingFraDelbehandlinger);
				valgteDelbehandlinger_list.setListData(valgteDelbehandlinger.toArray());
			}else if(e.getSource() == remove_btn){
				valgteDelbehandlinger.remove(currentSelectedDelbehandlingFraValgteDelbehandlinger);
				valgteDelbehandlinger_list.setListData(valgteDelbehandlinger.toArray());
			}else if(e.getSource() == ok_btn){
				if(navn_txt.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Indtast navn", "Fejl", JOptionPane.ERROR_MESSAGE);
				}else{
					Behandling b = Service.createBehandling(navn_txt.getText());
					for(Delbehandling d : valgteDelbehandlinger){
						Service.addDelbehandlingTilBehandling(b, d);
					}
					behandling_list.setListData(Service.getBehandling().toArray());
					dispose();
				}
			}else if(e.getSource() == cancel_btn){
				dispose();
			}
		}
		
	}
}
