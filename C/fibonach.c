#include <stdio.h>

int main()
{
	int a=1,b=1,k;
	int n,i;
	
	printf("Please enter the number of terms: ");//for n>45 it gives negative numbers.
	scanf("%d",&n);

		while(n<=0){
		printf("Please enter a valid number (number>0) : ");
		scanf("%d",&n);
					}
	
	if(n==1)
	printf("\nThe first number of Fibonacci sequence is: 1\n\n");
	
	if(n==2)
	printf("\n The first two numbers of Fibonacci sequence are: 1 - 2\n\n");
	
	if(n>=3){
		printf("\n\nThe first %d numbers of Fibonacci sequence are:\n\n %d - %d -",n,a,b);
			for(i=1; i<=n-2; i++){
			k=a+b;
				printf(" %d ",k);
			if(i<=n-3)//bikkarem dieh!!!
			printf(",");//separator
			a=b;
			b=k;
									}
	printf("\n\n\n");
				}
				
}
