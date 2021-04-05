package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.control.StateComparator;
import simulator.factories.Factory;
import simulator.factories.MassEqualStateBuider;
import simulator.factories.MassLosingBodyBuilder;
import simulator.factories.MovingTowardsFixedPointBuilder;
import simulator.factories.NewtonUniversalGravitationBuilder;
import simulator.factories.NoForceBuilder;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.factories.BasicBodyBuilder;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.EpsilonEqualStateBuilder;

public class Main {

	// default values for some parameters
	//
	private final static Double _dtimeDefaultValue = 2500.0;
	private final static String _forceLawsDefaultValue = "nlug";
	private final static String _stateComparatorDefaultValue = "epseq";
	
	
	/*-s,--steps <arg> An integer representing the number of
	simulation steps. Default value: 150.*/
	private final static int _stepsDefaultValue = 150;

	// some attributes to stores values corresponding to command-line parameters
	//
	private static Double _dtime = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static String _outExpectedFile = null;
	private static String _steps = null;
	private static JSONObject _forceLawsInfo = null;
	private static JSONObject _stateComparatorInfo = null;

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<ForceLaws> _forceLawsFactory;
	private static Factory<StateComparator> _stateComparatorFactory;

	private static void init() {
		//Inicializacion de los builders
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
		bodyBuilders.add(new BasicBodyBuilder());
		bodyBuilders.add(new MassLosingBodyBuilder());
		_bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);
		
		//Inicializacion de las leyes de fuerza
		ArrayList<Builder<ForceLaws>> forceBuilders = new ArrayList<>();
		forceBuilders.add(new NoForceBuilder());
		forceBuilders.add(new MovingTowardsFixedPointBuilder());
		forceBuilders.add(new NewtonUniversalGravitationBuilder());
		_forceLawsFactory = new BuilderBasedFactory<ForceLaws>(forceBuilders);
		
