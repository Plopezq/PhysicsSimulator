package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;
import simulator.misc.Vector2D;
import simulator.model.NewtonUniversalGravitation;

public class EpsilonEqualStateBuilder extends Builder<StateComparator> {

	private EpsilonEqualStates comparador;
	
	
	public EpsilonEqualStateBuilder() {
		
	}
	
	@Override
	protected Object createTheInstance(JSONObject info) {
		if (info.isEmpty()) {
			this.comparador = new EpsilonEqualStates();	
		}else {
			this.comparador = new EpsilonEqualStates(info.getDouble("eps"));	
		}
		return this.comparador;
	}
	
	
	protected JSONObject createData(){
		JSONObject force = new JSONObject();
		force.put("type", "epseq");
			JSONObject data = new JSONObject();
			data.put("eps", 0.1);
		force.put("data", data);
		force.put("desc", "Comparador de igualdad, modulo epsilon");
		return force;
	}
}
