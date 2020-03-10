package FXMLController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Controller.DirectorJpaController;
import Controller.MovieJpaController;
import Controller.PersonJpaController;
import Model.Director;
;
import Model.Movie;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Model.Movie;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ASUS
 */


public class LandingSceneController implements Initializable {

    String input;
    File file;
    byte[] picInBytes;
    FileInputStream fileInputStream;

    MovieJpaController mc = new MovieJpaController(Util.MovieManagementUtil.getEntityManagerFactory());
    PersonJpaController pc = new PersonJpaController(Util.MovieManagementUtil.getEntityManagerFactory());
    DirectorJpaController dc = new DirectorJpaController(Util.MovieManagementUtil.getEntityManagerFactory());

    /**
     * Initializes the controller class.
     */
    @FXML
    private void onClickBtnRegister(ActionEvent event) {
        new Panel(event, "RegisterScene.fxml", "Register Form");
    }

    @FXML
    private void onClickBtnLogin(ActionEvent event) {
        new Panel(event, "LoginScene.fxml", "Login Form");
    }

    @FXML
    private void onClickBtnInitData(ActionEvent event) throws IOException, Exception {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initDirector();
            initMovie();
        } catch (IOException ex) {
            Logger.getLogger(LandingSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initDirector() throws FileNotFoundException, IOException {
        //James Cameron
        Director d1 = new Director();
        d1.setBirthdate("August 16, 1954 ");
        d1.setOccupation("Filmmaker, environmentalist, philanthropist");
        d1.setShortbio("James Francis Cameron is a Canadian filmmaker, artist, and environmentalist, who is best known for making science fiction and epic films for the Hollywood mainstream.");
        input = "src/main/resources/images/jamescameron.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        d1.setPicture(picInBytes);
        dc.create(d1);

//Michael bay
        Director d2 = new Director();
        d2.setBirthdate("February 17, 1965");
        d2.setOccupation("Film director, producer, camera operator, actor");
        d2.setShortbio("Michael Benjamin Bay is an American filmmaker known for directing and producing big-budget, high-concept action films characterized by fast cutting, stylistic visuals and extensive use of special effects, including frequent depictions of explosions.");
        input = "src/main/resources/images/michaelbay.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        d2.setPicture(picInBytes);
        dc.create(d2);

//        //sam raimi
        Director d3 = new Director();
        d3.setBirthdate("October 23, 1959");
        d3.setOccupation("Director, producer, screenwriter, actor");
        d3.setShortbio("Samuel M. Raimi is an American filmmaker, actor, and producer, primarily known for creating the cult horror Evil Dead series, and directing the Spider-Man trilogy (2002–2007).");
        input = "src/main/resources/images/samraimi.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        d3.setPicture(picInBytes);
        dc.create(d3);

//        //joe russo
        Director d4 = new Director();
        d4.setBirthdate("July 8, 1971");
        d4.setOccupation("Film directors, producers, screenwriters");
        d4.setShortbio("Joe Russo is a producer and director, known for Avengers: Endgame (2019), Avengers: Infinity War (2018) and Captain America: The Winter Soldier (2014).");
        input = "src/main/resources/images/joerusso.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        d4.setPicture(picInBytes);
        dc.create(d4);
//        
//        
//        //anthony russo
        Director d5 = new Director();
        d5.setBirthdate("February 3, 1970");
        d5.setOccupation("Film directors, producers, screenwriters");
        d5.setShortbio("Anthony Russo is a producer and director, known for Avengers: Endgame (2019), Avengers: Infinity War (2018) and Captain America: The Winter Soldier (2014).");
        input = "src/main/resources/images/anthonyrusso.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        d5.setPicture(picInBytes);
        dc.create(d5);
    }

    private void initMovie() throws FileNotFoundException, IOException {

        //array director declaration
        ArrayList<Director> arrdirectors = new ArrayList<>();
        Director director;

        //create movie titanic
        Movie m1 = new Movie();
        m1.setName("Titanic");
        m1.setReleasedate("January 5, 1998");
        input = "src/main/resources/images/titanic.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        m1.setPoster(picInBytes);

        //get titanic director
        director = dc.findDirector(1);
        //renew the array
        arrdirectors = new ArrayList<>();
        //add d1 to arraylist directors
        arrdirectors.add(director);
        //set director
        m1.setDirectorList(arrdirectors);
        //create movie titanic
        mc.create(m1);

        //CREATE MOVIE AVATAR
        Movie m2 = new Movie();
        m2.setName("Avatar");
        m2.setReleasedate("December 17, 2009");
        input = "src/main/resources/images/avatar.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        m2.setPoster(picInBytes);

        //get avatar director
        director = dc.findDirector(1);
        //renew the array
        arrdirectors = new ArrayList<>();
        //add d1 to arraylist directors
        arrdirectors.add(director);
        //set director
        m2.setDirectorList(arrdirectors);
        mc.create(m2);

        //CREATE MOVIE SPIDERMAN
        Movie m3 = new Movie();
        m3.setName("Spider-man");
        m3.setReleasedate("May 22, 2002");
        input = "src/main/resources/images/spiderman.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        m3.setPoster(picInBytes);

        //get spiderman director
        director = dc.findDirector(3);
        //renew the array
        arrdirectors = new ArrayList<>();
        //add d1 to arraylist directors
        arrdirectors.add(director);
        //set director
        m3.setDirectorList(arrdirectors);

        mc.create(m3);

        //CREATE MOVIE TRANSFORMERS 1
        Movie m4 = new Movie();
        m4.setName("Transformers");
        m4.setReleasedate("June 28, 2007");
        input = "src/main/resources/images/transformers.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        m4.setPoster(picInBytes);
        mc.create(m4);

        //Create movie Transformers: Revenge of the Fallen
        Movie m5 = new Movie();
        m5.setName("Transformers: Revenge of the Fallen");
        m5.setReleasedate("June 24, 2009");
        input = "src/main/resources/images/transformers2.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        m5.setPoster(picInBytes);

        //get Transformers director
        director = dc.findDirector(2);
        //renew the array
        arrdirectors = new ArrayList<>();
        //add d1 to arraylist directors
        arrdirectors.add(director);
        //set director
        m5.setDirectorList(arrdirectors);
        mc.create(m5);

        //CREATE MOVIE AVENGERS ENDGAME
        Movie m6 = new Movie();
        m6.setName("Avengers: Endgame");
        m6.setReleasedate("April 24, 2019");
        input = "src/main/resources/images/avengers.jpg";
        file = new File(input);
        picInBytes = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        m6.setPoster(picInBytes);

        //get Avenger Endgame director
        director = dc.findDirector(4); //joe russo
        //renew the array
        arrdirectors = new ArrayList<>();
        //add d1 to arraylist directors
        arrdirectors.add(director);
        
        director = dc.findDirector(5); //anthony russo
        //add director2 to array list
        arrdirectors.add(director);
        
        //set director
        m6.setDirectorList(arrdirectors);
        mc.create(m6);

    }

}
