/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package to_do_list;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author user
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private AnchorPane main_form;
    @FXML
    private TextField login_username;
    @FXML
    private TextField login_showPassword;
    @FXML
    private PasswordField login_password;
    @FXML
    private CheckBox login_selectShowPassword;
    @FXML
    private Button login_btn;
    @FXML
    private Button login_createAccount;
    @FXML
    private Hyperlink login_forgotPassword;
    @FXML
    private AnchorPane signup_form;
    @FXML
    private TextField signup_email;
    @FXML
    private TextField signup_username;
    @FXML
    private TextField signup_password;
    @FXML
    private TextField signup_cPassword;
    @FXML
    private Button signup_btn;
    @FXML
    private Button signup_loginAccount;
    @FXML
    private ComboBox<?> signup_selectQuestion;
    @FXML
    private TextField signup_answer;
    @FXML
    private AnchorPane forgot_form;
    @FXML
    private TextField forgot_answer;
    @FXML
    private Button forgot_proceedBtr;
    @FXML
    private Button forgot_backBtn;
    @FXML
    private ComboBox<?> forgot_selectQuestion;
    @FXML
    private TextField forgot_username;
    @FXML
    private AnchorPane changePass_form;
    @FXML
    private Button changePass_proceedBtn;
    @FXML
    private Button changePass_backBtn;
    @FXML
    private PasswordField changePass_cPassword;
    @FXML
    private PasswordField changePass_password;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
