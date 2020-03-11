/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLController;

import Controller.ImageController;
import Controller.MovieJpaController;
import Controller.PersonJpaController;
import Model.Movie;
import Model.Person;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author NICHOLAS ANTHONY SUHARTONO 1118049
 */
public class MyFavouriteMoviesSceneController implements Initializable {

    static Person person;
    PersonJpaController pc = new PersonJpaController(Util.MovieManagementUtil.getEntityManagerFactory());
    MovieJpaController mc = new MovieJpaController(Util.MovieManagementUtil.getEntityManagerFactory());
    //Size Image Declaration
    int scaledWidth = 100;
    int scaledHeight = 150;

    @FXML
    private Label lblUsername;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private JFXListView<Label> listView;

    @FXML
    private JFXButton btnBack;

    @FXML
    public void onClickBtnBack(ActionEvent event) {
        new Panel(event, "Dashboard.fxml", "Dashboard");
    }

    @FXML
    private void onClickBtnRemove(ActionEvent event) throws Exception {

        //get selected item as a label
        Label selectedItem = listView.getSelectionModel().getSelectedItem();
        //get the index of the selected label
        int index = listView.getSelectionModel().getSelectedIndex();
        //get person favorite movie
        List<Movie> arrFavMovie = person.getMovieList();
        //remove the arrmovie index and shift the element to the left
        arrFavMovie.remove(index);
        //set the updated movie list
        person.setMovieList(arrFavMovie);
        pc.edit(person);

        new Panel(event, "MyFavouriteMoviesScene.fxml", "My Favourite Movies");

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        person = LoginSceneController.person;
        lblUsername.setText(person.getUsername());

        for (int i = 0; i < person.getMovieList().size(); i++) {
            //Loping movie from datahabse and get it
            Movie movie = person.getMovieList().get(i);

            //declaration for director name
            String directorName = "";
            for (int j = 0; j < movie.getDirectorList().size(); j++) {
                //eliminate coma from the first director
                if (j == 0) {
                    directorName += movie.getDirectorList().get(j).getName();
                } else {
                    directorName += ", " + movie.getDirectorList().get(j).getName();
                }

            }
            try {
                //set label as movie name and director name
                Label lbl = new Label(movie.getName() + "\nRelease Date :" + movie.getReleasedate() + "\n" + "Directors : " + directorName);
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
