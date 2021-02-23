#include <stdio.h>

main()
{
	float A,B,C;
	float delta,i=sqrt(-1);
	float x1,x2,X;
	
			printf ("Your A=");
				scanf ("%f",&A);
			printf ("Your B=");
				scanf ("%f",&B);
			printf ("Your C=");
				scanf ("%f",&C);
			
					if(A==0){
					X=-C/B;
					printf("\n\nThe equation has only one answer and it is: %f\n\n\n",X);
				}
				else{
				   
			delta=(B*B)-(4*A*C);
			printf("\n\t\t\t(Delta=%f)\t\t\n",delta);//to see whether it's correct or not.
						
							if(delta==0){
				X=-B/(2*A);
				printf("\n\t\t\tX=%f\n\n",X);
									}
						
							if(delta>0){
				x1=(-B+sqrt(delta))/(2*A);
				x2=(-B-sqrt(delta))/(2*A);
				printf("\n\n\t\tX1=%f\tX2=%f\n\n\n\n",x1,x2);
									}
						
							if(delta<0){
				printf("\t\t(Answers are COMPLEX NUMBERS)\n\n");
				x1=-B+sqrt(-delta)/(2*A);
				x2=-B-sqrt(-delta)/(2*A);
				printf("\n\t\tX1=%fi\tX2=%fi\n\n\n\n",x1,x2);
									}
				}
				
	}