package simulator.control;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Body;

public class MassEqualStates implements StateComparator {

	//constructor por defecto
	public MassEqualStates() {
		
	}
	
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {

		if (s1.get("tiempo_actual") == s2.get("tiempo_actual")) {
			List<Body> bodies_s1 = (List<Body>) s1.get("bodies");
			List<Body> bodies_s2 = (List<Body>) s2.get("bodies");

			for (int i = 0; i < bodies_s1.size(); i++) {
				if (bodies_s1.get(i).getId() != bodies_s2.get(i).getId()
						|| bodies_s1.get(i).getMass() != bodies_s2.get(i).getMass())
					return false;
			}
				// el bucle ha acabado todos son iguales.
			return true;
		} else
			return false;
	}

}
