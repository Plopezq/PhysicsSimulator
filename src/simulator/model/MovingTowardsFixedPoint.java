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
	
	

	@Override
	public void apply(List<Body> bs) {
		// si tiene una aceleracion fija de 9.81 significa que 9.81=f/m por lo tanto f=9.81m
		double g=9.81;
		Vector2D nueva_f;

		for (Body b : bs) {
			nueva_f.plus(b.m);
			nueva_f.scale(9.81);
			
			b.resetForce();
			b.addForce(nueva_f);
			aceleracion = -g * b.getPosition().direction();
			b.;
		}		


}
