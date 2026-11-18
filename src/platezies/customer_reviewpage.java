package platezies;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.Timer;

public class customer_reviewpage extends JFrame {
    // Constants
    private final Runner runner;
    private static final Font MAIN_FONT = new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 36);
    private static final Font SUB_FONT = new Font("Tw Cen MT Condensed", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Impact", Font.PLAIN, 14);
    private static final Color TEAL_COLOR = new Color(0, 128, 128);
    private static final Color LIGHT_TEAL = new Color(0, 150, 150);
    private static final Color TEXT_COLOR = Color.BLACK;

    // Components
    private String currentRunner;
    private JComboBox<String> orderCombo;
    private JPanel ratingPanel;
    private JTextArea commentArea;
    private int selectedRating = 0;
    private List<Review> reviews;
    private JPanel reviewsContainer;
    private JLabel avgRatingLabel;
    private Timer autoRefreshTimer;

    public customer_reviewpage(Runner runner) {
        
        this.runner = runner;
        currentRunner = runner.getUsername();
        this.reviews = new ArrayList<>();
        
        setupFrame();
        setupUI();
        setupAutoRefresh();
        setupFileMonitoring();
        ensureFilesExist();
        loadData();
        
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Customer Reviews - " + currentRunner);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.WHITE);
    }

    private void setupUI() {
        // Main panel with padding
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Submit Review Panel
        JPanel submitPanel = createSubmitPanel();
        mainPanel.add(submitPanel);

        // Reviews Panel
        JPanel reviewsPanel = createReviewsPanel();
        mainPanel.add(reviewsPanel);

        add(mainPanel);
        
        
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    bottomPanel.setBackground(Color.WHITE);
    
    JButton backButton = createStyledButton("Back to Main", new Color(170, 170, 170));
    backButton.setPreferredSize(new Dimension(200, 40));
    backButton.addActionListener(e -> {
        dispose(); // 关闭当前窗口
    });
    
    bottomPanel.add(backButton);
    add(bottomPanel, BorderLayout.SOUTH);

    }

private JPanel createSubmitPanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(TEAL_COLOR),
        "Submit Review",
        TitledBorder.LEFT,
        TitledBorder.TOP,
        SUB_FONT,
        TEAL_COLOR
    ));
    panel.setBackground(Color.WHITE);

    
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBackground(Color.WHITE);
        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        inputPanel.setBackground(Color.WHITE);

        // Order Selection
    JPanel orderPanel = new JPanel(new BorderLayout(5, 5));
    orderPanel.setBackground(Color.WHITE);
    JLabel orderLabel = new JLabel("Select Order");
    orderLabel.setFont(SUB_FONT);
    orderCombo = new JComboBox<>();
    orderCombo.setFont(SUB_FONT);
    orderCombo.setPreferredSize(new Dimension(orderCombo.getPreferredSize().width, 35));
    orderPanel.add(orderLabel, BorderLayout.NORTH);
    orderPanel.add(orderCombo, BorderLayout.CENTER);
    orderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
    contentPanel.add(orderPanel);
    contentPanel.add(Box.createVerticalStrut(10));

        // Rating Stars
    ratingPanel = createRatingPanel();
    ratingPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
    contentPanel.add(ratingPanel);
    contentPanel.add(Box.createVerticalStrut(10));

        // Comment Area
    JPanel commentPanel = new JPanel(new BorderLayout(5, 5));
    commentPanel.setBackground(Color.WHITE);
    JLabel commentLabel = new JLabel("Comment");
    commentLabel.setFont(SUB_FONT);
    commentArea = new JTextArea(3, 30); // 减小行数为3
    commentArea.setFont(SUB_FONT);
    commentArea.setLineWrap(true);
    commentArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(commentArea);
    commentPanel.add(commentLabel, BorderLayout.NORTH);
    commentPanel.add(scrollPane, BorderLayout.CENTER);
    commentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); // 限制最大高度
    contentPanel.add(commentPanel);
    contentPanel.add(Box.createVerticalStrut(20));

        // Submit Button
    JButton submitButton = createStyledButton("Submit Review", TEAL_COLOR);
    submitButton.setMaximumSize(new Dimension(200, 40));
    submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    submitButton.addActionListener(e -> submitReview());
    contentPanel.add(submitButton);
    contentPanel.add(Box.createVerticalStrut(10));

    panel.add(contentPanel, BorderLayout.CENTER);
    return panel;
}

private JPanel createRatingPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.setBackground(Color.WHITE);
    
    JLabel ratingLabel = new JLabel("Rating");
    ratingLabel.setFont(SUB_FONT);
    panel.add(ratingLabel);
    panel.add(Box.createHorizontalStrut(10));

    // 创建一个专门用于存储星星的容器
    JPanel starPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    starPanel.setBackground(Color.WHITE);

    for (int i = 1; i <= 5; i++) {
        final int rating = i;
        JLabel star = new JLabel("☆");
        star.setName("star" + i);  // 为每个星星添加名称
        star.setFont(new Font("Dialog", Font.PLAIN, 24));
        star.setCursor(new Cursor(Cursor.HAND_CURSOR));
        star.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRating = rating;
                updateStars();
            }
        });
        starPanel.add(star);
    }
    
    panel.add(starPanel);
    return panel;
}

    private JPanel createReviewsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(TEAL_COLOR),
            "Reviews",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            SUB_FONT,
            TEAL_COLOR
        ));
        panel.setBackground(Color.WHITE);

        // Average Rating
        avgRatingLabel = new JLabel("Average Rating: 0.0 ★");
        avgRatingLabel.setFont(SUB_FONT);
        panel.add(avgRatingLabel, BorderLayout.NORTH);

        // Reviews Container
        reviewsContainer = new JPanel();
        reviewsContainer.setLayout(new BoxLayout(reviewsContainer, BoxLayout.Y_AXIS));
        reviewsContainer.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(reviewsContainer);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

