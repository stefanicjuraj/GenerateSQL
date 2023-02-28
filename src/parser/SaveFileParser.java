package parser;

import java.io.*;
import java.util.*;
import javax.swing.*;

import model.Connector;
import model.Field;
import model.Table;

public class SaveFileParser extends FileParser {

   private BufferedReader br;
   private String currentLine;

   private int numLine;
   public static final String DIAGRAM_ID = "Diagram File"; // first line of .edg files should be this
   public static final String SAVE_ID = "Convert Save File"; // first line of save files should be this

   public SaveFileParser() {
      alTables = new ArrayList<Table>();
      alFields = new ArrayList<Field>();
      alConnectors = new ArrayList<Connector>();
      numLine = 0;
   }

   @Override
   public void analyze() throws Exception { // this method is fucked
      StringTokenizer stTables;
      StringTokenizer stNatFields;
      StringTokenizer stRelFields;
      StringTokenizer stField;
      Table tempTable;
      Field tempField;
      currentLine = br.readLine();
      currentLine = br.readLine(); // this should be "Table: "
      while (currentLine.startsWith("Table: ")) {
         int numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); // get the Table number
         currentLine = br.readLine(); // this should be "{"
         currentLine = br.readLine(); // this should be "TableName"
         String tableName = currentLine.substring(currentLine.indexOf(" ") + 1);
         tempTable = new Table(numFigure + DELIM + tableName);

         currentLine = br.readLine(); // this should be the NativeFields list
         stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
         int numFields = stNatFields.countTokens();
         for (int i = 0; i < numFields; i++) {
            tempTable.addNativeField(Integer.parseInt(stNatFields.nextToken()));
         }

         currentLine = br.readLine(); // this should be the RelatedTables list
         stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
         int numTables = stTables.countTokens();
         for (int i = 0; i < numTables; i++) {
            tempTable.addRelatedTable(Integer.parseInt(stTables.nextToken()));
         }
         tempTable.makeArrays();

         currentLine = br.readLine(); // this should be the RelatedFields list
         stRelFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
         numFields = stRelFields.countTokens();

         for (int i = 0; i < numFields; i++) {
            tempTable.setRelatedField(i, Integer.parseInt(stRelFields.nextToken()));
         }

         alTables.add(tempTable);
         currentLine = br.readLine(); // this should be "}"
         currentLine = br.readLine(); // this should be "\n"
         currentLine = br.readLine(); // this should be either the next "Table: ", #Fields#
      }
      while ((currentLine = br.readLine()) != null) {
         stField = new StringTokenizer(currentLine, DELIM);
         int numFigure = Integer.parseInt(stField.nextToken());
         String fieldName = stField.nextToken();
         tempField = new Field(numFigure + DELIM + fieldName);
         tempField.setTableID(Integer.parseInt(stField.nextToken()));
         tempField.setTableBound(Integer.parseInt(stField.nextToken()));
         tempField.setFieldBound(Integer.parseInt(stField.nextToken()));
         tempField.setDataType(Integer.parseInt(stField.nextToken()));
         tempField.setVarcharValue(Integer.parseInt(stField.nextToken()));
         tempField.setIsPrimaryKey(Boolean.parseBoolean(stField.nextToken()));
         tempField.setDisallowNull(Boolean.parseBoolean(stField.nextToken()));
         if (stField.hasMoreTokens()) { // Default Value may not be defined
            tempField.setDefaultValue(stField.nextToken());
         }
         alFields.add(tempField);
      }
   } // parseSaveFile()

   private void makeArrays() { // convert ArrayList object into arrays of the appropriate Class type
      if (alTables != null) {
         tables = (Table[]) alTables.toArray(new Table[alTables.size()]);
      }
      if (alFields != null) {
         fields = alFields.toArray(new Field[alFields.size()]);
      }
      if (alConnectors != null) {
         connectors = alConnectors.toArray(new Connector[alConnectors.size()]);
      }
   }

   public Table[] getTables() {
      return tables;
   }

   public Field[] getFields() {
      return fields;
   }

   @Override
   public void parse(File file) {
      this.parseFile = file;
      try {
         FileReader fr = new FileReader(parseFile);
         br = new BufferedReader(fr);
         // test for what kind of file we have
         currentLine = br.readLine().trim();
         numLine++;

         if (currentLine.startsWith(SAVE_ID)) { // the file chosen is a Save file created by this application
            this.analyze(); // parse the file
            br.close();
            this.makeArrays(); // convert ArrayList object into arrays of the appropriate Class type
         } else { // the file chosen is something else
            JOptionPane.showMessageDialog(null, "Unrecognized file format");
         }

      } // try
      catch (FileNotFoundException fnfe) {
         System.out.println(parseFile.getName());
         System.exit(0);
      } // catch FileNotFoundException
      catch (Exception ioe) {
         System.out.println(ioe);
         System.exit(0);
      } // catch IOException
   }

}