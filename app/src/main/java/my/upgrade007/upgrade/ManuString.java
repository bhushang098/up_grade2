package my.upgrade007.upgrade;

public class ManuString {

    public  String getPerfectString (String rawString)
    {

        String mainString = rawString.substring(0,10);
        String perfectString = mainString.substring(8,10)+"-"+mainString.substring(5,7)+"-"+mainString.substring(0,4);
        return perfectString;
    }
}
