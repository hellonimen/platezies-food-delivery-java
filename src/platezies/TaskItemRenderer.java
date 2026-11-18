package platezies;

import javax.swing.*;
import java.awt.*;

public class TaskItemRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                 boolean isSelected, boolean cellHasFocus) {
        if (value instanceof TaskManagementPage.TaskItem) {
            TaskManagementPage.TaskItem task = (TaskManagementPage.TaskItem) value;
            JLabel label = (JLabel) super.getListCellRendererComponent(
                list, task.toString(), index, isSelected, cellHasFocus);
            
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 128, 128)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            
            if (isSelected) {
                label.setBackground(new Color(0, 150, 150));
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
            
            return label;
        }
        
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}