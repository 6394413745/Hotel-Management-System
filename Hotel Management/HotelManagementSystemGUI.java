 import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Room Class
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
        isBooked = true;
    }

    public void vacateRoom() {
        isBooked = false;
    }

    public String getDetails() {
        return "Room Number: " + roomNumber + ", Type: " + roomType + ", Price: $" + price + ", Available: " + (!isBooked);
    }
}

// Customer Class
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
        this.room = room;
        room.bookRoom();
    }

    public void vacateRoom() {
        if (this.room != null) {
            room.vacateRoom();
            this.room = null;
        }
    }

    public String getDetails() {
        return "Customer: " + name + ", Phone: " + phone + ", Room: " + (room != null ? room.getRoomNumber() : "No Room");
    }
}

// Hotel Class
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
            if (room.getRoomType().equalsIgnoreCase(roomType) && !room.isBooked()) {
                return room;
            }
        }
        return null;
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isBooked()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}

// GUI Class for Hotel Management System
public class HotelManagementSystemGUI extends JFrame {

    private Hotel hotel;

    private JTextArea roomDetailsArea;
    private JTextArea customerDetailsArea;
    private JTextField customerNameField;
    private JTextField customerPhoneField;
    private JTextField roomTypeField;

    public HotelManagementSystemGUI() {
        hotel = new Hotel("Grand Hotel");

        // Adding some rooms for the demo
        hotel.addRoom(new Room(101, "Single", 100.0));
        hotel.addRoom(new Room(102, "Double", 150.0));
        hotel.addRoom(new Room(103, "Suite", 250.0));

        // Setting up the JFrame
        setTitle("Hotel Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        // Top Panel - Room and Customer Details
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));

        // Room Details Area
        roomDetailsArea = new JTextArea(10, 20);
        roomDetailsArea.setEditable(false);
        JScrollPane roomScrollPane = new JScrollPane(roomDetailsArea);
        topPanel.add(new JScrollPane(roomScrollPane));

        // Customer Details Area
        customerDetailsArea = new JTextArea(10, 20);
        customerDetailsArea.setEditable(false);
        JScrollPane customerScrollPane = new JScrollPane(customerDetailsArea);
        topPanel.add(customerScrollPane);

        // Bottom Panel - Booking Section
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(4, 2));

        // Customer Name Input
        bottomPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField(10);
        bottomPanel.add(customerNameField);

        // Customer Phone Input
        bottomPanel.add(new JLabel("Customer Phone:"));
        customerPhoneField = new JTextField(10);
        bottomPanel.add(customerPhoneField);

        // Room Type Input
        bottomPanel.add(new JLabel("Room Type (Single/Double/Suite):"));
        roomTypeField = new JTextField(10);
        bottomPanel.add(roomTypeField);

        // Buttons
        JButton bookButton = new JButton("Book Room");
        JButton vacateButton = new JButton("Vacate Room");
        bottomPanel.add(bookButton);
        bottomPanel.add(vacateButton);

        // Adding panels to main panel
        mainPanel.add(topPanel);
        mainPanel.add(bottomPanel);

        // Adding main panel to the frame
        add(mainPanel);

        // Event Listeners for Buttons
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookRoom();
            }
        });

        vacateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vacateRoom();
            }
        });

        updateRoomDetails();
        updateCustomerDetails();
    }

    // Booking a room
    private void bookRoom() {
        String name = customerNameField.getText();
        String phone = customerPhoneField.getText();
        String roomType = roomTypeField.getText();

        if (name.isEmpty() || phone.isEmpty() || roomType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Room availableRoom = hotel.findAvailableRoom(roomType);
        if (availableRoom != null) {
            Customer customer = new Customer(name, phone);
            customer.bookRoom(availableRoom);
            hotel.addCustomer(customer);
            JOptionPane.showMessageDialog(this, "Room booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No available room of type " + roomType, "Error", JOptionPane.ERROR_MESSAGE);
        }

        updateRoomDetails();
        updateCustomerDetails();
    }

    // Vacating a room
    private void vacateRoom() {
        String name = customerNameField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Customer customer : hotel.getCustomers()) {
            if (customer.getName().equalsIgnoreCase(name)) {
                customer.vacateRoom();
                JOptionPane.showMessageDialog(this, "Room vacated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateRoomDetails();
                updateCustomerDetails();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Customer not found", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Update room details area
    private void updateRoomDetails() {
        roomDetailsArea.setText("Available Rooms:\n");
        for (Room room : hotel.getAvailableRooms()) {
            roomDetailsArea.append(room.getDetails() + "\n");
        }
    }

    // Update customer details area
    private void updateCustomerDetails() {
        customerDetailsArea.setText("Customer Details:\n");
        for (Customer customer : hotel.getCustomers()) {
            customerDetailsArea.append(customer.getDetails() + "\n");
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HotelManagementSystemGUI().setVisible(true);
            }
        });
    }
}

    

