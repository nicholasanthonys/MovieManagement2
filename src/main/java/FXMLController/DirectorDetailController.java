/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLController;

import Controller.DirectorJpaController;
import Controller.ImageController;
import static FXMLController.MyFavouriteMoviesSceneController.person;
import Model.Director;
import Model.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.stream.FileImageInputStream;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DirectorDetailController implements Initializable {

    //set btn back
    DirectorJpaController dc = new DirectorJpaController(Util.MovieManagementUtil.getEntityManagerFactory());

    //Size Image Declaration
    int scaledWidth = 100;
    int scaledHeight = 150;
    
    @FXML
    JFXButton btnBack;
    
    @FXML
    ListView<Label> listView;
    
    @FXML
    Label lblDirectorName;
    
    @FXML
    ImageView ivDirector;
    
    @FXML
    Label lblBirthDate;
    
    @FXML
    Label lblOccupation;
    
    @FXML
    Label lblShortBio;
    
    
    @FXML
    public void onClickBtnBack(ActionEvent event) {
        
        new Panel(event, "ListDirectorScene.fxml", "List All Director");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Director director = ListDirectorSceneController.director;
        
        //set director name
        lblDirectorName.setText(director.getName());
        
        //set director birthday
        lblBirthDate.setText(director.getBirthdate());
        
        //set director occupation
        lblOccupation.setText(director.getOccupation());
        
        //SET director occupation
        lblShortBio.setText(director.getShortbio());
        
        try {
            //set image view director
            Image img = new Image(new FileInputStream("src/main/resources/outputimages/directors/" + director.getId() + ".jpg"));
            ivDirector.setImage(img);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DirectorDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < director.getMovieList().size(); i++) {
            //Loping movie from datahabse and get it
            Movie movie = director.getMovieList().get(i);
            
            try {
                //set label as movie name and director name
                Label lbl = new Label(movie.getName());
                //output directory for the images from database
                String output = "src/main/resources/outputimages/favmovies/" + movie.getId() + ".jpg";
                //out put the image and resize it
                ImageController.outputImageResize(movie.getPoster(), output, scaledWidth, scaledHeight);
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
