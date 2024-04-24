import com.aluracursos.CurrencyConverter.API.FileGenerator;
import com.aluracursos.CurrencyConverter.models.Money;
import com.aluracursos.CurrencyConverter.API.MoneyConsult;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //Read user input from the console
        Scanner lecture = new Scanner(System.in);
        //List to store Money objects that represent the conversions performed
        List<Money> money = new ArrayList<>();
        //Handle JSON file creation
        FileGenerator fileGenerator = new FileGenerator();
        //Serialize Java objects to JSON with field names in Camel Case and readable formats
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        int menu = 0;
        //APIKey and APIUrl
        String apiKey = "5aeff9b7d35ae7e559e4ed37";
        String direction = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/";

        //Menu
        do {
            System.out.println("************************************************************");
            System.out.println("Welcome to currency converter!\nPlease choose the option you prefer:");
            System.out.println("1. Dollar => Argentine Peso");
            System.out.println("2. Argentine Peso => Dollar");
            System.out.println("3. Dollar => Chilean Peso");
            System.out.println("4. Chilean Peso => Dollar");
            System.out.println("5. Argentine Peso => Chilean Peso");
            System.out.println("6. Chilean Peso => Argentine Peso");
            System.out.println("7. Exit");
            System.out.println("************************************************************");

            menu = lecture.nextInt();

            switch (menu){
                case 1:
                    //Call to the conversion method.
                    //lecture = scanner
                    //USD, ARS = String
                    //direction = API base URL
                    //money = List that stores objects of type Money
                    conversion(lecture, "USD", "ARS", direction, money);
                    break;
                case 2:
                    conversion(lecture, "ARS", "USD", direction, money);
                    break;
                case 3:
                    conversion(lecture, "USD", "CLP", direction, money);
                    break;
                case 4:
                    conversion(lecture, "CLP", "USD", direction, money);
                    break;
                case 5:
                    conversion(lecture, "ARS", "CLP", direction, money);
                    break;
                case 6:
                    conversion(lecture, "CLP", "ARS", direction, money);
                    break;
                case 7:
                    System.out.println("Thanks for using my program! :)))");
                    break;
                default:
                    System.out.println("That option doesn't exists!");
            }
        }while(menu!=7);

        fileGenerator.saveJSON(money);
    }

    //Private method that performs currency conversion
    private static void conversion(Scanner scanner, String fromCurrency, String toCurrency, String apiURL, List<Money> moneyList) {
        System.out.print("Enter the amount in " + fromCurrency + ": ");
        double amount = scanner.nextDouble();
        //Creation of a Money object to handle the data entered by the user
        Money originalMoney = new Money(amount, fromCurrency);
        //Interact with the API to convert the value of originalMoney to the specified currency in toCurrency
        Money convertedMoney = MoneyConsult.convertCurrency(originalMoney, toCurrency, apiURL);
        if (convertedMoney != null) {
            moneyList.add(convertedMoney);
            System.out.println("The money converted from " + fromCurrency + " to " + toCurrency + " is: " + convertedMoney);
        } else {
            System.out.println("Conversion failed!.");
        }
    }
}

