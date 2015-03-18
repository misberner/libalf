/*
 * This file is part of libalf.
 *
 * libalf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * libalf is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with libalf.  If not, see <http://www.gnu.org/licenses/>.
 *
 * (c) 2009 Lehrstuhl Logik und Theorie diskreter Systeme (I7), RWTH Aachen University
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import de.libalf.BasicAutomaton;
import de.libalf.Knowledgebase;
import de.libalf.LearningAlgorithm;
import de.libalf.LibALFFactory;
import de.libalf.LibALFFactory.Algorithm;
import de.libalf.jni.JNIFactory;

/**
 * <p>
 * This class demonstrates how to use offline algorithms in libalf. In this
 * example, the user acts as teacher that is queried for both membership and
 * equivalence queries.
 * </p>
 * 
 * <p>
 * The program takes the sample words and their classifications as the input,
 * computes the conjecture and produces a ".dot" file of the conjecture as the
 * output. 
 * In the beginning, knowledgebase is created under a JNIFactory. The
 * user is first required to mention the alphabet size. Then, the user is
 * requested to list all the sample words and classify them accordingly as
 * whether they have to be accepted or rejected by the automaton. This
 * information is simultaneously stored in the knowledgebase. After the user has
 * completed providing the samples, the learning algorithm is created with the
 * algorithm, the knowledgebase,and the alphabet size as parameters. The
 * learning algorithm is then made to advance by which it construct a conjecture
 * from the information available in the knowledgebase. This conjecture is then
 * subjected to the <code>todot()</code> method to formulate the "output.dot"
 * file containing the code to construct the automaton. In this demo, the RPNI
 * algorithm is used.
 * </p>
 * 
 * <p>
 * Please refer to the {@link JalfOfflineLearningExample#main(String[])} method
 * for details on how to use libalf's offline algorithms. Other methods are used
 * for user interaction and not necessary for the actual learning.
 * </p>
 */
public class Offline {

	/**
	 * A <code>BufferedReader</code> to read the inputs given by the user.
	 */
	static BufferedReader r;
	
	/**
	 *  An <code>Integer</code> to store the Alphabet Size.
	 */
	static int alphabetsize;

	/**
	* Prints a short information text.
	*/
	private static void print_Help() {
		String help = "\n";
		help += "This example demonstrates how to use libalf's online algorithms Using\n";
		help += "the JNI connection method. It puts the user, i.e. you, in the position\n";
		help += "of the teacher. Using libalf is easy:\n";
		help += "\n";
		help += "1) Input the size of the alphabet you want to use.\n\n";
		help += "   libalf uses integers as symbols. This means that a word is a sequence\n";
		help += "   of integers in the range between 0 and the size of the alphabet - 1.\n\n";
		help += "   For reasons of easier parsing, you are only allowed alphabets up to\n";
		help += "   ten symbols in this example (which is, however, no restriction in\n";
		help += "   general).\n\n";
		help += "2) Provide a list of classified words. A classification is either 1 (the\n";
		help += "   word has to be accepted) or 0 (the word has to be rejected).\n";

		System.out.println(help);
	}
	
	/**
	 * Method to obtain the information about the Alphabet size from the user.
	 * The alphabet size has to be between 1 and 9.
	 * 
	 * @return Returns the Alphabet size as an Integer.
	 * @throws NumberFormatException
	 *             Exception produced when a non-integral input is provided.
	 * @throws IOException
	 *             Exception produced by failed or interrupted I/O Operation
	 */
	private static int get_AlphabetSize() throws NumberFormatException,
			IOException {
		
		int i;
		do {
			System.out.print("Please input the alphabet size (between 1 and 10): ");
			r = new BufferedReader(new InputStreamReader(System.in));
			i = Integer.parseInt(r.readLine());
			if (i >= 1 && i <= 10)
				return i;
			else
				System.out.println("Wrong input. Please enter value between 1 and 10.");
		} while (true);
	}

