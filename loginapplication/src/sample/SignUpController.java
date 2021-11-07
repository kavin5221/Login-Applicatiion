package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {


    @FXML
    private Button button_signup;

    @FXML
    private Button button_log_in;

    @FXML
    private RadioButton rb_yes;

    @FXML
    private RadioButton rb_no;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ToggleGroup toggleGroup=new ToggleGroup();
        rb_yes.setToggleGroup(toggleGroup);
        rb_no.setToggleGroup(toggleGroup);

        rb_yes.setSelected(true);


        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()){
                    DButils.signUpuser(event,tf_username.getText(),tf_password.getText(),toggleName);
                }else{
                    System.out.println("Please fill in all information");
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                    //alert.set

                }
            }
        });

        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event,"sample.fxml","Log in!",null);
            }
        });





    }
}
