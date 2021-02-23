import java.io.*;
import java.util.*;

class crossword
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        boolean f = HoriChk(s,0,1,"LONDON");
        System.out.println(f);
    }
    
    static boolean HoriChk(String crossword, int i, int j, String w)
    {
        boolean flag = true;
        int len = w.length();
        if(crossword.charAt(j-1)=='+'  && crossword.charAt(j+len)=='+')
        {
            for(int k=0;k<len;k++)
            {
                if(crossword.charAt(j+k)!='-')
                flag = false;
            }
        }
        else
        {
            flag = false;
        }
        
       return flag; 
    }
}