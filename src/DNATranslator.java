import java.util.Scanner;

/**
 * Created by navid on 1/4/16.
 */
public class DNATranslator
{
    public static void main(String[] args)
    {
        String inputDNASequence;
        Scanner scanInputDNASequence = new Scanner(System.in);

        System.out.println("Welcome to DNA-Translator");
		System.out.print("Please enter a valid DNA Sequence from 5' to 3': ");
        inputDNASequence = scanInputDNASequence.next();

        while(!isDNASequenceValid(inputDNASequence))
        {
            System.out.println("Invalid sequence. Note that a valid DNA Sequence consists of only nucleotides A,C,G,T and must be at least 6 nucleotides long.");
            System.out.print("Please enter a valid DNA Sequence: ");
            inputDNASequence = scanInputDNASequence.next();
        }

        AminoAcidGenerator aminoAcidGenerator = new AminoAcidGenerator(inputDNASequence);
    }

    public static boolean isDNASequenceValid(String DNASequence)
    {
        if(DNASequence == null || DNASequence.isEmpty() || (DNASequence.length() < 6))
            return false;

        for(int i = 0 ; i < DNASequence.length() ; i++)
        {
            char currentChar = DNASequence.charAt(i);
            if(!"ACTG".contains(Character.toString(currentChar)))
                return false;
        }

        return true;
    }
}
