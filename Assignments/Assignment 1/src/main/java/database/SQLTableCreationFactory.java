package database;
import static database.Constants.Tables.*;


public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        switch (table) {
            case ACCOUNT:
                return "CREATE TABLE IF NOT EXISTS `account` (" +
                        " `idaccount` INT NOT NULL AUTO_INCREMENT," +
                        " `idclient` INT NOT NULL," +
                        " `number` VARCHAR(45) NOT NULL," +
                        " `type` VARCHAR(45) NOT NULL," +
                        " `balance` FLOAT NOT NULL," +
                        " `creation_date` DATETIME NOT NULL," +
                        " PRIMARY KEY (`idaccount`)," +
                        " UNIQUE INDEX `idaccount_UNIQUE` (`idaccount` ASC) VISIBLE," +
                        " INDEX `idclient_idx` (`idclient` ASC) VISIBLE," +
                        " CONSTRAINT `idclient`" +
                        " FOREIGN KEY (`idclient`)" +
                        " REFERENCES `client` (`idclient`)" +
                        " ON DELETE CASCADE" +
                        " ON UPDATE CASCADE);";
            case CLIENT:
                return "CREATE TABLE IF NOT EXISTS `client` (" +
                        " `idclient` INT NOT NULL AUTO_INCREMENT," +
                        " `surname` VARCHAR(45) NOT NULL," +
                        " `card_number` VARCHAR(45) NOT NULL," +
                        " `numerical_code` VARCHAR(45) NOT NULL," +
                        " `address` VARCHAR(45) NOT NULL," +
                        " PRIMARY KEY (`idclient`)," +
                        " UNIQUE INDEX `idclient_UNIQUE` (`idclient` ASC) VISIBLE);";
            case USER:
                return "CREATE TABLE `user` (" +
                        "  `iduser` INT NOT NULL AUTO_INCREMENT," +
                        "  `username` VARCHAR(45) NOT NULL," +
                        "  `password` VARCHAR(100) NOT NULL," +
                        "  `idrole` INT NOT NULL," +
                        "  PRIMARY KEY (`iduser`)," +
                        "  UNIQUE INDEX `iduser_UNIQUE` (`iduser` ASC) VISIBLE," +
                        "  INDEX `idrole_idx` (`idrole` ASC) VISIBLE," +
                        "  CONSTRAINT `idrole`" +
                        "    FOREIGN KEY (`idrole`)" +
                        "    REFERENCES `role` (`id`)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            case ROLE:
                return "  CREATE TABLE IF NOT EXISTS `role` (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX role_UNIQUE (role ASC));";

            case RIGHT:
                return "  CREATE TABLE IF NOT EXISTS `right` (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `right` VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (`id`)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                        "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";

            case ROLE_RIGHT:
                return "  CREATE TABLE IF NOT EXISTS `role_right` (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role_id INT NOT NULL," +
                        "  right_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX role_id_idx (role_id ASC)," +
                        "  INDEX right_id_idx (right_id ASC)," +
                        "  CONSTRAINT role_id" +
                        "    FOREIGN KEY (role_id)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT right_id" +
                        "    FOREIGN KEY (right_id)" +
                        "    REFERENCES `right` (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";
            case ACTIVITIES:
                return "CREATE TABLE `activities` (" +
                        "  `idactivities` INT NOT NULL AUTO_INCREMENT," +
                        "  `iduser` INT NOT NULL," +
                        "  `activity` VARCHAR(145) NOT NULL," +
                        "  `date` DATETIME NOT NULL," +
                        "  PRIMARY KEY (`idactivities`)," +
                        "  UNIQUE INDEX `idactivities_UNIQUE` (`idactivities` ASC) VISIBLE," +
                        "  INDEX `iduser_idx` (`iduser` ASC) VISIBLE," +
                        "  CONSTRAINT `iduser`" +
                        "    FOREIGN KEY (`iduser`)\n" +
                        "    REFERENCES `user` (`iduser`)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";



            default:
                return "";

        }
    }

}
