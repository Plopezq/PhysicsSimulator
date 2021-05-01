package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
// ...
	private Controller _ctrl;
	private boolean _stopped;
	private JButton loadButton;
	private JFileChooser fc;
	private JButton physicsButton;
	private JButton runButton;
	private JButton stopButton;
	private JButton exitButton;
	private JToolBar toolBar;
	private JSpinner numPasos;
	private JTextField dt;


	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		//_ctrl.addObserver(this); //da problemas
	}

	private void initGUI() {
		
		toolBar = new JToolBar("ToolBar flotante");
		//BOTON CARGHAR DATOS
		loadButton = new JButton("LOAD"); // hay que cambiarlo por el icono.
		loadButton = new JButton();
		fc = new JFileChooser();
		loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					int v = fc.showOpenDialog(null); 
					if (v==JFileChooser.APPROVE_OPTION){ 
						File file = fc.getSelectedFile(); 
						System.out.println("loading " +file.getName()); 
					} else 
						System.out.println("load cancelled by user"); }

		});
		toolBar.add(loadButton);
		toolBar.addSeparator();
		//BOTON LEYES DE LA FISICA
		physicsButton = new JButton();
		physicsButton.setIcon(new ImageIcon("resources/icons/physics.png"));
		/*physicsButton.addActionListener(new ActionListener() {
			/* @Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Has pulsado el boton de fisica.", 
						"Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});*/ //TODO
		toolBar.add(physicsButton);
		toolBar.addSeparator();
		
		//BOTON DE RUN
		runButton = new JButton();
		runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		//runButton.addActionListener(/*new RunButtomListener()*/); //TODO
		toolBar.add(runButton);
		
		//BOTON DE STOP
		stopButton = new JButton();
		stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		toolBar.add(stopButton);
		
		//SELECTOR DEL NUMERO DE PASOS
		numPasos = new JSpinner();
		toolBar.add(numPasos);

		//DELTA TIME
		dt = new JTextField();
		toolBar.add(dt);
		
		
		
		toolBar.add(Box.createGlue());
		//BOTON DE CERRAR
		exitButton = new JButton();
		exitButton.setIcon(new ImageIcon("icons/exit.png"));
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Si","No"}; 
				int n = JOptionPane.showOptionDialog(null, 
						"Elige una opción:",
						"Opciones Posibles", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.INFORMATION_MESSAGE,
						null, options, options[0]);
					if (n==JOptionPane.YES_OPTION)
						System.exit(0);
			}
		});
		toolBar.add(exitButton);

		
		
		this.add(toolBar);
	}

	// other private/protected methods
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				// TODO show the error in a dialog box
				// TODO enable all buttons
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} else {
			_stopped = true;
			// TODO enable all buttons
		}
	}
	// SimulatorObserver methods

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub

	}
}