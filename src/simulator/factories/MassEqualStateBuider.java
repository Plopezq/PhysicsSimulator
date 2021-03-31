package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.MassEqualStates;
import simulator.control.StateComparator;
import simulator.misc.Vector2D;

public class MassEqualStateBuider extends Builder<StateComparator>{

	private MassEqualStates comparador;
	
	public MassEqualStateBuider() {
		
	}

	@Override
	protected Object createTheInstance(JSONObject info) {
		this.comparador = new MassEqualStates();	
		return this.comparador;
	}
	
	protected JSONObject createData(){
		JSONObject comparator = new JSONObject();
		comparator.put("type", "masseq");
			JSONObject data = new JSONObject(); //VACIO
			comparator.put("data", data);
		comparator.put("desc", "Compara los estados de dos cuerpos");
		return comparator;
	}
}
