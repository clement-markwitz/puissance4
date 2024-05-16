package fr.univartois.butinfo.ihm.fourinaline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;

public class FourInALineController {

    Grid mainGrid=new Grid();
    Token token;

    @FXML
    private Label ecran;
    @FXML
    private GridPane grilleJeu;
    @FXML
    void onRec(ActionEvent event) {
        mainGrid.clear();
        token=Token.RED;
        setDisable(false);
        afficher();
    }
    @FXML
    void on0(ActionEvent event) {
        verification(0);
    }

    @FXML
    void on1(ActionEvent event) {
        verification(1);
    }

    @FXML
    void on2(ActionEvent event) {
        verification(2);
    }

    @FXML
    void on3(ActionEvent event) {
        verification(3);
    }

    @FXML
    void on4(ActionEvent event) {
        verification(4);
    }

    @FXML
    void on5(ActionEvent event) {
        verification(5);
    }

    @FXML
    void on6(ActionEvent event) {
        verification(6);
    }
    public void nouvellePartie(){
        afficher();
        token=Token.RED;
    }
    Button[] moveButtons;
    ImageView[][] cells;
    @FXML
    void initialize() {
        moveButtons = new Button[grilleJeu.getColumnCount()];
        cells = new ImageView[grilleJeu.getRowCount() - 1][grilleJeu.getColumnCount()];

        for (Node child : grilleJeu.getChildren()) {
            // On récupère la ligne où le label se trouve.
            Integer row = GridPane.getRowIndex(child);
            if (row == null) {
                row = 0;
            }

            // On récupère la colonne où le label se trouve.
            Integer column = GridPane.getColumnIndex(child);
            if (column == null) {
                column = 0;
            }

            if (child instanceof Button button) {
                moveButtons[column] = button;

            } else if (child instanceof ImageView view) {
                cells[row - 1][column] = view;
            }
        }
        nouvellePartie();
    }
    private Image loadImage(String name) {
        URL urlImage = getClass().getResource("./images/" + name + ".gif");
        return new Image(urlImage.toExternalForm(), 50, 50, true, false);
    }

    public void afficher(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                cells[i][j].setImage(loadImage(String.valueOf(mainGrid.get(i,j))));
            }
        }
    }
    public void setDisable(Boolean disable){
        for(int i=0;i<7;i++){
            moveButtons[i].setDisable(disable);
        }
    }
    public void verification(int col){
        token=token.next();
        mainGrid.play(token,col);
        if(Objects.equals(mainGrid.findFourInALine(), Optional.of(Token.YELLOW))){
            ecran.setText("le joueur jaune a gagné");
            setDisable(true);
        }
        if(Objects.equals(mainGrid.findFourInALine(), Optional.of(Token.RED))){
            ecran.setText("le joueur rouge a gagné");
            setDisable(true);
        }
        if(mainGrid.isFull()){
            ecran.setText("personne a gagné");
            setDisable(true);
        }
        afficher();
    }
}
