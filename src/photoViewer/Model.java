package photoViewer;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Model {
	private ArrayList <Photo> photos;
	private SerializationOfPhoto sop = new SerializationOfPhoto();
	
	public Model () {
		photos = sop.deserialize("src/photoViewer/photos.ser");
		
	}
	public void addPhoto (int index, String pathOfPhoto) {
		Photo newPic = new Photo ("Description goes here", "Date goes here", pathOfPhoto);
		photos.add(index, newPic);
		sop.serialize(photos, "src/photoViewer/photos.ser");
	}
	public boolean deletePhoto(int numPicToDelete) {
		if (numPicToDelete < 0 || photos.isEmpty())
			return false;
		photos.remove(numPicToDelete);
		sop.serialize(photos, "src/photoViewer/photos.ser");
		return true;
	}
	
	public void saveChanges(int index, String desc, String date) {
		int numPics = photos.size();
		if (index < 0 || numPics == 0 || index >= numPics) 
			return;
		photos.get(index).setDescription(desc);
		photos.get(index).setDate(date);
		sop.serialize(photos, "src/photoViewer/photos.ser");
		
	}
	public int getNumPhotos() {
		return photos.size();
	}
	
	public String getDescription(int index) {
		int numPics = photos.size();
		if (index < 0 || numPics == 0 || index >= numPics)
			return null;
		return photos.get(index).getDescription();
	}
	public String getDate(int index) {
		int numPics = photos.size();
		if (index < 0 || numPics == 0 || index >= numPics)
			return null;
		return photos.get(index).getDate();
	}
	public ImageIcon getImageIcon(int index) {
		int numPics = photos.size();
		if (index < 0 || numPics == 0 || index >= numPics)
			return null;
		return photos.get(index).getImageIcon();
	}
	
}
