package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class RemoveEmployee extends JFrame implements ActionListener {

    JTextField searchField;
    JButton search, delete, back;

    JLabel lblname, lblphone, lblemail;

    RemoveEmployee() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblId = new JLabel("Enter Employee ID:");
        lblId.setBounds(50, 30, 150, 30);
        add(lblId);

        searchField = new JTextField();
        searchField.setBounds(200, 30, 150, 30);
        add(searchField);

        search = new JButton("Search");
        search.setBounds(360, 30, 100, 30);
        search.addActionListener(this);
        add(search);

        JLabel labelname = new JLabel("Name:");
        labelname.setBounds(50, 100, 100, 30);
        add(labelname);

        lblname = new JLabel();
        lblname.setBounds(200, 100, 200, 30);
        add(lblname);

        JLabel labelphone = new JLabel("Phone:");
        labelphone.setBounds(50, 150, 100, 30);
        add(labelphone);

        lblphone = new JLabel();
        lblphone.setBounds(200, 150, 200, 30);
        add(lblphone);

        JLabel labelemail = new JLabel("Email:");
        labelemail.setBounds(50, 200, 100, 30);
        add(labelemail);

        lblemail = new JLabel();
        lblemail.setBounds(200, 200, 200, 30);
        add(lblemail);

        delete = new JButton("Delete");
        delete.setBounds(80, 280, 120, 30);
        delete.setBackground(Color.BLACK);
        delete.setForeground(Color.WHITE);
        delete.addActionListener(this);
        add(delete);

        back = new JButton("Back");
        back.setBounds(230, 280, 120, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/delete.png"));
        Image i2 = i1.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(380, 0, 600, 400);
        add(image);

        setSize(1000, 400);
        setLocation(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == search) {

            String empId = searchField.getText().trim();

            if (empId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter an Employee ID.");
                return;
            }

            try {
                Conn c = new Conn();
                String query = "SELECT name, phone, email FROM employee WHERE empId = ?";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, empId);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    lblname.setText(rs.getString("name"));
                    lblphone.setText(rs.getString("phone"));
                    lblemail.setText(rs.getString("email"));
                } else {
                    JOptionPane.showMessageDialog(null, "No employee found with this ID.");
                    lblname.setText("");
                    lblphone.setText("");
                    lblemail.setText("");
                }
                rs.close();
                pst.close();
                c.c.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == delete) {

            String empId = searchField.getText().trim();

            if (empId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please search an Employee ID first.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                Conn c = new Conn();
                String query = "DELETE FROM employee WHERE empId = ?";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, empId);
                int result = pst.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Employee Deleted Successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Employee ID not found.");
                }
                pst.close();
                c.c.close();

                setVisible(false);
                new Home();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new RemoveEmployee();
    }
}
