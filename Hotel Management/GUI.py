import tkinter as tk
from tkinter import messagebox, ttk


class HotelManagementSystem:
    def __init__(self, root):
        self.root = root
        self.root.title("Hotel Management System")
        self.root.geometry("600x400")

        # Guest Information Storage
        self.guest_data = []

        # Title Label
        title_label = tk.Label(self.root, text="Hotel Management System", font=("Arial", 20, "bold"), bg="lightblue")
        title_label.pack(fill=tk.X)

        # Guest Name Label and Entry
        tk.Label(self.root, text="Guest Name:", font=("Arial", 12)).place(x=50, y=80)
        self.guest_name_entry = tk.Entry(self.root, font=("Arial", 12))
        self.guest_name_entry.place(x=200, y=80)

        # Room Number Label and Entry
        tk.Label(self.root, text="Room Number:", font=("Arial", 12)).place(x=50, y=120)
        self.room_number_entry = tk.Entry(self.root, font=("Arial", 12))
        self.room_number_entry.place(x=200, y=120)

        # Buttons: Add Guest, View Guests, Remove Guest
        add_button = tk.Button(self.root, text="Add Guest", font=("Arial", 12), command=self.add_guest)
        add_button.place(x=100, y=180)

        view_button = tk.Button(self.root, text="View Guests", font=("Arial", 12), command=self.view_guests)
        view_button.place(x=250, y=180)

        remove_button = tk.Button(self.root, text="Remove Guest", font=("Arial", 12), command=self.remove_guest)
        remove_button.place(x=400, y=180)

        # Table for Guest List
        self.tree = ttk.Treeview(self.root, columns=("Name", "Room"), show="headings", height=5)
        self.tree.heading("Name", text="Guest Name")
        self.tree.heading("Room", text="Room Number")
        self.tree.column("Name", width=200)
        self.tree.column("Room", width=100)
        self.tree.place(x=50, y=250)

    def add_guest(self):
        guest_name = self.guest_name_entry.get()
        room_number = self.room_number_entry.get()

        if guest_name == "" or room_number == "":
            messagebox.showwarning("Input Error", "Please enter all details")
        else:
            self.guest_data.append((guest_name, room_number))
            self.update_guest_list()
            self.clear_entries()

    def view_guests(self):
        self.update_guest_list()

    def remove_guest(self):
        selected_item = self.tree.selection()

        if not selected_item:
            messagebox.showwarning("Selection Error", "Please select a guest to remove")
        else:
            selected_guest = self.tree.item(selected_item)['values']
            self.guest_data = [guest for guest in self.guest_data if guest != tuple(selected_guest)]
            self.update_guest_list()

    def update_guest_list(self):
        # Clear the current data in the treeview
        for item in self.tree.get_children():
            self.tree.delete(item)

        # Add all guests back
        for guest in self.guest_data:
            self.tree.insert('', tk.END, values=guest)

    def clear_entries(self):
        self.guest_name_entry.delete(0, tk.END)
        self.room_number_entry.delete(0, tk.END)


if __name__ == "__main__":
    root = tk.Tk()
    app = HotelManagementSystem(root)
    root.mainloop()
