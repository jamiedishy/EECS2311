package enamel;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
* This class constructs the main application window containing a menu bar,
* a status bar, a left panel that will list the scenes in the current
* scenario file, and a dynamic center panel that displays other application
* pages.
* 
* @author Team 14: Jamie Dishy, Jonas Laya, Samuel On, Paul Sison
* 
*/

public class ScenarioEditorView {

	// main window components
	Stage window;
	Scene main;
	BorderPane mainLayout;

	// menu bar components
	MenuBar menuBar;
	Menu menuFile, menuAudio, menuHelp;
	MenuItem miNew, miOpen, miSave, miSaveAs, miPlay, miClose, miExit;	
	MenuItem miUpload, miRecord;
	MenuItem miStats;
	
	// status bar components
	AnchorPane statusBar;
	HBox headerinfoLayout;
	Label filenameLbl, cellLbl, buttonLbl, statusLbl;
	
	// left panel components
	ScenePanelView tree;

	/**
     * Instantiates all main window components.
     */
	public ScenarioEditorView() {
		window = new Stage();
		mainLayout = new BorderPane();
		
		menuBar = new MenuBar();
		
		menuFile = new Menu("_File");
		miNew = new MenuItem("_New...");
		miOpen = new MenuItem("_Open...");
		miSave = new MenuItem("_Save");
		miSaveAs = new MenuItem("S_ave as...");
		miPlay = new MenuItem("_Play scenario");
		miClose = new MenuItem("_Close file");
		miExit = new MenuItem("_Exit");
		
		menuAudio = new Menu("_Audio");
		miUpload = new MenuItem("_Upload...");
		miRecord = new MenuItem("_Record...");
		
		menuHelp = new Menu("_Help");
		miStats = new MenuItem("_Stats");
		
		statusBar = new AnchorPane();
		headerinfoLayout = new HBox();
		filenameLbl = new Label();
		cellLbl = new Label();
		buttonLbl = new Label();
		statusLbl = new Label();
		
		layoutWindow();	
	}

	/**
	 * Formats the window and its components i.e. where and how the components
	 * will be displayed within the application window.
	 */
	private void layoutWindow() {
		menuFile.getItems().addAll(
				miNew, miOpen, new SeparatorMenuItem(), 
				miSave, miSaveAs, new SeparatorMenuItem(),
				miPlay, new SeparatorMenuItem(),
				miClose, new SeparatorMenuItem(),
				miExit
			);
		menuAudio.getItems().addAll(miUpload, miRecord);
		menuHelp.getItems().addAll(miStats);
		menuBar.getMenus().addAll(menuFile, menuAudio, menuHelp);
		
		headerinfoLayout.setSpacing(16);
		statusBar.setPadding(new Insets(0, 10, 0, 5));
		headerinfoLayout.setPadding(new Insets(5, 5, 5, 5));

		AnchorPane.setLeftAnchor(headerinfoLayout, 0.0);
		AnchorPane.setTopAnchor(headerinfoLayout, 0.0);
		AnchorPane.setBottomAnchor(headerinfoLayout, 0.0);
		AnchorPane.setRightAnchor(statusLbl, 0.0);
		AnchorPane.setTopAnchor(statusLbl, 0.0);
		AnchorPane.setBottomAnchor(statusLbl, 0.0);
		
		headerinfoLayout.getChildren().addAll(
				new Label("Braille Cells:"), cellLbl,
				new Separator(Orientation.VERTICAL),
				new Label("Buttons:"), buttonLbl,
				new Separator(Orientation.VERTICAL),
				filenameLbl
			);
		
		statusBar.getChildren().addAll(
				headerinfoLayout,
				statusLbl
			);
		
		menuBar.setId("toolbar");
		statusBar.setId("toolbar");
		
		mainLayout.setTop(menuBar);
		mainLayout.setBottom(statusBar);
		main = new Scene(mainLayout);
	}
	
	/**
	 * Renders the application window on the user's screen.
	 */
	public void display() {
		window.setTitle("Scenario Editor");
		window.setMinHeight(720);
		window.setMinWidth(1360);
		main.getStylesheets().add("default.css");
		
		window.setScene(main);
		window.show();
	}

}
