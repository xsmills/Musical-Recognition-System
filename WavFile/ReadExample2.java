package WavFile;

import java.util.List;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadExample2 {
	
	public static void main(String[] args)
	{
	try
	{
	// Open the wav file specified as the first argument
	WavFile wavFile = WavFile.openWavFile(new File("piano/pianosamp8.wav"));

	// Display information about the wav file
	wavFile.display();

	// Get the number of audio channels in the wav file
	int numChannels = wavFile.getNumChannels();
	int framesRead = 0;
	long numFrames = wavFile.getNumFrames();
	double[] buffer = new double[400000 * numChannels];
	
	double[][] buffer1 = new double[304][13];
	framesRead = wavFile.readFrames(buffer, 400000);
	int j = 0;
	for (int i=0; i < 400000; i+=1320) {
			
		double[] buffer2 = Arrays.copyOfRange(buffer, i, i+1320);
		MFCC mf = new MFCC(13, 16000, 24, (int) Math.pow(2,12), true, 22, false);

		double[] buffer3 = mf.getParameters(buffer2);
		buffer1[j] = buffer3;
		j++;
		
		//for (double d: buffer3) {
			//System.out.print(d);
		//}
		//System.out.print("\n");
	}
	

	System.out.println("Wavfile sorted" + "\n");
	wavFile.close();
	
	int i = 0;
	double[][] buffer4 = new double[5][13]; 
	while (i<304) {
		double sumCoeffs = 0;
		if (i< 60) {
			sumCoeffs = 0;
			for (int k=0; k<13; k++) {
				while (i<60) {
				sumCoeffs += buffer1[i][k];
				i++;
				}
				buffer4[0][k] = sumCoeffs/13.0;
				}
		}
		
		if (i>= 60 && i< 120) {
			sumCoeffs = 0;
			for (int k=0; k<13; k++) {
				while (i<120) {
				sumCoeffs += buffer1[i][k];
				i++;
				}
				buffer4[1][k] = sumCoeffs/13.0;
				}
		}
		
		if (i>= 120 && i< 180) {
			sumCoeffs = 0;
			for (int k=0; k<13; k++) {
				while (i<180) {
				sumCoeffs += buffer1[i][k];
				i++;
				}
				buffer4[2][k] = sumCoeffs/13.0;
				}
		}
		
		if (i>= 180 && i< 240) {
			sumCoeffs = 0;
			for (int k=0; k<13; k++) {
				while (i<240) {
				sumCoeffs += buffer1[i][k];
				i++;
				}
				buffer4[3][k] = sumCoeffs/13.0;
				}
		}
		
		if (i>=240 && i< 300) {
			sumCoeffs = 0;
			for (int k=0; k<13; k++) {
				while (i<300) {
				sumCoeffs += buffer1[i][k];
				i++;
				}
				buffer4[4][k] = sumCoeffs/13.0;
				}
		}
		
		i++;
		
	}
	
	//String[] buffer5 = new String[5];
	List<String[]> data = new ArrayList<String[]>();
	
	for (double[] d: buffer4) {
		int b = 0;
		String[] buffer5 = new String[d.length];
		for (double dou: d) {
			String strD = Double.toString(dou);
			buffer5[b] = strD;
			b++;
		}
		data.add(buffer5);
			}
	
	String csv = "output.csv";
	CSVWriter writer = new CSVWriter(new FileWriter(csv));
	
	writer.writeAll(data);
	 
	writer.close();
	
	
	}
	catch (Exception e)
	{
		System.err.println(e);
	}
	}
	
	  /**
     * Return sample standard deviation of array, NaN if no such value.
     */
    public static double stddev(double[] a) {
        return Math.sqrt(var(a));
    }
    
    /**
     * Return sample variance of array, NaN if no such value.
     */
    public static double var(double[] a) {
        if (a.length == 0) return Double.NaN;
        double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (a.length - 1);
    }
    
    /**
     * Return average value in array, NaN if no such value.
     */
    public static double mean(double[] a) {
        if (a.length == 0) return Double.NaN;
        double sum = sum(a);
        return sum / a.length;
    }
    
    /**
     * Return sum of all values in array.
     */
    public static double sum(double[] a) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        return sum;
    }
}
	

