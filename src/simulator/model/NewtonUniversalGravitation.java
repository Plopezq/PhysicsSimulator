package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

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
			//Dos cuerpos se aplican una fuerza, uno sobre otro --> dos bucles for
		for(int i = 0; i < bs.size(); i++) {
			Vector2D Fi = new Vector2D(0,0); //Sumatorio de la fuerza que aplican todos los cuerpos j sobre i
			for(int j = 0; j < bs.size(); j++) {
				if(i != j) { //NO se aplica la fuerza sobre si mismo
					if(bs.get(i).m == 0) { //si la masa de i es 0, la aceleracion y la velocidad valen 0
						bs.get(i).setVelocity(new Vector2D());
					}else {//Si la masa NO es 0, calculamos Fi
						double denominador = bs.get(j).getPosition().distanceTo(bs.get(i).getPosition());
						if(denominador == 0) {
							double fij = 0;
						}
						if(denominador > 0){
							double numerador = bs.get(i).getMass() * bs.get(j).getMass();
							Vector2D dist = bs.get(i).getPosition().minus(bs.get(j).getPosition());
							double fij = this.G * (numerador)/(Math.pow(denominador, 2));
							Vector2D Fij = new Vector2D(dist.scale(fij));
							Fi = Fi.plus(Fij); //Le sumo la fuerza que se le aplican
						}
					}
				}
			}
			//En caso de ser el mismo, se sumaria 0,0
			bs.get(i).addForce(Fi); //Modifico su aceleracion
		}
	}
	@Override
	public String toString() {

		return null;
	}
}
