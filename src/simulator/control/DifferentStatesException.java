package simulator.control;

public class DifferentStatesException extends Exception {
	

   private int codigoError;
     
    public DifferentStatesException(int codigoError){
        super();
        this.codigoError=codigoError;
    }
     
    @Override
    public String getMessage(){

        String mensaje="";

        switch(codigoError){
            case 0:
                mensaje="Los estados NO son iguales";
                break;
        }
         
        return mensaje;
         
    }
	

	
}
