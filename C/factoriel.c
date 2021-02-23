#include <stdio.h>

main()
{
	float num,i,j;
	printf("Enter the number: ");
	scanf("%f",&num);
	j=num;
	
		if(num>=0){
		for(i=num-1; i>0; i--)
		num=num*i;
					}
	
		if(num<0){
		for(i=num+1; i<0; i++)
		num=num*i;
					}
				
	printf("\n\n\t\t%d!= %f\n\n\n",(int)j,num);
}
