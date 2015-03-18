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

import java.io.*;

import de.libalf.BasicAutomaton;
import de.libalf.Knowledgebase;
import de.libalf.LearningAlgorithm;
import de.libalf.LibALFFactory;
import de.libalf.LibALFFactory.Algorithm;
import de.libalf.jni.JNIFactory;

/**
 * <p>
 * This class demonstrates how to use online algorithms in libalf. In this
 * example, the user is queried for classified examples that are then used to
 * compute the final result.
 * </p>
 * 
 * <p>
 * This program takes the alphabet size as the input and produces the conjecture
 * as the output by requesting the user to classify the queries/words computed
 * in the knowledgebase. As the first step, a knowledgebase is created under the
 * JNIFactory. The user is then required to provide information about the
 * alphabet size of the automaton. Once this information is obtained, a learning
 * algorithm is created by passing the type of algorithm, empty knowledgebase,
 * and the alphabet size as the parameters. The learning algorithm advances
 * iteratively, checking at every step, if enough information exists in the
 * knowledgebase to construct a conjecture. If there was no enough information
 * to compute a conjecture, a list of words (called queries) that are to be
 * classifed by the user is produced. The words are presented to the user for
 * classification and this information is added to the knowledgebase. When
 * during an advance, if a conjecture was computed, then it is presented to the
 * user to check its equivalence. If the conjecture was correct, the file
 * "output_online.dot" containing the code for the automaton is created.
 * However, if the conjecture is incorrect, then the user is prompted to provide
 * a counter example which is used in furthur construction of the conjecture. In
 * this demo, we ANGLUIN algorithm is used.
 * </p>
 * 
 * <p>
 * Please refer to the {@link JalfOnlineLearningExample#main(String[])} method
 * for details on how to use libalf's onlinealgorithms. Other methods are used
 * for user interaction and not necessary for the actual learning.
 * </p>
 * 
 */
public class Online {
	/**
	 * A <code>BufferedReader<code> r to read the inputs given by the user.
	 */
	static BufferedReader r;

	/**
	 * A <code>String</code> to store the counter example when provided by the
	 * user.
	 */
	static String cstr;

	/**
	 * A <code>String</code> that stores the inputs provided by user in the
	 * console.
	 */
	static String reader;

	/**
	 * An <code>int</code> used to store the alphabet size provided by the user
	 * for the target language.
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
		help += "2) Answer the queries (simply follow the instructions on the screen).\n\n";
		help += "   Membership queries have to be classified as belonging to the target\n";
		help += "   language (1) or not (0).\n\n";
		help += "   Conjectures to equivalence queries are given in the Graphviz dot\n";
		help += "   format. You can use the dot tool to draw the automata.\n";

		System.out.println(help);
	}
	
	/**
	 * Method to obtain the information about the alphabet size from the user.
	 * 
	 * @return Returns the alphabet size as an <code>int</code>.
	 * @throws NumberFormatException
	 *             Exception produced when a non-integral input is provided.
	 * @throws IOException
	 *             Exception produced by failed or interrupted I/O operation
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
				System.out
						.println("Wrong input. Please enter value between 0 and 10.");
		} while (true);
	}

	/**
	 * Method to convert the word from <code>int</code> Array to
	 * <code>String</code>.
	 * 
	 * @param query
	 *            An <code>int</code> array that contains the word.
	 * @return A <code>String</code> which comprises the word.
	 */
	private static String word2string(int[] query) {
		String w = new String();
		if (query == null)
			return null;
		for (int i : query) {
			w += i;
		}
		return w;

	}

