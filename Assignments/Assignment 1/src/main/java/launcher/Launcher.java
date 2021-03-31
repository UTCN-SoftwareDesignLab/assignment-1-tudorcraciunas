package launcher;

import database.Bootstrapper;

import java.sql.SQLException;

public class Launcher {

    public static boolean BOOTSTRAP = false;

    public static void main(String[] args) {
        bootstrap();

        ComponentFactory componentFactory = ComponentFactory.instance(false);
        componentFactory.getLoginView().setVisible();
    }

    private static void bootstrap() {
        if (BOOTSTRAP) {
            try {
                new Bootstrapper().execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
