#include <stdio.h>

int main()
{
	int i,j,k,o;
	int n,temp;
	int A[1001];
	
	printf("Enter the number of your terms: ");
	scanf("%d",&n);
	
	while(n>999||n<0)
	{
		printf("Your number should be 0<-<1000: ");
		scanf("%d",&n);
	}
	
	for(i=1; i<=n; i++)
	{
		printf("Enter the %dth number: ",i);
		scanf("%d",&A[i]);
	}
	






int B[10];
scanf("%s",B);
puts(B);






	int sort(int A[],int n){
		
	
	for(j=1; j<=n; j++){
		for(k=1; k<=n; k++){
			if(A[k]>A[k+1]){
				temp=A[k];
				A[k]=A[k+1];
				A[k+1]=temp;
			}
		}
	}

	}














	printf("\n\n");
				for(o=1; o<=n; o++){
			printf(" %d ",A[o]);
			if(o<n)
			printf(";");
		}
		printf("\n\n");
}
