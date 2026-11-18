package platezies;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.border.*;
import java.util.stream.Collectors;
import javax.swing.Timer;

public class RevenueDashboardPage extends JFrame {
    private final Runner runner;
    private static final Font MAIN_FONT = new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 36);
    private static final Font SUB_FONT = new Font("Tw Cen MT Condensed", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Impact", Font.PLAIN, 14);
    private static final Color TEAL_COLOR = new Color(0, 128, 128);
    private static final Color LIGHT_TEAL = new Color(0, 150, 150);
    private static final double DELIVERY_FEE = 5.0;

    private JLabel dailyEarningsLabel;
    private JLabel monthlyEarningsLabel;
    private JLabel yearlyEarningsLabel;
    private JLabel statusLabel;
    private ButtonGroup filterGroup;
    private String currentRunner;
    private List<DeliveryRevenue> revenueHistory;
    private JLabel totalDeliveriesLabel;
    private Timer autoRefreshTimer;
    private double lastAverageRating;
    private Object lastReviewChecksum;

    public RevenueDashboardPage(Runner runner) {
        this.runner = runner;
        String currentRunner = runner.getUsername();
        this.revenueHistory = new ArrayList<>();
        
        setTitle("Revenue Dashboard - " + runner);
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // 设置左右面板
        mainPanel.add(createProfilePanel(), BorderLayout.WEST);
        mainPanel.add(createRevenuePanel(), BorderLayout.CENTER);

        add(mainPanel);
        add(createButtonPanel(), BorderLayout.SOUTH);

        // 初始化数据
        loadRevenueData();
        
        // 启动自动刷新
        startAutoRefresh();
        
        // 添加文件监听
        setupFileMonitoring();

        setLocationRelativeTo(null);
        setVisible(true);
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
                            if (fileName.equals("platezies_txtfiles/delivery_revenue.txt") || 
                                fileName.equals("platezies_txtfiles/runner_status.txt") ||
                                fileName.equals("platezies_txtfiles/runner_reviews.txt")) {
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
    autoRefreshTimer = new Timer(8000, e -> refreshData());
    autoRefreshTimer.start();
}

public void refreshData() {
    loadRevenueData();
    updateRunnerStatus();
    updateDashboardData();

    Component[] components = ((JPanel)getContentPane().getComponent(0)).getComponents();
    JLabel avgRatingLabel = null;
    JTextArea recentReviewsArea = null;
    
    for (Component comp : components) {
        if (comp instanceof JPanel) {
            for (Component innerComp : ((JPanel)comp).getComponents()) {
                if (innerComp instanceof JLabel && 
                    ((JLabel)innerComp).getText().startsWith("Average Rating")) {
                    avgRatingLabel = (JLabel)innerComp;
                }
                if (innerComp instanceof JScrollPane) {
                    Component viewComp = ((JScrollPane)innerComp).getViewport().getView();
                    if (viewComp instanceof JTextArea) {
                        recentReviewsArea = (JTextArea)viewComp;
                    }
                }
            }
        }
    }

    // Load reviews if components are found
    if (avgRatingLabel != null && recentReviewsArea != null) {
        List<ReviewData> runnerReviews = loadRunnerReviewsData();
        
        double avgRating = runnerReviews.isEmpty() ? 0.0 : 
            runnerReviews.stream()
                .mapToInt(review -> review.rating)
                .average()
                .orElse(0.0);

        String recentReviewsText = runnerReviews.isEmpty() ? "No reviews yet." : 
            runnerReviews.stream()
                .sorted((r1, r2) -> r2.date.compareTo(r1.date))
                .limit(3)
                .map(review -> String.format("%d★ - %s", review.rating, review.comment))
                .collect(Collectors.joining("\n"));

        avgRatingLabel.setText(String.format("Average Rating: %.1f★", avgRating));
        recentReviewsArea.setText(recentReviewsText);
    }

    revalidate();
    repaint();
}

private List<ReviewData> loadRunnerReviewsData() {
    List<ReviewData> runnerReviews = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/runner_reviews.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] data = line.split(",");
            if (data.length >= 6 && data[0].equals(currentRunner)) {
                runnerReviews.add(new ReviewData(data));
            }
        }
    } catch (IOException e) {
        System.out.println("No reviews found for runner: " + e.getMessage());
    }
    return runnerReviews;
}
    
    private void updateRunnerStatus() {
        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/runner_status.txt"))) {
            String line;
            String lastStatus = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(currentRunner)) {
                    lastStatus = line;
                }
            }
            if (lastStatus != null) {
                String[] data = lastStatus.split(",");
                boolean isOnline = "online".equals(data[1]);
                updateStatus(isOnline);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(boolean isOnline) {
        if (statusLabel != null) {
            statusLabel.setText("Status: " + (isOnline ? "Active" : "Offline"));
            statusLabel.setForeground(isOnline ? new Color(76, 175, 80) : Color.RED);
        }
    }

private JPanel createProfilePanel() {
    JPanel profilePanel = new JPanel(new BorderLayout(10, 10));
    profilePanel.setPreferredSize(new Dimension(300, 0));
    profilePanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(TEAL_COLOR),
        "Runner Profile",
        TitledBorder.LEFT,
        TitledBorder.TOP,
        MAIN_FONT
    ));
    profilePanel.setBackground(Color.WHITE);

    // Profile信息面板
    JPanel infoPanel = new JPanel(new GridLayout(8, 1, 5, 5));  // 增加一行
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    JLabel nameLabel = new JLabel("Name: " + currentRunner);
    JLabel idLabel = new JLabel("Runner ID: " + currentRunner);
    statusLabel = new JLabel("Status: Checking...");
    totalDeliveriesLabel = new JLabel("Total Deliveries: " + calculateTotalDeliveries());

    // New labels for rating and reviews
    JLabel avgRatingLabel = new JLabel("Average Rating: Calculating...");
    JLabel reviewsTitleLabel = new JLabel("Recent Reviews");  // 新增的标题标签
    reviewsTitleLabel.setFont(SUB_FONT.deriveFont(Font.BOLD, 20f));
    reviewsTitleLabel.setForeground(TEAL_COLOR);

    JTextArea recentReviewsArea = new JTextArea("Recent Reviews Loading...");
    JScrollPane reviewScrollPane = new JScrollPane(recentReviewsArea);
    reviewScrollPane.setPreferredSize(new Dimension(250, 100));

    nameLabel.setFont(SUB_FONT);
    idLabel.setFont(SUB_FONT);
    statusLabel.setFont(SUB_FONT);
    totalDeliveriesLabel.setFont(SUB_FONT);
    avgRatingLabel.setFont(SUB_FONT);
    recentReviewsArea.setFont(SUB_FONT.deriveFont(18f));
    recentReviewsArea.setEditable(false);

    // 初始化时加载评论
    loadRunnerReviews(avgRatingLabel, recentReviewsArea);

    infoPanel.add(nameLabel);
    infoPanel.add(idLabel);
    infoPanel.add(statusLabel);
    infoPanel.add(totalDeliveriesLabel);
    infoPanel.add(avgRatingLabel);
    infoPanel.add(reviewsTitleLabel);  // 添加标题标签
    infoPanel.add(reviewScrollPane);

    profilePanel.add(infoPanel, BorderLayout.CENTER);

    return profilePanel;
}

    private void loadRunnerReviews(JLabel avgRatingLabel, JTextArea recentReviewsArea) {
        List<ReviewData> runnerReviews = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/runner_reviews.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length >= 6 && data[0].equals(currentRunner)) {
                    runnerReviews.add(new ReviewData(data));
                }
            }
        } catch (IOException e) {
            System.out.println("No reviews found for runner.");
        }

        // Calculate average rating
        double avgRating = runnerReviews.isEmpty() ? 0.0 : 
            runnerReviews.stream()
                .mapToInt(review -> review.rating)
                .average()
                .orElse(0.0);

        // Update average rating label
        avgRatingLabel.setText(String.format("Average Rating: %.1f★", avgRating));

        // Generate recent reviews text
        String recentReviewsText = runnerReviews.isEmpty() ? "No reviews yet." : 
            runnerReviews.stream()
                .sorted((r1, r2) -> r2.date.compareTo(r1.date))
                .limit(3)
                .map(review -> String.format("%d★ - %s", review.rating, review.comment))
                .collect(Collectors.joining("\n"));

        recentReviewsArea.setText(recentReviewsText);
    }

    private JPanel createRevenuePanel() {
        JPanel revenuePanel = new JPanel(new BorderLayout(10, 10));
        revenuePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(TEAL_COLOR),
            "Revenue Summary",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            MAIN_FONT
        ));
        revenuePanel.setBackground(Color.WHITE);

        // 收入统计面板
        JPanel summaryPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        dailyEarningsLabel = createEarningsLabel("Today's Earnings: RM 0.00");
        monthlyEarningsLabel = createEarningsLabel("Monthly Earnings: RM 0.00");
        yearlyEarningsLabel = createEarningsLabel("Yearly Earnings: RM 0.00");

        summaryPanel.add(createPeriodPanel("Daily Summary", dailyEarningsLabel));
        summaryPanel.add(createPeriodPanel("Monthly Summary", monthlyEarningsLabel));
        summaryPanel.add(createPeriodPanel("Yearly Summary", yearlyEarningsLabel));

        revenuePanel.add(summaryPanel, BorderLayout.CENTER);

        return revenuePanel;
    }

    private JPanel createPeriodPanel(String title, JLabel contentLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(TEAL_COLOR),
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            SUB_FONT
        ));
        panel.add(contentLabel, BorderLayout.CENTER);
        return panel;
    }

    private JLabel createEarningsLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SUB_FONT);
        label.setForeground(Color.BLACK);
        label.setBorder(new EmptyBorder(10, 20, 10, 20));
        return label;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.WHITE);
        
        JButton backButton = createStyledButton("Back to Main", new Color(170, 170, 170));
        backButton.addActionListener(e -> dispose());
        
        panel.add(backButton);
        return panel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLACK);
            }
        });

        return button;
    }

   
    private void loadRevenueData() {
        revenueHistory.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/delivery_revenue.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length >= 5 && data[0].equals(currentRunner)) {
                    revenueHistory.add(new DeliveryRevenue(data));
                }
            }
        } catch (IOException e) {
            System.out.println("No revenue history found. Starting fresh.");
        }
        calculateEarnings();
    }

    private void calculateEarnings() {
        Date now = new Date();
        String today = new SimpleDateFormat("dd-MM-yyyy").format(now);
        String month = new SimpleDateFormat("MM-yyyy").format(now);
        String year = new SimpleDateFormat("yyyy").format(now);

        double dailyEarnings = 0;
        double monthlyEarnings = 0;
        double yearlyEarnings = 0;

        for (DeliveryRevenue revenue : revenueHistory) {
            if (revenue.type.equals("delivery")) {
                if (revenue.date.startsWith(today)) {
                    dailyEarnings += revenue.amount;
                }
                if (revenue.date.contains(month)) {
                    monthlyEarnings += revenue.amount;
                }
                if (revenue.date.contains(year)) {
                    yearlyEarnings += revenue.amount;
                }
            }
        }

        updateEarningsLabels(dailyEarnings, monthlyEarnings, yearlyEarnings);
    }

    private void updateEarningsLabels(double daily, double monthly, double yearly) {
        dailyEarningsLabel.setText(String.format("Today's Earnings: RM %.2f", daily));
        monthlyEarningsLabel.setText(String.format("Monthly Earnings: RM %.2f", monthly));
        yearlyEarningsLabel.setText(String.format("Yearly Earnings: RM %.2f", yearly));
    }

    private int calculateTotalDeliveries() {
        return (int) revenueHistory.stream()
                .filter(r -> r.type.equals("delivery"))
                .count();
    }

    private void updateDashboardData() {
        if (totalDeliveriesLabel != null) {
            totalDeliveriesLabel.setText("Total Deliveries: " + calculateTotalDeliveries());
        }
        calculateEarnings();
    }

    // Inner classes
    class DeliveryRevenue {
        String runnerId;
        String orderId;
        double amount;
        String date;
        String type;

        public DeliveryRevenue(String[] data) {
            this.runnerId = data[0];
            this.orderId = data[1];
            this.amount = Double.parseDouble(data[2]);
            this.date = data[3];
            this.type = data[4];
        }
    }

    public static class ReviewData {
        String runnerId;
        String orderId;
        String customerId;
        int rating;
        String comment;
        String date;

        public ReviewData(String[] data) {
            if (data.length >= 6) {
                this.runnerId = data[0].trim();
                this.orderId = data[1].trim();
                this.customerId = data[2].trim();
                try {
                    this.rating = Integer.parseInt(data[3].trim());
                } catch (NumberFormatException e) {
                    this.rating = 0;
                    System.err.println("Error parsing rating for order " + this.orderId + ": " + data[3]);
                }
                this.comment = data[4].trim();
                this.date = data[5].trim();
            }
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