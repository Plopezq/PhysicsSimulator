package simulator.model;

import java.util.List;

public interface ForceLaws {
	public void apply(List<Body> bs);
	//debe aplicar fuerzas a los distintos cuerpos de la lista que va como parámetro.
}
