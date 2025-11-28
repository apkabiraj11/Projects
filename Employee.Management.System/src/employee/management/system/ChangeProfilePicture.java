package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ChangeProfilePicture extends JFrame implements ActionListener {

    JButton choose, save, back;
    JLabel preview;
    BufferedImage chosenImage;
    Home homeReference;
    String username;
    String selectedPath;

    public ChangeProfilePicture(Home homeRef, String username) {
        this.homeReference = homeRef;
        this.username = username;

        setLayout(null);

        JLabel heading = new JLabel("Change Profile Picture");
        heading.setBounds(120, 20, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        preview = new JLabel();
        preview.setBounds(120, 70, 150, 150);
        add(preview);

        choose = new JButton("Choose Image");
        choose.setBounds(300, 90, 150, 30);
        choose.addActionListener(this);
        add(choose);

        save = new JButton("Save");
        save.setBounds(120, 240, 150, 30);
        save.addActionListener(this);
        add(save);

        back = new JButton("Back");
        back.setBounds(300, 240, 150, 30);
        back.addActionListener(this);
        add(back);

        setSize(500, 350);
        setLocation(500, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src == choose) {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                selectedPath = f.getAbsolutePath();
                try {
                    chosenImage = ImageIO.read(f);
                    Image scaled = chosenImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    preview.setIcon(new ImageIcon(scaled));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Unable to read selected image.");
                    e.printStackTrace();
                }
            }
        } else if (src == save) {
            if (selectedPath == null || selectedPath.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please choose an image first.");
                return;
            }
            try {
                // Update UI immediately
                if (chosenImage != null) {
                    homeReference.updateProfilePic(chosenImage);
                }
                // Save path in DB for persistence
                homeReference.updateProfilePicInDB(selectedPath);
                JOptionPane.showMessageDialog(null, "Profile picture updated.");
                setVisible(false);
                homeReference.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving profile picture: " + e.getMessage());
            }
        } else if (src == back) {
            setVisible(false);
            homeReference.setVisible(true);
        }
    }
}
