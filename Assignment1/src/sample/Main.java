package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class Main extends Application {
    private BorderPane layout;
    private Stage window;
    private TableView table;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //training
        Map<String, Integer> trainHamFreq = new TreeMap<>();
        ReadFiles trainHam= new ReadFiles("./data/train/ham", trainHamFreq);
        trainHamFreq = trainHam.readFiles(new File("./data/train/ham"), trainHamFreq);

        Map<String, Integer> trainSpamFreq = new TreeMap<>();
        ReadFiles trainSpam= new ReadFiles("./data/train/spam", trainSpamFreq);
        trainSpamFreq = trainSpam.readFiles(new File("./data/train/spam"), trainSpamFreq);

        //probabilities
        Map hamProbability = trainHam.getProbabilities(trainHamFreq);

        Map spamProbability = trainSpam.getProbabilities(trainSpamFreq);

        //testing
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("./data"));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        //ReadFiles test = new ReadFiles(mainDirectory.getName());

        primaryStage.setTitle("Spam Detector 3000");

        table = new TableView<>();

        TableColumn<TestFile,String> fileColumn = null;
        fileColumn = new TableColumn<>("File");
        fileColumn.setMinWidth(100);
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));

        TableColumn<TestFile,String> classColumn = null;
        classColumn = new TableColumn<>("Actual class");
        classColumn.setMinWidth(100);
        classColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));

        TableColumn<TestFile,Double> probabilityColumn = null;
        probabilityColumn = new TableColumn<>("Spam probability");
        probabilityColumn.setMinWidth(100);
        probabilityColumn.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));

        table.getColumns().add(fileColumn);
        table.getColumns().add(classColumn);
        table.getColumns().add(probabilityColumn);

        GridPane accuracy = new GridPane();



        layout = new BorderPane();
        layout.setCenter(table);
        layout.setBottom(accuracy);

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
