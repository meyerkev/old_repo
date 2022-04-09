package com.sammiller;

import java.io.File;
import java.util.Date;
import java.util.Vector;

public class VocabWord {
	
	public final String wordName;
	
	public final String definition;
	
	private int imagePos;
	private int soundPos;
	
	private int upDownCounter;
	private int correctCounter;
	private int incorrectCounter;
	
	private Date dateDownloaded;
	private Date dateMastered;
	
	public VocabWord (String _wordName, String _definition, int _imagePos, int _soundPos, Date _dateDownloaded)
	{
		wordName = _wordName;
		definition = _definition;
		imagePos = _imagePos;
		soundPos = _soundPos;
		upDownCounter = 0;
		correctCounter = 0;
		incorrectCounter = 0;
		dateDownloaded = _dateDownloaded;
		dateMastered = null;
	}
	
	public VocabWord (String _wordName, String _definition, int _imagePos, int _soundPos, int _upDownCounter, int _correctCounter, int _incorrectCounter, Date _dateDownloaded, Date _dateMastered)
	{
		wordName = _wordName;
		definition = _definition;
		imagePos = _imagePos;
		soundPos = _soundPos;
		upDownCounter = _upDownCounter;
		correctCounter = _correctCounter;
		incorrectCounter = _incorrectCounter;
		dateDownloaded = _dateDownloaded;
		dateMastered = _dateMastered;
	}

	public void setImagePos(int imagePos) {
		this.imagePos = imagePos;
	}

	public int getImagePos() {
		return imagePos;
	}

	public void setSoundPos(int soundPos) {
		this.soundPos = soundPos;
	}

	public int getSoundPos() {
		return soundPos;
	}
	
	//TODO: create a DateMastered if upDownCounter exceeds "mastered" level
	
	public int registerCorrectAnswer()
	{
		if (upDownCounter >= 0) upDownCounter = upDownCounter+1;
		correctCounter = correctCounter+1;
		return upDownCounter;
	}
	
	public int registerIncorrectAnswer()
	{
		if (upDownCounter > 0) upDownCounter = upDownCounter-1;
		incorrectCounter = incorrectCounter+1;
		return upDownCounter;
	}
	
	public int getCorrectCounter() {
		return correctCounter;
	}

	public int getIncorrectCounter() {
		return incorrectCounter;
	}

	public Date getDateDownloaded() {
		return dateDownloaded;
	}

	public Date getDateMastered() {
		return dateMastered;
	}
	
	public static Vector<VocabWord> getSampleVocabWords(File sourceFile)
	{
		Vector<VocabWord> words = new Vector<VocabWord>();
		long l = 62049620;
		words.add(new VocabWord("intersect", "v. To cut through or into so as to divide.", 0, 0, 2, 3, 5, new Date(l), new Date(l)));
		words.add(new VocabWord("intestacy", "n. The condition resulting from one's dying not having made a valid will. ", 0, 0, 4, 5, 7, new Date(l), new Date(l)));
		words.add(new VocabWord("intestine", "n. That part of the digestive tube below or behind the stomach, extending to the anus. ", 0, 0, 4, 5, 8, new Date(l), new Date(l)));
		words.add(new VocabWord("intimidate", "v. To cause to become frightened. ", 0, 0, 5, 3, 6, new Date(l), new Date(l)));
		words.add(new VocabWord("intricate", "adj. Difficult to follow or understand. ", 0, 0, 3,5, 66, new Date(l), new Date(l)));
		return words;
	}

}