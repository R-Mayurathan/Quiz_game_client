package Quiz_Game.rmi.client;

import Quiz_Game.Interface.Interface;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JOptionPane;
import javax.swing.Timer;
/*
 * This program generate quiz
 * I got the logic for this quiz from this tutorial https://www.youtube.com/watch?v=IbqPMxERvBQ
 * @author  Rasu Mayurathan
 * @version 1.0
 * @since   2021-11-22
 */
public class QuizGameGUI extends javax.swing.JFrame {

    public String QuestionId = "1";
    public String answer;
    public int min;
    public int sec;
    public int marks = 0;

    /**
     * *
     * This method is to check the answers
     */
    public void answerCheck() {//starting of answercheck()
        String studentAnswer = "";
        if (Opt1_RadioBtn.isSelected()) {
            studentAnswer = Opt1_RadioBtn.getText();
        } else if (Opt2__RadioBtn.isSelected()) {
            studentAnswer = Opt2__RadioBtn.getText();
        } else if (Opt3_RadioBtn.isSelected()) {
            studentAnswer = Opt3_RadioBtn.getText();
        } else {
            studentAnswer = Opt4_RadioBtn.getText();
        }

        if (studentAnswer.equals(answer)) {
            marks = marks + 1;
            String marks1 = String.valueOf(marks);
            YourMarks_Label.setText(marks1);
        }

        //question number change
        int questionId1 = Integer.parseInt(QuestionId);
        questionId1 = questionId1 + 1;
        QuestionId = String.valueOf(questionId1);

        //clear jRadioButton
        Opt1_RadioBtn.setSelected(false);
        Opt2__RadioBtn.setSelected(false);
        Opt3_RadioBtn.setSelected(false);
        Opt4_RadioBtn.setSelected(false);

        //last question hide next button
        if (QuestionId.equals("10")) {
            Next_Question_Btn.setVisible(false);
        }

    }//ending of answercheck()

