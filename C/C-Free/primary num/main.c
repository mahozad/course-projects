#include <stdio.h>

int main()
{
	int n,i;

	printf("Please enter your number: ");
	scanf("%d",&n);

		if(n<0)
		n=-n;

		for(i=2; i<n; i++){
		if(n%i==0){
		printf("\n%d is NOT a Prime Number.\n\n",n);
		i=n;}
	}
	
	if(i==n)
	printf("\n%d is a PRIME NUMBER.\n\n",n);

}
