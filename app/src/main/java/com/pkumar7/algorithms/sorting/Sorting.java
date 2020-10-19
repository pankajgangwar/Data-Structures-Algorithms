package com.pkumar7.algorithms.sorting;;

public class Sorting {
	public static void main(String[] args) {
		Sorting sorting = new Sorting();
		int arr[] = {9,3,12,60,45,5};
		sorting.sortArray(arr);
	}

	/* 912. Sort an Array
	 * https://leetcode.com/problems/sort-an-array/
	 * */
	public int[] sortArray(int[] nums) {
		quickSort(nums, 0, nums.length - 1);
		return nums;
	}

	public void quickSort(int[] arr, int low, int high){
		if(low < high){
			int p = hoaresPartition(arr, low, high);
			quickSort(arr, low, p - 1 );
			quickSort(arr, p + 1, high);
		}
	}

	public int hoaresPartition(int[] a, int low, int high){
		int pivot = a[low];
		int i = low;
		int j = high + 1;
		while(true){
			do {
				i++;
			}while (i < high && a[i] < pivot);

			do {
				j--;
			}while (j > low && pivot < a[j]);

            /*while(i < high && a[++i] < pivot);
            while(j > low && pivot < a[--j]);*/
			if(i >= j) {
				break;
			}
			swap(a, i, j);
		}
		swap(a, low, j);
		return j;
	}

	public int lomutoPartition(int[] arr, int low, int high ){
		int pivot = arr[high];
		int i = low;
		for (int j = low; j <= high; j++) {
			if(arr[j] < pivot){
				swap(arr, i, j);
				i++;
			}
		}
		swap(arr, i, high);
		return i;
	}

	public void swap(int[] a , int i, int j ){
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}


	public void selectionSort(int arr[]){
		for(int i = 0 ; i < arr.length -1 ; i++){
			int min_pos = i;
			for(int j = i+1; j < arr.length; j++){
				if(arr[i] > arr[j]){
					min_pos = j;
				}
			}
			int temp = arr[i];
			arr[i] = arr[min_pos];
			arr[min_pos] = temp;
		}
	}
	
	public void mergeSort(int arr[],int l,int r){
		int m = (l+r)/2;
		if( l < r){
			mergeSort(arr, l, m);
			mergeSort(arr, m+1, r);
			merge(arr,l,m,r);
		}
	}

	private void merge(int[] arr, int l, int m, int r) {
		int n1 = m-l+1;
		int n2 = r-m;
		
		int L[] = new int[n1];
		int R[] = new int[n2];
		
		for(int i=0;i<n1;i++)
			L[i] = arr[l+i];
		
		for(int j=0;j<n2;j++)
			R[j] = arr[m+1+j];
		
		int i=0,j=0;
		int k=l;
		while(i<n1 && j<n2){
			if(L[i] <= R[j]){
				arr[k] = L[i];
				i++;
			}else{
				arr[k] = R[j];
				j++;
			}
			k++;
		}
		while(i<n1){
			arr[k]=L[i];
			i++;k++;
		}
		while(j<n2){
			arr[k]=R[j];
			j++;k++;
		}
	}

}