    /**
     * *
     * This method is to get the questions
     */
    public void question() {

        try {
            /*The suspect that this block of statement can throw
         *exception so we handled it by placing these statements
         *inside try and handled the exception in catch block
             */

            Interface a = (Interface) Naming.lookup("rmi://localhost:1099/Server"); //It calls naming lookup and url      
            CachedRowSet questionDetails = a.getquestions(QuestionId);

            while (questionDetails.next()) {
                QuestionNumber_Label.setText(questionDetails.getString(1));
                Question.setText(questionDetails.getString(2));
                Opt1_RadioBtn.setText(questionDetails.getString(3));
                Opt2__RadioBtn.setText(questionDetails.getString(4));
                Opt3_RadioBtn.setText(questionDetails.getString(5));
                Opt4_RadioBtn.setText(questionDetails.getString(6));
                answer = questionDetails.getString(7);
            }

            /*
        * This block will only execute if any  exception occurs in try block
             */
        } catch (NotBoundException | RemoteException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } catch (MalformedURLException ex) {
            Logger.getLogger(QuizGameGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /***
     * This method is to submit the answers
     */
    public void submit()
    {//submit start
        String rollNo=RollNumber_Label.getText();
        answerCheck();
  
            boolean insert = false;
         try{ 
          /*The suspect that this block of statement can throw
           *exception so we handled it by placing these statements
           *inside try and handled the exception in catch block
           */
           
            Interface b = (Interface) Naming.lookup("rmi://localhost:1099/Server"); //It calls naming lookup and url     
            
            String marks1=String.valueOf(marks);
            insert = b.Updateresults(rollNo, marks1);
            
            if(insert == true)
            {
               setVisible(false);
               new QuizGameStudentScore(marks1).setVisible(true); 
            }
            
        }catch(NotBoundException | RemoteException ex){
            JOptionPane.showMessageDialog(null,"Error! "+ex,"Bug!",JOptionPane.ERROR_MESSAGE);
        } catch (MalformedURLException ex) {
            Logger.getLogger(QuizGameGUI.class.getName()).log(Level.SEVERE, null, ex);
        }        
          
          
   
     }//submit end
    

    /**
     * Creates new form quizGameStudent
     */
    public QuizGameGUI() {
        initComponents();
    }
    String name;
    Timer time;
      public QuizGameGUI(String rollNo) {//starting quizgame student
        initComponents();
        RollNumber_Label.setText(rollNo);
        
        //date
          SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
          Date date=new Date();
          Date_Label.setText(dateFormat.format(date));
   
        try {           
        Registry reg = LocateRegistry.getRegistry("localhost", 1099);
        Interface a = (Interface)reg.lookup("Server");
            
        CachedRowSet studentname=a.getStudentName(rollNo);
                   
        while(studentname.next())
        {   
            Name_Label.setText(studentname.getString(2));
            name =String.valueOf(studentname);     
        }
        
        CachedRowSet questionDetails=a.getquestions(QuestionId);
        
        while(questionDetails.next())
        {   
            QuestionNumber_Label.setText(questionDetails.getString(1));
            Question.setText(questionDetails.getString(2));
            Opt1_RadioBtn.setText(questionDetails.getString(3));
            Opt2__RadioBtn.setText(questionDetails.getString(4));
            Opt3_RadioBtn.setText(questionDetails.getString(5));
            Opt4_RadioBtn.setText(questionDetails.getString(6));
            answer=questionDetails.getString(7);            
        }
       /*
        * This block will only execute if any  exception occurs in try block
        */
        } catch (NotBoundException | RemoteException | SQLException e) {
	   JOptionPane.showMessageDialog(null, e);
	} 
          
                          //time program
              setLocationRelativeTo(this);
              time=new Timer(1000,new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent ae) {
                      Minute_Label.setText(String.valueOf(sec));
                      Secon_Label.setText(String.valueOf(min));
                      
                      if(sec==60)
                      {
                          sec=0;
                          min++;
                          if(min==10)
                          {
                              time.stop();
                              answerCheck();
                              submit();
                          }
                      }
                      sec++;
                  }
              });
              
              time.start();

             
    }//starting quizgame student

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Welcome = new javax.swing.JLabel();
        TotalTime = new javax.swing.JLabel();
        TenMIN = new javax.swing.JLabel();
        TimeTaken = new javax.swing.JLabel();
        RN = new javax.swing.JLabel();
        N = new javax.swing.JLabel();
        TQ = new javax.swing.JLabel();
        QN = new javax.swing.JLabel();
        YM = new javax.swing.JLabel();
        RollNumber_Label = new javax.swing.JLabel();
        Name_Label = new javax.swing.JLabel();
        Total_Question_Label = new javax.swing.JLabel();
        QuestionNumber_Label = new javax.swing.JLabel();
        YourMarks_Label = new javax.swing.JLabel();
        Opt1_RadioBtn = new javax.swing.JRadioButton();
        Opt2__RadioBtn = new javax.swing.JRadioButton();
        Opt3_RadioBtn = new javax.swing.JRadioButton();
        Opt4_RadioBtn = new javax.swing.JRadioButton();
        Next_Question_Btn = new javax.swing.JButton();
        Submit_Btn = new javax.swing.JButton();
        Date_Label = new javax.swing.JLabel();
        D = new javax.swing.JLabel();
        Secon_Label = new javax.swing.JLabel();
        Minute_Label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Question = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Welcome.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 50)); // NOI18N
        Welcome.setText("WELCOME");
        getContentPane().add(Welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        TotalTime.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        TotalTime.setForeground(new java.awt.Color(255, 255, 255));
        TotalTime.setText("Total Time");
        getContentPane().add(TotalTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 30, -1, -1));

        TenMIN.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        TenMIN.setForeground(new java.awt.Color(255, 255, 255));
        TenMIN.setText("10 min");
        getContentPane().add(TenMIN, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 30, -1, -1));

        TimeTaken.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        TimeTaken.setForeground(new java.awt.Color(255, 255, 255));
        TimeTaken.setText("Time Taken");
        getContentPane().add(TimeTaken, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 80, -1, -1));

        RN.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        RN.setForeground(new java.awt.Color(255, 255, 255));
        RN.setText("Roll Number");
        getContentPane().add(RN, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        N.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        N.setForeground(new java.awt.Color(255, 255, 255));
        N.setText("Name");
        getContentPane().add(N, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));

        TQ.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        TQ.setForeground(new java.awt.Color(255, 255, 255));
        TQ.setText("Total Question");
        getContentPane().add(TQ, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        QN.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        QN.setForeground(new java.awt.Color(255, 255, 255));
        QN.setText("Question Number");
        getContentPane().add(QN, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));

        YM.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        YM.setForeground(new java.awt.Color(255, 255, 255));
        YM.setText("Your Marks");
        getContentPane().add(YM, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, -1, -1));

        RollNumber_Label.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        RollNumber_Label.setForeground(new java.awt.Color(255, 255, 255));
        RollNumber_Label.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        getContentPane().add(RollNumber_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 200, 30));

        Name_Label.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        Name_Label.setForeground(new java.awt.Color(255, 255, 255));
        Name_Label.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        getContentPane().add(Name_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 200, 30));

        Total_Question_Label.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        Total_Question_Label.setForeground(new java.awt.Color(255, 255, 255));
        Total_Question_Label.setText("10");
        Total_Question_Label.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        getContentPane().add(Total_Question_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, -1, -1));

        QuestionNumber_Label.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        QuestionNumber_Label.setForeground(new java.awt.Color(255, 255, 255));
        QuestionNumber_Label.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        getContentPane().add(QuestionNumber_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 430, 200, 30));

        YourMarks_Label.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        YourMarks_Label.setForeground(new java.awt.Color(255, 255, 255));
        YourMarks_Label.setText("00");
        YourMarks_Label.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        getContentPane().add(YourMarks_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 530, -1, -1));

        Opt1_RadioBtn.setBackground(new java.awt.Color(255, 255, 255));
        Opt1_RadioBtn.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Opt1_RadioBtn.setText("Option 1");
        Opt1_RadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opt1_RadioBtnActionPerformed(evt);
            }
        });
        getContentPane().add(Opt1_RadioBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, -1, -1));

        Opt2__RadioBtn.setBackground(new java.awt.Color(255, 255, 255));
        Opt2__RadioBtn.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Opt2__RadioBtn.setText("Option 2");
        Opt2__RadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opt2__RadioBtnActionPerformed(evt);
            }
        });
        getContentPane().add(Opt2__RadioBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, -1, -1));

        Opt3_RadioBtn.setBackground(new java.awt.Color(255, 255, 255));
        Opt3_RadioBtn.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Opt3_RadioBtn.setText("Option 3");
        Opt3_RadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opt3_RadioBtnActionPerformed(evt);
            }
        });
        getContentPane().add(Opt3_RadioBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 450, -1, -1));

        Opt4_RadioBtn.setBackground(new java.awt.Color(255, 255, 255));
        Opt4_RadioBtn.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Opt4_RadioBtn.setText("Option 4");
        Opt4_RadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opt4_RadioBtnActionPerformed(evt);
            }
        });
        getContentPane().add(Opt4_RadioBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 540, -1, -1));

        Next_Question_Btn.setBackground(new java.awt.Color(255, 255, 255));
        Next_Question_Btn.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        Next_Question_Btn.setText("NEXT");
        Next_Question_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Next_Question_BtnActionPerformed(evt);
            }
        });
        getContentPane().add(Next_Question_Btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 630, -1, -1));

        Submit_Btn.setBackground(new java.awt.Color(255, 255, 255));
        Submit_Btn.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        Submit_Btn.setText("Submit");
        Submit_Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Submit_BtnActionPerformed(evt);
            }
        });
        getContentPane().add(Submit_Btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(975, 636, -1, -1));

        Date_Label.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        Date_Label.setForeground(new java.awt.Color(255, 255, 255));
        Date_Label.setText("showdate");
        getContentPane().add(Date_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 30, -1, -1));

        D.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        D.setForeground(new java.awt.Color(255, 255, 255));
        D.setText("Date:");
        getContentPane().add(D, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, -1, -1));

        Secon_Label.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        Secon_Label.setForeground(new java.awt.Color(255, 0, 51));
        Secon_Label.setText("00");
        getContentPane().add(Secon_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 80, -1, -1));

        Minute_Label.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        Minute_Label.setForeground(new java.awt.Color(255, 0, 51));
        Minute_Label.setText("00");
        getContentPane().add(Minute_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 80, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/LeftMainBg.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 0, new java.awt.Color(255, 255, 255)));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 770));

        jPanel1.setBackground(new java.awt.Color(52, 52, 52));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(255, 255, 255)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 889, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 119, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 890, 120));

        Question.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jScrollPane3.setViewportView(Question);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 810, 110));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Next_Question_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Next_Question_BtnActionPerformed
        //This code segment is to call answerCheck() and question() methods
        answerCheck();
        question();
    }//GEN-LAST:event_Next_Question_BtnActionPerformed

    private void Submit_BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Submit_BtnActionPerformed
        //This code segment to submit the answers
        int a=JOptionPane.showConfirmDialog(null,"Do youo really want to Submit","Select",JOptionPane.YES_NO_OPTION);
        if(a==0)
        {
           // answerCheck();
            submit();
        }
    }//GEN-LAST:event_Submit_BtnActionPerformed

    private void Opt1_RadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opt1_RadioBtnActionPerformed
        // This code segment is to select the answers
        if(Opt1_RadioBtn.isSelected()){
            Opt2__RadioBtn.setSelected(false);
            Opt3_RadioBtn.setSelected(false);
            Opt4_RadioBtn.setSelected(false);
        }
    }//GEN-LAST:event_Opt1_RadioBtnActionPerformed

    private void Opt2__RadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opt2__RadioBtnActionPerformed
        // This code segment is to select the answers
          if(Opt2__RadioBtn.isSelected()){
            Opt1_RadioBtn.setSelected(false);
            Opt3_RadioBtn.setSelected(false);
            Opt4_RadioBtn.setSelected(false);
        }
    }//GEN-LAST:event_Opt2__RadioBtnActionPerformed

    private void Opt3_RadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opt3_RadioBtnActionPerformed
        // This code segment is to select the answers
         if(Opt3_RadioBtn.isSelected()){
            Opt1_RadioBtn.setSelected(false);
            Opt2__RadioBtn.setSelected(false);
            Opt4_RadioBtn.setSelected(false);
        }
    }//GEN-LAST:event_Opt3_RadioBtnActionPerformed

    private void Opt4_RadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opt4_RadioBtnActionPerformed
        // This code segment is to select the answers
        if(Opt4_RadioBtn.isSelected()){
            Opt1_RadioBtn.setSelected(false);
            Opt2__RadioBtn.setSelected(false);
            Opt3_RadioBtn.setSelected(false);
            
        }
    }//GEN-LAST:event_Opt4_RadioBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuizGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuizGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuizGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuizGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuizGameGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel D;
    private javax.swing.JLabel Date_Label;
    private javax.swing.JLabel Minute_Label;
    private javax.swing.JLabel N;
    private javax.swing.JLabel Name_Label;
    private javax.swing.JButton Next_Question_Btn;
    private javax.swing.JRadioButton Opt1_RadioBtn;
    private javax.swing.JRadioButton Opt2__RadioBtn;
    private javax.swing.JRadioButton Opt3_RadioBtn;
    private javax.swing.JRadioButton Opt4_RadioBtn;
    private javax.swing.JLabel QN;
    private javax.swing.JTextPane Question;
    private javax.swing.JLabel QuestionNumber_Label;
    private javax.swing.JLabel RN;
    private javax.swing.JLabel RollNumber_Label;
    private javax.swing.JLabel Secon_Label;
    private javax.swing.JButton Submit_Btn;
    private javax.swing.JLabel TQ;
    private javax.swing.JLabel TenMIN;
    private javax.swing.JLabel TimeTaken;
    private javax.swing.JLabel TotalTime;
    private javax.swing.JLabel Total_Question_Label;
    private javax.swing.JLabel Welcome;
    private javax.swing.JLabel YM;
    private javax.swing.JLabel YourMarks_Label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
