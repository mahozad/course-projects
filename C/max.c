#include <stdio.h>

main()
{
	int j,i,num,n;
	j=0; i=1;
	printf("Enter the number of your terms: ");
	scanf("%d",&n);
	for(i=1; i<=n; i++){
		printf("Enter number: ");
		scanf("%d",&num);
		if(num>j)
		j=num;	}
	printf("\nThe biggest number is: %d\n\n",j);
}
