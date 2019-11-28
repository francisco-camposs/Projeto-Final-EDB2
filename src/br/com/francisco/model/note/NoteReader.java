package br.com.francisco.model.note;

import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class NoteReader {

    ArrayList<ListCell> list;
    HashMap<String, String> notes;

    public void listView(ArrayList<ListCell> cell, String locate) throws IOException {

        File directory = new File(locate);
        ListCell item = new ListCell();
        Scanner util;
        String aux1;
        String aux2;

        for (var value: directory.listFiles()) {
            util = new Scanner(value);
            item.setText(aux1 = util.nextLine());
            while (util.hasNextLine()){
                item.setText(item.getText()+"\n"+ (aux2 = util.nextLine()));
//                notes.put(aux1, aux2);
                item.setStyle("-fx-background-color: #e2f2f5; -fx-border-radius: 0px; -fx-border-color: #b6eff9; -fx-padding: 0px 0px; -fx-start-margin: 0px 0px;");
            }
            item.setMinWidth(200);
            cell.add(item);
            item = new ListCell();
        }
        list = cell;
    }

    public ArrayList<ListCell> getList() {
        return list;
    }

    public void setList(ArrayList<ListCell> list) {
        this.list = list;
    }

    public HashMap<String, String> getNotes() {
        return notes;
    }

    public void setNotes(HashMap<String, String> notes) {
        this.notes = notes;
    }
}
