package io.github.toandv.cleangithub;

import javafx.scene.control.CheckBox;

import java.util.Set;

/**
 * Created by toan on 5/24/16.
 */
public class RepositoryCheckBox extends CheckBox {

    Repository repository;

    public RepositoryCheckBox(Repository repository, Set<Repository> deleteRepositories) {
        super(repository.full_name);
        this.repository = repository;
        this.setOnAction(e -> {
            System.out.println(Thread.currentThread() + " Checkbox clicked!");
            if (this.isSelected()) {
                deleteRepositories.add(this.repository);
            } else {
                deleteRepositories.remove(this.repository);
            }
            System.out.println(deleteRepositories);
        });
    }
}
