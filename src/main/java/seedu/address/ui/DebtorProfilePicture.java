package seedu.address.ui;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jaivigneshvenugopal
/**
 * Displays profile picture of each debtor
 */
public class DebtorProfilePicture extends UiPart<Region> {
    public static final String FXML = "DebtorProfilePicture.fxml";
    public static final String DEFAULT_INTERNAL_PROFILEPIC_FOLDER_PATH = "src/main/resources/images/profilePics/";
    public static final String DEFAULT_PROFILEPIC_PATH = "src/main/resources/images/profilePics/unknown.jpg";
    public static final String JPG_EXTENSION = ".jpg";

    @FXML
    private ImageView profilePic = new ImageView();

    public DebtorProfilePicture(ReadOnlyPerson person) {
        super(FXML);
        String imageName = person.getName().toString().replaceAll("\\s+", "");
        String imagePath = DEFAULT_PROFILEPIC_PATH;

        if (person.hasDisplayPicture()) {
            imagePath =  DEFAULT_INTERNAL_PROFILEPIC_FOLDER_PATH + imageName + JPG_EXTENSION;
        }

        File file = new File(imagePath);

        Image image = null;

        try {
            image = new Image(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        profilePic.setImage(image);
        profilePic.setFitWidth(350);
        profilePic.setFitHeight(350);
        registerAsAnEventHandler(this);
    }

    public ImageView getImageView() {
        return profilePic;
    }

}
