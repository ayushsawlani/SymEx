package test;

import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.*;


public class g4 {		
	
		static void f_instrumented(int max_count, int a, int b,int c) {
		System.out.print("BEGIN->BB1 ");
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

		int check1;
		int check2;
		int check20;
		int check21;	
		int count = 0;
		System.out.print("BB1->BB2 ");
		while((true)&&(count<max_count))
		{
			System.out.print("BB2->D3 ");
			int x = a;
			int y = b;
			int error = c;
			
			check1 = x + y;
			if(check1>3)
			{
				System.out.print("D3->BB4 ");
				p0 = 1;
				System.out.print("BB4->BB6 ");
			}
			else
			{
				System.out.print("D3->BB5 ");
				p0 = 0;
				System.out.print("BB5->BB6 ");
			}
			check20 = x * x;
			check21 = y * y;
			check2 = check20 + check21;
			System.out.print("BB6->D7 ");
			if(check2 < 4)
			{
				System.out.print("D7->BB8 ");
				p1 = 1;
				System.out.print("BB8->BB10 ");
			}
			else
			{
				System.out.print("D7->BB9 ");
				p1 = 0;
				System.out.print("BB9-BB10> ");
			}
			System.out.print("BB10->D11 ");
			if (cstate1 == 0)
			{
				System.out.print("D11->BB12 ");
				System.out.print("BB12->D13 ");
				if((p0==1)&&(p1==0))
				{
					System.out.print("D13->BB14 ");
					cstate1 = 9;
					grant1 = 1;
					grant2 = 0;
					System.out.print("BB14->BB28 ");
				}
				else{
					System.out.print("D13->BB15 ");
					}
					System.out.print("BB15->D16 ");
					if((p0==0)&&(p1==0))
					{
						System.out.print("D16->BB17 ");
						cstate1 = 0;
						grant1 = 1;
						grant2 = 0;
						System.out.print("BB17->BB27 ");
					
					}
				else
				{
					System.out.print("D16->BB18 ");
					
				}
				System.out.print("BB18->D19 ");
				if((p0==0)&&(p1==1))
				{
					System.out.print("D19->BB20 ");
					cstate1 = 7;
					grant1 = 1;
					grant2 = 0;
					System.out.print("BB20->BB26 ");
					
				}
				else
				{
					System.out.print("D19->BB21 ");
					
				
				System.out.print("BB21->D22 ");
				if((p0==1)&&(p1==1))
				{
					System.out.print("D22->BB23 ");
					cstate1 = 11;
					grant1 = 1;
					grant2 = 0;
					System.out.print("BB23->BB25 ");
				}
				else
				{
					System.out.print("D22->BB24 ");
					System.out.print("BB24->BB25 ");
				}
				System.out.print("BB25->BB26 ");
				}
				System.out.print("BB26->BB27 ");
				System.out.print("BB27->BB28 ");
				System.out.print("D11->BB29 ");
				System.out.print("BB29->D30 ");
			}
			else if (cstate1 == 7)
			{
				System.out.print("D30->BB31 ");
				System.out.print("BB31->D32 ");
				if((p0==1)&&(p1==0))
				{
					System.out.print("D32->BB33 ");
					cstate1 = 9;
					grant1 = 0;
					grant2 = 1;
					System.out.print("BB33->BB47 ");
					System.out.print("D32->BB34 ");
					System.out.print("BB34->D35 ");
				}
				else if((p0==0)&&(p1==0))
				{
					System.out.print("D35->BB36 ");
					cstate1 = 0;
					grant1 = 0;
					grant2 = 1;
					System.out.print("BB36->BB46 ");
					System.out.print("D35->BB37 ");
					System.out.print("BB37->D38 ");
				
				}
				else if((p0==0)&&(p1==1))
				{
					System.out.print("D38->BB39 ");
					cstate1 = 7;
					grant1 = 0;
					grant2 = 1;
					System.out.print("BB39->BB45 ");
					System.out.print("D38->BB40 ");
					System.out.print("BB40->D41 ");
				
				}
				else if((p0==1)&&(p1==1))
				{
					System.out.print("D41->BB42 ");
					cstate1 = 11;
					grant1 = 1;
					grant2 = 0;
					System.out.print("BB42->BB44 ");
				}
				else
				{
					System.out.print("D41->BB43 ");
					System.out.print("BB43->BB44 ");
				}
				System.out.print("BB44->BB45 ");
				System.out.print("BB45->BB46 ");
				System.out.print("BB46->BB47 ");
				System.out.print("D30->BB48 ");
				System.out.print("BB48->D49 ");
			}
			
			else if (cstate1 == 9)
			{
				System.out.print("D49->BB50 ");
				System.out.print("BB50->D51 ");
				if((p0==1)&&(p1==0))
				{
					System.out.print("D51->BB52 ");
					cstate1 = 9;
					grant1 = 1;
					grant2 = 0;
					System.out.print("BB52->BB66 ");
					System.out.print("D51->BB53 ");
					System.out.print("BB53->D54 ");
				}
				
				else if((p0==0)&&(p1==0))
				{
					System.out.print("D54->BB55 ");
					cstate1 = 0;
					grant1 = 1;
					grant2 = 0;
					System.out.print("BB55->BB65 ");
					System.out.print("D54->BB56 ");
					System.out.print("BB56->D57 ");
				}
				
				else if((p0==0)&&(p1==1))
				{
					System.out.print("D57->BB58 ");
					cstate1 = 7;
					grant1 = 1;
					grant2 = 0;
					System.out.print("BB58->BB64 ");
					System.out.print("D57->BB59 ");
					System.out.print("BB59->D60 ");
				}
				
				else if((p0==1)&&(p1==1))
				{
					System.out.print("D60->BB61 ");
					cstate1 = 11;
					grant1 = 1;
					grant2 = 0;
					System.out.print("BB61->BB63 ");
				}
				else
				{
					System.out.print("D60->BB62 ");
					System.out.print("BB62->BB63 ");
				}
				System.out.print("BB64->BB65 ");
				System.out.print("BB65->BB66 ");
				System.out.print("BB66->BB68 ");
		}
		else
		{
			System.out.print("D49->BB67 ");
			cstate1 = 11;
			grant1 = 1;
			grant2 = 0;
			System.out.print("BB67->BB68 ");
		}
		System.out.print("-> ");
		System.out.print("-> ");
		if(cstate2 == 0)
		{
			System.out.print("-> ");
			if(error == 1)
			{
				System.out.print("D73->BB74 ");
				cstate2 = 12;
				light = 1;
				System.out.print("BB74->BB76 ");
			}
			else
			{
				System.out.print("D73->BB75 ");
				cstate2 = 6;
				light = 0;
				System.out.print("BB75->BB76 ");
			}
		}
		System.out.print("D71->BB77 ");
		System.out.print("BB77->D78 ");
		if(cstate2 == 43)
		{
			System.out.print("D78->BB79 ");
			System.out.print("BB79->D80 ");
			if(error == 1)
			{
				System.out.print("D80->BB81 ");
				cstate2 = 12;
				light = 1;
				System.out.print("BB81->BB83 ");
			}
			else
			{
				System.out.print("D80->BB82 ");
				cstate2 = 43;
				light = 0;
				System.out.print("BB82->BB83 ");
			}
			System.out.print("BB83->BB84 ");
		}
		System.out.print("BB84->D85 ");
		if(cstate2 == 6)
		{
			System.out.print("D85->BB86 ");
			System.out.print("BB86->D87 ");
			if(error == 1)
			{
				System.out.print("D87->BB88 ");
				cstate2 = 12;
				light = 1;
				System.out.print("BB88->BB90 ");
			}
			else
			{
				System.out.print("D87->BB89 ");
				cstate2 = 6;
				light = 0;
				System.out.print("BB89->BB90 ");
			}
			System.out.print("BB90->BB91 ");
		}
		System.out.print("BB91->D92 ");
		if(cstate2 == 396)
		{
			System.out.print("D92->BB93 ");
			System.out.print("BB93->D94 ");
			if(error == 1)
			{
				System.out.print("D94->BB95 ");
				cstate2 = 12;
				light = 1;
				System.out.print("BB95->BB105 ");
				System.out.print("D94->BB96 ");
			 	System.out.print("BB96->D97 ");
			}
			
			else if((error == 0)&&(t1 == 0))
			{
				System.out.print("D97->BB98 ");
				cstate2 = 396;
				light = 1;
				System.out.print("BB98->BB104 ");
				System.out.print("D97->BB99 ");
				System.out.print("BB99->D100 ");
			}
			
			else if((error == 0)&&(t1 == 1))
			{
				System.out.print("D100->BB101 ");
				cstate2 = 43;
				light = 0;
				System.out.print("BB101->BB103 ");
			}
			else
			{
				System.out.print("D100->BB102 ");
				System.out.print("BB102->BB103 ");	
			}
			System.out.print("BB103->BB104 ");
		}
		System.out.print("BB104->BB105 ");
		System.out.print("BB105->BB106 ");
		System.out.print("BB106->D107 ");
		if(cstate2 == 81)
		{
			System.out.print("D107->BB108 ");
			System.out.print("BB108->D109 ");
			if(error == 1)
			{
				System.out.print("D109->BB110 ");
				cstate2 = 12;
				light = 1;
				System.out.print("BB110->BB120 ");
				System.out.print("D109->BB111 ");
				System.out.print("BB111->D112 ");
			
			}
			else if((error == 0)&&(t1 == 0))
			{
				System.out.print("D112->BB113 ");
				cstate2 = 396;
				light = 1;
				System.out.print("BB113-> BB119");
				System.out.print("D112->BB114 ");
				System.out.print("BB114->D115 ");
			
			}
			else if((error == 0)&&(t1 == 1))
			{
				System.out.print("D115->BB116 ");
				cstate2 = 43;
				light = 0;
				System.out.print("BB116->BB118 ");
			}
			else
			{
				System.out.print("D115->BB117 ");
				System.out.print("BB117->BB118 ");
			}
		}
		System.out.print("BB118->BB119 ");
		System.out.print("BB120->BB121 ");
		System.out.print("BB121->D122 ");
		if(cstate2 == 12)
		{
			System.out.print("D122->BB123 ");
			System.out.print("BB123->D124 ");
			if(error == 1)
			{
				System.out.print("D124->BB125 ");
				cstate2 = 12;
				light = 1;
				System.out.print("BB125->BB135 ");
			else{
				System.out.print("D124->BB126 ");
				System.out.print("BB126->D127 ");
			
			 if((error == 0)&&(t1 == 0))
			{
				System.out.print("D127->BB128 ");
				cstate2 = 81;
				light = 1;
				System.out.print("BB128->BB134 ");
			else{
				System.out.print("D127->BB129 ");
				System.out.print("BB129->D130 ");
			
			if((error == 0)&&(t1 == 1))
			{
				System.out.print("D130->BB131 ");
				cstate2 = 6;
				light = 0;
				System.out.print("BB131->BB133 ");
			}
			else
			{
				System.out.print("D130->BB132 ");
				System.out.print("BB132->BB133 ");
			}

			System.out.print("BB133->BB134 ");
			}
			System.out.print("BB134->BB135 ");
			}
			System.out.print("BB135->BB136 ");
		}
		System.out.print("BB136->WHILE->BB2 ");
		count++;
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
	   	  // f_original(24,100);
	  }  
	
	//int[] data = {10,20,30,40,50,60,71,80,90,91};


	/*ArrayList<Integer> input = new ArrayList<Integer>();
        List<Integer> namesList = Arrays.asList( 46, 53, 51, 20, 67, 59, 70, 78, 50, 30, 24, 43, 26, 38, 35, 18, 13, 11, 92, 31, 0, 102, 2147483647);
    	input.addAll(namesList);
	
	 for(int i = 0; i < input.size() ; i++) {
		  System.out.println(); 
		  System.out.println(input.get(i));
		  int x = input.get(i);
	    	  //System.out.println(x);
	    	  f_instrumented(count, x);
	   	  // f_original(24,100);
	  }*/
	
	  
	}

}

