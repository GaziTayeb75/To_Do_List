/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package to_do_list;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    
   private Connection connect;
   private PreparedStatement prepare;
   private ResultSet result;
   private Statement statement;
   
   public Connection connectDB(){
       
       try{
           Class.forName("com.mysql.jdbc.Driver");
           Connection connect
                   = DriverManager.getConnection("jdbc:mysql://localhost/useraccount","root","");
           return connect;
       }catch(Exception e){e.printStackTrace();}
  return null;
   }
   
   
   
   public void login(){
       
   }
   
   public void register(){
       alertMessage alert = new alertMessage();
       //check we have empty fields
       
       if(signup_email.getText().isEmpty() || signup_username.getText().isEmpty()
               || signup_password.getText().isEmpty() || signup_cPassword.getText().isEmpty()
               || signup_selectQuestion.getSelectionModel().getSelectedItem() == null
               || signup_answer.getText().isEmpty()){
           alert.errorMessage("All fields are necessary to be filled ");
       } else if(signup_password.getText() == signup_cPassword.getText()){  
           //check if the value of password fields is equal to confirm password
           alert.errorMessage("Password does not match");
           
       }else if(signup_password.getText().length()<8){
          //check tf the length of password value if less then to 8, check The characters of password 
       
      alert.errorMessage("invalid Password,at least 8 characters needed");
       
       }else{
           //check if the username is already taken
           String checkUsername = "SELECT * FROM users WHERE username ='"
                   +signup_username.getText()+"'";
            connect = connectDB();
             try{
                  statement = connect.createStatement();
           result = statement.executeQuery(checkUsername);
           
           if(result.next()){
               alert.errorMessage(signup_username.getText() + "is already taken");
           }else{
               
               String insertData = "INSERT INTO USER "
                    +"(email,username,password,question,answer,date)"
                    +"VALUES(?,?,?,?,?,?)";//
               
               prepare = connect.prepareStatement(insertData);
              prepare.setString(1, signup_email.getText());
               prepare.setString(2, signup_username.getText());
               prepare.setString(3, signup_password.getText());
               prepare.setString(4,
                       (String) signup_selectQuestion.getSelectionModel().getSelectedItem());
               prepare.setString(5, signup_answer.getText());
              
               Date date = new Date();
               java.sql.Date sqlDate = new java.sql.Date(date.getTime());
               
               prepare.setString(6, String.valueOf(sqlDate));
               
               prepare.executeUpdate();
               
               alert.successMessage("Registered Successfully!!");
               
               
           
           }
             }catch(Exception e){
                 e.printStackTrace();
             
          }
          
       }
         
   }
   
   private String[] questionList = {"What is your favorite food?","What is your favorite color?",
         "What is the name of your pet?", "What is your most favorite sport?"};
   
           public void questions(){
              List<String> listQ = new ArrayList<>();
              
              for(String data: questionList){
                  listQ.add(data);
              }
              ObservableList listData = FXCollections.observableArrayList(listQ);
         signup_selectQuestion.setItems(listData);
           }

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       questions();
    }    
    
}
