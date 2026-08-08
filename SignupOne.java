
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;

public class SignupOne extends JFrame implements ActionListener {

    long random;

    JTextField nameTextfield, fnameTextfield, emailTextfield, addressTextfield, cityTextfield, stateTextfield, pincodeTextfield;

    JButton button;

    JRadioButton male, female, others, other, married, unmarried;

    JDateChooser dateChooser;

    SignupOne() {

        setLayout(null);

        Random ran = new Random();
        this.random = Math.abs((ran.nextLong() % 9000L) + 1000L);

        setTitle("Application Form Page");

        JLabel formno = new JLabel("APPLICATION FORM NO. " + random);
        formno.setFont(new Font("Raleway",Font.BOLD,38));
        formno.setBounds(140,20,600,40);
        add(formno);

        JLabel personaldetails = new JLabel("Page 1: Personal Details");
        personaldetails.setFont(new Font("Raleway",Font.BOLD,22));
        personaldetails.setBounds(290,80,400,30);
        add(personaldetails);

        JLabel name = new JLabel("Name: ");
        name.setFont(new Font("Raleway",Font.BOLD,20));
        name.setBounds(100,160,100,30);
        add(name);

         nameTextfield = new JTextField();
        nameTextfield.setFont(new Font("Raleway",Font.BOLD,14));
        nameTextfield.setBounds(300,160,400,30);
        add(nameTextfield);

        JLabel fname = new JLabel("Father's Name:");
        fname.setFont(new Font("Raleway",Font.BOLD,20));
        fname.setBounds(100,210,150,30);
        add(fname);

         fnameTextfield = new JTextField();
        fnameTextfield.setFont(new Font("Raleway",Font.BOLD,14));
        fnameTextfield.setBounds(300,210,400,30);
        add(fnameTextfield);

        JLabel dob = new JLabel("Date of Birth:");
        dob.setFont(new Font("Raleway",Font.BOLD,20));
        dob.setBounds(100,260,150,30);
        add(dob);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(300,260,400,30);
        add(dateChooser);


        JLabel gender = new JLabel("Gender: ");
        gender.setFont(new Font("Raleway",Font.BOLD,20));
        gender.setBounds(100,310,100,30);
        add(gender);

         male = new JRadioButton("Male");
        male.setBackground(Color.WHITE);
        male.setBounds(300,310,60,30);
        add(male);

         female = new JRadioButton("Female");
        female.setBackground(Color.WHITE);
        female.setBounds(450,310,80,30);
        add(female);

         other = new JRadioButton("Other");
        other.setBackground(Color.WHITE);
        other.setBounds(600,310,100,30);
        add(other);

        ButtonGroup gendergroup = new ButtonGroup();
        gendergroup.add(male);
        gendergroup.add(female);
        gendergroup.add(other);


        JLabel email = new JLabel("Email Address:");
        email.setFont(new Font("Raleway",Font.BOLD,20));
        email.setBounds(100,360,150,30);
        add(email);

         emailTextfield = new JTextField();
        emailTextfield.setFont(new Font("Raleway",Font.BOLD,14));
        emailTextfield.setBounds(300,360,400,30);
        add(emailTextfield);

        JLabel marital = new JLabel("Marital Status:");
        marital.setFont(new Font("Raleway",Font.BOLD,20));
        marital.setBounds(100,410,150,30);
        add(marital);

         married = new JRadioButton("Married");
        married.setBackground(Color.WHITE);
        married.setBounds(300,410,100,30);
        add(married);

         unmarried = new JRadioButton("Unmarried");
        unmarried.setBackground(Color.WHITE);
        unmarried.setBounds(450,410,100,30);
        add(unmarried);

         others = new JRadioButton("Other");
        others.setBackground(Color.WHITE);
        others.setBounds(600,410,100,30);
        add(others);

        ButtonGroup status = new ButtonGroup();
        status.add(married);
        status.add(unmarried);
        status.add(others);

        JLabel address = new JLabel("Address:");
        address.setFont(new Font("Raleway",Font.BOLD,20));
        address.setBounds(100,460,100,30);
        add(address);

         addressTextfield = new JTextField();
        addressTextfield.setFont(new Font("Raleway",Font.BOLD,14));
        addressTextfield.setBounds(300,460,400,30);
        add(addressTextfield);

        JLabel city = new JLabel("City:");
        city.setFont(new Font("Raleway",Font.BOLD,20));
        city.setBounds(100,510,50,30);
        add(city);

         cityTextfield = new JTextField();
        cityTextfield.setFont(new Font("Raleway",Font.BOLD,14));
        cityTextfield.setBounds(300,510,400,30);
        add(cityTextfield);

        JLabel state = new JLabel("State:");
        state.setFont(new Font("Raleway",Font.BOLD,20));
        state.setBounds(100,560,100,30);
        add(state);

         stateTextfield = new JTextField();
        stateTextfield.setFont(new Font("Raleway",Font.BOLD,14));
        stateTextfield.setBounds(300,560,400,30);
        add(stateTextfield);

        JLabel pincode = new JLabel("Pin Code:");
        pincode.setFont(new Font("Raleway",Font.BOLD,20));
        pincode.setBounds(100,610,100,30);
        add(pincode);

         pincodeTextfield = new JTextField();
        pincodeTextfield.setFont(new Font("Raleway",Font.BOLD,14));
        pincodeTextfield.setBounds(300,610,400,30);
        add(pincodeTextfield);

        button = new JButton("Next");
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setBounds(620,660,80,30);
        button.addActionListener(this);
        add(button);


        getContentPane().setBackground(Color.WHITE);

        setSize(850, 800);
        setLocation(350,10);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SignupOne();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String formno =  "" + random; //  long
        String name = nameTextfield.getText();
        String fname = nameTextfield.getText();
        String dob = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        String gender = null;
        if (male.isSelected()){
            gender = "Male";
        } else if (female.isSelected()){
            gender = "female";
        } else if (other.isSelected()){
            gender = "Other";
        }

        String email = emailTextfield.getText();
        String marital =  null;
        if (unmarried.isSelected()){
            marital = "Unmarried";
        } else if (married.isSelected()){
            marital = "Married";
        } else if (others.isSelected()){
            marital = "Others";
        }

        String address = addressTextfield.getText();
        String city = cityTextfield.getText();
        String state = stateTextfield.getText();
        String pincode = pincodeTextfield.getText();

        try{
            if (name.equals("")){
                JOptionPane.showMessageDialog(null,"Name is Required");
            }else {
                conn c = new conn();
                String query = "insert into signup values('"+formno+"', '"+name+"', '"+fname+"','"+dob+"','"+gender+"', '"+email+"', '"+marital+"', '"+address+"', '"+city+"','"+state+"','"+pincode+"')";

                c.s.executeUpdate(query);

                setVisible(false);
                new SignupTwo(formno);
            }
        } catch (Exception E){
            System.out.println(E);
        }


    }
}
