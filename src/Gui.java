import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Gui {
	ResourceBundle strings;
	private Stage window;
	private Scene scene;
	private BorderPane bPane;
	private HBox topPane;
	private VBox leftPane;
	private ScrollPane centerPane;
	private Label monies;
	private Label titleLabel;
	private Button settings;
	
	private Button clicker;
	
	private Button buyWorker;
	private Button speedWorker;
	private Button capWorker;
	
	private Button buyBag;
	private Button speedBag;
	private Button capBag;

	public Gui(Stage stage) {
		window = stage;
		createMainScreen();
		scene = new Scene(bPane, 480, 854);
		window.setTitle("Cargo Simulator");
		window.setResizable(false);
		window.setScene(scene);
		window.show();
	}
	
	public void createMainScreen(){
		bPane = new BorderPane();//Create borderpane which will be the backbone of the entire layout
		
		Locale currentLocale = new Locale("en", "EN");
		strings = ResourceBundle.getBundle("strings", currentLocale);

		//Create a VBox for the left region of the borderpane
		leftPane = new VBox();
		leftPane.setSpacing(16);
		leftPane.setAlignment(Pos.CENTER);
		leftPane.setStyle("-fx-background-color: #CCCCCC;");
		
		//Create a HBox for the top region of the borderpane
		topPane = new HBox();
		topPane.setStyle("-fx-background-color: #0049FF;");
		topPane.setPadding(new Insets(4, 4, 4, 4));

		//Create a ScrollPane for the right region of the borderpane
		centerPane = new ScrollPane();

		//Set the right boxes to their respective borderpane
		bPane.setLeft(leftPane);
		bPane.setTop(topPane);
		bPane.setCenter(centerPane);
		
		//Add content to topPane
		titleLabel = new Label("Cargo Clicker");
		titleLabel.setFont(Font.font ("Calibri", 48));
		titleLabel.setTextFill(Color.web("#FFFFFF"));
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		settings = createImgTTButton(loadImage("res/images/worker.png", 64), "Change game settings");
		//settings.setOnAction(e -> MainApp.addWorker());
		topPane.getChildren().addAll(titleLabel, spacer, settings);
		
		//Add content to leftPane
		monies = new Label("");
		clicker = createImgTTButton(loadImage("res/images/clicker.png", 128), strings.getString("clickTT"));
		clicker.setOnAction(e -> MainApp.addWorker());
		leftPane.getChildren().addAll(monies, clicker);
		
		createMainLeft();
	}

	public void createMainLeft(){
		buyWorker = createImgTTButton(loadImage("res/images/worker.png", 64), strings.getString("buyWorkerTT"));
		//buyWorker.setOnAction(e -> buyWorker.setGraphic(loadImage("res/images/workerSpeed.png", 64)));	//TESTING PURPOSES
		buyWorker.setOnAction(e -> MainApp.addWorker());
		speedWorker = createImgTTButton(loadImage("res/images/workerSpeed.png", 64), strings.getString("speedWorkerTT"));
		speedWorker.setOnAction(e -> MainApp.speedWorker());
		capWorker = createImgTTButton(loadImage("res/images/workerCap.png", 64), strings.getString("capWorkerTT"));
		capWorker.setOnAction(e -> MainApp.capWorker());
		VBox workerBox = createItemVBox(strings.getString("workers"), strings.getString("buyWorker"), strings.getString("speedWorker"), strings.getString("capWorker"), buyWorker, speedWorker, capWorker);
		
		buyBag = createImgTTButton(loadImage("res/images/worker.png", 64), strings.getString("buyBagTT"));
		buyBag.setOnAction(e -> MainApp.addWorker());
		speedBag = createImgTTButton(loadImage("res/images/workerSpeed.png", 64), strings.getString("speedBagTT"));
		speedBag.setOnAction(e -> MainApp.addWorker());
		capBag = createImgTTButton(loadImage("res/images/workerCap.png", 64), strings.getString("capBagTT"));
		capBag.setOnAction(e -> MainApp.addWorker());
		VBox bagBox = createItemVBox(strings.getString("bags"), strings.getString("buyBag"), strings.getString("speedBag"), strings.getString("capBag"), buyBag, speedBag, capBag);
		
		VBox itemBox = new VBox();
		itemBox.setSpacing(16);
		itemBox.setStyle("-fx-background-color: #FFFFFF;");
		itemBox.getChildren().addAll(workerBox, bagBox);
		centerPane.setContent(itemBox);
	}
	
	public Button createImgTTButton(ImageView img, String tooltip){
		Button button = new Button("", img);
		button.setGraphic(img);
		button.setPadding(Insets.EMPTY);
		button.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
		if(!tooltip.equals(""))
			button.setTooltip(new Tooltip(tooltip));
		return button;
	}
	
	//public 
	
	public VBox createItemVBox(String name, String buy, String speed, String cap, Button buyButton, Button speedButton, Button capButton){
		VBox buyBox = createSubItemBox(buy, buyButton);
		VBox speedBox = createSubItemBox(speed, speedButton);
		VBox capBox = createSubItemBox(cap, capButton);
		Region spacer1 = new Region();
		Region spacer2 = new Region();

		HBox itemBox = new HBox();
		itemBox.setSpacing(8);
		itemBox.setAlignment(Pos.CENTER);
		itemBox.getChildren().addAll(buyBox, spacer1, speedBox, spacer2, capBox);

		Label title = new Label(name);

		VBox layout = new VBox();
		layout.setPadding(new Insets(5, 8, 5, 8));
		layout.setSpacing(8);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(title, itemBox);
		return layout;
	}
	
	public VBox createSubItemBox(String action, Button button){
		VBox subBox = new VBox();
		subBox.setSpacing(4);
		subBox.setAlignment(Pos.CENTER);
		Label buyLabel = new Label(action);
		subBox.getChildren().addAll(button, buyLabel);
		return subBox;
	}

	public ImageView loadImage(String fileName, int width){
		ImageView imageView = null;
		BufferedImage img = null;
		try {
			//Create a buffered image which will trigger the catch statement if image not found
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			if(img == null)
				System.out.println("Image " + fileName + " not found.");
		}
		Image image = new Image("file:"+fileName, width, 0, true, false, true);
		imageView =  new ImageView(image);
		return imageView;
	}

	public void setMonies(String message){
		monies.setText(message);
	}
}
