#include <stdio.h>

 main()
{
	float i=0,j;
	int n;
	printf("Enter the number = ");
	scanf("%d",n);
	while(!n==0){
	j=n%10;
	n=n/10;
	i=i*10+j;
	}
	if(i==j){
	printf("Maghloub");
	}
	else{
	printf("Not Maghloub");
	}
}
