package listener;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;

import parser.ConvertFileParser;
import parser.FileParserFactory;
import parser.SaveFileParser;
import parser.XmlFileParser;

import static generateSQL.GUI.*;
import generateSQL.GUI;

import static utils.Constants.*;

import static listener.DDLButtonListener.saveFile;

import static screen.RelationScreen.*;
import static screen.TableScreen.*;
import static screen.RelationScreen.jfDR;

public class MenuListener implements ActionListener {

    public void actionPerformed(ActionEvent ae) {
        int returnVal;
        if ((ae.getSource() == jmiDTOpen) || (ae.getSource() == jmiDROpen)) {
            if (!GUI.isDataSaved()) {
                int answer = JOptionPane.showConfirmDialog(null, "You currently have unsaved data. Continue?",
                        "Are you sure?", JOptionPane.YES_NO_OPTION);
                if (answer != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            jfc.addChoosableFileFilter(eff);
            returnVal = jfc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                readFile();
            } else {
                return;
            }
            GUI.setDataSaved(true);
        }

        save(ae);

        if ((ae.getSource() == jmiDTExit) || (ae.getSource() == jmiDRExit)) {
            if (!GUI.isDataSaved()) {
                int answer = JOptionPane.showOptionDialog(null,
                        "You currently have unsaved data. Would you like to save?",
                        "Are you sure?",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, null, null);
                if (answer == JOptionPane.YES_OPTION || saveFile == null) {
                    saveAs();
                }
                if ((answer == JOptionPane.CANCEL_OPTION) || (answer == JOptionPane.CLOSED_OPTION)) {
                    return;
                }
            }
            System.exit(0); // No was selected
        }

        showAboutInfo(ae);
    } // MenuListener.actionPerformed()

    private void save(ActionEvent ae) {
        if ((ae.getSource() == jmiDTSaveAs) || (ae.getSource() == jmiDRSaveAs)
                || (ae.getSource() == jmiDTSave) || (ae.getSource() == jmiDRSave)) {
            if ((ae.getSource() == jmiDTSaveAs) || (ae.getSource() == jmiDRSaveAs)) {
                saveAs();
            } else {
                writeSave();
            }
        }
    }

    private void showAboutInfo(ActionEvent ae) {
        if ((ae.getSource() == jmiDTHelpAbout) || (ae.getSource() == jmiDRHelpAbout)) {
            JOptionPane.showMessageDialog(null, "Convert ERD To DDL Conversion Tool\n"
                    + "by Stephen A. Capperell\n"
                    + "© 2007-2008");
        }
    }

    private void readFile() {
        GUI.setParseFile(jfc.getSelectedFile());

        // test.xml

        int index = GUI.getParseFile().getName().lastIndexOf(".");
        String fileExtension = GUI.getParseFile().getName().substring(index);

        GUI.setEcfp(FileParserFactory.get(fileExtension));
        GUI.getEcfp().parse(getParseFile());

        GUI.setTables(GUI.getEcfp().getTables());
        for (int i = 0; i < GUI.getTables().length; i++) {
            GUI.getTables()[i].makeArrays();
        }

        GUI.setFields(getEcfp().getFields());
        GUI.setEcfp(null);
        populateLists();
        GUI.setSaveFile(null);
        jmiDTSave.setEnabled(false);
        jmiDRSave.setEnabled(false);
        jmiDTSaveAs.setEnabled(true);
        jmiDRSaveAs.setEnabled(true);
        jbDTDefineRelations.setEnabled(true);

        jbDTCreateDDL.setEnabled(true);
        jbDRCreateDDL.setEnabled(true);

        GUI.setTruncatedFilename(
                getParseFile().getName().substring(getParseFile().getName().lastIndexOf(File.separator) + 1));
        jfDT.setTitle(DEFINE_TABLES + " - " + GUI.getTruncatedFilename());
        jfDR.setTitle(DEFINE_RELATIONS + " - " + GUI.getTruncatedFilename());
    }

}