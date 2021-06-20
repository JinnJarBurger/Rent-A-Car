import java.sql.*;

public class CarShare {
    private Connection connect = null;
    private Statement statement = null;

    private String url = "jdbc:hsqldb:file:./src/carSharing/db/";

    public void createTables(String[] dbsName) {
        url = dbsName.length > 0 ? url + dbsName[1] : url + "carSharing";

        createCompany();
        createCar();
        createCustomer();
    }

    public void createCompany() {
        try {
            connect = DriverManager.getConnection(url);
            statement = connect.createStatement();

            String query =
                    "CREATE TABLE IF NOT EXISTS COMPANY (" +
                            "ID INT PRIMARY KEY," +
                            "NAME VARCHAR(100) NOT NULL UNIQUE)";

            statement.executeUpdate(query);

            statement.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createCar() {
        try {
            connect = DriverManager.getConnection(url);
            statement = connect.createStatement();

            String query =
                    "CREATE TABLE IF NOT EXISTS CAR (" +
                            "ID INT PRIMARY KEY," +
                            "NAME VARCHAR(100) NOT NULL UNIQUE," +
                            "COMPANY_ID INT NOT NULL," +
                            "CONSTRAINT fk_companyID FOREIGN KEY (COMPANY_ID) " +
                            "REFERENCES COMPANY(ID))";

            statement.executeUpdate(query);

            statement.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createCustomer() {
        try {
            connect = DriverManager.getConnection(url);
            statement = connect.createStatement();

            String query =
                    "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                            "ID INT PRIMARY KEY," +
                            "NAME VARCHAR(100) NOT NULL UNIQUE," +
                            "RENTED_CAR_ID INT DEFAULT NULL," +
                            "CONSTRAINT FK_RENTED_ID FOREIGN KEY (RENTED_CAR_ID) " +
                            "REFERENCES CAR(ID))";

            statement.executeUpdate(query);

            statement.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public boolean companyListManager() {
        boolean isEmpty = true;
        try {
            connect = DriverManager.getConnection(url);
            statement = connect.createStatement();

            String query = "SELECT * FROM COMPANY";

            ResultSet result = statement.executeQuery(query);

            isEmpty = !result.next();

            if (isEmpty)
                System.out.println("\nThe company list is empty!");
            else {
                System.out.println("\nChoose the company:");

                do {
                    int id = result.getInt("ID");
                    String companyName = result.getString("NAME");
                    System.out.println(id + ". " + companyName);
                } while (result.next());
            }
            result.close();
            statement.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return isEmpty;
    }

    public void getCompanyCarManager(String company) {
        try {
            connect = DriverManager.getConnection(url);

            String query = "SELECT " +
                    "CAR.NAME " +
                    "FROM CAR INNER JOIN COMPANY ON CAR.COMPANY_ID = COMPANY.ID " +
                    "WHERE COMPANY.NAME = ?";

            PreparedStatement preStmt = connect.prepareStatement(query);
            preStmt.setString(1, company);
            ResultSet result = preStmt.executeQuery();

            if (!result.next())
                System.out.println("\nThe car list is empty!");
            else {
                System.out.println("\nCar list:");

                int id = 0;
                do {
                    id++;
                    String carName = result.getString("NAME");
                    System.out.println(id + ". " + carName);
                } while (result.next());
            }
            preStmt.close();
            result.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void insertCompany(String companyName) {
        try {
            connect = DriverManager.getConnection(url);
            statement = connect.createStatement();

            String query = "SELECT MAX(ID) AS maxID FROM COMPANY";

            ResultSet result = statement.executeQuery(query);
            result.next();
            int nextId = result.getInt("maxID") + 1;

            String query2 = "INSERT INTO COMPANY VALUES (?, ?)";

            PreparedStatement preStmt = connect.prepareStatement(query2);
            preStmt.setInt(1, nextId);
            preStmt.setString(2, companyName);
            preStmt.executeUpdate();

            System.out.println("The company was created!");

            result.close();
            preStmt.close();
            statement.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void insertCar(int companyId, String carName) {
        try {
            connect = DriverManager.getConnection(url);
            statement = connect.createStatement();

            String query = "SELECT MAX(ID) AS ID FROM CAR";

            ResultSet result = statement.executeQuery(query);
            result.next();
            int nextId = result.getInt("ID") + 1;

            String query2 = "INSERT INTO CAR VALUES (?, ?, ?)";

            PreparedStatement preStmt = connect.prepareStatement(query2);
            preStmt.setInt(1, nextId);
            preStmt.setString(2, carName);
            preStmt.setInt(3, companyId);
            preStmt.executeUpdate();

            System.out.println("The car was added!\n");

            result.close();
            preStmt.close();
            statement.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void insertCustomer(String customer) {
        try {
            connect = DriverManager.getConnection(url);
            statement = connect.createStatement();

            String query = "SELECT MAX(ID) AS maxID FROM CUSTOMER";

            ResultSet result = statement.executeQuery(query);
            result.next();
            int nextId = result.getInt("maxID") + 1;

            String query2 = "INSERT INTO CUSTOMER (ID, NAME) VALUES (?, ?)";

            PreparedStatement preStmt = connect.prepareStatement(query2);
            preStmt.setInt(1, nextId);
            preStmt.setString(2, customer);
            preStmt.executeUpdate();

            System.out.println("The customer was added!\n");

            result.close();
            preStmt.close();
            statement.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public String getCompanyName(int companyId) {
        String name = null;
        try {
            connect = DriverManager.getConnection(url);

            String query = "SELECT NAME " +
                    "FROM COMPANY " +
                    "WHERE ID = ?";

            PreparedStatement preStmt = connect.prepareStatement(query);
            preStmt.setInt(1, companyId);
            ResultSet result = preStmt.executeQuery();

            result.next();
            name = result.getString("NAME");

            preStmt.close();
            result.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return name;
    }

    public int getCompanyID(String companyName) {
        int id = 0;
        try {
            connect = DriverManager.getConnection(url);

            String query = "SELECT ID " +
                    "FROM COMPANY " +
                    "WHERE NAME = ?";

            PreparedStatement preStmt = connect.prepareStatement(query);
            preStmt.setString(1, companyName);
            ResultSet result = preStmt.executeQuery();

            result.next();
            id = result.getInt("ID");

            preStmt.close();
            result.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return id;
    }

    public boolean customerList() {
        boolean isEmpty = true;
        try {
            connect = DriverManager.getConnection(url);
            statement = connect.createStatement();

            String query = "SELECT * FROM CUSTOMER";

            ResultSet result = statement.executeQuery(query);

            isEmpty = !result.next();

            if (isEmpty)
                System.out.println("\nThe customer list is empty!\n");
            else {
                System.out.println("\nChoose a customer:");

                do {
                    int id = result.getInt("ID");
                    String customerName = result.getString("NAME");
                    System.out.println(id + ". " + customerName);
                } while (result.next());
            }
            result.close();
            statement.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return isEmpty;
    }

    public String getCustomerName(int customerId) {
        String name = null;
        try {
            connect = DriverManager.getConnection(url);

            String query = "SELECT NAME " +
                    "FROM CUSTOMER " +
                    "WHERE ID = ?";

            PreparedStatement preStmt = connect.prepareStatement(query);
            preStmt.setInt(1, customerId);
            ResultSet result = preStmt.executeQuery();

            result.next();
            name = result.getString("NAME");

            preStmt.close();
            result.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return name;
    }

    public void rentedCar(String customerName) {
        try {
            connect = DriverManager.getConnection(url);

            String query = "SELECT CAR.NAME " +
                    "FROM CUSTOMER C INNER JOIN CAR ON C.RENTED_CAR_ID = CAR.ID " +
                    "WHERE C.NAME = ?";

            PreparedStatement preStmt = connect.prepareStatement(query);
            preStmt.setString(1, customerName);
            ResultSet resultSet = preStmt.executeQuery();

            if (!resultSet.next())
                System.out.println("\nYou didn't rent a car!");
            else {
                query = "SELECT CAR.NAME AS carName, COM.NAME AS comName " +
                        "FROM CAR " +
                        "INNER JOIN CUSTOMER C ON CAR.ID = C.RENTED_CAR_ID " +
                        "INNER JOIN COMPANY COM on CAR.COMPANY_ID = COM.ID " +
                        "WHERE C.NAME = ?";

                preStmt = connect.prepareStatement(query);
                preStmt.setString(1, customerName);
                resultSet = preStmt.executeQuery();

                resultSet.next();
                String carName = resultSet.getString("carName");
                String companyName = resultSet.getString("comName");

                System.out.println("\n" +
                        "Your rented car:\n" +
                        carName + "\n" +
                        "Company:\n" +
                        companyName);
            }
            preStmt.close();
            resultSet.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void returnRented(String customerName) {
        try {
            connect = DriverManager.getConnection(url);

            String query =
                    "SELECT CAR.NAME " +
                            "FROM CUSTOMER C INNER JOIN CAR ON C.RENTED_CAR_ID = CAR.ID " +
                            "WHERE C.NAME = ?";

            PreparedStatement preStmt = connect.prepareStatement(query);
            preStmt.setString(1, customerName);
            ResultSet resultSet = preStmt.executeQuery();

            if (!resultSet.next())
                System.out.println("\nYou didn't rent a car!");
            else {
                query = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL " +
                        "WHERE NAME = ?";

                preStmt = connect.prepareStatement(query);
                preStmt.setString(1, customerName);
                preStmt.executeUpdate();

                System.out.println("\nYou've returned a rented car!");
            }
            preStmt.close();
            resultSet.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public boolean companyListCustomer(String customer) {
        boolean isEmptyOrRented = false;
        try {
            connect = DriverManager.getConnection(url);
            statement = connect.createStatement();

            String query1 = "SELECT * FROM COMPANY";
            String query2 = "SELECT CAR.NAME " +
                    "FROM CAR INNER JOIN CUSTOMER C ON CAR.ID = C.RENTED_CAR_ID " +
                    "WHERE C.NAME = ?";

            ResultSet result1 = statement.executeQuery(query1);
            PreparedStatement preStmt = connect.prepareStatement(query2);
            preStmt.setString(1, customer);
            ResultSet result2 = preStmt.executeQuery();

            boolean isEmpty = !result1.next();
            boolean isRented = result2.next();
            isEmptyOrRented = isEmpty || isRented;

            if (isEmpty)
                System.out.println("\nThe company list is empty!");
            else if (isRented)
                System.out.println("\nYou've already rented a car!");
            else {
                System.out.println("\nChoose a company:");

                do {
                    int id = result1.getInt("ID");
                    String companyName = result1.getString("NAME");
                    System.out.println(id + ". " + companyName);
                } while (result1.next());
            }
            preStmt.close();
            result1.close();
            result2.close();
            statement.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return isEmptyOrRented;
    }

    public boolean getCompanyCarCustomer(String companyName) {
        boolean isEmpty = false;
        try {
            connect = DriverManager.getConnection(url);

            String query = "SELECT " +
                    "CAR.NAME AS NAME " +
                    "FROM CAR INNER JOIN COMPANY ON CAR.COMPANY_ID = COMPANY.ID " +
                    "LEFT JOIN CUSTOMER on CAR.ID = CUSTOMER.RENTED_CAR_ID " +
                    "WHERE COMPANY.NAME = ? " +
                    "AND CUSTOMER.RENTED_CAR_ID IS NULL";


            PreparedStatement preStmt = connect.prepareStatement(query);
            preStmt.setString(1, companyName);
            ResultSet result = preStmt.executeQuery();

            isEmpty = !result.next();

            if (isEmpty)
                System.out.printf("\nNo available cars in the '%s' company\n", companyName);
            else {
                System.out.println("\nChoose a car:");

                int id = 0;
                do {
                    id++;
                    String carName = result.getString("NAME");
                    System.out.println(id + ". " + carName);
                } while (result.next());
            }
            preStmt.close();
            result.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return isEmpty;
    }

    public void rentCar(String customerName, String companyName, int option) {
        try {
            connect = DriverManager.getConnection(url);

            String query = "SELECT CAR.NAME AS carName, CAR.ID AS carId " +
                    "FROM CAR INNER JOIN COMPANY C ON C.ID = CAR.COMPANY_ID " +
                    "WHERE C.NAME = ?";


            PreparedStatement preStmt = connect.prepareStatement(query);
            preStmt.setString(1, companyName);
            ResultSet result = preStmt.executeQuery();


            int id = 0;
            String query2;
            while (result.next()) {
                id++;
                if (id == option) {
                    int carId = result.getInt("carId");
                    query2 = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? " +
                            "WHERE NAME = ?";

                    preStmt = connect.prepareStatement(query2);
                    preStmt.setInt(1, carId);
                    preStmt.setString(2, customerName);
                    preStmt.executeUpdate();
                    System.out.printf("\nYou rented '%s'\n", result.getString("carName"));
                    break;
                }
            }
            preStmt.close();
            result.close();
            connect.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}