private void updateStars() {
    // 确保使用正确的方法获取星星组件
    Component[] components = ratingPanel.getComponents();
    for (Component comp : components) {
        if (comp instanceof JPanel) {  // 找到星星面板
            Component[] stars = ((JPanel)comp).getComponents();
            for (int i = 0; i < stars.length; i++) {
                if (stars[i] instanceof JLabel) {
                    JLabel star = (JLabel) stars[i];
                    star.setText(i < selectedRating ? "★" : "☆");
                }
            }
            break;
        }
    }
}

    private void setupAutoRefresh() {
        autoRefreshTimer = new Timer(15000, e -> loadData());
        autoRefreshTimer.start();
    }

    private void setupFileMonitoring() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(".");
            dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            new Thread(() -> {
                while (true) {
                    try {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            String fileName = event.context().toString();
                            if (fileName.equals("platezies_txtfiles/runner_reviews.txt") || 
                                fileName.equals("platezies_txtfiles/customer_ratings.txt")) {
                                SwingUtilities.invokeLater(this::loadData);
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

    private void ensureFilesExist() {
        ensureFileExists("platezies_txtfiles/runner_reviews.txt");
        ensureFileExists("platezies_txtfiles/customer_ratings.txt");
    }

    private void ensureFileExists(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Error creating file " + filename + ": " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadData() {
        loadCompletedOrders();
        loadReviews();
        updateReviewsDisplay();
    }

    private void loadCompletedOrders() {
        orderCombo.removeAllItems();
        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/tasks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5 && 
                    data[3].equalsIgnoreCase("completed") && 
                    data[4].equals(currentRunner)) {
                    orderCombo.addItem(data[0]);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading orders: " + e.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadReviews() {
        reviews.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("platezies_txtfiles/runner_reviews.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    String[] data = line.split(",");
                    if (data.length >= 6 && data[0].equals(currentRunner)) {
                        reviews.add(new Review(data));
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing review line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("No existing reviews found.");
        }

        // Update average rating
        double avgRating = reviews.stream()
            .mapToInt(r -> r.rating)
            .average()
            .orElse(0.0);
        avgRatingLabel.setText(String.format("Average Rating: %.1f ★", avgRating));
    }

    private void updateReviewsDisplay() {
        reviewsContainer.removeAll();
        
        // Sort reviews by date (newest first)
        reviews.sort((r1, r2) -> r2.date.compareTo(r1.date));

        for (Review review : reviews) {
            JPanel reviewPanel = createReviewPanel(review);
            reviewsContainer.add(reviewPanel);
            reviewsContainer.add(Box.createVerticalStrut(10));
        }

        reviewsContainer.revalidate();
        reviewsContainer.repaint();
    }

    private JPanel createReviewPanel(Review review) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEAL_COLOR),
            new EmptyBorder(10, 10, 10, 10)
        ));

        // Header (Order ID and Rating)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JLabel orderLabel = new JLabel("Order #" + review.orderId);
        orderLabel.setFont(SUB_FONT);
        
    StringBuilder stars = new StringBuilder();
    for (int i = 0; i < review.rating; i++) {
        stars.append("★");
            
                 
        }
    JLabel ratingLabel = new JLabel(stars.toString());
    ratingLabel.setFont(SUB_FONT);
    ratingLabel.setForeground(Color.BLACK); //

        headerPanel.add(orderLabel, BorderLayout.WEST);
        headerPanel.add(ratingLabel, BorderLayout.EAST);

        // Comment
        JTextArea commentLabel = new JTextArea(review.comment);
        commentLabel.setFont(SUB_FONT);
        commentLabel.setLineWrap(true);
        commentLabel.setWrapStyleWord(true);
        commentLabel.setEditable(false);
        commentLabel.setBackground(Color.WHITE);

        // Date
        JLabel dateLabel = new JLabel(review.date);
        dateLabel.setFont(SUB_FONT.deriveFont(Font.PLAIN, 12));
        dateLabel.setForeground(Color.GRAY);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(commentLabel, BorderLayout.CENTER);
        panel.add(dateLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void submitReview() {
        String orderId = (String) orderCombo.getSelectedItem();
        if (orderId == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select an order",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedRating == 0) {
            JOptionPane.showMessageDialog(this, 
                "Please select a rating",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String comment = commentArea.getText().trim();
        if (comment.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a comment",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Save to customer_ratings.txt
        try (FileWriter writer = new FileWriter("platezies_txtfiles/customer_ratings.txt", true)) {
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            writer.write(String.format("%s,%s,%d,%s,%s\n",
                currentRunner,
                orderId,
                selectedRating,
                comment.replace(",", ";"), // Avoid CSV format issues
                date
            ));
                // Reset form
                orderCombo.setSelectedIndex(0);
                selectedRating = 0;
                updateStars();
                commentArea.setText("");

                JOptionPane.showMessageDialog(this, 
                    "Review submitted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh data
                loadData();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error saving review: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        // Inner class for Review data
        private static class Review {
            String runnerId;
            String orderId;
            String customerId;
            int rating;
            String comment;
            String date;

            public Review(String[] data) {
                this.runnerId = data[0].trim();
                this.orderId = data[1].trim();
                this.customerId = data[2].trim();
                this.rating = Integer.parseInt(data[3].trim());
                this.comment = data[4].trim();
                this.date = data[5].trim();
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