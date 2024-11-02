package testing;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.Test;

import BankingManagmentSystem.Accounts;
import dataProviders.ExcelDataProvider;

public class AccountsTest {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank", "username", "password");
    }

    @Test(dataProvider = "accountsData", dataProviderClass = ExcelDataProvider.class)
    public void testOpenAccount(String email, String fullName, double balance, String securityPin, String expectedResult, String functionName) {
        try (Connection connection = getConnection();
             Scanner scanner = new Scanner(System.in)) {
            Accounts accounts = new Accounts(connection, scanner);
            String result = "";
            if ("open_account".equals(functionName)) {
                if (accounts.account_exist(email)) {
                    result = "Account Already Exists";
                } else {
                    result = "Account Created";
                }
            }
            Assert.assertEquals(result, expectedResult);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "accountsData", dataProviderClass = ExcelDataProvider.class)
    public void testGetAccountNumber(String email, String fullName, double balance, String securityPin, String expectedResult, String functionName) {
        try (Connection connection = getConnection();
             Scanner scanner = new Scanner(System.in)) {
            String result = "";
            if ("getAccount_number".equals(functionName)) {
                try {
                    result = "Account Number Found";
                } catch (RuntimeException e) {
                    result = e.getMessage();
                }
            }
            Assert.assertEquals(result, expectedResult);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
