package com.example.demo;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/rental_system_db";
                String username = "root";
                String password = "admin";
                System.out.println("Connection started...");
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static List<Property> getProperties() {
        List<Property> properties = new ArrayList<>();

        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM property";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int propertyId = resultSet.getInt("id");
                    String address = resultSet.getString("address");
                    String type = resultSet.getString("type");
                    int rent = resultSet.getInt("rate");
                    int bedrooms = resultSet.getInt("bedrooms");
                    boolean rented = resultSet.getBoolean("rentable");
                    Property property = new Property(address, type, bedrooms, rent, rented);
                    property.setPropertyId(propertyId);
                    properties.add(property);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static void addProperty(Property property){
        try {
            Connection connection = getConnection();
            String query = "INSERT INTO property (address, type, bedrooms, rate, rentable) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, property.getAddress());
                preparedStatement.setString(2, property.getType());
                preparedStatement.setInt(3, property.getNumBedrooms());
                preparedStatement.setInt(4, property.getRentalRate());
                preparedStatement.setBoolean(5, property.isRentable());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Tenant> getTenants() {
        List<Tenant> tenants = new ArrayList<>();

        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM tenant";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id=resultSet.getInt("tenant_id");
                    String name = resultSet.getString("name");
                    String phoneNumber = resultSet.getString("phone");
                    String email = resultSet.getString("email");

                    Tenant tenant = new Tenant(name, phoneNumber, email);
                    tenant.setId(id);
                    tenants.add(tenant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tenants;
    }

    public static List<Lease> getLeases() {
        List<Lease> leases = new ArrayList<>();

        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM lease";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int leaseId = resultSet.getInt("lease_id");
                    int propertyId = resultSet.getInt("property_id");
                    int tenantId = resultSet.getInt("id_tenant");
                    Timestamp fromDateTimeTimestamp = resultSet.getTimestamp("date");

                    LocalDateTime startDate = fromDateTimeTimestamp.toLocalDateTime();

                    Property property = getPropertyById(propertyId);

                    Tenant tenant = getTenantById(tenantId);

                    Lease lease = new Lease(property,tenant);
                    lease.setLeaseId(leaseId);
                    lease.setStartDate(startDate);
                    leases.add(lease);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leases;
    }

    private static Property getPropertyById(int propertyId) {
        Property property = null;
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM property WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, propertyId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String address = resultSet.getString("address");
                        String type = resultSet.getString("type");
                        int bedrooms = resultSet.getInt("bedrooms");
                        int rent = resultSet.getInt("rate");
                        boolean rented = resultSet.getBoolean("rentable");

                        property = new Property(address, type, bedrooms, rent, rented);
                        property.setPropertyId(propertyId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return property;
    }

    private static Tenant getTenantById(int tenantId) {
        Tenant tenant = null;
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM tenant WHERE tenant_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, tenantId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id= resultSet.getInt("tenant_id");
                        String name = resultSet.getString("name");
                        String phoneNumber = resultSet.getString("phone");
                        String email = resultSet.getString("email");
                        tenant = new Tenant( name, phoneNumber, email);
                        tenant.setId(id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenant;
    }

    public static void addTenant(Tenant tenant) {
        try {
            Connection connection = getConnection();
            String query = "INSERT INTO tenant (name, phone, email) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, tenant.getName());
                preparedStatement.setString(2, tenant.getPhoneNumber());
                preparedStatement.setString(3, tenant.getEmail());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Tenant getTenantByName(String name) {
        Tenant tenant = null;
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM tenant WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int tenantId = resultSet.getInt("tenant_id");
                        String phoneNumber = resultSet.getString("phone");
                        String email = resultSet.getString("email");

                        tenant = new Tenant(name, phoneNumber, email);
                        tenant.setId(tenantId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenant;
    }

    public static void addLease(Lease lease) {
        Tenant ten=getTenantByName(lease.getTenant().getName());
        try {
            Connection connection = getConnection();
            String query = "INSERT INTO lease (property_id, id_tenant, date) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, lease.getProperty().getPropertyId());
                preparedStatement.setInt(2, ten.getId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(lease.getStartDate()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rentProperty(int propertyId) {
        try {
            Connection connection = getConnection();
            String updatePropertyQuery = "UPDATE property SET rentable = 0 WHERE id = ?";

            try (PreparedStatement updatePropertyStatement = connection.prepareStatement(updatePropertyQuery)) {
                updatePropertyStatement.setInt(1, propertyId);
                updatePropertyStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticateUser(String username, String password) {
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role='user'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User getUserByUsername(String username) {
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM users WHERE username = ? and role='user'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String foundUsername = resultSet.getString("username");
                        String foundPassword = resultSet.getString("password");
                        return new User(foundUsername, foundPassword);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addUser(String username, String password) {
        try {
            Connection connection = getConnection();
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, "user");
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean authenticateAdmin(String username, String password) {
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM users WHERE (role = 'admin' AND username = 'admin') AND password = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void closeConnection() {
        if (connection != null) {
            try {
                System.out.println("Connection ended...");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

