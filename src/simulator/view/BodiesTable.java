package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
	
	
	private BodiesTableModel tableModel;

	public BodiesTable(Controller ctrl) {
		this.setLayout(new BorderLayout()); 
		this.setBorder(BorderFactory.createTitledBorder( 
				BorderFactory.createLineBorder(Color.black, 2), 
				"Bodies", 
				TitledBorder.LEFT, TitledBorder.TOP));
		tableModel = new BodiesTableModel(ctrl);
		JTable table = new JTable(tableModel);
		this.add(new JScrollPane(table), BorderLayout.PAGE_START);
		table.setFillsViewportHeight(true);		
		this.setPreferredSize(new Dimension(300, 200));

	}

	
}
