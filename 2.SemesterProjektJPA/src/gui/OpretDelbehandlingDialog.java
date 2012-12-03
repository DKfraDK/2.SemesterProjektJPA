package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import service.Service;

public class OpretDelbehandlingDialog extends JDialog {

	private JList delbehandling_list;
	private JTextField navn_txt;
	private Controller controller = new Controller();
	private JButton ok_btn, cancel_btn;
	private JLabel min_lbl, ideal_lbl, max_lbl;
	private JTextField min_txt, ideal_txt, max_txt;
	
	/**
	 * Create the dialog.
	 */
	public OpretDelbehandlingDialog(JList delbehandling_list) {
		this.delbehandling_list = delbehandling_list;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		getContentPane().setLayout(null);
		setBounds(100, 100, 450, 300);
		
		JLabel navn_lbl = new JLabel("Navn:");
		navn_lbl.setBounds(18, 12, 44, 16);
		getContentPane().add(navn_lbl);
		
		navn_txt = new JTextField();
		navn_txt.setBounds(102, 6, 134, 28);
		getContentPane().add(navn_txt);
		navn_txt.setColumns(10);
		
		ok_btn = new JButton("Ok");
		ok_btn.setBounds(95, 239, 117, 29);
		getContentPane().add(ok_btn);
		ok_btn.addActionListener(controller);
		
		cancel_btn = new JButton("Cancel");
		cancel_btn.setBounds(241, 239, 117, 29);
		getContentPane().add(cancel_btn);
		cancel_btn.addActionListener(controller);
		
		min_lbl = new JLabel("Min");
		min_lbl.setBounds(18, 52, 61, 16);
		getContentPane().add(min_lbl);
		
		ideal_lbl = new JLabel("Ideal");
		ideal_lbl.setBounds(18, 92, 61, 16);
		getContentPane().add(ideal_lbl);
		
		max_lbl = new JLabel("Max");
		max_lbl.setBounds(18, 132, 61, 16);
		getContentPane().add(max_lbl);
		
		min_txt = new JTextField();
		min_txt.setBounds(102, 46, 134, 28);
		getContentPane().add(min_txt);
		min_txt.setColumns(10);
		
		ideal_txt = new JTextField();
		ideal_txt.setBounds(102, 86, 134, 28);
		getContentPane().add(ideal_txt);
		ideal_txt.setColumns(10);
		
		max_txt = new JTextField();
		max_txt.setBounds(102, 126, 134, 28);
		getContentPane().add(max_txt);
		max_txt.setColumns(10);		
		
	}

	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == ok_btn){
				if(navn_txt.getText().equals("") 
						|| min_txt.getText().equals("") 
						|| ideal_txt.getText().equals("")
						|| max_txt.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Udfyld alle felterne", "Fejl", JOptionPane.ERROR_MESSAGE);
				}else{
					int min = Integer.parseInt(min_txt.getText());
					int ideal = Integer.parseInt(ideal_txt.getText());
					int max = Integer.parseInt(max_txt.getText());
					Service.createDelbehandling(navn_txt.getText(), min, ideal, max);
					delbehandling_list.setListData(Service.getDelbehandlinger().toArray());
					dispose();
				}
			}else if(e.getSource() == cancel_btn){
				dispose();
			}
		}
	}

}
