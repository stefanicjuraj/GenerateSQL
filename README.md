<!-- GenerateSQL -->
<br />
<h1 align="center">GenerateSQL</h3>
<br />

<!-- ABOUT -->
## About

<div align="justify">
  
GenerateSQL is an application which reads schema description files (e.g. XML, JSON) and generates the appropriate SQL statements required to create tables for any number of database management systems (e.g. MySQL, Oracle). It does not require all the information found in the (previously created) file, but only needs information about `Figures` (attributes and entities) and `Connectors` (connections). GenerateSQL was created and refactored to make it as reusable and extensible as possible, with the addition of providing generic input and output.

<strong> Generic input </strong> — read a schema description file (e.g. XML, JSON, etc.) generated from another diagramming tool, and perform the same functions, with the minimal changes to the application itself.

<strong> Generic output </strong> — generate the appropriate statements for any number of database management systems (e.g. MySQL, Oracle, etc.) for the Diagrammer file, with minimal changes to the application itself.
Structure the code and provide it with the information it needs to create the tables on any different DBMS without major rewriting of the code. This allows switching vendors with minimal effort.

</div>

<!-- TECHNOLOGY -->
## Technology

* [![java][java]][java-url]

<!-- API -->
## API

<div align="justify">
  
  <strong> `Open File` </strong> — open a file for manipulation.
  
  <strong> `Identify File` </strong> — identify the nature of the file selected, and deal with it accordingly.
  
  <strong> `Parse Save File` </strong> — extract required data and recreate Table and Field objects.
  
  <strong> `Parse File` </strong> — extract required data and create Table, Field, and Connector objects.
  
  <strong> `Identify Connector endpoints` </strong> — step through list of Connectors identifying whether each endpoint is a Table or a Field.
  
  <strong> `Resolve Connectors` </strong> — step through list of Connectors (edited in Identify Connector Endpoints function), and modify Table and Field objects to reflect Connectors involved.
  
  <strong> `Populate Tables Screen` </strong> — show data on GUI.
  
  <strong> `Write Save File` </strong> — write the state of each Table and Field object to a delimited text file.
  
  <strong> `Create DDL` </strong> — write DDL statements to a script file suitable for importing into the target database.
  
  <strong> `Bind Relations function` </strong> — this will allow the user to establish foreign key relationships between tables.
  
<br />
  
<strong> `RunGenerateSQL` </strong>
  
Entry point for the application.
  
  
<strong> `GUI` </strong>
  
Contains all of the GUI elements and associated event processing logic. The GUI will be rendered using the end user’s platform’s, rather than the default Java. Notable additions include a Primary Key checkbox, changing the Default Value and Varchar Length controls to buttons, and adding buttons to allow the user to rearrange the field order on the Tables screen; on the Relations screen, a Bind/Unbind Relations button to force the user to explicitly take an action to establish foreign key relationships and allow the user to unbind relations previously made.
  
  
<strong> `RadioButtonListener` </strong>
  
RadioButtonListener is an inner class belonging to GUI that implements the ActionListener interface to listen for radio button events on Define Tables screen.
  

<strong> `WindowListener` </strong>
  
An inner class belonging to GUI that implements the WindowListener interface to listen for window events on both the Tables and Relations screens. The windowClosing() method is the only method that is defined; the other methods from the interface are nonfunctional stubs.
  
  
<strong> `DDLButtonListener` </strong>
  
DDLButtonListener is an inner class belonging to GUI that implements the ActionListener interface to listen for button events from the “CreateDDL” buttons on the Tables and Relations screens.
  
  
<strong> `MenuListener` </strong>
  
An inner class belonging to GUI that implements the ActionListener interface to listen for menu events on both the Define Tables and Define Relations screens.
  
  
<strong> `CreateDDL` </strong>
  
This class will define a set of methods to output DDL statements for the tables and fields that the user has defined through a diagram and refinements to that diagram with this application.
  

<strong> `DDLMySQL` </strong>
  
This class is the implementation of ConvertCreateDDL for MySQL.
  
  
<strong> `ConvertFileParser` </strong>
  
This class will define a constructor that will take a File object that needs to be opened and parsed for table and field data. Figures with Style “Entity” become Table objects and Figures with Style “Attribute” become Field objects. The presence of Figures with Style “Relation” will cause parsing to cease, and user will be prompted to edit the diagram. Connectors between Entities and Attributes will become Connector objects which will be used to establish relationships between the Tables and Fields.
  
  
<strong> `Table` </strong>
  
The Table class represents a Figure with Style “Entity” from the Diagram, and stores information about the database tables, like the tables and fields it is related to.
  
  
<strong> `Field` </strong>
  
The Field class represents a Figure with Style “Attribute” from the Diagram, and stores information about the database fields, like datatype, default values, and whether null values are allowed. The Timestamp datatype was removed from the list of supported datatypes because of the difficulty in anticipating the localization issues inherent in date/time formats. The Varchar datatype can be used to store strings that can be interpreted as dates.
  
  
<strong> `Connector` </strong>
  
The Connector class represents a Connector from the Diagram, and is used to determine which Field belongs with which Table, and which Tables are related.

<!-- TEAM -->
## Team

See `/src/resources/team.txt` to see all team members of this project.
    
<!-- CONTACT -->
## Contact

[![linkedin][linkedin]][linkedin-url]
[![email][email]][email-url]

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[linkedin]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/jurajstefanic/
[email]: https://img.shields.io/badge/email-555?style=for-the-badge&logo=gmail&logoColor=white
[email-url]: mailto:jurajstefanic@outlook.com
[java]: https://img.shields.io/badge/java-E34F26?style=for-the-badge&logo=&logoColor=white
[java-url]: https://www.java.com/