package com.mission.google.datastructures;;

public class Sorting {
	public static void main(String[] args) {
		
		Sorting sorting = new Sorting();
		
		int arr[] = {9,3,12,60,45,5};
		
		System.out.println("######## Unsorted Arr ############");
		sorting.printArr(arr);
		
		sorting.selectionSort(arr);
		
		sorting.printArr(arr);
	}

	
	private void printArr(int arr[]) {
		// TODO Auto-generated method stub
		for(int i=0; i < arr.length; i++){
			System.out.print(arr[i] + " \t");
		}
		System.out.print("\n");
	}

	public void selectionSort(int arr[]){
		System.out.println("######## Selection Sort ############");
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
		// TODO Auto-generated method stub
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
