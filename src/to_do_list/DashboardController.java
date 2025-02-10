package to_do_list;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private ListView<String> todoListView;
    
    @FXML
    private Button addTodoButton, updateButton, deleteButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/todo_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTodosFromDatabase();
    }

    @FXML
    private void handleAddTodo() {
        // Dialog for title
        TextInputDialog titleDialog = new TextInputDialog();
        titleDialog.setTitle("Add New Todo");
        titleDialog.setHeaderText("Enter new todo title:");
        Optional<String> titleResult = titleDialog.showAndWait();

        titleResult.ifPresent(title -> {
            if (title.trim().isEmpty()) {
                System.out.println("Todo title cannot be empty!");
            } else {
                // Dialog for description (optional)
                TextInputDialog descDialog = new TextInputDialog();
                descDialog.setTitle("Add Description");
                descDialog.setHeaderText("Enter todo description (optional):");
                Optional<String> descResult = descDialog.showAndWait();

                String description = descResult.orElse(""); // Use empty string if no description is provided
                addTodoToDatabase(title.trim(), description);
            }
        });
    }

    @FXML
    private void handleUpdateTodo() {
        String selectedTodo = todoListView.getSelectionModel().getSelectedItem();
        if (selectedTodo != null) {
            TextInputDialog dialog = new TextInputDialog(selectedTodo);
            dialog.setTitle("Update Todo");
            dialog.setHeaderText("Edit todo title:");
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(newTitle -> {
                if (!newTitle.isEmpty()) {
                    updateTodoInDatabase(selectedTodo, newTitle);
                } else {
                    System.out.println("Todo title cannot be empty!");
                }
            });
        }
    }

    @FXML
    private void handleDeleteTodo() {
        String selectedTodo = todoListView.getSelectionModel().getSelectedItem();
        if (selectedTodo != null) {
            deleteTodoFromDatabase(selectedTodo);
        }
    }

    private void loadTodosFromDatabase() {
        String query = "SELECT title FROM todos"; // You can include description if needed
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            todoListView.getItems().clear();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                todoListView.getItems().add(title);
                System.out.println("Loaded todo: " + title); // Debug
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading todos from database: " + e.getMessage());
        }
    }

    private void addTodoToDatabase(String title, String description) {
        String query = "INSERT INTO todos (title, description) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected); // Debug
            loadTodosFromDatabase(); // Refresh the list
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding todo to database: " + e.getMessage());
        }
    }

    private void updateTodoInDatabase(String oldTitle, String newTitle) {
        String query = "UPDATE todos SET title = ? WHERE title = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, oldTitle);
            preparedStatement.executeUpdate();
            loadTodosFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating todo in database: " + e.getMessage());
        }
    }

    private void deleteTodoFromDatabase(String title) {
        String query = "DELETE FROM todos WHERE title = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
            loadTodosFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting todo from database: " + e.getMessage());
        }
    }
}