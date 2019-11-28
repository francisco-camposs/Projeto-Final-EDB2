package br.com.francisco.control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.com.francisco.Trie;
import br.com.francisco.model.note.NoteReader;
import br.com.francisco.model.note.NoteWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.input.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<ListCell> areaNota;

    @FXML
    private TextArea texto;

    @FXML
    private Button adicionarButton;

    @FXML
    private Button cancelarButton;


    @FXML
    private TextField titulo;

    private Trie tree;

    private NoteReader reader;

    private NoteWriter writer;

    public Controller() {
    }

    @FXML
    void initialize() throws IOException {
        assert areaNota != null : "fx:id=\"areaNota\" was not injected: check your FXML file 'sample.fxml'.";
        assert texto != null : "fx:id=\"Texto\" was not injected: check your FXML file 'sample.fxml'.";
        assert adicionarButton != null : "fx:id=\"Adicionar\" was not injected: check your FXML file 'sample.fxml'.";
        assert cancelarButton != null : "fx:id=\"Cancelar\" was not injected: check your FXML file 'sample.fxml'.";


        tree = new Trie("data/portuguese.txt");
        writer = new NoteWriter();
        reader = new NoteReader();
        reader.listView(new ArrayList<ListCell>(), "data/note");

        for (var value:reader.getList()) {
            areaNota.getItems().add(value);
        }

        texto.setPrefRowCount(10);
        texto.setPrefColumnCount(30);
        texto.setWrapText(true);
        ListCell cell = new ListCell();

        System.out.println(texto.getBoundsInParent());

    }

    @FXML
    void newContextMenu1(KeyEvent event) {
        menuRefresh(texto.getAnchor());
    }

    @FXML
    void newContextMenu2(MouseEvent event) {
        menuRefresh(texto.getAnchor());
    }

    private void menuRefresh(int point) {
        String auxiliarStr = texto.getText();

        int finish = auxiliarStr.substring(point).indexOf(" ");
        finish = (finish == -1) ?  auxiliarStr.length(): finish+point;

        int begin = (auxiliarStr.substring(0,point).lastIndexOf(" "));
        begin = (begin == -1) ? 0 : begin+1;

        String prefix = auxiliarStr.substring(begin, finish);

        texto.setContextMenu(generateMenu(prefix.toLowerCase(), begin, finish));
    }

    private ContextMenu generateMenu(String prefix, int begin, int finish) {
        MenuItem tmp = new MenuItem();

        ArrayList<String> sugestions = tree.sugestions(prefix);

        if (sugestions == null){
            return new ContextMenu();
        }
        MenuItem[] menuItems = new MenuItem[sugestions.size() >= 10 ? 10 : sugestions.size()];
        for (int count = 0; count < menuItems.length && count< 10; count++) {
            tmp.setText(sugestions.get(count));
            int finalCount = count;
            MenuItem finalTmp = tmp;
            tmp.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    texto.setText(texto.getText().substring(0, begin) + finalTmp.getText() +texto.getText().substring(finish));
                    texto.positionCaret(begin+finalTmp.getText().length());
                }
            });

            menuItems[count] = tmp;
            tmp = new MenuItem();
        }

        ContextMenu aux = new ContextMenu(menuItems);
        for (var value: (new ContextMenu()).getItems()) {
            aux.getItems().add(value);
        }
        return aux;
    }

    @FXML
    void addNote(ActionEvent event){
        if (reader.getNotes().containsKey(titulo.getText())){
            System.out.println("Não pode!");
            return;
        } else {
//            writer.write(reader, )
        }
    }
}





