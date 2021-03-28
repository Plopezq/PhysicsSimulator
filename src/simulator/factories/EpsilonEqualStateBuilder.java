package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;
import simulator.model.NewtonUniversalGravitation;

public class EpsilonEqualStateBuilder extends Builder<StateComparator> {

	private EpsilonEqualStates comparador;
	
	
	public EpsilonEqualStateBuilder() {
		
	}
	
	@Override
	protected Object createTheInstance(JSONObject info) {
		if (info == null) {
			this.comparador = new EpsilonEqualStates();	
		}else {
			this.comparador = new EpsilonEqualStates(info.getDouble("eps"));	
		}
		return this.comparador;
	}
	
	
	protected JSONObject createData(){
		
		//TODO
		return null;
	}
}
