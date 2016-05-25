package io.github.toandv.cleangithub;

/**
 * Created by toan on 5/24/16.
 */
public class Authencation {

    static String user;

    static String pass;

    static String org;

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Authencation.user = user;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        Authencation.pass = pass;
    }

    public static String getOrg() {
        return org;
    }

    public static void setOrg(String org) {
        Authencation.org = org;
    }
}
