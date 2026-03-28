package org.example.ui;

import org.example.model.Mission;
import org.example.parser.IMissionParser;
import org.example.parser.ParserFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {
    private JTextField txtMissionId;
    private JTextField txtDate;
    private JTextField txtLocation;
    private JTextArea txtCurse;
    private JTextArea areaSorcerers;
    private JTextArea areaTechniques;
    private JTextField txtOutcome;
    private JTextField txtDamageCost;
    private JTextArea areaNotes;

    public MainFrame(){
        setTitle("Анализатор миссий");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLocationRelativeTo(null);
        start();
    }

    private void start(){
        JPanel mainPanel = new JPanel(new GridLayout(15, 1, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton openButton = new JButton("Open file");
        openButton.addActionListener(e -> openFile());
        mainPanel.add(openButton);

        mainPanel.add(new JLabel(""));

        mainPanel.add(new JLabel("Mission ID:"));
        txtMissionId = new JTextField();
        txtMissionId.setEditable(false);
        mainPanel.add(txtMissionId);

        mainPanel.add(new JLabel("Date:"));
        txtDate = new JTextField();
        txtDate.setEditable(false);
        mainPanel.add(txtDate);

        mainPanel.add(new JLabel("Location:"));
        txtLocation = new JTextField();
        txtLocation.setEditable(false);
        mainPanel.add(txtLocation);

        mainPanel.add(new JLabel("Curse"));
        txtCurse = new JTextArea();
        txtCurse.setEditable(false);
        JScrollPane scrollCurse = new JScrollPane(txtCurse);
        mainPanel.add(scrollCurse);

        mainPanel.add(new JLabel("Participants:"));
        areaSorcerers = new JTextArea(3, 40);
        areaSorcerers.setEditable(false);
        JScrollPane scrollSorcerers = new JScrollPane(areaSorcerers);
        mainPanel.add(scrollSorcerers);

        mainPanel.add(new JLabel("Techniques:"));
        areaTechniques = new JTextArea(3, 40);
        areaTechniques.setEditable(false);
        JScrollPane scrollTechniques = new JScrollPane(areaTechniques);
        mainPanel.add(scrollTechniques);

        mainPanel.add(new JLabel("Outcome:"));
        txtOutcome = new JTextField();
        txtOutcome.setEditable(false);
        mainPanel.add(txtOutcome);

        mainPanel.add(new JLabel("Damage Cost:"));
        txtDamageCost = new JTextField();
        txtDamageCost.setEditable(false);
        mainPanel.add(txtDamageCost);

        mainPanel.add(new JLabel("Notes"));
        areaNotes = new JTextArea(2, 40);
        areaNotes.setEditable(false);
        JScrollPane scrollNotes = new JScrollPane(areaNotes);
        mainPanel.add(scrollNotes);

        JButton clearButton = new JButton("Очистить");
        clearButton.addActionListener(e -> clearFields());
        mainPanel.add(clearButton);

        add(mainPanel);

        clearFields();

    }

    private void openFile(){
        JFileChooser fc = new JFileChooser();
        int res = fc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            processFile(fc.getSelectedFile());
        }
    }

    private void processFile(File file){
        try{
            IMissionParser parser = ParserFactory.getParser(file);
            Mission mission = parser.parse(file);
            displayMission(mission);
            setTitle("Анализатор миссий " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayMission(Mission mission){
        txtMissionId.setText(nullSafe(mission.getMissionId()));
        txtDate.setText(nullSafe(mission.getDate()));
        txtLocation.setText(nullSafe(mission.getLocation()));

        if (mission.getCurse() != null) {
            txtCurse.setText(mission.getCurse().toString());
        } else {
            txtCurse.setText("");
        }

        StringBuilder sb = new StringBuilder();
        int count1 = 1;
        if (mission.getSorcerers() != null) {
            for (Mission.Sorcerer s : mission.getSorcerers()) {
                sb.append(count1).append(": ").append(s).append("\n");
                count1++;
            }
        }
        areaSorcerers.setText(sb.toString());

        sb = new StringBuilder();
        int count2 = 1;
        if (mission.getTechniques() != null) {
            for (Mission.Technique t : mission.getTechniques()) {
                sb.append(count2).append(": ").append(t).append("\n");
                count2++;
            }
        }
        areaTechniques.setText(sb.toString());

        //txtOutcome.setText(nullSafe(mission.getOutcome()));
        txtDamageCost.setText(String.valueOf(mission.getDamageCost()));
        areaNotes.setText(nullSafe(mission.getNotes()));
    }

    private void clearFields() {
        txtMissionId.setText("");
        txtDate.setText("");
        txtLocation.setText("");
        txtCurse.setText("");
        areaSorcerers.setText("");
        areaTechniques.setText("");
        txtOutcome.setText("");
        txtDamageCost.setText("0");
        areaNotes.setText("");
        setTitle("Анализатор миссий");
    }
    private String nullSafe(String value) {
        return value != null && !value.isEmpty() ? value : "";
    }




}
