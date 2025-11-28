package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.sql.*;

public class Home extends JFrame implements ActionListener {

    JButton view, add, update, remove;
    JLabel profilePicLabel;
    BufferedImage profileImage;
    String username; // logged-in user

    public Home(String username) {
        this.username = username;
        initUI();
        loadProfilePicFromDB();
    }

    // convenience constructor (if needed)
    public Home() {
        this(null);
    }

    private void initUI() {
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1120, 630);
        add(image);

        // Load default profile picture first
        try {
            profileImage = ImageIO.read(getClass().getClassLoader().getResource("icons/second.jpg"));
        } catch (Exception e) {
            profileImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        }

        profilePicLabel = new JLabel(new ImageIcon(getCircularImage(profileImage, 50)));
        profilePicLabel.setBounds(20, 20, 50, 50);
        profilePicLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profilePicLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new EditProfile(Home.this, username);
            }
        });
        image.add(profilePicLabel);

        JLabel heading = new JLabel("Employee Management System");
        heading.setBounds(620, 20, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        image.add(heading);

        add = new JButton("Add Employee");
        add.setBounds(650, 80, 150, 40);
        add.addActionListener(this);
        image.add(add);

        view = new JButton("View Employees");
        view.setBounds(820, 80, 150, 40);
        view.addActionListener(this);
        image.add(view);

        update = new JButton("Update Employee");
        update.setBounds(650, 140, 150, 40);
        update.addActionListener(this);
        image.add(update);

        remove = new JButton("Remove Employee");
        remove.setBounds(820, 140, 150, 40);
        remove.addActionListener(this);
        image.add(remove);

        setSize(1120, 630);
        setLocation(250, 100);
        setVisible(true);
    }

    // Circular crop helper
    private Image getCircularImage(BufferedImage src, int diameter) {
        BufferedImage resized = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resized.createGraphics();
        g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, diameter, diameter));
        g2.drawImage(src, 0, 0, diameter, diameter, null);
        g2.dispose();
        return resized;
    }

    // Update profile picture in UI only
    public void updateProfilePic(BufferedImage newPic) {
        profileImage = newPic;
        profilePicLabel.setIcon(new ImageIcon(getCircularImage(profileImage, 50)));
    }

    // Update profile picture path in DB for logged-in user
    public void updateProfilePicInDB(String path) {
        if (username == null) return;
        try {
            Conn c = new Conn();
            String q = "UPDATE login SET photo = ? WHERE username = ?";
            PreparedStatement pst = c.c.prepareStatement(q);
            pst.setString(1, path);
            pst.setString(2, username);
            pst.executeUpdate();
            pst.close();
            c.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load profile picture path from DB and display
    private void loadProfilePicFromDB() {
        if (username == null) return;
        try {
            Conn c = new Conn();
            String q = "SELECT photo FROM login WHERE username = ?";
            PreparedStatement pst = c.c.prepareStatement(q);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String path = rs.getString("photo");
                if (path != null && !path.trim().isEmpty()) {
                    try {
                        BufferedImage img = ImageIO.read(new File(path));
                        if (img != null) updateProfilePic(img);
                    } catch (Exception ex) {
                        // couldn't read file - ignore and keep default
                    }
                }
            }
            rs.close();
            pst.close();
            c.c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            setVisible(false);
            new AddEmployee();
        } else if (ae.getSource() == view) {
            setVisible(false);
            new ViewEmployee();
        } else if (ae.getSource() == update) {
            setVisible(false);
            new ViewEmployee();
        } else if (ae.getSource() == remove) {
            setVisible(false);
            new RemoveEmployee();
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}
