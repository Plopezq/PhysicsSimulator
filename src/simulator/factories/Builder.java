package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {

	
	//private T objeto;
	
	public Builder(){
		
	}
	
	@SuppressWarnings("unchecked")
	public T createInstance(JSONObject info) {
		//si la información suministrada por info es correcta, 
		//entonces crea un objeto de tipo T
		//En otro caso devuelve null para indicar 
		//que este constructor es incapaz de reconocer ese formato.
		/* En caso de que reconozca el campo type pero haya un error 
		 * en alguno de los valores suministrados por la sección data, 
		 * el método lanza una excepcion IllegalArgumentException.*/
	
		//Comprobamos que el campo type del JSON es correcto
		String aux = (String) info.get("type");
		if(aux.equals("basic")) {
			return (T) new BasicBodyBuilder().createTheInstance(info.getJSONObject("data"));
		}else if(aux.equals("mlb")) {
			return (T) new MassLosingBodyBuilder().createTheInstance(info.getJSONObject("data"));
		}else if(aux.equals("nlug")) {
			return (T) new NewtonUniversalGravitationBuilder().createTheInstance(info.getJSONObject("data"));
		}else if(aux.equals("mtfp")) {
			return (T) new MovingTowardsFixedPointBuilder().createTheInstance(info.getJSONObject("data"));
		}else if(aux.equals("nf")) {
			return (T) new NoForceBuilder().createTheInstance(info.getJSONObject("data"));
		}else if(aux.equals("masseq")) {
			return (T) new MassEqualStateBuider().createTheInstance(info.getJSONObject("data"));
		}else if(aux.equals("epseq")) {
			return (T) new EpsilonEqualStateBuilder().createTheInstance(info.getJSONObject("data"));
		}
			
		//No hay ningun objeto de ese tipo
		return null;
		//Comprobamos los valores de la seccion data son correctos --> eso se hara en el propio objeto
		//TODO
	}
	
	public JSONObject getBuilderInfo() {
		
		/* devuelve un objeto JSON que sirve de plantilla para el 
		 * correspondiente constructor, i.e., un valor válido para el parámetro de 
		 * createInstance (ver getInfo() de Factory<T>).*/
			return this.createData();
			//Deberia devolver un JSON distinto en funcion de la clase que lo llame, queda probarlo
	}
	
	protected JSONObject createData(){
		return this.createData();
		//Deberia entrar en los metodos de las hijas
	}
	
	protected abstract Object createTheInstance(JSONObject info);
	
	
}
