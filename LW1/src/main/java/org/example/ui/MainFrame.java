//package org.example.ui;
//
//import org.example.model.Mission;
//import org.example.parser.IMissionParser;
//import org.example.parser.ParserFactory;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.File;
//
//public class MainFrame extends JFrame {
//    private JTextField txtMissionId;
//    private JTextField txtDate;
//    private JTextField txtLocation;
//    private JTextArea txtCurse;
//    private JTextArea areaSorcerers;
//    private JTextArea areaTechniques;
//    private JTextField txtOutcome;
//    private JTextField txtDamageCost;
//    private JTextArea areaNotes;
//
//    public MainFrame(){
//        setTitle("Анализатор миссий");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(700, 900);
//        setLocationRelativeTo(null);
//        start();
//    }
//
//    private void start(){
//        JPanel mainPanel = new JPanel(new GridLayout(15, 1, 5, 5));
//        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        JButton openButton = new JButton("Open file");
//        openButton.addActionListener(e -> openFile());
//        mainPanel.add(openButton);
//
//        mainPanel.add(new JLabel(""));
//
//        mainPanel.add(new JLabel("Mission ID:"));
//        txtMissionId = new JTextField();
//        txtMissionId.setEditable(false);
//        mainPanel.add(txtMissionId);
//
//        mainPanel.add(new JLabel("Date:"));
//        txtDate = new JTextField();
//        txtDate.setEditable(false);
//        mainPanel.add(txtDate);
//
//        mainPanel.add(new JLabel("Location:"));
//        txtLocation = new JTextField();
//        txtLocation.setEditable(false);
//        mainPanel.add(txtLocation);
//
//        mainPanel.add(new JLabel("Curse"));
//        txtCurse = new JTextArea();
//        txtCurse.setEditable(false);
//        JScrollPane scrollCurse = new JScrollPane(txtCurse);
//        mainPanel.add(scrollCurse);
//
//        mainPanel.add(new JLabel("Participants:"));
//        areaSorcerers = new JTextArea(3, 40);
//        areaSorcerers.setEditable(false);
//        JScrollPane scrollSorcerers = new JScrollPane(areaSorcerers);
//        mainPanel.add(scrollSorcerers);
//
//        mainPanel.add(new JLabel("Techniques:"));
//        areaTechniques = new JTextArea(3, 40);
//        areaTechniques.setEditable(false);
//        JScrollPane scrollTechniques = new JScrollPane(areaTechniques);
//        mainPanel.add(scrollTechniques);
//
//        mainPanel.add(new JLabel("Outcome:"));
//        txtOutcome = new JTextField();
//        txtOutcome.setEditable(false);
//        mainPanel.add(txtOutcome);
//
//        mainPanel.add(new JLabel("Damage Cost:"));
//        txtDamageCost = new JTextField();
//        txtDamageCost.setEditable(false);
//        mainPanel.add(txtDamageCost);
//
//        mainPanel.add(new JLabel("Notes"));
//        areaNotes = new JTextArea(2, 40);
//        areaNotes.setEditable(false);
//        JScrollPane scrollNotes = new JScrollPane(areaNotes);
//        mainPanel.add(scrollNotes);
//
//        JButton clearButton = new JButton("Очистить");
//        clearButton.addActionListener(e -> clearFields());
//        mainPanel.add(clearButton);
//
//        add(mainPanel);
//
//        clearFields();
//
//    }
//
//    private void openFile(){
//        JFileChooser fc = new JFileChooser();
//        int res = fc.showOpenDialog(this);
//        if (res == JFileChooser.APPROVE_OPTION) {
//            processFile(fc.getSelectedFile());
//        }
//    }
//
//    private void processFile(File file){
//        try{
//            IMissionParser parser = ParserFactory.getParser(file);
//            Mission mission = parser.parse(file);
//            displayMission(mission);
//            setTitle("Анализатор миссий " + file.getName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void displayMission(Mission mission){
//        txtMissionId.setText(nullSafe(mission.getMissionId()));
//        txtDate.setText(nullSafe(mission.getDate()));
//        txtLocation.setText(nullSafe(mission.getLocation()));
//
//        if (mission.getCurse() != null) {
//            txtCurse.setText(mission.getCurse().toString());
//        } else {
//            txtCurse.setText("");
//        }
//
//        StringBuilder sb = new StringBuilder();
//        int count1 = 1;
//        if (mission.getSorcerers() != null) {
//            for (Mission.Sorcerer s : mission.getSorcerers()) {
//                sb.append(count1).append(": ").append(s).append("\n");
//                count1++;
//            }
//        }
//        areaSorcerers.setText(sb.toString());
//
//        sb = new StringBuilder();
//        int count2 = 1;
//        if (mission.getTechniques() != null) {
//            for (Mission.Technique t : mission.getTechniques()) {
//                sb.append(count2).append(": ").append(t).append("\n");
//                count2++;
//            }
//        }
//        areaTechniques.setText(sb.toString());
//
//        //txtOutcome.setText(nullSafe(mission.getOutcome()));
//        txtDamageCost.setText(String.valueOf(mission.getDamageCost()));
//        areaNotes.setText(nullSafe(mission.getNotes()));
//    }
//
//    private void clearFields() {
//        txtMissionId.setText("");
//        txtDate.setText("");
//        txtLocation.setText("");
//        txtCurse.setText("");
//        areaSorcerers.setText("");
//        areaTechniques.setText("");
//        txtOutcome.setText("");
//        txtDamageCost.setText("0");
//        areaNotes.setText("");
//        setTitle("Анализатор миссий");
//    }
//    private String nullSafe(String value) {
//        return value != null && !value.isEmpty() ? value : "";
//    }
//
//
//
//
//}
package org.example.ui;

