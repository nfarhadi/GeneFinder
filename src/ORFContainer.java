import java.util.Vector;

/**
 * Created by navid on 2/15/16.
 */
public class ORFContainer
{
    public Vector<String> ORF;
    public Vector<String> frames;

    public ORFContainer()
    {
        ORF = new Vector<String>();
        frames = new Vector<String>();
        ORF.add("");
        frames.add("");
    }

    public void add(String input, String frame)
    {
        if(input.length() > ORF.get(0).length())
        {
            ORF.clear();
            frames.clear();
            ORF.add(input);
            frames.add(frame);
        }
        else if (input.length() == ORF.get(0).length())
        {
            ORF.add(input);
            frames.add(frame);
        }
        else
        {
            return;
        }
    }
}
