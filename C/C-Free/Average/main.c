#include <stdio.h>

int main()
{
	int n,sum,Average,grade,i;
	printf ("Please enter the number of students=");
	scanf("%d",&n);
	while(n<0){
		printf("Number of students can't be smaller then 0. Please enter a valid number=");
		scanf("%d",&n);
	}
	sum=0;
	for(i=1; i<n+1; i++){
		printf("Enter grade=");
		scanf("%d",&grade);
		sum=sum+grade;
	}
	Average=sum/n;
	printf("Average=%d\n",Average);
}
