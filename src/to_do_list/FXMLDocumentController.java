package to_do_list;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class FXMLDocumentController implements Initializable {

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
    private ComboBox<String> signup_selectQuestion;
    @FXML
    private TextField signup_answer;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    // Database connection method
    public Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/useraccount", "root", "");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // User registration
    public void register() {
        alertMessage alert = new alertMessage();

        // Check if fields are empty
        if (signup_email.getText().isEmpty() || signup_username.getText().isEmpty() ||
            signup_password.getText().isEmpty() || signup_cPassword.getText().isEmpty() ||
            signup_selectQuestion.getSelectionModel().getSelectedItem() == null || signup_answer.getText().isEmpty()) {

            alert.errorMessage("All fields are necessary to be filled.");
            return;
        }

        // Check if passwords match
        if (!signup_password.getText().equals(signup_cPassword.getText())) {
            alert.errorMessage("Passwords do not match.");
            return;
        }

        // Check if password length is valid
        if (signup_password.getText().length() < 8) {
            alert.errorMessage("Invalid password. At least 8 characters are required.");
            return;
        }

        // Check if username is already taken
        String checkUsername = "SELECT * FROM users WHERE username = ?";
        connect = connectDB();
        try {
            prepare = connect.prepareStatement(checkUsername);
            prepare.setString(1, signup_username.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                alert.errorMessage(signup_username.getText() + " is already taken.");
            } else {
                // Insert user data into database
                String insertData = "INSERT INTO users (email, username, password, question, answer, date) VALUES (?, ?, ?, ?, ?, ?)";
                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, signup_email.getText());
                prepare.setString(2, signup_username.getText());
                prepare.setString(3, signup_password.getText());
                prepare.setString(4, signup_selectQuestion.getSelectionModel().getSelectedItem());
                prepare.setString(5, signup_answer.getText());

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setDate(6, sqlDate);

                prepare.executeUpdate();
                alert.successMessage("Registered successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Question list initialization
    private final String[] questionList = {
        "What is your favorite food?",
        "What is your favorite color?",
        "What is the name of your pet?",
        "What is your most favorite sport?"
    };

    public void questions() {
        ObservableList<String> listData = FXCollections.observableArrayList(questionList);
        signup_selectQuestion.setItems(listData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        questions();
    }
}
