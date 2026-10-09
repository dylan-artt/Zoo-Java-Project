import java.io.*;
import java.util.*;

abstract class Animal { //Animal parent class

    String name;
    int age;
    String colour;
    double weight;

    public Animal(String name, int age, String colour, double weight) { //The 4 parent variables which are inherited by the subclasses
        this.name = name;
        this.age = age;
        this.colour = colour;
        this.weight = weight;
    }

    public abstract String makeSound(); // Make sound method which is used by the subclasses and is overridden to produce unique sounds for each animal

    public boolean isValid() {
        return !(name.isEmpty() || colour.isEmpty() || age <= 0 || weight <= 0); //Checks that values are suitable for adding/editing an animal
    }

    public String getDetails() {
        return this.getClass().getSimpleName() + "," + name + "," + age + "," + colour + "," + weight; //Getter/setter for the variables
    }
}

interface Swimmable { //Interfaces which show the behaviour of my animals in the subclasses
    void swim();
    void performWaterCheck(); // NEW
}

interface Flyable {
    void fly();
    void wingHealthCheck(); // NEW
}




class Dog extends Animal { //Dog subclass

    String breed;
    public Dog(String name, int age, String colour, double weight, String breed) {
        super(name, age, colour, weight);
        this.breed = breed;
    }

    public String makeSound() {
        return "Woof! I am " + name + ", a " + age + " year old " + breed;
    }

    @Override //Overriding get details to add in the unique variable
    public String getDetails() {
        return super.getDetails() + "," + breed;
    }
}

class Shark extends Animal implements Swimmable {

    String waterType;

    public Shark(String name, int age, String colour, double weight, String waterType) {
        super(name, age, colour, weight);
        this.waterType = waterType;
    }

    public void swim() {
        System.out.println(name + " is swimming.");
    }

    public String makeSound() {
        return "Blub! I am " + name + " the shark.";
    }

    @Override
    public String getDetails() {
        return super.getDetails() + "," + waterType;
    }

    public void performWaterCheck() {
        System.out.println(name + " water health checked for today.");
    }
}

class Bird extends Animal implements Flyable {

    double wingSpan;

    public Bird(String name, int age, String colour, double weight, double wingSpan) {
        super(name, age, colour, weight);
        this.wingSpan = wingSpan;
    }

    public void fly() {
        System.out.println(name + " is flying.");
    }

    public String makeSound() {
        return "Chirp! I am " + name + " the bird.";
    }

    @Override
    public String getDetails() {
        return super.getDetails() + "," + wingSpan;
    }

    public void wingHealthCheck() {
        System.out.println(name + " wings checked for today."); //Informs the result of the wing check
    }
}

class Zoo { //Main zoo class

    String zooName;
    ArrayList<Animal> animals = new ArrayList<>(); //Where all the animals are stored during runtime. It's dynamic so allows an unlimited number of animals
    public void addAnimal(Animal a) { //Add animal method. This will add an animal to the array list

        if (a.isValid()) {
            animals.add(a); //Animal is added if the variables are valid
            System.out.println("Animal added successfully.");
        } else {
            System.out.println("Invalid animal - not saved. Ensure all data was filled and/or entered correctly.");
        }
    }

    public void removeAnimal(String name) {

        boolean removed = animals.removeIf(a -> a.name.equalsIgnoreCase(name)); //Takes the name of the animal to be removed
        if (removed) {
            System.out.println("Animal removed successfully.");
        } else { //Makes sure the animal exists
            System.out.println("No animal found with that name.");
        }
    }

