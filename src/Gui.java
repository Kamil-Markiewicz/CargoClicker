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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
	private VBox mainBox;
	private HBox layout;
	private GridPane grid;
	private HBox topPane;
	private VBox leftPane;
	private ScrollPane centerPane;
	private Label monies;
	private Label titleLabel;
	private Button settings;
	private int scale;

	private Button clicker;

	private Button buyWorker;
	private Button speedWorker;
	private Button capWorker;
	private Label buyWorkerPrice;
	private Label speedWorkerPrice;
	private Label capWorkerPrice;

	private Button buyBag;
	private Button speedBag;
	private Button capBag;
	private Label buyBagPrice;
	private Label speedBagPrice;
	private Label capBagPrice;

	public Gui(Stage stage) {
		window = stage;
		scale = 5;
		createMainScreen();
		scene = new Scene(mainBox, 480, 854);
		window.setTitle("Cargo Simulator");
		window.setScene(scene);
		window.show();
	}

	public void createMainScreen(){
		Locale currentLocale = new Locale("en", "EN");
		strings = ResourceBundle.getBundle("strings", currentLocale);

		mainBox = new VBox();
		layout = new HBox();
		VBox.setVgrow(layout, Priority.ALWAYS);
		
		//Create a VBox for the left region of the borderpane
		leftPane = new VBox();
		leftPane.setSpacing(16);
		leftPane.setPadding(new Insets(2, 2, 2, 2));
		leftPane.setAlignment(Pos.CENTER);
		leftPane.setMaxWidth(960);
		leftPane.setStyle("-fx-background-image: url('file:res/images/background.png'); -fx-background-repeat: repeat; -fx-background-size: " + 16*scale + " " + 9*scale + ";");

		//Create a HBox for the top region of the borderpane
		topPane = new HBox();
		topPane.setStyle("-fx-background-color: #0049FF;");
		topPane.setPadding(new Insets(4, 4, 4, 4));

		//Create a ScrollPane for the right region of the borderpane
		centerPane = new ScrollPane();
		centerPane.setFitToWidth(true);
		centerPane.setFitToHeight(true);
		
		//Set the boxes into a layout
		layout.getChildren().addAll(leftPane, centerPane);
		mainBox.getChildren().addAll(topPane, layout);

		//Add content to topPane
		titleLabel = new Label("Cargo Clicker");
		titleLabel.setFont(Font.font ("Segoe", 9*scale));
		titleLabel.setTextFill(Color.web("#FFFFFF"));
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		settings = createImgTTButton(loadImage("res/images/worker.png", 18*scale), "Change game settings");
		//settings.setOnAction(e -> MainApp.addWorker());
		VBox settingsBox = new VBox();
		settingsBox.setAlignment(Pos.CENTER);
		settingsBox.getChildren().addAll(settings);
		topPane.getChildren().addAll(titleLabel, spacer, settingsBox);

		//Add content to leftPane
		monies = new Label("");
		clicker = createImgTTButton(loadImage("res/images/clicker.png", 36*scale), strings.getString("clickTT"));
		clicker.setOnAction(e -> MainApp.addWorker());
		leftPane.getChildren().addAll(monies, clicker);

		grid = new GridPane();
		grid.setHgap(0);
		grid.setVgap(0);
		grid.setGridLinesVisible(true);
		centerPane.setContent(grid);
		ColumnConstraints column = new ColumnConstraints();
		column.setHgrow(Priority.ALWAYS);
		grid.getColumnConstraints().add(column);

		column = new ColumnConstraints();
		grid.getColumnConstraints().add(column);
		grid.getColumnConstraints().add(column);
		grid.getColumnConstraints().add(column);
		
		grid.setStyle("-fx-background-image: url('file:res/images/mainBackground.png'); -fx-background-repeat: repeat; -fx-background-size: " + 16*scale + " " + 9*scale + ";");
		
		HBox.setHgrow(leftPane, Priority.ALWAYS);
		HBox.setHgrow(centerPane, Priority.SOMETIMES);

		buyWorker = createImgTTButton(loadImage("res/images/worker.png", 18*scale), strings.getString("buyWorkerTT"));
		//buyWorker.setOnAction(e -> buyWorker.setGraphic(loadImage("res/images/workerSpeed.png", 64)));	//TESTING PURPOSES
		buyWorker.setOnAction(e -> MainApp.addWorker());
		speedWorker = createImgTTButton(loadImage("res/images/workerSpeed.png", 18*scale), strings.getString("speedWorkerTT"));
		speedWorker.setOnAction(e -> MainApp.speedWorker());
		capWorker = createImgTTButton(loadImage("res/images/workerCap.png", 18*scale), strings.getString("capWorkerTT"));
		capWorker.setOnAction(e -> MainApp.capWorker());
		buyWorkerPrice = new Label("0.00M");
		speedWorkerPrice = new Label("0.00M");
		capWorkerPrice = new Label("0.00M");
		
		buyBag = createImgTTButton(loadImage("res/images/worker.png", 18*scale), strings.getString("buyBagTT"));
		buyBag.setOnAction(e -> MainApp.addWorker());
		speedBag = createImgTTButton(loadImage("res/images/workerSpeed.png", 18*scale), strings.getString("speedBagTT"));
		speedBag.setOnAction(e -> MainApp.addWorker());
		capBag = createImgTTButton(loadImage("res/images/workerCap.png", 18*scale), strings.getString("capBagTT"));
		capBag.setOnAction(e -> MainApp.addWorker());
		buyBagPrice = new Label("0.00M");
		speedBagPrice = new Label("0.00M");
		capBagPrice = new Label("0.00M");
		
		createMainRight();
	}

	public void createMainRight(){
		addItems(strings.getString("workers"), strings.getString("buyWorker"), strings.getString("speedWorker"), strings.getString("capWorker"), buyWorker, speedWorker, capWorker, buyWorkerPrice, speedWorkerPrice, capWorkerPrice, 0);

		addItems(strings.getString("bags"), strings.getString("buyBag"), strings.getString("speedBag"), strings.getString("capBag"), buyBag, speedBag, capBag, buyBagPrice, speedBagPrice, capBagPrice, 2);
	}

	public void addItems(String name, String buy, String speed, String cap, Button buyButton, Button speedButton, Button capButton, Label buyPrice, Label speedPrice, Label capPrice, int position){
		Label title = new Label(name);

		HBox titleBox = new HBox();
		titleBox.setAlignment(Pos.CENTER);
		titleBox.getChildren().addAll(title);
		grid.add(titleBox, 1, position, 3, 1);

		VBox buyBox = createItemVBox(buy, buyButton, buyPrice);
		grid.add(buyBox, 1, position+1);
		
		VBox speedBox = createItemVBox(speed, speedButton, speedPrice);
		grid.add(speedBox, 2, position+1);
		
		VBox capBox = createItemVBox(cap, capButton, capPrice);
		grid.add(capBox, 3, position+1);
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

	public VBox createItemVBox(String name, Button button, Label price){
		Label title = new Label(name);
		title.setPadding(new Insets(0, 0, -6, 0));

		VBox layout = new VBox();
		layout.setSpacing(scale);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(title, button, price);
		return layout;
	}

	/**
	public VBox createItemVBox(String name, String buy, String speed, String cap, Button buyButton, Button speedButton, Button capButton){
		VBox buyBox = createItemBox(buy, buyButton);
		VBox speedBox = createItemBox(speed, speedButton);
		VBox capBox = createItemBox(cap, capButton);
		Region spacer1 = new Region();
		Region spacer2 = new Region();

		HBox itemBox = new HBox();
		itemBox.setAlignment(Pos.CENTER);
		itemBox.getChildren().addAll(buyBox, spacer1, speedBox, spacer2, capBox);

		Label title = new Label(name);
		title.setPadding(new Insets(0, 0, -6, 0));

		VBox layout = new VBox();
		layout.setSpacing(scale);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(title, itemBox);
		return layout;
	}
	 */

	public VBox createItemBox(String action, Button button){
		Label actionLabel = new Label(action);
		actionLabel.setPadding(new Insets(0, 0, -6, 0));
		Label priceLabel = new Label("0.00M");
		priceLabel.setPadding(new Insets(0, 0, -6, 0));
		VBox subBox = new VBox();
		subBox.setSpacing(2*scale);
		subBox.setPadding(new Insets(2, 2, 2, 2));
		subBox.setAlignment(Pos.CENTER);
		subBox.getChildren().addAll(actionLabel, button, priceLabel);
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
