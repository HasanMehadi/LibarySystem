package ICONMANAGER;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LibraryAssistantIconManager {
    public static final String IMAGE_ICON="UI/icons/library.jpg";

    public static void setStageIcon(Stage stage){
        stage.getIcons().add(new Image(IMAGE_ICON));
    }
}
