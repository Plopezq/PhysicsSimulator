package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;


public class BuilderBasedFactory<T> implements Factory<T> {

	List<Builder<T>> constructores; //Lista de constructores

	public BuilderBasedFactory(List<Builder<T>> listaBuilder) {
		this.constructores = new ArrayList<>(listaBuilder);
	}

	@Override
	public T createInstance(JSONObject info) {
		/*
		 * public T createInstance(JSONObject info): recibe una structura JSON que
		 * describe el objeto a crear (ver la sintaxis más abajo), y devuelve una
		 * instancia de la clase correspondiente – una instancia de un subtipo de T. En
		 * caso de que info sea incorrecto, entonces lanza una excepción del tipo
		 * IllegalArgumentException.
		 */
//		El método createInstance de la factoría ejecuta los constructores uno a uno 
//		hasta que encuentre el constructor capaz de crear el objeto correspondiente — 
//		debe lanzar una excepcion IllegalArgumentException en caso de fallo.
		T obj = null;
		//System.out.println(info);
		for(Builder<T> build : this.constructores) {
			obj = build.createInstance(info);
			if (obj != null) return obj;
			else throw new IllegalArgumentException();
		}
		return obj;
	}

	@Override
	public List<JSONObject> getInfo() {
		/*
		 * public List<JSONObject>getInfo(): devuelve una lista de objetos JSON, que son
		 * “plantillas” para structuras JSON válidas. Los objetos de esta lista se
		 * pueden pasar como parámetro al método createInstance. Esto es muy útil para
		 * saber cuáles son los valores válidos para una factoría concreta, sin saber
		 * mucho sobre la factoría en si misma. Por ejemplo, utilizaremos este método
		 * cuando mostremos al usuario los posibles valores de las leyes de la gravedad.
		 */
		// El método getInfo() devuelve en una lista las estructuras JSON devueltas por getBuilderInfo().
		List<JSONObject> lista = new ArrayList<JSONObject>();
		for(Builder<T> build : this.constructores) {
			JSONObject aux = build.getBuilderInfo();
			lista.add(aux);
		}
		return lista;
	}

}
