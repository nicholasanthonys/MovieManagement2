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
import javax.swing.JOptionPane;

/**
 *FXML CONTROLLER CLASS
 * @author NICHOLAS ANTHONY SUHARTONO 1118049
 */
public class DashboardController implements Initializable {

    @FXML
    Label lblUsername;

    @FXML
    ImageView ivMovie;

    @FXML
    JFXListView<Label> listView;

    @FXML
    JFXButton btnMyFavouriteMovies;

    @FXML
    JFXButton btnAddToFavourite;
    
    @FXML
    JFXButton btnLogOut;
    
    static Person person;

    //Movie Controller Declaration
    MovieJpaController mc = new MovieJpaController(Util.MovieManagementUtil.getEntityManagerFactory());
    PersonJpaController pc = new PersonJpaController(Util.MovieManagementUtil.getEntityManagerFactory());

    //Size Image Declaration
    int scaledWidth = 100;
    int scaledHeight = 150;

    @FXML
    public void onClickBtnSeeDirector(ActionEvent event) {
 
        new Panel(event, "ListDirectorScene.fxml", " List All Director ");
    }

    @FXML
    public void onClickBtnLogOut(ActionEvent event) {

        new Panel(event, "LoginScene.fxml", "Login Form");
    }

    @FXML
    private void onClickAddToFavourite(ActionEvent event) throws Exception {
        System.out.println("btn add to favourite clicked");
        
        //get selected item as label
        Label selectedItem = listView.getSelectionModel().getSelectedItem();
        //determine the index of selected label
        int index = listView.getSelectionModel().getSelectedIndex();
        //movie is index + 1
        int movieId = index + 1;
        //get selected movie;
        Movie movieSelected = mc.findMovie(movieId);
        //get person favorite movie list
        List<Movie> arrFavMovie = person.getMovieList();

        //check if the selected movie in favourite movie
        boolean exist = false;
        for (Movie i : arrFavMovie) {
            if (i.getId() == movieSelected.getId()) {
                exist = true;
                break;
            }

        }

        if (exist) {
            JOptionPane.showMessageDialog(null, "Movie Already in your Favourite List !");
        } else {
            //if not exist then add to favorite movie list
            arrFavMovie.add(movieSelected);
            person.setMovieList(arrFavMovie);
            pc.edit(person);
            JOptionPane.showMessageDialog(null, "Movie Added to your favourite list");
        }

    }

    @FXML
    private void onClickBtnMyFavouriteMovies(ActionEvent event) {

        new Panel(event, "MyFavouriteMoviesScene.fxml", "My Favourite Movies");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        person = LoginSceneController.person;
        //INISIALISASI SEMUA MOVIES KE DASHBOARD

        // Set label username to person name
        lblUsername.setText(person.getUsername());

        for (int i = 0; i < mc.getMovieCount(); i++) {
            //Loping movie from datahabse and get it
            Movie movie = mc.findMovie(i + 1);
            //get movie name
            String movieName = movie.getName();
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
                Label lbl = new Label(movie.getName() + "\n" + "Directors : " + directorName);
                //output directory for the images from database
                String output = "src/main/resources/outputimages/movies/" + movie.getId() + ".jpg";  
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
