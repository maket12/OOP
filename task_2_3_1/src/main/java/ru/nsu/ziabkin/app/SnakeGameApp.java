package ru.nsu.ziabkin.app;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SnakeGameApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            URL resource = getClass().getResource("/views/game_view.fxml");
            if (resource == null) {
                throw new IllegalStateException("Не удалось найти файл разметки: /views/game_view.fxml");
            }
            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle("Snake Game MVC");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Критическая ошибка", "Не удалось запустить игру.\nПричина: " + e.getMessage());
        }
    }

    /**
     * Метод для вывода всплывающего окна с ошибкой.
     */
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("Упс, что-то пошло не так!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}