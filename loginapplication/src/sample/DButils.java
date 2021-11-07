package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DButils {

    public static void changeScene(ActionEvent event,String fxmlFile,String title,String nickname){
        Parent root=null;

        if(nickname !=null){
            try{
                FXMLLoader loader=new FXMLLoader(DButils.class.getResource(fxmlFile));
                root=loader.load();
                LoggedinController loggedinController =loader.getController();
                loggedinController.setUserInformation(nickname);
            }catch (IOException e){
                e.printStackTrace();
            }
        } else{
            try{
                root= FXMLLoader.load(DButils.class.getResource(fxmlFile));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }

    public static void signUpuser(ActionEvent event,String username,String password,String nickname){
        Connection connection=null;
        PreparedStatement psInsert =null;
        PreparedStatement psCheckUserExists=null;
        ResultSet resultSet=null;
        String user_id="19";

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kavin","kavin1177");
            psCheckUserExists = connection.prepareStatement("select * from users where username= ?");
            psCheckUserExists.setString(1,username);
            resultSet=psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("User already exists!");
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else{
                psInsert = connection.prepareStatement("insert into users values (?,?,?,?)");
                psInsert.setString(1,user_id);
                psInsert.setString(2,username);
                psInsert.setString(3,password);
                psInsert.setString(4, username);
                psInsert.executeQuery();
                changeScene(event,"Logged-in.fxml","Welcome",username);

            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists !=null){
                try{
                    psCheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert !=null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }



        }


    }

    public static void logInUser(ActionEvent event,String username,String password){
        Connection connection =null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kavin","kavin1177");
            preparedStatement = connection.prepareStatement("select * from users where username= ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!");
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            }else{
                while(resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedusername= resultSet.getString("username");
                   if(retrievedPassword.equals(password)){
                       changeScene(event,"Logged-in.fxml","Welcome",username);
                   }else{
                       System.out.println("Password did not match!");
                       Alert alert =new Alert(Alert.AlertType.ERROR);
                       alert.setContentText("The provided credentials are incorrect");
                       alert.show();
                   }
                }
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            if(resultSet !=null ){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement !=null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }



        }


}
