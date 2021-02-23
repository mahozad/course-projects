#include <stdio.h>

int main()
{
	int n,i=2,num,j=0;

	printf("Enter your number: ");
	scanf("%d",&n);
		
		while(n<=0){
		printf("Please enter a number that is >=1: ");
		scanf("%d",&n);}
		
		if(n==1||n==2)
		printf("\nNO prime number from 0 to %d.",n);
	
			else{
		
			printf("\nPrime numbers from 0 to %d are: ",n);
		
				for(num=2; num<n; num++){
	  	
		  	while(num%i!=0){
  			if(i<num)
  			i++;}
  			if(i==num){
  			j++;
			  printf("%d-",num);
  			i=2;
				}
				}
				printf("\n\nAnd the number of them is: %d TA!\n",j);
			}
		
			printf("\n\n");
			
			
	return 0;
}