		//Inicializacion de los comparadores de estado
		ArrayList<Builder<StateComparator>> stateBuilder = new ArrayList<>();
		stateBuilder.add(new EpsilonEqualStateBuilder());
		stateBuilder.add(new MassEqualStateBuider());
		_stateComparatorFactory = new BuilderBasedFactory<StateComparator>(stateBuilder);
		

	}

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);

			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			//  add support of -o, -eo, and -s (define corresponding parse methods)
			parseOutFileOption(line);
			parseExpectedOutputOption(line);
			parseStepsOption(line);

			
			parseDeltaTimeOption(line);
			parseForceLawsOption(line);
			parseStateComparatorOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	
	

	

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

		//  add support for -o, -eo, and -s (add corresponding information to
		// cmdLineOptions)
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg()
				.desc("Output file, where output is written. " + "Default value: the standard output").build());
		cmdLineOptions.addOption(Option.builder("eo").longOpt("expected-output").hasArg()
				.desc("The expected output file. If not provided no comparison is applied.").build());
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg()
				.desc("An integer representing the number of simulation steps. Default value: 150.").build());

		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg().desc(
				"A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());

		// force laws
		cmdLineOptions.addOption(Option.builder("fl").longOpt("force-laws").hasArg()
				.desc("Force laws to be used in the simulator. Possible values: "
						+ factoryPossibleValues(_forceLawsFactory) + ". Default value: '" + _forceLawsDefaultValue
						+ "'.")
				.build());

		// gravity laws
		cmdLineOptions.addOption(Option.builder("cmp").longOpt("comparator").hasArg()
				.desc("State comparator to be used when comparing states. Possible values: "
						+ factoryPossibleValues(_stateComparatorFactory) + ". Default value: '"
						+ _stateComparatorDefaultValue + "'.")
				.build());

		return cmdLineOptions;
	}

	public static String factoryPossibleValues(Factory<?> factory) {
		if (factory == null)
			return "No values found (the factory is null)";

		String s = "";

		for (JSONObject fe : factory.getInfo()) {
			if (s.length() > 0) {
				s = s + ", ";
			}
			s = s + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
		}

		s = s + ". You can provide the 'data' json attaching :{...} to the tag, but without spaces.";
		return s;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null) {
			throw new ParseException("In batch mode an input file of bodies is required");
		}
	}
	

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}

	private static JSONObject parseWRTFactory(String v, Factory<?> factory) {

		// the value of v is either a tag for the type, or a tag:data where data is a
		// JSON structure corresponding to the data of that type. We split this
		// information
		// into variables 'type' and 'data'
		//
		int i = v.indexOf(":");
		String type = null;
		String data = null;
		if (i != -1) {
			type = v.substring(0, i);
			data = v.substring(i + 1);
		} else {
			type = v;
			data = "{}";
		}

		// look if the type is supported by the factory
		boolean found = false;
		for (JSONObject fe : factory.getInfo()) {
			if (type.equals(fe.getString("type"))) {
				found = true;
				break;
			}
		}

		// build a corresponding JSON for that data, if found
		JSONObject jo = null;
		if (found) {
			jo = new JSONObject();
			jo.put("type", type);
			jo.put("data", new JSONObject(data));
		}
		return jo;

	}

	private static void parseForceLawsOption(CommandLine line) throws ParseException {
		String fl = line.getOptionValue("fl", _forceLawsDefaultValue);
		_forceLawsInfo = parseWRTFactory(fl, _forceLawsFactory);
		if (_forceLawsInfo == null) {
			throw new ParseException("Invalid force laws: " + fl);
		}
	}

	private static void parseStateComparatorOption(CommandLine line) throws ParseException {
		String scmp = line.getOptionValue("cmp", _stateComparatorDefaultValue);
		_stateComparatorInfo = parseWRTFactory(scmp, _stateComparatorFactory);
		if (_stateComparatorInfo == null) {
			throw new ParseException("Invalid state comparator: " + scmp);
		}
	}
	
	private static void parseOutFileOption(CommandLine line) throws ParseException  {
		_outFile  = line.getOptionValue("o");
		
		if (_outFile == null) {
			throw new ParseException("Out file argument missing");
		}		
	}
	
	private static void parseStepsOption(CommandLine line) {
		_steps = line.getOptionValue("s", Integer.toString(_stepsDefaultValue));
	}
	
	private static void parseExpectedOutputOption(CommandLine line) {
		_outExpectedFile = line.getOptionValue("ou");
	}
	
	private static void startBatchMode() throws Exception {
		/*
		 * cree el simulador (una instancia de PhyicsSimulator), pasando como argumentos
		 * las leyes de la fuerza y el delta time (las opciones -fl y -dt
		 * respectivamente).
		 */
		ForceLaws force = _forceLawsFactory.createInstance(_forceLawsInfo);
		PhysicsSimulator simulador = new PhysicsSimulator(_dtime, force);

		/*
		 * • cree los ficheros de entrada y salida tal y como vengan especificados por
		 * las opciones -i, -o, y -eo. Recuerda que si la opción -o no aparece en la
		 * línea de comandos, entonces se utiliza la salida por consola, i.e.,
		 * System.out para mostrar la salida.
		 */
		//Hecho mas abajo
		
		
		/*
		 * cree un comparador de estados de acuerdo con la información que aparece en la
		 * opción -cmp.
		 */
		
		StateComparator comparator = _stateComparatorFactory.createInstance(_stateComparatorInfo);
		
		
		/*
		 * cree un controlador (instancia de la clase Controller), pasándole el
		 * simulador y la factoría de cuerpos.
		 * Añada los cuerpos al simulador llamando al
		 * método loadBodies del controlador.
		 */
		Controller control = new Controller(simulador, _bodyFactory);

		InputStream in = new FileInputStream(_inFile);
		control.loadBodies(in);

		/*
		 * inicie la simulación llamando al método run del controlador y pasándole los
		 * argumentos correspondientes.
		 */
		OutputStream out;
		if (_outFile == null)
			out = System.out;
		else
			out = new FileOutputStream(_outFile);

		InputStream expOut;
		if (_outExpectedFile == null)
			expOut = null;
		else
			expOut = new FileInputStream(_outExpectedFile);

		control.run(Integer.parseInt(_steps), out, expOut, comparator);

	}

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		startBatchMode();
	}

	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
