package com.example.mastermind;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.*;

public class ControladorMastermind implements Initializable {

    List<Integer> combinacion; // guarda la combinación
    int intento =1;
    List<Integer> listaIntento = new ArrayList<>();

    private String[] colores;

    private int contador = 0;

    HBox h;

    @FXML
    private VBox padre;

    @FXML
    private HBox fila1,fila2,fila3,fila4,fila5;

    @FXML
    private Circle i0;

    @FXML
    private Circle i2;

    @FXML
    private Circle i1;

    @FXML
    private Circle i3;

    @FXML
    private Circle i4;
    @FXML
    private Circle i5;

    @FXML
    void ponerColor(MouseEvent event) {
        Node source = (Node) event.getSource();
        String nombre= source.getId();
        int posicion = Integer.parseInt(nombre.substring(nombre.length()-1));

        HBox h = (HBox) padre.lookup("#fila"+intento);
        Circle c = (Circle) h.getChildren().get(contador);
        c.setStyle("-fx-fill: "+colores[posicion]);

        if(listaIntento.size()==5){
            listaIntento.remove(contador);
        }
        listaIntento.add(contador,posicion);

        if (contador < 4){
            contador++;
        } else {
            contador = 0;
        }
    }

    @FXML
    void comprobar(ActionEvent event) {

        if(!comparar()){
            redibujar();

            intento++;

            if(intento == 6){
                Alert derrota = new Alert(Alert.AlertType.ERROR);
                derrota.setTitle("Derrota");
                derrota.setHeaderText("Derrota.");
                derrota.setContentText("Te has quedado sin intentos.");
                derrota.showAndWait();
                empezarPartida();
            }
        }


    }

    Boolean comparar(){
        int iguales = 0;
        int pos = 0;
        for(int num: combinacion){
            if(num != listaIntento.get(pos)){
                listaIntento.remove(pos);
                listaIntento.add(pos,-1);
            } else{
                iguales++;
            }
            pos++;
        }

        System.out.println(listaIntento);

        if(iguales == 5){
            System.out.println("Felicidades has ganado!");
            Alert victoria = new Alert(Alert.AlertType.CONFIRMATION);
            victoria.setTitle("Victoria");
            victoria.setHeaderText("Enhorabuena!");
            victoria.setContentText("Has ganado en el intento numero: "+intento);
            victoria.showAndWait();

            empezarPartida();
            return true;
        }else {
            return false;
        }
    }

    void redibujar(){
        int pos = 0;
        HBox h = (HBox) padre.lookup("#fila"+intento);
        for(int num: listaIntento){
            if(num == -1){
                Circle c = (Circle) h.getChildren().get(pos);
                c.setStyle("-fx-fill: white");
            }
            pos++;
        }
    }

    public void crearCombinacion() {
        Random aleatorio = new Random(System.currentTimeMillis());
        int contador = 0;
        while (contador < this.colores.length-1) {
            int num = aleatorio.nextInt(6);
            if (!this.combinacion.contains(num)) {
                combinacion.add(num);
                contador++;
            }
        }
        System.out.println(combinacion);
    }

    public void empezarPartida(){
        intento = 1;
        contador = 0;
        colores = new String[] {"blue","red","green","yellow","violet","brown"};
        combinacion = new ArrayList<>();
        h = new HBox();
        for (int i = 1; i < 6; i++) {
            HBox h = (HBox) padre.lookup("#fila"+i);
            for (int j = 0; j < 5; j++) {
                Circle c = (Circle) h.getChildren().get(j);
                c.setStyle("-fx-fill: white");
            }
        }
        crearCombinacion();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empezarPartida();
    }
}
