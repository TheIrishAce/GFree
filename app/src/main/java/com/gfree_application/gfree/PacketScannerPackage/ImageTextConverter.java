package com.gfree_application.gfree.PacketScannerPackage;

import java.util.ArrayList;

public class ImageTextConverter {
    ArrayList<String> dangerousIngridents = new ArrayList();
    ArrayList<String> warningIngridents = new ArrayList();
    ArrayList<String> safeIngridents = new ArrayList();

    public ImageTextConverter(){
        setDangerousIngredients();
        setWarningIngredients();
        setSafeIngredients();
    };

    //Create dangerous Ingredient array with dangerous ingredients.
    public void setDangerousIngredients() {
        dangerousIngridents.add("Gluten");
        dangerousIngridents.add("Corn"); //TEST
        dangerousIngridents.add("Barley Malt Extract");
        dangerousIngridents.add("Brewers Yeast");
        dangerousIngridents.add("Buckwheat");
        dangerousIngridents.add("Dextrin");
        dangerousIngridents.add("Ethanol");
        dangerousIngridents.add("Spelt");
        dangerousIngridents.add("Wheat");
        dangerousIngridents.add("Wheat Protein");
        dangerousIngridents.add("Hydrolyzed - Wheat Protein");
        dangerousIngridents.add("Malt");
        dangerousIngridents.add("Rye");
        dangerousIngridents.add("Guar Gum");
        dangerousIngridents.add("Seitan");
        dangerousIngridents.add("Soy Sauce");
        dangerousIngridents.add("Malt Vinegar");
        dangerousIngridents.add("Barley");
    }

    //Create warning Ingredient array with potentially unsafe ingredients.
    public void setWarningIngredients() {
        warningIngridents.add("Soya");
        warningIngridents.add("Oats");
        warningIngridents.add("Soy");
    }

    //Create safe Ingredient array with safe ingredients.
    public void setSafeIngredients() {
        safeIngridents.add("Lecithin");
        safeIngridents.add("Maltodextrin"); //TEST
        safeIngridents.add("Millet");
        safeIngridents.add("Silicon");
        safeIngridents.add("Starch");
        safeIngridents.add("Tapioca");
        safeIngridents.add("Xanthan Gum");
        safeIngridents.add("Modified Food Starch");
        safeIngridents.add("Gluten Free");
    }

    public String checkDangerousIngredients(String tokenizedIngredient, int imageTokenizedTextSize){
        //Output the Ingredient coming in.
        System.out.println("Ingredient Coming in equals: " + tokenizedIngredient);
        System.out.println("Ingredient List Size: " + dangerousIngridents.size());
        System.out.println("Tokenized Text List Size: " + imageTokenizedTextSize);
        String foundIngredient = "";

        for (int z = 0; z < dangerousIngridents.size(); z++){
            //Check if the Ingredient coming in is equal to any Ingredient from the list.
            if (tokenizedIngredient.contains(dangerousIngridents.get(z).toLowerCase())) {
                System.out.println("Returned Ingredient" + dangerousIngridents.get(z));
                foundIngredient = dangerousIngridents.get(z);
                break;
            }
        }

        //Check if array of dangerous ingridents is empty.
        if (dangerousIngridents.isEmpty()){
            System.out.println("Dangerous Ingredients Array is Empty");
        }
        
        System.out.println("ALL FOUND DANGERS : " + foundIngredient);
        return foundIngredient;
    };

    public String checkWarningIngredients(String tokenizedIngredient, int imageTokenizedTextSize){
        //Output the Ingredient coming in.
        System.out.println("Ingredient Coming in equals: " + tokenizedIngredient);
        System.out.println("Ingredient List Size: " + warningIngridents.size());
        System.out.println("Tokenized Text List Size: " + imageTokenizedTextSize);
        String foundIngrdient = "";

        for (int z = 0; z < warningIngridents.size(); z++){
            //Check if the Ingredient coming in is equal to any Ingredient from the list.
            if (tokenizedIngredient.contains(warningIngridents.get(z).toLowerCase())) {
                System.out.println("Returned Ingredient" + warningIngridents.get(z));
                foundIngrdient = warningIngridents.get(z);
                break;
            }
        }

        //Check if array of dangerous ingridents is empty.
        if (warningIngridents.isEmpty()){
            System.out.println("Warning Ingredients Array is Empty");
        }

        System.out.println("ALL FOUND WARNINGS : " + foundIngrdient);
        return foundIngrdient;
    };

    public String checkSafeIngredients(String tokenizedIngredient, int imageTokenizedTextSize){
        //Output the Ingredient coming in.
        System.out.println("Ingredient Coming in equals: " + tokenizedIngredient);
        System.out.println("Ingredient List Size: " + safeIngridents.size());
        System.out.println("Tokenized Text List Size: " + imageTokenizedTextSize);
        String foundIngrdient = "";

        for (int z = 0; z < safeIngridents.size(); z++){
            //Check if the Ingredient coming in is equal to any Ingredient from the list.
            if (tokenizedIngredient.contains(safeIngridents.get(z).toLowerCase())) {
                System.out.println("Returned Ingredient" + safeIngridents.get(z));
                foundIngrdient = safeIngridents.get(z);
                break;
            }
        }

        //Check if array of dangerous ingridents is empty.
        if (safeIngridents.isEmpty()){
            System.out.println("Safe Ingredients Array is Empty");
        }

        //System.out.println("Didn't Return Ingredient");
        System.out.println("ALL FOUND SAFE : " + foundIngrdient);
        return foundIngrdient;
    };
}
