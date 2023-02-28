package listener;

import java.awt.event.*;
import javax.swing.JRadioButton;

import generateSQL.GUI;

import model.Field;

import static screen.TableScreen.*;
import screen.TableScreen;
import screen.RelationScreen;

public class RadioButtonListener implements ActionListener {

    private final TableScreen defaultTableScreen;

    public RadioButtonListener(TableScreen defaultTableScreen) {
        this.defaultTableScreen = defaultTableScreen;
    }

    public void actionPerformed(ActionEvent ae) {
        JRadioButton[] jrbDataType = defaultTableScreen.getJrbDataType();
        for (int i = 0; i < jrbDataType.length; i++) {
            if (jrbDataType[i].isSelected()) {
                RelationScreen.getCurrentDTField().setDataType(i);
                break;
            }
        }
        if (jrbDataType[0].isSelected()) {
            jtfDTVarchar.setText(Integer.toString(Field.VARCHAR_DEFAULT_LENGTH));
            jbDTVarchar.setEnabled(true);
        } else {
            jtfDTVarchar.setText("");
            jbDTVarchar.setEnabled(false);
        }
        jtfDTDefaultValue.setText("");
        RelationScreen.getCurrentDTField().setDefaultValue("");
        GUI.setDataSaved(false);
    }

}