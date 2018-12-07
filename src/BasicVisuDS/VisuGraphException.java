package BasicVisuDS;

public class VisuGraphException extends Exception {
    String info;
    VisuGraphException(String info){
        this.info=info;
    }

    public String getInfo(){
        return info;
    }
}
