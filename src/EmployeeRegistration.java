import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeRegistration {
    private JPanel RegistrationForm;
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtContact;
    private JTextField txtAddress;
    private JButton saveButton;
    private JButton searchButton;
    private JButton DeleteButton;
    private JButton updateButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("EmployeeRegistration");
        frame.setContentPane(new EmployeeRegistration().RegistrationForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection conn;
    PreparedStatement pst;

    public EmployeeRegistration() {
        Connect();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String ename,eemail,econtact,eaddress;
              ename = txtName.getText();
              eemail = txtEmail.getText();
              econtact = txtContact.getText();
              eaddress = txtAddress.getText();
              try
              {
                  pst = conn.prepareStatement("insert into employee(Emp_Name,Emp_Email,Emp_Contact,Emp_Address)values(?,?,?,?)");
                  pst.setString(1,ename);
                  pst.setString(2,eemail);
                  pst.setString(3,econtact);
                  pst.setString(4,eaddress);
                  pst.executeUpdate();
                  JOptionPane.showMessageDialog(null,"Record has been Saved");
                  txtName.setText("");
                  txtEmail.setText("");
                  txtContact.setText("");
                  txtAddress.setText("");
                  txtName.requestFocusInWindow();
            }catch(Exception ex){
              ex.printStackTrace();
              }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String eid = txtId.getText();
                    pst = conn.prepareStatement("select Emp_Name,Emp_Email,Emp_Contact,Emp_Address from employee where id=?");
                    pst.setString(1,eid);
                    ResultSet rst = pst.executeQuery();
                    if(rst.next()==true)
                    {
                       String ename = rst.getString(1) ;
                        String email = rst.getString(2) ;
                        String econtact = rst.getString(3) ;
                        String eaddress = rst.getString(4) ;
                        txtName.setText(ename);
                        txtEmail.setText(email);
                        txtContact.setText(econtact);
                        txtAddress.setText(eaddress);
                    }
                    else{
                        txtName.setText("");
                        txtEmail.setText("");
                        txtContact.setText("");
                        txtAddress.setText("");
                        txtName.requestFocusInWindow();
                        JOptionPane.showMessageDialog(null,"No Record Found");

                    }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ename,eemail,econtact,eaddress,eid;
                ename = txtName.getText();
                eemail = txtEmail.getText();
                econtact = txtContact.getText();
                eaddress = txtAddress.getText();
                eid = txtId.getText();
                try
                {
                    pst = conn.prepareStatement("update employee set Emp_Name=?,Emp_Email=?,Emp_Contact=?,Emp_Address=? where id=?");
                    pst.setString(1,ename);
                    pst.setString(2,eemail);
                    pst.setString(3,econtact);
                    pst.setString(4,eaddress);
                    pst.setString(5,eid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record has been Updated");
                    txtName.setText("");
                    txtEmail.setText("");
                    txtContact.setText("");
                    txtAddress.setText("");
                    txtId.setText("");
                    txtName.requestFocusInWindow();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String eid = txtId.getText();
                    pst = conn.prepareStatement("delete from employee where id=?");
                    pst.setString(1,eid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record has been Deleted");
                    txtName.setText("");
                    txtEmail.setText("");
                    txtContact.setText("");
                    txtAddress.setText("");
                    txtId.setText("");
                    txtName.requestFocusInWindow();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }


    public void Connect(){

        try
        {

         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appmaven?useSSL=true", "root","root");
         System.out.println("Success");

        }catch (Exception e){e.printStackTrace();}
    }
}
