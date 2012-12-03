package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;

import service.Service;

public class MellemvareLagerGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Service.createSomeObjects();
		new MellemvareLagerGUI();
	}

	/**
	 * Create the frame.
	 */
	public MellemvareLagerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Lagerstyring", new LagerStyringPane());
		tabbedPane.addTab("Mellemvarer", new MellemvarePane());
		tabbedPane.addTab("Produkttyper", new ProdukttypePane());
		tabbedPane.addTab("Behandlinger", new BehandlingPane());
		tabbedPane.addTab("Delbehandlinger", new DelbehandlingPane());
		
		contentPane.add(tabbedPane);
		
		setVisible(true);
	}

}
