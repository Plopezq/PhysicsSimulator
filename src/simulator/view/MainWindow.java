package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
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

		//ANYADO AL PANEL EL PANEL DE CONTROL en el PAGE_START del panel mainPanel;
		mainPanel.add(new ControlPanel(this._ctrl), BorderLayout.PAGE_START );
		
		
		/* (3) crea un nuevo panel que use BoxLayout (y BoxLayout.Y_AXIS) y colócalo 
		 * en el CENTER de mainPanel. 
		 * Añade la tabla de cuerpos y el viewer en este panel.
		 */
		JPanel centerPanel = new JPanel(); 
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
			//ANYADO LA TABLA DE CUERPOS 
			centerPanel.add(new BodiesTable(this._ctrl));
			
			//ANYADO EL VIEWER
			centerPanel.add(new Viewer(this._ctrl));
		
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		
		//ANYADO LA BARRA DE ESTADO en el PAGE_END del mainPanel;
		mainPanel.add(new StatusBar(this._ctrl), BorderLayout.PAGE_END );

		
		
		//Centro el JFrame
		//Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
	    //int height = pantalla.height;
	    //int width = pantalla.width;
	    //this.setSize(width/2, height/2);	
	    this.setLocationRelativeTo(null);		
	    this.setVisible(true);
		//this.setBounds(400,400,600,300);
		this.pack();	
		
	}
	// other private/protected methods
	// ...
	
	
}
