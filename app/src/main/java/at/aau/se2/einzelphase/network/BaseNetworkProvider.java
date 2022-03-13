package at.aau.se2.einzelphase.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author Andreas Wölbl
 * Abstract base class providing networking features.
 */
public abstract class BaseNetworkProvider {

    protected BufferedReader in;
    protected DataOutputStream out;
    protected Socket socket;

    private final ExecutorService executor;

    private String server;
    private Integer port;
    protected boolean isConnected;

    public BaseNetworkProvider(String server, Integer port) {
        executor = Executors.newSingleThreadExecutor();
        this.server = server;
        this.port = port;
    }

    /**
     * Runs the given {@link Callable} function in the background and passes the
     * result to the given {@link Callback} function onResult or a possible exception to the {@link Callback} function onError.
     *
     * @param callable The function to run in the background
     * @param onResult The function that receives the result
     * @param onError  The function that receive exceptions to handle
     */
    protected <T> void doInBackground(Callable<T> callable, Callback<T> onResult, Callback<Exception> onError) {
        try {
            executor.execute(() -> {
                try {
                    openConnection(server, port);
                    onResult.notify(callable.call());
                } catch (Exception e) {
                    onError.notify(e);
                } finally {
                    if (socket != null)
                        try {
                            closeConnection();
                        } catch (IOException e) {
                            onError.notify(e);
                        }
                }
            });
        } catch (RejectedExecutionException e) {
            onError.notify(e);
        }
    }

    /**
     * Creates a new socket with given server domain and port and opens an input stream of type {@link BufferedReader}
     * and an output stream of type {@link DataOutputStream}
     *
     * @param server The server domain
     * @param port The port
     * @throws IOException
     */
    private void openConnection(String server, int port) throws IOException {
        socket = new Socket(server, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new DataOutputStream(socket.getOutputStream());
        isConnected = true;
    }

    /**
     * Closes the socket and its underlying streams
     * @throws IOException
     */
    public void closeConnection() throws IOException {
        socket.close();
        isConnected = false;
    }

    public void shutdownExecutor() {
        executor.shutdown();
    }

    public boolean isConnected() {
        return isConnected;
    }
}