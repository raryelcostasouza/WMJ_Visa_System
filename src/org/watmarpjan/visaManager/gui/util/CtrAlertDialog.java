/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author WMJ_user
 */
public class CtrAlertDialog
{

    public static void infoDialog(String title, String msg)
    {
        Alert a;
        Label lbMsg;
        
        lbMsg = new Label(msg);
        lbMsg.setWrapText(false);
        
        a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.getDialogPane().setContent(lbMsg);
        a.showAndWait();
    }

    public static boolean confirmationDialog(String title, String msg)
    {
        Alert a;
        Optional<ButtonType> op;
        Label lbMsg;
        
        lbMsg = new Label(msg);
        lbMsg.setWrapText(false);
        
        a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(title);
        a.getDialogPane().setContent(lbMsg);
        op = a.showAndWait();
        if (op.get() == ButtonType.OK)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public static boolean confirmationUnsavedChanges()
    {
        Alert a;
        Optional<ButtonType> op;
        Label lbMsg;

        lbMsg = new Label("There are unsaved changes on the current form. Would you like to save or discard them?");
        lbMsg.setWrapText(false);
        
        ButtonType saveButtonType = new ButtonType("Save", ButtonData.OK_DONE);
        ButtonType discardButtonType = new ButtonType("Discard", ButtonData.CANCEL_CLOSE);

        a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Unsaved Changes");
        a.getDialogPane().setContent(lbMsg);
        a.getDialogPane().getButtonTypes().setAll(saveButtonType, discardButtonType);

        op = a.showAndWait();
        if (op.get() == saveButtonType)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public static void errorDialog(String msg)
    {
        Alert a;

        a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error!");
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        a.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        a.setContentText(msg);

        a.showAndWait();
    }

    public static void exceptionDialog(Exception ex, String msg)
    {
        Alert alert;
        String outputMessage;

        outputMessage = msg + "\n\n"
                + "Exception Message: " + ex.getMessage();

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setContentText(outputMessage);

        TextArea textArea = new TextArea(getExceptionText(ex));
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        Label label = new Label("The exception stacktrace was:");
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    public static void databaseExceptionDialog(ConstraintViolationException cve, String msg, String msgNotUnique)
    {
        String msgDBEx;
        DerbySQLIntegrityConstraintViolationException dbex;
        
        dbex = (DerbySQLIntegrityConstraintViolationException) cve.getCause();
       
        msgDBEx = msg+"\n:"
                + msgNotUnique+"\n:"
                + "\nDetails:"
                + "\n\nConstraint name: " + dbex.getConstraintName()
                + "\nSQL state: " + cve.getSQLState()
                + "\nMessage: " + dbex.getMessage();

        exceptionDialog(cve, msg + msgNotUnique +msgDBEx);
    }

    public static void warningDialog(String msg)
    {
        Alert a;

        a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Warning!");
        a.setContentText(msg);
        a.showAndWait();
    }

    private static String getExceptionText(Exception ex)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        ex.printStackTrace(pw);
        return sw.toString();
    }
}