    public void editAnimal(String name, String type) {
        String choice;
        Scanner sc2 = new Scanner(System.in);

        for (Animal a : animals) {
            if (a.name.equalsIgnoreCase(name) &&
                    a.getClass().getSimpleName().equalsIgnoreCase(type)) {// Checks for either the name and the type

                String oldName = a.name; // This saves the old details of the animal incase the new details have a problem
                int oldAge = a.age;
                String oldColour = a.colour;
                double oldWeight = a.weight;
                String oldBreed = "";
                String oldWaterType = "";
                double oldWingspan = 0;

                System.out.println("Name: " + a.name);
                System.out.println("Age: " + a.age);
                System.out.println("Colour: " + a.colour);
                System.out.println("Weight: " + a.weight);

                if (a instanceof Bird) { //Checks if it's a bird, shark or dog which ensures that the right characteristic is listed
                    Bird b = (Bird) a;
                    System.out.println("Wingspan: " + b.wingSpan);
                }
                else if (a instanceof Shark) {
                    Shark s = (Shark) a;
                    System.out.println("Water type: " + s.waterType);
                }
                else if (a instanceof Dog) {
                    Dog d = (Dog) a;
                    System.out.println("Breed: " + d.breed);
                }

                System.out.println("Enter characteristic to edit: "); //User enters their choice
                choice = sc2.nextLine();
                if (choice.equalsIgnoreCase("name")) {
                    System.out.println("Enter new name: ");
                    a.name = sc2.nextLine(); //Will read in the name and save it
                    System.out.println("Name changed to " + a.name);
                }
                else if (choice.equalsIgnoreCase("age")) {
                    System.out.println("Enter new age: ");
                    a.age = sc2.nextInt();
                    sc2.nextLine();
                    System.out.println("Age changed to " + a.age);
                }
                else if (choice.equalsIgnoreCase("colour")) {
                    System.out.println("Enter new colour: ");
                    a.colour = sc2.nextLine();
                    System.out.println("Colour changed to " + a.colour);
                }
                else if (choice.equalsIgnoreCase("weight")) {
                    System.out.println("Enter new weight: ");
                    a.weight = sc2.nextInt();
                    sc2.nextLine();
                    System.out.println("Weight changed to " + a.weight);
                }
                else if (choice.equalsIgnoreCase("wingspan") && a instanceof Bird) {
                    Bird b = (Bird) a;
                    oldWingspan = ((Bird) a).wingSpan;
                    System.out.println("Enter new wingspan: ");
                    b.wingSpan = sc2.nextInt();
                    System.out.println("Wingspan changed to " + b.wingSpan);
                }
                else if (choice.equalsIgnoreCase("water type") && a instanceof Shark) {
                    Shark s = (Shark) a;
                    oldWaterType = ((Shark) a).waterType;
                    System.out.println("Enter new water type: ");
                    s.waterType = sc2.nextLine();
                    System.out.println("Water type changed to " + s.waterType);
                }
                else if (choice.equalsIgnoreCase("breed") && a instanceof Dog) {
                    Dog d = (Dog) a;
                    oldBreed = ((Dog) a).breed;
                    System.out.println("Enter new breed: ");
                    d.breed = sc2.nextLine();
                    System.out.println("Breed changed to " + d.breed);
                }
                else {
                    System.out.println("Invalid selection");
                }

                if (!a.isValid())
                {
                    System.out.println("Invalid update. One or more fields invalid.");

                    a.name = oldName;
                    a.age = oldAge;
                    a.colour = oldColour;
                    a.weight = oldWeight;

                    if (a instanceof Dog) ((Dog) a).breed = oldBreed;
                    if (a instanceof Shark) ((Shark) a).waterType = oldWaterType;
                    if (a instanceof Bird) ((Bird) a).wingSpan = oldWingspan;
                    //If the field is invalid, this will reset the animal to it's old features and inform the user their changes can't be made
                }
            }
        }
    }

    public void viewAnimals() {

        for (Animal a : animals) {
                System.out.println("\nType: " + a.getClass().getSimpleName());
                System.out.println("Name: " + a.name); //Goes through the full list of animals and lists the type and name of each
        }
    }

    public void searchAnimal(String searchWord) {
        boolean found = false;

        for (Animal a : animals) {
            if (a.name.equalsIgnoreCase(searchWord) || a.colour.equalsIgnoreCase(searchWord)) { // Checks for the name or colour
                found = true;
                System.out.println("\nType: " + a.getClass().getSimpleName());
                System.out.println("Name: " + a.name);
                System.out.println("Age: " + a.age);
                System.out.println("Colour: " + a.colour);
                System.out.println("Weight: " + a.weight);

                if (a instanceof Dog) {
                    System.out.println("Breed: " + ((Dog) a).breed); // Will print out whatever characteristic is linked to the animal
                }
                else if (a instanceof Shark) {
                    System.out.println("Water Type: " + ((Shark) a).waterType);
                }
                else if (a instanceof Bird) {
                    System.out.println("Wingspan: " + ((Bird) a).wingSpan);
                }
                System.out.println(a.makeSound());
            }
        }
        if (!found) {
            System.out.println("Animal not found!"); //Informs the user if the animal isn't found
        }
    }

    public void report() {

        int dogs = 0;
        int shark= 0;
        int birds = 0;
        int maxCount = 0;
        String dominant = "";

        for (Animal a : animals) {
            if (a instanceof Dog) dogs++;
            if (a instanceof Shark) shark++;
            if (a instanceof Bird) birds++; //Goes through the array list and counts up each type of animal
        }

        for (Animal a : animals) {
            int count = 0;
            for (Animal b : animals) {
                if (a.colour.equalsIgnoreCase(b.colour)) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                dominant = a.colour; //Searches for colours and finds the most common colour
            }
        }
        System.out.println("Zoo: " + zooName);
        System.out.println("Dogs: " + dogs);
        System.out.println("Shark: " + shark);
        System.out.println("Birds: " + birds);
        System.out.println("Dominant colour: " + dominant);
    }

    public void performDailyCare() {

        Scanner nameCareScan = new Scanner(System.in);
        System.out.println("Name of animal for check: ");
        String name = nameCareScan.nextLine();
        for (Animal a : animals) { //Checks the name of the selected animal and performs the check
            if (a.name.equalsIgnoreCase(name)) {
                System.out.println("Performing check for " + a.name);

                if (a instanceof Flyable) {
                    ((Flyable) a).fly();
                    ((Flyable) a).wingHealthCheck(); //Performs a check using the interface
                }

                if (a instanceof Swimmable) {
                    ((Swimmable) a).swim();
                    ((Swimmable) a).performWaterCheck();
                }
            }
        }
    }

