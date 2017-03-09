package solutions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author Michael U. Kalu
 *
 */

public class LSH {

	int k;
	int nShingles;
	int nHash;
	int nDocs;
	int rows, bands, lenBuckets; 
	double t;
	int[][] signatureMatrix;
	boolean[][] shingleMatrix;
	int[][][] arrayBuckets;
	List<String> docs;
	Map<String, Integer> shingles = new HashMap<String, Integer>();
	int a[],b[];
	String databaseFilename;

	/**
	 * 
	 * @param databaseFilename
	 * @param k
	 * @param seed
	 * @param nHash
	 * @param lenBuckets
	 * @param bands
	 * @param rows
	 * @param t
	 */
	public LSH(String databaseFilename, int k , int seed, int nHash, int lenBuckets, int bands , int rows , double t ) {
		this.k = k;
		this.nHash = nHash;
		this.nDocs = 0;
		this.nShingles = 0;
		this.lenBuckets = lenBuckets;
		this.bands = bands;
		this.rows = rows;
		this.t = t;
		this.docs = new ArrayList<String>();
		this.databaseFilename = databaseFilename;

		// Random generator 	
		Random generator = new Random(seed);
		this.a = new int[nHash];
		this.b = new int[nHash];
		for(int i = 0; i < nHash; i++) {
			a[i] = generator.nextInt(1000) + 1;
			b[i] = generator.nextInt(1000) + 1;
		}
		// -----------------------------------------


		// Open file 
		docs = new ArrayList<String>(); // To store each individual document
		Scanner rd = null;

		int id = 0;
		int start = 0;
		int end = this.k;
		String key = "";
		String line = "";

		try {
			rd = new Scanner(new File(this.databaseFilename)); //open the file
			while(rd.hasNextLine()) {
				line = rd.nextLine();
				line = line.toLowerCase().replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]+",""); // parse the line
				docs.add(line);
				while(end <= line.length()) {
					key = line.substring(start, end); // Add the shingle to the map
					if (!shingles.containsKey(key)) {
						shingles.put(key,id);
						id++;
					}
					start++;
					end++;
				}
				start = 0;
				end = this.k;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		nDocs = docs.size();
		nShingles = shingles.size();

		// -------------------------------------------- 

		// Initialize the shingle matrix with all false values
		nShingles = shingles.size(); 
		this.shingleMatrix = new boolean[nShingles][nDocs]; 
		for(int i=0; i< nShingles; i++) { 
			for(int j=0; j < nDocs; j++) { 
				shingleMatrix[i][j] = false; 
			} 
		} 

		// Initialize the signature matrix with all values that equal nShingles
		this.signatureMatrix = new int[nHash][nDocs]; 
		for(int i=0; i< nHash; i++) { 
			for(int j=0; j < nDocs; j++) { 
				signatureMatrix[i][j] = nShingles; 
			} 
		} 
	}

	/**
	 * Returns the number of unique shingles
	 * @return
	 */
	public int computeUniqueShingles(){
		return this.nShingles;
	}

	/**
	 * In this method we are going to compute the shingle matrix. Every position in the matrix is initalized to false to begin with
	 * @return
	 */
	public boolean[][] computeShingleMatrix(){
		for(int i=0; i< nDocs; i++) {
			String line = docs.get(i);

			/*
			 * we are iterating through all the keys in the map
			 */
			for (Map.Entry<String, Integer> map : shingles.entrySet()){
				String k = map.getKey();
				Integer id = map.getValue();
				if (line.contains(k)) { // if the document contains that shingle then we make that position true
					shingleMatrix[id][i] = true;
				}
			}
		}
		return this.shingleMatrix;
	}

	/**
	 * Converting a shingle matrix to signature matrix
	 * @return
	 */
	public int[][] shingleMatrixToSignatureMatrix(){		
		int hash_values;
		for(int i = 0; i < nDocs; i++) {
			for(int j = 0; j < nHash; j++){
				for (int k = 0; k < nShingles; k++){
					if (shingleMatrix[k][i]){ // if the position of matrix is true then create the hash value
						hash_values = (a[j] * k + b[j]) % nShingles;
						if (signatureMatrix[j][i] > hash_values) {
							signatureMatrix[j][i] = hash_values; // place hash value in matrix
						}
					}
				}
			}
		}
		return this.signatureMatrix;
	}


	/**
	 * Returns 3D array 
	 * @return
	 */
	public int[][][] doLSH() {
		int hash_value = 0;
		int count = 0;
		int mod = 0;
		int sub = 0;

		// Initialize the 3D array "array buckets" with all false vales
		this.arrayBuckets = new int[bands][lenBuckets][100];
		for(int i = 0; i < bands; i++){
			for(int j = 0; j < lenBuckets; j++){
				for(int m = 0; m < 100; m++){
					arrayBuckets[i][j][m] = -1;
				}
			}
		}

		for(int i = 0; i < nDocs; i++){
			for (int j = 0; j < nHash; j++){
				mod = j % rows; // mod index with num of rows
				hash_value += signatureMatrix[j][i];
				sub = rows - 1; // last element of row
				if (mod == sub) {
					hash_value = hash_value % lenBuckets;
					int x = 0;
					while(arrayBuckets[count][hash_value][x] != -1){
						x++;
					}
					arrayBuckets[count][hash_value][x] = i;
					count++;
					hash_value = 0;
				}
			}
			count = 0;
			hash_value = 0;
		}
		return arrayBuckets;
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
	public int nearestNeighbor(int d){
		this.doLSH(); // call the do LSH method to initialize the arrayBuckets 
		int hash_value = 0;
		int count = 0;
		int nearestNeighborDoc = -1;
		int union = 0;
		int intersect = 0;
		int mod = 0;
		int sub = 0;
		int id = 0;
		double ntf = 0.0;
		double T = 0.0;

		int i = 0;
		while(i < nHash){
			hash_value += signatureMatrix[i][d];
			mod = i % this.rows;
			sub = this.rows - 1;
			if( mod == sub){
				hash_value = hash_value % lenBuckets;
				int[] docsList = arrayBuckets[count][hash_value];
				int x = 0;
				while(docsList[x] != -1){
					id = docsList[x];
					if (d != id) {
						union = 0;
						intersect = 0; 
						for (int k = 0; k < nShingles; k++) {
							if (shingleMatrix[k][d] && shingleMatrix[k][id]){ // Intersect
								intersect++;
							}
							if (shingleMatrix[k][d] || shingleMatrix[k][id]){ // Union
								union++;
							}
						}
						T = (double)intersect / (double)union;
						if (T > this.t && T > ntf){
							ntf = T;
							nearestNeighborDoc = id;
						}

					}
					++x;
				}
				count++;
				hash_value = 0;
			}

			i++;
		}


		return nearestNeighborDoc;
	}


	/**
	 * HELPER METHODS
	 */

	/**
	 * This helper method creates k shingles
	 * @param line
	 * @return
	 */
	public ArrayList<String> createShingles(String line){
		ArrayList<String> slist = new ArrayList<String>();
		String key = "";
		for(int i = 0; i < line.length(); i++) {
			if ((i+k) > line.length()) break;
			key = line.substring(i, i+k);
			slist.add(key);
			key = "";
		}
		return slist;
	}

}
