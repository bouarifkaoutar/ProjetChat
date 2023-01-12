package com.example.demo1;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;


public class MainClient  extends Application{
    static final TextArea messageArea = new TextArea();
    static final Button btnConnexion=new Button("Se connecter");
    static final Button btnRegister=new Button("register");
    static final Button btnAddAmie=new Button("ajouterAmie");
    static final Button btnSend=new Button("Send");
    static final Button btndeconnexion=new Button(" se Deconnecter ");
    static final TextField inputMessage = new TextField();
    static final TextField inputNom = new TextField();
    static final TextField inputpassword = new TextField();
    static final TextField inputMessageServer = new TextField();
    static   final ComboBox<String> choixami1=new ComboBox<>();

    public static void main(String[] args) throws Exception {

        SendingMessage C1=new SendingMessage(1222,inputNom,inputpassword,btnConnexion,btnRegister,btnAddAmie,messageArea,inputMessage,inputMessageServer,choixami1,btnSend,btndeconnexion);
        C1.start();
        launch();

    }

    @Override
    public void start(Stage primaryStage) {
      messageArea.setMaxWidth(500);
      messageArea.setManaged(false);
      inputMessage.setManaged(false);
      inputMessageServer.setManaged(false);
      btndeconnexion.setManaged(false);
      btnAddAmie.setManaged(false);
      messageArea.setEditable(false);
      inputMessage.setMaxWidth(400);
      inputNom.setMaxWidth(250);
      inputpassword.setMaxWidth(250);
      choixami1.setManaged(false);
      btnSend.setManaged(false);
      HBox hbox=new HBox(20,inputMessage ,btnSend);
      VBox vbox=new VBox(20, new VBox(35,inputNom,inputpassword,btnConnexion,btnRegister,btnAddAmie,choixami1,messageArea),hbox,inputMessageServer,btndeconnexion);
        vbox.setStyle("-fx-padding: 16;-fx-color:blue;-fx-border-radius:60 60 60 60 ");

        Scene scene = new Scene(vbox, 550, 300);
        hbox.setMargin(hbox,new Insets(100,20,20,50));
        primaryStage.setTitle("SendClient");
        primaryStage.setScene(scene);

        primaryStage.show();

        }
}
