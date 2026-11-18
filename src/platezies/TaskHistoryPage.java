package platezies;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.TitledBorder;

public class TaskHistoryPage extends JFrame {
    private final Runner runner;
    private JList<String> historyList;
    private DefaultListModel<String> historyModel;
    private JTextField searchField;
    private List<String[]> allTasks;
    private Timer autoRefreshTimer;

    private static final Font MAIN_FONT = new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 36);
    private static final Font SUB_FONT = new Font("Tw Cen MT Condensed", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Impact", Font.PLAIN, 14);
    private static final Color TEAL_COLOR = new Color(0, 128, 128);
    private static final Color LIGHT_TEAL = new Color(0, 150, 150);
    private static final Color TEXT_COLOR = Color.BLACK;

    public TaskHistoryPage(Runner runner) {
        this.runner = runner;
        String currentRunner = runner.getUsername();
        setTitle("Task History");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // 加载所有任务
        allTasks = new ArrayList<>();
        historyModel = new DefaultListModel<>();
        
        setupUI();
        loadTasksFromFile();
        
        // 设置文件监听
        setupFileMonitoring();
        
        // 启动自动刷新
        startAutoRefresh();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        // 顶部搜索面板
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        // 任务历史列表
        historyList = new JList<>(historyModel);
        historyList.setFont(SUB_FONT);
        historyList.setForeground(TEXT_COLOR);
        historyList.setBackground(Color.WHITE);
        historyList.setCellRenderer(new TaskHistoryRenderer());

        JScrollPane listScrollPane = new JScrollPane(historyList);
        listScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(TEAL_COLOR),
            "Task History",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            MAIN_FONT,
            TEAL_COLOR
        ));

        add(listScrollPane, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel searchLabel = new JLabel("Search by ID or Date (dd/MM/yy):");
        searchLabel.setFont(SUB_FONT);
        searchLabel.setForeground(TEXT_COLOR);

        searchField = new JTextField(20);
        searchField.setFont(SUB_FONT);
        searchField.setForeground(TEXT_COLOR);

        JButton searchButton = createStyledButton("Search", TEAL_COLOR);
        searchButton.addActionListener(e -> filterTasksBySearch());

        JButton backButton = createStyledButton("Back to Main", new Color(170, 170, 170));
        backButton.addActionListener(e -> dispose());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(backButton);

        return searchPanel;
    }

    private void setupFileMonitoring() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(".");
            dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            // 启动监听线程
            new Thread(() -> {
                while (true) {
                    try {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            String fileName = event.context().toString();
                            if (fileName.equals("platezies_txtfiles/tasks.txt")) {
                                SwingUtilities.invokeLater(this::refreshData);
                            }
                        }
                        key.reset();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startAutoRefresh() {
        if (autoRefreshTimer != null) {
            autoRefreshTimer.stop();
        }
        autoRefreshTimer = new Timer(2000, e -> refreshData());
        autoRefreshTimer.start();
    }

    public void refreshData() {
        String searchText = searchField.getText();
        loadTasksFromFile();
        if (!searchText.isEmpty()) {
            filterTasksBySearch();
        }
    }

    private void loadTasksFromFile() {
        allTasks.clear();
        historyModel.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/tasks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length >= 5 && data[3].equalsIgnoreCase("completed")) {
                    allTasks.add(data);
                    String statusWithTime = String.format("%s at %s", data[3], formatDate(data[2]));
                    historyModel.addElement(String.format("%s | %s | %s | %s | %s",
                        data[0], data[1], data[2], data[4], statusWithTime));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading tasks: " + e.getMessage());
        }
    }

    private void filterTasksBySearch() {
        String searchText = searchField.getText().toLowerCase();
        historyModel.clear();
        
        for (String[] task : allTasks) {
            String taskDate = formatDate(task[2]).toLowerCase();
            if (task[0].toLowerCase().contains(searchText) || taskDate.contains(searchText)) {
                String statusWithTime = String.format("%s at %s", task[3], formatDate(task[2]));
                historyModel.addElement(String.format("%s | %s | %s | %s | %s",
                    task[0], task[1], task[2], task[4], statusWithTime));
            }
        }
    }

    private String formatDate(String dateTime) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            return outputFormat.format(inputFormat.parse(dateTime));
        } catch (Exception e) {
            return dateTime;
        }
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(color);
        button.setForeground(TEXT_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);

        // 添加鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(TEXT_COLOR);
            }
        });

        return button;
    }

    class TaskHistoryRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                    boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
            
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, TEAL_COLOR),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            
            if (isSelected) {
                label.setBackground(LIGHT_TEAL);
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(TEXT_COLOR);
            }
            
            return label;
        }
    }

    @Override
    public void dispose() {
        if (autoRefreshTimer != null) {
            autoRefreshTimer.stop();
        }
        super.dispose();
    }

    
}