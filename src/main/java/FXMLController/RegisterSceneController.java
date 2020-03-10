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

public class RegisterSceneController implements Initializable {

    @FXML
    private JFXTextField tfFullname;
    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXPasswordField pfPassword;

    @FXML
    private void onClickBtnRegister(ActionEvent event) throws Exception {
        System.out.println("You clicked Register button");

        PersonJpaController pc = new PersonJpaController(Util.MovieManagementUtil.getEntityManagerFactory());

        if (!tfFullname.getText().equals("") && !tfUsername.getText().equals("") && !pfPassword.getText().equals("")) {
            String hashedPassword = BCrypt.hashpw(pfPassword.getText(), BCrypt.gensalt());
            Person person = new Person();
            person.setName(tfFullname.getText());
            person.setUsername(tfUsername.getText());
            person.setPassword(hashedPassword);
            pc.create(person);
            JOptionPane.showMessageDialog(null, "Register Succeed");
            System.out.println("jumlah data di table person : " + pc.getPersonCount());
            new Panel(event,"LoginScene.fxml","Login Form");

        }else{
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
