package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
// ...
	private Controller _ctrl;
	private boolean _stopped;
	JButton loadButton;
	JFileChooser loadFile;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		// TODO build the tool bar by adding buttons, etc.
		// boton cargar datos
		loadButton = new JButton("LOAD"); // hay que cambiarlo por el icono.
		loadFile = new JFileChooser();
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						_ctrl.reset();
						InputStream bodies = new FileInputStream(loadFile.getSelectedFile());
						_ctrl.loadBodies(bodies);

					} catch (Exception exc) {
						JOptionPane.showMessageDialog(loadFile, exc.getMessage());

					}
				}
			}
		});

		// boton forcelaws
		
		// boton play
		// boton stop
		// boton cerrar
		// pasos
		// delta time
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