import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.*;



public class check{

	static void f_instrumented(int max_count, int a, int b,int c) {
	int grant1;
	int grant2;
	int light;

	int cstate1;
	int cstate2;
	int p0;
	int p1;
	int t1=0;
	
	cstate1 = 0;
	cstate2 = 0;
	int count = 0;

	int check1;
	int check2;
	int check20;
	int check21;
	while(count<max_count)
	{
		int x = a;
		int y = b;
		int error = c;
		
		check1 = x + y;

		if(check1>3)
		{
			p0 = 1;
		}
		else
		{
			p0 = 0;
		}

		check20 = x * x;
		check21 = y * y;
		check2 = check20 + check21;
		if(check2 < 4)
		{
			p1 = 1;
		}
		else
		{
			p1 = 0;
		}

		if (cstate1 == 0)
		{
			if((p0==1)&&(p1==0))
			{
				cstate1 = 9;
				grant1 = 1;
				grant2 = 0;
			}
			if((p0==0)&&(p1==0))
			{
				cstate1 = 0;
				grant1 = 1;
				grant2 = 0;
			}
			if((p0==0)&&(p1==1))
			{
				cstate1 = 7;
				grant1 = 1;
				grant2 = 0;
			}
			if((p0==1)&&(p1==1))
			{
				cstate1 = 11;
				grant1 = 1;
				grant2 = 0;
			}
			
		}

		if (cstate1 == 7)
		{
			if((p0==1)&&(p1==0))
			{
				cstate1 = 9;
				grant1 = 0;
				grant2 = 1;
			}
			if((p0==0)&&(p1==0))
			{
				cstate1 = 0;
				grant1 = 0;
				grant2 = 1;
			}
			if((p0==0)&&(p1==1))
			{
				cstate1 = 7;
				grant1 = 0;
				grant2 = 1;
			}
			if((p0==1)&&(p1==1))
			{
				cstate1 = 11;
				grant1 = 1;
				grant2 = 0;
			}
			

		}

		if (cstate1 == 9)
		{
			if((p0==1)&&(p1==0))
			{
				cstate1 = 9;
				grant1 = 1;
				grant2 = 0;
			}
			if((p0==0)&&(p1==0))
			{
				cstate1 = 0;
				grant1 = 1;
				grant2 = 0;
			}
			if((p0==0)&&(p1==1))
			{
				cstate1 = 7;
				grant1 = 1;
				grant2 = 0;
			}
			if((p0==1)&&(p1==1))
			{
				cstate1 = 11;
				grant1 = 1;
				grant2 = 0;
			}
		}
		if(cstate1 == 11)
		{
			cstate1 = 11;
			grant1 = 1;
			grant2 = 0;
		}

		

		if(cstate2 == 0)
		{
			if(error == 1)
			{
				cstate2 = 12;
				light = 1;
			}
			else
			{
				cstate2 = 6;
				light = 0;
			}
		}
		if(cstate2 == 43)
		{
			if(error == 1)
			{
				cstate2 = 12;
				light = 1;
			}
			else
			{
				cstate2 = 43;
				light = 0;
			}
		}
		if(cstate2 == 6)
		{
			if(error == 1)
			{
				cstate2 = 12;
				light = 1;
			}
			else
			{
				cstate2 = 6;
				light = 0;
			}
		}
		if(cstate2 == 396)
		{
			if(error == 1)
			{
				cstate2 = 12;
				light = 1;
			}
			if((error == 0)&&(t1 == 0))
			{
				cstate2 = 396;
				light = 1;
			}
			if((error == 0)&&(t1 == 1))
			{
				cstate2 = 43;
				light = 0;
			}
			
		}
		if(cstate2 == 81)
		{
			if(error == 1)
			{
				cstate2 = 12;
				light = 1;
			}
			if((error == 0)&&(t1 == 0))
			{
				cstate2 = 396;
				light = 1;
			}
			if((error == 0)&&(t1 == 1))
			{
				cstate2 = 43;
				light = 0;
			}
			
		}
		if(cstate2 == 12)
		{
			if(error == 1)
			{
				cstate2 = 12;
				light = 1;
			}
			if((error == 0)&&(t1 == 0))
			{
				cstate2 = 81;
				light = 1;
			}
			if((error == 0)&&(t1 == 1))
			{
				cstate2 = 6;
				light = 0;
			}
			
		}

	}

}






public static void main(String args[])throws FileNotFoundException  {

	//File file = new File("D:\\samples     C:\\symtest-feature-heuristic\\symtest-feature-heuristic\\src\\main\\test\\test_programs\\5\\file.txt");
	File file = new File("C:\\Symtest\\symtest\\src\\main\\test\\test_programs\\18g4LTL\\runs.txt");
	FileOutputStream fos = new FileOutputStream(file);
        PrintStream o = new PrintStream(fos); 
  	System.setOut(o); 
	  
	  int count = 10;

	
	  Random random = new Random();
	  for(int i = 0; i < 500; i++) {
		  System.out.println(); 
		  int a = random.nextInt(2);
		  int b = random.nextInt(2);
		  int c = random.nextInt(2);
		  
		  System.out.println("Input-->"+a+b+c);
	    	  f_instrumented(count, a,b,c);
	  }  
	  
	}

}

