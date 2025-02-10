/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxtaskwindow;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author user
 */
public class HomeController implements Initializable {

    @FXML
    private TextField tfSearch;
    @FXML
    private Label lblProjectCount;
    @FXML
    private Label lblName;
    @FXML
    private Label lblGreeting;
    @FXML
    private Button btnEX;
    @FXML
    private Label lblName1;
    @FXML
    private Label lblGreeting1;
    @FXML
    private Label lblUpcoming;
    @FXML
    private VBox vTaskItemsupcoming;
    @FXML
    private Label lblToday;
    @FXML
    private VBox vTaskItems;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void closeWindow(MouseEvent event) {
    }
    
}
