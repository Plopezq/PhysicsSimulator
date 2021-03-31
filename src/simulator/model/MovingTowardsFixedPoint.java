package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	/*
	 * Esta ley de gravedad se implementa en la clase MovingTowardsFixedPoint. La
	 * ley simula un escenario en el cual todos los cuerpos caen hacia el “centro
	 * del universo”, i.e. tienen una aceleración fija de g = 9,81 en dirección al
	 * origen ~o = (0, 0). Técnicamente, para un cuerpo Bi , asumiendo que ~di es su
	 * dirección, su aceleración debería ponerse a: −g · di .
	 */
	private Vector2D c;
	private double g;
	
	public MovingTowardsFixedPoint() {
		this.c = new Vector2D(); // [0,0]
		this.g = -9.81;
	}
	public MovingTowardsFixedPoint(Vector2D c, double g) {
		this.c = new Vector2D(c);
		this.g = g;
	}
	
	@Override
	public void apply(List<Body> bs) {
		// si tiene una aceleracion fija de 9.81 significa que 9.81=f/m por lo tanto
		// f=9.81m

		for (Body b : bs) {
			Vector2D dir = this.c.minus(b.p);
			Vector2D Fi = dir.scale(b.m/this.g);
			//b.resetForce();
			b.addForce(Fi);
			//Esto provocará que el cuerpo bi se mueva hacia ~c con una aceleración g.
		}
	}
	
	@Override
	public String toString() {

		return null;
	}
}
