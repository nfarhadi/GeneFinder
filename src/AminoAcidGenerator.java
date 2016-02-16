import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by navid on 1/5/16.
 */
public class AminoAcidGenerator
{
    public String DNASequence5to3, DNASequence3to5;
    public static final HashMap<String,String> DNAMap;

    static
    {
        DNAMap = new HashMap<String,String>();
        DNAMap.put("ATT","I");
        DNAMap.put("ATC","I");
        DNAMap.put("ATA","I");
        DNAMap.put("CTT","L");
        DNAMap.put("CTC","L");
        DNAMap.put("CTA","L");
        DNAMap.put("CTG","L");
        DNAMap.put("TTA","L");
        DNAMap.put("TTG","L");
        DNAMap.put("GTT","V");
        DNAMap.put("GTC","V");
        DNAMap.put("GTA","V");
        DNAMap.put("GTG","V");
        DNAMap.put("TTT","F");
        DNAMap.put("TTC","F");
        DNAMap.put("ATG","M");
        DNAMap.put("TGT","C");
        DNAMap.put("TGC","C");
        DNAMap.put("GCT","A");
        DNAMap.put("GCC","A");
        DNAMap.put("GCA","A");
        DNAMap.put("GCG","A");
        DNAMap.put("GGT","G");
        DNAMap.put("GGC","G");
        DNAMap.put("GGA","G");
        DNAMap.put("GGG","G");
        DNAMap.put("CCT","P");
        DNAMap.put("CCC","P");
        DNAMap.put("CCA","P");
        DNAMap.put("CCG","P");
        DNAMap.put("ACT","T");
        DNAMap.put("ACC","T");
        DNAMap.put("ACA","T");
        DNAMap.put("ACG","T");
        DNAMap.put("TCT","S");
        DNAMap.put("TCC","S");
        DNAMap.put("TCA","S");
        DNAMap.put("TCG","S");
        DNAMap.put("AGT","S");
        DNAMap.put("AGC","S");
        DNAMap.put("TAT","Y");
        DNAMap.put("TAC","Y");
        DNAMap.put("TGG","W");
        DNAMap.put("CAA","Q");
        DNAMap.put("CAG","Q");
        DNAMap.put("AAT","N");
        DNAMap.put("AAC","N");
        DNAMap.put("CAT","H");
        DNAMap.put("CAC","H");
        DNAMap.put("GAA","E");
        DNAMap.put("GAG","E");
        DNAMap.put("GAT","D");
        DNAMap.put("GAC","D");
        DNAMap.put("AAA","K");
        DNAMap.put("AAG","K");
        DNAMap.put("CGT","R");
        DNAMap.put("CGC","R");
        DNAMap.put("CGA","R");
        DNAMap.put("CGG","R");
        DNAMap.put("AGA","R");
        DNAMap.put("AGG","R");
        DNAMap.put("TAA","Z");
        DNAMap.put("TAG","Z");
        DNAMap.put("TGA","Z");
    }

    public String[] aminoAcidSequences; // 0: 5'-3' Frame 1, 1: 5'-3' Frame 2, 2: 5'-3' Frame 3, 3: 3'-5' Frame 1. 4: 3'-5' Frame 2, 5: 3'-5' Frame 3
    public Vector<Vector<String>> openReadingFrames; // 0: 5'-3' Frame 1, 1: 5'-3' Frame 2, 2: 5'-3' Frame 3, 3: 3'-5' Frame 1. 4: 3'-5' Frame 2, 5: 3'-5' Frame 3
    public static String[] frameInfo = {"5' to 3' Frame 1: ", "5' to 3' Frame 2: ", "5' to 3' Frame 3: ", "3' to 5' Frame 1: ", "3' to 5' Frame 2: ", "3' to 5' Frame 3: "};
    ORFContainer orfContainer;

    public AminoAcidGenerator(String inputDNASequence)
    {
        DNASequence5to3 = inputDNASequence;
        DNASequence3to5 = GenerateDNASequence3to5(DNASequence5to3);
        GenerateAminoAcidSequences();
        GenerateOpenReadingFrames();
        PrintData();
    }

    public void PrintData()
    {
        System.out.println("\nDNA sequence conversion to amino acids sequences:\n");

        for (int i = 0; i < 6; i++)
        {
            System.out.println(frameInfo[i] + aminoAcidSequences[i]);

            if (openReadingFrames.get(i).size() == 0)
            {
                System.out.println("No Open Reading Frames for this frame");
            }
            else
            {
                for (int j = 0; j < openReadingFrames.get(i).size(); j++)
                {
                    System.out.println("Open Reading Frame # " + (j+1) + ": " + openReadingFrames.get(i).get(j));
                }
            }

            System.out.println();
        }

        if (orfContainer.ORF.size() == 0)
        {
            System.out.println("There is no largest open reading frame because there are no open reading frames");
        }
        else if (orfContainer.ORF.size() == 1)
        {
            System.out.println("The largest open reading frame was found in " + orfContainer.frames.get(0) + " and it's length is " + orfContainer.ORF.get(0).length() + " amino acids:");
            System.out.println(orfContainer.ORF.get(0));
        }
        else
        {
            System.out.println("There were multiple largest open reading frames with length " + orfContainer.ORF.get(0).length() + " amino acids:");

            for (int i = 0; i < orfContainer.ORF.size(); i++)
            {
                System.out.println(orfContainer.frames.get(i) + ": " + orfContainer.ORF.get(i));
            }
        }

    }

