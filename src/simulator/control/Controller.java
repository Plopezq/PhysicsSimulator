package simulator.control;

import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;
import simulator.model.Body;
import simulator.model.ForceLaws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

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
	private Factory<Body> factBody; //Se usara para construir los cuerpos que se leen del fichero
	private Factory<ForceLaws> factLaws;
	//CONSTRUCTOR
	public Controller(PhysicsSimulator simulador, Factory<Body> factBody, Factory<ForceLaws> factLaws) {
		this.simulador = simulador;
		this.factBody = factBody;	
		this.factLaws = factLaws;
	}
	//METODOS
	public void loadBodies(InputStream in) { // (1)
		JSONObject jsonInput = new JSONObject(new JSONTokener(in)); // { "bodies": [bb 1 ,...,bb n ] }
		JSONArray objs = jsonInput.getJSONArray("bodies"); 
		for (int i = 0; i < objs.length(); i++) { //Leemos cuerpo a cuerpo
			//System.out.println(objs.getJSONObject(i)); //Para debuggear
			Body cuerpo = this.factBody.createInstance(objs.getJSONObject(i));//Creamos el cuerpo
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
		p.println(simulador.getState()); //estado s0 --> estado inicial del simulador

		if(expOut != null) { //Si tenemos archivo con el que comparar, comparamos
			if ( ! cmp.equal(estados.getJSONObject(0), this.simulador.getState()) ) { //Comparamos el estado inicial
				throw new DifferentStatesException(0); //Los estados NO son iguales
			}
		}
		// run the sumulation n steps, etc.
		for(int i = 1; i <= n; i++) {
			p.print(",");
			this.simulador.advance(); //ejecutamos un paso de simulacion
			p.println(this.simulador.getState());
			//System.out.println(i);
			//En cada paso de simulacion, debo comparar el estado actual, con el esperado 
			// usando el comparador que me proporcionan
			if(expOut != null) { //Si tenemos archivo con el que comparar, comparamos
				if ( ! cmp.equal(estados.getJSONObject(i), this.simulador.getState()) ) { 
					throw new DifferentStatesException(0); //Los estados NO son iguales
				}
			}
		}
		p.println("]"); p.println("}");
	}
	
	public void reset() {
		//invoca al metodo reset del simulador
		this.simulador.reset();
	}
	
	public void setDeltaTime(double dt) {
		//Invoca al metodo setDeltaTime del simulador
		this.simulador.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		//Invoca al metodo addObserver del simulador
		this.addObserver(o);
	}
	
	public void run(int n) throws DifferentStatesException {
		//Ejecuta n pasos del simulador sin escribir nada en consola. 
		//Este método es opcional. 
		//Puedes usar el método run que has programado en la práctica 1 de manera que, 
		//cuando se llame desde la GUI, se le proporcione un OutputStream que no imprima nada:
		//VER pagina 4
		OutputStream osAux = new OutputStream() { @Override public void write(int b) throws IOException { }; };		
		//TODO revisar si esta bien
		this.run(n, osAux, null, null);
	}
	
	public List<JSONObject> getForceLawsInfo(){
		//devuelve la lista devuelta por el método getInfo() de la factoría de leyes de fuerza.
		//Se utilizará en la GUI para mostrar las leyes de fuerza disponibles y permitir cambiarlas.
		return this.factLaws.getInfo();
	}
	
	public void setForceLaws(JSONObject info) {
		//usa la factoría de leyes de fuerza actual para crear un nuevo objeto 
		//de tipo ForceLaws a partir de info y cambia las leyes 
		//de la fuerza del simulador por él.
		//TODO revisar si de verdad esta bien
		Object aux = this.factLaws.createInstance(info);
		this.simulador.setForceLawsLaws((ForceLaws) aux);
	}
}
