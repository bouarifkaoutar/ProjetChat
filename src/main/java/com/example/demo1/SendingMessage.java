package com.example.demo1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SendingMessage   extends Thread {
    DatagramSocket socket;
    int portServer;
    byte[] bytes =new byte[256];
    TextArea messageText;
    TextField messageServer;
    TextField inputNom;
    TextField inputPassword;
    TextField inputBox;
   ComboBox<String> choixami;
    Button btnConnexion;
    Button btnRegister;
    Button btnAddfriend;
    Button btnSend;
    Button btnDeconnexion;
    InetAddress addr=InetAddress.getByName("localhost");
    DatagramPacket packet;
    Scene scene;
    static int NotConnected;
    DatagramPacket packetR;
    ArrayList<String> listami;

    public SendingMessage(int portServer,TextField inputBox1,TextField inputBox2,Button btn1,Button btn2,Button AddFriend,TextArea messageText, TextField inputBox,TextField txt,ComboBox<String> choixami,Button btnSend,Button btnDeconnexion) throws Exception {
        NotConnected=-1;
        this.choixami=choixami;
        this.btnSend=btnSend;
        this.messageServer=txt;
        this.btnAddfriend=AddFriend;
        this.portServer=portServer;
        this.btnConnexion=btn1;
        this.btnRegister=btn2;
        this.inputNom=inputBox1;
        this.inputPassword=inputBox2;
        this.messageText=messageText;
        this.inputBox=inputBox;
        this.btnDeconnexion=btnDeconnexion;
        this.portServer=portServer;
        socket=new DatagramSocket();



    }

	/*public void SendMessage(String message) throws IOException {
		bytes=message.getBytes();
		DatagramPacket packet=new DatagramPacket(bytes,bytes.length,addr,port);
		socket.send(packet);

	}*/
    public void sendMessage(String s){
        String Nom=inputNom.getText();
        String password=inputPassword.getText();

        String NomETpassord=Nom+s+password;
        byte[] bytes=NomETpassord.getBytes();
        /*inputNom.setText("");*/
        inputPassword.setText("");
        packet=new DatagramPacket(bytes,bytes.length,addr,this.portServer);

    }
    public void Deconnexion(){

        String Nom=inputNom.getText();
        String deconnecter="deconnexion"+"@"+Nom;
        byte[] bytes=deconnecter.getBytes();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, addr, this.portServer);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.inputNom.setManaged(true);
        this.inputNom.setVisible(true);
        this.inputPassword.setManaged(true);
        this.inputPassword.setVisible(true);
        this.btnConnexion.setManaged(true);
        this.btnRegister.setVisible(true);
        this.messageServer.setVisible(true);
        this.messageText.setManaged(false);
        this.messageText.setVisible(false);
        this.choixami.setManaged(false);
        this.choixami.setVisible(false);
        this.inputBox.setManaged(false);
        this.btnSend.setManaged(false);
        this.btnSend.setVisible(false);
        this.btnDeconnexion.setManaged(false);
        this.btnDeconnexion.setVisible(false);

    }
    public void RecieveMessage() throws IOException {

        packetR= new DatagramPacket(bytes,bytes.length);
        socket.receive(packetR);

        String str=new String(packetR.getData());


       if(str.startsWith("true")) {

           ObservableList<String> list2= FXCollections.observableArrayList();
           String[] list1=str.split("&");
           for(String ami:list1){
               if(!ami.contains(inputNom.getText())){
               list2.add(ami);
           }}
           list2.remove(0);

           this.inputNom.setManaged(false);
           this.inputNom.setVisible(false);
           this.inputPassword.setManaged(false);
           this.inputPassword.setVisible(false);
           this.btnConnexion.setManaged(false);
           this.btnRegister.setVisible(false);
           this.messageServer.setVisible(false);
           this.messageText.setManaged(true);
           this.messageText.setVisible(true);
           this.inputBox.setManaged(true);
           this.btnSend.setManaged(true);
           this.btnSend.setVisible(true);
           this.btnDeconnexion.setManaged(true);
           this.btnDeconnexion.setVisible(true);
          // this.choixami.setItems(list);
           this.choixami.setItems(list2);
           this.choixami.setManaged(true);
           this.choixami.setVisible(true);
           bytes=new byte[256];
       }else if(str.startsWith("false")) {
           this.messageServer.setManaged(true);
           messageServer.setText("ce compte n'est pas enregistré ");
           NotConnected = 0;
       }else {
        System.out.println("le message :"+str);
           String[] msg=str.split(":");
        messageText.setText(messageText.getText()+"\n"+msg[0]+":"+msg[1]+"\n");
           bytes=new byte[256];
    }}

    public void run() {

            btnSend.setOnAction(event -> {


                    String temp = inputBox.getText(); // message to send
                    String nom=inputNom.getText();
                    String Receiver=choixami.getValue();
                    //String password=inputPassword.getText();

                    String message=nom+":"+temp+":"+Receiver;
                    System.out.println(message);
                    messageText.setText(messageText.getText() + nom+": "+inputBox.getText() + "\n"); // update messages on screen
                    byte[] msg = message.trim().getBytes(); // convert to bytes

                    DatagramPacket packet = new DatagramPacket(msg, msg.length, addr, this.portServer);
                    try {
                        socket.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
               inputBox.setText(""); // remove text from input box




                } );

            btnRegister.setOnAction((keyEvent) ->{
                messageText.setText("");
                 sendMessage("/");
                try {
                    socket.send(packet);
                    System.out.println("le message est envoyé ");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } );
            btnConnexion.setOnAction((actionEvent) -> {
                messageText.setText("");
                sendMessage("-");
                try {
                    socket.send(packet);
                    System.out.println("le message est envoyé ");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            /*btnAddfriend.setOnAction((actionEvent) -> {
                messageText.setText("");
                sendMessage("");
            });*/
            btnDeconnexion.setOnAction((e)->{
             Deconnexion();
            });
            while(true){
        try {
            RecieveMessage();

        } catch (IOException e) {
            e.printStackTrace();
        } ;

}}}













