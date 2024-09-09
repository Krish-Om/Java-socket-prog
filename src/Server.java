import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public Server(){
        connections = new ArrayList<>();
        done = false;
    }
    @Override
    public void run() {
//        System.out.println("Server started!");
        try {
                server = new ServerSocket(9999);
                pool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler connectionHandler = new ConnectionHandler(client);
                connections.add(connectionHandler);
                pool.execute(connectionHandler);
            }
        } catch (Exception e) {
            shutDown();
        }
    }

    public void broadcast(String message){
        for(ConnectionHandler ch: connections){
            if(ch != null){
                ch.sendMessage(message);
            }
        }
    }

    public void shutDown(){
        try {
        done = true;
        pool.shutdown();
        if(!server.isClosed()){
            server.close();
        }
        for(ConnectionHandler ch: connections){
            ch.shutDown();
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    class ConnectionHandler implements Runnable{
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ConnectionHandler(Socket client){
            this.client = client;
        }
        @Override
        public void run(){
            try {
                out = new PrintWriter(client.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please enter a nick-name: ");
//                if(nickname != null){
//                }
                nickname = in.readLine();
                System.out.println(nickname + " connected!");
                broadcast(nickname+" joined the chat!");
                String message;
                while ((message = in.readLine())!= null){
                    if(message.startsWith("/nick ")){
                        //TODO: handle nickname
                        String[] messageSplit = message.split("", 2);
                        if(messageSplit.length ==2){
                            broadcast(nickname+" renamed themselves to "+ messageSplit[1]);
                            System.out.println(nickname+" renamed themselves to "+ messageSplit[1]);
                            nickname= messageSplit[1];
                            out.println("Successfully changed nickname to :" + nickname);
                        }else{
                            out.println("no nick name provided");
                        }
                    }else if(message.startsWith("/quit")){
                        broadcast(nickname + ": left the chat");
                        shutDown();
                        //TODO: handle quit

                    }else{
                        broadcast(nickname+ " : "+ message );
                    }
                }
//                in.readLine();
//                out.println("Hello to the client");
            }catch (IOException e){
                shutDown();
            }
        }

        public void sendMessage(String message){
            out.println(message);

        }

        public void shutDown(){
            try{
            in.close();
            out.close();
            if(!client.isClosed()){
                client.close();
            }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server1 = new Server();
        server1.run();
    }
}
