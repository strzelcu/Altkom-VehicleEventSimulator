package ves.exceptions;

/**
 * Wyjątek nieznalezionego adresu przez GraphHopperService
 */
public class AddressNotFoundException extends Exception{
    public AddressNotFoundException(String message){
        super(message);
        printStackTrace();
    }
}
