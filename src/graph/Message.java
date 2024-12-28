
package graph;
import java.util.Date;

public class Message {
    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

    public Message(String str){
        if(str == null){
            throw new  IllegalArgumentException("Null String");
        }
        this.data = str.getBytes();
        this.asText = str;
        this.date = new Date();
        this.asDouble = parseDouble(str);
    }

    public Message(double num){
        this(Double.toString(num));
    }

    public Message(byte[] b){
        this(new String(b));
    }

    private static double parseDouble(String str) {
        try{
            return Double.parseDouble(str);
        }
        catch(NumberFormatException e) {
            return Double.NaN;
        }
    }

}
