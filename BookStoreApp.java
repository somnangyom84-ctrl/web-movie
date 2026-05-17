import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookStoreApp extends JFrame {
    // GUI Components
    private JTextField txtCode, txtTitle, txtPubYear, txtPrice;
    private JButton btnAdd, btnRemove, btnClose;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotalPrice;

    public BookStoreApp() {
        // Setup Window
        setTitle("Book Store");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- TOP PANEL: Input Form ---
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Code :"));
        txtCode = new JTextField();
        inputPanel.add(txtCode);

        inputPanel.add(new JLabel("Title :"));
        txtTitle = new JTextField();
        inputPanel.add(txtTitle);

        inputPanel.add(new JLabel("Pub Year :"));
        txtPubYear = new JTextField();
        inputPanel.add(txtPubYear);

        inputPanel.add(new JLabel("Price :"));
        txtPrice = new JTextField();
        inputPanel.add(txtPrice);

        // --- MIDDLE PANEL: Buttons and Table ---
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAdd = new JButton("Add");
        btnRemove = new JButton("Remove");
        btnClose = new JButton("Close");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnClose);
        centerPanel.add(buttonPanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Code", "Title", "Pub Year", "Price"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM PANEL: Total Price ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        lblTotalPrice = new JLabel("Total Price: 0.00");
        lblTotalPrice.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(lblTotalPrice);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- BUTTON ACTIONS ---

        // Add Button Action
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = txtCode.getText().trim();
                String title = txtTitle.getText().trim();
                String pubYear = txtPubYear.getText().trim();
                String price = txtPrice.getText().trim();

                // Validation checking
                if (code.isEmpty() || title.isEmpty() || pubYear.isEmpty() || price.isEmpty()) {
                    JOptionPane.showMessageDialog(BookStoreApp.this, "Please fill all fields.");
                    return;
                }

                // Add row to table
                tableModel.addRow(new Object[]{code, title, pubYear, price});
                
                // Clear inputs
                txtCode.setText("");
                txtTitle.setText("");
                txtPubYear.setText("");
                txtPrice.setText("");
                
                updateTotal();
            }
        });

        // Remove Button Action
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                    updateTotal();
                } else {
                    JOptionPane.showMessageDialog(BookStoreApp.this, "Select a row to remove.");
                }
            }
        });

        // Close Button Action
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    // Helper method to calculate total price
    private void updateTotal() {
        double total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            try {
                String priceStr = tableModel.getValueAt(i, 3).toString();
                total += Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                // Ignore if price isn't a valid number
            }
        }
        lblTotalPrice.setText("Total Price: " + String.format("%.2f", total));
    }

    public static void main(String[] args) {
        // Run GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BookStoreApp().setVisible(true);
            }
        });
    }
}