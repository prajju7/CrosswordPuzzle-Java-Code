import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution 
{
    // Checking if the word fits in Row 
    static boolean HoriChk(String[] crossword, int i, int j, String w)
    {
        boolean flag = true;
        int len = w.length();
        if( (len+j)>10)
        {
            return false;
        }
        if(j==0 && (len)<10)
        {
            if(crossword[i].charAt(len)!='+')
            return false;
        }
        else if(j>0 && (j+len)==10)
        {
            if(crossword[i].charAt(j-1)!='+')
            return false;
        }
        else if(j>0 && (j+len)<10)
        {
            if(crossword[i].charAt(j-1)!='+'  || crossword[i].charAt(j+len)!='+')
            {
                return false;
            }
        }
        for(int k=0;k<len;k++)
            {
                if(crossword[i].charAt(j+k)!='-' && crossword[i].charAt(j+k)!=w.charAt(k))
                return false;
            }
        
        
       return flag; 
       
    }
    
    // Checking if the word fits in Column 
    static boolean VertiChk(String[] crossword, int i, int j, String w)
    {
        boolean flag = true;
        int len = w.length();
        if( (len+i)>10)
        {
            return false;
        }
        if(i==0 && (len)<10)
        {
            if(crossword[len].charAt(j)!='+')
            return false;
        }
        else if(i>0 && (i+len)==10)
        {
            if(crossword[i-1].charAt(j)!='+')
            return false;
        }
        else if(i>0 && (i+len)<10)
        {
            if(crossword[i-1].charAt(j)!='+'  || crossword[i+len].charAt(j)!='+')
            {
                return false;
            }
        }
        for(int k=0;k<len;k++)
            {
                if(crossword[i+k].charAt(j)!='-' && crossword[i+k].charAt(j)!=w.charAt(k))
                {
                    return false;
                }
            }
        
        
       return flag;
    }
    
    // Placing the word in the Row
    static void placeHori(String[] crossword, int i, int j, String w)
    {
       // Code 
       for(int k =0; k<w.length();k++)
       {
           //String str = crossword[i];
           char ch = w.charAt(k);
           crossword[i] = crossword[i].substring(0, k+j)+ ch + crossword[i].substring(k+j + 1);
       }
    }
    
    // Placing the word in the column
    static void placeVerti(String[] crossword, int i, int j, String w)
    {
        //Code
        for(int k =0; k<w.length();k++)
       {
           //String str = crossword[i];
           char ch = w.charAt(k);
           crossword[i+k] = crossword[i+k].substring(0, j)+ ch + crossword[i+k].substring(j+1);
       }
    }
    
    //
    static String[] unplaceHori(String[] crossword,String word,int[] index)
    {
        int x = index[1];
        int y = index[2];
        for(int i=y;i<=word.length();i++)
        {
            crossword[x] = crossword[x].substring(0, i)+ '-' + crossword[x].substring(i+1);
        }
        index[0] = -1;
        index[1] = -1;
        index[2] = -1;
        return crossword;
    }
    
    static String[] unplaceVerti(String[] crossword,String word,int[] index)
    {
        int x = index[1];
        int y = index[2];
        for(int i=x;i<x+word.length();i++)
        {
            crossword[i] = crossword[i].substring(0, y)+ '-' + crossword[i].substring(y+1);
        }
        index[0] = -1;
        index[1] = -1;
        index[2] = -1;
        return crossword;
    }


    // Complete the crosswordPuzzle function below.
    static String[] crosswordPuzzle(String[] crossword, String[] words, int index, int[][] wp)
    {   
        //Base
        
        if(index>=words.length)
        {
            return crossword;
        }
        
        // Recursive Step
        String w = words[index];
        for(int i=0; i<10; i++)
        {
            for(int j=0; j<10; j++)
            {
                if(crossword[i].charAt(j) != '+')
                {
                    if(HoriChk(crossword,i,j,w))
                    {
                    
                        placeHori(crossword,i,j,w);
                        wp[index][0] = 0;
                        wp[index][1] = i;
                        wp[index][2] = j;
                        if((index+1) != words.length)
                        {
                            crossword = crosswordPuzzle(crossword,words,index+1,wp);
                        }
                    }
                    
                    else if(VertiChk(crossword,i,j,w))
                    {
                        
                        placeVerti(crossword,i,j,w);
                        wp[index][0] = 1;
                        wp[index][1] = i;
                        wp[index][2] = j;
                        if((index+1) != words.length)
                        {
                            crossword = crosswordPuzzle(crossword,words,index+1,wp);
                        }
                    }
                }
            }
        }
        
        //BACKTRACK 
        
        if(wp[index][0]==-1)
        {
            if(wp[index-1][0]==0)
            {
                crossword = unplaceHori(crossword,words[index-1],wp[index-1]);
            }
            if(wp[index-1][0]==1)
            {
                crossword = unplaceVerti(crossword,words[index-1],wp[index-1]);
            }
        }        
        return crossword;
    }
    
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] crossword = new String[10];

        for (int i = 0; i < 10; i++) {
            String crosswordItem = scanner.nextLine();
            crossword[i] = crosswordItem;
        }
        String words = scanner.nextLine();
        String w[] = words.split(";");
        int wp[][] = new int[w.length][3];
        for(int i =0;i<w.length;i++)
        for(int j=0;j<3;j++)
        wp[i][j] =-1;
        String[] result = crosswordPuzzle(crossword, w,0,wp);
        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(result[i]);

            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }
        bufferedWriter.newLine();
        bufferedWriter.close();
        scanner.close();
    }
}
