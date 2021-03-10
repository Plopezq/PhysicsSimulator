package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

	
	
	
	

	@Override
	public void apply(List<Body> bs) {
		for(int i = 0; i < bs.size(); i++) { //	Primer cuerpo que recibe las fuerzas 
			Vector2D Fi = new Vector2D(0,0); //Sumatorio de la fuerza que aplican todos los cuerpos j sobre i
			for(int j = 0; j < bs.size(); j++) { //	de todos estos cuerpos
				double G = Math.pow(6.67, -11);
				double numerador = bs.get(i).getMass() * bs.get(j).getMass();
				Vector2D dist = bs.get(j).getPosition().minus(bs.get(i).getPosition());
				double denominador = Math.pow(bs.get(j).getPosition().distanceTo(bs.get(i).getPosition()), 2);
				
				double fij = G * (numerador)/(denominador);
				
				Vector2D Fij = dist.scale(fij);
				
				Fi = Fi.plus(Fij); //Le sumo las fuerzas que se le aplican
			}
			//Modifico su aceleracion
			bs.get(i).addForce(Fi);
		}	
	}

}
