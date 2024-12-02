package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        TCP.TCP();

    }
}


class TCP {

    static void TCP() throws IOException {

         Socket socket = null;
         BufferedReader bufferedReader=null;
         InputStreamReader inputStreamReader=null;
         OutputStreamWriter outputStreamWriter=null;
         BufferedWriter bufferedWriter=null;


        try {

            socket= new Socket("localhost",1234);
            inputStreamReader=new InputStreamReader(socket.getInputStream());
            outputStreamWriter=new OutputStreamWriter(socket.getOutputStream());
            bufferedReader=new BufferedReader(inputStreamReader);
            bufferedWriter= new BufferedWriter(outputStreamWriter);

            Scanner scanner= new Scanner(System.in);

            while (true){
                String msg=scanner.nextLine();
                bufferedWriter.write(msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                System.out.println("Server" + bufferedReader.readLine());

                if(msg.equalsIgnoreCase("bye")){
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            assert socket != null;
            socket.close();
            assert bufferedReader != null;
            bufferedReader.close();
            assert bufferedWriter != null;
            bufferedWriter.close();
            inputStreamReader.close();
            outputStreamWriter.close();


        }
    }


}