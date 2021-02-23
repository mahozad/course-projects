#include <stdio.h>


main(){
	
	int mat[100][100];
	int n,x;       	//number of terms & desired number
	int i,k;       	//row & culomn of array
	int count=0;	//counter
	int q=0;	   //limits user input
	

	printf("Please enter the number of your terms: ");
	scanf("%d",&n);

		while(n<1){
		printf("Number of terms can't be <1. Please enter a valid number: ");
		scanf("%d",&n);}
	
	

	for(i=0; i<101; i++)
	for(k=0; k<101; k++){
	if(q<n){
		printf("Enter the %dth number: ",q+1);
		scanf("%d",&mat[i][k]);
		q++;}}
	
	
	
	printf("\nPlease enter the number you want to be counted: ");
	scanf("%d",&x);
	
	for(i=0; i<11; i++)
	for(k=0; k<11; k++){
		if(mat[i][k]==x)
		count++;}
	


	printf("%d has been repeated %d times in your numbers.\n\n",x,count);
	
	
} 