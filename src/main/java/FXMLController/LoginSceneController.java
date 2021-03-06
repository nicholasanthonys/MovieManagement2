/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * FXML Controller class
 *
 * @author NICHOLAS ANTHONY SUHARTONO 1118049
 */
public class LoginSceneController implements Initializable {

    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXPasswordField pfPassword;
    static Person person;

    @FXML
    private void onClickBtnLogin(ActionEvent event) {
        PersonJpaController pc = new PersonJpaController(Util.MovieManagementUtil.getEntityManagerFactory());
        boolean loginSucceed = false; //this variable willd determine if you succeed in login or not
        
        //if username is not empty and password is not empty
        if (!tfUsername.getText().equals("") && !pfPassword.getText().equals("")) {
            //get the person based on username as primary key
            person = new Person();
            person = pc.findPerson(tfUsername.getText());
            
            //if person is found
            if(person != null){
                //compare the password
                if(BCrypt.checkpw(pfPassword.getText(), person.getPassword())){
                    loginSucceed = true;
                           
                    new Panel( event, "Dashboard.fxml","Dashboard");
                }
            }
            
            
            if(!loginSucceed){
                JOptionPane.showMessageDialog(null, "username or password is incorrect");
            }
            
            
        } else {
            JOptionPane.showMessageDialog(null, "Semua Field harus diisi !");
        }
    }

    @FXML
    private void onClickLinkRegister(ActionEvent event) {
        new Panel(event, "RegisterScene.fxml", "Register Form");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
