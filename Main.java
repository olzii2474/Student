package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class Main {
    static Registration reg = new Registration();
    static JTable table;
    static DefaultTableModel model;
    static JFrame windowFrame;

    public static void main(String[] args) {
        windowFrame = new JFrame("Student Registration System");
        windowFrame.setSize(900, 700);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setLayout(new BorderLayout());


        JPanel buttonPanel = new JPanel(new FlowLayout());
        String[] btnNames = {
                "Choose Files", "Subjects", "Majors", "GPA",
                "Expelled Students", "By Major"
        };
        JButton[] buttons = new JButton[btnNames.length];

        for (int i = 0; i < btnNames.length; i++) {
            buttons[i] = new JButton(btnNames[i]);
            buttonPanel.add(buttons[i]);
        }

        windowFrame.add(buttonPanel, BorderLayout.NORTH);


        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        windowFrame.add(scrollPane, BorderLayout.CENTER);


        buttons[0].addActionListener(e -> {
            String[] fileNames = FileChooser(windowFrame);
            try {
                if (fileNames[0] != null && fileNames[1] != null && fileNames[2] != null) {
                    reg.loadExams(fileNames[0]);
                    reg.loadSubjects(fileNames[1]);
                    reg.loadMajors(fileNames[2]);
                    JOptionPane.showMessageDialog(windowFrame, "Files loaded successfully!");
                } else {
                    JOptionPane.showMessageDialog(windowFrame, "Please choose all 3 files!");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(windowFrame, "Error loading files: " + ex.getMessage());
            }
        });

        buttons[1].addActionListener(e -> showSubjects());
        buttons[2].addActionListener(e -> showMajors());
        buttons[3].addActionListener(e -> showGPA());
        buttons[4].addActionListener(e -> showExpelledStudents());
        buttons[5].addActionListener(e -> showByMajor());

        windowFrame.setVisible(true);
    }

    // === FILE CHOOSER ===
    public static String[] FileChooser(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Choose Files", true);
        dialog.setSize(600, 200);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel studentLabel = new JLabel("Students file:");
        JLabel subjectLabel = new JLabel("Subjects file:");
        JLabel majorLabel = new JLabel("Majors file:");
        JButton studentBtn = new JButton("Browse...");
        JButton subjectBtn = new JButton("Browse...");
        JButton majorBtn = new JButton("Browse...");
        JButton okBtn = new JButton("OK");

        final String[] paths = new String[3];

        studentBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                paths[0] = fc.getSelectedFile().getAbsolutePath();
                studentLabel.setText("Students: " + paths[0]);
            }
        });

        subjectBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                paths[1] = fc.getSelectedFile().getAbsolutePath();
                subjectLabel.setText("Subjects: " + paths[1]);
            }
        });

        majorBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                paths[2] = fc.getSelectedFile().getAbsolutePath();
                majorLabel.setText("Majors: " + paths[2]);
            }
        });

        okBtn.addActionListener(e -> dialog.dispose());

        dialog.add(studentLabel);
        dialog.add(studentBtn);
        dialog.add(subjectLabel);
        dialog.add(subjectBtn);
        dialog.add(majorLabel);
        dialog.add(majorBtn);
        dialog.add(new JLabel());
        dialog.add(okBtn);

        dialog.setVisible(true);
        return paths;
    }


    static void showSubjects() {
        String[] cols = {"Subject Code", "Name", "Credit"};
        Object[][] data = reg.getSubjectsTable();
        model.setDataVector(data, cols);
    }

    static void showMajors() {
        String[] cols = {"Major Code", "Major Name"};
        Object[][] data = reg.getMajorsTable();
        model.setDataVector(data, cols);
    }

    static void showGPA() {
        String[] cols = {"Student Code", "GPA"};
        Object[][] data = reg.getGpaTable();
        model.setDataVector(data, cols);
    }

    static void showExpelledStudents() {
        String[] cols = {"Student Code", "F Count", "Status"};
        Object[][] data = reg.getExpelledTable();
        model.setDataVector(data, cols);
    }

    static void showByMajor() {
        String[] cols = {"Major", "Student Code", "GPA"};
        Object[][] data = reg.getByMajorTable();
        model.setDataVector(data, cols);
    }
}
