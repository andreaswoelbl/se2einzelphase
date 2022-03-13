package at.aau.se2.einzelphase.network;

/**
 * @author Andreas Wölbl
 * Class to implement concrete networking features
 */
public class TCP extends BaseNetworkProvider {

    public TCP(String server, Integer port) {
        super(server, port);
    }

    /**
     * Writes the String outStr to the output stream and reads the input stream afterwards
     * @param outStr The string to write to the stream
     * @param onResult The function to receive the result
     * @param onError The function to receive exceptions
     */
    public void writeThenRead(String outStr, Callback<String> onResult, Callback<Exception> onError) {
        super.doInBackground(() -> {
            out.writeBytes(outStr.concat("\n"));
            return in.readLine();
        }, onResult, onError);
    }
}
