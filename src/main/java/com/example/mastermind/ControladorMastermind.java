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
    int intento =1; // el intento en el que se encuentra el jugador que irá aumentando cada vez que pulse en SUBMIT
    List<Integer> listaIntento = new ArrayList<>(); // almacena el intento que realiza el jugador
    private String[] colores; // almacena la lista de los colores que hay
    private int contador = 0; // contador que nos ayuda a cambiar el circulo en el que se pone el color a medida que se escoge
    HBox h; // hbox en el que se almacena la fila del intento en el que se encuentra el jugador

    @FXML
    private VBox padre; // vbox raiz del que sacaremos cada una de las filas
    @FXML
    private HBox fila1,fila2,fila3,fila4,fila5;
    @FXML
    private Circle i0,i1,i2,i3,i4,i5; // cada uno de los círculos que

    @FXML  //Método que inserta el color que pulsa el jugador
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

    @FXML  //Método que inicia la comprobación de la combinación cuando el jugador pulsa en SUBMIT
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

    //Método que compara la combinación del jugador con la combinación ganadora
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

    //Método que redibuja de blanco los círculos incorrectos y mantiene los que están correctos
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

    //Método que crea una combinación de colores aleatoria que será la combinación ganadora
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

    //Método que resetea todos los valores para poder empezar a jugar de 0.
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
