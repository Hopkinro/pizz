import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    private static final double TAX_RATE = 0.07; // Tax rate of 7%

    // Components
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppings;
    private JTextArea orderReceipt;
    private ButtonGroup crustGroup;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        crustGroup = new ButtonGroup();
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep Dish");

        // Add radio buttons to the group
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);

        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);

        toppings = new JCheckBox[]{
                new JCheckBox("Pepperoni"),
                new JCheckBox("Mushrooms"),
                new JCheckBox("Onions"),
                new JCheckBox("Olives"),
                new JCheckBox("Green Peppers"),
                new JCheckBox("Pineapple")
        };

        // Order Receipt
        orderReceipt = new JTextArea(10, 30);
        orderReceipt.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderReceipt);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton orderButton = new JButton("Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add components to panels
        JPanel crustPanel = createCrustPanel();
        JPanel sizePanel = createSizePanel();
        JPanel toppingsPanel = createToppingsPanel();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 1));
        topPanel.add(crustPanel);
        topPanel.add(sizePanel);
        topPanel.add(toppingsPanel);

        // Add components to the frame
        add(topPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReceipt();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // Create panel for crust selection
    private JPanel createCrustPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Crust Type"));
        panel.setLayout(new GridLayout(1, 3));
        panel.add(thinCrust);
        panel.add(regularCrust);
        panel.add(deepDishCrust);
        return panel;
    }

    // Create panel for size selection
    private JPanel createSizePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Size"));
        panel.add(sizeComboBox);
        return panel;
    }

    // Create panel for toppings selection
    private JPanel createToppingsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Toppings"));
        panel.setLayout(new GridLayout(3, 2));
        for (JCheckBox topping : toppings) {
            panel.add(topping);
        }
        return panel;
    }

    // Generate the order receipt and display in JTextArea
    private void generateReceipt() {
        StringBuilder receipt = new StringBuilder();

        // Crust
        String crustType = "None";
        if (thinCrust.isSelected()) crustType = "Thin Crust";
        if (regularCrust.isSelected()) crustType = "Regular Crust";
        if (deepDishCrust.isSelected()) crustType = "Deep Dish Crust";

        // Size
        String size = (String) sizeComboBox.getSelectedItem();
        double sizeCost = 0.0;
        switch (size) {
            case "Small": sizeCost = 8.00; break;
            case "Medium": sizeCost = 12.00; break;
            case "Large": sizeCost = 16.00; break;
            case "Super": sizeCost = 20.00; break;
        }

        // Toppings
        double toppingsCost = 0.0;
        StringBuilder selectedToppings = new StringBuilder();
        for (JCheckBox topping : toppings) {
            if (topping.isSelected()) {
                selectedToppings.append(topping.getText()).append("\n");
                toppingsCost += 1.00;
            }
        }

        // Calculate totals
        double subtotal = sizeCost + toppingsCost;
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        // Build the receipt
        receipt.append("=========================================\n")
                .append("Type of Crust & Size\t\t\t$").append(sizeCost + toppingsCost).append("\n")
                .append("Ingredient(s):\n").append(selectedToppings.toString())
                .append("\nSub-total:\t\t\t$").append(subtotal).append("\n")
                .append("Tax:\t\t\t\t$").append(tax).append("\n")
                .append("-------------------------------------------------\n")
                .append("Total:\t\t\t\t$").append(total).append("\n")
                .append("=========================================\n");

        // Display receipt
        orderReceipt.setText(receipt.toString());
    }

    // Clear all fields
    private void clearForm() {
        thinCrust.setSelected(false);
        regularCrust.setSelected(false);
        deepDishCrust.setSelected(false);
        sizeComboBox.setSelectedIndex(0);
        for (JCheckBox topping : toppings) {
            topping.setSelected(false);
        }
        orderReceipt.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PizzaGUIFrame frame = new PizzaGUIFrame();
                frame.setVisible(true);
            }
        });
    }
}
