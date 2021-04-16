package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	private List<Body> bodies;
	private double tiempo_actual;
	private double delta_time; // tiempo real por paso
	// representa el tiempo (en segundos) que corresponde
	// a un paso de simulación — se pasará al método move de los cuerpos.
	// Debe lanzar una excepción IllegalArgumentException en caso de que el valor no
	// sea válido.
	private ForceLaws leyes;

	public PhysicsSimulator(double tiempo_paso, ForceLaws leyes) {
		if (tiempo_paso < 0.0 || leyes == null)
			throw new IllegalArgumentException();
		this.delta_time = tiempo_paso;
		this.leyes = leyes;

		this.tiempo_actual = 0.0;
		this.bodies = new ArrayList<>();
	}

	public void advance() {
		/*
		 * 1. llama al método resetForce de todos los cuerpos, 2. llama al método apply
		 * de las leyes de fuerza, 3. llama a move(dt) para cada cuerpo, donde dt es el
		 * tiempo real por paso, 4. finalmente incrementa el tiempo actual en dt
		 * segundos.
		 */

		for (Body body : bodies) {
			body.resetForce();
		}

		this.leyes.apply(bodies);

		for (Body body : bodies) {
			body.move(this.delta_time);
		}
		this.tiempo_actual += this.delta_time;

	}

	public void addBody(Body b) {
		if (!bodies.contains(b))
			bodies.add(b);
		else
			throw new IllegalArgumentException();
	}

	public JSONObject getState() {
		JSONObject stateJSON = new JSONObject();
		stateJSON.put("time", this.tiempo_actual);

		JSONArray ja = new JSONArray();
		for (int i = 0; i < bodies.size(); i++) {
			ja.put(bodies.get(i).getState());
		}
		stateJSON.put("bodies", ja);

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

	public void reset() {
		tiempo_actual = 0.0;
		bodies.clear();
	}

	public void setDeltaTime(double dt) {
		if (dt <= 0.0)
			throw new IllegalArgumentException();

		delta_time = dt;
	}

	public void setForceLawsLaws(ForceLaws forceLaws) {
		if (forceLaws == null)
			throw new IllegalArgumentException();
		leyes = forceLaws;
	}

}
