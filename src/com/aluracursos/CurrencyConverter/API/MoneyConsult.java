package com.aluracursos.CurrencyConverter.API;

import com.aluracursos.CurrencyConverter.models.Money;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class MoneyConsult {
    public static Money convertCurrency(Money originalMoney, String toCurrency, String direction) {
        HttpClient client = HttpClient.newHttpClient(); //Create new HTTP client
        //New API request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direction + originalMoney.getCurrency()))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .build();

        try {
            //Send the request and receive the response as a string
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                //If the response is successful it parses the JSON response body to obtain the necessary objects
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                //Gets the json object containing the conversion rates
                JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");
                //Gets the conversion rate for the new currency, if available
                double rate = rates.has(toCurrency) ? rates.get(toCurrency).getAsDouble() : 0.0;
                //If the conversion rate is 0, it indicates a failure, otherwise it calculates the new amount and returns a new Money object
                if (rate == 0.0) {
                    System.out.println("Failed to retrieve rate for " + toCurrency);
                    return null;
                } else {
                    double convertedAmount = originalMoney.getAmount() * rate;
                    return new Money(convertedAmount, toCurrency);
                }
            } else {
                System.out.println("Failed to get a valid response from the API: Status Code = " + response.statusCode());
                return null;
            }
            //Catches and handles any exceptions that occur during the HTTP request or response processing
        } catch (Exception e) {
            System.out.println("Error during currency conversion: " + e.getMessage());
            return null;
        }
    }
}
