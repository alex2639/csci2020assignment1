package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Main extends Application {
    private BorderPane layout;
    private Stage window;
    private TableView table;

    @Override
    public void start(Stage primaryStage) throws Exception{
        DecimalFormat df = new DecimalFormat("0.00000");

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
        fileColumn.setMinWidth(200);
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

        int count=0;
        int right=0;
        double value=0;
        double[]values;

        //ReadFiles test = new ReadFiles(mainDirectory.getName());
        for(File fileEntry:mainDirectory.listFiles()){
            if(fileEntry.isDirectory()){
                for(File subFile:fileEntry.listFiles()){
                    probabilitySpam=file.spamFile(subFile,spamWords);
                    TestFile testFile=new TestFile(subFile.getName(),probabilitySpam,fileEntry.getName());
                    testFiles.add(testFile);
                    table.getItems().add(testFile);

                    if(probabilitySpam<.5 && fileEntry.getName().equalsIgnoreCase("ham") ||probabilitySpam>.5 && fileEntry.getName().equalsIgnoreCase("spam")){
                        right++;
                    }
                    value=value+probabilitySpam;
                    count++;
                }
            }

        }
        values=new double[count];
        double mean=value/count;
        double difference=0;
        int index=0;
        for(File fileEntry:mainDirectory.listFiles()){
            if(fileEntry.isDirectory()){
                for(File subFile:fileEntry.listFiles()){
                    probabilitySpam=file.spamFile(subFile,spamWords);
                    values[index]=probabilitySpam;
                    difference=difference+Math.abs(mean-values[index]);
                    index++;
                }
            }
        }
        double precision=1-(difference/(count));
        primaryStage.setTitle("Spam Detector 3000");

        GridPane stats = new GridPane();
        double accuracy=(double) right/count;
        Label accuracyLabel=new Label("Accuracy: "+df.format(accuracy));
        stats.add(accuracyLabel,0,0);

        Label precisionLabel=new Label("Precision: "+df.format(precision));
        stats.add(precisionLabel,0,1);

        layout = new BorderPane();
        layout.setCenter(table);
        layout.setBottom(stats);

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
