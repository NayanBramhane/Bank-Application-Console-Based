package org.example;

import java.sql.*;
import java.util.Scanner;

public class Process {
    String url = "jdbc:mysql://localhost:3306/bank";
    String username = "root";
    String password = "root";
    Connection con;

    Process() {
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error at Process constructor\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    Scanner sc = new Scanner(System.in);

    private void getAccountNo(BankInfo bankInfo) {
        System.out.print("Enter your Account No: ");
        bankInfo.setAccno(sc.next());
    }

    public void openAccount() {
        BankInfo bankInfo = new BankInfo();
        System.out.print("Enter Account No: ");
        bankInfo.setAccno(sc.next());
        System.out.print("Enter Account type: ");
        bankInfo.setAcc_type(sc.next());
        System.out.print("Enter Name: ");
        bankInfo.setName(sc.next());
        System.out.print("Enter Balance: ");
        bankInfo.setBalance(sc.nextLong());

        // Insert into database
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("insert into user_information (name, acc_no, acc_type, balance) values ('" +
                    bankInfo.getName() + "','" +
                    bankInfo.getAccno() + "','" +
                    bankInfo.getAcc_type() + "'," +
                    bankInfo.getBalance() +
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("------YOUR ACCOUNT DETAILS ARE -------");
        System.out.println("Name of account holder :: " + bankInfo.getName());
        System.out.println("Account no             :: " + bankInfo.getAccno());
        System.out.println("Account type           :: " + bankInfo.getAcc_type());
        System.out.println("Balance                :: " + bankInfo.getBalance());
    }

    public void demoaccount() {
        int demobalance = 50000;
        System.out.println("Name of account holder :: " + "Demo user");
        System.out.println("Account no             :: " + "8529637412");
        System.out.println("Account type           :: " + "demo");
        System.out.println("Balance                :: " + demobalance);
    }

    public void deposit() {
        BankInfo bankInfo = new BankInfo();
        getAccountNo(bankInfo);
        System.out.println("Enter the Amount you want to deposit ::");
        long deposit = sc.nextLong();
        try {
            long oldBalance = 0;
            Statement statement = con.createStatement();

            // get previous balance
            ResultSet rs = statement.executeQuery("select balance from user_information where acc_no = " +
                    bankInfo.getAccno());

            if (rs.next()) oldBalance = rs.getLong("balance");
            else {
                System.out.println("No such user is present");
                return;
            }

            // add the deposit amount
            long newBalance = oldBalance + deposit;

            // update balance
            int rowCount = statement.executeUpdate("UPDATE user_information " +
                    "SET balance = '" + newBalance +
                    "' WHERE acc_no = " + bankInfo.getAccno() + ";");

            if (rowCount > 0) {
                System.out.println("Rs " + deposit + " is deposited into your Account");
            } else {
                System.out.println("Failed to deposit your money");
                return;
            }

            // get new balance from database
            ResultSet rs1 = statement.executeQuery("select balance from user_information where acc_no = " +
                    bankInfo.getAccno());
            if (rs1.next()) bankInfo.setBalance(rs1.getLong("balance"));
            else {
                System.out.println("Unable to retrieve the balance from database");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error at deposit()\n" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("Current Available Balance is Rs " + bankInfo.getBalance());
    }

    public void withdraw() {
        BankInfo bankInfo = new BankInfo();
        getAccountNo(bankInfo);
        System.out.println("Enter the Amount you want to withdraw:");
        long withdraw = sc.nextLong();
        try {
            long oldBalance = 0;
            Statement statement = con.createStatement();

            // get previous balance
            ResultSet rs = statement.executeQuery("select balance from user_information where acc_no = " +
                    bankInfo.getAccno());

            if (rs.next()) oldBalance = rs.getLong("balance");
            else {
                System.out.println("No such user is present");
                return;
            }

            bankInfo.setBalance(oldBalance);

            // subtract the withdrawal amount
            if (withdraw <= bankInfo.getBalance()) {
                bankInfo.setBalance(bankInfo.getBalance() - withdraw);

                // update balance in database
                int rowCount = statement.executeUpdate("UPDATE user_information " +
                        "SET balance = " + bankInfo.getBalance() +
                        " WHERE acc_no = " + bankInfo.getAccno() + ";");

                if (rowCount > 0) {
                    System.out.println("Rs " + withdraw + " is withdrawn from your Account");
                } else {
                    System.out.println("Failed to withdraw your money");
                    return;
                }
            } else {
                System.out.println("Low Balance");
                System.out.println("Current Available Balance is Rs " + bankInfo.getBalance());
                return;
            }

            // get new balance from database
            ResultSet rs1 = statement.executeQuery("select balance from user_information where acc_no = " +
                    bankInfo.getAccno());
            if (rs1.next()) bankInfo.setBalance(rs1.getLong("balance"));
            else {
                bankInfo.setBalance(0);
                System.out.println("Unable to retrieve the balance from database");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error at deposit()\n" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("Current Available Balance is Rs " + bankInfo.getBalance());
    }

    public void checkbalance() {
        BankInfo bankInfo = new BankInfo();
        getAccountNo(bankInfo);
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from user_information where acc_no = " +
                    bankInfo.getAccno());
            if (rs.next()) {
                bankInfo.setName(rs.getString("name"));
                bankInfo.setAcc_type(rs.getString("acc_type"));
                bankInfo.setBalance(rs.getLong("balance"));
            } else {
                System.out.println("No such user is present");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error occurred at checkbalance()\n" + e.getMessage());
        }
        System.out.println("Your name is              :: " + bankInfo.getName());
        System.out.println("Account no                :: " + bankInfo.getAccno());
        System.out.println("Account type              :: " + bankInfo.getAcc_type());
        System.out.println("Balance                   :: " + bankInfo.getBalance());
        System.out.println("THANKS FOR BALANCE CHECKING ");
    }
}