	/**
	 * Method to obtain the samples from the user. The input sample obtained as
	 * <code>String</code> is converted to an array of <code>Integers</code>
	 * containing the alphabets. The latter is done by calling the "string2word"
	 * with the sample as a parameter
	 * 
	 * @return Returns an Integer Array of the alphabets which composes the
	 *         sample.
	 * @throws IOException
	 *             Exception produced by failed or interrupted I/O Operation.
	 */
	private static int[] get_Samples() throws IOException {
		
		int[] result;
		do{
			System.out.print("Enter a Word: ");
			String word = r.readLine();
			result = string2word(word);
			if (result[0]==10)
				System.out.println("Wrong Input. Sample does not conform to the alphabet size.");
			else
				return result;
		}while(true);
	}

	/**
	 * Method to obtain the classification of samples from the user.
	 * 
	 * @return Returns true if user classified the sample as 1. Returns false if
	 *         the user classified the sample as 0.
	 * @throws IOException
	 *             Exception produced by failed or interrupted I/O Operation.
	 */
	private static boolean get_Classification() throws IOException {
		
		do{
			System.out.print("Enter Classification (0/1): ");			
			String wc = r.readLine();
			if(wc.equals("1"))
				return true;
			else if(wc.equals("0"))
				return false;
			else
				System.out.println("Wrong Input. Please enter '1' to accept and '0' to reject.");
		}while(true);
	}

	/**
	 * Method to convert the samples as <code>String</code> to Array of
	 * <code>Integer</code>.
	 * 
	 * @param s
	 *            A string which contains the sample word entered by the user.
	 * @return Returns an <code>Integer</code> Array of the Alphabets.
	 */
	private static int[] string2word(String s) {
		
		if (s == null)
			return null;

		int[] result = new int[s.length()];
		for (int i = 0; i < s.length(); i++)
			{
				result[i] = s.charAt(i) - '0';
				if(result[i]<0 || result[i]> (alphabetsize - 1))
				{
					result[0] = 10;
					return result;
				}
			}
		return result;
	}

	private static String enough_Samples() throws IOException
	{
		String input;
		do{
			System.out.print("Do you want to input more samples? y/n : ");
			input = r.readLine();
			if(input.equals("y")||input.equals("n")) return input;
			else System.out.println("Wrong Input");
		}while(true);
	}

	/**
	 * The Main Method Program to Obtain sample words from user and produce the
	 * Automaton
	 */
	public static void main(String[] args) throws NumberFormatException,
			IOException {

		// Print help message
		print_Help();			
			
		/*
		 * A <code>String</code> to store information whether user wants to add
		 * more samples.
		 */
		String input = "y";

		/* 
		 * An Array of <code>Integers</code> which is used to store the sample
		 * the sample words.
		 */
		int[] words;

		/*
		 * A <code>boolean/<code> to store the classficiation of the sample
		 * word.
		 */ 
		boolean classification;

		/*
		 * A LibALFFactory called "factory" is created and set to be STATIC.
		 */
		LibALFFactory factory = JNIFactory.STATIC;

		/*
		 * A Knowledgebase under the name of "base" is created in the factory.
		 */
		Knowledgebase base = factory.createKnowledgebase();

		/*
		 * Information about the size of the alphabet is obtained from the user.
		 */
		alphabetsize = get_AlphabetSize();

		/*
		 * Samples and their classification are obtained from the user. "y" to
		 * be entered by the user if more samples must be added. "n" to be
		 * entered by the user if no more samples are to be added. The words are
		 * consequently added to the knowledgebase.
		 */
		while (input.equals("y")) {
			
			// Stores the sample entered by the user.
			words = get_Samples(); 
			
			// Stores the classification entered by the user.
			classification = get_Classification(); 
			
			// The sample is added to the knowledgebase.
			base.add_knowledge(words, classification); 
			
			// Information whether user wants to add more samples or not is obtained.
			input = enough_Samples();
		}

		/*
		 * A LearningAlgorithm is created from the Factory by passing three
		 * information. 1. The Algorithm to be used - Here we use the RPNI
		 * Algorithm. 2. The Knowledgebase - "base". 3. The Size of the Alphabet
		 * - "AlphabetSize".
		 */
		LearningAlgorithm algorithm = factory.createLearningAlgorithm(
				Algorithm.RPNI, base, alphabetsize);

		/*
		 * The Basic Automation from the given information is constructed. the
		 * method advance of the LearningAlgorithm creates the final conjecture.
		 */
		BasicAutomaton automaton = (BasicAutomaton) algorithm.advance();

		// Display result
		System.out.println("\nResult:\n\n" + automaton.toDot());
	}

}
