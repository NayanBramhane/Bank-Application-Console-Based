package org.example;

import java.util.Scanner;

public class Operation {
    public static void bankinfo() {
        Scanner scan = new Scanner(System.in);
        System.out.println(" ----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(" ***Banking Application***");
        System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println(" 1. Create a new account  \n 2. Check Balance\n 3. Deposit the amount \n 4. Withdraw the amount  \n 5. Watch demo account \n 6. Exit  \n\nENTER YOUR CHOICE :: ");
        int key = scan.nextInt();
        operation(key);
    }

    public static void operation(int key) {
        Scanner scan = new Scanner(System.in);
        Process bankprocess = new Process();
        switch (key) {
            case 1:
                System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                bankprocess.openAccount();
                System.out.println();
                System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println();
                System.out.println("MAIN PAGE_:: PRESS 1 ::");
                if (scan.nextInt() == 1)
                    bankinfo();
                break;
            case 2:
                System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                bankprocess.checkbalance();
                System.out.println();
                System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("MAIN PAGE_:: PRESS 1 ::");
                if (scan.nextInt() == 1)
                    bankinfo();
                break;
            case 3:
                System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println();
                System.out.println("---------------WELCOME TO DEPOSIT PAGE---------------- ");
                bankprocess.deposit();
                System.out.println();
                System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("MAIN PAGE_:: PRESS 1 ::");
                if (scan.nextInt() == 1)
                    bankinfo();
                break;
            case 4:
                System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                bankprocess.withdraw();
                System.out.println();
                System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("MAIN PAGE_:: PRESS 1 ::");
                if (scan.nextInt() == 1)
                    bankinfo();
                break;
            case 5:
                System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                bankprocess.demoaccount();
                System.out.println();
                System.out.println(" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("MAIN PAGE_:: PRESS 1 ::");
                if (scan.nextInt() == 1)
                    bankinfo();
                break;
            case 6:
                System.out.println("Thank you for using our banking application.");
                break;
        }
    }
}