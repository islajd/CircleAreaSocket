import service.CircleController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Server {
    private int port;
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader bufferedReader;

    public Server() throws IOException {
        this.initPort();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + serverSocket.getLocalPort());
            System.out.println("Waiting for client...");

            socket = serverSocket.accept();
            System.out.println("Client " + socket.getRemoteSocketAddress() + " connected to server");

            PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                try {
                    String messageFromClient = bufferedReader.readLine();
                    if (messageFromClient.equals("exit")) {
                        break;
                    }
                    try{
                        double radius = Double.parseDouble(messageFromClient);
                        double area = CircleController.circleArea(radius);
                        pw.println(area);
                    } catch (NumberFormatException e){
                        pw.println("Bad Request!");
                    }
                    System.out.println("Client [" + socket.getRemoteSocketAddress() + "] : " + messageFromClient);
                } catch (IOException e) {
                    System.out.println("Something went wrong.");
                    System.out.println(e);
                    break;
                }
            }
            bufferedReader.close();
            socket.close();
            System.out.println("Client " + socket.getRemoteSocketAddress() + " disconnect from server");
        } catch (IOException e) {
            System.out.println("Error : " + e);
        }
    }

    private void initPort() throws IOException {
        File configFile = new File("resource/config.properties");
        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();
        props.load(reader);
        String portStr = props.getProperty("port");
        port = Integer.parseInt(portStr);
        reader.close();
    }
}
