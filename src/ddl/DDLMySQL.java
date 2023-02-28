package ddl;

import javax.swing.*;

import screen.RelationScreen;

public class DDLMySQL extends CreateDDL {

   protected String databaseName;
   // this array is for determining how MySQL refers to datatypes
   protected String[] stringDataType = { "VARCHAR", "BOOL", "INT", "DOUBLE" };

   public DDLMySQL() {
      sb = new StringBuilder();
   } // DDLMySQL(Table[], Field[])

   public void createDDL() {
      RelationScreen.setReadSuccess(true);
      databaseName = generateDatabaseName();

      sb.append("CREATE DATABASE " + databaseName + ";\r\n");
      sb.append("USE " + databaseName + ";\r\n");

      for (int boundCount = 0; boundCount <= maxBound; boundCount++) { // process tables in order from least dependent
                                                                       // (least number of bound tables) to most
                                                                       // dependent
         for (int tableCount = 0; tableCount < numBoundTables.length; tableCount++) { // step through list of tables
            if (numBoundTables[tableCount] == boundCount) { //
               createTable(tableCount);
            }
         }
      }
   }

   public String generateDatabaseName() { // prompts user for database name
      String dbNameDefault = "MySQLDB";

      do {
         databaseName = (String) JOptionPane.showInputDialog(
               null,
               "Enter the database name:",
               "Database Name",
               JOptionPane.PLAIN_MESSAGE,
               null,
               null,
               dbNameDefault);
         if (databaseName == null) {
            RelationScreen.setReadSuccess(false);
            return "";
         }
         if (databaseName.equals("")) {
            JOptionPane.showMessageDialog(null, "You must select a name for your database.");
         }
      } while (databaseName.equals(""));
      return databaseName;
   }

   public String getDatabaseName() {
      return databaseName;
   }

   public String getProductName() {
      return "MySQL";
   }

   public String getSQLString() {
      createDDL();
      return sb.toString();
   }

   @Override
   protected String[] getStringDataTypes() {
      return stringDataType;
   }

}