import org.example.model.Mission;
import org.example.parser.IMissionParser;
import org.example.parser.ParserFactory;
import org.example.report.*;
import org.example.validator.DamageCostValidator;
import org.example.validator.DateValidator;
import org.example.validator.MissionValidator;
import org.example.validator.Validator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JComboBox<String> reportSelector;

    public MainFrame() {
        setTitle("Анализатор миссий");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        start();
    }

    private void start() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton openButton = new JButton("Открыть файл");
        openButton.addActionListener(e -> openFile());
        topPanel.add(openButton);

        JButton clearButton = new JButton("Очистить");
        clearButton.addActionListener(e -> textArea.setText(""));
        topPanel.add(clearButton);

        topPanel.add(new JLabel("Тип отчета:"));
        String[] reportTypes = {
                "Базовый",
                "Краткий",
                "Детальный",
                "Со статистикой",
                "Анализ рисков",
                "Полный"
        };
        reportSelector = new JComboBox<>(reportTypes);
        reportSelector.setPreferredSize(new Dimension(150, 25));
        topPanel.add(reportSelector);

        JButton refreshButton = new JButton("Обновить отчет");
        refreshButton.addActionListener(e -> refreshReport());
        topPanel.add(refreshButton);

        JLabel infoLabel = new JLabel("Поддерживаемые форматы: TXT, JSON, XML, YAML");
        infoLabel.setForeground(Color.GRAY);
        topPanel.add(infoLabel);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Файлы миссий (*.txt, *.json, *.xml, *.yaml)", "txt", "json", "xml", "yaml"));

        showWelcomeMessage();
    }

    private Mission currentMission;

    private void openFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            processFile(fileChooser.getSelectedFile());
        }
    }

    private void processFile(File file) {
        try {
            IMissionParser parser = ParserFactory.getParser(file);
            currentMission = parser.parse(file);
            Validator validator = new MissionValidator();
            validator.setNext(new DateValidator());
            validator.setNext(new DamageCostValidator());

            ArrayList<String> errors = validator.validate(currentMission);

            if (!errors.isEmpty()) {
                System.out.println("Найдены проблемы: ");
                for (String err : errors) {
                    System.out.println(" " + err);
                }
            }

            displayReport();
            setTitle("Анализатор миссий - " + file.getName());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    private void refreshReport() {
        if (currentMission != null) {
            displayReport();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Сначала загрузите файл миссии",
                    "Информация",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void displayReport() {
        String selected = (String) reportSelector.getSelectedItem();
        IReport report = buildReport(selected);
        String output = report.generate(currentMission);
        textArea.setText(output);
    }

    private IReport buildReport(String type) {
        IReport base = new SimpleReport();

        switch (type) {
            case "Со статистикой":
                return new StatsDecorator(base);
            case "Анализ рисков":
                return new RiskDecorator(base);
            case "Полный":
                return new RiskDecorator(new StatsDecorator(base));
            case "Детальный":
                return new DetailedReport();
            default:
                return base;
        }
    }

    private void showWelcomeMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("-".repeat(70)).append("\n");
        sb.append("             АНАЛИЗАТОР МИССИЙ МАГОВ\n");
        sb.append("-".repeat(70)).append("\n");
        sb.append("1 Нажмите \"Открыть файл\" для загрузки миссии\n");
        sb.append("2.Выберите тип отчета в выпадающем списке\n");
        sb.append("3.Нажмите \"Обновить отчет\" для смены типа\n\n");
        sb.append("Поддерживаемые форматы:\n");
        sb.append("TXT файлы (*.txt)\n");
        sb.append("JSON файлы (*.json)\n");
        sb.append("XML файлы (*.xml)\n\n");
        sb.append("Типы отчетов:\n");
        sb.append("Базовый - основная информация\n");
        sb.append("Краткий - только ID и итог\n");
        sb.append("Детальный - полная информация\n");
        sb.append("Со статистикой - базовая + статистика\n");
        sb.append("Анализ рисков - оценка рисков\n");
        //sb.append("Полный - детальный + статистика + риски\n");
        textArea.setText(sb.toString());
    }
}