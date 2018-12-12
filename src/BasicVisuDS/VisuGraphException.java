package BasicVisuDS;

public class VisuGraphException extends Exception {
    private String info;
    VisuGraphException(String info){
        this.info=info;
    }

    public String getInfo(){
        return info;
    }
}
