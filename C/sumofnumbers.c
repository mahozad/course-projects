#include <stdio.h>

main()
{

	int m,n,i;
	int temp,sum=0;
	printf("Enter the first number: ");
	scanf("%d",&m);
	printf("Enter the second number: ");
	scanf("%d",&n);

		if(n<m){
		temp=n;
		n=m;
		m=temp;
				}

		for(i=m+1; i<n; i++){
		sum=sum+i;
							}
	
	printf("\n\n\t\tSum of numbers from %d to %d is: %d\n\n\n",m,n,sum);

}