    public void GenerateOpenReadingFrames()
    {
        openReadingFrames = new Vector<Vector<String>>();
        orfContainer = new ORFContainer();

        Boolean firstM = false;

        for (int i = 0; i < 6; i++)
        {
            Vector<String> tempVector = new Vector<String>();
            String tempString = "";
            tempVector.clear();

            for (int j = 0; j < aminoAcidSequences[i].length(); j++)
            {
                char c = aminoAcidSequences[i].charAt(j);

                if (c == 'M')
                {
                    if (firstM == true)
                    {
                        tempString += aminoAcidSequences[i].charAt(j);
                    }
                    else
                    {
                        tempString += aminoAcidSequences[i].charAt(j);
                        firstM = true;
                    }
                }
                else if (c == 'Z')
                {
                    if (firstM == true)
                    {
                        firstM = false;
                        tempVector.add(tempString);
                        orfContainer.add(tempString,frameInfo[i].substring(0,frameInfo[i].length()-2));
                        tempString = "";
                    }
                    else
                    {
                        continue;
                    }
                }
                else
                {
                    if (firstM == true)
                    {
                        tempString += aminoAcidSequences[i].charAt(j);
                    }
                    else
                    {
                        continue;
                    }
                }
            }
            firstM = false;
            openReadingFrames.add(tempVector);
        }

    }

    public String GenerateDNASequence3to5(String inputDNASequence)
    {
        return new StringBuilder(inputDNASequence.replace('A','X').replace('C','Y').replace('T','A').replace('G','C').replace('X','T').replace('Y','G')).reverse().toString();
    }

    public void GenerateAminoAcidSequences()
    {
        String tempSequence = null;
        String tempCodon = null;

        aminoAcidSequences = new String[6];
        Arrays.fill(aminoAcidSequences,"");

        // Case for 5'-3' Frame 1
        tempSequence = DNASequence5to3.substring(0,DNASequence5to3.length() - (DNASequence5to3.length() % 3));
        for (int i = 0 ; i <= tempSequence.length()-1 ; i = i+3)
        {
            aminoAcidSequences[0] += DNAMap.get(tempSequence.substring(i, i + 3));
        }

        // Case for 5'-3' Frame 2
        if ((DNASequence5to3.length() % 3) == 0)
            tempSequence = DNASequence5to3.substring(1,DNASequence5to3.length() - 2);
        else if ((DNASequence5to3.length() % 3) == 1)
            tempSequence = DNASequence5to3.substring(1,DNASequence5to3.length());
        else
            tempSequence = DNASequence5to3.substring(1,DNASequence5to3.length() - 1);
        for (int i = 0 ; i <= tempSequence.length()-1 ; i = i+3)
        {
            aminoAcidSequences[1] += DNAMap.get(tempSequence.substring(i, i + 3));
        }

        // Case for 5'-3' Frame 3
        if ((DNASequence5to3.length() % 3) == 0)
            tempSequence = DNASequence5to3.substring(2,DNASequence5to3.length() - 1);
        else if ((DNASequence5to3.length() % 3) == 1)
            tempSequence = DNASequence5to3.substring(2,DNASequence5to3.length() - 2);
        else
            tempSequence = DNASequence5to3.substring(2,DNASequence5to3.length());
        for (int i = 0 ; i <= tempSequence.length()-1 ; i = i+3)
        {
            aminoAcidSequences[2] += DNAMap.get(tempSequence.substring(i, i + 3));
        }

        // Case for 3'-5' Frame 1
        tempSequence = DNASequence3to5.substring(0,DNASequence3to5.length() - (DNASequence3to5.length() % 3));
        for (int i = 0 ; i <= tempSequence.length()-1 ; i = i+3)
        {
            aminoAcidSequences[3] += DNAMap.get(tempSequence.substring(i, i + 3));
        }


        // Case for 3'-5' Frame 2
        if ((DNASequence3to5.length() % 3) == 0)
            tempSequence = DNASequence3to5.substring(1,DNASequence3to5.length() - 2);
        else if ((DNASequence3to5.length() % 3) == 1)
            tempSequence = DNASequence3to5.substring(1,DNASequence3to5.length());
        else
            tempSequence = DNASequence3to5.substring(1,DNASequence3to5.length() - 1);
        for (int i = 0 ; i <= tempSequence.length()-1 ; i = i+3)
        {
            aminoAcidSequences[4] += DNAMap.get(tempSequence.substring(i, i + 3));
        }

        // Case for 3'-5' Frame 3
        if ((DNASequence3to5.length() % 3) == 0)
            tempSequence = DNASequence3to5.substring(2,DNASequence3to5.length() - 1);
        else if ((DNASequence3to5.length() % 3) == 1)
            tempSequence = DNASequence3to5.substring(2,DNASequence3to5.length() - 2);
        else
            tempSequence = DNASequence3to5.substring(2,DNASequence3to5.length());
        for (int i = 0 ; i <= tempSequence.length()-1 ; i = i+3)
        {
            aminoAcidSequences[5] += DNAMap.get(tempSequence.substring(i, i + 3));
        }
    }
}


