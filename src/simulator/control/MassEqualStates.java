package simulator.control;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Body;

public class MassEqualStates implements StateComparator {

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {

		if (s1.get("tiempo_actual") == s2.get("tiempo_actual")) {
			List<Body> bodies_s1 = (List<Body>) s1.get("bodies");
			List<Body> bodies_s2 = (List<Body>) s2.get("bodies");
			
			return true;
		} else
			return false;
	}

}
