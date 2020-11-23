package com.gmail.psyh2409;

import java.sql.*;
import java.util.Scanner;

public class FlatsJDBC {
    private static Connection connection;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName(Config.getMyProp().getProperty("cfn"));
            connection = DriverManager.getConnection(
                    Config.getMyProp().getProperty("url"),
                    Config.getMyProp().getProperty("user"),
                    Config.getMyProp().getProperty("password"));

            initDB();

            while (true) {

                System.out.println("1: add flat");
                System.out.println("2: delete flat");
                System.out.println("3: change flat");
                System.out.println("4: get flat");
                System.out.println("5: get all flats");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        addFlat(sc);
                        break;
                    case "2":
                        deleteFlat(sc);
                        break;
                    case "3":
                        changeFlat(sc);
                        break;
                    case "4":
                        getFlat(sc);
                        break;
                    case "5":
                        getAll();
                        break;
                    default:
                        return;
                }

            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private static void initDB() throws SQLException {

        try (Statement st = connection.createStatement()) {
            st.execute("DROP TABLE IF EXISTS flatsdb.flats");
            st.execute("CREATE TABLE Flats (" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "city VARCHAR(20) NOT NULL, " +
                    "street VARCHAR(20) NOT NULL, " +
                    "home INT, " +
                    "flat INT, " +
                    "rooms INT, " +
                    "area INT, " +
                    "price INT)");
        }
    }

    private static void addFlat(Scanner sc) throws SQLException {
        System.out.println("Enter new flat below in format like:\n" +
                "cityName, streetName, homeNumber, flatNumber, numberOfRooms, areaNumber, priceNumber");
        String[] flat = sc.nextLine().split(", ");
        String cityName = "", streetName = "";
        int homeNumber = 0, flatNumber = 0, numberOfRooms = 0, areaNumber = 0, priceNumber = 0;
        if (flat.length == 7) {
            cityName = flat[0];
            streetName = flat[1];
            try {
                homeNumber = Integer.parseInt(flat[2]);
                flatNumber = Integer.parseInt(flat[3]);
                numberOfRooms = Integer.parseInt(flat[4]);
                areaNumber = Integer.parseInt(flat[5]);
                priceNumber = Integer.parseInt(flat[6]);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            Flat f = new Flat(cityName, streetName, homeNumber, flatNumber, numberOfRooms, areaNumber, priceNumber);
        }

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO flatsdb.flats (city, street, home, flat, rooms, area, price) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)");
        try {
            ps.setString(1, cityName);
            ps.setString(2, streetName);
            ps.setInt(3, homeNumber);
            ps.setInt(4, flatNumber);
            ps.setInt(5, numberOfRooms);
            ps.setInt(6, areaNumber);
            ps.setInt(7, priceNumber);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }

    private static void deleteFlat(Scanner sc) throws SQLException {
        System.out.println("Enter flat that you want to delete below in format like:\n" +
                "cityName, streetName, homeNumber, flatNumber, numberOfRooms, areaNumber, priceNumber");
        String[] strngs = sc.nextLine().split(", ");
        String city = "", street = "";
        int home = 0, flat = 0, rooms = 0, area = 0, price = 0;
        if (strngs.length == 7) {
            city = strngs[0];
            street = strngs[1];
            home = Integer.parseInt(strngs[2]);
            flat = Integer.parseInt(strngs[3]);
            rooms = Integer.parseInt(strngs[4]);
            area = Integer.parseInt(strngs[5]);
            price = Integer.parseInt(strngs[6]);
        }
        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM flatsdb.flats WHERE " +
                        "city = ? AND street = ? AND home = ? AND flat = ? AND rooms = ? AND area = ? AND price = ?");
        try {
            ps.setString(1, city);
            ps.setString(2, street);
            ps.setInt(3, home);
            ps.setInt(4, flat);
            ps.setInt(5, rooms);
            ps.setInt(6, area);
            ps.setInt(7, price);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }

    private static void getFlat(Scanner sc) {
        Flat f = new Flat();
        PreparedStatement statement = null;
        System.out.println("Enter flat that you want to get below in format like:\n" +
                "cityName, streetName, homeNumber, flatNumber, numberOfRooms, areaNumber, priceNumber");
        System.out.println("Or enter id of flat that you want to get:");
        String city = "", street = "";
        int id = 0, home = 0, flat = 0, rooms = 0, area = 0, price = 0;
        String str = sc.nextLine();
        try {
            if (!str.contains(" ")) {
                id = Integer.parseInt(str);
                statement = connection.prepareStatement("SELECT * FROM flatsdb.flats WHERE id = ?");
                statement.setInt(1, id);
            }
            if (str.split(", ").length == 7) {
                city = str.split(", ")[0];
                street = str.split(", ")[1];
                home = Integer.parseInt(str.split(", ")[2]);
                flat = Integer.parseInt(str.split(", ")[3]);
                rooms = Integer.parseInt(str.split(", ")[4]);
                area = Integer.parseInt(str.split(", ")[5]);
                price = Integer.parseInt(str.split(", ")[6]);

                statement = connection.prepareStatement(
                        "SELECT * FROM flatsdb.flats WHERE " +
                                "city = ? AND street = ? AND home = ? AND flat = ? AND rooms = ? AND area = ? AND price = ?");
                statement.setString(1, city);
                statement.setString(2, street);
                statement.setInt(3, home);
                statement.setInt(4, flat);
                statement.setInt(5, rooms);
                statement.setInt(6, area);
                statement.setInt(7, price);
            }
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                f.setId(rs.getInt(1));
                f.setCity(rs.getString(2));
                f.setStreet(rs.getString(3));
                f.setHome(rs.getInt(4));
                f.setFlat(rs.getInt(5));
                f.setRooms(rs.getInt(6));
                f.setArea(rs.getInt(7));
                f.setPrice(rs.getInt(8));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(f);
    }

    private static void changeFlat(Scanner sc) throws SQLException {
        System.out.println("Enter id of flat that you want changing");
        String s = sc.nextLine();
        String value = "";
        int id = Integer.parseInt(s);
        System.out.println("What do you want to change from this city, street, home, flat, rooms, area, price?\n" +
                "Enter this word:");
        s = sc.nextLine();
        System.out.println("Enter new value:");
        value = sc.nextLine();

        PreparedStatement ps = connection.prepareStatement(
                "UPDATE flatsdb.flats SET " + s + " = ? " +
                        "WHERE id = ? ");
        try {
            if (s.equals("city") || s.equals("street")) {
                ps.setString(1, value);
            } else {
                ps.setInt(1, Integer.parseInt(value));
            }
            ps.setInt(2, id);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
        System.out.println("Change saved.");
    }

    private static void getAll() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM flatsdb.flats");
        try {
            ResultSet rs = ps.executeQuery();
            try {
                ResultSetMetaData md = rs.getMetaData();
                for (int i = 1; i <= md.getColumnCount(); i++)
                    System.out.print(md.getColumnName(i) + "\t\t");
                System.out.println();
                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        System.out.print(rs.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }
}
