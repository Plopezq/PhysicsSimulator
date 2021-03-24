package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {

	
	private T objeto;
	Builder(){
		
	}
	
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
			return (T) new BasicBodyBuilder().createInstance((JSONObject) info.get("type"));
		}else if(aux.equals("mlb")) {
			
		}else if(aux.equals("nlug")) {
			
		}else if(aux.equals("mtcp")) {
			
		}else if(aux.equals("nf")) {
			
		}else if(aux.equals("masseq")) {
			
		}else if(aux.equals("epseq")) {
			
		}else {
			//No hay ningun objeto de ese tipo
			return null;
		}
		
		
		//Comprobamos los valores de la seccion data son correctos --> eso se hara en el propio objeto
		
		
		
		
		
		return null;
	}
	
	public JSONObject getBuilderInfo() {
		
		/* devuelve un objeto JSON que sirve de plantilla para el 
		 * correspondiente constructor, i.e., un valor válido para el parámetro de 
		 * createInstance (ver getInfo() de Factory<T>).*/
		
		
		
		
		return null;
	}
	
	
	
}
