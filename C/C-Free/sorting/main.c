#include <stdio.h>

int main()
{
	int A[101],n,j,k,s;
	printf("Enter number of your terms (must be < 100): ");
	scanf("%d",&n);
	for(j=1; j<=n; j++){
		printf("Enter the %dth number: ",j);
		scanf("%d",&A[j]);
	}
	for(k=1; k<=n; k++){
		s=sort(A[j]);
		printf("%d,",s);
	}
	
	
	
}

	int sort(int A[], int n){
	int i=2, temp;
		if(A[i]<A[i-1]){
		temp=A[i];
		A[i]=A[i-1];
		A[i-1]=temp;
		return (A[i-1]);
	}	
	}