package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {

	private Vector2D c;
	private double g;

	public MovingTowardsFixedPoint() {
		this.c = new Vector2D(); // [0,0]
		this.g = 9.81;
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
			Vector2D Fi = ((this.c.minus(b.p)).direction()).scale(b.m * this.g);
			b.addForce(Fi);

			// Esto provocará que el cuerpo bi se mueva hacia ~c con una aceleración g.
		}
	}

	@Override
	public String toString() {

		return "Moving towards -" + c.toString() + " with constant acceleration -" + this.g;
	}
}
