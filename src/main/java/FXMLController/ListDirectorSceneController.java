/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLController;

import Controller.DirectorJpaController;
import Controller.ImageController;
import Model.Director;
import com.jfoenix.controls.JFXButton;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ListDirectorSceneController implements Initializable {
    //controller of director
    DirectorJpaController dc = new DirectorJpaController(Util.MovieManagementUtil.getEntityManagerFactory());
    static Director director;
    //Size Image Declaration
    int scaledWidth = 100;
    int scaledHeight = 150;

    @FXML
    private ListView<Label> listView;

    @FXML
    private JFXButton btnViewDetail;

    @FXML
    private JFXButton btnBack;

    @FXML
    public void onClickBtnBack(ActionEvent event) {
        new Panel(event, "Dashboard.fxml", "Dashboard");
    }

    @FXML
    private void onClickViewDetail(ActionEvent event) {
        System.out.println(" you click view detail ");
        Label selectedItem = listView.getSelectionModel().getSelectedItem();
        int index = listView.getSelectionModel().getSelectedIndex();

        int directorId = index + 1;
        //get mvie;
        director = dc.findDirector(directorId);

        new Panel(event, "DirectorDetail.fxml", "Director Detail");

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (int i = 0; i < dc.getDirectorCount(); i++) {
            //Loping movie from datahabse and get it
            Director director = dc.findDirector(i + 1);
            //get movie name
            String directorName = director.getName();
            //declaration for director name

            try {
                //set label as movie name and director name
                Label lbl = new Label(directorName);
                //output directory for the images from database
                String output = "src/main/resources/outputimages/directors/" + director.getId() + ".jpg";
                //out put the image and resize it
                ImageController.outputImageResize(director.getPicture(), output, scaledWidth, scaledHeight);
                //add image to label
                lbl.setGraphic(new ImageView(new Image(new FileInputStream(output))));
                //set font size
                lbl.setStyle("-fx-font-size:15px;");
                //add label to image
                listView.getItems().add(lbl);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
