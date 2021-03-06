package edu.stanford.rsl.tutorial.basics.videoTutorials;

import edu.stanford.rsl.conrad.data.numeric.Grid2D;
import edu.stanford.rsl.conrad.utils.ImageUtil;
import ij.IJ;
import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.plugin.filter.Convolver;
import edu.stanford.rsl.conrad.filtering.MeanFilteringTool;
import edu.stanford.rsl.conrad.filtering.MedianFilteringTool;

public class videoTutorialsFilterTools {
	public static void main(String[] args) {
		
		String imgPath = System.getProperty("user.dir") + "/data" + "/mipda/";
		String filename = imgPath + "vessel.jpg";
 
		// Open the image using ImageJ
		ImagePlus imp = IJ.openImage(filename);
		
		// Wrap the ImagePlus instance to a Grid2D image
		Grid2D gridImage = ImageUtil.wrapImagePlus(imp).getSubGrid(0);
		
		// Grid2D images for saving the filtered images
		Grid2D filteredMean = new Grid2D(gridImage);
		Grid2D filteredMedian = new Grid2D(gridImage);
		
		
		
		// Mean filtering
		// Create a mean filter instance 
		MeanFilteringTool meanFilter = new MeanFilteringTool();
		
		// Create the mean filter and configure the kernel function
		meanFilter.configure(5,5);
		
		// Perform the filtering and save the filtered image
		meanFilter.applyToolToImage(filteredMean);
		
		// Display and compare the filtered image with the original one
		//gridImage.show("Original");
		//filteredMean.show("Mean filtered");
		
		
		// Median filtering
		// Create a median filter instance
		MedianFilteringTool medianFilter = new MedianFilteringTool();
		
		// Configure the kernel width and height
		medianFilter.configure(5,5);
		
		// Perform the filtering and 
		filteredMedian = medianFilter.applyToolToImage(gridImage);
		
		// Compare the result with the original image 
		//gridImage.show("Original");
		//filteredMean.show("Mean filtered");
		//filteredMedian.show("Median filtered");
		
		
		
		// Gaussian filtering
		// Using ImageJ to perform Gaussian filtering 
		IJ.run(imp, "Gaussian Blur...", "sigma=1.5");
		//gridImage.show("Original");
		//imp.show();
		
		
		
		// We can also create our own kernel function and then perform convolution
		// Set kernel width and height
		int kw = 3;
		int kh = 3;
		
		// Float array for storing the kernel data
		float[] kernel = new float[kw*kh];
		
		// Define the kernel
		for (int i = 0; i < kernel.length; ++i) {
			kernel[i] = 1.f/(kw*kh);
		}
		
		
		
		// Wrap the Grid2D image to FloaProcessor
		FloatProcessor ip = ImageUtil.wrapGrid2D(new Grid2D(gridImage));
		
		// Create a Convolver object
		Convolver conv = new Convolver();
		
		// Perform the convolution between the image and the previously defined kernel
		conv.convolve(ip, kernel, kw, kh);
		
		
		// Wrap FloatProcessor to Grid2D
		Grid2D convolvedImage = ImageUtil.wrapFloatProcessor(ip);
		
		// Show the images
		gridImage.show("Original");
		convolvedImage.show("Convolved Image");
		
		
		
		
	
	
	}
}
