#include <stdio.h>
void strcat(int ar1[],int ar2[]);
main(){
	int str1[50];
	int str2[50];
	printf("Please enter the first name: ");
	scanf("%s",str1);
	printf("Please enter the second name: ");
	scanf("%s",str2);
	strcat(str1,str2);
	printf("%s",str1);
}

void strcat(int ar1[],int ar2[]){

	int i=0,j=0,k;

	while(ar1[i]!=0)
		i++;
		
	
	while(ar2[j]!=0)
	j++;
	
	
		for(k=0; k<j; k++){
		ar1[i]=ar2[k];
		i++;
	}
	printf("%s",ar1);
}