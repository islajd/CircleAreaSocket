package Connection;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Client {
    private String host;
    private int port;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter pw;
    private void initHost() throws IOException {
        File configFile = new File("resource/config.properties");
        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();
        props.load(reader);
        String portStr = props.getProperty("port");
        port = Integer.parseInt(portStr);
        reader.close();
    }

    public Client() throws IOException {
        initHost();
        try{
            socket = new Socket(host,port);
            System.out.println("Client started on port " + socket.getLocalPort());
            System.out.println("Connected to server[host: " + socket.getInetAddress().toString()
                    + ", port: " + socket.getPort() +"]");

            pw = new PrintWriter(socket.getOutputStream(),true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            System.out.println("Something went wrong.");
            System.out.println(e);
        }
    }

    public String getResponse() throws IOException {
        String response = bufferedReader.readLine();
        System.out.println("server response: " + response);
        return response;
    }

    public void closeConnection() throws IOException {
        pw.close();
        bufferedReader.close();
        socket.close();
    }

    public boolean sendRequest(String request){
        try{
            System.out.print("Message to server : " + request);
            pw.println(request);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
