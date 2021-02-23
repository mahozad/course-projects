#include <stdio.h>

main(){


int n,i;
	

	struct phone{
		char fname[30];
		char lname[30];
		int dinum;
		char add[1000];};
	

	struct phone friend[100];
	
	
	printf("Please enter the number of your friends: ");
	scanf("%d",&n);

			
			
	for(i=0; i<n; i++){
		printf("Enter the name: ");
		scanf("%s",&friend[i].fname);
		printf("Enrer the last name: ");
		scanf("%s",&friend[i].lname);
		printf("Enter the phone number: ");
		scanf("%d",&friend[i].dinum);
		printf("Enter the address: ");
		scanf("%s",&friend[i].add);
	}
}
