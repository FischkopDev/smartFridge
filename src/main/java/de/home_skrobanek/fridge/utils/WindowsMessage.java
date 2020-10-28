package de.home_skrobanek.fridge.utils;
/**
 * @author
 * 	Timo Skrobanek
 *
 * @date
 * 	30.12.18
 */

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;

public class WindowsMessage {

    public static void displayInformation(String title, String msg) throws AWTException, MalformedURLException {
        // Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        // If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        // Alternative (if the icon is on the classpath):
        // Image image =
        // Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        // Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        // Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage(title, msg, MessageType.INFO);
    }

    public static void displayWarning(String title, String msg) throws AWTException, MalformedURLException {
        // Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        // If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        // Alternative (if the icon is on the classpath):
        // Image image =
        // Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        // Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        // Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage(title, msg, MessageType.WARNING);
    }

    public static void displayError(String title, String msg) throws AWTException, MalformedURLException {
        // Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        // If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        // Alternative (if the icon is on the classpath):
        // Image image =
        // Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        // Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        // Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage(title, msg, MessageType.ERROR);
    }

    public static void displayCustomMessage(String title, String msg, String tooltip, String trayIconString,
                                            String imageUrl, MessageType type) throws AWTException, MalformedURLException {
        // Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        // If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage(imageUrl);
        // Alternative (if the icon is on the classpath):
        // Image image =
        // Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, trayIconString);
        // Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        // Set tooltip text for the tray icon
        trayIcon.setToolTip(tooltip);
        tray.add(trayIcon);

        trayIcon.displayMessage(title, msg, type);
    }

}
