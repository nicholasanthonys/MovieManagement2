package FXMLController;

import Controller.PersonJpaController;
import Model.Person;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author NICHOLAS ANTHONY SUHARTONO 1118049
 */

public class RegisterSceneController implements Initializable {

    @FXML
    private JFXTextField tfFullname;
    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXPasswordField pfPassword;

    @FXML
    private void onClickBtnRegister(ActionEvent event) throws Exception {
        //person controller object
        PersonJpaController pc = new PersonJpaController(Util.MovieManagementUtil.getEntityManagerFactory());
        
        //if fullname, username, and password is not empty
        if (!tfFullname.getText().equals("") && !tfUsername.getText().equals("") && !pfPassword.getText().equals("")) {
            //hash the password
            String hashedPassword = BCrypt.hashpw(pfPassword.getText(), BCrypt.gensalt());
            //create person object
            Person person = new Person();
            //set person full name
            person.setName(tfFullname.getText());
            //set person username
            person.setUsername(tfUsername.getText());
            //set person hashed password
            person.setPassword(hashedPassword);
            //insert person to database
            pc.create(person);
            JOptionPane.showMessageDialog(null, "Register Succeed");
            System.out.println("jumlah data di table person : " + pc.getPersonCount());
            new Panel(event, "LoginScene.fxml", "Login Form");

        } else {
            JOptionPane.showMessageDialog(null, "Semua Field harus diisi !");
        }
    }

    @FXML
    private void onClickLinkLogin(ActionEvent event) {
        new Panel(event, "LoginScene.fxml", "Login Form");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
