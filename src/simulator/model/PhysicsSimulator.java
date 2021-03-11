package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class PhysicsSimulator {
	protected List<Body> bodies;
	protected double tiempo_paso;
	protected double tiempo_actual;
	protected ForceLaws ley;

	public PhysicsSimulator(double tiempo_paso, ForceLaws ley) {
		if (tiempo_paso < 0.0 || ley == null)
			throw new IllegalArgumentException();
		this.tiempo_paso = tiempo_paso;
		this.ley = ley;

		this.tiempo_actual = 0.0;
		this.bodies = new ArrayList<>();
	}

	public void advance() {
		/*
		 * 1. llama al método resetForce de todos los cuerpos, 
		 * 2. llama al método apply de las leyes de fuerza, 
		 * 3. llama a move(dt) para cada cuerpo, donde dt es el tiempo real por paso,
		 * 4. finalmente incrementa el tiempo actual en dt segundos.
		 */

		for (Body body : bodies) {
			body.resetForce();
		}
		
		ley.apply(bodies);

		for (Body body : bodies) {
			body.move(tiempo_paso);
		}
		tiempo_actual += tiempo_paso;

	}

	public void addBody(Body b) {
		if (!bodies.contains(b))
			bodies.add(b);
		else
			throw new IllegalArgumentException();
	}

	public JSONObject getState() {
		JSONObject stateJSON = new JSONObject(this);
		return stateJSON;	
	}

	@Override
	public String toString() {

		return getState().toString();
	}

	public List<Body> getBodies() {
		return bodies;
	}

	
	public double getTiempo_actual() {
		return tiempo_actual;
	}

	

}
