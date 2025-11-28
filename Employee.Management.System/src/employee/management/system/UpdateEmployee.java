package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;

public class UpdateEmployee extends JFrame implements ActionListener {

    JTextField tfName, tfFName, tfSalary, tfAddress, tfPhone, tfEmail, tfDesignation, tfAadhar;
    JDateChooser dcDob;
    JComboBox<String> cbEducation;
    JButton btnSave, btnBack;
    String empId;

    // original values to keep if field left empty
    String origName, origFName, origDob, origSalary, origAddress, origPhone, origEmail, origEducation, origDesignation, origAadhar;

    public UpdateEmployee(String empId) {
        this.empId = empId;
        setLayout(null);
        setSize(900, 700);
        setLocation(300, 50);

        JLabel heading = new JLabel("Update Employee");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);

        tfName = new JTextField();
        tfName.setBounds(200, 150, 150, 30);
        add(tfName);

        JLabel labelfname = new JLabel("Father's Name");
        labelfname.setBounds(400, 150, 150, 30);
        labelfname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelfname);

        tfFName = new JTextField();
        tfFName.setBounds(600, 150, 150, 30);
        add(tfFName);

        JLabel labeldob = new JLabel("Date of Birth");
        labeldob.setBounds(50, 200, 150, 30);
        labeldob.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldob);

        dcDob = new JDateChooser();
        dcDob.setBounds(200, 200, 150, 30);
        add(dcDob);

        JLabel labelsalary = new JLabel("Salary");
        labelsalary.setBounds(400, 200, 150, 30);
        labelsalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsalary);

        tfSalary = new JTextField();
        tfSalary.setBounds(600, 200, 150, 30);
        add(tfSalary);

        JLabel labeladdress = new JLabel("Address");
        labeladdress.setBounds(50, 250, 150, 30);
        labeladdress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeladdress);

        tfAddress = new JTextField();
        tfAddress.setBounds(200, 250, 150, 30);
        add(tfAddress);

        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(400, 250, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);

        tfPhone = new JTextField();
        tfPhone.setBounds(600, 250, 150, 30);
        add(tfPhone);

        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 300, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);

        tfEmail = new JTextField();
        tfEmail.setBounds(200, 300, 150, 30);
        add(tfEmail);

        JLabel labeleducation = new JLabel("Highest Education");
        labeleducation.setBounds(400, 300, 150, 30);
        labeleducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeleducation);

        String courses[] = {"BBA", "BCA", "BA", "BSC", "B.COM", "BTech", "MBA", "MCA", "MA", "MTech", "MSC", "PHD"};
        cbEducation = new JComboBox<>(courses);
        cbEducation.setBounds(600, 300, 150, 30);
        add(cbEducation);

        JLabel labeldesignation = new JLabel("Designation");
        labeldesignation.setBounds(50, 350, 150, 30);
        labeldesignation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldesignation);

        tfDesignation = new JTextField();
        tfDesignation.setBounds(200, 350, 150, 30);
        add(tfDesignation);

        JLabel labelaadhar = new JLabel("NID Number");
        labelaadhar.setBounds(400, 350, 150, 30);
        labelaadhar.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelaadhar);

        tfAadhar = new JTextField();
        tfAadhar.setBounds(600, 350, 150, 30);
        add(tfAadhar);

        btnSave = new JButton("Save");
        btnSave.setBounds(250, 550, 150, 40);
        btnSave.setBackground(Color.BLACK);
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(this);
        add(btnSave);

        btnBack = new JButton("Back");
        btnBack.setBounds(450, 550, 150, 40);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(this);
        add(btnBack);

        // Load existing data
        loadEmployeeData();

        setVisible(true);
    }

    private void loadEmployeeData() {
        try {
            Conn c = new Conn();
            String q = "SELECT * FROM employee WHERE empId = ?";
            PreparedStatement pst = c.c.prepareStatement(q);
            pst.setString(1, empId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                origName = rs.getString("name");
                origFName = rs.getString("fname");
                origDob = rs.getString("dob");
                origSalary = rs.getString("salary");
                origAddress = rs.getString("address");
                origPhone = rs.getString("phone");
                origEmail = rs.getString("email");
                origEducation = rs.getString("education");
                origDesignation = rs.getString("designation");
                origAadhar = rs.getString("aadhar");

                // populate fields with existing values
                tfName.setText(origName);
                tfFName.setText(origFName);
                if (origDob != null && !origDob.isEmpty()) {
                    // JDateChooser expects Date object; if stored as string, skip setting
                    ((JTextField) dcDob.getDateEditor().getUiComponent()).setText(origDob);
                }
                tfSalary.setText(origSalary);
                tfAddress.setText(origAddress);
                tfPhone.setText(origPhone);
                tfEmail.setText(origEmail);
                cbEducation.setSelectedItem(origEducation);
                tfDesignation.setText(origDesignation);
                tfAadhar.setText(origAadhar);
            } else {
                JOptionPane.showMessageDialog(null, "Employee not found.");
                setVisible(false);
                new Home();
            }
            rs.close();
            pst.close();
            c.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnSave) {
            try {
                String name = tfName.getText().trim();
                String fname = tfFName.getText().trim();
                String dob = ((JTextField) dcDob.getDateEditor().getUiComponent()).getText().trim();
                String salary = tfSalary.getText().trim();
                String address = tfAddress.getText().trim();
                String phone = tfPhone.getText().trim();
                String email = tfEmail.getText().trim();
                String education = (String) cbEducation.getSelectedItem();
                String designation = tfDesignation.getText().trim();
                String aadhar = tfAadhar.getText().trim();

                // If user left a field empty, keep original
                if (name.isEmpty()) name = origName;
                if (fname.isEmpty()) fname = origFName;
                if (dob.isEmpty()) dob = origDob;
                if (salary.isEmpty()) salary = origSalary;
                if (address.isEmpty()) address = origAddress;
                if (phone.isEmpty()) phone = origPhone;
                if (email.isEmpty()) email = origEmail;
                if (education == null || education.isEmpty()) education = origEducation;
                if (designation.isEmpty()) designation = origDesignation;
                if (aadhar.isEmpty()) aadhar = origAadhar;

                // Basic validation as in AddEmployee
                if (!name.matches("[A-Za-z][A-Za-z\\s0-9]*")) {
                    throw new Exception("Name must start with a letter and can contain letters, digits and spaces.");
                }
                if (!fname.matches("[A-Za-z][A-Za-z\\s0-9]*")) {
                    throw new Exception("Father's Name must start with a letter and can contain letters, digits and spaces.");
                }
                if (!salary.matches("[1-9][0-9]*")) {
                    throw new Exception("Salary must be a positive integer.");
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

                Conn c = new Conn();
                String q = "UPDATE employee SET name = ?, fname = ?, dob = ?, salary = ?, address = ?, phone = ?, email = ?, education = ?, designation = ?, aadhar = ? WHERE empId = ?";
                PreparedStatement pst = c.c.prepareStatement(q);
                pst.setString(1, name);
                pst.setString(2, fname);
                pst.setString(3, dob);
                pst.setString(4, salary);
                pst.setString(5, address);
                pst.setString(6, phone);
                pst.setString(7, email);
                pst.setString(8, education);
                pst.setString(9, designation);
                pst.setString(10, aadhar);
                pst.setString(11, empId);

                int updated = pst.executeUpdate();
                pst.close();
                c.c.close();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(null, "Employee updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "No changes were made.");
                }
                setVisible(false);
                new Home();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == btnBack) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new UpdateEmployee("000000");
    }
}
