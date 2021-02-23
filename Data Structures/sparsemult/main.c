#include <stdio.h>


int assertNum();
int assertRange(int max);
int sortCheck(int array[][3]);
void finalSort(int array[][3]);
void showResult(int array[][3]);
void check(int ar1[][3], int ar2[][3]);
void initialize(int num, int array[][3]);
void getValues(int array[][3], int sparseNum);
void sparseMultiplication(int ar1[][3], int ar2[][3], int ar3[][3]);


int main() {
    int sparse1[100][3], sparse2[100][3], sparse3[100][3];
    getValues(sparse1, 1);
    getValues(sparse2, 2);
    check(sparse1, sparse2);
    sparseMultiplication(sparse1, sparse2, sparse3);
    finalSort(sparse3);
    printf("\n\n\n\nThe multiplication sparse matrix:\n\n\n");
    showResult(sparse3);
    printf("\n\n\nPress Enter to exit...");
    char s;
    scanf("%c", &s);
}

void check(int ar1[][3], int ar2[][3]) {
    while (ar1[0][1] != ar2[0][0] || sortCheck(ar1) != 0 || sortCheck(ar2) != 0) {
        if (ar1[0][1] != ar2[0][0]) {
            printf("\n\nNumber of columns of matrix 1 is not equal with"
            " number of rows of matrix 2. Enter again");
        }
        else {
            printf("\n\nYour matrixes are not correctly sorted. They"
            " must be in ascending order. Enter again");
        }
        getValues(ar1, 1);
        getValues(ar2, 2);
    }
}

int sortCheck(int array[][3]) {
    int i;
    for (i = 1; i < array[0][2]; i++) {
        if (array[i][0] > array[i + 1][0]) {
            return -1;
        }
        else if (array[i][0] == array[i + 1][0] &&
                array[i][1] > array[i + 1][1]) {
            return -1;
        }
    }
    return 0;
}

void getValues(int array[][3], int sparseNum) {
    int num;
    printf("\n\n\n...............................\n");
    printf(".:Enter data for sparse %d:.", sparseNum);
    printf("\n...............................\n\n\n");
    printf("Enter number of matrix rows:\t");
    array[0][0] = assertNum();
    printf("Enter number of matrix columns:\t");
    array[0][1] = assertNum();
    printf("Enter number of the elements:\t");
    scanf("%d", &num);
    while (num > 98 || num < 1 || num > array[0][0] * array[0][1]) {
        printf("number of elements must be between 1 to %d"
                       " (less than 99):\t", array[0][0] * array[0][1]);
        scanf("%d", &num);
    }
    array[0][2] = num;
    initialize(num, array);
}

int assertNum() {
    int num;
    scanf("%d", &num);
    while (num < 1) {
        printf("Number cannot be less than 1. Enter another one:\t");
        scanf("%d", &num);
    }
    return num;
}

void initialize(int num, int array[][3]) {
    int count;
    for (count = 1; count <= num; count++) {
        printf("\n\nEnter row of %dth element:\t", count);
        array[count][0] = assertRange(array[0][0]);
        printf("Enter column of %dth element:\t", count);
        array[count][1] = assertRange(array[0][1]);
        printf("Enter the value of the element:\t");
        scanf("%d", &array[count][2]);
    }
}

int assertRange(int max) {
    int num;
    scanf("%d", &num);
    while (num < 0 || num >= max) {
        printf("number must be between 0 to %d\t", max);
        scanf("%d", &num);
    }
    return num;
}

void sparseMultiplication(int ar1[][3], int ar2[][3], int ar3[][3]) {
    int i, j, col, flag;
    int counter = 0;
    int sum = 0;
    for (i = 1; i <= ar1[0][2]; i++) {
        for (j = 1; j <= ar2[0][2]; j++) {
            if (ar1[i][1] == ar2[j][0]) {
                sum += ar1[i][2] * ar2[j][2];
                col = ar2[j][1];
                if (ar1[i][0] != ar1[i + 1][0]) {
                    counter++;
                    ar3[counter][0] = ar1[i][0];
                    ar3[counter][1] = col;
                    ar3[counter][2] = sum;
                    sum = 0;
                    break;
                }
                else
                    while (ar1[i][0] == ar1[i + 1][0]) {
                        i++;
                        for (j = 1; j <= ar2[0][2]; j++) {
                            if (ar2[j][0] == ar1[i][1] && ar2[j][1] == col) {
                                flag = 1;
                                sum += ar1[i][2] * ar2[j][2];
                            }
                        }
                    }
                if (flag == 0) { i--; }
                flag = 0;
                counter++;
                ar3[counter][0] = ar1[i][0];
                ar3[counter][1] = col;
                ar3[counter][2] = sum;
                sum = 0;
                break;
            }
        }
    }
    ar3[0][0] = ar1[0][0];
    ar3[0][1] = ar2[0][1];
    ar3[0][2] = counter;
}

void finalSort(int array[][3]) {
    int i, temp[1][3];
    for (i = 1; i < array[0][2]; i++) {
        if (array[i][0] == array[i + 1][0] && array[i][1] > array[i + 1][1]) {
            temp[0][0] = array[i][0];
            temp[0][1] = array[i][1];
            temp[0][2] = array[i][2];
            array[i][0] = array[i + 1][0];
            array[i][1] = array[i + 1][1];
            array[i][2] = array[i + 1][2];
            array[i + 1][0] = temp[0][0];
            array[i + 1][1] = temp[0][1];
            array[i + 1][2] = temp[0][2];
        }
    }
}

void showResult(int array[][3]) {
    int count, i;
    for (count = 0; count <= array[0][2]; count++) {
        for (i = 0; i < 3; i++) {
            printf("%d\t", array[count][i]);
            if (i == 2 && count == 0) {
                printf("\n-------------------");
            }
            if (i == 2) {
                printf("\n\n");
            }
        }
    }
    printf("\n");
}
