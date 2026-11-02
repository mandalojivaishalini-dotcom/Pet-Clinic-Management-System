
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// PARENT CLASS
abstract class Pet {

    private int petId;
    private String petName;
    private String ownerName;
    private int age;
    private double weight;
    private String species;
    private LocalDate vaccinationDate;

    static final double CONSULTATION_FEE = 300;
    static final double MEDICINE_RATE_PER_KG = 15;
    static final double SPECIALIST_FEE = 500;

    Pet(int id, String name, String owner, int age,
        double weight, String species, LocalDate date) {

        this.petId = id;
        this.petName = name;
        this.ownerName = owner;
        this.age = age;
        this.weight = weight;
        this.species = species;
        this.vaccinationDate = date;
    }

    // GETTERS
    public int getPetId() {
        return petId;
    }

    public double getWeight() {
        return weight;
    }

    public String getPetName() {
        return petName;
    }

    // METHOD OVERLOADING
    double calculateTreatmentCost() {
        return CONSULTATION_FEE + (weight * MEDICINE_RATE_PER_KG);
    }

    double calculateTreatmentCost(double discount) {
        double cost = calculateTreatmentCost();
        return cost - (cost * discount / 100);
    }

    // UPDATE DETAILS
    void updateDetails(double weight, int age) {
        this.weight = weight;
        this.age = age;
    }

    // ABSTRACT METHOD
    abstract void display();

    void displayDetails() {
        System.out.println("Pet ID          : " + petId);
        System.out.println("Pet Name        : " + petName);
        System.out.println("Owner Name      : " + ownerName);
        System.out.println("Species         : " + species);
        System.out.println("Age             : " + age);
        System.out.println("Weight          : " + weight + " kg");
        System.out.println("Vaccination Date: " + vaccinationDate);
    }
}

// DOG CLASS
class Dog extends Pet {

    Dog(int id, String name, String owner, int age,
        double weight, LocalDate date) {

        super(id, name, owner, age, weight, "Dog", date);
    }

    @Override
    void display() {
        displayDetails();
    }
}

// CAT CLASS
class Cat extends Pet {

    Cat(int id, String name, String owner, int age,
        double weight, LocalDate date) {

        super(id, name, owner, age, weight, "Cat", date);
    }

    @Override
    void display() {
        displayDetails();
    }
}

// BIRD CLASS
class Bird extends Pet {

    Bird(int id, String name, String owner, int age,
         double weight, LocalDate date) {

        super(id, name, owner, age, weight, "Bird", date);
    }

    @Override
    void display() {
        displayDetails();
    }
}

// MAIN CLASS
public class PetCare {

    static ArrayList<Pet> pets = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // SEARCH PET
    static Pet search(int id) {

        for (Pet pet : pets) {
            if (pet.getPetId() == id) {
                return pet;
            }
        }

        return null;
    }

    // ADD PET
    static void addPet() {

        try {
            System.out.print("Enter Pet ID: ");
            int id = Integer.parseInt(sc.nextLine());

            if (id <= 0 || search(id) != null) {
                System.out.println("Invalid or existing Pet ID!");
                return;
            }

            System.out.print("Enter Pet Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Owner Name: ");
            String owner = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Weight: ");
            double weight = Double.parseDouble(sc.nextLine());

            if (age < 0 || weight <= 0 ||
                name.trim().isEmpty() || owner.trim().isEmpty()) {
                System.out.println("Invalid pet details!");
                return;
            }

            System.out.print("Enter Species (Dog/Cat/Bird): ");
            String species = sc.nextLine().trim();

            System.out.print("Enter Vaccination Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            Pet pet = null;

            if (species.equalsIgnoreCase("Dog")) {
                pet = new Dog(id, name, owner, age, weight, date);
            } else if (species.equalsIgnoreCase("Cat")) {
                pet = new Cat(id, name, owner, age, weight, date);
            } else if (species.equalsIgnoreCase("Bird")) {
                pet = new Bird(id, name, owner, age, weight, date);
            } else {
                System.out.println("Invalid species!");
                return;
            }

            pets.add(pet);
            System.out.println("Pet added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric values!");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date! Use YYYY-MM-DD.");
        }
    }

    // DISPLAY ALL PETS
    static void displayPets() {

        if (pets.isEmpty()) {
            System.out.println("No pet records found!");
            return;
        }

        for (Pet pet : pets) {
            System.out.println("\n----------------------------");
            pet.display();
        }
    }

    // SEARCH PET DETAILS
    static void searchPet() {

        System.out.print("Enter Pet ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());

            Pet pet = search(id);

            if (pet != null) {
                pet.display();
            } else {
                System.out.println("Pet not found!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Pet ID!");
        }
    }

    // CALCULATE TREATMENT COST
    static void calculateBill() {

        System.out.print("Enter Pet ID: ");

        try {
            int id = Integer.parseInt(sc.nextLine());

            Pet pet = search(id);

            if (pet == null) {
                System.out.println("Pet not found!");
                return;
            }

            System.out.println("1. Normal Treatment");
            System.out.println("2. Treatment with Discount");

            System.out.print("Enter Choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            double bill;

            if (choice == 1) {
                bill = pet.calculateTreatmentCost();
            } else if (choice == 2) {

                System.out.print("Enter Discount Percentage: ");
                double discount = Double.parseDouble(sc.nextLine());

                if (discount < 0 || discount > 100) {
                    System.out.println("Invalid discount!");
                    return;
                }

                bill = pet.calculateTreatmentCost(discount);

            } else {
                System.out.println("Invalid choice!");
                return;
            }

            System.out.printf("Total Treatment Cost: Rs. %.2f%n", bill);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }

    // UPDATE PET DETAILS
    static void updatePet() {

        try {
            System.out.print("Enter Pet ID: ");
            int id = Integer.parseInt(sc.nextLine());

            Pet pet = search(id);

            if (pet == null) {
                System.out.println("Pet not found!");
                return;
            }

            System.out.print("Enter New Age: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Enter New Weight: ");
            double weight = Double.parseDouble(sc.nextLine());

            if (age < 0 || weight <= 0) {
                System.out.println("Invalid details!");
                return;
            }

            pet.updateDetails(weight, age);

            System.out.println("Pet details updated successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println("       PET CLINIC SYSTEM");
            System.out.println("================================");
            System.out.println("1. Add Pet");
            System.out.println("2. Display All Pets");
            System.out.println("3. Search Pet");
            System.out.println("4. Calculate Treatment Bill");
            System.out.println("5. Update Pet Details");
            System.out.println("6. Exit");
            System.out.println("================================");

            System.out.print("Enter Your Choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        addPet();
                        break;

                    case 2:
                        displayPets();
                        break;

                    case 3:
                        searchPet();
                        break;

                    case 4:
                        calculateBill();
                        break;

                    case 5:
                        updatePet();
                        break;

                    case 6:
                        System.out.println("Thank you for using Pet Clinic System!");
                        break;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }

            } catch (NumberFormatException e) {
                choice = 0;
                System.out.println("Please enter a valid menu option!");
            }

        } while (choice != 6);

        sc.close();
    }
}
