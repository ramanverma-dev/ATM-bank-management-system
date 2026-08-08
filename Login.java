import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JButton login, signup, clear;
    JTextField cardTextField;
    JPasswordField pinTextField;

    Login() {

        setTitle("\"Bank Management System - Login");
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bank.png"));
        Image i2 = i1.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(70,20,100,100);
        add(label);

        JLabel text = new JLabel("Welcome to Bank");
        text.setFont(new Font("Osward", Font.BOLD,38));
        text.setBounds(200,60,400,40);
        add(text);

        JLabel cardno = new JLabel("Card No: ");
        cardno.setFont(new Font("Raleway", Font.BOLD,28));
        cardno.setBounds(120,150,150,30);
        add(cardno);

        cardTextField = new JTextField();
        cardTextField.setBounds(300,150,250,30);
        cardTextField.setFont(new Font("Arial",Font.BOLD,14));
        add(cardTextField);

        JLabel pin = new JLabel("PIN: ");
        pin.setFont(new Font("Raleway", Font.BOLD,28));
        pin.setBounds(120,220,400,30);
        add(pin);

        pinTextField = new JPasswordField();
        pinTextField.setBounds(300,220,250,30);
        pinTextField.setFont(new Font("Arial",Font.BOLD,14));
        add(pinTextField);

        login = new JButton("SIGN IN");
        login.setBounds(300,300,100,30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        clear = new JButton("CLEAR");
        clear.setBounds(450,300,100,30);
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);

        signup = new JButton("SIGN UP");
        signup.setBounds(300,350,250,30);
        signup.setBackground(Color.BLACK);
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);

        getContentPane().setBackground(Color.WHITE);

        setSize(800,480);
        setVisible(true);
        setLocation(350,200);
    }

    public static void main(String[] args){
        new Login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == clear){
        cardTextField.setText("");
        pinTextField.setText("");
    }else if (e.getSource() == login){
        conn conn = new conn();

        String cardnumber = cardTextField.getText();
        String pinnumber = pinTextField.getText();
        String query = "select * from login where cardnumber = '"
                + cardnumber + "' and pinnumber = '"
                + pinnumber + "'";

        try{
            System.out.println("Card: " + cardnumber);
            System.out.println("PIN: " + pinnumber);
            System.out.println(query);
            ResultSet rs = conn.s.executeQuery(query);

            if (rs.next()) {
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,"Incorrect Card Number or Pin");
            }
        } catch (Exception ex){
            System.out.println(ex);
        }
    } else if (e.getSource() == signup){
    setVisible(false);
    new SignupOne().setVisible(true);
    }
    }
}
