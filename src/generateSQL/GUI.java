package generateSQL;

import javax.swing.*;
import java.io.*;
import java.util.*;

import model.Field;
import model.Table;

import parser.FileParser;
import parser.ConvertFileParser;

import screen.TableScreen;
import screen.RelationScreen;
import static screen.TableScreen.*;
import static screen.RelationScreen.*;

import ddl.CreateDDL;

import static utils.Constants.*;

/**
 * GUI class contain all of the GUI elements associated with event
 * processing logic.
 * The GUI will be rendered using the end user’s platform’s look and feel,
 * rather than the default Java look and feel.
 * The basic functionality of these screens was detailed in the preliminary
 * design document.
 * Notable additions will include a Primary Key checkbox, changing the Default
 * Value and Varchar Length controls to buttons, and
 * adding buttons to allow the user to rearrange the field order on the Define
 * Tables screen.
 * On the Define Relations screen, a Bind/Unbind Relations button to force the
 * user to explicitly take an action to establish foreign
 * key relationships and allow the user to unbind relations previously made.
 * 
 * @see javax.swing.*
 * @see java.io.*
 * @see java.util.*
 * @see static utils.Constants.*
 */
public class GUI {

    private static File parseFile;
    private static File saveFile;
    private static File outputFile;
    private static File outputDir;
    private static File outputDirOld;

    private static String truncatedFilename;
    private static String sqlString;
    private static String databaseName;

    private static FileParser ecfp;
    private static CreateDDL eccd;
    private static Table[] tables; // master copy of Table object
    private static Field[] fields; // master copy of Field object

    private static boolean dataSaved = true;
    private static List<Object> alSubclasses;
    private static List<String> alProductNames;
    private static String[] productNames;
    private static Object[] objSubclasses;

    /**
     * getParseFile is a getter method that returns the file being parsed
     * 
     * @return the parsed file
     */
    public static File getParseFile() {
        return parseFile;
    }

    /**
     * setParseFile is a setter method that sets the file being parsed
     * 
     * @param parseFile sets the file being parsed
     */
    public static void setParseFile(File parseFile) {
        GUI.parseFile = parseFile;
    }

    /**
     * getSaveFile is a getter method that returns the file being saved
     * 
     * @return the saved file
     */
    public static File getSaveFile() {
        return saveFile;
    }

    /**
     * setSaveFile is a setter method that sets the file being saved
     * 
     * @param saveFile sets the file being saved
     */
    public static void setSaveFile(File saveFile) {
        GUI.saveFile = saveFile;
    }

    /**
     * getOutputFile is a getter method that returns the output file
     * 
     * @return the output file
     */
    public static File getOutputFile() {
        return outputFile;
    }

    /**
     * setOutputFile is a setter method that sets the output file
     * 
     * @param outputFile sets the output file
     */
    public static void setOutputFile(File outputFile) {
        GUI.outputFile = outputFile;
    }

    /**
     * getOutputDir is a getter method that returns the output directory value
     * 
     * @return the output directory file
     */
    public static File getOutputDir() {
        return outputDir;
    }

    /**
     * setOutputDir is a setter method that sets the output directory file
     * 
     * @param outputDir sets the output directory file
     */
    public static void setOutputDir(File outputDir) {
        GUI.outputDir = outputDir;
    }

    /**
     * getOutputDirOld is a getter method that returns the old directory of a file
     * 
     * @return the old output directory file
     */
    public static File getOutputDirOld() {
        return outputDirOld;
    }

    /**
     * setOutputDirOld is a setter method that sets the old output directory file
     * 
     * @param outputDirOld sets the old output directory file
     */
    public static void setOutputDirOld(File outputDirOld) {
        GUI.outputDirOld = outputDirOld;
    }

    /**
     * getTruncatedFilename is a getter method that gets the truncated file name
     * 
     * @return the truncated filename
     */
    public static String getTruncatedFilename() {
        return truncatedFilename;
    }

    /**
     * setTruncatedFilename is a setter method that sets the file name to be
     * truncated
     * 
     * @param truncatedFilename sets the file to be truncated
     */
    public static void setTruncatedFilename(String truncatedFilename) {
        GUI.truncatedFilename = truncatedFilename;
    }

    /**
     * getSqlString is a getter method that gets the SqlString
     * 
     * @return the sqlString
     */
    public static String getSqlString() {
        return sqlString;
    }

    /**
     * setSqlString is a setter method that sets the sqlString string
     * 
     * @param sqlString sets the string sqlString file
     */
    public static void setSqlString(String sqlString) {
        GUI.sqlString = sqlString;
    }

    /**
     * getDatabaseName is a getter method that gets the used database name
     * 
     * @return the database name
     */
    public static String getDatabaseName() {
        return databaseName;
    }

    /**
     * setDatabaseName is a setter method that sets the used database name
     * 
     * @param databaseName sets the used database name
     */
    public static void setDatabaseName(String databaseName) {
        GUI.databaseName = databaseName;
    }

    /**
     * getEcfp is a getter method that gets the converted file parsed
     * 
     * @return the converted file parsed ecfp
     */
    public static FileParser getEcfp() {
        return ecfp;
    }

    /**
     * setEcfp is a setter method that sets the converted file parsed
     * 
     * @param ecfp sets the converted file parsed
     */
    public static void setEcfp(FileParser ecfp) {
        GUI.ecfp = ecfp;
    }

