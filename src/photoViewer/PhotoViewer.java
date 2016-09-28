package photoViewer;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhotoViewer extends JFrame {
	private Model photos = new Model();
	private JMenuItem exit, maintain, browse;
	private Boolean isInBrowseMode = true;
	private JButton prevButton, nextButton, delete, add, save;
	private JTextArea descTextArea;
	private int picNum = 0;
	private JMenuBar menuBar;
	private ImageIcon image;
	private JScrollPane scrollPane;
	private JMenu fileMenu, viewMenu;
	private JLabel imageLabel, pictureCountLabel;
	private JTextField dateTextField, picNumTextField;

	public static void main(String[] args) {
		JFrame frame = new PhotoViewer();
		frame.pack();
		frame.setVisible(true);
	}

	public PhotoViewer() {
		this.setSize(400,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Photo Viewer");

		Container contentPane = getContentPane();

		makeMenuBar(this);

		imageLabel = new JLabel("", SwingConstants.CENTER);
		scrollPane = new JScrollPane(imageLabel);

		descTextArea = new JTextArea(4,20);
		descTextArea.setEnabled(false);
		dateTextField = new JTextField("   ");
		dateTextField.setEnabled(false);

		JPanel datePane = new JPanel();

		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel controlPane = new JPanel();
		controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.PAGE_AXIS));

		JPanel descriptionPane = new JPanel();
		descriptionPane.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel descLabel = new JLabel("Description:");
		descriptionPane.add(descLabel);
		descriptionPane.add(descTextArea);

		JLabel dateLabel = new JLabel("Date:");
		dateLabel.setPreferredSize(new Dimension(descLabel.getPreferredSize().width,dateLabel.getPreferredSize().height));
		datePane.add(dateLabel);
		datePane.add(dateTextField);

		ListenForButton lForButton = new ListenForButton();

		save = new JButton ("Save Changes");
		delete = new JButton ("Delete");
		add = new JButton ("Add Photo");
		add.addActionListener(lForButton);
		save.addActionListener(lForButton);
		delete.addActionListener(lForButton);

		save.setVisible(!isInBrowseMode);
		delete.setVisible(!isInBrowseMode);
		add.setVisible(!isInBrowseMode);

		JPanel buttonPane = new JPanel();
		buttonPane.add(delete);
		buttonPane.add(save);
		buttonPane.add(add);

		JPanel leftRightPane = new JPanel();
		leftRightPane.setLayout(new BorderLayout());
		leftRightPane.add(datePane,BorderLayout.WEST);
		leftRightPane.add(buttonPane,BorderLayout.EAST);

		JPanel southButtonPanel = new JPanel();
		picNumTextField = new JTextField(Integer.toString(picNum),4);
		picNumTextField.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				picNum = Integer.parseInt(picNumTextField.getText());
				setValuesOfFields();
			}
		});
		pictureCountLabel = new JLabel(" of " + Integer.toString(photos.getNumPhotos()));

		prevButton = new JButton ("<Prev");
		prevButton.addActionListener(lForButton);

		nextButton = new JButton ("Next>");
		nextButton.addActionListener(lForButton);

		southButtonPanel.add(picNumTextField);
		southButtonPanel.add(pictureCountLabel);
		southButtonPanel.add(prevButton);
		southButtonPanel.add(nextButton);
		FlowLayout flowLayout = (FlowLayout) southButtonPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		controlPane.add(descriptionPane);
		controlPane.add(leftRightPane);
		controlPane.add(southButtonPanel);

		contentPane.add(controlPane, BorderLayout.SOUTH); // Or PAGE_END

		setInitialStatus();
	}

	//Sets the initial UI status of the photoViewer. If there are photos already loaded into the program,
	//it will get the first photo. Otherwise, it will start with a blank slate
	private void setInitialStatus() {
		int numPhotos = photos.getNumPhotos();
		if (numPhotos <= 0) {
			image = null;
			nextButton.setEnabled(false);
		} else {
			picNum = 1;
			picNumTextField.setText(Integer.toString(picNum));
			image = photos.getImageIcon(0);
			imageLabel.setIcon(image);
			descTextArea.setText(photos.getDescription(0));
			dateTextField.setText(photos.getDate(0));
			if (numPhotos == 1)
				nextButton.setEnabled(false);
		}
		prevButton.setEnabled(false);
	}

	private void makeMenuBar (JFrame theBar) {
		menuBar = new JMenuBar ();
		fileMenu = new JMenu("File");
		viewMenu = new JMenu("View");

		exit = new JMenuItem ("Exit"); 
		ListenForMenuItem lForMenuItem = new ListenForMenuItem();
		exit.addActionListener(lForMenuItem);

		browse = new JMenuItem ("Browse");
		browse.addActionListener(lForMenuItem);
		maintain = new JMenuItem ("Maintain");
		maintain.addActionListener(lForMenuItem);

		fileMenu.add(exit);
		viewMenu.add(browse);
		viewMenu.add(maintain);

		menuBar.add(fileMenu);
		menuBar.add(viewMenu);

		theBar.setJMenuBar(menuBar);
	}

	//changes the image in the UI based on the current picNum
	private void changePic() {
		image = photos.getImageIcon(picNum-1);
		imageLabel.setIcon(image);
		descTextArea.setText(photos.getDescription(picNum-1));
		dateTextField.setText(photos.getDate(picNum-1));	
	}

	//this method is for setting that status (enabled/disabled) of the save, delete, and add buttons
	//and description text, date text, and current picNum fields
	private void setStatusOfComponents() {
		descTextArea.setEnabled(!isInBrowseMode);
		dateTextField.setEnabled(!isInBrowseMode);
		if (photos.getNumPhotos() > 1) {
			nextButton.setEnabled(isInBrowseMode);
			if (picNum > 1)
				prevButton.setEnabled(isInBrowseMode);
		}
		picNumTextField.setEnabled(isInBrowseMode);

		save.setVisible(!isInBrowseMode);
		delete.setVisible(!isInBrowseMode);
		add.setVisible(!isInBrowseMode);
	}

	//get the date, description, and image from the model at the current picNum
	private void setValuesOfFields() {
		picNumTextField.setText(Integer.toString(picNum));
		image = photos.getImageIcon(picNum-1);
		imageLabel.setIcon(image);
		descTextArea.setText(photos.getDescription(picNum-1));
		dateTextField.setText(photos.getDate(picNum-1));
		pictureCountLabel.setText("of " + Integer.toString(photos.getNumPhotos()));
	}

	private class ListenForButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == prevButton) {
				if (picNum > 1) {
					picNum--;
					changePic();
				}
				//disable previous button if there isn't a previous photo
				if (picNum <= 1) {
					prevButton.setEnabled(false);
				}
				nextButton.setEnabled(true);
			}
			if (e.getSource() == nextButton) {
				if (picNum < photos.getNumPhotos()) {
					picNum++;
					changePic();
				}
				//disable next button if there isn't a next photo
				if (picNum >= photos.getNumPhotos()) {
					nextButton.setEnabled(false);
				}
				prevButton.setEnabled(true);
			}
			picNumTextField.setText(Integer.toString(picNum));	
			if (e.getSource() == add) {
				//open up the dialog box for the user
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("."));
				fc.setDialogTitle("Choose a photo to add");
				fc.showOpenDialog(rootPane);
				//make sure they didn't push 'cancel' button
				if (fc.getSelectedFile() != null) {
					photos.addPhoto(picNum, fc.getSelectedFile().getPath());
					picNum++;
					setValuesOfFields();
					nextButton.setEnabled(false);
				}
			}
			//save the description and date of the picture that the user is currently on
			if (e.getSource() == save) {
				photos.saveChanges(picNum - 1, descTextArea.getText(), dateTextField.getText());
			}
			//delete the photo that the user is currently on
			if (e.getSource() == delete) {
				//make sure there is a photo to delete
				if (photos.deletePhoto(picNum - 1)) {
					int totalNumPhotos = photos.getNumPhotos();
					//if the user deleted the last photo, then the picNum should be 0
					if (totalNumPhotos == 0) {
						picNum = 0;
					}
					//as long as we're not at the first photo, we want to go to the previous photo.
					//Otherwise, we want to stay at the current photo
					if (picNum > 1)
						picNum--;
					setValuesOfFields();
				}
			}
		}
	}

	private class ListenForMenuItem implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == exit) {
				System.exit(0);
			}
			if (e.getSource() == maintain && isInBrowseMode) {
				isInBrowseMode = false;
				setStatusOfComponents();
			}
			if (e.getSource() == browse && !isInBrowseMode) {
				isInBrowseMode = true;
				setStatusOfComponents();
				//Reset the text of the description and date in case they didn't push the save button
				descTextArea.setText(photos.getDescription(picNum-1));
				dateTextField.setText(photos.getDate(picNum-1));
			}

		}

	}

}