	/**
	 * Method to convert the words from type <code>String</code> to Array of
	 * <code>int</code>.
	 * 
	 * @param s
	 *            A <code>String</code> which contains the sample word entered
	 *            by the user.
	 * @return Returns an <code>int</code> array of the symbols.
	 */
	private static int[] string2word(String s) {
		if (s == null)
			return null;

		int[] result = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			result[i] = s.charAt(i) - '0';
			if (result[i] < 0 || result[i] > (alphabetsize - 1)) {
				result[0] = 10;
				return result;
			}
		}
		return result;
	}

	/**
	 * Method used to obtain information about the classification of a word. The
	 * user classifies the word by typing "y" for acceptance and "n" for
	 * rejection when displayed on the screen.
	 * 
	 * @param query
	 *            The word which must be classified.
	 * @return Returns true if user entered 'y'. Returns false if user entered
	 *         'n'.
	 * @throws IOException
	 *             Exception produced by failed or interrupted I/O Operation.
	 */
	private static boolean answer_Membership(int[] query) throws IOException {
		String str = new String();
		str = word2string(query);
		r = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print("Please classify the word '" + str + "' (0/1): ");
			reader = r.readLine();
			if (reader.equals("1"))
				return true;
			else if (reader.equals("0"))
				return false;
			else
				System.out.println("Wrong input. Enter '0' or '1'!");
		}

	}

	/**
	 * Method to check if the correct conjecture is computed. The conjecture is
	 * displayed to the user. The user classifies by entering 'y' for acceptance
	 * and 'n' for rejection.
	 * 
	 * @param conjecture
	 *            The conjecture that is computed after classification of words.
	 * @return Returns true if user entered 'y'. Returns false if user entered
	 *         'n'.
	 * @throws IOException
	 *             Exception produced by failed or interrupted I/O Operation.
	 */
	private static boolean check_Equivalence(BasicAutomaton conjecture)
			throws IOException {
		System.out.println("\nConjecture:\n");
		System.out.println(conjecture.toDot());
		System.out
				.print("\nEnter 'y' if the conjecture is correct, 'n' if conjecture is incorrect: ");
		while (true) {
			reader = r.readLine();
			if (reader.equals("y"))
				return true;
			else if (reader.equals("n"))
				return false;
			else
				System.out.println("Wrong input. Enter 'y' or 'n'!");
		}
	}

	/**
	 * Method used to obtain the counter example when a computed conjecture is
	 * rejected.
	 * 
	 * @return Returns the word obtained as counter example after converting it
	 *         to an array of <code>int</code>.
	 * @throws IOException
	 *             Exception produced by failed or interrupted I/O Operation.
	 */
	private static int[] get_CounterExample() throws IOException {

		int[] ce;
		do {
			System.out.print("Enter counter example: ");
			String word = r.readLine();
			ce = string2word(word);
			if (ce[0] == 10)
				System.out
						.println("Wrong Input. Counter Example does not conform to the alphabet size.");
			else
				return ce;
		} while (true);
	}

	/**
	 * The main Method
	 * 
	 * @throws IOException
	 *             Exception produced by failed or interrupted I/O Operation.
	 */
	public static void main(String[] args) throws IOException {

		// Print help message
		print_Help();
	
		/*
		 * A LibALFFactory called "factory" is obtained.
		 */
		LibALFFactory factory = JNIFactory.STATIC;

		/*
		 * A Knowledgebase called "knowledgebase" is created in the factory.
		 */
		Knowledgebase knowledgebase = factory.createKnowledgebase();

		/*
		 * Information about the size of the alphabet is obtained from the user.
		 */
		alphabetsize = get_AlphabetSize();

		/*
		 * A LearningAlgorithm is created from the Factory by passing three
		 * information. 1. The Algorithm to be used - Here we use the RPNI
		 * Algorithm. 2. The Knowledgebase - "base". 3. The Size of the Alphabet
		 * - "AlphabetSize".
		 */
		LearningAlgorithm algorithm = factory.createLearningAlgorithm(
				Algorithm.ANGLUIN, knowledgebase, alphabetsize);

		/*
		 * An BasicAutomaton automaton is created and initialized to null. The
		 * automaton is used to store the a conjecture which is marked correct
		 * by the user.
		 */
		BasicAutomaton automaton = null;

		/*
		 * The method "advance" is iterated in a loop which checks if there is
		 * enough information to formulate a conjecture. If there was no enough
		 * information for the same, the method creates a list of words that are
		 * to be classified by the user. The classification is then added to the
		 * knowledgebase. This information may either give enough knowledge to
		 * the algorithm to produce a conjecture or may produce more queries to
		 * be resolved. This is identified in the next iteration of "advance".
		 * 
		 * On the otherhand, When the algorithm has enough information to
		 * compute a conjecture, it is presented to the user to classify it as
		 * correct or incorrect. If marked correct, the conjecture is stored in
		 * a variable named "automaton" which is later used to construct the
		 * ".dot" output. If the conjecture is rejected, the algorithm requires a
		 * counter example from the user which will be used to construct further
		 * queries to formulate the conjecture.
		 */
		do {
			BasicAutomaton conjecture = (BasicAutomaton) algorithm.advance();

			if (!knowledgebase.get_queries().isEmpty()) {
				for (int[] query : knowledgebase.get_queries()) {
					boolean answer = answer_Membership(query);
					knowledgebase.add_knowledge(query, answer);

				}
			} else {
				if (check_Equivalence(conjecture))
					automaton = conjecture;
				else
					algorithm.add_counterexample(get_CounterExample());
			}
		} while (automaton == null);

		// Present result
		System.out.println("\nResult:\n\n" + automaton.toDot());
	}
}
