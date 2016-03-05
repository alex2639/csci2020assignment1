package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        int hamFileCount=0;
        for (File fileEntry:new File("./data/train/ham").listFiles()){
            hamFileCount++;
        }
        ReadFiles trainHam= new ReadFiles("./data/train/ham", trainHamFreq);
        trainHamFreq = trainHam.readFiles(new File("./data/train/ham"), trainHamFreq);

        int spamFileCount=0;
        for (File fileEntry:new File("./data/train/spam").listFiles()){
            spamFileCount++;
        }

        Map<String, Integer> trainSpamFreq = new TreeMap<>();
        ReadFiles trainSpam= new ReadFiles("./data/train/spam", trainSpamFreq);
        trainSpamFreq = trainSpam.readFiles(new File("./data/train/spam"), trainSpamFreq);

        //probability words
        Map hamWordFolder = trainHam.getProbabilities(trainHamFreq,hamFileCount);

        Map spamWordFolder = trainSpam.getProbabilities(trainSpamFreq,spamFileCount);

        Probabilities spam=new Probabilities(hamWordFolder,spamWordFolder);

        Map spamWords = spam.spamProbability(hamWordFolder,spamWordFolder);

        //testing
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("./data"));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        Testing file=new Testing(spamWords);
        double probabilitySpam;
        table = new TableView<>();
        TableColumn<TestFile,String> fileColumn = null;
        fileColumn = new TableColumn<>("File");
        fileColumn.setMinWidth(100);
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));

        TableColumn<TestFile,String> classColumn = null;
        classColumn = new TableColumn<>("Actual class");
        classColumn.setMinWidth(100);
        classColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));

        //TableColumn<TestFile,Double> probabilityColumn = null;
        TableColumn<TestFile,String> probabilityColumn = null;
        probabilityColumn = new TableColumn<>("Spam probability");
        probabilityColumn.setMinWidth(200);
        //probabilityColumn.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));
        probabilityColumn.setCellValueFactory(new PropertyValueFactory<>("spamProbRounded"));

        table.getColumns().add(fileColumn);
        table.getColumns().add(classColumn);
        table.getColumns().add(probabilityColumn);

        ObservableList<TestFile> testFiles= FXCollections.observableArrayList();

        //ReadFiles test = new ReadFiles(mainDirectory.getName());
        for(File fileEntry:mainDirectory.listFiles()){
            probabilitySpam=file.spamFile(fileEntry,spamWords);
            TestFile testFile=new TestFile(fileEntry.getName(),probabilitySpam,mainDirectory.getName());
            testFiles.add(testFile);
            table.getItems().add(testFile);
        }

        primaryStage.setTitle("Spam Detector 3000");

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
