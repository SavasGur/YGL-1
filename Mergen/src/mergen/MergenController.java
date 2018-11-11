/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mergen;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author mergenCrew
 */
public class MergenController implements Initializable {

    @FXML
    BorderPane browserBP;
    @FXML
    WebView browserWV;
    @FXML
    ImageView stopReloadIV;
    @FXML
    TextField addressBarTF;
    @FXML
    ProgressIndicator progressPI;
    @FXML
    Label statusL;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {                          
        browserWV.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>(){
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                statusL.setText("Yükleniyor... " + browserWV.getEngine().getLocation());                
                stopReloadIV.setImage(new Image(getClass().getResourceAsStream("/images/stoploading.png")));
                progressPI.setVisible(true);
                if(newValue == Worker.State.SUCCEEDED) {
                    addressBarTF.setText(browserWV.getEngine().getLocation());
                    statusL.setText("Yüklendi.");
                    progressPI.setVisible(false);
                    stopReloadIV.setImage(new Image(getClass().getResourceAsStream("/images/reload.png")));
                    if(browserBP.getParent() != null) {
                        TabPane tp = (TabPane)browserBP.getParent().getParent();                           
                        for(Tab tab : tp.getTabs()) {
                            if(tab.getContent() == browserBP) {
                                tab.setText(browserWV.getEngine().getTitle());
                                break;
                            }
                        }                                                
                    }
                }
                
            }
            
        });         
    }   
    
    
    
    @FXML
    private void browserBackButtonAction(ActionEvent event) {
        if(browserWV.getEngine().getHistory().getCurrentIndex() <= 0) {
            return;
        }
        browserWV.getEngine().getHistory().go(-1);        
    }
    
    @FXML
    private void browserForwardButtonAction(ActionEvent event) {
        if((browserWV.getEngine().getHistory().getCurrentIndex() + 1) >= browserWV.getEngine().getHistory().getEntries().size()) {
            return;
        }
        browserWV.getEngine().getHistory().go(1);
    }
    
    @FXML
    private void browserGoButtonAction(ActionEvent event) throws FileNotFoundException {
        String url = addressBarTF.getText().trim();
        
        if(url.isEmpty()) {
            JOptionPane.showMessageDialog(null, "URL belirtilmedi.");
            return;
        }
        if(!url.startsWith("http://") && !url.startsWith("https://")) {
         url = "http://"+url;   
        }
        browserWV.getEngine().load(url);
        //browserStopReloadIV.setImage(new Image(getClass().getResourceAsStream("/images/stoploading.png")));
        
        
    }
    
    @FXML
    private void browserStopReloadButtonAction(ActionEvent event) {                
        if(browserWV.getEngine().getLoadWorker().isRunning()) {
            browserWV.getEngine().getLoadWorker().cancel();
            statusL.setText("Yüklendi.");
            progressPI.setVisible(false);            
            stopReloadIV.setImage(new Image(getClass().getResourceAsStream("/images/reload.png")));
        } else {            
            browserWV.getEngine().reload();
            stopReloadIV.setImage(new Image(getClass().getResourceAsStream("/images/stoploading.png")));
        }
        
    }
    
    @FXML
    private void browserHomeButtonAction(ActionEvent event) {
        browserWV.getEngine().loadContent("<html><title>Yeni Sekme</title></html>");
        addressBarTF.setText("");        
    }
    
    
    
}
