#include <stdio.h>
void A(int x, int *a, int *b);
int main(){
	int m,n,w;
	printf("Enter your number: ");
	scanf("%d",&w);
	A(w,&m,&n);
	printf("m=%d , n=%d\n",m,n);
}

void A(int x, int *a, int *b){
	int k=x,f,j=0,i=0;
	while(x!=0){
	i++;
	x=x/10;}
	while(k!=0){
		f=k%10;
		j+=f;
		k=k/10;	}
		*a=i;
		*b=j;
}

