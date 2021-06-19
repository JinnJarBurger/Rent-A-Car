import java.util.Scanner;

public class UI {
    CarShare carShare;

    public void run(String[] dbsName) {
        carShare = new CarShare();
        carShare.createTables(dbsName);
        mainMenu();
    }

    public void mainMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("""
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit""");

        int option = sc.nextInt();
        sc.nextLine();

        if (option == 1)
            menuTwo();
        else if (option == 2)
            customerChoice();
        else if (option == 3) {
            addCustomer();
        }
    }

    private void addCustomer() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nEnter the customer name:");
        carShare.insertCustomer(sc.nextLine());
        mainMenu();
    }

    private void customerChoice() {
        Scanner sc = new Scanner(System.in);
        int option;

        if (!carShare.customerList()) {
            System.out.println("0. Back");

            option = sc.nextInt();
            sc.nextLine();

            if (option == 0) {
                System.out.println();
                mainMenu();
            } else
                customerMenu(carShare.getCustomerName(option));
        } else
            mainMenu();
    }

    private void customerMenu(String customerName) {
        Scanner sc = new Scanner(System.in);
        int option;

        while (true) {
            System.out.println("""

                    1. Rent a car
                    2. Return a rented car
                    3. My rented car
                    0. Back""");

            option = sc.nextInt();

            if (option == 0) {
                System.out.println();
                mainMenu();
                break;
            } else if (option == 1) {
                if (!carShare.companyListCustomer(customerName)) {
                    System.out.println("0. Back");

                    option = sc.nextInt();
                    customerComCarList(customerName, carShare.getCompanyName(option));
                }
            } else if (option == 2) {
                carShare.returnRented(customerName);
            } else if (option == 3) {
                carShare.rentedCar(customerName);
            }
        }
    }

    private void customerComCarList(String customerName, String companyName) {
        Scanner sc = new Scanner(System.in);
        int option;

        if (!carShare.getCompanyCarCustomer(companyName)) {
            System.out.println("0. Back");

            option = sc.nextInt();

            carShare.rentCar(customerName, companyName, option);
        }
    }

    public void menuTwo() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""

                    1. Company list
                    2. Create a company
                    0. Back""");

            int option = sc.nextInt();
            sc.nextLine();

            if (option == 0) {
                System.out.println();
                mainMenu();
                break;
            } else if (option == 1) {
                menuThree();
                break;
            } else if (option == 2) {
                System.out.println("\nEnter the company name:");
                carShare.insertCompany(sc.nextLine());
            }
        }
    }

    public void menuThree() {
        Scanner sc = new Scanner(System.in);

        if (carShare.companyListManager())
            menuTwo();
        else {
            System.out.println("0. Back");

            int option = sc.nextInt();

            if (option == 0)
                menuTwo();
            else
                companyMenu(carShare.getCompanyName(option));
        }
    }

    public void companyMenu(String companyName) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n'" + companyName + "' company");

        while (true) {
            System.out.println("""
                    1. Car list
                    2. Create a car
                    0. Back"""
            );

            int option = sc.nextInt();
            sc.nextLine();

            if (option == 0) {
                menuTwo();
                break;
            } else if (option == 1) {
                carShare.getCompanyCarManager(companyName);
                System.out.println();
            } else if (option == 2) {
                System.out.println("\nEnter the car name:");
                carShare.insertCar(carShare.getCompanyID(companyName), sc.nextLine());
            }
        }
    }
}