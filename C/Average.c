#include <stdio.h>

main() {
    int n, i;
    float sum, grade, Average;
    printf("Please enter the number of students=");
    scanf("%d", &n);
    while (n < 0) {
        printf("Number of students can't be smaller then 0. Please enter a valid number=");
        scanf("%d", &n);
    }
    if (n == 0)
        printf("\n\t\tAverage = 0\n\n");
    else {
        sum = 0;
        for (i = 1; i < n + 1; i++) {
            printf("Enter grade = ");
            scanf("%f", &grade);
            sum = sum + grade;
        }
        Average = sum / n;
        printf("\n\t\t\tAverage = %f     %c\n\n\n", Average, 2);
    }
}
