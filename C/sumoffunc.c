#include <stdio.h>
#include <math.h>
int main()
{
	int c,n;
	float k=1,j,i=1,x;
	
	printf("Enter the number of terms: ");
	scanf("%d",&n);
	while(n<=0){
		printf("Number of terms should be >= 1:");
		scanf("%d",&n);}
		
		if(n==1)
		printf("Sum=1\n");
	else
	{
	printf("Enter x: ");
	scanf("%f",&x);
	
	j=x;

		for(c=2; c<=n; c++){
		k=k+(j/i);
		j=j*x;
		i++;
	}

	printf("\nSum of the first %d terms of series with x=%d is: %f\n\n",n,(int)x,k);
	printf("Press Any Key to Continue...\n");  
	getchar();
	getchar();    
	}
	return 0;
}
