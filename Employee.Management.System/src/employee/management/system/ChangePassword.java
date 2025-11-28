package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ChangePassword extends JFrame implements ActionListener {

    JTextField currentUserField, newUserField;
    JPasswordField currentPassField, newPassField, confirmPassField;
    JButton changeBtn, backBtn;
    String username;
    Home homeRef;

    public ChangePassword(String username, Home homeRef) {
        this.username = username;
        this.homeRef = homeRef;

        setLayout(null);

        JLabel heading = new JLabel("Change Username / Password");
        heading.setBounds(80, 20, 350, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        JLabel lblCurrentUser = new JLabel("Current Username:");
        lblCurrentUser.setBounds(50, 70, 150, 25);
        add(lblCurrentUser);
        currentUserField = new JTextField(username);
        currentUserField.setBounds(210, 70, 180, 25);
        add(currentUserField);

        JLabel lblCurrentPass = new JLabel("Current Password:");
        lblCurrentPass.setBounds(50, 110, 150, 25);
        add(lblCurrentPass);
        currentPassField = new JPasswordField();
        currentPassField.setBounds(210, 110, 180, 25);
        add(currentPassField);

        JLabel lblNewUser = new JLabel("New Username:");
        lblNewUser.setBounds(50, 150, 150, 25);
        add(lblNewUser);
        newUserField = new JTextField();
        newUserField.setBounds(210, 150, 180, 25);
        add(newUserField);

        JLabel lblNewPass = new JLabel("New Password:");
        lblNewPass.setBounds(50, 190, 150, 25);
        add(lblNewPass);
        newPassField = new JPasswordField();
        newPassField.setBounds(210, 190, 180, 25);
        add(newPassField);

        JLabel lblConfirmPass = new JLabel("Confirm Password:");
        lblConfirmPass.setBounds(50, 230, 150, 25);
        add(lblConfirmPass);
        confirmPassField = new JPasswordField();
        confirmPassField.setBounds(210, 230, 180, 25);
        add(confirmPassField);

        changeBtn = new JButton("Change");
        changeBtn.setBounds(80, 280, 120, 30);
        changeBtn.addActionListener(this);
        add(changeBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(230, 280, 120, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(500, 380);
        setLocation(500, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == changeBtn) {

                String currentUser = currentUserField.getText().trim();
                String currentPass = new String(currentPassField.getPassword());
                String newUser = newUserField.getText().trim();
                String newPass = new String(newPassField.getPassword());
                String confirmPass = new String(confirmPassField.getPassword());

                if (currentUser.isEmpty() || currentPass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Current username and password are required.");
                    return;
                }

                // Verify current username & password
                Conn c = new Conn();
                String checkQ = "SELECT * FROM login WHERE username = ? AND password = ?";
                PreparedStatement pst = c.c.prepareStatement(checkQ);
                pst.setString(1, currentUser);
                pst.setString(2, currentPass);
                ResultSet rs = pst.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Current username or password is incorrect.");
                    rs.close();
                    pst.close();
                    c.c.close();
                    return;
                }

                rs.close();
                pst.close();

                // Validation for new password
                if (!newPass.isEmpty() && !newPass.equals(confirmPass)) {
                    JOptionPane.showMessageDialog(null, "New password and confirm password do not match.");
                    c.c.close();
                    return;
                }

                // If new username is blank, keep old username
                if (newUser.isEmpty()) {
                    newUser = currentUser;
                }

                // If new password is blank, keep old password
                if (newPass.isEmpty()) {
                    // Fetch old password
                    PreparedStatement pstOld = c.c.prepareStatement("SELECT password FROM login WHERE username=?");
                    pstOld.setString(1, currentUser);
                    ResultSet rsOld = pstOld.executeQuery();
                    if (rsOld.next()) {
                        newPass = rsOld.getString("password");
                    }
                    rsOld.close();
                    pstOld.close();
                }

                // Update login table
                String updateQ = "UPDATE login SET username = ?, password = ? WHERE username = ?";
                PreparedStatement updateStmt = c.c.prepareStatement(updateQ);
                updateStmt.setString(1, newUser);
                updateStmt.setString(2, newPass);
                updateStmt.setString(3, currentUser);
                int rows = updateStmt.executeUpdate();
                updateStmt.close();
                c.c.close();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Username and/or password updated successfully.");
                    username = newUser; // update local reference
                    setVisible(false);
                    homeRef.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Update failed.");
                }

            } else if (ae.getSource() == backBtn) {
                setVisible(false);
                homeRef.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
