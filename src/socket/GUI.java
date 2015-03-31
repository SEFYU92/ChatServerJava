/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Youssef
 */
public class GUI extends Application{
    public static Thread reader;
    public static Client clientst;
    public Button sendButton;
    public TextArea chat_history;
    public TextField msg_field;
    public ListView<String> friend_list;
    
    public void run(Client cl1)
    {
        clientst = cl1;
        Application.launch("");
    }
    
    public void start(Stage stage)
    {
        stage.setTitle("Chat");
	Group root = new Group();
	sendButton = new Button("Send");
	chat_history = new TextArea();
	msg_field = new TextField();	
	friend_list = new ListView<String>();
	friend_list.setPrefHeight(70);	
	chat_history.setEditable(false);	
	HBox hbox = new HBox();
	VBox vbox = new VBox();
	vbox.prefHeight(1000);	
	hbox.getChildren().addAll(new Label("Enter message : "), msg_field,sendButton);
	vbox.getChildren().addAll(chat_history, hbox, friend_list);
	Scene scene = new Scene(vbox, 400, 400);
        
        sendButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
		clientst.writemsg(msg_field.getText());
		msg_field.clear();
            }
	});
        
        reader = new Thread()
        {
            public void run()
            {
                boolean alive=true;
		while (alive) {
                String msg = clientst.readmsg();
		if(msg==null) try {
                throw new IOException();
                } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
		Platform.runLater(new Runnable() {
		public void run() {
		chat_history.appendText("\n" + msg);
		}
		});
		} 
		alive=false;
            }  
	}; 
        
	reader.start();
        stage.setScene(scene);
        stage.show();
    }
}
