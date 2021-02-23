#include <stdio.h>

int main()
{
	int a=1,b=1,n,i,k;
	printf("Please enter the number of terms: ");
	scanf("%d",&n);
	while(n<=0){
		printf("Please enter a valid number (number>0) : ");
		scanf("%d",&n);
	}
	if(n==1)
	printf("1\n");
	if(n==2)
	printf("1 - 2\n");
	if(n>=3){
		printf(" %d - %d -",a,b);
		for(i=1; i<=n-2; i++){
		k=a+b;
		printf(" %d ",k);
		if(i<=n-3)
		printf("");
		a=b;
		b=k;
	}
	printf("\n");
	}
}
