package de.home_skrobanek.fridge.utils;

/*
    extra class for @Food. It only has the three important
    variables which will be displayed in the UI.
 */
public class FoodDisplay {

    private String name, amount, type;

    public FoodDisplay(String name, String amount, String type){
        this.name = name;
        this.amount = amount;
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
