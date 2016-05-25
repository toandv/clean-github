package io.github.toandv.cleangithub;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by toan on 5/25/16.
 */
public class App extends Application {

    VBox vbchecks;

    List<RepositoryCheckBox> checkBoxes = new ArrayList<>();

    Set<Repository> deleteRepositories = new LinkedHashSet<>();

    Button deleteButton2 = new Button("Delete");

    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("CleanGitHub");
        login();
    }

    private void login() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Sign in button pressed");
            String user = userTextField.getText();
            String pass = pwBox.getText();
            if (user != null && pass != null) {
                Authencation.setUser(user);
                Authencation.setPass(pass);
                init1();
            }
        });

        Scene scene = new Scene(grid, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void init1() {
        vbchecks = new VBox();
        vbchecks.setSpacing(10);
        vbchecks.setPadding(new Insets(20));

        loadRepositories();

        vbchecks.getChildren().addAll(checkBoxes);
        deleteButton2.setOnAction(e -> {
            Repository.delete(Authencation.getUser(), Authencation.getPass(), deleteRepositories);
            loadRepositories();
            init1();
        });

        FlowPane root = new FlowPane();
        root.setHgap(20);
        root.getChildren().addAll(deleteButton2, vbchecks);

        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setTitle("Clean GitHub");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadRepositories() {
        List<Repository> repositories = Repository
                .loadUsersRepositories(Authencation.getUser(), Authencation.getPass(), 20);

        checkBoxes = repositories
                .stream()
                .map(r -> new RepositoryCheckBox(r, deleteRepositories))
                .collect(Collectors.toList());
    }


}

