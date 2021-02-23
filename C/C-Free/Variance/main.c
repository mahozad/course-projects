#include <stdio.h>

main()
{
	int avg,sum=0,n,i;
	int number[100];
	printf("Enter the number of your terms: ");
	scanf("%d",&n);
	for(i=1; i<=n; i++){
	printf("Enter number: ");
	scanf("%d",&number[i]);
	sum=sum+number[i];
	}
	avg=sum/n;
	printf("Avg= %d",avg);
}
