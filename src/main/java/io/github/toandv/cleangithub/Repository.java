package io.github.toandv.cleangithub;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by toan on 5/24/16.
 */
public class Repository {

    public String id;

    public String name;

    public String description;

    public String full_name;

    public String html_url;

    public String url;

    public boolean fork;

    public Owner owner;


    public static class Owner {

        public String login;

        public String id;

        public String url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Repository that = (Repository) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static List<Repository> loadRepositories(String targetReposPath, String user, String pass, int perPage) {
        try {
            HttpResponse<String> list = Unirest
                    .get("https://api.github.com/" + targetReposPath + "/repos")
                    .basicAuth(user, pass)
                    .queryString("per_page", perPage)
                    .asString();
            return JSONUtils
                    .fromJson(list.getBody(), ArrayList.class, Repository.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Repository> loadUsersRepositories(String user, String pass, int perPage) {
        return loadRepositories("users/" + user, user, pass, perPage);
    }

    public static List<Repository> loadOrgsRepositories(String org, String user, String pass, int perPage) {
        return loadRepositories("orgs/" + org, user, pass, perPage);
    }

    public static List<Repository> delete(String user, String pass, Collection<Repository> repositories) {
        List<Repository> deletedRepositories = new ArrayList<>();
        for (Repository repository : repositories) {
            try {
                Unirest
                        .delete(repository.url)
                        .basicAuth(user, pass)
                        .asString();
                deletedRepositories.add(repository);
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }
        return deletedRepositories;
    }

    @Override
    public String toString() {
        return url;
    }
}
