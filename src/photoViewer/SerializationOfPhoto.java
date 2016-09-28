package photoViewer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerializationOfPhoto {
	public void serialize(ArrayList<Photo> photos, String fileName) {
		try ( ObjectOutputStream out = 
				new ObjectOutputStream(new FileOutputStream(fileName))) {
			out.writeObject(photos);
		} catch (IOException ex) {
			System.out.print("IOException occured during serialization");
		}
	}
	
	public ArrayList<Photo> deserialize(String fileName) {
		ArrayList<Photo> photos = null;
		try ( ObjectInputStream in = 
				new ObjectInputStream(new FileInputStream(fileName))) {
			photos = (ArrayList<Photo>) in.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Excepiton occured during deserialization");
		}
		return photos;
	}
}
