
import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Lecture 7: <code>BOTView</code> class implements a view.
 * 
 * @author Denis Gracanin
 * @version 1
 */
public class BOTView extends BorderPane {
	private ObjectProperty<File> file = null;
	private Stage stage = null;
	private MenuItem openFile = null;
	private MenuItem closeFile = null;
	private Canvas canvas = null;
	private GraphicsContext gc = null;

	/**
	 * Creates a view object.
	 */
	BOTView(Stage s) {
		super();
		stage = s;
		file = new SimpleObjectProperty<File>();
		file.setValue(null);
		MenuBar menubar = new MenuBar();
		Menu menuFile = new Menu("File");
		openFile = new MenuItem("Open");
		openFile.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		openFile.setOnAction((ActionEvent t) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Drawing File");
			File f = fileChooser.showOpenDialog(stage);
			file.setValue(f);
			openFile.setDisable(true);
			closeFile.setDisable(false);
		});
		closeFile = new MenuItem("Close");
		closeFile.setDisable(true);
		closeFile.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));
		closeFile.setOnAction((ActionEvent t) -> {
			file.setValue(null);
			openFile.setDisable(false);
			closeFile.setDisable(true);
		});
		MenuItem save = new MenuItem("Save");
		save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		save.setOnAction((ActionEvent t) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Drawing File");
			File f = fileChooser.showSaveDialog(stage);
			f = file.getValue();
		});
		MenuItem newfile = new MenuItem("New");
		newfile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		newfile.setOnAction((ActionEvent t) -> {
			file.setValue(null);
			openFile.setDisable(false);
			closeFile.setDisable(false);
		});

		SeparatorMenuItem separator = new SeparatorMenuItem();

		MenuItem quit = new MenuItem("Quit");
		quit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
		quit.setOnAction((ActionEvent t) -> {
			System.exit(0);
		});        
		menuFile.getItems().addAll(newfile, openFile, save, closeFile, separator,  quit);
		
		Menu menuEdit = new Menu("Edit");
		MenuItem copy = new MenuItem("Copy");
		copy.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
		copy.setOnAction((ActionEvent t) -> {
			System.out.println("Testing copy code");
			//copy code. store locally?
		});
		MenuItem paste = new MenuItem("Paste");
		paste.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
		paste.setOnAction((ActionEvent t) -> {
			//paste code
		});
		MenuItem delete = new MenuItem("Delete");
		delete.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
		delete.setOnAction((ActionEvent t) -> {
			//delete code
		});
		menuEdit.getItems().addAll(copy, paste, delete);
		

		Menu menuHelp = new Menu("Help");
		MenuItem about = new MenuItem("About");
		about.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		about.setOnAction((ActionEvent t) -> {
			MessageDialog dialog = new MessageDialog("Homework 2", "Homework 2 version 1.");
			dialog.show();
		});   
		menuHelp.getItems().addAll(about);

		menubar.getMenus().addAll(menuFile, menuEdit, menuHelp);
		setTop(menubar);
		canvas = new Canvas(300, 250);
		gc = canvas.getGraphicsContext2D();
		setCenter(canvas);
		BorderPane.setAlignment(canvas, Pos.TOP_LEFT);
	}

	/**
	 * Gets the file property.
	 */
	public ObjectProperty<File> fileProperty() { return file; }

	/**
	 * Clears the drawing.
	 */
	public void clear() { gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); }

	/**
	 * Draws a single element.
	 * 
	 * @param s The string representation of the element.
	 */
	public void draw(String s) {
		//TODO: rewrite draw method
	}
}

/**
 * Creates the message dialog.
 * 
 * @author Denis Gracanin
 * @version 1
 */
class MessageDialog extends Stage {

	/**
	 * Creates an instance of <code>MessageDialog</code> class.
	 * 
	 * @param title The dialog title.
	 * @param message The dialog message.
	 */
	public MessageDialog(String title, String message) {
		super();
		setTitle(title);
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);

		BorderPane root = new BorderPane();
		root.getStyleClass().add("message-dialog");
		TextArea messageArea = new TextArea(message);
		messageArea.setEditable(false);
		messageArea.setWrapText(true);
		messageArea.setFocusTraversable(false);
		root.setCenter(messageArea);
		root.getStyleClass().add("message-dialog");
		Button okButton = new Button("OK");
		okButton.setOnAction((e) -> { close(); });
		BorderPane.setAlignment(okButton, Pos.CENTER);
		BorderPane.setMargin(okButton, new Insets(10,10,10,10));
		root.setBottom(okButton);

		Scene scene = new Scene(root, 200, 150);
		try {
			scene.getStylesheets().add(getClass().getResource("bot.css").toExternalForm());
		} catch(Exception e) {
			e.printStackTrace();
		}
		setScene(scene);
	}

}

