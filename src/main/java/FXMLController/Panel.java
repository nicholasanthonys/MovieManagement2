/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLController;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author NICHOLAS ANTHONY SUHARTONO 1118049
 */
public class Panel {

    public static Stage stage;

    public Panel(ActionEvent event, String resource, String title) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + resource));
            Scene scene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
