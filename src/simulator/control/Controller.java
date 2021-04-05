package simulator.control;

import simulator.model.PhysicsSimulator;
import simulator.model.Body;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.*;

public class Controller {
//	Es el encargado de (1) leer los cuerpos desde un InputStream dado 
//	y añadirlos al simulador; 
//	(2) ejecutar el simulador un número determinado de pasos y 
//	mostrar los diferentes estados de cada paso en un OutputStream dado.
	
	private PhysicsSimulator simulador; //Se usara para ejecutar las diferentes operaciones
	private Factory<Body> factoria; //Se usara para construir los cuerpos que se leen del fichero
	
	//CONSTRUCTOR
	public Controller(PhysicsSimulator simulador, Factory<Body> factoria) {
		this.simulador = simulador;
		this.factoria = factoria;		
	}
	//METODOS
	public void loadBodies(InputStream in) { // (1)
		JSONObject jsonInput = new JSONObject(new JSONTokener(in)); // { "bodies": [bb 1 ,...,bb n ] }
		JSONArray objs = jsonInput.getJSONArray("bodies"); 
		for (int i = 0; i < objs.length(); i++) { //Leemos cuerpo a cuerpo
			//System.out.println(objs.getJSONObject(i)); //Para debuggear
			Body cuerpo = this.factoria.createInstance(objs.getJSONObject(i));//Creamos el cuerpo
			this.simulador.addBody(cuerpo); //Lo anyadimos al simulador
		}
	}
	
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws DifferentStatesException { // (2)
		JSONArray estados = null;
		//expOut contiene la salida esperada o null
		if(expOut != null) { //Lo convierto en una estructura JSON
			JSONObject jsonInput = new JSONObject(new JSONTokener(expOut));
			 estados = jsonInput.getJSONArray("states"); //Array con los diferentes estados que esperamos
		}
		// ejecuta el simulador n pasos, y muestra los diferentes estados obtenidos en out, 
		// utilizando el siguiente formato JSON: { "states": [s0 ,s1 ,...,sn ] }
		
	  	PrintStream p = new PrintStream(out);
		p.println("{"); p.println("\"states\": [");
		p.print(simulador.getState()); p.print(","); //estado s0 --> estado inicial del simulador

		if ( ! cmp.equal(estados.getJSONObject(0), this.simulador.getState()) ) { //Comparamos el estado inicial
			throw new DifferentStatesException(0); //Los estados NO son iguales
		}
		
		// run the sumulation n steps, etc.
		for(int i = 1; i <= n; i++) {
			this.simulador.advance(); //ejecutamos un paso de simulacion
			p.print(this.simulador.getState()); p.print(",");
			
			//En cada paso de simulacion, debo comparar el estado actual, con el esperado 
			// usando el comparador que me proporcionan
			if ( ! cmp.equal(estados.getJSONObject(i), this.simulador.getState()) ) { 
				throw new DifferentStatesException(0); //Los estados NO son iguales
			}
		}
		p.println("]"); p.println("}");
	}
	
}
