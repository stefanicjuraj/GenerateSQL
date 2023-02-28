package listener;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

import ddl.CreateDDL;
import ddl.DDLMySQL;
import ddl.DDLOracle;
import ddl.DDLGeneratorFactory;

import static screen.TableScreen.*;
import static screen.RelationScreen.*;

import generateSQL.GUI;

import utils.Constants;

public class DDLButtonListener implements ActionListener {

    PrintWriter pw;
    static File parseFile;
    static File saveFile;
    private static File outputDir;

    private static String[] productNames;
    private String databaseName;
    private CreateDDL convertCreateDDL;

    public void actionPerformed(ActionEvent ae) {
        setOutputDir();
        GUI.setSqlString(getSQLStatements());
        if (GUI.getSqlString().equals(Constants.CANCELLED)) {
            return;
        }
        writeSQL(GUI.getSqlString());
    }

    public static void setOutputDir() {
        int returnVal;
        GUI.setOutputDirOld(outputDir);
        GUI.setAlSubclasses(new ArrayList<>());
        GUI.setAlProductNames(new ArrayList<>());

        returnVal = jfcOutputDir.showOpenDialog(null);

        if (returnVal == JFileChooser.CANCEL_OPTION) {
            return;
        }

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            outputDir = jfcOutputDir.getSelectedFile();
        }

        productNames = CreateDDL.getProducts();

        if ((parseFile != null || saveFile != null) && outputDir != null) {
            jbDTCreateDDL.setEnabled(true);
            jbDRCreateDDL.setEnabled(true);
        }

    }

    public static String displayProductNames() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < productNames.length; i++) {
            sb.append(productNames[i] + "\n");
        }
        return sb.toString();
    }

    private String getSQLStatements() {
        String strSQLString = "";
        String response = (String) JOptionPane.showInputDialog(
                null,
                "Select a product:",
                "Create DDL",
                JOptionPane.PLAIN_MESSAGE,
                null,
                productNames,
                null);

        if (response == null) {
            return Constants.CANCELLED;
        }

        int selected;
        for (selected = 0; selected < productNames.length; selected++) {
            if (response.equals(productNames[selected])) {
                break;
            }
        }

        try {

            GUI.setSaveFile(new File("././resources/test.sql"));
            saveFile = GUI.getSaveFile();

            convertCreateDDL = DDLGeneratorFactory.get(String.valueOf(selected));
            convertCreateDDL.setTables(GUI.getTables());
            convertCreateDDL.setFields(GUI.getFields());
            convertCreateDDL.initialize();

            strSQLString = convertCreateDDL.getSQLString();
            databaseName = convertCreateDDL.getDatabaseName();
        } catch (Exception e) {
            System.out.println(e);
        }

        return strSQLString;
    }

    private void writeSQL(String output) {
        databaseName = convertCreateDDL.getDatabaseName();

        jfc.resetChoosableFileFilters();
        if (parseFile != null) {
            GUI.setOutputFile(new File(parseFile.getAbsolutePath().substring(0,
                    (parseFile.getAbsolutePath().lastIndexOf(File.separator) + 1)) + databaseName + ".sql"));
        } else {
            GUI.setOutputFile(new File(saveFile.getAbsolutePath().substring(0,
                    (saveFile.getAbsolutePath().lastIndexOf(File.separator) + 1)) + databaseName + ".sql"));
        }
        if (databaseName.equals("")) {
            return;
        }
        jfc.setSelectedFile(GUI.getOutputFile());
        int returnVal = jfc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            GUI.setOutputFile(jfc.getSelectedFile());
            if (GUI.getOutputFile().exists()) {
                int response = JOptionPane.showConfirmDialog(null, "Overwrite existing file?", "Confirm Overwrite",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            try {
                pw = new PrintWriter(new BufferedWriter(new FileWriter(GUI.getOutputFile(), false)));
                // write the SQL statements
                pw.println(output);
                // close the file
                pw.close();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
    }

}