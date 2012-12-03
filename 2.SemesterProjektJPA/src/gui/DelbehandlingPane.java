package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Delbehandling;
import service.Service;

public class DelbehandlingPane extends JPanel {

	private Controller controller = new Controller();
	private JList delbehandling_list;
	private JButton fjernDelbehandling_btn, opretDelbehandling_btn;
	
	
	/**
	 * Create the panel.
	 */
	public DelbehandlingPane() {
		setLayout(null);
		
		delbehandling_list = new JList();
		delbehandling_list.setBounds(6, 6, 200, 288);
		delbehandling_list.setListData(Service.getDelbehandlinger().toArray());
		add(delbehandling_list);
		delbehandling_list.addListSelectionListener(controller);
		
		fjernDelbehandling_btn = new JButton("Fjern delbehandling");
		fjernDelbehandling_btn.setBounds(247, 6, 162, 29);
		add(fjernDelbehandling_btn);
		fjernDelbehandling_btn.addActionListener(controller);
		
		opretDelbehandling_btn = new JButton("Opret delbehandling");
		opretDelbehandling_btn.setBounds(247, 46, 162, 29);
		add(opretDelbehandling_btn);
		opretDelbehandling_btn.addActionListener(controller);
		

	}
	
	private class Controller implements ActionListener, ListSelectionListener{

		private Delbehandling currentSelectedDelbehandling;
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getSource() == delbehandling_list){
				currentSelectedDelbehandling = (Delbehandling) delbehandling_list.getSelectedValue();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == opretDelbehandling_btn){
				new OpretDelbehandlingDialog(delbehandling_list);
			}else if(e.getSource() == fjernDelbehandling_btn){
				if(Service.deleteDelBehandling(currentSelectedDelbehandling)){
					delbehandling_list.setListData(Service.getDelbehandlinger().toArray());
				}else{
					JOptionPane.showMessageDialog(null, "Delbehandlingen du fors¿ger at fjerne er tilknyttet til en behandling", "Fejl", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
	}

}
