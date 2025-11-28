package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditProfile extends JFrame implements ActionListener {

    JButton changePic, changePass, back;
    Home homeReference;
    String username;

    // Constructor accepts Home reference and username
    EditProfile(Home homeRef, String username) {
        this.homeReference = homeRef;
        this.username = username;

        setLayout(null);

        JLabel heading = new JLabel("Edit Profile");
        heading.setBounds(150, 20, 300, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 28));
        add(heading);

        changePic = new JButton("Change Profile Picture");
        changePic.setBounds(120, 100, 250, 40);
        changePic.addActionListener(this);
        add(changePic);

        changePass = new JButton("Change Password");
        changePass.setBounds(120, 160, 250, 40);
        changePass.addActionListener(this);
        add(changePass);

        back = new JButton("Back");
        back.setBounds(120, 220, 250, 40);
        back.addActionListener(this);
        add(back);

        setSize(500, 350);
        setLocation(500, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == changePic) {
            setVisible(false);
            new ChangeProfilePicture(homeReference, username);
        } else if (ae.getSource() == changePass) {
            setVisible(false);
            new ChangePassword(username, homeReference);
        } else if (ae.getSource() == back) {
            setVisible(false);
            homeReference.setVisible(true);
        }
    }
}
