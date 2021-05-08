package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {

	private Controller _ctrl;
	private boolean _stopped;
	private JButton loadButton;
	private JFileChooser fc;
	private JButton physicsButton;
	private MyDialogWindow forceLawsDialog;
	private JButton runButton;
	private JButton stopButton;
	private JButton exitButton;
	private JToolBar toolBar;
	private JSpinner numPasos;
	private JLabel jl1;
	private JLabel jl2;
	private JTextField dt;

	public ControlPanel(Controller ctrl) {
		super();
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());
		toolBar = new JToolBar("ToolBar flotante");

		// BOTON CARGHAR DATOS
		loadButton = new JButton();
		fc = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
		loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int v = fc.showOpenDialog(null);
				if (v == JFileChooser.APPROVE_OPTION) {
					_ctrl.reset(); // LIMPIAMOS el simulador
					File file = fc.getSelectedFile();
					InputStream stream;
					try {
						stream = new FileInputStream(file);
						_ctrl.loadBodies(stream); // CARGAMOS el fichero al simulador
					} catch (FileNotFoundException e1) {
						e1.printStackTrace(); // No deberia pasar nunca
					}
					System.out.println("loading " + file.getName());
				} else
					System.out.println("load cancelled by user");
			}

		});
		loadButton.setToolTipText("Cargar fichero");
		toolBar.add(loadButton);

		toolBar.addSeparator();
		// BOTON LEYES DE LA FISICA
		physicsButton = new JButton();
		physicsButton.setIcon(new ImageIcon("resources/icons/physics.png"));
		physicsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectForceLaws();
			}
		});
		physicsButton.setToolTipText("Cambiar leyes de fuerza");
		toolBar.add(physicsButton);
		toolBar.addSeparator();

		// BOTON DE RUN
		runButton = new JButton();
		runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Desactivamos todos los botones excepto el de stop
				loadButton.setEnabled(false);
				physicsButton.setEnabled(false);
				runButton.setEnabled(false);
				numPasos.setEnabled(false);
				dt.setEnabled(false);

				// Cambiamos el valor de stopper
				_stopped = false;

				// Ponemos el dt correspondiente
				_ctrl.setDeltaTime(Double.parseDouble(dt.getText()));

				// Llamamos al run__sim con los pasos que diga el spinner
				int value = (Integer) numPasos.getValue();
				run_sim(value);

			}
		});
		runButton.setToolTipText("Boton de run");
		toolBar.add(runButton);

		// BOTON DE STOP
		stopButton = new JButton();
		stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Cambiamos el valor de stopper
				_stopped = true;
			}
		});
		stopButton.setToolTipText("Boton de stop");
		toolBar.add(stopButton);

		// SELECTOR DEL NUMERO DE PASOS
		jl1 = new JLabel("Steps: ");
		jl1.setOpaque(true);
		SpinnerModel modelo = new SpinnerNumberModel(10000, 0, 500000, 100);// value, min, max, steps
		numPasos = new JSpinner(modelo);
		numPasos.setMaximumSize(new Dimension(200, 50));
		toolBar.add(jl1);
		numPasos.setToolTipText("Selector del numero de pasos");
		toolBar.add(numPasos);

		// DELTA TIME
		jl2 = new JLabel("Delta time: ");
		jl2.setOpaque(true);
		dt = new JTextField(6);
		dt.setText("2.500");
		dt.setMaximumSize(new Dimension(200, 50));
		toolBar.add(jl2);
		dt.setToolTipText("Delta time");
		toolBar.add(dt);

		toolBar.add(Box.createGlue());
		toolBar.addSeparator();
		// BOTON DE CERRAR
		exitButton = new JButton();
		exitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		JFrame ancestor = (JFrame) SwingUtilities.getWindowAncestor(this);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "Si", "No" };
				int n = JOptionPane.showOptionDialog(ancestor, "Estas seguro de querer cerrar el simulador?:",
						"Physics Simulator", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
						options[0]);
				if (n == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		exitButton.setToolTipText("Boton de cerrar");
		toolBar.add(exitButton);
		toolBar.setFloatable(true);
		// this.setVisible(true);
		this.add(toolBar, BorderLayout.PAGE_START);
	}

	// other private/protected methods
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				// show the error in a dialog box
				JOptionPane.showMessageDialog(this, "Error Dialog.", "Error Icon", JOptionPane.ERROR_MESSAGE);
				// enable all buttons
				loadButton.setEnabled(true);
				physicsButton.setEnabled(true);
				runButton.setEnabled(true);
				numPasos.setEnabled(true);
				dt.setEnabled(true);
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
			// enable all buttons
			loadButton.setEnabled(true);
			physicsButton.setEnabled(true);
			runButton.setEnabled(true);
			numPasos.setEnabled(true);
			dt.setEnabled(true);
			_stopped = true;	
		}
	}

	protected void selectForceLaws() {

		if (forceLawsDialog == null) {
			JFrame ancestor = (JFrame) SwingUtilities.getWindowAncestor(this);
			this.forceLawsDialog = new MyDialogWindow(ancestor);
		}
		
		int status = forceLawsDialog.open(_ctrl.getForceLawsInfo());

			_ctrl.setForceLaws(forceLawsDialog.getTableData());
		if (status == 0) {
			System.out.println("Canceled");
		} else {
			//System.out.println("Here is your JSON:");
			System.out.println("OK");
			_ctrl.setForceLaws(forceLawsDialog.getTableData());
		}



	}
	// SimulatorObserver methods

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		String cadena = String.valueOf(dt);
		this.dt.setText(cadena);

	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		String cadena = String.valueOf(dt);
		this.dt.setText(cadena);
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
		String cadena = String.valueOf(dt);
		this.dt.setText(cadena);

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
//		JSONObject aux = new JSONObject(fLawsDesc);
//		_ctrl.setForceLaws(aux);
	}
}