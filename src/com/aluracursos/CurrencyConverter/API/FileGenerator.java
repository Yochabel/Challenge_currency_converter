package com.aluracursos.CurrencyConverter.API;

import com.aluracursos.CurrencyConverter.models.Money;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileGenerator {
    //Method that saves a list of Money objects to a JSON file
    public void saveJSON(List<Money> moneyList){
        try{
            //Create a JSON instance to print the well-formatted JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            //Create a FileWriter object to write to the CurrencyConverter.json file
            FileWriter escritura = new FileWriter("CurrencyConverter.json");
            //Convert the list of Money objects to JSON and write this JSON representation to the file
            escritura.write(gson.toJson(moneyList));
            //Close the flow to the file
            escritura.close();
            //Any IOException that may occur during the file write, prints an error message
        }catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
