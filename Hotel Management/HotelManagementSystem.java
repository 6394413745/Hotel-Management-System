import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class for Room
class Room {
    private int roomNumber;
    private String roomType;
    private double price;
    private boolean isBooked;

    public Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        if (!isBooked) {
            isBooked = true;
            System.out.println("Room " + roomNumber + " booked successfully.");
        } else {
            System.out.println("Room " + roomNumber + " is already booked.");
        }
    }

    public void vacateRoom() {
        if (isBooked) {
            isBooked = false;
            System.out.println("Room " + roomNumber + " vacated successfully.");
        } else {
            System.out.println("Room " + roomNumber + " is already vacant.");
        }
    }

    public String getDetails() {
        return "Room Number: " + roomNumber + ", Type: " + roomType + ", Price: $" + price + ", Available: " + (!isBooked);
    }
}

// Class for Customer
class Customer {
    private String name;
    private String phone;
    private Room room;

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.room = null;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Room getRoom() {
        return room;
    }

    public void bookRoom(Room room) {
        if (this.room == null && !room.isBooked()) {
            room.bookRoom();
            this.room = room;
        } else {
            System.out.println("Cannot book the room. Either you already have a room or the room is unavailable.");
        }
    }

    public void vacateRoom() {
        if (this.room != null) {
            this.room.vacateRoom();
            this.room = null;
        } else {
            System.out.println("No room to vacate.");
        }
    }

    public String getDetails() {
        return "Customer Name: " + name + ", Phone: " + phone + ", Room: " + (room != null ? room.getRoomNumber() : "No Room");
    }
}

// Class for Hotel
class Hotel {
    private String name;
    private List<Room> rooms;
    private List<Customer> customers;

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Room findAvailableRoom(String roomType) {
        for (Room room : rooms) {
            if (room.getRoomType().equals(roomType) && !room.isBooked()) {
                return room;
            }
        }
        return null;
    }

    public void showAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.println(room.getDetails());
            }
        }
    }

    public void showCustomerDetails() {
        System.out.println("Customer Details:");
        for (Customer customer : customers) {
            System.out.println(customer.getDetails());
        }
    }

    public Customer findCustomerByName(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }
}

// Main class for testing
public class HotelManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a hotel
        Hotel hotel = new Hotel("Grand Hotel");

        // Add rooms to the hotel
        hotel.addRoom(new Room(101, "Single", 100.0));
        hotel.addRoom(new Room(102, "Double", 150.0));
        hotel.addRoom(new Room(103, "Suite", 250.0));

        // Add customers to the hotel
        Customer customer1 = new Customer("Alice", "1234567890");
        Customer customer2 = new Customer("Bob", "0987654321");

        hotel.addCustomer(customer1);
        hotel.addCustomer(customer2);

        // Menu loop
        boolean running = true;
        while (running) {
            System.out.println("\n--- Hotel Management System ---");
            System.out.println("1. Show Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Vacate Room");
            System.out.println("4. Show Customer Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    hotel.showAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String roomType = scanner.nextLine();
                    Customer customer = hotel.findCustomerByName(customerName);
                    if (customer != null) {
                        Room availableRoom = hotel.findAvailableRoom(roomType);
                        if (availableRoom != null) {
                            customer.bookRoom(availableRoom);
                        } else {
                            System.out.println("No available rooms of type " + roomType);
                        }
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter customer name: ");
                    customerName = scanner.nextLine();
                    customer = hotel.findCustomerByName(customerName);
                    if (customer != null) {
                        customer.vacateRoom();
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 4:
                    hotel.showCustomerDetails();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
