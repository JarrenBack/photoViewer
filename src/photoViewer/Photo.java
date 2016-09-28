package photoViewer;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String description, date;
	private ImageIcon image;
	
	Photo(String desc, String date, String fileLocation) {
		this.description = desc;
		this.date = date;
		this.image = new ImageIcon(fileLocation);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ImageIcon getImageIcon() {
		return image;
	}

	public void setImageIcon(String fileLocation) {
		this.image = new ImageIcon(fileLocation);
	}
	

}
