import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Transactions extends JFrame implements ActionListener {

    JButton deposit, withdrawl, FashCash, ministatement, pinchange, balanceequiry, exit;

    static String pinnumber;

    Transactions(String pinnumber){
        this.pinnumber = pinnumber;
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900,900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);

        JLabel text = new JLabel("Please select your Transaction");
        text.setBounds(210,300,700,35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System",Font.BOLD,16));
        image.add(text);

        deposit = new JButton("Deposit");
        deposit.setBounds(355,415,150,30);
        deposit.addActionListener(this);
        image.add(deposit);

        withdrawl = new JButton("Cash Withdrawl");
        withdrawl.setBounds(170,415,150,30);
        withdrawl.addActionListener(this);
        image.add(withdrawl);

        FashCash = new JButton("Fast Cash");
        FashCash.setBounds(170,450,150,30);
        FashCash.addActionListener(this);
        image.add(FashCash);

        ministatement = new JButton("Mini Statement");
        ministatement.setBounds(355,450,150,30);
        ministatement.addActionListener(this);
        image.add(ministatement);

        pinchange = new JButton("PIN Change");
        pinchange.setBounds(170,485,150,30);
        pinchange.addActionListener(this);
        image.add(pinchange);

        balanceequiry = new JButton("Balance Equiry");
        balanceequiry.setBounds(355,485,150,30);
        balanceequiry.addActionListener(this);
        image.add(balanceequiry);

        exit = new JButton("Exit");
        exit.setBounds(355,520,150,30);
        exit.addActionListener(this);
        image.add(exit);

        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit){
            System.exit(0);
        } else if (e.getSource() == deposit){
            setVisible(false);
            new Deposit(pinnumber).setVisible(true);
        } else if(e.getSource() == withdrawl) {
            setVisible(false);
            new Withdrawl(pinnumber).setVisible(true);
        } else if (e.getSource() == FashCash){
            setVisible(false);
            new FashCash(pinnumber).setVisible(true);
        } else if (e.getSource() == pinchange){
            setVisible(false);
            new PinChange(pinnumber).setVisible(true);
        } else if (e.getSource() == balanceequiry){
            setVisible(false);
            new BalanceEnquiry(pinnumber).setVisible(true);
        } else if (e.getSource() == ministatement){
            setVisible(false);
            new MiniStatement(pinnumber).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Transactions(pinnumber);
    }
}
