package cryorbiter.mojito_spb.exception;

public class NonTrouve extends Exception{
    private final Object key;

    public NonTrouve(String type, Object key){
        super("Non trouve [" + type + "] : " + key);
        this.key = key;
    }
    public Object getKey() {
        return key;
    }

}
