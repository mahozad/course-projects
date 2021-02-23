#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <sys/time.h>

void quickSort(long array[], long begin, long end);
void insertionSort(long a[], long size);
void initTimer(int function, long a[]);

void main() {
    long counter;//for initializing arrays
    long array1[50000];
    long array2[50000];
    for (counter = 0; counter < 50000; counter++) {
        array2[counter] = array1[counter] = (long) rand();
    }
    initTimer(1, array1);//run quickSort and calculate its time
    initTimer(2, array2);//run insertionSort and calculate its time
    printf("Sorry sir, we are currently working on mergeSort!");
    printf("\n\n\n\n\nPress Enter to exit...");
    char s;
    scanf("%c", &s);
}

//this function runs one of sort funcs and calculates its time
//:function: variable is for running whether quick or insertion sort
void initTimer(int function, long a[]) {
    long counter;
    long milisec1;//holds start time
    long milisec2;//holds end time
    srand(time(NULL));
    struct timeval te;
    gettimeofday(&te, NULL);
    milisec1 = te.tv_sec * 1000LL + te.tv_usec / 1000;
    if (function == 1) {
        quickSort(a, 0, 50000);
    } else {
        insertionSort(a, 50000);
    }
    struct timeval ta;
    gettimeofday(&ta, NULL);
    milisec2 = ta.tv_sec * 1000LL + ta.tv_usec / 1000;
    printf("Sorted array of %s Sort:\n\n", function == 1 ? "Quick" : "Insertion");
    for (counter = 0; counter < 50000; counter++) {
        printf("%10d\t", a[counter]);
    }
    printf("\ntime for %s Sort: %d milliseconds\n\n\n\n\n",function == 1 ? "Quick" : "Insertion", milisec2 - milisec1);
}

void quickSort(long array[], long begin, long end) {
    if (begin < end) {
        long piv = array[begin];
        long i = begin + 1;
        long j = end;
        long temp;
        while (i < j) {
            if (array[i] <= piv) {
                i++;
            }
            else if (array[j] >= piv) {
                j--;
            }
            else {
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        if (array[i] < piv) {
            temp = array[i];
            array[i] = array[begin];
            array[begin] = temp;
            i--;
        }
        else {
            i--;
            temp = array[i];
            array[i] = array[begin];
            array[begin] = temp;
        }
        quickSort(array, begin, i);
        quickSort(array, j, end);
    }
}

void insertionSort(long a[], long size) {
    long i, j, k, temp;
    for (i = 1; i < size; i++) {
        for (j = 0; j < i; j++) {
            if (a[i] < a[j]) {
                temp = a[i];
                for (k = i; k > j; k--) {
                    a[k] = a[k - 1];
                }
                a[j] = temp;
            }
        }
    }
}
