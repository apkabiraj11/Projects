package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class ViewEmployee extends JFrame implements ActionListener {

    JTable table;
    JTextField searchField;
    JButton search, print, update, back, showAll;
    JButton sortSalary, sortEmpId;

    ViewEmployee() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblSearch = new JLabel("Search Employee ID:");
        lblSearch.setBounds(20, 15, 150, 25);
        add(lblSearch);

        searchField = new JTextField();
        searchField.setBounds(170, 15, 150, 25);
        add(searchField);

        search = new JButton("Search");
        search.setBounds(330, 15, 90, 25);
        search.addActionListener(this);
        add(search);

        showAll = new JButton("Show All");
        showAll.setBounds(430, 15, 100, 25);
        showAll.addActionListener(this);
        add(showAll);

        sortSalary = new JButton("Sort by Salary");
        sortSalary.setBounds(600, 15, 140, 25);
        sortSalary.addActionListener(this);
        add(sortSalary);

        sortEmpId = new JButton("Sort by Employee ID");
        sortEmpId.setBounds(750, 15, 160, 25);
        sortEmpId.addActionListener(this);
        add(sortEmpId);

        table = new JTable();
        loadAllEmployees();

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 70, 900, 530);
        add(jsp);

        print = new JButton("Print");
        print.setBounds(150, 620, 100, 25);
        print.addActionListener(this);
        add(print);

        update = new JButton("Update");
        update.setBounds(300, 620, 100, 25);
        update.addActionListener(this);
        add(update);

        back = new JButton("Back");
        back.setBounds(450, 620, 100, 25);
        back.addActionListener(this);
        add(back);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    private void loadAllEmployees() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT empId, name, fname, dob, salary, address, phone, email, education, designation, aadhar FROM employee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
            c.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeesByQuery(String query) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
            c.c.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

    private String getSelectedEmployeeIdFromTable() {
        int row = table.getSelectedRow();
        if (row != -1) {
            Object val = table.getModel().getValueAt(row, 0); // empId assumed first column
            return val != null ? val.toString() : "";
        }
        return "";
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();

        if (src == search) {
            String empId = searchField.getText().trim();
            if (empId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Employee ID to search.");
                return;
            }
            String query = "SELECT empId, name, fname, dob, salary, address, phone, email, education, designation, aadhar FROM employee WHERE empId = '" + empId + "'";
            loadEmployeesByQuery(query);

        } else if (src == showAll) {
            loadAllEmployees();

        } else if (src == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (src == update) {
            String empId = searchField.getText().trim();
            if (empId.isEmpty()) {
                empId = getSelectedEmployeeIdFromTable();
            }
            if (empId == null || empId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter or select an Employee ID to update.");
                return;
            }
            setVisible(false);
            new UpdateEmployee(empId);

        } else if (src == sortSalary) {
            loadEmployeesByQuery("SELECT empId, name, fname, dob, salary, address, phone, email, education, designation, aadhar FROM employee ORDER BY CAST(salary AS UNSIGNED) ASC");

        } else if (src == sortEmpId) {
            loadEmployeesByQuery("SELECT empId, name, fname, dob, salary, address, phone, email, education, designation, aadhar FROM employee ORDER BY empId ASC");

        } else if (src == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}
