package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

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
		
		//TODO
		return null;
	}
}
