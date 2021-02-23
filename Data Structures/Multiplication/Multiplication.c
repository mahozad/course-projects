#include <stdio.h>

struct LinkedList {
    char items[200];
    struct LinkedList *next;
    struct LinkedList *prev;
};

typedef struct LinkedList LL;

int multiply(char op1[], char op2[], LL **l);
void getAndCheckNum(char op[]);
void showResult(LL *l);
int length(char op[]);
int carry(int num);
long power(int n);

int main() {
    LL *l;
    char op1[100];
    char op2[100];
    printf("Enter the first number: ");
    getAndCheckNum(op1);
    printf("\nEnter the second number: ");
    getAndCheckNum(op2);
    if (multiply(op1, op2, &l) != 0) {
        printf("Sorry, there are some problems in multiplication."
                       " Please try again later.");
        return 1;
    }
    showResult(l);
    return 0;
}

void getAndCheckNum(char op[]) {
    int i;
    gets(op);
    for (i = 0; op[i] != '\0'; i++) {
        if (!(op[i] >= '0' && op[i] <= '9')) {
            puts("\nYour number contains invalid characters."
                         "\nPlease enter your number correct: ");
            getAndCheckNum(op);
        }
    }
    if (length(op) > 100) {
        puts("\nYour number exceeds maximum length."
                     "\nPlease enter another one: ");
        getAndCheckNum(op);
    }
}

int multiply(char op1[], char op2[], LL **l) {
    LL *p1, *p2;
    long long temp = 0;
    long long product = 0;
    int i, j, k = 0;
    p1 = (LL *) malloc(sizeof(LL));
    if (!p1)return 1;
    *l = p1;
    for (i = length(op1); i >= 0; i--) {
        for (j = length(op2); j >= 0; j--) {
            product += (op1[i] - '0') * (op2[j] - '0');
        }
        product *= power(k++);
    }
    return 0;
}

int length(char op[]) {
    int i;
    for (i = 0; op[i] != '\0'; i++);
    return i - 1;
}

int carry(int num) {
    return num / 10;
}

long power(int n) {
    if (n == 0) { return 1; }
    long pow = 1;
    int i;
    for (i = 0; i < n; i++) {
        pow *= 10;
    }
    return pow;
}

void showResult(LL *l) {

}
