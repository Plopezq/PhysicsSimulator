package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

	private double G;

	public NewtonUniversalGravitation(double G) {
		this.G = G;
	}

	public NewtonUniversalGravitation() {
		this.G = 6.67E-11;
	}

	@Override
	public void apply(List<Body> bs) {
		// Dos cuerpos se aplican una fuerza, uno sobre otro --> dos bucles for

		for (int i = 0; i < bs.size(); i++) {
			Vector2D Fi = new Vector2D(); // Sumatorio de la fuerza que aplican todos los cuerpos j sobre i
			if (bs.get(i).m == 0) // si la masa de i es 0, la aceleracion y la velocidad valen 0
				bs.get(i).setVelocity(new Vector2D());
			else
				for (int j = 0; j < bs.size(); j++) {
					if (i != j) { // NO se aplica la fuerza sobre si mismo
						double distancia = bs.get(j).getPosition().distanceTo(bs.get(i).getPosition());
						double fij = 0;
						if (distancia > 0) {
							double numerador = bs.get(i).getMass() * bs.get(j).getMass();
							fij = (this.G * numerador) / (Math.pow(distancia, 2));

							Vector2D dij = new Vector2D((bs.get(j).p.minus(bs.get(i).p)).direction());
							Vector2D Fij = new Vector2D(dij.scale(fij));

							Fi = Fi.plus(Fij); // Le sumo la fuerza que se le aplican

						}
					}
				}
			// En caso de ser el mismo, se sumaria 0,0
			bs.get(i).addForce(Fi); // Modifico su aceleracion
		}
	}

	@Override
	public String toString() {

		return "Newton’s Universal Gravitation with G=-" + G;
	}
}
