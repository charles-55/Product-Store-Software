package myStore;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The StoreView Class.
 * The GUI
 *
 * @author Dhruv Sannd - 101156588
 * @author Osamudiamen Nwoko - 101152520
 *
 * @version 4.0
 */

public class StoreView {

    private final StoreManager manager = new StoreManager();
    private final int cartID = manager.getCartID();
    private final JFrame homeFrame = new JFrame();
    private final JFrame cartFrame = new JFrame();
    private final JFrame checkoutFrame = new JFrame();
    private final JPanel menuPanel = new JPanel();
    private final JButton homeButton = getHomeButton();
    private final JButton cartButton = getCartButton();
    private final JButton checkoutButton = getCheckoutButton();
    private final JButton quitButton = getQuitButton();

    /**
     * The default constructor
     */
    private StoreView() {
        initialize();
        homeFrame();
        homeFrame.setVisible(true);
    }

    /**
     * Design of the menu panel
     */
    private void initialize() {
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(new JLabel("Menu"));
        menuPanel.add(homeButton);
        menuPanel.add(cartButton);
        menuPanel.add(checkoutButton);
        menuPanel.add(quitButton, BorderLayout.PAGE_END);
    }

    /**
     * The Home Frame
     */
    private void homeFrame() {
        homeFrame.setTitle("THE PRODUCT STORE");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel subHeader = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel bodyPanel = new JPanel(new BorderLayout());
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel subInventoryPanel = new JPanel();
        JPanel inventoryPanel = new JPanel(new BorderLayout());

        p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
        p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));
        subInventoryPanel.setLayout(new BoxLayout(subInventoryPanel, BoxLayout.PAGE_AXIS));

        for(Product p : manager.getInventoryProducts()) {
            subInventoryPanel.add(productDisplay(p, false, 0));
        }

        JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inventoryPanel.add(subInventoryPanel, BorderLayout.LINE_START);
        inventoryPanel.add(scrollPane, BorderLayout.LINE_END);

        subHeader.add(new JLabel("Welcome to the Product Store! (ID: " + cartID + ")."), BorderLayout.PAGE_START);
        subHeader.add(new JLabel("Re-click the current menu page to refresh when needed."), BorderLayout.PAGE_END);
        headerPanel.add(subHeader);
        bodyPanel.add(inventoryPanel, BorderLayout.LINE_START);
        bodyPanel.add(menuPanel, BorderLayout.LINE_END);

        mainPanel.add(headerPanel, BorderLayout.PAGE_START);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        homeFrame.add(mainPanel, BorderLayout.LINE_START);

        homeFrame.pack();
        homeFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        homeFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(homeFrame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    manager.returnToInventory();
                    manager.resetCart();
                    homeFrame.setVisible(false);
                    cartFrame.setVisible(false);
                    checkoutFrame.setVisible(false);
                    homeFrame.dispose();
                    cartFrame.dispose();
                    checkoutFrame.dispose();
                }
            }
        });
    }

    /**
     * The Cart Frame
     */
    private void cartFrame() {
        cartFrame.setTitle("THE PRODUCT STORE");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel bodyPanel = new JPanel(new BorderLayout());
        JPanel subCartPanel = new JPanel();
        JPanel cartPanel = new JPanel(new BorderLayout());

        subCartPanel.setLayout(new BoxLayout(subCartPanel,BoxLayout.PAGE_AXIS));

        if(manager.getCart().size() == 0) {
            subCartPanel.add(new JLabel("Cart is empty."));
        } else {
            for(int[] c : manager.getCart()) {
                Product p = manager.getInventory().getProduct(c[0]);
                subCartPanel.add(productDisplay(p, true, c[1]));
            }
        }

        JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cartPanel.add(subCartPanel, BorderLayout.LINE_START);
        cartPanel.add(scrollPane, BorderLayout.LINE_END);

        headerPanel.add(new JLabel("Welcome to the Product Store! (ID: " + cartID + ")"));
        bodyPanel.add(cartPanel, BorderLayout.LINE_START);
        bodyPanel.add(menuPanel, BorderLayout.LINE_END);

        mainPanel.add(headerPanel, BorderLayout.PAGE_START);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        cartFrame.add(mainPanel);

        cartFrame.pack();
        cartFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        cartFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(homeFrame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    manager.returnToInventory();
                    manager.resetCart();
                    homeFrame.setVisible(false);
                    cartFrame.setVisible(false);
                    checkoutFrame.setVisible(false);
                    homeFrame.dispose();
                    cartFrame.dispose();
                    checkoutFrame.dispose();
                }
            }
        });
    }

    /**
     * The Checkout Frame
     */
    private void checkoutFrame() {
        checkoutFrame.setTitle("THE PRODUCT STORE");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel receiptPanel = new JPanel(new BorderLayout());
        JPanel subReceiptPanel = new JPanel();
        JPanel productPanel = new JPanel();
        JPanel quantityPanel = new JPanel();
        JPanel pricePanel = new JPanel();
        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel bodyPanel = new JPanel(new BorderLayout());
        JPanel subBodyPanel = new JPanel();

        subBodyPanel.setLayout(new BoxLayout(subBodyPanel, BoxLayout.PAGE_AXIS));
        subReceiptPanel.setLayout(new BoxLayout(subReceiptPanel,BoxLayout.LINE_AXIS));
        productPanel.setLayout(new BoxLayout(productPanel,BoxLayout.PAGE_AXIS));
        quantityPanel.setLayout(new BoxLayout(quantityPanel,BoxLayout.PAGE_AXIS));
        pricePanel.setLayout(new BoxLayout(pricePanel,BoxLayout.PAGE_AXIS));

        productPanel.add(new JLabel( "Product | "));
        quantityPanel.add(new JLabel("Quantity | "));
        pricePanel.add(new JLabel("Unit Price"));

        for(int[] c : manager.getCart()) {
            Product p = manager.getInventory().getProduct(c[0]);
            productPanel.add(new JLabel(p.getName() + " | "));
            quantityPanel.add(new JLabel(c[1] + " | "));
            pricePanel.add(new JLabel("$" + p.getPrice()));
        }

        subReceiptPanel.add(productPanel);
        subReceiptPanel.add(quantityPanel);
        subReceiptPanel.add(pricePanel);

        JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        receiptPanel.add(subReceiptPanel, BorderLayout.LINE_START);
        receiptPanel.add(scrollPane, BorderLayout.LINE_END);

        headerPanel.add(new JLabel("Checkout (CartID: " + cartID + ")"));
        subBodyPanel.add(receiptPanel);
        subBodyPanel.add(new JLabel("Total : $" + manager.processTransaction()));
        bodyPanel.add(subBodyPanel, BorderLayout.LINE_START);
        bodyPanel.add(menuPanel, BorderLayout.LINE_END);

        mainPanel.add(headerPanel, BorderLayout.PAGE_START);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        checkoutFrame.add(mainPanel);

        checkoutFrame.pack();
        checkoutFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        checkoutFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(homeFrame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    manager.returnToInventory();
                    manager.resetCart();
                    homeFrame.setVisible(false);
                    cartFrame.setVisible(false);
                    checkoutFrame.setVisible(false);
                    homeFrame.dispose();
                    cartFrame.dispose();
                    checkoutFrame.dispose();
                }
            }
        });
    }

    /**
     * Returns the Home button with its features
     * @return  JButton: The Home button
     */
    private JButton getHomeButton() {
        JButton button = new JButton("Home");

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                homeFrame();
                checkoutFrame.setVisible(false);
                checkoutFrame.dispose();
                cartFrame.setVisible(false);
                cartFrame.dispose();
                homeFrame.setVisible(true);
            }
        });
        return button;
    }

    /**
     * Returns the View Cart button with its features
     * @return  JButton: The View Cart button
     */
    private JButton getCartButton() {
        JButton button = new JButton("View Cart");

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                cartFrame();
                homeFrame.setVisible(false);
                homeFrame.dispose();
                checkoutFrame.setVisible(false);
                checkoutFrame.dispose();
                cartFrame.setVisible(true);
            }
        });
        return button;
    }

    /**
     * Returns the Checkout button with its features
     * @return  JButton: The Checkout button
     */
    private JButton getCheckoutButton() {
        JButton button = new JButton("Checkout");

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                checkoutFrame();
                homeFrame.setVisible(false);
                homeFrame.dispose();
                cartFrame.setVisible(false);
                cartFrame.dispose();
                checkoutFrame.setVisible(true);
            }
        });
        return button;
    }

    /**
     * Returns the Quit button with its features
     * @return  JButton: The Quit button
     */
    private JButton getQuitButton() {
        JButton button = new JButton("Quit");

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (JOptionPane.showConfirmDialog(homeFrame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    manager.returnToInventory();
                    manager.resetCart();
                    homeFrame.setVisible(false);
                    cartFrame.setVisible(false);
                    checkoutFrame.setVisible(false);
                    homeFrame.dispose();
                    cartFrame.dispose();
                    checkoutFrame.dispose();
                }
            }
        });
        return button;
    }

    /**
     * Returns the Add button with its features
     * @return  JButton: The Add button
     */
    private JButton getAddButton(int id) {
        JButton button = new JButton("Add");

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int amount = getAmount();
                manager.addToCart(id, amount);
            }
        });
        return button;
    }

    /**
     * Returns the Remove button with its features
     * @return  JButton: The Remove button
     */
    private JButton getRemoveButton(int id) {
        JButton button = new JButton("Remove");

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int amount = getAmount();
                manager.removeFromCart(id, amount);
            }
        });
        return button;
    }

    /**
     * Opens a dialog box to get the amount of a product to add or remove from cart
     * @return  int: amount to add or remove
     */
    private int getAmount() {
        String s = JOptionPane.showInputDialog("Please insert an amount:");

        int amount = 0;
        try {
            amount = Integer.parseInt(s);
        }
        catch (NumberFormatException n) {
            JOptionPane.showMessageDialog(homeFrame, "Invalid Input");
        }
        return amount;
    }

    /**
     * Designs how the products are seen in any frame
     * @param p  Product: Product to d
     * @param b  boolean: Decides the design based on the frame (Home or Cart)
     * @param q  int: Quantity of product in cart
     * @return  JPanel: Design of how the product is seen
     */
    private JPanel productDisplay(Product p, boolean b, int q) {
        JPanel display = new JPanel(new BorderLayout());
        JPanel header = new JPanel();
        JPanel imagePanel = new JPanel(new BorderLayout());
        JPanel footer = new JPanel(new BorderLayout());
        BufferedImage image;
        JLabel imageLabel = new JLabel();

        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        int id = p.getID();

        String fileLocation = "\\" + id + ".png";
        imagePanel.setPreferredSize(new Dimension(50, 100));

        try {
            image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(StoreView.class.getResource(fileLocation));
            ImageIcon imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
        } catch(IllegalArgumentException | IOException e) {
            imageLabel.setText(p.getName() + " image");
        } finally {
            imagePanel.add(imageLabel, BorderLayout.CENTER);
        }

        if(b) {
            header.add(new JLabel(q + " | "), BorderLayout.LINE_START);
        } else {
            header.add(new JLabel(manager.checkStock(p) + " | "), BorderLayout.LINE_START);
        }
        header.add(new JLabel(p.getName() + " | "), BorderLayout.CENTER);
        header.add(new JLabel("$" + p.getPrice()));
        if(b) {
            footer.add(getRemoveButton(p.getID()));
        } else {
            footer.add(getAddButton(p.getID()));
        }

        display.add(header, BorderLayout.PAGE_START);
        display.add(imagePanel, BorderLayout.CENTER);
        display.add(footer, BorderLayout.PAGE_END);

        return display;
    }

    public static void main(String[] args) {
        new StoreView();
    }
}