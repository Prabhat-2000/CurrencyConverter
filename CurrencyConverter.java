import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) throws IOException {
        // Add currency codes
        HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();
        currencyCodes.put(1,"USD");
        currencyCodes.put(2,"CAD");
        currencyCodes.put(3,"EUR");
        currencyCodes.put(4,"HKD");
        currencyCodes.put(5,"INR");

        String fromCode, toCode;
        Integer from,to;
        double amount;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the currency converter!");

        System.out.println("Currency converting from?");
        System.out.println("1:USD(US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR(Euro) \t 4:HKD(Hong Kong Dollar) \t 5:INR(Indian Rupee)");
        from= sc.nextInt();
        while(from<1 || from>5){
            System.out.println("please select a valid code");
            System.out.println("1:USD(US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR(Euro) \t 4:HKD(Hong Kong Dollar) \t 5:INR(Indian Rupee)");
            from = sc.nextInt();
        }
        fromCode = currencyCodes.get(from);


        System.out.println("Currency converting to?");
        System.out.println("1:USD(US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR(Euro) \t 4:HKD(Hong Kong Dollar) \t 5:INR(Indian Rupee)");
        to= sc.nextInt();
        while(to<1 || to>5){
            System.out.println("please select a valid code");
            System.out.println("1:USD(US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR(Euro) \t 4:HKD(Hong Kong Dollar) \t 5:INR(Indian Rupee)");
            to = sc.nextInt();
        }
        toCode = currencyCodes.get(to);

        System.out.println("Amount you wish to convert?");
        amount = sc.nextFloat();


        sendHttpGetRequest(fromCode, toCode, amount);

        System.out.println("Thank you for using the currency converter!");

    }


   private static void  sendHttpGetRequest(String fromCode,String toCode,double amount) throws IOException {

        String GET_URL = "https://api.exchangeratesapi.io/latest?base=" + toCode + "&symbols" +fromCode ;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){
            //success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) !=null){
                response.append(inputLine);
            }in.close();


           JSONObject obj = new JSONObject(response.toString());
           Double exchangeRate = obj.getJSONObject("rates").getDouble(fromCode);
            System.out.println(obj.getJSONObject("rates"));
            System.out.println(exchangeRate); //for debugging
            System.out.println();
            System.out.println(amount + fromCode + "=" + amount/exchangeRate + toCode);

        }else{
            System.out.println("get request failed due to server issue!");


        }

    }


}
