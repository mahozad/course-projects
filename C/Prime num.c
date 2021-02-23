#include <stdio.h>

int main()
{
	int n,i=2;

	printf("Enter your number: ");
	scanf("%d",&n);
	
	if(n==1||n==-1||n==0)
		printf("\n%d is NOT a Prime Number\n\n",n);
		else{
		if(n<0)
		n=-n;
  	
	  		while(n%i!=0){
  			if(i<n)
  			i++;}
  			if(i==n)
  			printf("\nPRIME NUMBER\n\n");
  			else
  			printf("\nNOT prime number\n\n");
		}
	
	return 0;
}

