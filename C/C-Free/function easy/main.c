#include <stdio.h>

main()
{
	float A,B,C;
	float Delta,X;
	float X1,X2;
	printf("Your A: ");
	scanf("%f",&A);//A is pos
	printf("Your B: ");
	scanf("%f",&B);
	printf("Your C: ",C);
	scanf("%f",&C);
	
	Delta=B*B-4*A*C;
	printf("Delta= %f\n",Delta);//to see whether it's correct or not.
	
	if(Delta<0)
	printf("Delta<0 and equation has no answer\n");
	if(Delta==0){
		X=-B/(2*A);
		printf("Equation has only one answer: %f\n",X);
		
	}
	if(Delta>0){
		X1=(-B+sqrt(Delta))/(2*A);
		X2=(-B-sqrt(Delta))/(2*A);
		printf("X1=%f\tX2=%f\n",X1,X2);
	}
	
	return 0;
}
