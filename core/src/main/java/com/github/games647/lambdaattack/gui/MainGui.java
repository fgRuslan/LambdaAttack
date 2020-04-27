package com.github.games647.lambdaattack.gui;

import com.github.games647.lambdaattack.GameVersion;
import com.github.games647.lambdaattack.LambdaAttack;
import com.github.games647.lambdaattack.Options;
import com.github.games647.lambdaattack.logging.LogHandler;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainGui {

    private final JFrame frame = new JFrame("LordSapphire v0.2 | Лорд Сапфир");

    private final LambdaAttack botManager;

    public MainGui(LambdaAttack botManager) {
        this.botManager = botManager;

        this.frame.setResizable(true);
		this.frame.setPreferredSize(new Dimension(600, 395));
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setLayout(new GridLayout(2, 0, 0, 0));

        setLookAndFeel();

        JPanel topPanel = setTopPane();
        JScrollPane buttonPane = setButtonPane();

        this.frame.add(topPanel, BorderLayout.PAGE_START);
        this.frame.add(buttonPane, BorderLayout.CENTER);
        this.frame.pack();
        this.frame.setVisible(true);

        LambdaAttack.getLogger().info("Запуск программы...");
    }

    private JPanel setTopPane() {
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("IP: "), BorderLayout.WEST);
        JTextField hostInput = new JTextField("127.0.0.1");
        topPanel.add(hostInput, BorderLayout.WEST);

        topPanel.add(new JLabel("Порт: "), BorderLayout.WEST);
        JTextField portInput = new JTextField("25565");
        topPanel.add(portInput, BorderLayout.WEST);

        JComboBox<String> versionBox = new JComboBox<>();
        Arrays.stream(GameVersion.values())
                .sorted(Comparator.reverseOrder())
                .map(GameVersion::getVersion)
                .forEach(versionBox::addItem);

        topPanel.add(versionBox, BorderLayout.WEST);

        topPanel.add(new JLabel("Задержка между заходами (мс): "), BorderLayout.WEST);
        JSpinner delay = new JSpinner();
        delay.setValue(1000);
        topPanel.add(delay, BorderLayout.WEST);

        topPanel.add(new JLabel("Кол-во ботов: "), BorderLayout.WEST);
        JSpinner amount = new JSpinner();
        amount.setValue(20);
        topPanel.add(amount, BorderLayout.WEST);

        topPanel.add(new JLabel("Имена ботов: "), BorderLayout.WEST);
        JTextField nameFormat = new JTextField("Bot-%d");
        topPanel.add(nameFormat, BorderLayout.WEST);

        JButton startButton = new JButton("Начать");
        JButton stopButton = new JButton("Остановить");
        topPanel.add(startButton, BorderLayout.WEST);
        topPanel.add(stopButton, BorderLayout.WEST);

        JButton loadNames = new JButton("Загрузить ники");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("", "txt"));
        loadNames.addActionListener(new LoadNamesListener(botManager, frame, fileChooser));

        topPanel.add(loadNames, BorderLayout.WEST);

        JButton loadProxies = new JButton("Загрузить прокси");

        loadProxies.addActionListener(new LoadProxiesListener(botManager, frame, fileChooser));

        topPanel.add(loadProxies, BorderLayout.WEST);
        
        topPanel.add(new JLabel("Время между сообщениями (мс): "), BorderLayout.WEST);
        JSpinner msgDelay = new JSpinner();
        msgDelay.setValue(8000);
        topPanel.add(msgDelay, BorderLayout.WEST);
        
        topPanel.add(new JLabel("Сообщение: "), BorderLayout.SOUTH);
        JTextField msg = new JTextField("НОВЫЙ ВАНИЛЬНЫЙ ПРОЕКТ! СКОРЕЕ ЗАХОДИ, IP: VANILLA-MC.XYZ | ВЕРСИЯ 1.15.2");
        topPanel.add(msg, BorderLayout.SOUTH);

        topPanel.add(new JLabel("Автоматическая регистрация: "), BorderLayout.WEST);
        JCheckBox autoRegister = new JCheckBox();
        topPanel.add(autoRegister, BorderLayout.WEST);

        topPanel.add(new JLabel("Сообщение регистрации: "), BorderLayout.SOUTH);
        JTextField regmes = new JTextField("/reg LordSapphire LordSapphire");
        topPanel.add(regmes, BorderLayout.SOUTH);

        topPanel.add(new JLabel("Сообщение авторизации: "), BorderLayout.SOUTH);
        JTextField logmes = new JTextField("/login LordSapphire");
        topPanel.add(logmes, BorderLayout.SOUTH);

        startButton.addActionListener((action) -> {
            // collect the options on the gui thread
            // for thread-safety
            Options options = new Options(
                    hostInput.getText(),
                    Integer.parseInt(portInput.getText()),
                    (int) amount.getValue(),
                    (int) delay.getValue(),
                    nameFormat.getText(),
                    GameVersion.findByName((String) versionBox.getSelectedItem()),
                    autoRegister.isSelected(),
                    (int) msgDelay.getValue(),
                    msg.getText(),
                    regmes.getText(),
                    logmes.getText());

            botManager.getThreadPool().submit(() -> {
                try {
                    botManager.start(options);
                } catch (Exception ex) {
                    LambdaAttack.getLogger().log(Level.INFO, ex.getMessage(), ex);
                }
            });
        });

        stopButton.addActionListener(action -> botManager.stop());
        return topPanel;
    }
    //private JPanel setTopPane() {

    private JScrollPane setButtonPane() throws SecurityException {
        JScrollPane buttonPane = new JScrollPane();
        buttonPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        buttonPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JTextArea logArea = new JTextArea(15, 1);
        buttonPane.getViewport().setView(logArea);

        LambdaAttack.getLogger().addHandler(new LogHandler(logArea));

        return buttonPane;
    }

    private void setLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LambdaAttack.getLogger().log(Level.SEVERE, null, ex);
        }
    }
}
