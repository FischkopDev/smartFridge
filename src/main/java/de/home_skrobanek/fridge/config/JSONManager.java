package de.home_skrobanek.fridge.config;

import de.home_skrobanek.fridge.console.management.Console;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

//https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
public class JSONManager {

    private File fridge = new File("config/fridge.json");
    private File path = new File("config/");

    public JSONManager(){
        path.mkdir();
        try {
            fridge.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Console.debugMessage("loaded file manager");
    }

    public void writeInJSON(String name, String einheit, String amount, String category){
        //First Employee
        JSONObject employeeDetails = new JSONObject();
        employeeDetails.put("Einheit", einheit);
        employeeDetails.put("Menge", amount);
        employeeDetails.put("Kategorie", category);

        JSONObject employeeObject = new JSONObject();
        employeeObject.put(name, employeeDetails);

        //Add employees to list
        JSONArray employeeList = readFromJSON();
        employeeList.add(employeeObject);

        //Write JSON file
        try (FileWriter file = new FileWriter(fridge)) {

            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray readFromJSON() {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fridge)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;

            return employeeList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void parseEmployeeObject(JSONObject employee)
    {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("employee");

        //Get employee first name
        String firstName = (String) employeeObject.get("firstName");
        System.out.println(firstName);

        //Get employee last name
        String lastName = (String) employeeObject.get("lastName");
        System.out.println(lastName);

        //Get employee website name
        String website = (String) employeeObject.get("website");
        System.out.println(website);
    }
}
