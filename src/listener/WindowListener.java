package listener;

import java.awt.event.*;
import javax.swing.*;

import static generateSQL.GUI.*;
import generateSQL.GUI;

import static listener.DDLButtonListener.saveFile;

import static screen.TableScreen.jfDT;
import static screen.RelationScreen.jfDR;

/**
 * Checks the window status and logs the information.
 * Uppon closing the window, windowClosing method checks for saved data
 */
public class WindowListener implements WindowListener {

    /**
     * Logs the info of when the window activates
     * 
     * @param we Checks for the window event
     */
    public void windowActivated(WindowEvent we) {
        System.out.println("Window activated");
    }

    /**
     * Logs the info of when the window is closed
     * 
     * @param we Checks for the window event
     */
    public void windowClosed(WindowEvent we) {
        System.out.println("Window closed");
    }

    /**
     * Logs the info of when the window is deactivated
     * 
     * @param we Checks for the window event
     */
    public void windowDeactivated(WindowEvent we) {
        System.out.println("Window deactivated");
    }

    /**
     * Logs the info of when the window deinconifiez
     * 
     * @param we Checks for the window event
     */
    public void windowDeiconified(WindowEvent we) {
        System.out.println("Window deinconified");
    }

    /**
     * Logs the info of when the window iconifiez
     * 
     * @param we Checks for the window event
     */
    public void windowIconified(WindowEvent we) {
        System.out.println("Window iconified");
    }

    /**
     * Logs the info of when the window opens
     * 
     * @param we Checks for the window event
     */
    public void windowOpened(WindowEvent we) {
        System.out.println("Window open");
    }

    /**
     * Upon closing the window the method checks if the data is saved.
     * If the data is saved, the window will close. If the data is not saved the
     * method will
     * inform the user that the data is not saved, and will provide the user with a
     * choice
     * asking if the user would like to save the data. If the user chooses YES, the
     * data will
     * be saved and the window will close. If the user chooses CANCEL or CLOSE, the
     * data will
     * not be saved and the window will close.
     * 
     * @param we Checks for the window event
     */
    public void windowClosing(WindowEvent we) {
        if (!GUI.isDataSaved()) {
            int answer = JOptionPane.showOptionDialog(null,
                    "You currently have unsaved data. Would you like to save?",
                    "Are you sure?",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
            if (answer == JOptionPane.YES_OPTION) {
                if (saveFile == null) {
                    saveAs();
                }
                writeSave();
            }
            if ((answer == JOptionPane.CANCEL_OPTION) || (answer == JOptionPane.CLOSED_OPTION)) {
                if (we.getSource() == jfDT) {
                    jfDT.setVisible(true);
                }
                if (we.getSource() == jfDR) {
                    jfDR.setVisible(true);
                }
                return;
            }
        }
        System.exit(0);
    }

}