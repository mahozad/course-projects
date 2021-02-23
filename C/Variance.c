#include <stdio.h>

main()
{
	int n,i,j;
	int number[100];
	float sum=0,avg,variance;
	printf("Enter the number of your terms: ");
	scanf("%d",&n);
	for(i=1; i<=n; i++){
	printf("Enter number: ");
	scanf("%d",&number[i]);
	sum=sum+number[i];
	}
	avg=sum/n;
	printf("\n\nAvg= %f\n",avg);
	sum=0;
		for(i=1; i<=n; i++){
		j=(number[i]-avg)*(number[i]-avg);
		sum=sum+j;
	}
	variance=sqrt((sum/(n-1)));
	printf("Variance= %f\n\n\n",variance);
}
