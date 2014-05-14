package WavFile;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ReadPianoSamples {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
		
			String out = "output.txt";
			FileWriter writer = new FileWriter(out);	
			
		// Open the wav file specified as the first argument
		HashMap<String, String> instruFiles = new HashMap<String,String>();
		for (int i=1; i <= 29; i++) {
			if (i != 7 && i != 8) {
				String s = "piano/pianosamp" + i + ".wav";
				instruFiles.put(s, "piano");
			}
		}
		
		for (int i=1; i <= 30; i++) {		
				String s = "sax/saxsamp" + i + ".wav";
				instruFiles.put(s, "sax");

		}
		
		for (int i=1; i <= 30; i++) {
				String s = "harp/harpsamp" + i + ".wav";
				instruFiles.put(s, "harp");
		}
		
		
		/*for (int i=1; i <= 16; i++) {		
			String s = "guitar/guitar2samp" + i + ".wav";
			instruFiles.put(s, "guitar"); 

	}  */
		
		
		List<String[]> data = new ArrayList<String[]>();
		
		for (String p: instruFiles.keySet()) {
		
		// Open the wav file specified 
		WavFile wavFile = WavFile.openWavFile(new File(p));

		// Display information about the wav file
		//wavFile.display();

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
					i = 0;
					while (i<60) {
					sumCoeffs += buffer1[i][k];
					i++;
					}
					buffer4[0][k] = sumCoeffs/60.0;
					}
			}
			
			if (i>= 60 && i< 120) {
				sumCoeffs = 0;
				for (int k=0; k<13; k++) {
					i = 60;
					while (i<120) {
					sumCoeffs += buffer1[i][k];
					i++;
					}
					buffer4[1][k] = sumCoeffs/60.0;
					}
			}
			
			if (i>= 120 && i< 180) {
				sumCoeffs = 0;
				for (int k=0; k<13; k++) {
					i = 120;
					while (i<180) {
					sumCoeffs += buffer1[i][k];
					i++;
					}
					buffer4[2][k] = sumCoeffs/60.0;
					}
			}
			
			if (i>= 180 && i< 240) {
				sumCoeffs = 0;
				for (int k=0; k<13; k++) {
					i = 180;
					while (i<240) {
					sumCoeffs += buffer1[i][k];
					i++;
					}
					buffer4[3][k] = sumCoeffs/60.0;
					}
			}
			
			if (i>=240 && i< 300) {
				sumCoeffs = 0;
				for (int k=0; k<13; k++) {
					i = 240;
					while (i<300) {
					sumCoeffs += buffer1[i][k];
					i++;
					}
					buffer4[4][k] = sumCoeffs/60.0;
					}
			}
			
			i++;
			
		}
		
		
		//add coefficients to string array
		for (double[] d: buffer4) {
			//int b = 0;
			//String[] buffer5 = new String[d.length+1];
			for (double dou: d) {
				String strD = Double.toString(dou);
				//buffer5[b] = strD;
				//b++;
				writer.write(strD + ",");
			}
			
			//buffer5[b] = instruFiles.get(p);
			writer.write(instruFiles.get(p) + "\n");
			//data.add(buffer5);
				}
		
		System.out.println(p + " has been scored.");
		
		}
		
		
		
		
		 
		writer.close();
		
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
		

	}

}
