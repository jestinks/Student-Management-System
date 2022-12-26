import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
class StudentRankingProject extends JFrame implements ActionListener
{
    final String username = "root";
    final String password = "root";
    final String dataConn = "jdbc:mysql://localhost:3306/student";
    String det[] = new String[5];
    int marks[] = new int[10]; int regindex[] = new int[10];
    JTextField regnot,namet,mark1t,mark2t,mark3t,checkt,totalmarkt,rankt;
    JLabel regno,name,mark1,mark2,mark3,check,totalmark,rank;
    JButton buttonS;
    int count = 10,ttlmarks,ranknum ,regnoid ;
    public StudentRankingProject()
    {
        setTitle("Student Management System");
        regnot = new JTextField ();
        namet = new JTextField ();
        mark1t = new JTextField ();
        mark2t = new JTextField ();
        mark3t = new JTextField ();
        checkt = new JTextField ();
        totalmarkt = new JTextField();
        rankt = new JTextField ();
        regno = new JLabel("Reg. No. ");
        name = new JLabel("Name ");
        mark1 = new JLabel("Mark 1");
        mark2 = new JLabel("Mark 2");
        mark3 = new JLabel("Mark 3");
        check = new JLabel("Check ");
        totalmark = new JLabel("Total Marks ");
        rank = new JLabel("Rank ");
        buttonS = new JButton("Submit");
        Uisetup();
        setLayout(null);
        setVisible(true);
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void Uisetup()
    {
        add(regno);
        regno.setBounds(30,30,60,20);
        add(regnot);
        regnot.setBounds(100,30,150,25);
        add(name);
        name.setBounds(280,30,60,20);
        add(namet);
        namet.setBounds(330,30,200,25);
        add(mark1t);
        mark1t.setBounds(30,70,150,25);
        add(mark2t); 
        mark2t.setBounds(205,70,150,25);
        add(mark3t);
        mark3t.setBounds(380,70,150,25);
        add(mark1);
        mark1.setBounds(85,100,60,20);
        add(mark2);
        mark2.setBounds(260,100,60,20);
        add(mark3);
        mark3.setBounds(435,100,60,20);
        add(buttonS);
        buttonS.setBounds(205,130,150,25);
        buttonS.addActionListener(this);  
        add(check); 
        check.setBounds(140,200,60,20);   
        add(checkt);
        checkt.setBounds(205,200,150,25);
        add(totalmark);
        totalmark.setBounds(30,250,80,20);
        add(totalmarkt);
        totalmarkt.setBounds(110,250,150,25);
        add(rank);
        rank.setBounds(320,250,40,20);
        add(rankt); 
        rankt.setBounds(360,250,150,25);
    }

    public void actionPerformed(ActionEvent evt)
    {         
        if(evt.getSource() == buttonS && checkt.getText().isEmpty() && !(regnot.getText().isEmpty() && namet.getText().isEmpty() && mark1t.getText().isEmpty() && mark2t.getText().isEmpty() && mark3t.getText().isEmpty()))
        {                 
            count--;             
            det[0] = regnot.getText();
            det[1] = namet.getText();
            det[2] = mark1t.getText();
            det[3] = mark2t.getText();
            det[4] = mark3t.getText();
            String namec = det[1];
            int regnoc = Integer.parseInt(det[0]);
            int mark1c = Integer.parseInt(det[2]);
            int mark2c = Integer.parseInt(det[3]);
            int mark3c = Integer.parseInt(det[4]);
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(dataConn,username,password);
                Statement stm = con.createStatement();
                String sql = "INSERT INTO studentdetails VALUES("+regnoc+",'"+namec+"',"+mark1c+","+mark2c+","+mark3c+")";
                stm.executeUpdate(sql); 
                JOptionPane.showMessageDialog(this, "Student Data Inserted ,\n 4 more left to insert");
                namet.setText("");
                regnot.setText("");
                mark1t.setText("");
                mark2t.setText("");
                mark3t.setText("");
                con.close();         
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        if(evt.getSource() == buttonS && (regnot.getText().isEmpty() && namet.getText().isEmpty() && mark1t.getText().isEmpty() && mark2t.getText().isEmpty() && mark3t.getText().isEmpty()) )
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(dataConn,username,password);
                Statement stm = conn.createStatement();
                String checkc = checkt.getText();
                int dis = Integer.parseInt(checkc);
                String sql = "SELECT RegisterNumber, Mark1, Mark2, Mark3 FROM studentdetails";
                ResultSet rs = stm.executeQuery(sql);
                int h = 0;
                while(rs.next())
                {
                    regnoid = rs.getInt("RegisterNumber");
                    int mark1id = rs.getInt("Mark1");
                    int mark2id = rs.getInt("Mark2");
                    int mark3id = rs.getInt("Mark3");
                    marks[h] = mark1id + mark2id + mark3id ;
                    regindex[h] = regnoid; h++;
                }
                rs.close();
                int i;
                for(i =0;i<10;i++)
                    if(regindex[i] == dis)
                    {
                        int distemp = marks[i];
                        Arrays.sort(marks);
                        reverse(marks);
                        for(int j = 0;j<10;j++)
                        {
                            if(distemp == marks[j])
                            {
                                totalmarkt.setText(distemp+"/180");
                                rankt.setText((j+1)+"/10");
                            }
                        }
                        break; 
                    }
                if(i==10)
                {
                    JOptionPane.showMessageDialog(this, "Entered Register Number Not found ! ");
                    checkt.setText("");
                    totalmarkt.setText("");
                    rankt.setText("");
                }
                conn.close();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            } 
        }
    }
    public void reverse(int[] array)
    {
        int n = array.length;
        for (int i = 0; i < n / 2; i++) {
            int temp = array[i];
            array[i] = array[n - i - 1];
            array[n - i - 1] = temp;
        }
    }
    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new StudentRankingProject();
            }
        });
    }
}