    /**
     * getEccd is a getter method that gets the created converted ddl
     * 
     * @return the created converted ddl eccd
     */
    public static CreateDDL getEccd() {
        return eccd;
    }

    /**
     * setEccd is a setter method that sets the created converted ddl
     * 
     * @param eccd sets the created converted ddl
     */
    public static void setEccd(CreateDDL eccd) {
        GUI.eccd = eccd;
    }

    /**
     * getFields is a getter method that gets the fields
     * 
     * @return fields
     */
    public static Field[] getFields() {
        return fields;
    }

    /**
     * setFields is a setter method that sets the fields
     * 
     * @param fields - takes in and sets an array of fields
     */
    public static void setFields(Field[] fields) {
        GUI.fields = fields;
    }

    /**
     * isDataSaved is a boolean method for verifying whether the data has been saved
     * or not
     * 
     * @return the saved data
     */
    public static boolean isDataSaved() {
        return dataSaved;
    }

    /**
     * setDataSaved is a setter boolean method for setting the data that will be
     * saved
     * 
     * @param dataSaved - sets the data that will be saved
     */
    public static void setDataSaved(boolean dataSaved) {
        GUI.dataSaved = dataSaved;
    }

    /**
     * getAlSubclasses is a getter method for getting the array-list object classes
     * 
     * @return the array-list object classes
     */
    public static List<Object> getAlSubclasses() {
        return alSubclasses;
    }

    /**
     * setAlSubclasses is a setter method for setting the array-list object classes
     * 
     * @param alSubclasses sets the array-list object classes
     */
    public static void setAlSubclasses(List<Object> alSubclasses) {
        GUI.alSubclasses = alSubclasses;
    }

    /**
     * getAlProductNames is a getter method for getting the array-list string
     * classes
     * 
     * @return the array-list string classes
     */
    public static List<String> getAlProductNames() {
        return alProductNames;
    }

    /**
     * setAlProductNames is a setter method for setting the array-list string
     * classes
     * 
     * @param alProductNames sets the array-list string classes
     */
    public static void setAlProductNames(List<String> alProductNames) {
        GUI.alProductNames = alProductNames;
    }

    /**
     * getProductNames is a getter method for getting the string list of products
     * (product names)
     * 
     * @return the provided string list of product names
     */
    public static String[] getProductNames() {
        return productNames;
    }

    /**
     * setProductNames is a setter method for setting the string list of products
     * 
     * @param productNames sets the string list of product names
     */
    public static void setProductNames(String[] productNames) {
        GUI.productNames = productNames;
    }

    /**
     * getObjSubclasses is a getter method for getting the object info
     * 
     * @return the object classes
     */
    public static Object[] getObjSubclasses() {
        return objSubclasses;
    }

    /**
     * setObjSubclasses is a setter method for setting the object info
     * 
     * @param objSubclasses sets the list of object classes
     */
    public static void setObjSubclasses(Object[] objSubclasses) {
        GUI.objSubclasses = objSubclasses;
    }

    /**
     * getTables is a getter method for getting the table info
     * 
     * @return the tables provided
     */
    public static Table[] getTables() {
        return tables;
    }

    /**
     * setTables is a setter method for setting the table info
     * 
     * @param tables sets the provided tables
     */
    public static void setTables(Table[] tables) {
        GUI.tables = tables;
    }

    /**
     * 
     */
    public GUI() {
        this.showGUI();
    } // GUI.GUI()

    public void showGUI() {
        new TableScreen(); // Show define table screen
        new RelationScreen(); // Show define relation screen
    } // showGUI()

    /**
     * saveAs method is a void method for providing additional information and logic
     * surrounding saving a file
     * 
     * @return the jfc.showSaveDialog as null if save file does not exist
     */
    public static void saveAs() {
        int returnVal;
        jfc.addChoosableFileFilter(effSave);
        returnVal = jfc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            saveFile = jfc.getSelectedFile();
            if (saveFile.exists()) {
                int response = JOptionPane.showConfirmDialog(null, "Overwrite existing file?", "Confirm Overwrite",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            if (!saveFile.getName().endsWith("sav")) {
                String temp = saveFile.getAbsolutePath() + ".sav";
                saveFile = new File(temp);
            }
            jmiDTSave.setEnabled(true);
            truncatedFilename = saveFile.getName().substring(saveFile.getName().lastIndexOf(File.separator) + 1);
            jfDT.setTitle(DEFINE_TABLES + " - " + truncatedFilename);
            jfDR.setTitle(DEFINE_RELATIONS + " - " + truncatedFilename);
        } else {
            return;
        }
        writeSave();
    }

    /**
     * writeSave method is a void method for saving the provided data, information
     * and logic surrounding saving a file
     */
    public static void writeSave() {
        if (saveFile != null) {
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(saveFile, false)));
                // write the identification line
                pw.println(ConvertFileParser.SAVE_ID);
                // write the tables
                pw.println("#Tables#");
                for (int i = 0; i < getTables().length; i++) {
                    pw.println(getTables()[i]);
                }
                // write the fields
                pw.println("#Fields#");
                for (int i = 0; i < fields.length; i++) {
                    pw.println(fields[i]);
                }
                // close the file
                pw.close();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            dataSaved = true;
        }
    }

}