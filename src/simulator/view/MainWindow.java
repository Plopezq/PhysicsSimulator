package simulator.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	// ...
	Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//ANYADO AL PANEL EL PANEL DE CONTROL arriba del todo
		mainPanel.add(new ControlPanel(this._ctrl));
		
		
		
		//ANYADO LA TABLA DE ESTADOS abajo del todo
		//this.add(new StatusBar(this._ctrl), BorderLayout.PAGE_END);
		
		
		
		//ANYADO LE VISOR GRAFICO
		
		
		
		
		//ANYADO LA BARRA DE ESTADO
	
		
		
		
		
		this.setVisible(true);
		this.pack();	
	}
	// other private/protected methods
	// ...
	
	
}
