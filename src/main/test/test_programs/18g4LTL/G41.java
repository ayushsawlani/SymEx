
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.*;


public class G41 {		
	
		static void f_instrumented(int max_count, int a, int b,int c, int d) 
		{
			System.out.print("BEGIN->BB1 ");
			int grant1;
			int grant2;
			int light;

			int cstate1;
			int cstate2;
			int p0;
			int p1;
				
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
				int t1 = d;
		
				check1 = x + y;
				if(check1 > 3)
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
					System.out.print("BB9->BB10 ");
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
						System.out.print("BB14->BB27 ");
					}
					else 
					{
						System.out.print("D13->BB15 ");
						System.out.print("BB15->D16 ");
						if((p0==0)&&(p1==0))
						{
							System.out.print("D16->BB17 ");
							cstate1 = 0;
							grant1 = 1;
							grant2 = 0;
							System.out.print("BB17->BB26 ");
						}
						else
						{
							System.out.print("D16->BB18 ");
							System.out.print("BB18->D19 ");
							if((p0==0)&&(p1==1))
							{
								System.out.print("D19->BB20 ");
								cstate1 = 7;
								grant1 = 1;
								grant2 = 0;
								System.out.print("BB20->BB25 ");
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
									System.out.print("BB23->BB24 ");
								}
								else
								{
									System.out.print("D22->BB24 ");
								}
								System.out.print("BB24->BB25 ");
							}
							System.out.print("BB25->BB26 ");
						}
						System.out.print("BB26->BB27 ");
					}
					System.out.print("BB27->BB70 ");
				}

				else 
				{
					System.out.print("BB28->D29 ");
					System.out.print("-> ");
					if (cstate1 == 7)
					{
						System.out.print("D29->BB3O ");
						System.out.print("BB30->D31 ");
						if((p0==1)&&(p1==0))
						{
							System.out.print("D31->BB32 ");
							cstate1 = 9;
							grant1 = 0;
							grant2 = 1;
							System.out.print("BB32->BB45 ");
						}
						else 
						{
							System.out.print("D31->BB33 ");
							System.out.print("BB33->D34 ");
							if((p0==0)&&(p1==0))
							{
								System.out.print("D34->BB35 ");
								cstate1 = 0;
								grant1 = 0;
								grant2 = 1;
								System.out.print("BB35->BB44 ");
							}
							else 
							{
								System.out.print("D34->BB36 ");
								System.out.print("BB36->D37 ");
								if((p0==0)&&(p1==1))
								{
									System.out.print("D37->BB38 ");
									cstate1 = 7;
									grant1 = 0;
									grant2 = 1;
									System.out.print("BB38->BB43 ");
								}
								else 
								{
									System.out.print("D37-> BB39");
									System.out.print("BB39->D40 ");
									if((p0==1)&&(p1==1))
									{
										System.out.print("D40->BB41 ");
										cstate1 = 11;
										grant1 = 1;
										grant2 = 0;
										System.out.print("BB41->BB42 ");
									}
									System.out.print("BB42->BB43 ");
								}
								System.out.print("BB43->BB44 ");
							}
							System.out.print("BB44->BB45 ");
						}
						System.out.print("BB45->BB69 ");
					}
					else 
					{
						System.out.print("D29->BB46 ");
						System.out.print("BB46->D47 ");
						if (cstate1 == 9)
						{
							System.out.print("D47->BB48 ");
							System.out.print("BB48->D49 ");
							if((p0==1)&&(p1==0))
							{
								System.out.print("D49->BB50 ");
								cstate1 = 9;
								grant1 = 1;
								grant2 = 0;
								System.out.print("BB50->BB63 ");
							}
							else
							{
								System.out.print("D49->BB51 ");
								System.out.print("BB51->D52 ");
								if((p0==0)&&(p1==0))
								{
									System.out.print("D52->BB53 ");
									cstate1 = 0;
									grant1 = 1;
									grant2 = 0;
									System.out.print("BB53->BB62 ");
								}
								else 
								{
									System.out.print("D52->BB54 ");
									System.out.print("BB54->D55 ");
									if((p0==0)&&(p1==1))
									{
										System.out.print("D55->BB56 ");
										cstate1 = 7;
										grant1 = 1;
										grant2 = 0;
										System.out.print("BB56->BB61 ");
									}
									else
									{
										System.out.print("D55->BB57 ");
										System.out.print("BB57->D58 ");
										if((p0==1)&&(p1==1))
										{
											System.out.print("D58->BB59 ");
											cstate1 = 11;
											grant1 = 1;
											grant2 = 0;
											System.out.print("BB59->BB60 ");
										}
										System.out.print("BB60->BB61 ");
									}
									System.out.print("BB61->BB62 ");
								}
								System.out.print("BB62->BB63 ");
							}
							System.out.print("BB63->BB68 ");

						}
						else
						{
							System.out.print("D47->BB64 ");
							System.out.print("BB64->D65 ");
							if(cstate1==11)
							{
								System.out.print("D65->BB66 ");
								cstate1 = 11;
								grant1 = 1;
								grant2 = 0;
								System.out.print("BB66->BB67 ");
							}
							System.out.print("BB67->BB68 ");
						}
						System.out.print("BB68->BB69 ");
					
					}
					System.out.print("BB69->BB70 ");
				}
			System.out.print("BB70->D71 ");
			if(cstate2 == 0)
			{
				System.out.print("D71->BB72 ");
				System.out.print("BB72->D73 ");
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
				System.out.print("BB76->BB138 ");
			}
			else 
			{
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
					System.out.print("BB83->BB137 ");
				}
				else 
				{
					System.out.print("D78->BB84 ");
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
						System.out.print("BB90->BB136 ");
					}
					else
					{ 
						System.out.print("D85->BB91 ");
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
								System.out.print("BB95->BB104 ");
							}
							else 
							{
								System.out.print("D94->BB96 ");
								System.out.print("BB96->D97 ");
								if((error == 0)&&(t1 == 0))
								{
									System.out.print("D97->BB98 ");
									cstate2 = 396;
									light = 1;
									System.out.print("BB98->BB103 ");
								}
								else
								{
									System.out.print("D97->BB99 ");
									System.out.print("BB99->D100 ");
									if((error == 0)&&(t1 == 1))
									{
										System.out.print("D100->BB101 ");
										cstate2 = 43;
										light = 0;
										System.out.print("BB101->BB102 ");
									}
									System.out.print("BB102->BB103 ");
								}
								System.out.print("BB103->BB104 ");
							}
							System.out.print("BB104->BB135 ");
						}
						else 
						{
							System.out.print("D92->BB105 ");
							System.out.print("BB105->D106 ");
							if(cstate2 == 81)
							{
								System.out.print("D106->BB107 ");
								System.out.print("BB107->D108 ");
								if(error == 1)
								{
									System.out.print("D108->BB109 ");
									cstate2 = 12;
									light = 1;
									System.out.print("BB109->BB118 ");
								}
								else 
								{
									System.out.print("D018->BB110 ");
									System.out.print("BB110->D111 ");
									if((error == 0)&&(t1 == 0))
									{
										System.out.print("D111->BB112 ");
										cstate2 = 396;
										light = 1;
										System.out.print("BB112->BB117 ");
									}
									else
									{
										System.out.print("D111->BB113 ");
										System.out.print("BB113->D114 ");
										if((error == 0)&&(t1 == 1))
										{
											System.out.print("D114->BB115 ");
											cstate2 = 43;
											light = 0;
											System.out.print("BB115->BB116 ");
										}
										System.out.print("BB116->BB117 ");
									}
									System.out.print("BB117->BB118 ");
								}
								System.out.print("BB118->BB119 ");
							}
							else 
							{
								System.out.print("D106->BB119 ");
								System.out.print("BB119->D120 ");
								if(cstate2 == 12)
								{
									System.out.print("D120->BB121 ");
									System.out.print("BB121->D122 ");
									if(error == 1)
									{
										System.out.print("D122->BB123 ");
										cstate2 = 12;
										light = 1;
										System.out.print("BB123->BB132 ");
									}
									else 
									{
										System.out.print("D122->BB124 ");
										System.out.print("BB124->D125 ");
										if((error == 0)&&(t1 == 0))
										{
											System.out.print("D125->BB126 ");
											cstate2 = 81;
											light = 1;
											System.out.print("BB126->BB131 ");
										}
										else 
										{
											System.out.print("D125->BB127 ");
											System.out.print("BB127->D128 ");
											if((error == 0)&&(t1 == 1))
											{
												System.out.print("D128->BB129 ");
												cstate2 = 6;
												light = 0;
												System.out.print("BB129->BB130 ");
											}
											System.out.print("BB130->BB131 ");
										}
										System.out.print("BB131->BB132 ");
									}
									System.out.print("BB132->BB133 ");
								}
								System.out.print("BB133->BB134 ");
							}
							System.out.print("BB134->BB135 ");
						}
						System.out.print("BB135->BB136 ");
					}
					System.out.print("BB136->BB137 ");	
				}
				System.out.print("BB137->BB138 ");
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
	  
	  int count = 5;

	
	  Random random = new Random();
	  for(int i = 0; i < 500; i++) {
		  System.out.println();
		  int a = random.nextInt(2);
		  int b = random.nextInt(2);
		  int c = random.nextInt(2);
		  int d = random.nextInt(2);
		  
		//  System.out.println("Input-->"+a+b+c+d);
	      f_instrumented(count, a,b,c,d);
	   	  // f_original(24,100);
	  }  
	
	//int[] data = {10,20,30,40,50,60,71,80,90,91};


	
	
	  
	}

}

