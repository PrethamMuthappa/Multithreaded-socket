package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

       TCPServer tcpServer= new TCPServer();
       tcpServer.run();
    }
}

class TCPServer implements Runnable{

   public void run()  {

        ServerSocket serverSocket=null;
       try {
           serverSocket = new ServerSocket(1234);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

           while (true) {

              try {
                  Socket socket = serverSocket.accept();
                  socket.setReuseAddress(true);
                  new Thread(new Clienthandler(socket)).start();

              } catch (Exception e) {
                  e.printStackTrace();
              }
           }

    }
}

class Clienthandler implements Runnable {

    private Socket socket;

    public Clienthandler(Socket socket) {
        this.socket=socket;
    }


    @Override
    public void run() {
        InputStreamReader inputStreamReader;
        OutputStreamWriter outputStreamWriter;
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try{

            inputStreamReader= new InputStreamReader(socket.getInputStream());
            outputStreamWriter= new OutputStreamWriter(socket.getOutputStream());
            bufferedReader= new BufferedReader(inputStreamReader);
            bufferedWriter= new BufferedWriter(outputStreamWriter);

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


        while (true){

           try {
               String msgfromserver=bufferedReader.readLine();

               if(msgfromserver==null){
                   System.out.println("Client terminated");
                   break;
               }

               System.out.println(msgfromserver);

               bufferedWriter.write("  received");
               bufferedWriter.newLine();
               bufferedWriter.flush();


               if(msgfromserver.equalsIgnoreCase("bye")){
                   break;
               }
           }
           catch (Exception e){
               System.out.println(e.getMessage());
           }


        }



    }
}