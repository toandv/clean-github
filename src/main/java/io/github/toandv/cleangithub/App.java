package io.github.toandv.cleangithub;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by toan on 5/25/16.
 */
public class App extends Application {

    VBox vbchecks;

    List<RepositoryCheckBox> checkBoxes;

    Set<Repository> deleteRepositories = new LinkedHashSet<>();

    Button deleteButton2 = new Button("Delete");


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        vbchecks = new VBox();
        vbchecks.setSpacing(10);
        vbchecks.setPadding(new Insets(20));

        loadRepositories();

        vbchecks.getChildren().addAll(checkBoxes);
        deleteButton2.setOnAction(e -> {
            Repository.delete(Authencation.getUser(), Authencation.getPass(), deleteRepositories);
            loadRepositories();
            start(primaryStage);
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

