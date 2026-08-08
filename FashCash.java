import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FashCash extends JFrame implements ActionListener {

    JButton btn100, btn500, btn1000, btn2000, btn5000, btn10000, back;

    static String pinnumber;

    FashCash(String pinnumber){
        this.pinnumber = pinnumber;
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900,900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);

        JLabel text = new JLabel("SELECT WITHDRAWL AMOUNT");
        text.setBounds(210,300,700,35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System",Font.BOLD,16));
        image.add(text);

        btn100 = new JButton("Rs 100");
        btn100.setBounds(355,415,150,30);
        btn100.addActionListener(this);
        image.add(btn100);

        btn500 = new JButton("Rs 500");
        btn500.setBounds(170,415,150,30);
        btn500.addActionListener(this);
        image.add(btn500);

        btn1000 = new JButton("Rs 1000");
        btn1000.setBounds(170,450,150,30);
        btn1000.addActionListener(this);
        image.add(btn1000);

        btn2000 = new JButton("Rs 2000");
        btn2000.setBounds(355,450,150,30);
        btn2000.addActionListener(this);
        image.add(btn2000);

        btn5000 = new JButton("Rs 5000");
        btn5000.setBounds(170,485,150,30);
        btn5000.addActionListener(this);
        image.add(btn5000);

        btn10000 = new JButton("Rs 10000");
        btn10000.setBounds(355,485,150,30);
        btn10000.addActionListener(this);
        image.add(btn10000);

        back = new JButton("BACK");
        back.setBounds(355,520,150,30);
        back.addActionListener(this);
        image.add(back);

        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            conn conn = new conn();

            if (e.getSource() == back) {

                setVisible(false);
                new Transactions(pinnumber).setVisible(true);

            } else if (e.getSource() == btn100) {

                conn.s.executeUpdate("insert into bank values('" + pinnumber + "', now(), 'Withdraw', '100')");
                JOptionPane.showMessageDialog(null, "Rs 100 Withdraw Successfully");
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);

            } else if (e.getSource() == btn500) {

                conn.s.executeUpdate("insert into bank values('" + pinnumber + "', now(), 'Withdraw', '500')");
                JOptionPane.showMessageDialog(null, "Rs 500 Withdraw Successfully");
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);

            } else if (e.getSource() == btn1000) {

                conn.s.executeUpdate("insert into bank values('" + pinnumber + "', now(), 'Withdraw', '1000')");
                JOptionPane.showMessageDialog(null, "Rs 1000 Withdraw Successfully");
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);

            } else if (e.getSource() == btn2000) {

                conn.s.executeUpdate("insert into bank values('" + pinnumber + "', now(), 'Withdraw', '2000')");
                JOptionPane.showMessageDialog(null, "Rs 2000 Withdraw Successfully");
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);

            } else if (e.getSource() == btn5000) {

                conn.s.executeUpdate("insert into bank values('" + pinnumber + "', now(), 'Withdraw', '5000')");
                JOptionPane.showMessageDialog(null, "Rs 5000 Withdraw Successfully");
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);

            } else if (e.getSource() == btn10000) {

                conn.s.executeUpdate("insert into bank values('" + pinnumber + "', now(), 'Withdraw', '10000')");
                JOptionPane.showMessageDialog(null, "Rs 10000 Withdraw Successfully");
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FashCash(pinnumber);
    }
}
