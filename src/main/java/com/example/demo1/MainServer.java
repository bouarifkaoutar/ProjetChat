package com.example.demo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;


public class MainServer {

    public static void main(String[] args) throws Exception  {
        Server server=new Server();
        server.start();

}


}