    public void saveToFile() {

        try {

            PrintWriter pw = new PrintWriter("AnimalDetails.txt"); //This is the file to be saved to for animals
            for (Animal a : animals) {
                if (a.isValid()) {
                    pw.println(a.getDetails()); //If animals are valid, details will be written to the file
                }
                else {
                    System.out.println("One or more animals had invalid details. One or more animals may not have saved.");
                }
            }
            pw.close();
            System.out.println("Animals saved to file.");

            PrintWriter zooWriter = new PrintWriter("ZooDetails.txt");
            zooWriter.println(zooName); //ZooName is rewritten to the file, as is the size of the zoo after saving
            zooWriter.println(animals.size());
            zooWriter.close();

        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    public void loadFromFile() {

        try {
            File fileZ = new File("ZooDetails.txt");
            if (!fileZ.exists()) return;

            Scanner scZ = new Scanner(fileZ);
            if (scZ.hasNextLine()) {
                zooName = scZ.nextLine(); // Zoo name is read back in
            }
            scZ.close();

            File fileA = new File("AnimalDetails.txt");
            if (!fileA.exists()) return;
            Scanner scA = new Scanner(fileA);
            while (scA.hasNextLine()) {

                String line = scA.nextLine();
                String[] details = line.split(","); //Details are read in from the file for the animals
                String type = details[0].trim();
                String name = details[1].trim();
                int age = Integer.parseInt(details[2].trim());
                String colour = details[3].trim();
                double weight = Double.parseDouble(details[4].trim());

                if (type.equals("Dog")) {
                    String breed = details[5];
                    animals.add(new Dog(name, age, colour, weight, breed)); //Variable read in depends on type
                }

                if (type.equals("Shark")) {
                    String species = details[5];
                    animals.add(new Shark(name, age, colour, weight, species));
                }

                if (type.equals("Bird")) {
                    double wingSpan = Double.parseDouble(details[5]);
                    animals.add(new Bird(name, age, colour, weight, wingSpan));
                }
            }
            scA.close();

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}

public class ZooMain {
    public static void main(String[] args) { //Main class which is displayed to the user

        Scanner sc = new Scanner(System.in);
        Zoo zoo = new Zoo();
        zoo.loadFromFile();
        int choice = 0;

        do {
            System.out.println("\n1. Add Animal"); //This is the main menu where each function can be selected by number
            System.out.println("2. Remove Animal");
            System.out.println("3. Edit Animals");
            System.out.println("4. View Animals");
            System.out.println("5. Search Animal");
            System.out.println("6. Report");
            System.out.println("7. Perform Daily Care");
            System.out.println("8. Save & Exit");
            choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                System.out.println("1. Dog  2. Shark  3. Bird"); // Takes in details to be passed to add animal
                int type = sc.nextInt();
                sc.nextLine();

                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Age: ");
                int age = sc.nextInt();

                System.out.print("Weight: ");
                double weight = sc.nextDouble();
                sc.nextLine();

                System.out.print("Colour: ");
                String colour = sc.nextLine();

                if (type == 1) {
                    System.out.print("Breed: ");
                    String breed = sc.nextLine();
                    zoo.addAnimal(new Dog(name, age, colour, weight, breed)); //Calls the add animal function and passes through the variables
                }

                if (type == 2) {
                    System.out.print("Water type: ");
                    String water = sc.nextLine();
                    zoo.addAnimal(new Shark(name, age, colour, weight, water));
                }

                if (type == 3) {
                    System.out.print("Wing span: ");
                    double wing = sc.nextDouble();
                    zoo.addAnimal(new Bird(name, age, colour, weight, wing));
                }
            }

            if (choice == 2) {
                System.out.print("Enter name: ");
                zoo.removeAnimal(sc.nextLine()); // Passes through the name of animal to be removed
            }

            if (choice == 3) {
                String editname, edittype;
                System.out.println("What type of animal would you like to edit?");
                edittype = sc.nextLine();
                System.out.println("What is the name of the animal you would like to edit"); //Passes name and type to be edited
                editname = sc.nextLine();
                zoo.editAnimal(editname, edittype);
            }

            if (choice == 4) {
                zoo.viewAnimals(); //Shows the view of every animal in the zoo
            }

            if (choice == 5) {
                System.out.print("Enter name or colour: ");
                zoo.searchAnimal(sc.nextLine()); //Passes the name or colour through to be searched
            }

            if (choice == 6) {
                zoo.report(); //Presents the report
            }

            if (choice == 7) {
                zoo.performDailyCare(); //Calls the daily care function
            }

            if (choice == 8) {
                zoo.saveToFile();
                System.out.println("Program ending."); //Exits the program and saves the details of animals and the zoo to file
            }

        } while (choice != 8);
        sc.close();
    }
}