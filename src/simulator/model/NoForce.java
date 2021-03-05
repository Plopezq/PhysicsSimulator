package simulator.model;

import java.util.List;

public class NoForce  implements ForceLaws {

	@Override
	public void apply(List<Body> bs) {
		// No hace nada, es decir, los cuerpos
		// se mueven con un aceleracion fija
		
	}

}
