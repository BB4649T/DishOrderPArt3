package edu.restaurant.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private final static int defaultPort = 5432;
    private final String jdbcUrl;
    private final String user;
    private final String password;

    public DataSource(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    // Constructeur par défaut utilisant les variables d'environnement
    public DataSource() {
        this(
                "jdbc:postgresql://"
                        + System.getenv("DATABASE_HOST")
                        + ":" + defaultPort
                        + "/"
                        + System.getenv("DATABASE_NAME"),
                System.getenv("DATABASE_USER"),
                System.getenv("DATABASE_PASSWORD")
        );
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de connexion à " + jdbcUrl, e);
        }
    }
}