package com.example.demo1;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Server  extends Thread{
    byte[] bytes=new byte[256];
    int port=1222;
    //static ArrayList<Integer> users=new ArrayList<>();
    static ArrayList<Session> connectedPerson=new ArrayList<>();
    DatagramSocket socket;
    InetAddress addr;
    DatagramPacket packet;
    static ArrayList<MessagwAncien> lastmsg=new ArrayList<>();
    static ArrayList<String> ami=new ArrayList<>();
    public Server() throws Exception {
        socket=new DatagramSocket(port);

        addr=InetAddress.getByName("localhost");
    }

    public void ReceiveMessage() throws IOException, SQLException, ClassNotFoundException {

        DatagramPacket packet= new DatagramPacket(bytes,bytes.length);
        socket.receive(packet);
        System.out.println("hi serveur ");
        String message=new String(packet.getData());
        bytes=new byte[256];
        System.out.println("MESSAGE bien recu "+message);
        int port_user=packet.getPort();

        if(message.contains("/")) {

            Register(message,port_user);
        }else if(message.contains("-")){
            Connexion(message,port_user);
        }
        else if(message.contains(":")){
            Chatter(message,port_user);
        }else if(message.contains("deconnexion")){
            deconnecterClient(message,port_user);
        }
        else{
            AddAmie();
        }

        }
        public void AddAmie(){

        }
        public void deconnecterClient(String msg,int port){
        String[] str=msg.split("@");

        String nom=str[1].trim();
        Session Anciensession=new Session(nom,port);
            System.out.println("deconnecter "+Anciensession.nom);


            connectedPerson.remove(Anciensession);

        }

     public void Chatter(String msg,int port_user) throws IOException, SQLException, ClassNotFoundException {


         String[] message = msg.split(":");
         String Sender = message[0];

         String msg1=message[1];
         String receiver=message[2].trim();
         /*Connexion.sql = "SELECT nom_user1 FROM  ami WHERE  nom_user2='" + Sender + "'";
         Connexion.stmt = Connexion.conn.createStatement();
         Connexion.rs = Connexion.stmt.executeQuery(Connexion.sql);
         System.out.println(Connexion.rs);
         String nomAmi=null;
         while(Connexion.rs.next()) {
             nomAmi = Connexion.rs.getString(1);
             System.out.println(nomAmi);
             ami.add(nomAmi);



         }*/
         String messagetoSend=Sender+":"+msg1.trim();
         byte[] byteMessage = messagetoSend.getBytes();

         for (int i=0;i<connectedPerson.size();i++) {
              System.out.println(connectedPerson.get(i).nom);
             if ( !connectedPerson.get(i).nom.contains(Sender) && connectedPerson.get(i).nom.contains(receiver) ){

                 DatagramPacket forward = new DatagramPacket(byteMessage, byteMessage.length, addr,connectedPerson.get(i).port);
                 socket.send(forward);
                 System.out.println("MESSAGE a envoye a : " +  connectedPerson.get(i).port + " " + msg1.trim());
                 System.out.println("Sender : " +  Sender + " receiver"+ receiver);}
             else if(!connectedPerson.get(i).nom.contains(receiver)){
                 MessagwAncien msgAncien=new MessagwAncien(receiver,msg.trim());
                 lastmsg.add(msgAncien);


         } }}

    public void Register(String msg,int port) throws SQLException, ClassNotFoundException {
        String[] words = msg.split("/");
        String Nom=words[0];
        String Password=words[1];
        Session ami=new Session(Nom,port);
        connectedPerson.add(ami);
        Connexion.Connection();
        Connexion.sql="INSERT INTO `user` (`nom`, `password`) VALUES ('"+Nom+"','"+Password+"')";
        Connexion.stmt=Connexion.conn.createStatement();
        Connexion.stmt.executeUpdate(Connexion.sql);
        Connexion.conn.close();
        Connexion.stmt.close();
    }

    public void  Connexion(String msg,int port) throws SQLException, ClassNotFoundException, IOException {
        //users.add(port);
        String[] words = msg.split("-");
        String Nom=words[0];
        String Password=words[1];
        Session Ami=new Session(Nom,port);
        connectedPerson.add(Ami);
        Connexion.Connection();
        Connexion.sql="SELECT nom,password FROM  user WHERE  nom='"+Nom+"' and password= '"+Password+"'";
        Connexion.stmt=Connexion.conn.createStatement();
        Connexion.rs=Connexion.stmt.executeQuery(Connexion.sql);
        String response;
        if(Connexion.rs.next()){
            System.out.println("ce compte est  enregistrer");


          response="true";


            for (int i=0;i<lastmsg.size();i++){
                if(lastmsg.get(i).nom.contains(Nom)){
                    byte[] last=lastmsg.get(i).message.getBytes();
                    DatagramPacket forward = new DatagramPacket(last,last.length, addr,port);
                    socket.send(forward);
                }
            }




        }else{
           response="false";
            System.out.println("ce compte n'est pas enregistré");
            //String s="ce compte n'est pas enregistré";

        }
        Connexion.sql = "SELECT nom_user1 FROM  ami WHERE  nom_user2='" + Nom + "'";
        Connexion.stmt = Connexion.conn.createStatement();
        Connexion.rs = Connexion.stmt.executeQuery(Connexion.sql);
        System.out.println(Connexion.rs);
        String nomAmi=null;
        while(Connexion.rs.next()) {
            nomAmi = Connexion.rs.getString(1);
            System.out.println(nomAmi);

                if(!ami.contains(nomAmi)){
                ami.add(nomAmi);
           }


        }
        String listAmi=String.join("&",ami);
        String listAmiAndresponse=response.trim()+"&"+listAmi.trim();
        byte[] byteresponse=listAmiAndresponse.getBytes();
        DatagramPacket Response = new DatagramPacket(byteresponse, byteresponse.length, addr,port);
        socket.send(Response);
        Connexion.conn.close();
        Connexion.stmt.close();
        ami=new ArrayList<>();
    }

    public void run() {

        while(true) {
            try {

                ReceiveMessage();
            } catch (IOException | SQLException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
