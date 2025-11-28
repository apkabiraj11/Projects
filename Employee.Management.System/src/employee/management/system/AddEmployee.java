package employee.management.system;

import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;
import java.awt.event.*;
import java.sql.*;

public class AddEmployee extends JFrame implements ActionListener {

    Random ran = new Random();
    int number = ran.nextInt(999999);

    JTextField tfname, tffname, tfaddress, tfphone, tfaadhar, tfemail, tfsalary, tfdesignation;
    JDateChooser dcdob;
    JComboBox<String> cbeducation;
    JLabel lblempId;
    JButton add, back;

    AddEmployee() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Add Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);

        tfname = new JTextField();
        tfname.setBounds(200, 150, 150, 30);
        add(tfname);

        JLabel labelfname = new JLabel("Father's Name");
        labelfname.setBounds(400, 150, 150, 30);
        labelfname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelfname);

        tffname = new JTextField();
        tffname.setBounds(600, 150, 150, 30);
        add(tffname);

        JLabel labeldob = new JLabel("Date of Birth");
        labeldob.setBounds(50, 200, 150, 30);
        labeldob.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldob);

        dcdob = new JDateChooser();
        dcdob.setBounds(200, 200, 150, 30);
        add(dcdob);

        JLabel labelsalary = new JLabel("Salary");
        labelsalary.setBounds(400, 200, 150, 30);
        labelsalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsalary);

        tfsalary = new JTextField();
        tfsalary.setBounds(600, 200, 150, 30);
        add(tfsalary);

        JLabel labeladdress = new JLabel("Address");
        labeladdress.setBounds(50, 250, 150, 30);
        labeladdress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(200, 250, 150, 30);
        add(tfaddress);

        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(400, 250, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);

        tfphone = new JTextField();
        tfphone.setBounds(600, 250, 150, 30);
        add(tfphone);

        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 300, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);

        JLabel labeleducation = new JLabel("Highest Education");
        labeleducation.setBounds(400, 300, 150, 30);
        labeleducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeleducation);

        String courses[] = {"BBA", "BCA", "BA", "BSC", "B.COM", "BTech", "MBA", "MCA", "MA", "MTech", "MSC", "PHD"};
        cbeducation = new JComboBox<>(courses);
        cbeducation.setBackground(Color.WHITE);
        cbeducation.setBounds(600, 300, 150, 30);
        add(cbeducation);

        JLabel labeldesignation = new JLabel("Designation");
        labeldesignation.setBounds(50, 350, 150, 30);
        labeldesignation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldesignation);

        tfdesignation = new JTextField();
        tfdesignation.setBounds(200, 350, 150, 30);
        add(tfdesignation);

        JLabel labelaadhar = new JLabel("NID Number");
        labelaadhar.setBounds(400, 350, 150, 30);
        labelaadhar.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(600, 350, 150, 30);
        add(tfaadhar);

        JLabel labelempId = new JLabel("Employee id");
        labelempId.setBounds(50, 400, 150, 30);
        labelempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelempId);

        // pad the random number to 6 digits
        String empIdStr = String.format("%06d", number);
        lblempId = new JLabel(empIdStr);
        lblempId.setBounds(200, 400, 150, 30);
        lblempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblempId);

        add = new JButton("Add Details");
        add.setBounds(250, 550, 150, 40);
        add.addActionListener(this);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add(add);

        back = new JButton("Back");
        back.setBounds(450, 550, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        setSize(900, 700);
        setLocation(300, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            try {
                String name = tfname.getText().trim();
                String fname = tffname.getText().trim();
                String dob = "";
                if (dcdob.getDate() != null) {
                    dob = ((JTextField) dcdob.getDateEditor().getUiComponent()).getText().trim();
                }
                String salary = tfsalary.getText().trim();
                String address = tfaddress.getText().trim();
                String phone = tfphone.getText().trim();
                String email = tfemail.getText().trim();
                String education = (String) cbeducation.getSelectedItem();
                String designation = tfdesignation.getText().trim();
                String aadhar = tfaadhar.getText().trim();
                String empId = lblempId.getText().trim();

                // Validation
                if (name.isEmpty() || !name.matches("[A-Za-z][A-Za-z\\s0-9]*")) {
                    throw new Exception("Name must start with a letter and can contain letters, digits and spaces.");
                }
                if (fname.isEmpty() || !fname.matches("[A-Za-z][A-Za-z\\s0-9]*")) {
                    throw new Exception("Father's Name must start with a letter and can contain letters, digits and spaces.");
                }
                if (dob.isEmpty()) {
                    throw new Exception("Date of birth is required.");
                }
                if (!salary.matches("[1-9][0-9]*")) {
                    throw new Exception("Salary must be a positive integer.");
                }
                if (address.isEmpty()) {
                    throw new Exception("Address is required.");
                }
                if (!phone.matches("01[0-9]{9}")) {
                    throw new Exception("Phone number must be 11 digits starting with 01.");
                }
                if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}")) {
                    throw new Exception("Enter a valid email address.");
                }
                if (!aadhar.matches("[0-9]{10}")) {
                    throw new Exception("NID Number must be exactly 10 digits.");
                }

                // Insert into database using PreparedStatement
                Conn conn = new Conn();
                String query = "INSERT INTO employee (empId, name, fname, dob, salary, address, phone, email, education, designation, aadhar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1, empId);
                pst.setString(2, name);
                pst.setString(3, fname);
                pst.setString(4, dob);
                pst.setString(5, salary);
                pst.setString(6, address);
                pst.setString(7, phone);
                pst.setString(8, email);
                pst.setString(9, education);
                pst.setString(10, designation);
                pst.setString(11, aadhar);

                pst.executeUpdate();
                pst.close();
                conn.c.close();

                JOptionPane.showMessageDialog(null, "Details added successfully");
                setVisible(false);
                new Home();

            } catch (SQLIntegrityConstraintViolationException si) {
                JOptionPane.showMessageDialog(null, "Employee ID or NID may already exist. " + si.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new AddEmployee();
    }
}
