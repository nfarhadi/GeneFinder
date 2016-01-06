import java.util.HashMap;

/**
 * Created by navid on 1/5/16.
 */
public class AminoAcidGenerator
{
    public String DNASequence5to3, DNASequence3to5;
    public static HashMap<String,String> DNAMap;

    public AminoAcidGenerator(String inputDNASequence)
    {
        GenerateDNAMap();
        DNASequence5to3 = inputDNASequence;
        DNASequence3to5 = GenerateDNASequence3to5(DNASequence5to3);
    }

    public String GenerateDNASequence3to5(String inputDNASequence)
    {
        return inputDNASequence.replace('A','X').replace('C','Y').replace('T','A').replace('G','C').replace('X','T').replace('Y','G');
    }

    public static void GenerateDNAMap()
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
        DNAMap.put("TAA","*");
        DNAMap.put("TAG","*");
        DNAMap.put("TGA","*");
    }
}


