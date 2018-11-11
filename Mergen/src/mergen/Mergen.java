/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mergen;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.WindowEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author mergenCrew
 */
public class Mergen extends Application {
    
    
    
    TabPane root;
            
    @Override
    public void start(Stage stage) throws IOException {                
        Parent browser = FXMLLoader.load(getClass().getResource("Mergen.fxml"));
        Tab browserTab = new Tab("Yeni Sekme", browser);
        Tab addTab = new Tab("+", null);
        addTab.setClosable(false);        
        addTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                addNewTab();
            }
        });
        root = new TabPane(browserTab, addTab);
        Scene scene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("/images/mrgnicon.png"));
        stage.getIcons().add(icon);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        stage.setScene(scene);
        stage.setTitle("Mergen");
        stage.show();
                
    }

    final void addNewTab() {
        try {
            Parent browser = FXMLLoader.load(getClass().getResource("Mergen.fxml"));
            Tab browserTab = new Tab("Yeni Sekme", browser);
            root.getTabs().add(root.getTabs().size() - 1, browserTab);
            root.getSelectionModel().select(browserTab);
        } catch (IOException ex) {
            Logger.getLogger(Mergen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
         
    
}
